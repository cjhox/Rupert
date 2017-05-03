package client.application;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.activemq.broker.BrokerService;

import server.Server;
import shared.Color;
import shared.Player;
import shared.PlayerPosition;
import shared.com.Connection;
import shared.com.Flag;
import shared.com.Publisher;
import shared.com.Subscriber;
import shared.com.UpdateHealth;
import shared.com.UpdatePlacement;
import shared.com.UpdatePosition;
import shared.game.Game;
import client.domain.WindowModel;
import client.ui.HomescreenView;
import client.ui.MainGameView;
import client.ui.ServerlobbyView;

/**
 * WindowController beinhaltet alle Controller bis zum Moment an dem das
 * eigentliche Spiel startet.
 */
public class WindowController {

	private HomescreenView		homescreen;
	private ServerlobbyView		serverlobby;
	private JFrame				frame;
	private WindowModel			windowModel;
	private GameViewController	gameViewControll;

	public WindowController(JFrame frame) {
		this.frame = frame;
		showHomescreenGui();
	}

	/**
	 * Zeigt das GUI der HomescreenView und initiiert das setzen der nötigen
	 * Listeners und Models der HomescreenView.
	 */
	private void showHomescreenGui() {
		homescreen = new HomescreenView(WindowModel.getIP());
		frame.getContentPane().add(homescreen);

		setHomescreenListeners();
	}

	/**
	 * Setzt die Listeners der HomescreenView.
	 */
	private void setHomescreenListeners() {
		homescreen.setIPDocumentListener(new IPDocumentListener(homescreen));
		homescreen.setCloseButtonListener(new CloseButtonListener());
		homescreen.setJoinButtonListener(new JoinButtonListener());
		homescreen.setCreateButtonListener(new CreateButtonListener());
	}

	private void showServerlobbyGui() {
		frame.getContentPane().remove(homescreen);
		serverlobby = new ServerlobbyView();
		frame.getContentPane().add(serverlobby);
		frame.setVisible(true);

		initializeServerlobby();
		setServerlobbyListeners();
	}

	/**
	 * Zur Zeit noch leer, kann eine bedeutung bekommen wenn die Serverlobby
	 * ausgebaut wird und somit Models initialisiert werden müssen.
	 */
	private void initializeServerlobby() {

	}

	private void setServerlobbyListeners() {
		serverlobby.setAbortButtonListener(new AbortButtonListener());
		serverlobby.setStartButtonListener(new StartButtonListener());
	}

	private void showMainGameGui(Serializable obj) {
		Game sourceGame = (Game) obj;
		if (gameViewControll == null) {
			frame.getContentPane().remove(serverlobby);
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
			MainGameView mainGameView = new MainGameView();
			frame.getContentPane().add(mainGameView);
			frame.setVisible(true);
			frame.setMinimumSize(new Dimension(1100, 700));
			gameViewControll = new GameViewController(mainGameView, windowModel, sourceGame);
		}
	}

	// Beginn der Listeners der ServerlobbyView

