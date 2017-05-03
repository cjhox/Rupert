package client.domain;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import client.resources.DrawCoordinate;
import client.resources.GameListener;
import client.resources.Sprite;
import client.resources.UnitListenerFactoryInterface;
import shared.GameCoordinate;
import shared.game.Field;
import shared.game.NullUnit;
import shared.game.Unit;
import shared.gameround.PlaceDraw;

/**
 * Auf der Map dargestelltes Feld.
 */
public class DrawableField extends Field implements Sprite {

	private static final long		serialVersionUID	= -9186207402974401105L;

	private DrawCoordinate			drawCoordinate;
	private List<GameListener>		actionListeners		= new LinkedList<GameListener>();
	private UnitListenerFactoryInterface	unitListenerFactory;
	private DrawableUnit			unit				= new DrawableNullUnit();

	public DrawableField(Field field, GameListener actionListener, UnitListenerFactoryInterface unitListenerFactory) {
		super(field.getFieldtype(), field.getGameCoordinate());
		this.unitListenerFactory = unitListenerFactory;
		addActionListener(actionListener);
		drawCoordinate = DrawCoordinate.getDrawCoordinate(gameCoordinate, GameCoordinate.STEP_WIDTH,
				GameCoordinate.STEP_HEIGHT);
		if (!(field.getUnit() instanceof NullUnit)) {
			unit = new DrawableUnit(field.getUnit(), gameCoordinate, unitListenerFactory);
		}
	}

	@Override
	public boolean placeUnit(PlaceDraw placeDraw) {
		if (unit instanceof DrawableNullUnit) {
			unit = new DrawableUnit(placeDraw.getNumber(), placeDraw.getUnittype(), placeDraw.getPlayer(),
					gameCoordinate, unitListenerFactory);
			return true;
		}
		return false;
	}

	@Override
	public boolean placeUnit(Unit unit) {
		if (unit instanceof DrawableUnit) {
			if (this.unit instanceof DrawableNullUnit) {
				this.unit = (DrawableUnit) unit;
				this.unit.move(gameCoordinate);
				return true;
			}
		}
		return false;
	}

	@Override
	public DrawableUnit removeUnit() {
		DrawableUnit unit = this.unit;
		this.unit = new DrawableNullUnit();
		return unit;
	}

	@Override
	public DrawableUnit getUnit() {
		return unit;
	}

	public void doClick() {
		if (unit instanceof DrawableNullUnit) {
			doClick(gameCoordinate);
		} else {
			unit.doClick(gameCoordinate);
		}
	}

	@Override
	public List<GameListener> getActionListeners() {
		return actionListeners;
	}

	@Override
	public DrawCoordinate getDrawCoordinate() {
		return drawCoordinate;
	}

	@Override
	public void draw(Graphics g) {
		if (isSelected) {
			g.drawImage(fieldtype.getImageHL(), drawCoordinate.getAX(), drawCoordinate.getAY(), drawCoordinate.getBX(),
					drawCoordinate.getBY(), 0, 0, GameCoordinate.STEP_WIDTH, GameCoordinate.STEP_HEIGHT, null);
		} else {
			g.drawImage(fieldtype.getImage(), drawCoordinate.getAX(), drawCoordinate.getAY(), drawCoordinate.getBX(),
					drawCoordinate.getBY(), 0, 0, GameCoordinate.STEP_WIDTH, GameCoordinate.STEP_HEIGHT, null);
		}
		unit.draw(g);
	}

	@Override
	public String toString() {
		return "DrawableField [drawCoordinate=" + drawCoordinate + ", actionListeners=" + actionListeners + ", unit="
				+ unit + ", fieldtype=" + fieldtype + ", gameCoordinate=" + gameCoordinate + ", isSelected="
				+ isSelected + "]";
	}
}
