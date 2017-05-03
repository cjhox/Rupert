package shared.game;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import shared.GameCoordinate;
import shared.Player;
import shared.Unittype;
import shared.com.UpdateHealthData;
import shared.gameround.MoveDraw;
import shared.gameround.PlaceDraw;

/**
 * Klasse zum Serverseitigen Speichern des Games. Analog zu DrawableGame des
 * Clients.
 */
public class Game extends Observable implements Serializable, GameReadOnly {

	private static final long	serialVersionUID	= -2544772964732228620L;

	protected Player			host;
	protected Player			client;

	private GameMap				gameMap;
	private Set<GameCoordinate>	unitPositions;

	public Game(Player host, Player client) {
		super();
		this.host = host;
		this.client = client;
		unitPositions = new HashSet<GameCoordinate>();
	}

	// kann in DrawableGame nicht verwendet werden
	public void initializeMap() {
		gameMap = new GameMap(GameMap.MAP_HEIGHT, GameMap.MAP_WIDTH);
		gameMap.initializeFields();
	}

	// wird überschrieben
	public boolean move(MoveDraw moveDraw) {
		if (unitPositions.contains(moveDraw.getSource())) {
			if (unitPositions.add(moveDraw.getTarget())) {
				unitPositions.remove(moveDraw.getSource());
				return getGameMap().move(moveDraw);
			}
		}
		return false;
	}

	// wird auch in DrawableGame verwendet
	public void fight(UpdateHealthData data) {
		getGameMap().fight(data);
	}

	// kann in DrawableGame nicht verwendet werden
	public void resetDeffending() {
		for (GameCoordinate gameCoordinate : unitPositions) {
			getUnit(gameCoordinate).setDeffending(false);
		}
	}

	// muss überschrieben werden
	public boolean place(PlaceDraw placeDraw) {
		if (getGameMap().place(placeDraw)) {
			unitPositions.add(placeDraw.getTarget());
			return true;
		}
		return false;
	}

	public boolean isTargetAllowed(GameCoordinate target, Player player) {
		return getGameMap().isTargetAllowed(target, player);
	}

	// kann auch in DrawableGame verwendet werden
	@Override
	public Player getHost() {
		return host;
	}

	// kann auch in DrawableGame verwendet werden
	@Override
	public Player getClient() {
		return client;
	}

	// wird überschrieben
	public GameMap getGameMap() {
		return gameMap;
	}

	// kann in DrawableGame nicht verwendet werden
	@Override
	public List<Unit> getUnits() {
		List<Unit> units = new LinkedList<Unit>();
		for (GameCoordinate gameCoordinate : unitPositions) {
			Unit unit = getUnit(gameCoordinate);
			if (!unit.isDead()) {
				units.add(getUnit(gameCoordinate));
			}
		}
		return units;
	}

	private Unit getUnit(GameCoordinate gameCoordinate) {
		return getGameMap().getUnit(gameCoordinate);
	}

	@Override
	public Unittype getUnittype(GameCoordinate gameCoordinate) {
		return getGameMap().getUnittype(gameCoordinate);
	}

	@Override
	public String toString() {
		return "Game [host=" + host + ", client=" + client + ", gameMap=" + gameMap + ", unitPositions="
				+ unitPositions + "]";
	}
}