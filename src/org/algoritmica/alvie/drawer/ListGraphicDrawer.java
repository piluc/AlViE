package org.algoritmica.alvie.drawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import org.algoritmica.alvie.bean.VisualListBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.ListI;
import org.algoritmica.alvie.desktop.Main;
import org.algoritmica.alvie.graphic.LineType;
import org.algoritmica.alvie.graphic.PaintableI;
import org.algoritmica.alvie.graphic.ShapeAC;
import org.algoritmica.alvie.graphic.Text;
import org.algoritmica.alvie.graphic.VisualLink;
import org.algoritmica.alvie.graphic.VisualNode;
import org.algoritmica.alvie.gui.ViewPanel;
import org.algoritmica.alvie.gui.ViewPanelI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.MessageUtility;

/*
 * This is the graphical list drawer. Each element has its own color, font, shape,
 * height, and width. An element of the list is connected to the next one by means
 * of a segment: each segment has its own color, type (that is, continue or dashed),
 * and thickness. The elements are hence drawn as a rectangular or oval shape, one 
 * after the other, containing the representation of the element information and connected
 * by a segment to the next element of the list. The message drawing method simply draws 
 * the message in the view panel associated with the drawer.
 */
public class ListGraphicDrawer<I extends InformationI> extends ListDrawerA {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 402000;

	private Color[] color;

	private Font[] font;

	private ShapeAC[] shape;

	private Color[] lineColor;

	private LineType[] lineType;

	private Float[] lineThickness;

	private Double defaultShapeHeight, defaultShapeWidth;

	private ViewPanelI viewPanel;

	public ListGraphicDrawer(String name, ViewPanel viewPanel) {
		super(name);
		this.viewPanel = viewPanel;
	}

	private void draw(int x, int y, ListI<I> list) {
		viewPanel.draw(visit(x, y, list));
	}

	public void draw(ListI<I> list, VisualListBean bean) {
		int n = list.size();
		defaultShapeHeight = bean.getDefaultShapeHeight();
		defaultShapeWidth = bean.getDefaultShapeWidth();
		color = new Color[n];
		font = new Font[n];
		shape = new ShapeAC[n];
		lineColor = new Color[Math.max(n - 1, 0)];
		lineType = new LineType[Math.max(n - 1, 0)];
		lineThickness = new Float[Math.max(n - 1, 0)];
		for (int i = 0; i < n; i++) {
			color[i] = bean.getDefaultColor() != null ? bean.getDefaultColor() : getDefaultColor();
			font[i] = bean.getDefaultFont() != null ? bean.getDefaultFont() : getDefaultFont();
			String shapeName = bean.getDefaultShape() != null ? bean.getDefaultShape() : getDefaultShape();
			try {
				shape[i] = (ShapeAC) Class.forName(graphicPackagePrefix + shapeName + shapeSuffix).getConstructor(Class.forName("java.lang.Double"), Class.forName("java.lang.Double")).newInstance(defaultShapeWidth, defaultShapeHeight);
			} catch (Exception e) {
				MessageUtility.errorMessage(shapeName + localResourceBundle.getString("errorMessage004"), localResourceBundle.getString("title008"), errorId + 1);
			}

		}
		for (int i = 0; i < n - 1; i++) {
			lineColor[i] = bean.getDefaultLineColor() != null ? bean.getDefaultLineColor() : getDefaultLineColor();
			lineType[i] = bean.getDefaultLineType() != null ? bean.getDefaultLineType() : getDefaultLineType();
			lineThickness[i] = bean.getDefaultLineThickness() != null ? bean.getDefaultLineThickness() : getDefaultLineThickness();
		}
		Map<Integer, Color> colorMap = bean.getElementColorCollection();
		Map<Integer, Font> fontMap = bean.getElementFontCollection();
		Map<Integer, String> shapeMap = bean.getElementShapeCollection();
		Map<Integer, Double> shapeHeightMap = bean.getElementShapeHeightCollection();
		Map<Integer, Double> shapeWidthMap = bean.getElementShapeWidthCollection();
		for (Integer i : colorMap.keySet()) {
			color[i] = colorMap.get(i);
			font[i] = fontMap.get(i);
			try {
				shape[i] = (ShapeAC) Class.forName(graphicPackagePrefix + shapeMap.get(i) + shapeSuffix).getConstructor(Class.forName("java.lang.Double"), Class.forName("java.lang.Double")).newInstance(shapeWidthMap.get(i), shapeHeightMap.get(i));
			} catch (Exception e) {
				MessageUtility.errorMessage(bean.getElementShapeCollection().get(i) + localResourceBundle.getString("errorMessage004"), localResourceBundle.getString("title008"), errorId + 2);
			}
		}
		Map<Integer, Color> lineColorMap = bean.getLineColorCollection();
		Map<Integer, LineType> lineTypeMap = bean.getLineTypeCollection();
		Map<Integer, Float> lineThicknessMap = bean.getLineThicknessCollection();
		for (Integer i : lineColorMap.keySet()) {
			lineColor[i] = lineColorMap.get(i);
			lineType[i] = lineTypeMap.get(i);
			lineThickness[i] = lineThicknessMap.get(i);
		}
		setOriginX(!isMultiPane() ? bean.getOriginX() : getDefaultOriginX());
		setOriginY(!isMultiPane() ? bean.getOriginY() : getDefaultOriginY());
		draw(getOriginX(), getOriginY(), list);
	}

	public void drawMessage(String message) {
		Text messageText = new Text(message);
		viewPanel.drawMessage(messageText);
	}

	public ViewPanelI getPanel() {
		return viewPanel;
	}

	public void setPanel(ViewPanel panel) {
		this.viewPanel = panel;
	}

	private Vector<PaintableI> visit(int x, int y, ListI<I> list) {
		int currentX = x;
		int maxHeight = y;
		int linkLength = Integer.parseInt(Main.config.getProperty("interListElementSpace"));
		Vector<PaintableI> graphicObjectCollection = new Vector<PaintableI>();
		int listLength = list.size();
		VisualNode visualDestinationNode = null;
		for (int k = 0; k < listLength - 1; k++) {
			VisualNode visualSourceNode = new VisualNode(list.head().stringValue(), shape[k], currentX, y);
			visualSourceNode.setFillColor(color[k]);
			visualSourceNode.setFont(font[k]);
			visualSourceNode.setId((int) list.createElementID(k));
			list = list.tail();
			currentX = currentX + (int) shape[k].getWidth() + linkLength;
			visualDestinationNode = new VisualNode(list.head().stringValue(), shape[k + 1], currentX, y);
			visualDestinationNode.setFillColor(color[k + 1]);
			visualDestinationNode.setFont(font[k + 1]);
			visualDestinationNode.setId((int) list.createElementID(k + 1));
			VisualLink visualLink = new VisualLink(visualSourceNode, visualDestinationNode);
			visualLink.setLineColor(lineColor[k]);
			visualLink.setLineType(lineType[k]);
			visualLink.setLineThickness(lineThickness[k]);
			graphicObjectCollection.add(visualLink);
			graphicObjectCollection.add(visualSourceNode);
			if (y + shape[k].getHeight() > maxHeight) {
				maxHeight = (int) (y + shape[k].getHeight());
			}
		}
		if (visualDestinationNode != null) {
			graphicObjectCollection.add(visualDestinationNode);
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
