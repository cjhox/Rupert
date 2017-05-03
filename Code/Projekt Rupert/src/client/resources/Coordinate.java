package client.resources;

import java.awt.Point;

import shared.GameCoordinate;

public class Coordinate {

	public int x;
	public int y;

	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Coordinate(Point point) {
		x = point.x;
		y = point.y;
	}

	public GameCoordinate getGameCoordinate() {
		return new GameCoordinate((x - GameCoordinate.OFFSET_X)
				/ GameCoordinate.STEP_WIDTH, (y - GameCoordinate.OFFSET_Y)
				/ GameCoordinate.STEP_HEIGHT);
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
