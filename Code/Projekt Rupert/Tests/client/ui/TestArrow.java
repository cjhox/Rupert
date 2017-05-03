package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shared.game.GameCoordinate;
import shared.game.MoveDraw;

public class TestArrow {

	private MyCanvas	canvas;
	private JSlider		xSlider;
	private JSlider		ySlider;

	public TestArrow(JFrame frame) {
		MoveDraw md = new MoveDraw(0, new GameCoordinate(0, 0), new GameCoordinate(4, 6), null);

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

		JLabel xLabel = new JLabel("1");
		GridBagConstraints gbc_xLabel = new GridBagConstraints();
		gbc_xLabel.anchor = GridBagConstraints.NORTH;
		gbc_xLabel.insets = new Insets(0, 0, 5, 5);
		gbc_xLabel.gridx = 0;
		gbc_xLabel.gridy = 0;
		panel.add(xLabel, gbc_xLabel);

		JLabel yLabel = new JLabel("1");
		GridBagConstraints gbc_yLabel = new GridBagConstraints();
		gbc_yLabel.anchor = GridBagConstraints.NORTH;
		gbc_yLabel.insets = new Insets(0, 0, 5, 0);
		gbc_yLabel.gridx = 1;
		gbc_yLabel.gridy = 0;
		panel.add(yLabel, gbc_yLabel);

		xSlider = new JSlider();
		xSlider.setMinimum(-8);
		xSlider.setValue(1);
		xSlider.setMaximum(8);
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
		ySlider.setMinimum(-8);
		ySlider.setValue(1);
		ySlider.setMaximum(8);
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
		canvas.setMoveDraw(md);
		canvas.repaint();
	}

	public class MyCanvas extends JPanel {

		/**
		 * 
		 */
		private static final long	serialVersionUID	= 4341402539760088456L;
		private MoveDraw			draw;

		public MyCanvas(MoveDraw draw) {
			this.draw = draw;
		}

		public void setMoveDraw(MoveDraw moveDraw) {
			draw = moveDraw;
		}

		protected void paintComponent(Graphics g) {
			g.clearRect(0, 0, 500, 400);
			g.setColor(new Color(0, 0, 0));

			new Arrow(draw.getSource(), draw.getTarget()).draw(g);
		}
	}
}
