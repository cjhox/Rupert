package server;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Test {

	private static void showWindow() {
		JFrame frame = new JFrame("Rupert");
		frame.setBounds(100, 100, 526, 317);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		frame.setMinimumSize(new Dimension(500, 500));
		frame.setSize(new Dimension(500, 500));
		frame.setPreferredSize(new Dimension(500, 500));

//		new TestArrow(frame);		
		new TestGetFieldBefore(frame);
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> showWindow());
	}
}