//package client.application;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.jms.JMSException;
//import javax.swing.JFrame;
//
//import server.domain.Game;
//import server.domain.GameMap;
//import shared.com.Publisher;
//import shared.domain.Flag;
//import client.domain.DrawableGame;
//import client.ui.ServerlobbyView;
//
//public class ServerlobbyViewController {
//	
//	private ServerlobbyView serverlobby;
//	private JFrame frame;
//	private Publisher toServer;
//	
//	public ServerlobbyViewController(JFrame frame, Publisher toServer) {
//		this.frame = frame;
//		this.toServer = toServer;
//	}
//	
//	public void showGui(){
//		serverlobby = new ServerlobbyView();
//		frame.getContentPane().add(serverlobby);
//		frame.setVisible(true);
//		
//		initializeServerlobby();
//		setListeners();
//	}
//	
//	public void initializeServerlobby(){
//		
//	}
//	
//	public void setListeners(){
//		serverlobby.setAbortButtonListener(new AbortButtonListener());
//		
//		//TODO löschen sobald Methode durch Server aufgerufen wird.
//		setStartButtonListener(new DrawableGame(new Game(null, null, new GameMap()), null));
//	}
//	
//	//TODO Aufruf dieser Methode im Subscriber zum Setzen des Listeners auf den Startbutton. Zudem Enabeln des Buttons nach übergabe des DrawableGame.
//	public void setStartButtonListener(DrawableGame game){
//		serverlobby.setStartButtonListener(new StartButtonListener(game));
//	}
//	
//	class AbortButtonListener implements ActionListener{
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			frame.dispose();
//		}
//	}
//	
//	class StartButtonListener implements ActionListener{
//
//		private DrawableGame game;
//
//		public StartButtonListener(DrawableGame game) {
//			this.game = game;
//		}
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//
//			try {
//				toServer.publish(Flag.StartGame);
//			} catch (JMSException ex) {
//				// TODO Error --> finish
//				ex.printStackTrace();
//			}
//			
//			frame.getContentPane().remove(serverlobby);
//			MainGameViewController controller = new MainGameViewController(frame, toServer, game);
//			controller.showGui();
//		}
//	}
//}
