package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shared.game.GameCoordinate;
import shared.game.MoveDraw;

public class TestGetFieldBefore {

	public static final int		size			= 10;
	public List<GameCoordinate>	gameCoordinates	= new ArrayList<GameCoordinate>();
	private MyCanvas			canvas;
	private JSlider				ySlider;
	private JSlider				xSlider;

	public TestGetFieldBefore(JFrame frame) {

		MoveDraw md = new MoveDraw(0, new GameCoordinate(0, 0), new GameCoordinate(5, 9), null);

		canvas = new MyCanvas(md);
		canvas.setSize(new Dimension(500, 500));
		canvas.setPreferredSize(new Dimension(500, 500));
		canvas.setMinimumSize(new Dimension(500, 500));
		canvas.setBackground(new Color(255, 255, 255));

		exec(md);

		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 225, 225, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel xLabel = new JLabel("10");
		GridBagConstraints gbc_xLabel = new GridBagConstraints();
		gbc_xLabel.anchor = GridBagConstraints.NORTH;
		gbc_xLabel.insets = new Insets(0, 0, 5, 5);
		gbc_xLabel.gridx = 0;
		gbc_xLabel.gridy = 0;
		panel.add(xLabel, gbc_xLabel);

		JLabel yLabel = new JLabel("10");
		GridBagConstraints gbc_yLabel = new GridBagConstraints();
		gbc_yLabel.anchor = GridBagConstraints.NORTH;
		gbc_yLabel.insets = new Insets(0, 0, 5, 0);
		gbc_yLabel.gridx = 1;
		gbc_yLabel.gridy = 0;
		panel.add(yLabel, gbc_yLabel);

		xSlider = new JSlider();
		xSlider.setValue(10);
		xSlider.setMaximum(20);
		GridBagConstraints gbc_xSlider = new GridBagConstraints();
		gbc_xSlider.anchor = GridBagConstraints.NORTH;
		gbc_xSlider.insets = new Insets(0, 0, 0, 5);
		gbc_xSlider.gridx = 0;
		gbc_xSlider.gridy = 1;
		xSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				xLabel.setText(xSlider.getValue() + "");
				exec(new MoveDraw(0, new GameCoordinate(0, 0), new GameCoordinate(xSlider.getValue(),
						ySlider.getValue()), null));
			}
		});
		panel.add(xSlider, gbc_xSlider);

		ySlider = new JSlider();
		ySlider.setValue(10);
		ySlider.setMaximum(20);
		GridBagConstraints gbc_ySlider = new GridBagConstraints();
		gbc_ySlider.anchor = GridBagConstraints.NORTH;
		gbc_ySlider.gridx = 1;
		gbc_ySlider.gridy = 1;
		ySlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				yLabel.setText(ySlider.getValue() + "");
				exec(new MoveDraw(0, new GameCoordinate(0, 0), new GameCoordinate(xSlider.getValue(),
						ySlider.getValue()), null));
			}
		});
		panel.add(ySlider, gbc_ySlider);

		xLabel.setLabelFor(xSlider);
		yLabel.setLabelFor(ySlider);

		frame.getContentPane().add(panel, BorderLayout.SOUTH);
	}

	public void exec(MoveDraw md) {
		GameCoordinate target = md.getTarget();
		gameCoordinates.clear();
		gameCoordinates.add(target);
		while (!target.equals(md.getSource())) {
			target = getFieldBevore(md, target);
			if (target == null) {
				System.err.println("null");
				break;
			}
			gameCoordinates.add(target);
		}
		canvas.setMoveDraw(md);
		canvas.repaint();
	}

	public GameCoordinate getFieldBevore(MoveDraw move, GameCoordinate target) {
		GameCoordinate source = move.getSource();
		GameCoordinate realTarget = move.getTarget();
		int a = realTarget.x - source.x;
		int b = realTarget.y - source.y;

		int absA = Math.abs(a);
		int absB = Math.abs(b);

		int fA = 1;
		int fB = 1;

		if (absA == 0) {
			fA = 0;
		} else {
			fA = a / absA;
		}

		if (absB == 0) {
			fB = 0;
		} else {
			fB = b / absB;
		}

		if (absA > absB) {
			absB++;
			GameCoordinate gameCoordinate = searchFieldBefor(target, realTarget, absA, absB, fA, fB, false);
			return new GameCoordinate(realTarget.x - (gameCoordinate.x * fA), realTarget.y - (gameCoordinate.y * fB));
		} else {
			absA++;
			GameCoordinate gameCoordinate = searchFieldBefor(target, realTarget, absB, absA, fA, fB, true);
			return new GameCoordinate(realTarget.x - (gameCoordinate.y * fA), realTarget.y - (gameCoordinate.x * fB));
		}
	}

	private GameCoordinate searchFieldBefor(GameCoordinate target, GameCoordinate realTarget, int absA, int absB,
			int fA, int fB, boolean switchXY) {
		int anz = (int) ((absA + absB) / absB);
		int rest = absA + absB - anz * (absB);
		int x = 0;
		boolean found = false;

		for (int y = 0; y < absB; y++) {
			int tempAnz = anz;
			if ((absB - rest) / 2 < y && (absB + rest) / 2 >= y) {
				tempAnz++;
			}

			for (int j = 0; j < tempAnz; j++) {
				if (found) {
					return new GameCoordinate(x, y);
				}
				if (switchXY) {
					if (target.equals(new GameCoordinate(realTarget.x - (y * fA), realTarget.y - (x * fB)))) {
						found = true;
					}
				} else {
					if (target.equals(new GameCoordinate(realTarget.x - (x * fA), realTarget.y - (y * fB)))) {
						found = true;
					}
				}
				x++;
			}
			x--;
		}
		return null;
	}

	public class MyCanvas extends JPanel {

		/**
		 * 
		 */
		private static final long	serialVersionUID	= 4341402539760088456L;
		private MoveDraw			moveDraw;

		public MyCanvas(MoveDraw moveDraw) {
			this.moveDraw = moveDraw;
		}

		public void setMoveDraw(MoveDraw moveDraw) {
			this.moveDraw = moveDraw;
		}

		protected void paintComponent(Graphics g) {
			g.clearRect(0, 0, 500, 400);
			g.setColor(new Color(0, 0, 0));
			for (GameCoordinate gc : gameCoordinates) {
				g.drawRect(gc.x * size + size / 2 + 40, 300 - gc.y * size - size / 2, size, size);
			}

			g.drawLine(moveDraw.getSource().x * size + size + 40, 300 - moveDraw.getSource().y * size,
					moveDraw.getTarget().x * size + size + 40, 300 - moveDraw.getTarget().y * size);
		}
	}
}