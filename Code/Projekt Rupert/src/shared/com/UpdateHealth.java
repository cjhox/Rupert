package shared.com;

/**
 * UpdateHealth ist das Serialisierbare Objekt, welches nach der Kampfphase vom
 * Server an den Client gesendet wird. UpdateHealth enthält eine List, welche
 * alle vom Server durchgeführten Züge in der korrekten Reihenfolge enthält
 * damit der Client seine Units entsprechend konsistent Updaten kann.
 */
public class UpdateHealth extends Update<UpdateHealthData> {

	private static final long	serialVersionUID	= -2761411205881230248L;

	@Override
	public String toString() {
		return "UpdateHealth [datas=" + datas + "]";
	}
}
