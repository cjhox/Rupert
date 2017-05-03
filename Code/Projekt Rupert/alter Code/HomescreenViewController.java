//package client.application;
//
//import java.awt.Color;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.Serializable;
//
//import javax.jms.DeliveryMode;
//import javax.jms.JMSException;
//import javax.jms.ObjectMessage;
//import javax.swing.JFrame;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//
//import org.apache.activemq.broker.BrokerService;
//
//import server.Server;
//import shared.com.Connection;
//import shared.com.Publisher;
//import shared.com.Subscriber;
//import shared.domain.Flag;
//import shared.domain.UpdateHealth;
//import shared.domain.UpdatePosition;
//import shared.game.GameCoordinate;
//import shared.game.GameInterface;
//import shared.game.PlaceDraw;
//import shared.game.PlacePhase;
//import shared.game.Player;
//import shared.game.StartRound;
//import shared.game.UnitInterface;
//import shared.resource.Unittype;
//import client.domain.DrawableGame;
//import client.domain.DrawableUnit;
//import client.domain.GameListener;
//import client.domain.GameModel;
//import client.domain.WindowModel;
//import client.ui.HomescreenView;
//import client.ui.MainGameView;
//
//public class HomescreenViewController {
//
//	private HomescreenView homescreen;
//	private JFrame frame;
//	private Connection connection;
//	private Publisher toServer;
//	private WindowModel windowModel;
//	private GameControll gameControll;
//	private MainGameView mainGameView;
//
//	public HomescreenViewController(JFrame frame) {
//		this.frame = frame;
//	}
//
//	public void showGui() {
//		homescreen = new HomescreenView();
//		frame.getContentPane().add(homescreen);
//
//		initializeHomescreen();
//		setListeners();
//	}
//
//	// Models initialisieren wenn nötig
//	public void initializeHomescreen() {
//
//	}
//
//	// Aufruf der Methoden zum setzen der Listeners
//	public void setListeners() {
//		homescreen.setClientIPDocumentListener(new ClientIPDocumentListener(homescreen));
//		homescreen.setHostIPDocumentListener(new HostIPDocumentListener(homescreen));
//		homescreen.setCloseButtonListener(new CloseButtonListener());
//		homescreen.setJoinButtonListener(new JoinButtonListener());
//		homescreen.setCreateButtonListener(new CreateButtonListener());
//	}
//
//	class ClientIPDocumentListener implements DocumentListener {
//
//		HomescreenView homescreen;
//
//		public ClientIPDocumentListener(HomescreenView homescreen) {
//			this.homescreen = homescreen;
//		}
//
//		@Override
//		public void changedUpdate(DocumentEvent e) {
//			checkText();
//		}
//
//		@Override
//		public void insertUpdate(DocumentEvent e) {
//			checkText();
//		}
//
//		@Override
//		public void removeUpdate(DocumentEvent e) {
//			checkText();
//		}
//
//		public void checkText() {
//			if (!homescreen.txtHomescreenClientIP.getText().isEmpty()) {
//				homescreen.btnHomescreenJoin.setEnabled(true);
//			} else {
//				homescreen.btnHomescreenJoin.setEnabled(false);
//			}
//		}
//
//	}
//
//	class HostIPDocumentListener implements DocumentListener {
//
//		HomescreenView homescreen;
//
//		public HostIPDocumentListener(HomescreenView homescreen) {
//			this.homescreen = homescreen;
//		}
//
//		@Override
//		public void changedUpdate(DocumentEvent e) {
//			checkText();
//		}
//
//		@Override
//		public void insertUpdate(DocumentEvent e) {
//			checkText();
//		}
//
//		@Override
//		public void removeUpdate(DocumentEvent e) {
//			checkText();
//		}
//
//		public void checkText() {
//			if (!homescreen.txtHomescreenHostIP.getText().isEmpty()) {
//				homescreen.btnHomescreenCreate.setEnabled(true);
//			} else {
//				homescreen.btnHomescreenCreate.setEnabled(false);
//			}
//		}
//	}
//
//	class CloseButtonListener implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			frame.dispose();
//		}
//	}
//
//	/**
//	 * Verbindet sich auf ein bestehendes Spiel. Startet die ServerlobbyView.
//	 */
//	class JoinButtonListener implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//
//			String clientIP = "tcp://"
//					+ homescreen.txtHomescreenClientIP.getText() + ":61616";
//
//			try {
//				buildConnection(clientIP);
//				toServer.publish(new Player("Player2", new Color(255, 1, 1)));
//			} catch (JMSException ex) {
//				ex.printStackTrace();
//			}
//
//			frame.getContentPane().remove(homescreen);
//			ServerlobbyViewController controller = new ServerlobbyViewController(
//					frame, toServer);
//			controller.showGui();
//		}
//	}
//
//	/**
//	 * Erstellt einen Server, Client wird zum "Host". Startet die
//	 * ServerlobbyView.
//	 */
//	class CreateButtonListener implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//
//			String hostIP = "tcp://" + homescreen.txtHomescreenHostIP.getText()
//					+ ":61616";
//
//			BrokerService broker = new BrokerService();
//			broker.setUseJmx(true);
//			try {
//				broker.addConnector(hostIP);
//				broker.start();
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//
//			try {
//				@SuppressWarnings("unused")
//				Server server = new Server(hostIP);
//
//				buildConnection(hostIP);
//
//				toServer.publish(new Player("Player1", new Color(0, 0, 255)));
//
//			} catch (JMSException ex) {
//				ex.printStackTrace();
//			}
//
//			frame.getContentPane().remove(homescreen);
//			ServerlobbyViewController controller = new ServerlobbyViewController(
//					frame, toServer);
//			controller.showGui();
//		}
//	}
//
//	private void buildConnection(String IP) throws JMSException {
//
//		connection = new Connection(IP, "Client", "Server");
//		toServer = connection.createPublisher(DeliveryMode.NON_PERSISTENT);
//		connection.createSubscriber(new Subscriber() {
//
//			@Override
//			public void onMessage(ObjectMessage message) {
//				handleMessage(message);
//			}
//		});
//
//		connection.start();
//	}
//
//	protected void handleMessage(ObjectMessage message) {
//		try {
//			Serializable obj = message.getObject();
//
//			if (obj instanceof String) {
//
//			} else if (obj instanceof Flag) {
//				Flag flag = (Flag) obj;
//				if (flag.equals(Flag.ReadyForStart)) {
//					enableStart();
//				} else if (flag.equals(Flag.StartTimer)) {
//					startTimer();
//				}
//			} else if (obj instanceof GameInterface) {
//				GameInterface sourceGame = (GameInterface) obj;
//				showMainGameView();
//				gameControll = new GameControll(mainGameView, toServer, sourceGame);
//				startPlacePhase();
//			} else if (obj instanceof StartRound) {
//				StartRound startRound = (StartRound) obj;
//				placeUnits(startRound);
//			} else if (obj instanceof UpdatePosition) {
//				UpdatePosition updatePosition = (UpdatePosition) obj;
//				moveUnits(updatePosition);
//			} else if (obj instanceof UpdateHealth) {
//				UpdateHealth updateHealth = (UpdateHealth) obj;
//				fightUnits(updateHealth);
//			}
//
//		} catch (JMSException e) {
//			e.printStackTrace();
//			// TODO Error --> finish
//		}
//	}
//
//	private void showMainGameView() {
//		mainGameView = new MainGameView();
//		frame.getContentPane().add(mainGameView);
//		frame.setVisible(true);
//	}
//
//	private void enableStart() {
//		// TODO Auto-generated method stub
//
//	}
//
//	private void startPlacePhase() {
//		// TODO Auto-generated method stub
//
//	}
//
//	private void placeUnits(StartRound startRound) {
//		
//	}
//
//	private void moveUnits(UpdatePosition updatePosition) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	private void fightUnits(UpdateHealth updateHealth) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	private void startTimer() {
//		// TODO Auto-generated method stub
//
//	}
//}
