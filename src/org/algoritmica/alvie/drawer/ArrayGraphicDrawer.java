package org.algoritmica.alvie.drawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import java.util.Vector;

import org.algoritmica.alvie.bean.VisualArrayBean;
import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.graphic.PaintableI;
import org.algoritmica.alvie.graphic.RectangularShape;
import org.algoritmica.alvie.graphic.ShapeAC;
import org.algoritmica.alvie.graphic.Text;
import org.algoritmica.alvie.graphic.VisualNode;
import org.algoritmica.alvie.gui.ViewPanel;
import org.algoritmica.alvie.gui.ViewPanelI;
import org.algoritmica.alvie.information.InformationI;

/*
 * This is the graphical array drawer. Each element has its own color and font. All
 * element shapes are rectangular, but each element has its own height and width. The
 * elements are drawn as rectangles, one after the other, containing the string 
 * representation of the element information. The message drawing method simply draws 
 * the message in the view panel associated with the drawer.
 */
public class ArrayGraphicDrawer<I extends InformationI> extends ArrayDrawerA {
	protected Color[] color;

	protected Font[] font;

	private ShapeAC[] shape;

	protected ViewPanelI viewPanel;

	private Double defaultShapeHeight, defaultShapeWidth;

	public ArrayGraphicDrawer(String name, ViewPanel viewPanel) {
		super(name);
		this.viewPanel = viewPanel;
	}

	public void draw(ArrayI<I> a, VisualArrayBean bean) {
		int n = a.length();
		defaultShapeHeight = bean.getDefaultShapeHeight() != null ? bean.getDefaultShapeHeight() : getDefaultShapeHeight();
		defaultShapeWidth = bean.getDefaultShapeWidth() != null ? bean.getDefaultShapeWidth() : getDefaultShapeWidth();
		color = new Color[n];
		font = new Font[n];
		shape = new ShapeAC[n];
		for (int i = 0; i < n; i++) {
			color[i] = bean.getDefaultColor() != null ? bean.getDefaultColor() : getDefaultColor();
			font[i] = bean.getDefaultFont() != null ? bean.getDefaultFont() : getDefaultFont();
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

	private void draw(int x, int y, ArrayI<I> a) {
		viewPanel.draw(visit(x, y, a));
	}

	public void drawMessage(String message) {
		Text messageText = new Text(message);
		viewPanel.drawMessage(messageText);
	}

	private Vector<PaintableI> visit(int x, int y, ArrayI<I> array) {
		int currentX = x;
		int maxHeight = y;
		Vector<PaintableI> graphicObjectCollection = new Vector<PaintableI>();
		int arrayLength = array.length();
		for (int k = 0; k < arrayLength; k++) {
			VisualNode visualNode = new VisualNode(array.elementAt(k).stringValue(), shape[k], currentX, y);
			visualNode.setFillColor(color[k]);
			visualNode.setFont(font[k]);
			visualNode.setId((int) array.createElementID(k));
			graphicObjectCollection.add(visualNode);
			currentX = currentX + (int) (shape[k].getWidth());
			if (y + shape[k].getHeight() > maxHeight) {
				maxHeight = (int) (y + shape[k].getHeight());
			}
		}
		int viewPanelWidth = viewPanel.getPreferredSize().width;
		int viewPanelHeight = viewPanel.getPreferredSize().height;
		if (currentX > viewPanelWidth) {
			viewPanelWidth = currentX;
		}
		if (maxHeight > viewPanelHeight) {
			viewPanelHeight = maxHeight;
		}
		viewPanel.setPreferredSize(new Dimension(viewPanelWidth, viewPanelHeight));
		return graphicObjectCollection;
	}
}
