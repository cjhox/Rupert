package shared.game;

import shared.resource.Action;

/**
 * Der PlaceDraw wurde ursprünglich geplant als der Zug in der PlacePhase, bei welchem die Einheiten zu beginn des Spiels auf dem Feld platziert werden.
 * Da diese Phase aus Zeitgründen nicht im ursprünglichen Sinne implementiert wird, wird der PlaceDraw nicht verwendet. Wir lassen Ihn jedoch für allfällige zukünfgite Erweiterungen bestehen.
 */

public class PlaceDraw extends Draw {

	private static final long serialVersionUID = 1641631086813668463L;
	
	private UnitInterface unit;

	public PlaceDraw(int number, UnitInterface unit, GameCoordinate target, Action.PlaceAction action) {
		super(number, unit.getGameCoordinate(), target, action);
		this.unit = unit;
	}

	public UnitInterface getUnit() {
		return unit;
	}

}