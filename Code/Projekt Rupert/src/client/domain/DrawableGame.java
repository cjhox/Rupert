package client.domain;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import client.resources.Coordinate;
import client.resources.GameListener;
import client.resources.UnitListenerFactoryInterface;
import shared.GameCoordinate;
import shared.PlayerPosition;
import shared.Unittype;
import shared.game.Game;
import shared.gameround.MoveDraw;
import shared.gameround.PlaceDraw;

/**
 * Klasse für ein Spiel.
 */
public class DrawableGame extends Game {

	private static final long	serialVersionUID	= -7985890433855278677L;

	private DrawableGameMap		gameMap;
	private List<AreaField>		targetArea;
	private List<AreaField>		startArea;

	public DrawableGame(Game sourceGame, GameListener mapListener, UnitListenerFactoryInterface unitListenerFactory) {
		super(sourceGame.getHost(), sourceGame.getClient());
		this.gameMap = new DrawableGameMap(sourceGame.getGameMap(), mapListener, unitListenerFactory);
		targetArea = new ArrayList<AreaField>();
		startArea = new ArrayList<AreaField>();
	}

	public void select(GameCoordinate gameCoordinate) {
		gameMap.select(gameCoordinate);
		setChanged();
		notifyObservers();
	}

	public void showStartArea(GameListener gameListener, PlayerPosition playerPosition) {
		startArea.clear();

		startArea.addAll(gameMap.getStartField(gameListener, playerPosition));
	}

	public void setTargetArea(GameCoordinate gameCoordinate, int targetRange, GameListener actionListener) {
		targetArea.clear();
		int x = gameCoordinate.x - targetRange;
		int y = gameCoordinate.y;
		int anz = 1;
		int offset = 0;
		targetArea.add(new AreaField(new GameCoordinate(x, y), actionListener));
		for (int i = 1; i <= targetRange * 2; i++) {
			if (x + i <= gameCoordinate.x) {
				anz += 2;
			} else {
				anz -= 2;
				offset += 2;
			}
			for (int j = 0; j < anz; j++) {
				targetArea.add(new AreaField(new GameCoordinate(x + i, y - i + j + offset), actionListener));
			}
		}
	}

	public void clearTargetArea() {
		targetArea.clear();
	}

	public void clearStartArea() {
		startArea.clear();
	}

	public int getHealthFromSelectedUnit() {
		return getGameMap().getHealthFromSelectedUnit();
	}

	public Unittype getUnittypeFromSelectedUnit() {
		return getGameMap().getUnittypeFromSelectedUnit();
	}

	public GameCoordinate getGCFromSelectedUnit() {
		return getGameMap().getGCFromSelectedUnit();
	}

	@Override
	public boolean move(MoveDraw moveDraw) {
		return getGameMap().move(moveDraw);
	}

	@Override
	public boolean place(PlaceDraw placeDraw) {
		return getGameMap().place(placeDraw);
	}

	@Override
	public DrawableGameMap getGameMap() {
		return gameMap;
	}

	public void doClick(Coordinate coordinate) {

		for (AreaField startField : startArea) {
			if (startField.isClicked(coordinate)) {
				startField.doClick(coordinate.getGameCoordinate());
				return;
			}
		}

		for (AreaField targetField : targetArea) {
			if (targetField.isClicked(coordinate)) {
				targetField.doClick(coordinate.getGameCoordinate());
				return;
			}
		}

		gameMap.doClick(coordinate.getGameCoordinate());
	}

	public void draw(Graphics g) {
		gameMap.draw(g);

		for (AreaField targetField : targetArea) {
			targetField.draw(g);
		}

		for (AreaField startField : startArea) {
			startField.draw(g);
		}
	}

	@Override
	public String toString() {
		return "DrawableGame [gameMap=" + gameMap + ", targetArea=" + targetArea + ", host=" + host + ", client="
				+ client + "]";
	}
}
