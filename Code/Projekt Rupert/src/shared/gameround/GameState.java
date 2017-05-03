package shared.gameround;

import java.io.Serializable;
/**
 * Der Server setzt den GameState je nach dem in welchem Zustand das Spiel ist. Je nach dem welcher GameState gesetzt ist wird der Server unterschiedlich auf eingehende Messages reagieren.
 */
public enum GameState implements Serializable {
	StartUp, Place, Play, EndGame;
/**
 * Der PlayState wird ist ein SubState des GameState Play (Da nur während Play aktiv ist effektiv gespielt wird).
 * Die beiden States Move und Fight stehen für die beiden Phasen des Spiels und signalisieren Client und Server was sie als nächstes für Eingaben erwarten.
 */
	public enum PlayState implements Serializable {
		Place, Move, Fight;
	}
}