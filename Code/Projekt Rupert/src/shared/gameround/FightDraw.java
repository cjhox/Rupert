package shared.gameround;

import shared.Action;
import shared.GameCoordinate;
import shared.Action.FightAction;

public class FightDraw extends Draw {

	private static final long	serialVersionUID	= 9218134172465585966L;
	private static int			nextId				= 0;

	public FightDraw(GameCoordinate source, GameCoordinate target, Action.FightAction action) {
		super(nextId++, source, target, action);
	}

	@Override
	public FightAction getAction() {
		return (FightAction) action;
	}
}
