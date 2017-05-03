package shared.game;

import java.io.Serializable;
import java.util.List;

public interface GameInterface extends Serializable {

	public GameMapInterface getGameMap();

	public Player getHost();

	public Player getClient();

	public List<? extends UnitInterface> getUnits();

	public UnitInterface getUnit(GameCoordinate gameCoordinate);

	public String toString();
}