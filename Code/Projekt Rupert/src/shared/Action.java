package shared;

import java.io.Serializable;

/**
 * Das Interface Action enthält drei Enums, welche in den drei Phasen verwendet
 * werden, um den aktuell zu verwendenden Zug zu finden.
 */
public interface Action extends Serializable {

	public enum FightAction implements Action {
		ATTACK, DEFENCE, HEAL;
	}

	public enum MoveAction implements Action {
		MOVE;
	}

	public enum PlaceAction implements Action {
		PLACE;
	}
}
