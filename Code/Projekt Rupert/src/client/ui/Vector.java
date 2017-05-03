package client.ui;

/**
 * Erstellen eines Vektors. Wird für die Erstellung der auf der Map dargestellten Pfeile verwendet.
 */
public class Vector {

	public double	x;
	public double	y;
	public double	length;

	public Vector(double x, double y) {
		super();
		this.x = x;
		this.y = y;
		setLength();
		//System.out.println("x: " + x + " y: " + y + " length: " + length);
	}

	public Vector(double x, double y, double length) {
		super();
		this.x = x;
		this.y = y;
		this.length = length;
	}

	private void setLength() {
		length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public Vector getPerpendicular() {	
		return new Vector(-(y / length), (x / length), 1);
	}
}
