package server.domain;

import java.io.Serializable;

import org.apache.activemq.util.Callback;

import shared.Player;
import shared.gameround.Draw;
import shared.gameround.GameRound;
import shared.gameround.Phase;

public class Handler<T extends Phase<? extends Draw>> {

	public static Player	first;
	private ServerData		data;

	public Handler(ServerData data) {
		super();
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	public void handle(Serializable obj, T def, Callback callback) {
		try {
			T phase = (T) obj;
			if (data.isDemoGame()) {
				((GameRound<T>) data.getGameRound()).setPhaseFirstPlayer(phase);
				((GameRound<T>) data.getGameRound()).setPhaseSecoundPlayer(def);
				callback.execute();
			} else if (first == null) {
				((GameRound<T>) data.getGameRound()).setPhaseFirstPlayer(phase);
				first = phase.getPlayer();
				data.startTimer();
			} else if (!first.equals(phase.getPlayer())) {
				((GameRound<T>) data.getGameRound()).setPhaseSecoundPlayer(phase);
				callback.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
