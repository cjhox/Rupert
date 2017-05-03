package client.ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Die ServerlobbyView wird von dem starten des Spiels angezeigt. Bei einem späteren Erweitern des Spiels können bei dieser View auch einstellungen am Server vorgenommen werden.
 */
public class ServerlobbyView extends JPanel {


	private static final long	serialVersionUID	= 8981708816397212670L;

	private JTextField			txtServerlobbyNamePlayer1;
	private JTextField			txtServerlobbyNamePlayer2;
	private JButton				btnServerlobbyAbort;
	private JButton				btnServerlobbyStart;

	/**
	 * Create the application.
	 */
	public ServerlobbyView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 100, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, Double.MIN_VALUE };
		setLayout(gbl_panel);

		Component verticalStrutServerlobby1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrutServerlobby1 = new GridBagConstraints();
		gbc_verticalStrutServerlobby1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrutServerlobby1.gridwidth = 2;
		gbc_verticalStrutServerlobby1.gridx = 0;
		gbc_verticalStrutServerlobby1.gridy = 0;
		add(verticalStrutServerlobby1, gbc_verticalStrutServerlobby1);

		initializeIPHeader();

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridwidth = 2;
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 2;
		add(verticalStrut, gbc_verticalStrut);

		initializeBody();

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.gridwidth = 2;
		gbc_verticalStrut_1.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 6;
		add(verticalStrut_1, gbc_verticalStrut_1);
	}

	private void initializeBody() {
		JLabel lblServerlobbyPlayer1 = new JLabel("Player1:");
		GridBagConstraints gbc_lblServerlobbyPlayer1 = new GridBagConstraints();
		gbc_lblServerlobbyPlayer1.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerlobbyPlayer1.gridx = 0;
		gbc_lblServerlobbyPlayer1.gridy = 3;
		add(lblServerlobbyPlayer1, gbc_lblServerlobbyPlayer1);

		txtServerlobbyNamePlayer1 = new JTextField();
		txtServerlobbyNamePlayer1.setEditable(false);
		txtServerlobbyNamePlayer1.setText("Player1");
		GridBagConstraints gbc_txtServerlobbyNamePlayer1 = new GridBagConstraints();
		gbc_txtServerlobbyNamePlayer1.insets = new Insets(0, 0, 5, 0);
		gbc_txtServerlobbyNamePlayer1.gridx = 1;
		gbc_txtServerlobbyNamePlayer1.gridy = 3;
		add(txtServerlobbyNamePlayer1, gbc_txtServerlobbyNamePlayer1);
		txtServerlobbyNamePlayer1.setColumns(10);

		JLabel lblServerlobbyPlayer2 = new JLabel("Player2:");
		GridBagConstraints gbc_lblServerlobbyPlayer2 = new GridBagConstraints();
		gbc_lblServerlobbyPlayer2.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerlobbyPlayer2.gridx = 0;
		gbc_lblServerlobbyPlayer2.gridy = 4;
		add(lblServerlobbyPlayer2, gbc_lblServerlobbyPlayer2);

		txtServerlobbyNamePlayer2 = new JTextField();
		txtServerlobbyNamePlayer2.setEditable(false);
		txtServerlobbyNamePlayer2.setText("Player2");
		GridBagConstraints gbc_txtServerlobbyNamePlayer2 = new GridBagConstraints();
		gbc_txtServerlobbyNamePlayer2.insets = new Insets(0, 0, 5, 0);
		gbc_txtServerlobbyNamePlayer2.gridx = 1;
		gbc_txtServerlobbyNamePlayer2.gridy = 4;
		add(txtServerlobbyNamePlayer2, gbc_txtServerlobbyNamePlayer2);
		txtServerlobbyNamePlayer2.setColumns(10);

		btnServerlobbyAbort = new JButton("Abort");
		GridBagConstraints gbc_btnServerlobbyAbort = new GridBagConstraints();
		gbc_btnServerlobbyAbort.insets = new Insets(0, 0, 5, 5);
		gbc_btnServerlobbyAbort.gridx = 0;
		gbc_btnServerlobbyAbort.gridy = 5;
		add(btnServerlobbyAbort, gbc_btnServerlobbyAbort);

		btnServerlobbyStart = new JButton("Start Game");
		GridBagConstraints gbc_btnServerlobbyStart = new GridBagConstraints();
		gbc_btnServerlobbyStart.insets = new Insets(0, 0, 5, 0);
		gbc_btnServerlobbyStart.gridx = 1;
		gbc_btnServerlobbyStart.gridy = 5;
		add(btnServerlobbyStart, gbc_btnServerlobbyStart);
		//btnServerlobbyStart.setEnabled(false);
	}

	private void initializeIPHeader() {
		JLabel lblServerlobbyServer = new JLabel("Server:");
		GridBagConstraints gbc_lblServerlobbyServer = new GridBagConstraints();
		gbc_lblServerlobbyServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerlobbyServer.gridx = 0;
		gbc_lblServerlobbyServer.gridy = 1;
		add(lblServerlobbyServer, gbc_lblServerlobbyServer);

		JLabel lblServerlobbyIP = new JLabel("\"IP-Address\"");
		GridBagConstraints gbc_lblServerlobbyIP = new GridBagConstraints();
		gbc_lblServerlobbyIP.insets = new Insets(0, 0, 5, 0);
		gbc_lblServerlobbyIP.gridx = 1;
		gbc_lblServerlobbyIP.gridy = 1;
		add(lblServerlobbyIP, gbc_lblServerlobbyIP);
	}

	public void setAbortButtonListener(ActionListener l) {
		btnServerlobbyAbort.addActionListener(l);
	}

	public void setStartButtonListener(ActionListener l) {
		btnServerlobbyStart.addActionListener(l);
	}

	public void enableStartButton() {
		btnServerlobbyStart.setEnabled(true);
	}
}
