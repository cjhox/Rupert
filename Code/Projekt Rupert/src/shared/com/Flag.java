package shared.com;

import java.io.Serializable;
/**
 * Der Enum stellt "leere Objekte" für die Kommunikation zwischen Client und Server zur Verfügung.
 */
public enum Flag implements Serializable {
	ReadyForStart, StartGame, StartTimer; 
}
