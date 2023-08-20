package org.algoritmica.alvie.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class VisualPoint extends VisualGeometricObjectA {

	private final int RADIUS = 3;

	public VisualPoint(int x, int y) {
		origin = new Point(x, y);
	}

	public void paint(Graphics2D graphics) {
		Color c = graphics.getColor();
		graphics.setColor(color);
		graphics.fillOval((int) origin.getX() - RADIUS, (int) origin.getY() - RADIUS, 2 * RADIUS, 2 * RADIUS);
		graphics.setColor(c);
	}

	public Rectangle2D getBoundingBox(Graphics2D graphics) {
		return new Rectangle2D.Double((int) origin.getX() - RADIUS, (int) origin.getY() - RADIUS, 2 * RADIUS, 2 * RADIUS);
	}
}
