package shared.game;

import java.util.List;

import shared.GameCoordinate;
import shared.Player;
import shared.Unittype;

public interface GameReadOnly {

	public Player getHost();

	public Player getClient();
	
	public List<Unit> getUnits();

	public Unittype getUnittype(GameCoordinate gameCoordinate);

}