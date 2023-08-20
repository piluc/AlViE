package org.algoritmica.alvie.drawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import java.util.Vector;

import org.algoritmica.alvie.bean.VisualPseudocodeBean;
import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.graphic.PaintableI;
import org.algoritmica.alvie.graphic.RectangularShape;
import org.algoritmica.alvie.graphic.Text;
import org.algoritmica.alvie.graphic.VisualPseudocodeLine;
import org.algoritmica.alvie.gui.ViewPanel;
import org.algoritmica.alvie.gui.ViewPanelI;
import org.algoritmica.alvie.information.StringInformation;

/*
 * This is the pseudo-code array drawer. Each element has its own color and font. All
 * element shapes are rectangular, but each element has its own height and width. The
 * elements are drawn as rectangles, one below the other, containing the code line. 
 * The message drawing method simply draws the message in the view panel associated 
 * with the drawer.
 */
public class PseudocodeGraphicDrawer extends ArrayDrawerA {
	protected Color[] color;

	protected Font[] font;

	private RectangularShape[] shape;

	protected ViewPanelI viewPanel;

	private Double defaultShapeHeight, defaultShapeWidth;

	public PseudocodeGraphicDrawer(String name, ViewPanel vp) {
		super(name);
		this.viewPanel = vp;
	}

	public void draw(ArrayI<StringInformation> a, VisualPseudocodeBean bean) {
		int n = a.length();
		defaultShapeHeight = bean.getDefaultShapeHeight();
		defaultShapeWidth = bean.getDefaultShapeWidth();
		color = new Color[n];
		font = new Font[n];
		shape = new RectangularShape[n];
		for (int i = 0; i < n; i++) {
			color[i] = bean.getDefaultColor();
			font[i] = bean.getDefaultFont();
			shape[i] = new RectangularShape(defaultShapeWidth, defaultShapeHeight);
		}
		Map<Integer, Color> colorMap = bean.getElementColorCollection();
		Map<Integer, Font> fontMap = bean.getElementFontCollection();
		Map<Integer, Double> shapeHeightMap = bean.getElementShapeHeightCollection();
		Map<Integer, Double> shapeWidthMap = bean.getElementShapeWidthCollection();
		for (Integer i : colorMap.keySet()) {
			color[i] = colorMap.get(i);
			font[i] = fontMap.get(i);
			shape[i] = new RectangularShape(shapeWidthMap.get(i), shapeHeightMap.get(i));
		}
		setOriginX(!isMultiPane() ? bean.getOriginX() : getDefaultOriginX());
		setOriginY(!isMultiPane() ? bean.getOriginY() : getDefaultOriginY());
		draw(getOriginX(), getOriginY(), a);
	}

	private void draw(int x, int y, ArrayI<StringInformation> array) {
		viewPanel.draw(visit(x, y, array));
	}

	public void drawMessage(String message) {
		Text messageText = new Text(message);
		viewPanel.drawMessage(messageText);
	}

	private Vector<PaintableI> visit(int x, int y, ArrayI<StringInformation> array) {
		Vector<PaintableI> graphicObjectCollection = new Vector<PaintableI>();
		int maxWidth = 0;
		int currentY = y;
		int arrayLength = array.length();
		for (int k = 0; k < arrayLength - 1; k++) {
			VisualPseudocodeLine visualPseudocodeLine = new VisualPseudocodeLine(array.elementAt(k).stringValue(), shape[k], x, currentY);
			visualPseudocodeLine.setFont(font[k]);
			visualPseudocodeLine.setFillColor(color[k]);
			visualPseudocodeLine.setId(array.createElementID(k));
			graphicObjectCollection.add(visualPseudocodeLine);
			currentY = currentY + (int) (shape[k + 1].getHeight());
			if (shape[k].getWidth() > maxWidth) {
				maxWidth = (int) (shape[k].getWidth());
			}
		}
		VisualPseudocodeLine visualPseudocodeLine = new VisualPseudocodeLine(array.elementAt(arrayLength - 1).stringValue(), shape[arrayLength - 1], x, currentY);
		visualPseudocodeLine.setFont(font[arrayLength - 1]);
		visualPseudocodeLine.setFillColor(color[arrayLength - 1]);
		visualPseudocodeLine.setId(array.createElementID(arrayLength - 1));
		graphicObjectCollection.add(visualPseudocodeLine);
		if (shape[arrayLength - 1].getWidth() > maxWidth) {
			maxWidth = (int) (shape[arrayLength - 1].getWidth());
		}
		int viewPanelWidth = viewPanel.getPreferredSize().width;
		int viewPanelHeight = viewPanel.getPreferredSize().height;
		if (maxWidth > viewPanelWidth) {
			viewPanelWidth = maxWidth;
		}
		if (currentY > viewPanelHeight) {
			viewPanelHeight = currentY;
		}
		viewPanel.setPreferredSize(new Dimension(viewPanelWidth, viewPanelHeight));
		return graphicObjectCollection;
	}
}
