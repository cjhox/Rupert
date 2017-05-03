package shared.game;

public class StartRound extends Round {

	/**
	 * Enthält die PlacePhasen der beiden Spieler. Wurde aus Zeitgründen nicht wie geplant implementiert, bleibt für Zukünftige erweiterungen bestehen.
	 */
	private static final long serialVersionUID = 1399209437474942215L;
	
	private PlacePhase[] placePhases;

	public StartRound(int number) {
		super(number);
		placePhases = new PlacePhase[2];
	}

	public void setFirst(PlacePhase placePhase) {
		placePhases[0] = placePhase;
	}

	public void setSecound(PlacePhase placePhase) {
		placePhases[1] = placePhase;
	}

	public PlacePhase[] getPhases() {
		return placePhases;
	}
}
