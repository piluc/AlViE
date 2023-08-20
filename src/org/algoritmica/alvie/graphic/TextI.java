package org.algoritmica.alvie.graphic;

import java.awt.Color;
import java.awt.Font;

public interface TextI extends PositionedI {

	public Font getFont();

	public void setFont(Font font);

	public Color getTextColor();

	public void setTextColor(Color color);
}
