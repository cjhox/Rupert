package shared.gameround;

import java.io.Serializable;

import shared.Action;
import shared.GameCoordinate;

public class Draw implements Serializable {

	/**
	 * Die abstrakte Klasse Draw ist die Superklasse von FightDraw und MoveDraw.
	 * Stellt die n�tigen Funktionen und Attribute f�r die abgeleiteten
	 * Draw-Klassen zur Verf�gung. Wird als Serialisierbares Objekt als Teil der
	 * Klasse UpdateHealth bzw. UpdatePosition �bermittelt.
	 */
	private static final long	serialVersionUID	= -783447094049279682L;

	private int					number;
	private GameCoordinate		source;
	private GameCoordinate		target;
	protected Action			action;

	public Draw(int number, GameCoordinate source, GameCoordinate target, Action action) {
		super();
		this.number = number;
		this.source = source;
		this.target = target;
		this.action = action;
	}

	public int getNumber() {
		return number;
	}

	public GameCoordinate getSource() {
		return source;
	}

	public GameCoordinate getTarget() {
		return target;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public String toString() {
		if (target == null) {
			return number + ": " + action + " " + source;
		} else if (source == null) {
			return number + ": " + action + " " + target;
		} else {
			return number + ": " + action + " " + source + " --> " + target;
		}
	}
}
