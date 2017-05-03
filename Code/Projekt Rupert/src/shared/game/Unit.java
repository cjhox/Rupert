package shared.game;

import java.io.Serializable;

import shared.Color;
import shared.Player;
import shared.Unittype;
import shared.gameround.PlaceDraw;

/**
 * Serverseitige Klasse für die Units. Oberklasse für DrawableUnit des Clients.
 */
public class Unit implements Serializable {

	private static final long	serialVersionUID	= -5258784038613343709L;

	protected Health			health;
	protected int				identifier;
	protected boolean			isDeffending		= false;
	protected Player			player;
	protected Unittype			unittype;

	protected Unit() {
	}

	public Unit(int identifier, Unittype unittype, Player player) {
		this.identifier = identifier;
		this.unittype = unittype;
		health = new Health(unittype.getMaxHealth());
		this.player = player;
	}

	public Unit(PlaceDraw placeDraw) {
		this(placeDraw.getNumber(), placeDraw.getUnittype(), placeDraw.getPlayer());
	}

	public Color getColor() {
		return player.getColor();
	}

	public int getHealing() {
		return unittype.getHealing();
	}

	public int getHealth() {
		return health.getHealth();
	}

	public int getIdentifier() {
		return identifier;
	}

	public Player getPlayer() {
		return player;
	}

	public Unittype getUnittype() {
		return unittype;
	}

	public void heal(int value) {
		health.heal(value);
	}

	public boolean hurt(int value) {
		if (isDeffending) {
			value *= 1.0 - (unittype.getDefence() / 100.0);
		}

		health.hurt(value);
		return health.isDead();
	}

	public boolean isDead() {
		return health.isDead();
	}

	public void setDeffending(boolean isDeffending) {
		this.isDeffending = isDeffending;
	}

	@Override
	public String toString() {
		return "Unit [health=" + health + ", identifier=" + identifier + ", isDeffending=" + isDeffending + ", player="
				+ player + ", unittype=" + unittype + "]";
	}
}
