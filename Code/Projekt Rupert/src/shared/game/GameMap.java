package shared.game;

import java.io.Serializable;
import java.util.Arrays;

import shared.GameCoordinate;
import shared.Player;
import shared.Unittype;
import shared.com.UpdateHealthData;
import shared.gameround.MoveDraw;
import shared.gameround.PlaceDraw;

/**
 * Serverseitige Map des Spiels. Analog zu DrawableGameMap des Clients.
 */
public class GameMap implements Serializable {

	public static final int		MAP_HEIGHT			= 14;
	public static final int		MAP_WIDTH			= 20;
	public static final int		SF_HEIGHT			= 10;
	public static final int		SF_WIDTH			= 4;

	// inv: getField(x, y).getGameCoordinate().x == x;
	// inv: getField(x, y).getGameCoordinate().y == y;

	private static final long	serialVersionUID	= -106969111947311427L;

	protected int				height;
	protected int				width;

	private Field[][]			fields;

	public GameMap(int height, int width) {
		this.height = height;
		this.width = width;
	}

	// kann in DrawableGameMap nicht verwendet werden
	public void initializeFields() {
		fields = new Field[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				fields[y][x] = new Field(Fieldtype.GRASS, new GameCoordinate(x, y));
			}
		}
	}

	public boolean move(MoveDraw moveDraw) {
		return getField(moveDraw.getTarget()).placeUnit(getField(moveDraw.getSource()).removeUnit());
	}

	public void fight(UpdateHealthData data) {
		switch (data.getAction()) {
		case ATTACK:
			if (!getField(data.getSource()).isDead()) {
				getField(data.getTarget()).attack(data.getValue());
			}
			break;

		case DEFENCE:
			getField(data.getTarget()).setDeffending(true);
			break;

		case HEAL:
			getField(data.getTarget()).heal(data.getValue());
			break;

		default:
			break;
		}
	}

	public boolean place(PlaceDraw placeDraw) {
		return getField(placeDraw.getTarget()).placeUnit(placeDraw);
	}

	public boolean isTargetAllowed(GameCoordinate target, Player player) {
		return getUnittype(target) != Unittype.EMPTY && !player.equals(getUnit(target).getPlayer());
	}

	// wird überschrieben
	public Field[][] getFields() {
		return fields;
	}

	// wird überschrieben
	public Field getField(GameCoordinate gameCoordinate) {
		return fields[gameCoordinate.y][gameCoordinate.x];
	}

	// wird überschrieben
	public Unit getUnit(GameCoordinate gameCoordinate) {
		return getField(gameCoordinate).getUnit();
	}

	public Unittype getUnittype(GameCoordinate gameCoordinate) {
		return getField(gameCoordinate).getUnittype();
	}

	// kann auch in DrawableGameMap verwendet werden
	public int getHeight() {
		return height;
	}

	// kann auch in DrawableGameMap verwendet werden
	public int getWidth() {
		return width;
	}

	@Override
	public String toString() {
		return "GameMap [height=" + height + ", width=" + width + ", fields=" + Arrays.toString(fields) + "]";
	}
}
