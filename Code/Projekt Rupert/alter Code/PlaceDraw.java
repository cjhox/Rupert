package shared.game;

import shared.resource.Action;

/**
 * Der PlaceDraw wurde urspr�nglich geplant als der Zug in der PlacePhase, bei welchem die Einheiten zu beginn des Spiels auf dem Feld platziert werden.
 * Da diese Phase aus Zeitgr�nden nicht im urspr�nglichen Sinne implementiert wird, wird der PlaceDraw nicht verwendet. Wir lassen Ihn jedoch f�r allf�llige zuk�nfgite Erweiterungen bestehen.
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