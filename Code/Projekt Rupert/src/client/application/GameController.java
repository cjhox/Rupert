package client.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jms.JMSException;

import shared.Action;
import shared.Action.PlaceAction;
import shared.GameCoordinate;
import shared.Player;
import shared.Unittype;
import shared.com.UpdateHealth;
import shared.com.UpdateHealthData;
import shared.com.UpdatePlacement;
import shared.com.UpdatePosition;
import shared.gameround.MoveDraw;
import shared.gameround.PlaceDraw;
import client.domain.GameModel;
import client.resources.GameListener;
import client.resources.UnitListenerFactoryInterface;

public class GameController {

	private GameModel					model;
	private boolean						freezCanvas			= false;
	private final UnitListenerFactory	unitListenerFactory	= new UnitListenerFactory();

	public GameController(GameModel model) {
		super();
		this.model = model;
	}

	public void placeUnits(UpdatePlacement updatePlacement) {
		for (PlaceDraw placeDraw : updatePlacement.getUpdate()) {
			model.place(placeDraw);
		}
	}

	/**
	 * Nimmt als Parameter eine UpdatePosition vom Server entgegen und bewegt
	 * die Einheiten entsprechend der darin enthaltenen Anweisungen.
	 */
	public void moveUnits(UpdatePosition updatePosition) {
		for (MoveDraw moveDraw : updatePosition.getUpdate()) {
			model.move(moveDraw);
		}
	}

	/**
	 * Nimt als Parameter eine UpdateHealth vom Server entgegen, berechnet
	 * entsprechend den darin enthaltenen Informationen wie der Server den für
	 * jede Unit entstehenden Schaden und aktualisiert entsprechend deren
	 * Health. Prüft am Ende ob das Spiel vorbei ist.
	 */
	public void hurtUnits(UpdateHealth updateHealth) {
		for (UpdateHealthData data : updateHealth.getUpdate()) {
			model.fight(data);
		}
	}

	public void setFreezCanvas(boolean freezCanvas) {
		this.freezCanvas = freezCanvas;
	}
	
	public boolean isFreezCanvas() {
		return freezCanvas;
	}

	public UnitListenerFactory getUnitListenerFactory() {
		return unitListenerFactory;
	}

	public ActionListener getActionButtonListener(Action action) {
		return new ActionButtonListener(action);
	}

	public ActionListener getPlaceButtonListener(Unittype unittype) {
		return new PlaceButtonListener(unittype);
	}

	public ActionListener getOkButtonListener() {
		return new OkButtonListener();
	}

	/**
	 * Listenerklasse für auf der GameView platzierte Units. Selektiert die
	 * angeklickte Unit und zeichnet die TargetArea (Aktionsradius der Unit in
	 * dieser Phase).
	 */
	private class UnitListener implements GameListener {

		private Unittype	unittype;
		private Player		player;

		public UnitListener(Unittype unittype, Player player) {
			this.unittype = unittype;
			this.player = player;
		}

		@Override
		public void actionPerformed(GameCoordinate source) {
			if (player.equals(model.getPlayer())) {
				model.select(source);
				model.setTargetArea(source, unittype, new GameActionListener(source));
			} else {
				model.select(source);
				model.clearTargetArea();
			}
		}

		@Override
		public String toString() {
			return "UnitListener [unittype=" + unittype + ", player=" + player + "]";
		}
	}

	private class UnitListenerFactory implements UnitListenerFactoryInterface {

		public GameListener getInstance(Unittype unittype, Player player) {
			return new UnitListener(unittype, player);
		}

		@Override
		public String toString() {
			return "UnitListenerFactory";
		}
	}

	/**
	 * 
	 */
	private class GameActionListener implements GameListener {

		private GameCoordinate	source;

		public GameActionListener(GameCoordinate source) {
			super();
			this.source = source;
		}

		@Override
		public void actionPerformed(GameCoordinate target) {
			model.addDraw(source, target);
			model.clearTargetArea();
		}

		@Override
		public String toString() {
			return "GameActionListener [source=" + source + "]";
		}
	}

	/**
	 * Listener für den DeffendButton der ButtonView. Setzt wenn möglich die
	 * aktuell gewählte Einheit in den Verteidigungsmodus.
	 */
	private class ActionButtonListener implements ActionListener {

		private Action	action;

		public ActionButtonListener(Action action) {
			super();
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.getUnittypeFromSelectedUnit() != Unittype.EMPTY) {
				model.addButtonDraw(model.getGCFromSelectedUnit(), action);
				model.clearTargetArea();
			}
		}

		@Override
		public String toString() {
			return "ActionButtonListener [action=" + action + "]";
		}
	}

	private class PlaceButtonListener implements ActionListener {

		private Unittype	unittype;

		public PlaceButtonListener(Unittype unittype) {
			super();
			this.unittype = unittype;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.getUnittypeFromSelectedUnit() == Unittype.EMPTY) {
				model.addPlaceDraw(model.getGCFromSelectedUnit(), PlaceAction.PLACE, unittype);
			}
		}

		@Override
		public String toString() {
			return "PlaceButtonListener [unittype=" + unittype + "]";
		}
	}

	/**
	 * Listenerklasse für den OK Button der ButtonView. Leitet die aktuelle
	 * Phase zur Berechnung an den Server weiter.
	 */
	private class OkButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!freezCanvas) {
				try {
					model.publish(model.getPhase());
				} catch (JMSException e) {
				}
			}
		}

		@Override
		public String toString() {
			return "OkButtonListener";
		}
	}
}
