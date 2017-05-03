package shared;

import java.io.Serializable;

/**
 * GameCoordinate sind die Spielbezogenen Koordinaten. Diese werden für den Gameraster verwendet.
 */

public class GameCoordinate implements Serializable {

	private static final long serialVersionUID = 5749917415392640301L;
	
	public int x;
	public int y;
	
	public static final int STEP_WIDTH = 64;
	public static final int STEP_HEIGHT = 64;
	public static final int OFFSET_X = 32;
	public static final int OFFSET_Y = 32;

	public GameCoordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameCoordinate other = (GameCoordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
}
