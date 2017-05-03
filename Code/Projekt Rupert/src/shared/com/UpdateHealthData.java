package shared.com;

import java.io.Serializable;

import shared.GameCoordinate;
import shared.Action.FightAction;

public class UpdateHealthData implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5513753436319952516L;

	private GameCoordinate		target;
	private GameCoordinate		source;
	private FightAction			action;
	private int					value;

	public UpdateHealthData(GameCoordinate target, GameCoordinate source, FightAction action, int value) {
		super();
		this.target = target;
		this.source = source;
		this.action = action;
		this.value = value;
	}

	public GameCoordinate getTarget() {
		return target;
	}

	public GameCoordinate getSource() {
		return source;
	}

	public FightAction getAction() {
		return action;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "UpdateHealthData [target=" + target + ", source=" + source + ", action=" + action + ", value=" + value
				+ "]";
	}
	
	
}