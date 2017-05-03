package client.resources;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

public interface Drawable extends ImageObserver {

	public abstract void draw(Graphics g);
}
