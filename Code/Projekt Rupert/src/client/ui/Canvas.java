package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import shared.Action.FightAction;
import shared.gameround.Draw;
import shared.gameround.PlaceDraw;
import shared.gameround.GameState.PlayState;
import client.domain.GameModel;

public class Canvas extends JPanel {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private GameModel			model;

	private JLabel				phaseLabel;

	public Canvas(GameModel model) {
		super();
		this.model = model;
		setLayout(new BorderLayout());

		phaseLabel = new JLabel("Move ");
		phaseLabel.setVisible(false);
		phaseLabel.setForeground(new Color(100, 149, 237));
		phaseLabel.setFont(new Font("Andalus", Font.BOLD | Font.ITALIC, 90));
		phaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(phaseLabel, BorderLayout.CENTER);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		model.draw(g);

		for (Draw draw : model.getPhase().getDraws()) {
			if (draw instanceof PlaceDraw) {
				PlaceDraw placeDraw = (PlaceDraw) draw;
				new UnitIcon(placeDraw.getTarget(), placeDraw.getImage()).draw(g);
			} else if (draw.getAction() == FightAction.DEFENCE) {
				new UnitIcon(draw.getTarget(), UnitIcon.getShieldImage()).draw(g);
			} else if (draw.getAction() == FightAction.HEAL) {
				new UnitIcon(draw.getTarget(), UnitIcon.getHealImage()).draw(g);
			} else {
				new Arrow(draw.getSource(), draw.getTarget()).draw(g);
			}
		}
	}

	public void showPhaseChange(PlayState playState) {
		phaseLabel.setText(playState + "! ");
		phaseLabel.setVisible(true);
		long end = java.time.LocalTime.now().toNanoOfDay() + (1200 * 1000000);// ms
		long now;
		do {
			now = java.time.LocalTime.now().toNanoOfDay();
		} while (now <= end);
		phaseLabel.setVisible(false);
	}
}