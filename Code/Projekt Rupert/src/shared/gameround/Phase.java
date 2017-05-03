package shared.gameround;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import shared.Player;

/**
 * Die Phase zeigt dem Client bzw. dem Server an, was für Inputs erwartet
 * werden.
 */

public abstract class Phase<T extends Draw> implements Serializable {

	private static final long	serialVersionUID	= -9026107027965730807L;

	private Player				player;
	protected List<T>			draws				= new ArrayList<T>();

	public Phase(Player player) {
		super();
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public List<T> getDraws() {
		return draws;
	}

	// pre: (Player of the Unit standing on d.getSource()).equals(player)
	public boolean addDraw(T draw) {
		for (T d : draws) {
			if (d.getSource().equals(draw.getSource())) {
				return false;
			}
		}
		return draws.add(draw);
	}

	public boolean removeDraw(T draw) {
		return draws.remove(draw);
	}

	@Override
	public String toString() {
		return "Phase [player=" + player + ", draws=" + draws + "]";
	}

	public boolean moveUp(int selectedIndex) {
		if (selectedIndex > 0 && selectedIndex < draws.size()) {
			T temp = draws.get(selectedIndex - 1);
			draws.set(selectedIndex - 1, draws.get(selectedIndex));
			draws.set(selectedIndex, temp);
			return true;
		}
		return false;
	}

	public boolean moveDown(int selectedIndex) {
		if (selectedIndex + 1 < draws.size() && selectedIndex >= 0) {
			T temp = draws.get(selectedIndex);
			draws.set(selectedIndex, draws.get(selectedIndex + 1));
			draws.set(selectedIndex + 1, temp);
			return true;
		}
		return false;
	}

	public boolean delete(int selectedIndex) {
		if (selectedIndex >= 0 && selectedIndex < draws.size()) {
			draws.remove(selectedIndex);
			return true;
		}
		return false;
	}
}