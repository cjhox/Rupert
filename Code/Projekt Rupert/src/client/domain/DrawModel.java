package client.domain;

import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;

import shared.gameround.Draw;

public class DrawModel extends AbstractListModel<Draw> implements Observer {

	private static final long	serialVersionUID	= 3170739847963562000L;
	private GameModelInterface	model;

	public DrawModel(GameModelInterface model) {
		this.model = model;
		model.addObserver(this);
	}

	@Override
	public Draw getElementAt(int index) {
		return model.getDrawByIndex(index);
	}

	@Override
	public int getSize() {
		if (model.getPhase() != null && model.getPhase().getDraws() != null) {
			return model.getSizeOfPhase();
		}
		return 0;

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		fireContentsChanged(this, 0, getSize());
	}

	public boolean moveUp(int selectedIndex) {
		return model.moveUp(selectedIndex);
	}

	public boolean moveDown(int selectedIndex) {
		return model.moveDown(selectedIndex);
	}

	public boolean delete(int selectedIndex) {
		return model.delete(selectedIndex);
	}
}