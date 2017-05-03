package shared.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.activemq.console.Main;

import shared.Unittype;

/**
 * Der Enum Fieldtype stellt die verschiendenen Feldtypen zur verfügung. Aus
 * Zeitgründen wurde nur Grass im Spiel verwendet, dies kann jedoch einfach
 * erweitert werden.
 */
public enum Fieldtype implements Serializable {
	GRASS("grass"), STEPPE("grass"), FOREST("grass"), HILL("grass"), RIVER("grass");

	// inv: adventage.size() >= old adventage.size();
	/**
	 * Die Map andvantage listet auf, welcher Unittype auf diesem Feld welchen
	 * Bonus bekommt.
	 */
	private Map<Unittype, Unittype>	adventage;
	private BufferedImage			image;
	private BufferedImage			imageHL;

	// post: adventage != null;
	private Fieldtype(String path) {
		adventage = new HashMap<>();

		try {
			image = ImageIO.read(Main.class.getResourceAsStream("/" + path + ".png"));
			imageHL = ImageIO.read(Main.class.getResourceAsStream("/" + path + "_hl.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// pre: to != null && value != null
	// pre: !adventage.containsKey(to);
	// post: adventage.containsKey(to);
	// post: adventage.get(to) == value;
	// post: !adventage.isEmpty();
	public void addAdventage(Unittype to, Unittype value) {
		adventage.put(to, value);
	}

	// pre: adventage.containsKey(to);
	public Unittype getAdventage(Unittype to) {
		return adventage.get(to);
	}

	public BufferedImage getImage() {
		return image;
	}

	public BufferedImage getImageHL() {
		return imageHL;
	}
}