package shared.com;

import shared.gameround.MoveDraw;

/**
 * UpdatePosition ist das serialisierbare Objekt, welches nach der
 * Bewegungsphase vom Server an den Client gesendet wird. UpdatePosition enth�lt
 * eine ArrayList, welche alle vom Server durchgef�hrten Z�ge in der richtigen
 * Reihenfolge enth�lt, damit der Client die Position der Units konsistent
 * Updaten kann.
 */
public class UpdatePosition extends Update<MoveDraw> {

	private static final long	serialVersionUID	= 6256834580802245173L;

	@Override
	public String toString() {
		return "UpdatePosition [datas=" + datas + "]";
	}
}
