package shared.game;

import java.io.Serializable;

/**
 * Die Round wurde urspr�nglich als abstrakte Superklasse der Normalen GameRound und der spetziellen StartRound benutzt.
 * Da die StartRound f�r dieses Projekt nicht wie geplant implementiert wird, entf�llt der Sinn der Klasse Round. Sie kann gel�scht werden, sobald die StartRound �berarbeitet ist.
 */
public abstract class Round implements Serializable{

	private static final long serialVersionUID = -1187270024734685781L;
	
	public int number;

	public Round(int number) {
		super();
		this.number = number;
	}	
}
