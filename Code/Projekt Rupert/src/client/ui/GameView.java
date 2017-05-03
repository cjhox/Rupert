package client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import shared.GameCoordinate;
import shared.game.GameMap;
import shared.gameround.GameState.PlayState;
import client.domain.GameModel;

/**
 * Die GameView ist eine View innerhalb der MainGameView und beinhaltet das Canvas und das eigentliche Spiel
 */
public class GameView extends JPanel implements Observer {

	private static final long serialVersionUID = 355591614927452959L;

	private Canvas canvas;

	private static final Dimension CANVAS_SIZE = new Dimension(
			(GameMap.MAP_WIDTH) * GameCoordinate.STEP_WIDTH + 2
			* GameCoordinate.OFFSET_X, (GameMap.MAP_HEIGHT)
			* GameCoordinate.STEP_HEIGHT + 2 * GameCoordinate.OFFSET_Y);
	private static final Dimension APP_SIZE = new Dimension(400, 400);

	public GameView(GameModel model) {

		model.addObserver(this);

		canvas = new Canvas(model);
		canvas.setPreferredSize(CANVAS_SIZE);

		setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
		setPreferredSize(APP_SIZE);
		setLayout(new GridLayout(1, 1, 0, 0));
		JScrollPane scrollPane = new JScrollPane(canvas);
		add(scrollPane);
	}

	public void addCanvasClickListener(ClickListener canvasClickListener) {
		canvas.addMouseListener(canvasClickListener);
	}

	@Override
	public void repaint() {
		if (canvas != null) {
			canvas.repaint();
		}
	}

	public void showPhaseChange(PlayState playState) {
		canvas.showPhaseChange(playState);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
}
