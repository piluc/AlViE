package org.algoritmica.alvie.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class VisualSegment extends VisualGeometricObjectA {
	protected Point2D destination;

	public VisualSegment(int x1, int y1, int x2, int y2) {
		origin = new Point(x1, y1);
		destination = new Point(x2, y2);
	}

	public void paint(Graphics2D graphics) {
		Color c = graphics.getColor();
		graphics.setColor(color);
		Stroke currentStroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(lineSize));
		graphics.drawLine((int) origin.getX(), (int) origin.getY(), (int) destination.getX(), (int) destination.getY());
		graphics.setStroke(currentStroke);
		graphics.setColor(c);
	}

	public Rectangle2D getBoundingBox(Graphics2D graphics) {
		return new Rectangle2D.Double((int) origin.getX(), (int) origin.getY(), Math.abs(origin.getX() - destination.getX()), Math.abs(origin.getY() - destination.getY()));
	}
}
