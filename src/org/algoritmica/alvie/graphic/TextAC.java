package org.algoritmica.alvie.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class TextAC implements TextI {
	protected Color textColor;

	protected Font textFont;

	protected Point2D origin;

	public abstract String toString();

	public void paint(Graphics2D graphics) {
		Color tempColor = graphics.getColor();
		Font tempFont = graphics.getFont();
		graphics.setColor(getTextColor());
		graphics.setFont(getFont());
		int x = (int) this.origin.getX();
		int y = (int) this.origin.getY();
		graphics.drawString(toString(), x, y);
		graphics.setFont(tempFont);
		graphics.setColor(tempColor);
		graphics.getFontRenderContext();
	}

	public Rectangle2D getBoundingBox(Graphics2D graphics) {
		FontRenderContext render = graphics.getFontRenderContext();
		TextLayout layout = new TextLayout(toString(), getFont(), render);
		return layout.getBounds();
	}

	public Font getFont() {
		return textFont;
	}

	public void setFont(Font font) {
		textFont = font;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color color) {
		textColor = color;
	}

	public Point2D getOrigin() {
		return origin;
	}

	public void setOrigin(Point2D origin) {
		this.origin = origin;
	}
}