	private class AbortButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		}
	}

	private class StartButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				publish(Flag.StartGame);
			} catch (JMSException ex) {
				// TODO Error --> finish
				ex.printStackTrace();
			}
		}
	}

	// Ende der Listenres der ServerlobbyView

	// Beginn der Listeners der HomescreenView

	private class IPDocumentListener implements DocumentListener {

		HomescreenView	homescreen;

		public IPDocumentListener(HomescreenView homescreen) {
			this.homescreen = homescreen;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			checkText();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			checkText();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			checkText();
		}

		public void checkText() {
			if (!homescreen.isIPEmpty()) {
				homescreen.enableJoinButton(true);
				homescreen.enableCreateButton(true);
			} else {
				homescreen.enableJoinButton(false);
				homescreen.enableCreateButton(false);
			}
		}

	}

	private class CloseButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frame.dispose();
		}
	}

	/**
	 * Listener für den JoinButton. Verbindet sich wenn möglich auf ein
	 * bestehendes Spiel. Startet die ServerlobbyView.
	 */
	private class JoinButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String clientIP = "tcp://" + homescreen.getIPText() + ":61616";

			try {
				windowModel = new WindowModel(new Player("Player2", Color.RED, PlayerPosition.RIGHT),
						buildConnection(clientIP));
				publish(windowModel.getPlayer());

			} catch (JMSException ex) {
				ex.printStackTrace();
			}

			showServerlobbyGui();
		}
	}

	/**
	 * Listener des Create Buttons. Erstellt einen Server und startet die
	 * ServerlobbyView.
	 */
	private class CreateButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String hostIP = "tcp://" + homescreen.getIPText() + ":61616";

			BrokerService broker = new BrokerService();
			broker.setUseJmx(true);
			try {
				broker.addConnector(hostIP);
				broker.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			try {
				@SuppressWarnings("unused")
				Server server = new Server(hostIP);

				windowModel = new WindowModel(new Player("Player1", Color.BLUE, PlayerPosition.LEFT),
						buildConnection(hostIP));
				publish(windowModel.getPlayer());

			} catch (JMSException ex) {
				ex.printStackTrace();
			}

			frame.getContentPane().remove(homescreen);

			showServerlobbyGui();
		}
	}

	// Ende der Listeners der HomescreenView

	/**
	 * Erstellen der Verbindung zwischen Client und Server. Leitet eingehende
	 * Messages an die Funktion handleMessage() weiter.
	 * 
	 * @return
	 */
	private Publisher buildConnection(String IP) throws JMSException {

		Connection connection = new Connection(IP, "Client", "Server");
		Publisher toServer = connection.createPublisher(DeliveryMode.NON_PERSISTENT);
		connection.createSubscriber(new Subscriber() {

			@Override
			public void onMessage(ObjectMessage message) {
				handleMessage(message);
			}
		});

		connection.start();

		return toServer;
	}

	public void publish(Serializable obj) throws JMSException {
		windowModel.publish(obj);
	}

	/**
	 * Verarbeitung der vom Server eingetroffenen Messages. Ruft die
	 * entsprechenden Funktionen auf.
	 */
	protected void handleMessage(ObjectMessage message) {
		Serializable obj = null;
		try {
			obj = message.getObject();
		} catch (JMSException e) {
			e.printStackTrace();
			// TODO Error --> finish
			return;
		}

		if (obj == null) {
			return;
		}

		if (obj instanceof String) {

		} else if (obj instanceof Flag) {
			Flag flag = (Flag) obj;
			if (flag.equals(Flag.ReadyForStart)) {
				enableStart();
			} else if (flag.equals(Flag.StartTimer)) {
				startTimer();
			}
		} else if (obj instanceof Game) {
			showMainGameGui(obj);
		} else if (obj instanceof UpdatePlacement) {
			placeUnits((UpdatePlacement) obj);
		} else if (obj instanceof UpdatePosition) {
			moveUnits((UpdatePosition) obj);
		} else if (obj instanceof UpdateHealth) {
			hurtUnits((UpdateHealth) obj);
		} else if (obj instanceof Player) {
			showWinner((Player) obj);
		}
	}

	private void showWinner(Player player) {
		gameViewControll.showWinner(player);
	}

	private void enableStart() {
		serverlobby.enableStartButton();
	}

	private void placeUnits(UpdatePlacement updatePlacement) {
		gameViewControll.placeUnits(updatePlacement);
	}

	/**
	 * Leitet Messages vom Typ UpdatePosition an die entsprechende Methode im
	 * GameControll weiter.
	 * 
	 * @param obj
	 */
	private void moveUnits(UpdatePosition updatePosition) {
		gameViewControll.moveUnits(updatePosition);
	}

	/**
	 * Leitet Messages vom Typ UpdateHealth an die entsprechende Methode im
	 * GameControll weiter.
	 */
	private void hurtUnits(UpdateHealth updateHealth) {
		gameViewControll.hurtUnits(updateHealth);
	}

	/**
	 * Starten eines Timers. Wird allenfalls bei einem weiteren Ausbau des
	 * Spiels benötigt.
	 */
	private void startTimer() {
		// TODO Auto-generated method stub
	}
}
