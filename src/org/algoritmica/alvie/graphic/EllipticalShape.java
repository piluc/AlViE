package org.algoritmica.alvie.graphic;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class EllipticalShape extends ShapeAC {

	public EllipticalShape() {
	}

	public EllipticalShape(Ellipse2D ellipse) {
		super(ellipse.getX(), ellipse.getY(), ellipse.getWidth(), ellipse.getHeight());
	}

	public EllipticalShape(Double width, Double height) {
		super(width, height);
	}

	public EllipticalShape(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	public Shape getShape() {
		return new Ellipse2D.Double(origin.getX(), origin.getY() - height, width, height);
	}

	public Shape getInnerShape(int borderDeltaX, int borderDeltaY) {
		return new Ellipse2D.Double(origin.getX() + borderDeltaX, origin.getY() + borderDeltaY - height, width - 2 * borderDeltaX, height - 2 * borderDeltaY);
	}
}
