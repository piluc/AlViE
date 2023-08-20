package org.algoritmica.alvie.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;

public abstract class VisualGeometricObjectA extends PaintableAC {
	protected Color color;

	protected Font font;

	protected float lineSize;

	protected Point2D origin;

	protected String text;

	public Color getColor() {
		return color;
	}

	public void setColor(Color c) {
		color = c;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public float getLineThickness() {
		return lineSize;
	}

	public void setLineThickness(float size) {
		this.lineSize = size;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
