package shared.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Die PlacePhase wurde ursprünglich geplant als erste Phase, bei welcher die Einheiten auf dem Spielfeld platziert werden. Wurde aufgrund von Zeitmangel nicht im Spiel eingebaut.
 * Die PlacePhase lassen wir für allfällige zukünfgite erweiterungen bestehen.
 */

public class PlacePhase extends Phase {

	private static final long serialVersionUID = 2516782505509723162L;
	
	private List<PlaceDraw> draws;

	public PlacePhase(Player player) {
		super(player);
		this.draws = new ArrayList<PlaceDraw>();
	}

	public boolean addDraw(Draw draw) {
		return draws.add((PlaceDraw) draw);
	}

	public boolean removeDraw(Draw draw) {
		return draws.remove(draw);
	}

	@Override
	public List<PlaceDraw> getDraws() {
		return draws;
	}
}
