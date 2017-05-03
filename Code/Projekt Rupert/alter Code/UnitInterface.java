package shared.game;

import java.io.Serializable;

import shared.resource.Color;
import shared.resource.Unittype;

public interface UnitInterface extends Serializable {

	public void move(GameCoordinate gameCoordinate);

	public boolean hurt(int value);
	
	public int getIdentifier();

	public Unittype getUnittype();

	public GameCoordinate getGameCoordinate();

	public Player getPlayer();

	public Color getColor();

	public int getHealth();

	public boolean isDead();

	public boolean isDeffending();

	public void setDeffending(boolean isDeffending);

	public String toString();

	public void heal(int value);
}