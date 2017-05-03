package shared;

import java.io.Serializable;

/**
 * Der Player wird beim Starten des Spiels erstellt und vom Client an den Server
 * übermittelt.
 */

public class Player implements Serializable {

	private static final long	serialVersionUID	= -5662585918919170931L;

	private String				name;
	private Color				color;
	private PlayerPosition		position;

	public Player(String name, Color color, PlayerPosition position) {
		super();
		this.name = name;
		this.color = color;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public void changePosition() {
		if (position != null)
			switch (position) {
			case LEFT:
				position = PlayerPosition.RIGHT;
				break;
			case RIGHT:
				position = PlayerPosition.LEFT;
				break;
			}
	}

	public PlayerPosition getPosition() {
		if (position == null) {
			System.err.println("Player.getPosition: PlayerPosition is undefined!");
		}
		return position;
	}

	@Override
	public String toString() {
		return name + ": color=" + color + ", position=" + position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		Player other = (Player) obj;
		if (color != other.color)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (position != other.position)
			return false;
		return true;
	}
}
