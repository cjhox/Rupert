package shared.gameround;

import shared.Action;
import shared.GameCoordinate;
import shared.Action.MoveAction;

public class MoveDraw extends Draw {

	private static final long	serialVersionUID	= -2680988134012929560L;
	private static int			nextId				= 0;

	public MoveDraw(GameCoordinate source, GameCoordinate target, Action.MoveAction action) {
		super(nextId++, source, target, action);
	}

	public MoveDraw(MoveDraw move, GameCoordinate target) {
		super(move.getNumber(), move.getSource(), target, move.getAction());
	}

	@Override
	public MoveAction getAction() {
		return (MoveAction) action;
	}
}
