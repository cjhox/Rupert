package client.domain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.activemq.console.Main;

import client.resources.DrawCoordinate;
import client.resources.GameListener;
import client.resources.Sprite;
import shared.GameCoordinate;

/**
 * Klasse für die Darstellung der Zielfelder.
 */
public class AreaField implements Sprite {

	private BufferedImage image;
	private DrawCoordinate	drawCoordinate;
	private List<GameListener>	actionListeners		= new LinkedList<GameListener>();

	public AreaField(GameCoordinate gameCoordinate,
			GameListener actionListener) {
		addActionListener(actionListener);
		try {
			image = ImageIO.read(Main.class.getResourceAsStream("/gray.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		drawCoordinate = DrawCoordinate.getDrawCoordinate(gameCoordinate,
				GameCoordinate.STEP_WIDTH, GameCoordinate.STEP_HEIGHT);
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
		g.drawImage(image, drawCoordinate.getAX(),
				drawCoordinate.getAY(), drawCoordinate.getBX(),
				drawCoordinate.getBY(), 0, 0, GameCoordinate.STEP_WIDTH,
				GameCoordinate.STEP_HEIGHT, this);

	}
}
