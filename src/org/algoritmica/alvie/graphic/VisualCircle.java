package org.algoritmica.alvie.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class VisualCircle extends VisualGeometricObjectA {
	protected int radius;

	public VisualCircle(int x, int y, int r) {
		origin = new Point(x, y);
		radius = r;
	}

	public void paint(Graphics2D graphics) {
		Color c = graphics.getColor();
		graphics.setColor(color);
		graphics.fillOval((int) origin.getX(), (int) origin.getY(), 2 * radius, 2 * radius);
		graphics.setColor(c);
	}

	public Rectangle2D getBoundingBox(Graphics2D graphics) {
		return new Rectangle2D.Double((int) origin.getX(), (int) origin.getY(), 2 * radius, 2 * radius);
	}
}
