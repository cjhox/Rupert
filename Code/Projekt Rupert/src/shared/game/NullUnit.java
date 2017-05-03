package shared.game;

import shared.Color;
import shared.Player;
import shared.Unittype;


public class NullUnit extends Unit {

	private static final long	serialVersionUID	= 7851930670580921449L;

	@Override
	public Color getColor() {
		return null;
	}

	@Override
	public int getHealing() {
		return 0;
	}

	@Override
	public int getHealth() {
		return 0;
	}

	@Override
	public int getIdentifier() {
		return -1;
	}

	@Override
	public Player getPlayer() {
		return null;
	}

	@Override
	public Unittype getUnittype() {
		return Unittype.EMPTY;
	}

	@Override
	public void heal(int value) {
	}

	@Override
	public boolean hurt(int value) {
		return true;
	}

	@Override
	public boolean isDead() {
		return true;
	}

	@Override
	public void setDeffending(boolean isDeffending) {
	}

	@Override
	public String toString() {
		return "NullUnit";
	}
}
