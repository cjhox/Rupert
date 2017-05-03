package shared.com;

import shared.gameround.MoveDraw;

/**
 * UpdatePosition ist das serialisierbare Objekt, welches nach der
 * Bewegungsphase vom Server an den Client gesendet wird. UpdatePosition enthält
 * eine ArrayList, welche alle vom Server durchgeführten Züge in der richtigen
 * Reihenfolge enthält, damit der Client die Position der Units konsistent
 * Updaten kann.
 */
public class UpdatePosition extends Update<MoveDraw> {

	private static final long	serialVersionUID	= 6256834580802245173L;

	@Override
	public String toString() {
		return "UpdatePosition [datas=" + datas + "]";
	}
}
