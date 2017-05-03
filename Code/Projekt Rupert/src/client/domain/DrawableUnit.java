package client.domain;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import client.resources.DrawCoordinate;
import client.resources.GameListener;
import client.resources.Sprite;
import client.resources.UnitListenerFactoryInterface;
import shared.GameCoordinate;
import shared.Player;
import shared.Unittype;
import shared.game.Unit;

/**
 * Eine auf der Map dargestellte Unit.
 */
public class DrawableUnit extends Unit implements Sprite {

	private static final long	serialVersionUID	= -1343474203806664085L;

	private DrawCoordinate		drawCoordinate;
	private List<GameListener>	actionListeners		= new LinkedList<GameListener>();

	protected DrawableUnit() {
		super();
	}

	public DrawableUnit(int identifier, Unittype unittype, Player player, GameCoordinate gameCoordinate,
			UnitListenerFactoryInterface unitListenerFactory) {
		super(identifier, unittype, player);
		addActionListener(unitListenerFactory.getInstance(unittype, player));
		drawCoordinate = DrawCoordinate.getDrawCoordinate(gameCoordinate, GameCoordinate.STEP_WIDTH,
				GameCoordinate.STEP_HEIGHT);
	}

	public DrawableUnit(Unit unit, GameCoordinate gameCoordinate, UnitListenerFactoryInterface unitListener) {
		this(unit.getIdentifier(), unit.getUnittype(), unit.getPlayer(), gameCoordinate, unitListener);
	}

	public void move(GameCoordinate gameCoordinate) {
		drawCoordinate = DrawCoordinate.getDrawCoordinate(gameCoordinate, GameCoordinate.STEP_WIDTH,
				GameCoordinate.STEP_HEIGHT);
	}

	@Override
	public List<GameListener> getActionListeners() {
		return actionListeners;
	}

	@Override
	public DrawCoordinate getDrawCoordinate() {
		return drawCoordinate;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(unittype.getImage(player.getColor()), drawCoordinate.getAX(), drawCoordinate.getAY(),
				GameCoordinate.STEP_WIDTH, GameCoordinate.STEP_HEIGHT, null);
	}

	@Override
	public String toString() {
		return "DrawableUnit [drawCoordinate=" + drawCoordinate + ", actionListeners=" + actionListeners + ", health="
				+ health + ", identifier=" + identifier + ", isDeffending=" + isDeffending + ", player=" + player
				+ ", unittype=" + unittype + "]";
	}
}