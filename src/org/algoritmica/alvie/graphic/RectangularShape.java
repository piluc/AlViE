package org.algoritmica.alvie.graphic;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class RectangularShape extends ShapeAC {
	public RectangularShape() {
	}

	public RectangularShape(Rectangle2D rectangle) {
		super(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
	}

	public RectangularShape(Double width, Double height) {
		super(width, height);
	}

	public RectangularShape(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	public Shape getShape() {
		return new Rectangle2D.Double(origin.getX(), origin.getY() - height, width, height);
	}

	public Shape getInnerShape(int borderDeltaX, int borderDeltaY) {
		return new Rectangle2D.Double(origin.getX() + borderDeltaX, origin.getY() + borderDeltaY - height, width - 2 * borderDeltaX, height - 2 * borderDeltaY);
	}
}
