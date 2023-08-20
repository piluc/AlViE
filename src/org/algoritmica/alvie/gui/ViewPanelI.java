package org.algoritmica.alvie.gui;

import java.awt.Dimension;
import java.util.Vector;

import org.algoritmica.alvie.graphic.PaintableI;
import org.algoritmica.alvie.graphic.PositionedI;

public interface ViewPanelI {

	public void draw(Vector<PaintableI> items);

	public void drawMessage(PositionedI message);

	public Dimension getPreferredSize();

	public void setPreferredSize(Dimension d);
}
