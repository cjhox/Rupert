package shared;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.activemq.console.Main;

/**
 * Der Enum Unittype stellt die verschiedenen Arten von Einheiten mit ihren
 * jeweiligen Werden zur Verfügung. Im Spiel wurden drei verschiedene Einheiten
 * Implementiert, dies ist jedoch einfach erweiterbar.
 */
public enum Unittype implements Serializable {
	SWORDSMAN(100, 50, 80, 1, 2, "swordsman", 60), 
	ARCHER(80, 40, 50, 6, 2, "archer", 50),
	KNIGHT(150, 60, 30, 2, 5, "knight", 20), 
	EMPTY(0, 0, 0, 0, 0, null, 0);

	private int								maxHealth;
	private int								damage;
	private int								defence;
	private int								range;
	private int								speed;
	private List<Entry<Unittype, Integer>>	adventage;
	private BufferedImage					imageWhite	= null;
	private BufferedImage					imageBlue	= null;
	private BufferedImage					imageRed	= null;
	private int								healing;

	public int getHealing() {
		return healing;
	}

	private Unittype(int maxHealth, int damage, int defence, int range, int speed, String path, int healing) {
		this.maxHealth = maxHealth;
		this.damage = damage;
		this.defence = defence;
		this.range = range;
		this.speed = speed;
		this.healing = healing;
		adventage = new ArrayList<>();

		if (path != null) {
			try {
				imageWhite = ImageIO.read(Main.class.getResourceAsStream("/" + path + "_white.png"));
				imageBlue = ImageIO.read(Main.class.getResourceAsStream("/" + path + "_blue.png"));
				imageRed = ImageIO.read(Main.class.getResourceAsStream("/" + path + "_red.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// pre: to != null && value != null
	// pre: !adventage.containsKey(to);
	// post: adventage.containsKey(to);
	// post: adventage.get(to) == value;
	// post: !adventage.isEmpty();
	public void addAdventage(Unittype to, int value) {
		adventage.add(new SimpleEntry<Unittype, Integer>(to, value));
	}

	public double getAdventage(Unittype to) {
		for (Entry<Unittype, Integer> entry : adventage) {
			if (entry.getKey().equals(to)) {
				return ((100.0 + entry.getValue()) / 100.0);
			}
		}
		return 1;
	}

	public List<Entry<Unittype, Integer>> getAdventages() {
		return adventage;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getAttackDamage() {
		return damage;
	}

	public int getDefence() {
		return defence;
	}

	public int getRange() {
		return range;
	}

	public int getSpeed() {
		return speed;
	}

	public BufferedImage getImage(Color c) {
		switch (c) {
		case BLACK:
		case GREEN:
		case WHITE:
			return imageWhite;
		case BLUE:
			return imageBlue;
		case RED:
			return imageRed;
		default:
			return imageWhite;
		}
	}
}
