package org.algoritmica.alvie.drawer;

import java.awt.Color;
import java.awt.Font;

import org.algoritmica.alvie.graphic.LineType;

/*
 * This interface includes all functionalities of all possible kinds of drawers. 
 * It is introduced in order to allow us to use a common name when loading and 
 * drawing a data structure.
 */
public interface DrawerI {

	public void drawMessage(String m);

	public Color getDefaultColor();

	public Font getDefaultFont();

	public Color getDefaultLineColor();

	public Float getDefaultLineThickness();

	public LineType getDefaultLineType();

	public String getDefaultShape();

	public Double getDefaultShapeHeight();

	public Double getDefaultShapeWidth();

	public String getName();

	public int getOriginX();

	public int getOriginY();

	public void setDefaultColor(Color color);

	public void setDefaultFont(Font font);

	public void setDefaultLineColor(Color defaultLineColor);

	public void setDefaultLineThickness(Float defaultLineThickness);

	public void setDefaultLineType(LineType defaultLineType);

	public void setDefaultShape(String font);

	public void setDefaultShapeHeight(Double height);

	public void setDefaultShapeWidth(Double width);

	public void setOriginX(int originX);

	public void setOriginY(int originY);
}
