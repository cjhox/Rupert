package shared.gameround;

import java.io.Serializable;

public class GameRound<T extends Phase<?>> implements Serializable {

	/**
	 * Eine GameRound beinhaltet eine Bewegungsphase und eine Kampfphase. Die
	 * Game Round hat somit zwei Arrays, eins pro Phase, in denen wiederum je
	 * ein Array pro Spieler gespeichert ist.
	 */
	private static final long	serialVersionUID	= -1893005830673625311L;
	private static int			numberCounter		= 0;

	private int					number;
	private T					phaseFirstPlayer;
	private T					phaseSecoundPlayer;

	public GameRound() {
		super();
		number = numberCounter++;
	}

	public void setPhaseFirstPlayer(T phaseFirstPlayer) {
		this.phaseFirstPlayer = phaseFirstPlayer;
	}

	public void setPhaseSecoundPlayer(T phaseSecoundPlayer) {
		this.phaseSecoundPlayer = phaseSecoundPlayer;
	}

	public T getPhaseFirstPlayer() {
		return phaseFirstPlayer;
	}

	public T getPhaseSecoundPlayer() {
		return phaseSecoundPlayer;
	}
	
	public int getNumber() {
		return number;
	}
}
