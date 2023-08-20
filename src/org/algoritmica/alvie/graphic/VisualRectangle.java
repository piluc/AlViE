package org.algoritmica.alvie.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class VisualRectangle extends VisualGeometricObjectA {
	protected int width;

	protected int height;

	public VisualRectangle(int x, int y, int w, int h) {
		origin = new Point(x, y);
		width = w;
		height = h;
	}

	public void paint(Graphics2D graphics) {
		Color c = graphics.getColor();
		graphics.setColor(color);
		graphics.fillRect((int) origin.getX(), (int) origin.getY(), width, height);
		graphics.setColor(c);
	}

	public Rectangle2D getBoundingBox(Graphics2D graphics) {
		return new Rectangle2D.Double((int) origin.getX(), (int) origin.getY(), width, height);
	}
}
