package client.domain;

import java.awt.Graphics;

import client.resources.Coordinate;
import client.resources.DrawCoordinate;
import client.resources.GameListener;
import shared.Color;
import shared.GameCoordinate;
import shared.Player;
import shared.Unittype;

public class DrawableNullUnit extends DrawableUnit {

	private static final long	serialVersionUID	= -1039500755335273873L;

	@Override
	public boolean addActionListener(GameListener actionListener) {
		return true;
	}

	@Override
	public void doClick(GameCoordinate gameCoordinate) {
	}

	@Override
	public void draw(Graphics g) {
	}

	@Override
	public DrawCoordinate getDrawCoordinate() {
		return null;
	}

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
	public boolean isClicked(Coordinate coordinate) {
		return false;
	}

	@Override
	public boolean isDead() {
		return true;
	}

	@Override
	public boolean removeActionListener(GameListener actionListener) {
		return true;
	}

	@Override
	public void setDeffending(boolean isDeffending) {
	}

	@Override
	public String toString() {
		return "DrawableNullUnit";
	}
}
