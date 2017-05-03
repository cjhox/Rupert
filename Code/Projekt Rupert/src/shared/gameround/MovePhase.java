package shared.gameround;

import shared.Player;

public class MovePhase extends Phase<MoveDraw> {

	/**
	 * MovePhase erweitert die in der Superklasse Phase Definierten Funktionen
	 * addDraw, getDraw und removeDraw.
	 */
	private static final long	serialVersionUID	= -6224527017578787254L;

	public MovePhase(Player player) {
		super(player);
	}
}
