package client.ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

/**
 * Erste dargestellte View. Ermöglicht das Erstellen eines Spiels/Servers oder
 * das Verbinden auf ein bestehendes Spiel/Server.
 */
public class HomescreenView extends JPanel {

	private static final long	serialVersionUID	= -395731569998276531L;

	private InetAddress			inetAddress;
	private JTextField			txtHomescreenIP;
	private Component			verticalStrutHomescreen2;
	private JButton				btnHomescreenJoin;
	private JButton				btnHomescreenClose;
	private Component			verticalStrutHomescreen3;
	private JLabel				lblHomescreenTitle;
	private JButton				btnHomescreenCreate;
	private JPanel				panel;
	private JLabel				lblIp;

	/**
	 * Create the application.
	 * 
	 * @param inetAddress
	 */
	public HomescreenView(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 148, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 2.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 2.0, Double.MIN_VALUE };
		setLayout(gbl_panel);

		Component verticalStrutHomescreen1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrutHomescreen1 = new GridBagConstraints();
		gbc_verticalStrutHomescreen1.gridwidth = 3;
		gbc_verticalStrutHomescreen1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrutHomescreen1.gridx = 0;
		gbc_verticalStrutHomescreen1.gridy = 0;
		add(verticalStrutHomescreen1, gbc_verticalStrutHomescreen1);

		lblHomescreenTitle = new JLabel("Rupert: The Game\r\n");
		GridBagConstraints gbc_lblHomescreenTitle = new GridBagConstraints();
		gbc_lblHomescreenTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblHomescreenTitle.gridx = 1;
		gbc_lblHomescreenTitle.gridy = 1;
		add(lblHomescreenTitle, gbc_lblHomescreenTitle);

		verticalStrutHomescreen2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrutHomescreen2 = new GridBagConstraints();
		gbc_verticalStrutHomescreen2.gridwidth = 3;
		gbc_verticalStrutHomescreen2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrutHomescreen2.gridx = 0;
		gbc_verticalStrutHomescreen2.gridy = 2;
		add(verticalStrutHomescreen2, gbc_verticalStrutHomescreen2);

		initializeInput();

		verticalStrutHomescreen3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrutHomescreen3 = new GridBagConstraints();
		gbc_verticalStrutHomescreen3.gridwidth = 3;
		gbc_verticalStrutHomescreen3.gridx = 0;
		gbc_verticalStrutHomescreen3.gridy = 7;
		add(verticalStrutHomescreen3, gbc_verticalStrutHomescreen3);
	}

	private void initializeInput() {
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		lblIp = new JLabel("IP: ");
		panel.add(lblIp);

		txtHomescreenIP = new JTextField();
		if (inetAddress != null) {
			txtHomescreenIP.setText(inetAddress.getHostAddress());
		} else {
			// no Internet connection detected
			txtHomescreenIP.setText("localhost");
		}
		panel.add(txtHomescreenIP);
		txtHomescreenIP.setToolTipText("Enter you IP adress to create a new game or the IP adress of the host to join a game.");
		txtHomescreenIP.setColumns(10);

		btnHomescreenCreate = new JButton("Create new Game");
		GridBagConstraints gbc_btnHomescreenCreate = new GridBagConstraints();
		gbc_btnHomescreenCreate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHomescreenCreate.insets = new Insets(0, 0, 5, 5);
		gbc_btnHomescreenCreate.gridx = 1;
		gbc_btnHomescreenCreate.gridy = 4;
		add(btnHomescreenCreate, gbc_btnHomescreenCreate);

		btnHomescreenJoin = new JButton("Join Game");
		btnHomescreenJoin.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_btnHomescreenJoin = new GridBagConstraints();
		gbc_btnHomescreenJoin.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHomescreenJoin.insets = new Insets(0, 0, 5, 5);
		gbc_btnHomescreenJoin.gridx = 1;
		gbc_btnHomescreenJoin.gridy = 5;
		add(btnHomescreenJoin, gbc_btnHomescreenJoin);

		btnHomescreenClose = new JButton("Exit");
		GridBagConstraints gbc_btnHomescreenClose = new GridBagConstraints();
		gbc_btnHomescreenClose.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHomescreenClose.insets = new Insets(0, 0, 5, 5);
		gbc_btnHomescreenClose.gridx = 1;
		gbc_btnHomescreenClose.gridy = 6;
		add(btnHomescreenClose, gbc_btnHomescreenClose);
	}

	public void setIPDocumentListener(DocumentListener l) {
		txtHomescreenIP.getDocument().addDocumentListener(l);
	}

	public void setCloseButtonListener(ActionListener l) {
		btnHomescreenClose.addActionListener(l);
	}

	public void setJoinButtonListener(ActionListener l) {
		btnHomescreenJoin.addActionListener(l);
	}

	public void setCreateButtonListener(ActionListener l) {
		btnHomescreenCreate.addActionListener(l);
	}

	public String getIPText() {
		return txtHomescreenIP.getText();
	}

	public void enableCreateButton(boolean b) {
		btnHomescreenCreate.setEnabled(b);
	}

	public void enableJoinButton(boolean b) {
		btnHomescreenJoin.setEnabled(b);
	}

	public boolean isIPEmpty() {
		return txtHomescreenIP.getText().isEmpty();
	}
}
