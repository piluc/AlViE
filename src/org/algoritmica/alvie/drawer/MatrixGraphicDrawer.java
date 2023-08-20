package org.algoritmica.alvie.drawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import java.util.Vector;

import org.algoritmica.alvie.bean.VisualMatrixBean;
import org.algoritmica.alvie.datastructure.MatrixI;
import org.algoritmica.alvie.graphic.PaintableI;
import org.algoritmica.alvie.graphic.RectangularShape;
import org.algoritmica.alvie.graphic.ShapeAC;
import org.algoritmica.alvie.graphic.Text;
import org.algoritmica.alvie.graphic.VisualNode;
import org.algoritmica.alvie.gui.ViewPanel;
import org.algoritmica.alvie.gui.ViewPanelI;
import org.algoritmica.alvie.information.InformationI;

/*
 * This is the graphical matrix drawer. Each element has its own color and font. All
 * element shapes are rectangular and have all the same height and width. The
 * elements of a row are drawn as rectangles, one after the other, containing the string 
 * representation of the element information. The row are drawn one below the other. 
 * The message drawing method simply draws the message in the view panel associated with the drawer.
 */
public class MatrixGraphicDrawer<I extends InformationI> extends MatrixDrawerA {
	private Color[] color;

	private Font[] font;

	private ShapeAC[] shape;

	private ViewPanelI viewPanel;

	private Double defaultShapeHeight, defaultShapeWidth;

	public MatrixGraphicDrawer(String name, ViewPanel jp) {
		super(name);
		viewPanel = jp;
	}

	public void draw(MatrixI<I> matrix, VisualMatrixBean bean) {
		int h = matrix.height();
		int w = matrix.width();
		defaultShapeHeight = bean.getDefaultShapeHeight();
		defaultShapeWidth = bean.getDefaultShapeWidth();
		color = new Color[h * w];
		font = new Font[h * w];
		shape = new ShapeAC[h * w];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				color[i * w + j] = bean.getDefaultColor() != null ? bean.getDefaultColor() : getDefaultColor();
				font[i * w + j] = bean.getDefaultFont() != null ? bean.getDefaultFont() : getDefaultFont();
				shape[i * w + j] = new RectangularShape(defaultShapeWidth, defaultShapeHeight);
			}
		}
		Map<Integer, Color> colorMap = bean.getElementColorCollection();
		Map<Integer, Font> fontMap = bean.getElementFontCollection();
		for (Integer i : colorMap.keySet()) {
			color[i] = colorMap.get(i);
			font[i] = fontMap.get(i);
		}
		setOriginX(!isMultiPane() ? bean.getOriginX() : getDefaultOriginX());
		setOriginY(!isMultiPane() ? bean.getOriginY() : getDefaultOriginY());
		draw(getOriginX(), getOriginY(), matrix);
	}

	private void draw(int x, int y, MatrixI<I> matrix) {
		viewPanel.draw(visit(x, y, matrix));
	}

	public void drawMessage(String m) {
		Text message = new Text(m);
		viewPanel.drawMessage(message);
	}

	public Vector<PaintableI> visit(int x, int y, MatrixI<I> matrix) {
		int currentX = x;
		int currentY = y;
		Vector<PaintableI> graphicObjectCollection = new Vector<PaintableI>();
		int height = matrix.height();
		int width = matrix.width();
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				VisualNode visualNode = new VisualNode(matrix.elementAt(h, w).stringValue(), shape[h * width + w], currentX, currentY);
				visualNode.setFillColor(color[h * width + w]);
				visualNode.setFont(font[h * width + w]);
				visualNode.setId((int) matrix.createElementID(h, w));
				graphicObjectCollection.add(visualNode);
				currentX = currentX + (int) (shape[h * width + w].getWidth());
			}
			currentX = x;
			currentY = currentY + defaultShapeHeight.intValue();
		}
		currentY = currentY - defaultShapeHeight.intValue();
		int viewPanelWidth = viewPanel.getPreferredSize().width;
		int viewPanelHeight = viewPanel.getPreferredSize().height;
		if (currentX > viewPanelWidth) {
			viewPanelWidth = currentX;
		}
		if (currentY > viewPanelHeight) {
			viewPanelHeight = currentY;
		}
		viewPanel.setPreferredSize(new Dimension(viewPanelWidth, viewPanelHeight));
		return graphicObjectCollection;
	}
}
