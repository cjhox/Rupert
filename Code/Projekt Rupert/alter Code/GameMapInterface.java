package shared.game;

import java.io.Serializable;
import java.util.List;



public interface GameMapInterface extends Serializable {

	public static final int MAP_HEIGHT = 14;
	public static final int MAP_WIDTH = 20;
	public static final int SF_HEIGHT = 10;
	public static final int SF_WIDTH = 4;

	public FieldInterface[][] getMap();

	public FieldInterface getField(int x, int y);

	public FieldInterface getField(GameCoordinate gameCoordinate);
	
	public List<? extends UnitInterface> getUnits();

	public UnitInterface getUnit(GameCoordinate gameCoordinate);
	
	public int getHeight();
	
	public int getWidth();	
	
	public String toString();
}