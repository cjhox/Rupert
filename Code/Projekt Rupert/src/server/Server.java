package server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.apache.activemq.util.Callback;

import server.domain.Calculator;
import server.domain.DemoPlacePhaseFactory;
import server.domain.Handler;
import server.domain.ServerData;
import shared.Player;
import shared.com.Connection;
import shared.com.Flag;
import shared.com.Subscriber;
import shared.com.UpdateHealth;
import shared.com.UpdatePlacement;
import shared.com.UpdatePosition;
import shared.gameround.FightPhase;
import shared.gameround.GameRound;
import shared.gameround.GameState;
import shared.gameround.MoveDraw;
import shared.gameround.MovePhase;
import shared.gameround.Phase;
import shared.gameround.PlacePhase;

public class Server {

	private ServerData							data;
	private Calculator							calculator;
	private Handler<PlacePhase>					placeHandler;
	private Handler<MovePhase>					moveHandler;
	private Handler<FightPhase>					fightHandler;
	private List<GameRound<? extends Phase<?>>>	history = new LinkedList<>();

	private GameState							state			= GameState.StartUp;

	public Server(String IP) throws JMSException {
		Connection connection = new Connection(IP, "Server", "Client");
		data = new ServerData(connection.createPublisher(DeliveryMode.NON_PERSISTENT));
		
		connection.createSubscriber(new Subscriber() {

			@Override
			public void onMessage(ObjectMessage message) {
				handleMessage(message);
			}
		});

		connection.start();
		
		placeHandler	= new Handler<>(data);
		moveHandler		= new Handler<>(data);
		fightHandler	= new Handler<>(data);
	}

	protected void handleMessage(ObjectMessage message) {
		try {
			Serializable obj = message.getObject();

			if (obj instanceof String) {
				// String text = (String) obj;
				// if (text == "exit") {
				// connection.close();
				// }
			} else {
				switch (state) {
				case StartUp:
					handleStartUp(obj);
					break;
				case Play:
					handlePhase(obj);
					break;
				case EndGame:
					// TODO finish
					break;
				default:
					// TODO Error --> finish
					break;
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
			// TODO Error --> finish
		}
	}

	private void handleStartUp(Serializable obj) {
		if (obj instanceof Player) {
			data.addPlayer((Player) obj);
		} else if (obj instanceof Flag) {
			Flag flag = (Flag) obj;
			if (flag.equals(Flag.StartGame)) {
				startGame();
			}
		}
	}

	private void handlePhase(Serializable obj) {
		switch (data.getActivPhase()) {
		case Place:
			if (obj instanceof PlacePhase) {
				placeHandler.handle(obj, DemoPlacePhaseFactory.getDemoPlacePhase0(data.getDemoPlayer()), new EndOfPlacePhaseCallback());
			}
			break;
		case Move:
			if (obj instanceof MovePhase) {
				moveHandler.handle(obj, new MovePhase(data.getDemoPlayer()), new EndOfMovePhaseCallback());
			}
			break;
		case Fight:
			if (obj instanceof FightPhase) {
				fightHandler.handle(obj, new FightPhase(data.getDemoPlayer()), new EndOfFightPhaseCallback());
			}
		default:
			break;
		}
	}

	/**
	 * Erstellt und startet ein neues Spiel.
	 */
	private void startGame() {
		calculator = new Calculator(data.initializeGame());

		Handler.first = null;
		state = GameState.Play;
		data.changeToPlacePhase();
		data.publishGame();
	}

	private void checkIfGameIsFinished() {
		Player winner = calculator.isGameFinished();
		if (winner != null) {
			state = GameState.EndGame;
			data.publish(winner);
		}
	}

	private final class EndOfPlacePhaseCallback implements Callback {

		@Override
		public void execute() {
			history.add(data.getGameRound());

			@SuppressWarnings("unchecked")
			UpdatePlacement update = calculator.sortPlaceDraws((GameRound<PlacePhase>) data.getGameRound());
			data.executePlaceDraws(update);

			Handler.first = null;
			data.changeToMovePhase();
			data.publish(update);
		}
	}

	private final class EndOfFightPhaseCallback implements Callback {

		/**
		 * Erstellt eine neue Runde. Übermittelt die Ergebnisse der
		 * FightPhase der letzten Runde an den Client und startet
		 * eine neue MovePhase.
		 */
		@Override
		public void execute() {
			history.add(data.getGameRound());

			@SuppressWarnings("unchecked")
			UpdateHealth update = calculator.calcFight((GameRound<FightPhase>) data.getGameRound());
			data.executeFightDraws(update);

			Handler.first = null;
			data.changeToMovePhase();
			data.publish(update);

			checkIfGameIsFinished();
		}
	}

	private final class EndOfMovePhaseCallback implements Callback {

		/**
		 * Wechselt die Phase. Da die erste Phase einer Runde immer
		 * eine MovePhase ist, wird hier nur der wechsel zur
		 * FightPhase getätigt sowie die Ergebnisse der MovePhase an
		 * den Client übermittelt.
		 */
		@Override
		public void execute() {
			history.add(data.getGameRound());

			@SuppressWarnings("unchecked")
			List<MoveDraw> sortedMoveDraws = calculator.sortMoveDraws((GameRound<MovePhase>) data.getGameRound());

			UpdatePosition update = new UpdatePosition();
			for (MoveDraw moveDraw : sortedMoveDraws) {
				MoveDraw move = calculator.calcMoveDraw(moveDraw);
				update.add(move);
				data.move(move);
			}

			Handler.first = null;
			data.changeToFightPhase();
			data.publish(update);
		}
	}
}
