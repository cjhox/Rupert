package client.ui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * Die ButtonView ist eine View innerhalb der MainGameView. Beinhaltet Buttons
 * sowie Methoden für das setzen der Listeners und für das Enablen / Disablen.
 */
public class ButtonView extends JPanel {

	private static final long	serialVersionUID	= 617390249299481861L;

	private JButton				btnOKButtonView;
	private JSeparator			separator1;
	private JButton				btnArcherButtonView;
	private JButton				btnKnightButtonView;
	private JButton				btnSwordsmanButtonView;
	private JSeparator			separator2;
	private JButton				btnDeffendButtonView;
	private JButton				btnHealButtonView;

	public ButtonView() {
		super();

		setBorder(null);

		btnOKButtonView = new JButton("OK");
		add(btnOKButtonView);

		separator1 = new JSeparator();
		separator1.setOrientation(SwingConstants.VERTICAL);
		add(separator1);

		btnArcherButtonView = new JButton("Archer");
		btnArcherButtonView.setEnabled(false);
		add(btnArcherButtonView);

		btnKnightButtonView = new JButton("Knight");
		btnKnightButtonView.setEnabled(false);
		add(btnKnightButtonView);

		btnSwordsmanButtonView = new JButton("Swordsman");
		btnSwordsmanButtonView.setEnabled(false);
		add(btnSwordsmanButtonView);

		separator2 = new JSeparator();
		separator2.setOrientation(SwingConstants.VERTICAL);
		add(separator2);

		btnDeffendButtonView = new JButton("Deffend");
		btnDeffendButtonView.setEnabled(false);
		add(btnDeffendButtonView);

		btnHealButtonView = new JButton("Heal");
		btnHealButtonView.setEnabled(false);
		add(btnHealButtonView);
	}

	public void addOKButtonListener(ActionListener l) {
		btnOKButtonView.addActionListener(l);
	}

	public void addDeffendButtonListener(ActionListener l) {
		btnDeffendButtonView.addActionListener(l);
	}

	public void addHealButtonListener(ActionListener l) {
		btnHealButtonView.addActionListener(l);
	}

	public void addArcherButtonListener(ActionListener l) {
		btnArcherButtonView.addActionListener(l);
	}

	public void addKnightButtonListener(ActionListener l) {
		btnKnightButtonView.addActionListener(l);
	}

	public void addSwordsmanButtonListener(ActionListener l) {
		btnSwordsmanButtonView.addActionListener(l);
	}

	public void enableOkBtn() {
		btnOKButtonView.setEnabled(true);
	}

	public void disableOkBtn() {
		btnOKButtonView.setEnabled(false);
	}

	public void enableDeffendBtn() {
		btnDeffendButtonView.setEnabled(true);
	}

	public void disableDeffendBtn() {
		btnDeffendButtonView.setEnabled(false);
	}

	public void enableHealBtn() {
		btnHealButtonView.setEnabled(true);
	}

	public void disableHealBtn() {
		btnHealButtonView.setEnabled(false);
	}
	
	public void enableUnitButtons() {
		btnArcherButtonView.setEnabled(true);
		btnKnightButtonView.setEnabled(true);
		btnSwordsmanButtonView.setEnabled(true);
	}

	public void disableUnitButtons() {
		btnArcherButtonView.setEnabled(false);
		btnKnightButtonView.setEnabled(false);
		btnSwordsmanButtonView.setEnabled(false);
	}
	
	public void enableArcherBtn() {
		btnArcherButtonView.setEnabled(true);
	}

	public void disableArcherBtn() {
		btnArcherButtonView.setEnabled(false);
	}

	public void enableKnightBtn() {
		btnKnightButtonView.setEnabled(true);
	}

	public void disableKnightBtn() {
		btnKnightButtonView.setEnabled(false);
	}

	public void enableSwordsmanBtn() {
		btnSwordsmanButtonView.setEnabled(true);
	}

	public void disableSwordsmanBtn() {
		btnSwordsmanButtonView.setEnabled(false);
	}
}
