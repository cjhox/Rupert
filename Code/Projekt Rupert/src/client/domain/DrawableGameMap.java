package client.domain;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import client.resources.GameListener;
import client.resources.UnitListenerFactoryInterface;
import shared.GameCoordinate;
import shared.PlayerPosition;
import shared.Unittype;
import shared.game.Field;
import shared.game.GameMap;

/**
 * Die Map eines Spiels. Enthält DrawableFields.
 */
public class DrawableGameMap extends GameMap {

	// inv: getField(x, y).getGameCoordinate().x == x;
	// inv: getField(x, y).getGameCoordinate().y == y;

	private static final long	serialVersionUID	= 3897297952425425213L;

	private DrawableField[][]	fields;
	private DrawableField		selected;

	public DrawableGameMap(GameMap sourceMap, GameListener actionListener, UnitListenerFactoryInterface unitListenerFactory) {
		super(sourceMap.getHeight(), sourceMap.getWidth());
		fields = new DrawableField[height][width];
		Field[][] sourceFields = sourceMap.getFields();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				fields[y][x] = new DrawableField(sourceFields[y][x], actionListener, unitListenerFactory);
			}
		}
	}

	public void select(GameCoordinate gc) {
		if (selected != null) {
			selected.setSelection(false);
		}
		if (isOnMap(gc)) {
			selected = getField(gc);
			selected.setSelection(true);
		}
	}

	private boolean isOnMap(GameCoordinate gc) {
		return gc.x < MAP_WIDTH && gc.y < MAP_HEIGHT && gc.x >= 0 && gc.y >= 0;
	}

	public int getHealthFromSelectedUnit() {
		if (selected == null) {
			return 0;
		}
		return getUnit(selected.getGameCoordinate()).getHealth();
	}

	public Unittype getUnittypeFromSelectedUnit() {
		if (selected == null) {
			return null;
		}
		return getUnit(selected.getGameCoordinate()).getUnittype();
	}

	public GameCoordinate getGCFromSelectedUnit() {
		if (selected == null) {
			return new GameCoordinate(-2, -2);
		}
		return selected.getGameCoordinate();
	}

	@Override
	public DrawableField getField(GameCoordinate gameCoordinate) {
		return fields[gameCoordinate.y][gameCoordinate.x];
	}

	@Override
	public DrawableUnit getUnit(GameCoordinate gameCoordinate) {
		return getField(gameCoordinate).getUnit();
	}

	public void doClick(GameCoordinate gameCoordinate) {
		getField(gameCoordinate).doClick();
	}

	public void draw(Graphics g) {
		for (DrawableField[] fields : fields) {
			for (DrawableField field : fields) {
				field.draw(g);
			}
		}
	}

	@Override
	public String toString() {
		String s = "DrawableGameMap [fields=\n";
		for (DrawableField[] drawableFields : fields) {
			s += "[";
			for (DrawableField drawableField : drawableFields) {
				s += "[" + drawableField + "]";
			}
			s += "]\n";
		}
		s += ", selected=" + selected + ", height=" + height + ", width=" + width + "]";
		return s;
	}

	public List<AreaField> getStartField(GameListener gameListener, PlayerPosition playerPosition) {
		int x;
		int y;
		List<AreaField> tf = new ArrayList<AreaField>();

		switch (playerPosition) {
		case LEFT:
			for (x = 0; x < SF_WIDTH; x++) {
				for (y = (MAP_HEIGHT - SF_HEIGHT) / 2; y < (MAP_HEIGHT + SF_HEIGHT) / 2; y++) {
					tf.add(new AreaField(new GameCoordinate(x, y), gameListener));
				}
			}
			return tf;
		case RIGHT:
			for (x = MAP_WIDTH - SF_WIDTH; x < MAP_WIDTH; x++) {
				for (y = (MAP_HEIGHT - SF_HEIGHT) / 2; y < (MAP_HEIGHT + SF_HEIGHT) / 2; y++) {
					tf.add(new AreaField(new GameCoordinate(x, y), gameListener));
				}
			}
			return tf;
		default:
			System.err.println("DrawableGameMap.getStartField: PlayerPosition is undefined!");
			return tf;
		}
	}
}