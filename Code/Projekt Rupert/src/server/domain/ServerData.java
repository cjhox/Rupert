package server.domain;

import java.io.Serializable;

import javax.jms.JMSException;

import shared.Color;
import shared.Player;
import shared.PlayerPosition;
import shared.com.Flag;
import shared.com.Publisher;
import shared.com.UpdateHealth;
import shared.com.UpdateHealthData;
import shared.com.UpdatePlacement;
import shared.game.Game;
import shared.game.GameReadOnly;
import shared.gameround.FightPhase;
import shared.gameround.GameRound;
import shared.gameround.GameState.PlayState;
import shared.gameround.MoveDraw;
import shared.gameround.MovePhase;
import shared.gameround.Phase;
import shared.gameround.PlaceDraw;
import shared.gameround.PlacePhase;

public class ServerData {

	private Game							game;
	private GameRound<? extends Phase<?>>	gameRound;
	private PlayState						activPhase	= PlayState.Place;
	private boolean							demoGame	= false;

	private Player							player1;
	private Player							player2;
	private Publisher						toClient;

	public ServerData(Publisher toClient) {
		this.toClient = toClient;
	}

	public GameReadOnly initializeGame() {
		if (player2 == null) {
			player2 = new Player("Demo", Color.WHITE, PlayerPosition.RIGHT);
			demoGame = true;
		}

		game = new Game(player1, player2);
		game.initializeMap();
		return game;
	}

	public void addPlayer(Player player) {
		if (player1 == null) {
			player1 = player;
		} else if (!player.equals(player1)) {
			player2 = player;
			publish(Flag.ReadyForStart);
		}
	}

	public void executePlaceDraws(UpdatePlacement update) {
		for (PlaceDraw placeDraw : update.getUpdate()) {
			game.place(placeDraw);
		}
	}

	public boolean move(MoveDraw moveDraw) {
		return game.move(moveDraw);
	}

	public void executeFightDraws(UpdateHealth update) {
		for (UpdateHealthData updateData : update.getUpdate()) {
			game.fight(updateData);
		}
		game.resetDeffending();
	}

	public void changeToPlacePhase() {
		activPhase = PlayState.Place;
		gameRound = new GameRound<PlacePhase>();
	}

	public void changeToMovePhase() {
		activPhase = PlayState.Move;
		gameRound = new GameRound<MovePhase>();
	}

	public void changeToFightPhase() {
		activPhase = PlayState.Fight;
		gameRound = new GameRound<FightPhase>();
	}

	public void publishGame() {
		publish(game);
	}

	public void publish(Serializable obj) {
		try {
			toClient.publish(obj);
		} catch (JMSException e) {
			e.printStackTrace();
			// TODO Error --> finish
		}
	}

	public PlacePhase getDemoPlacePhase(int variante, Player player) {
		switch (variante) {
		case 0:
			DemoPlacePhaseFactory.getDemoPlacePhase0(player);
			break;

		default:
			break;
		}
		return null;
	}

	/**
	 * Methode zum Starten eines Timers. Aus Zeitgründen nicht im Spiel
	 * implementiert, kann jedoch bei einer Erweiterung integriert werden.
	 */
	public void startTimer() {
		// TODO Auto-generated method stub

	}

	public GameRound<? extends Phase<?>> getGameRound() {
		return gameRound;
	}

	public PlayState getActivPhase() {
		return activPhase;
	}

	public Player getDemoPlayer() {
		if (demoGame) {
			return player2;
		} else {
			return null;
		}
	}

	public boolean isDemoGame() {
		return demoGame;
	}
}