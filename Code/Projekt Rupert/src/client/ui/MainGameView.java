package client.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

/**
 * Die View, welche während dem Spielen dargestellt wird. Beinhaltet die ButtonView, die GameView, die StateView und die DrawView.
 */
public class MainGameView extends JPanel {

	private static final long serialVersionUID = -4776316801158020585L;

	public MainGameView() {
		setSize(new Dimension(1000, 600));
		setMinimumSize(new Dimension(700, 400));
		initialize();
	}

	private void initialize() {
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 300, 200, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{10.0, 1.0, 2.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_panel);
	}

	public void addGameView(GameView gameView) {
		GridBagConstraints gbc_panelGameViewMainGameView = new GridBagConstraints();
		gbc_panelGameViewMainGameView.gridwidth = 2;
		gbc_panelGameViewMainGameView.insets = new Insets(0, 0, 5, 5);
		gbc_panelGameViewMainGameView.fill = GridBagConstraints.BOTH;
		gbc_panelGameViewMainGameView.gridx = 0;
		gbc_panelGameViewMainGameView.gridy = 0;
		add(gameView, gbc_panelGameViewMainGameView);
	}

	public void addStateView(StateView stateView) {
		GridBagConstraints gbc_panelStatsLabelsMainGameView = new GridBagConstraints();
		gbc_panelStatsLabelsMainGameView.insets = new Insets(0, 0, 0, 5);
		gbc_panelStatsLabelsMainGameView.fill = GridBagConstraints.BOTH;
		gbc_panelStatsLabelsMainGameView.gridx = 1;
		gbc_panelStatsLabelsMainGameView.gridy = 1;
		add(stateView, gbc_panelStatsLabelsMainGameView);
	}

	public void addButtonView(ButtonView buttonView) {
		GridBagConstraints gbc_panelButtonsMainGameView = new GridBagConstraints();
		gbc_panelButtonsMainGameView.insets = new Insets(0, 0, 0, 5);
		gbc_panelButtonsMainGameView.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelButtonsMainGameView.gridx = 0;
		gbc_panelButtonsMainGameView.gridy = 1;
		add(buttonView, gbc_panelButtonsMainGameView);
	}

	public void addDrawView(DrawView drawView) {
		GridBagConstraints gbc_listDrawsMainGameView = new GridBagConstraints();
		gbc_listDrawsMainGameView.gridheight = 2;
		gbc_listDrawsMainGameView.insets = new Insets(0, 0, 5, 0);
		gbc_listDrawsMainGameView.fill = GridBagConstraints.BOTH;
		gbc_listDrawsMainGameView.gridx = 2;
		gbc_listDrawsMainGameView.gridy = 0;
		add(drawView, gbc_listDrawsMainGameView);
	}
}
