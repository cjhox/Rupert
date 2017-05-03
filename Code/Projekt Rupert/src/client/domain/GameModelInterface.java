package client.domain;

import java.util.Observer;

import shared.gameround.Draw;
import shared.gameround.Phase;

public interface GameModelInterface {

	public Phase<? extends Draw> getPhase();

	public default Draw getDrawByIndex(int index) {
		return getPhase().getDraws().get(index);
	}

	public default int getSizeOfPhase() {
		if (getPhase() != null && getPhase().getDraws() != null) {
			return getPhase().getDraws().size();
		}
		return 0;
	}

	public void addObserver(Observer o);

	public boolean moveUp(int selectedIndex);

	public boolean moveDown(int selectedIndex);

	public boolean delete(int selectedIndex);
}