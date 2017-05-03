package client.domain;

import java.awt.Graphics;
import java.util.Observer;

import shared.Action;
import shared.Action.FightAction;
import shared.Action.MoveAction;
import shared.Action.PlaceAction;
import shared.GameCoordinate;
import shared.Unittype;
import shared.com.UpdateHealthData;
import shared.game.Game;
import shared.gameround.Draw;
import shared.gameround.FightDraw;
import shared.gameround.FightPhase;
import shared.gameround.MoveDraw;
import shared.gameround.MovePhase;
import shared.gameround.Phase;
import shared.gameround.PlaceDraw;
import shared.gameround.PlacePhase;
import shared.gameround.GameState.PlayState;
import client.resources.Coordinate;
import client.resources.GameListener;
import client.resources.UnitListenerFactoryInterface;

public class GameModel extends WindowModel implements GameModelInterface {

	private DrawableGame		game;
	private Phase<MoveDraw>		movePhase;
	private Phase<FightDraw>	fightPhase;
	private Phase<PlaceDraw>	placePhase;
	private PlayState			playState;

	public GameModel(WindowModel windowModel) {
		super(windowModel.player, windowModel.toServer);
	}

	public void initializeGame(Game sourceGame, GameListener mapListener,
			UnitListenerFactoryInterface unitListenerFactory) {
		game = new DrawableGame(sourceGame, mapListener, unitListenerFactory);
		placePhase = new PlacePhase(player);
		movePhase = new MovePhase(player);
		fightPhase = new FightPhase(player);
	}

	@Override
	public boolean moveUp(int selectedIndex) {
		if (getPhase().moveUp(selectedIndex)) {
			doNotify();
			return true;
		}
		return false;
	}

	@Override
	public boolean moveDown(int selectedIndex) {
		if (getPhase().moveDown(selectedIndex)) {
			doNotify();
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(int selectedIndex) {
		if (getPhase().delete(selectedIndex)) {
			doNotify();
			return true;
		}
		return false;
	}

	public void addGameObserver(Observer o) {
		game.addObserver(o);
	}

	public void setPlayState(PlayState playState) {
		this.playState = playState;
		switch (playState) {
		case Place:
			placePhase = new PlacePhase(player);
			break;
		case Move:
			movePhase = new MovePhase(player);
			break;
		case Fight:
			fightPhase = new FightPhase(player);
			break;
		}
		doNotify();
	}

	public PlayState getPlayState() {
		return playState;
	}

	public void addDraw(GameCoordinate source, GameCoordinate target) {
		if (source.equals(target)) {
			return;
		}
		switch (playState) {
		case Move:
			movePhase.addDraw(new MoveDraw(source, target, MoveAction.MOVE));
			doNotify();
			break;
		case Fight:
			if (game.isTargetAllowed(target, player)) {
				fightPhase.addDraw(new FightDraw(source, target, FightAction.ATTACK));
				doNotify();
			}
			break;
		case Place:
			break;
		}
	}

	public void addButtonDraw(GameCoordinate source, Action action) {
		if (playState == PlayState.Fight) {
			fightPhase.addDraw(new FightDraw(source, source, (FightAction) action));
			doNotify();
		}
	}

	public void addPlaceDraw(GameCoordinate target, PlaceAction action, Unittype unittype) {
		placePhase.addDraw(new PlaceDraw(target, action, unittype, player));
		doNotify();
	}

	public void select(GameCoordinate source) {
		game.select(source);
	}

	public void showStartArea(GameListener gameListener) {
		game.showStartArea(gameListener, player.getPosition());
	}

	public void clearStartArea() {
		game.clearStartArea();
	}

	public void setTargetArea(GameCoordinate source, Unittype unittype, GameListener gameListener) {
		switch (playState) {
		case Move:
			game.setTargetArea(source, unittype.getSpeed(), gameListener);
			break;
		case Fight:
			game.setTargetArea(source, unittype.getRange(), gameListener);
			break;
		case Place:
			break;
		}
	}

	public void clearTargetArea() {
		game.clearTargetArea();
	}

	public Phase<? extends Draw> getPhase() {
		switch (playState) {
		case Place:
			return placePhase;
		case Move:
			return movePhase;
		case Fight:
			return fightPhase;
		}
		return null;
	}

	public int getHealthFromSelectedUnit() {
		return game.getHealthFromSelectedUnit();
	}

	public Unittype getUnittypeFromSelectedUnit() {
		return game.getUnittypeFromSelectedUnit();
	}

	public GameCoordinate getGCFromSelectedUnit() {
		return game.getGCFromSelectedUnit();
	}

	public void place(PlaceDraw placeDraw) {
		game.place(placeDraw);
	}

	public void move(MoveDraw moveDraw) {
		game.move(moveDraw);
	}

	public void fight(UpdateHealthData data) {
		game.fight(data);
	}

	public void doClick(Coordinate coordinate) {
		game.doClick(coordinate);
	}

	public void draw(Graphics g) {
		game.draw(g);
	}

	private void doNotify() {
		setChanged();
		notifyObservers();
	}
}