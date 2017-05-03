package client.resources;

import java.awt.Image;
import java.util.List;

import shared.GameCoordinate;

public interface Sprite extends Drawable {

	public default boolean addActionListener(GameListener actionListener) {
		return getActionListeners().add(actionListener);
	}

	public default boolean removeActionListener(GameListener actionListener) {
		return getActionListeners().remove(actionListener);
	}

	public default void doClick(GameCoordinate gameCoordinate) {
		for (GameListener actionListener : getActionListeners()) {
			actionListener.actionPerformed(gameCoordinate);
		}
	}

	public abstract List<GameListener> getActionListeners();

	@Override
	public default boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
	
	public abstract DrawCoordinate getDrawCoordinate();

	public default boolean isClicked(Coordinate coordinate) {
		if (getDrawCoordinate() != null) {
			if (coordinate.x > getDrawCoordinate().getAX() && coordinate.x < getDrawCoordinate().getBX()) {
				if (coordinate.y > getDrawCoordinate().getAY() && coordinate.y < getDrawCoordinate().getBY()) {
					return true;
				}
			}
		}
		return false;
	}
}
