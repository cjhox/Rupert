package shared.gameround;

import shared.Player;

/**
 * FightPhase erweitert die in der Superklasse Phase Definierten Funktionen
 * addDraw, getDraw und removeDraw.
 */
public class FightPhase extends Phase<FightDraw> {

	private static final long	serialVersionUID	= -8599307812650222263L;

	public FightPhase(Player player) {
		super(player);
	}
}
