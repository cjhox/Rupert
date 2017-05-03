package client.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import client.resources.Coordinate;
import client.resources.DrawCoordinate;
import client.resources.Drawable;
import shared.GameCoordinate;

/**
 * Klasse für die Pfeile.
 */
public class Arrow implements Drawable {

	private GameCoordinate		source;
	private GameCoordinate		target;

	private static final double	Root3	= Math.sqrt(3);
	private static final int LineLength = GameCoordinate.STEP_WIDTH - 10;

	public Arrow(GameCoordinate source, GameCoordinate target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		
		DrawCoordinate drawCoordinate = new DrawCoordinate(
			new Coordinate(
				source.x * GameCoordinate.STEP_WIDTH + GameCoordinate.STEP_WIDTH / 2 + GameCoordinate.OFFSET_X,
				source.y * GameCoordinate.STEP_HEIGHT + GameCoordinate.STEP_HEIGHT / 2 + GameCoordinate.OFFSET_Y), 
			new Coordinate(
				target.x * GameCoordinate.STEP_WIDTH + GameCoordinate.STEP_WIDTH / 2 + GameCoordinate.OFFSET_X,
				target.y * GameCoordinate.STEP_HEIGHT + GameCoordinate.STEP_HEIGHT / 2 + GameCoordinate.OFFSET_Y));
		
		Vector rB = new Vector(drawCoordinate.getBX(), drawCoordinate.getBY());
		
		Vector v = new Vector(drawCoordinate.getAX() - drawCoordinate.getBX(), drawCoordinate.getAY() - drawCoordinate.getBY());
		Vector u = calcFirstLine(v);
		Vector w = calcSecondLine(v, u);

		drawLine(g, rB, v, 5);
		drawLine(g, rB, u, 4);
		drawLine(g, rB, w, 4);
	}

	private void drawLine(Graphics g, Vector rB, Vector v, int thickness) {
		int factor1 = (thickness - 1) / 2;
		int factor2 = thickness - 1 - factor1;
		Vector s = v.getPerpendicular();

		int[] x = { (int) (rB.x - factor2 * s.x), (int) (rB.x + factor1 * s.x), (int) (rB.x + v.x + factor1 * s.x), (int) (rB.x + v.x - factor2 * s.x) };
		int[] y = { (int) (rB.y - factor2 * s.y), (int) (rB.y + factor1 * s.y), (int) (rB.y + v.y + factor1 * s.y), (int) (rB.y + v.y - factor2 * s.y) };

		g.fillPolygon(x, y, 4);
	}

	private Vector calcFirstLine(Vector v) {
		double y = (Root3 * v.y - v.x) / (2 * v.length) * LineLength;
		double x = (Root3 * v.x + v.y) / (2 * v.length) * LineLength;

		return new Vector(x, y, LineLength);
	}

	private Vector calcSecondLine(Vector v, Vector u) {
		int sig = -1;
		double part = Math.abs(v.y * u.y + v.x * u.x) / (Math.pow(v.x, 2) + Math.pow(v.y, 2));
		double x = u.x + sig * v.x * part;
		double y = u.y + sig * v.y * part;

		return new Vector(u.x - x * 2, u.y - y * 2, LineLength);
	}

}
