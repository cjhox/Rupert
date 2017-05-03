package shared.com;

import java.io.Serializable;
/**
 * Der Enum stellt "leere Objekte" f�r die Kommunikation zwischen Client und Server zur Verf�gung.
 */
public enum Flag implements Serializable {
	ReadyForStart, StartGame, StartTimer; 
}
