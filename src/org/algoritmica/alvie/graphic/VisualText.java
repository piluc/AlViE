package org.algoritmica.alvie.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class VisualText extends VisualGeometricObjectA {
	public VisualText(String t, int x, int y) {
		text = t;
		origin = new Point(x, y);
	}

	public void paint(Graphics2D graphics) {
		Color c = graphics.getColor();
		Font f = graphics.getFont();
		graphics.setColor(color);
		graphics.setFont(font);
		graphics.drawString(text, (int) origin.getX(), (int) origin.getY());
		graphics.setColor(c);
		graphics.setFont(f);
	}

	public Rectangle2D getBoundingBox(Graphics2D graphics) {
		return new Rectangle2D.Double((int) origin.getX(), (int) origin.getY(), 10, 10);
	}
}
