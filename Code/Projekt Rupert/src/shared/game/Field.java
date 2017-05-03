package shared.game;

import java.io.Serializable;

import shared.GameCoordinate;
import shared.Unittype;
import shared.gameround.PlaceDraw;

/**
 * Ein vom Server für die Berechnungen verwendetes Field. Analog zu
 * DrawableField des Clients.
 */
public class Field implements Serializable {

	private static final long	serialVersionUID	= 826490010909710092L;

	protected Fieldtype			fieldtype;
	protected GameCoordinate	gameCoordinate;
	protected boolean			isSelected			= false;

	private Unit				unit				= new NullUnit();

	public Field(Fieldtype fieldtype, GameCoordinate gameCoordinate) {
		this.fieldtype = fieldtype;
		this.gameCoordinate = gameCoordinate;
	}

	public boolean placeUnit(PlaceDraw placeDraw) {
		if (unit instanceof NullUnit) {
			unit = new Unit(placeDraw);
			return true;
		}
		return false;
	}

	public boolean placeUnit(Unit unit) {
		if (this.unit instanceof NullUnit) {
			this.unit = unit;
			return true;
		}
		return false;
	}

	public Unit removeUnit() {
		Unit unit = this.unit;
		this.unit = new NullUnit();
		return unit;
	}

	public void attack(int damage) {
		if (getUnit().hurt(damage)) {
			removeUnit();
		}
	}

	public void heal(int value) {
		getUnit().heal(value);
	}

	public void setDeffending(boolean deffending) {
		getUnit().setDeffending(deffending);
	}

	public boolean isDead() {
		return getUnit().isDead();
	}

	public Unit getUnit() {
		return unit;
	}

	public Unittype getUnittype() {
		return getUnit().getUnittype();
	}

	public void setSelection(boolean select) {
		isSelected = select;
	}

	public boolean getSelection() {
		return isSelected;
	}

	public Fieldtype getFieldtype() {
		return fieldtype;
	}

	public GameCoordinate getGameCoordinate() {
		return gameCoordinate;
	}

	@Override
	public String toString() {
		return "Field [fieldtype=" + fieldtype + ", gameCoordinate=" + gameCoordinate + ", isSelected=" + isSelected
				+ ", unit=" + unit + "]";
	}
}
