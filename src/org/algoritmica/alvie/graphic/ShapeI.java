package org.algoritmica.alvie.graphic;

import java.awt.Color;

public interface ShapeI extends PositionedI {

	public Color getFillColor();

	public void setFillColor(Color color);

	public void setLineType(LineType type);

	public void setSize(double width, double height);

	public Color getLineColor();

	public void setLineColor(Color color);
}
