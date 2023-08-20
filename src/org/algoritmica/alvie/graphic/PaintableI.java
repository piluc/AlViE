package org.algoritmica.alvie.graphic;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public interface PaintableI {

	public void paint(Graphics2D graphics);

	public Rectangle2D getBoundingBox(Graphics2D graphics);
}
