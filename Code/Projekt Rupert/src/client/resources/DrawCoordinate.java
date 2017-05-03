package client.resources;

import shared.GameCoordinate;

public class DrawCoordinate {

	private Coordinate	a;
	private Coordinate	b;

	public DrawCoordinate(Coordinate a, Coordinate b) {
		super();
		this.a = a;
		this.b = b;
	}

	public static DrawCoordinate getDrawCoordinate(GameCoordinate gameCoordinate, int width, int height) {
		if (gameCoordinate != null) {
			return new DrawCoordinate(
				new Coordinate(
					gameCoordinate.x * GameCoordinate.STEP_WIDTH + GameCoordinate.STEP_WIDTH / 2 - width / 2 + GameCoordinate.OFFSET_X,
					gameCoordinate.y * GameCoordinate.STEP_HEIGHT + GameCoordinate.STEP_HEIGHT - height + GameCoordinate.OFFSET_Y), 
				new Coordinate(
					gameCoordinate.x * GameCoordinate.STEP_WIDTH + GameCoordinate.STEP_WIDTH / 2 + width / 2 + GameCoordinate.OFFSET_X,
					gameCoordinate.y * GameCoordinate.STEP_HEIGHT + GameCoordinate.STEP_HEIGHT + GameCoordinate.OFFSET_Y));
		}
		return null;
	}

	public Coordinate getA() {
		return a;
	}

	public Coordinate getB() {
		return b;
	}

	public int getAX() {
		return a.x;
	}

	public int getAY() {
		return a.y;
	}

	public int getBX() {
		return b.x;
	}

	public int getBY() {
		return b.y;
	}

	@Override
	public String toString() {
		return "DrawCoordinate [" + a + ", " + b + "]";
	}
}
