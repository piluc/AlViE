package org.algoritmica.alvie.graphic;

import java.awt.Color;

public interface CurveI extends PaintableI {

	public Color getLineColor();

	public void setLineColor(Color color);

	public LineType getLineType();

	public float getLineThickness();

	public void setLineType(LineType type);

	public void setLineThickness(float thickness);
}
