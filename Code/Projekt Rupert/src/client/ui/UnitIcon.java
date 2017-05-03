package client.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.activemq.console.Main;

import client.resources.DrawCoordinate;
import client.resources.Drawable;
import shared.GameCoordinate;

/**
 * Klasse für das Zeichnen des HealIcons auf eine Unit, wenn diese heilt.
 */
public class UnitIcon implements Drawable {

	private GameCoordinate			gameCoordinate;
	private DrawCoordinate			drawCoordinate;	
	private BufferedImage			image;

	private static BufferedImage	shieldImage;	
	private static BufferedImage	healImage;

	static {
		try {
			shieldImage = ImageIO.read(Main.class.getResourceAsStream("/shield.png"));
			healImage = ImageIO.read(Main.class.getResourceAsStream("/heal.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public UnitIcon(GameCoordinate source, BufferedImage image) {
		gameCoordinate = source;
		this.image = image;
		drawCoordinate = DrawCoordinate.getDrawCoordinate(gameCoordinate, GameCoordinate.STEP_WIDTH,
				GameCoordinate.STEP_HEIGHT);
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, drawCoordinate.getAX(), drawCoordinate.getAY(), GameCoordinate.STEP_WIDTH,
				GameCoordinate.STEP_HEIGHT, this);
	}

	
	public static BufferedImage getShieldImage() {
		return shieldImage;
	}

	
	public static BufferedImage getHealImage() {
		return healImage;
	}

}
