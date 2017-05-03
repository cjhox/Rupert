package shared.gameround;

import java.awt.image.BufferedImage;

import shared.Action.PlaceAction;
import shared.GameCoordinate;
import shared.Player;
import shared.Unittype;

public class PlaceDraw extends Draw {

	private static final long	serialVersionUID	= 5141792581102676373L;
	private static int			nextId				= 0;

	private Unittype			unittype;
	private Player				player;

	public PlaceDraw(GameCoordinate target, PlaceAction action, Unittype unittype, Player player) {
		super(nextId++, target, target, action);
		this.unittype = unittype;
		this.player = player;
	}

	@Override
	public PlaceAction getAction() {
		return (PlaceAction) action;
	}

	public Unittype getUnittype() {
		return unittype;
	}

	public Player getPlayer() {
		return player;
	}

	public BufferedImage getImage() {
		return unittype.getImage(player.getColor());
	}

	@Override
	public String toString() {
		return getNumber() + ": " + action + " " + unittype + " --> " + getTarget();
	}
	
	
}
