//package client.application;
//
//import javax.swing.JFrame;
//
//import shared.com.Publisher;
//import shared.game.GameInterface;
//import client.ui.MainGameView;
//
//public class MainGameViewController {
//	
//	private MainGameView gameView;
//	private JFrame frame;
//	private Publisher toServer;
//
//	public MainGameViewController(JFrame frame, Publisher toServer, GameInterface sourceGame){
//		this.frame = frame;
//		this.toServer = toServer;
//	}
//	
//	public void showGui(){
//		gameView = new MainGameView();
//		frame.getContentPane().add(gameView);
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
//
//	}
//}
