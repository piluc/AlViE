package org.algoritmica.alvie.graphic;

import java.awt.geom.Point2D;

public interface PositionedI extends PaintableI {

	public Point2D getOrigin();

	public void setOrigin(Point2D origin);
}
