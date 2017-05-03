package client;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import shared.Unittype;
import client.application.WindowController;

public class Client {

	/**
	 * Erstellt das GUI des Clients.
	 */
	private static void showWindow() {
		JFrame frame = new JFrame("Rupert");
		frame.setBounds(100, 100, 526, 317);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(500, 300));

		loadConfigurations();

		new WindowController(frame);
	}
	/**
	 * Fügt den Unittypes statisch Advantages hinzu. Kann bei einer erweiterung als dynamische Option beim Erstellen des Servers implementiert werden.
	 */
	private static void loadConfigurations() {
		Unittype.ARCHER.addAdventage(Unittype.KNIGHT, 40);
		Unittype.KNIGHT.addAdventage(Unittype.SWORDSMAN, 40);
		Unittype.SWORDSMAN.addAdventage(Unittype.ARCHER, 40);
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> showWindow());
	}
}