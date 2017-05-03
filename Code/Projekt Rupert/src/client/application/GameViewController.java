package client.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import shared.Action.FightAction;
import shared.GameCoordinate;
import shared.Player;
import shared.Unittype;
import shared.com.UpdateHealth;
import shared.com.UpdatePlacement;
import shared.com.UpdatePosition;
import shared.game.Game;
import shared.gameround.GameState.PlayState;
import client.domain.DrawModel;
import client.domain.GameModel;
import client.domain.WindowModel;
import client.resources.Coordinate;
import client.resources.GameListener;
import client.ui.ButtonView;
import client.ui.ClickListener;
import client.ui.DrawView;
import client.ui.GameView;
import client.ui.MainGameView;
import client.ui.StateView;

/**
 * Initialisiert die MainGameView, welche während dem Spielen dargestellt wird.
 * Handelt alle Ein- und Ausgaben während dem Spielen.
 */
public class GameViewController {

	private GameController	gameController;
	private GameModel		model;
	private GameView		gameView;
	private StateView		stateView;
	private ButtonView		buttonView;
	private DrawView		drawView;
	private MainGameView	mainGameView;

	public GameViewController(MainGameView mainGameView, WindowModel windowModel, Game sourceGame) {
		super();
		this.mainGameView = mainGameView;

		model = new GameModel(windowModel);
		gameController = new GameController(model);
		model.initializeGame(sourceGame, new MapListener(), gameController.getUnitListenerFactory());

		showGui();
		addListeners();

		model.showStartArea(new StartFieldListener());
		model.setPlayState(PlayState.Place);
	}

	private void showGui() {
		gameView = new GameView(model);
		stateView = new StateView(model);
		buttonView = new ButtonView();
		drawView = new DrawView(new DrawModel(model));

		mainGameView.addGameView(gameView);
		mainGameView.addStateView(stateView);
		mainGameView.addButtonView(buttonView);
		mainGameView.addDrawView(drawView);
	}

	private void addListeners() {
		gameView.addCanvasClickListener(new CanvasClickListener());
		buttonView.addOKButtonListener(gameController.getOkButtonListener());
		buttonView.addOKButtonListener(new OkButtonListener());
		buttonView.addDeffendButtonListener(gameController.getActionButtonListener(FightAction.DEFENCE));
		buttonView.addHealButtonListener(gameController.getActionButtonListener(FightAction.HEAL));
		buttonView.addArcherButtonListener(gameController.getPlaceButtonListener(Unittype.ARCHER));
		buttonView.addKnightButtonListener(gameController.getPlaceButtonListener(Unittype.KNIGHT));
		buttonView.addSwordsmanButtonListener(gameController.getPlaceButtonListener(Unittype.SWORDSMAN));
		buttonView.addArcherButtonListener(new PlaceButtonListener());
		buttonView.addKnightButtonListener(new PlaceButtonListener());
		buttonView.addSwordsmanButtonListener(new PlaceButtonListener());
	}

	public void showWinner(Player player) {
		gameController.setFreezCanvas(true);
	}

	public void placeUnits(UpdatePlacement updatePlacement) {
		gameController.placeUnits(updatePlacement);

		model.clearStartArea();
		buttonView.disableUnitButtons();
		changePhase(PlayState.Move);
	}

	/**
	 * Nimmt als Parameter eine UpdatePosition vom Server entgegen und bewegt
	 * die Einheiten entsprechend der darin enthaltenen Anweisungen.
	 */
	public void moveUnits(UpdatePosition updatePosition) {
		gameController.moveUnits(updatePosition);

		buttonView.enableDeffendBtn();
		buttonView.enableHealBtn();
		changePhase(PlayState.Fight);
	}

	/**
	 * Nimt als Parameter eine UpdateHealth vom Server entgegen, berechnet
	 * entsprechend den darin enthaltenen Informationen wie der Server den für
	 * jede Unit entstehenden Schaden und aktualisiert entsprechend deren
	 * Health. Prüft am Ende ob das Spiel vorbei ist.
	 */
	public void hurtUnits(UpdateHealth updateHealth) {
		gameController.hurtUnits(updateHealth);

		buttonView.disableDeffendBtn();
		buttonView.disableHealBtn();
		changePhase(PlayState.Move);
	}

	/**
	 * Leitet das wechseln der Phase an die GameView weiter.
	 */
	private void changePhase(PlayState playState) {
		repaint();
		gameController.setFreezCanvas(true);
		gameView.showPhaseChange(playState);
		model.clearTargetArea();
		model.setPlayState(playState);
		gameController.setFreezCanvas(false);
		buttonView.enableOkBtn();
	}
	
	public void repaint() {
		gameView.repaint();
	}

	/**
	 * Listenerklasse für alle Actions auf der Map. Selektiert das angeklickte
	 * Feld.
	 */
	private class MapListener implements GameListener {

		@Override
		public void actionPerformed(GameCoordinate gameCoordinate) {
			model.select(gameCoordinate);
			model.clearTargetArea();
			if (model.getPlayState() == PlayState.Place) {
				buttonView.disableUnitButtons();
			}
		}

		@Override
		public String toString() {
			return "MapListener";
		}
	}

	/**
	 * 
	 */
	private class StartFieldListener implements GameListener {

		@Override
		public void actionPerformed(GameCoordinate gameCoordinate) {
			buttonView.enableUnitButtons();
			model.select(gameCoordinate);
		}

		@Override
		public String toString() {
			return "StartFieldListener";
		}
	}

	private class PlaceButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			buttonView.disableUnitButtons();
		}

		@Override
		public String toString() {
			return "PlaceButtonListener - View";
		}
	}

	/**
	 * Listenerklasse für den OK Button der ButtonView. Leitet die aktuelle
	 * Phase zur Berechnung an den Server weiter.
	 */
	private class OkButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!gameController.isFreezCanvas()) {

				buttonView.disableOkBtn();
			}
		}

		@Override
		public String toString() {
			return "OkButtonListener";
		}
	}

	/**
	 * ClickListener für das Canvas.
	 */
	private class CanvasClickListener extends ClickListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			click(new Coordinate(e.getPoint()));
			repaint();
		}

		private void click(Coordinate coordinate) {
			if (!gameController.isFreezCanvas()) {
				model.doClick(coordinate);
			}
		}
	}
}
