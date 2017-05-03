package server.domain;

import shared.GameCoordinate;
import shared.Player;
import shared.Unittype;
import shared.Action.PlaceAction;
import shared.gameround.PlaceDraw;
import shared.gameround.PlacePhase;

public class DemoPlacePhaseFactory {

	/**
	 * Platziert die Units auf dem Feld. Bei einer erweiterung des Games kann
	 * dies Innerhalb der PlacePhase durch die Spieler manuell getätigt werden.
	 */
	public static PlacePhase getDemoPlacePhase0(Player player) {
		int y = 5;
		int x, dir;

		switch (player.getPosition()) {
		case LEFT:
			x = 4;
			dir = 1;
			return formation0(player, y, x, dir);
		case RIGHT:
			x = 15;
			dir = -1;
			return formation0(player, y, x, dir);
		default:
			break;
		}

		return new PlacePhase(player);
	}

	private static PlacePhase formation0(Player player, int y, int x, int dir) {
		PlacePhase phase = new PlacePhase(player);

		phase.addDraw(new PlaceDraw(new GameCoordinate(x, y), PlaceAction.PLACE, Unittype.ARCHER, player));
		phase.addDraw(new PlaceDraw(new GameCoordinate(x, y + 1), PlaceAction.PLACE, Unittype.ARCHER, player));
		phase.addDraw(new PlaceDraw(new GameCoordinate(x, y + 2), PlaceAction.PLACE, Unittype.ARCHER, player));
		phase.addDraw(new PlaceDraw(new GameCoordinate(x, y + 3), PlaceAction.PLACE, Unittype.ARCHER, player));
		x += dir;
		phase.addDraw(new PlaceDraw(new GameCoordinate(x, y + 1), PlaceAction.PLACE, Unittype.KNIGHT, player));
		phase.addDraw(new PlaceDraw(new GameCoordinate(x, y + 2), PlaceAction.PLACE, Unittype.KNIGHT, player));
		x += dir;
		phase.addDraw(new PlaceDraw(new GameCoordinate(x, y), PlaceAction.PLACE, Unittype.SWORDSMAN, player));
		phase.addDraw(new PlaceDraw(new GameCoordinate(x + dir, y + 1), PlaceAction.PLACE, Unittype.SWORDSMAN, player));
		phase.addDraw(new PlaceDraw(new GameCoordinate(x + dir, y + 2), PlaceAction.PLACE, Unittype.SWORDSMAN, player));
		phase.addDraw(new PlaceDraw(new GameCoordinate(x, y + 3), PlaceAction.PLACE, Unittype.SWORDSMAN, player));

		return phase;
	}
}
