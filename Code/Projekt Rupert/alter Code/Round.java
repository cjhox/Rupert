package shared.game;

import java.io.Serializable;

/**
 * Die Round wurde ursprünglich als abstrakte Superklasse der Normalen GameRound und der spetziellen StartRound benutzt.
 * Da die StartRound für dieses Projekt nicht wie geplant implementiert wird, entfällt der Sinn der Klasse Round. Sie kann gelöscht werden, sobald die StartRound überarbeitet ist.
 */
public abstract class Round implements Serializable{

	private static final long serialVersionUID = -1187270024734685781L;
	
	public int number;

	public Round(int number) {
		super();
		this.number = number;
	}	
}
