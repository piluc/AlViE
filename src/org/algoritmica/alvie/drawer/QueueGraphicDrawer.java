package org.algoritmica.alvie.drawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import java.util.Vector;

import org.algoritmica.alvie.bean.VisualQueueBean;
import org.algoritmica.alvie.datastructure.QueueI;
import org.algoritmica.alvie.graphic.PaintableI;
import org.algoritmica.alvie.graphic.RectangularShape;
import org.algoritmica.alvie.graphic.ShapeAC;
import org.algoritmica.alvie.graphic.Text;
import org.algoritmica.alvie.graphic.VisualNode;
import org.algoritmica.alvie.gui.ViewPanel;
import org.algoritmica.alvie.gui.ViewPanelI;
import org.algoritmica.alvie.information.InformationI;

/*
 * This is the graphical queue drawer. Each element has its own color and font. All
 * element shapes are rectangular, but each element has its own height and width. The
 * elements are drawn as rectangles, one after the other (starting from the head of the queue), 
 * containing the string 
 * representation of the element information. The message drawing method simply draws 
 * the message in the view panel associated with the drawer.
 */
public class QueueGraphicDrawer<I extends InformationI> extends QueueDrawerA {
	private Color[] color;

	private Font[] font;

	private ShapeAC[] shape;

	private Double defaultShapeHeight, defaultShapeWidth;

	private ViewPanelI viewPanel;

	public QueueGraphicDrawer(String name, ViewPanel viewPanel) {
		super(name);
		this.viewPanel = viewPanel;
	}

	public void draw(QueueI<I> queue, VisualQueueBean bean) {
		int n = queue.size();
		defaultShapeHeight = bean.getDefaultShapeHeight();
		defaultShapeWidth = bean.getDefaultShapeWidth();
		color = new Color[n];
		font = new Font[n];
		shape = new ShapeAC[n];
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
		draw(getOriginX(), getOriginY(), queue);
	}

	private void draw(int x, int y, QueueI<I> queue) {
		viewPanel.draw(visit(x, y, queue));
	}

	public void drawMessage(String message) {
		Text messageText = new Text(message);
		viewPanel.drawMessage(messageText);
	}

	@SuppressWarnings("unchecked")
	private Vector<PaintableI> visit(int x, int y, QueueI<I> queue) {
		int size = queue.size();
		I[] a = (I[]) (new InformationI[size]);
		int i = 0;
		while (queue.size() > 0) {
			a[i] = queue.first();
			queue.dequeue();
			i = i + 1;
		}
		int currentX = x;
		int maxHeight = y;
		Vector<PaintableI> graphicObjectCollection = new Vector<PaintableI>();
		int arrayLength = a.length;
		for (int k = 0; k < arrayLength; k++) {
			VisualNode visualNode = new VisualNode(a[k].stringValue(), shape[k], currentX, y);
			visualNode.setFillColor(color[k]);
			visualNode.setFont(font[k]);
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
		for (int k = 0; k < size; k++) {
			queue.enqueue(a[k]);
		}
		return graphicObjectCollection;
	}
}
