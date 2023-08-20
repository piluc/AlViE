package org.algoritmica.alvie.drawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import org.algoritmica.alvie.bean.VisualBinaryTreeBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.BinaryTreeI;
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
 * This is the graphical binary tree drawer. Each element has its own color, font, shape,
 * height, and width. An element of the binary tree is connected to its father by means
 * of a segment: each segment has its own color, type (that is, continue or dashed),
 * and thickness. The elements are hence drawn as a rectangular or oval shape containing 
 * the representation of the element information and connected by a segment to its father. 
 * The origin of the drawer denotes the left bottom point of the root bounding rectangle.
 * The message drawing method simply draws the message in the view panel associated with the drawer.
 */
public class BinaryTreeGraphicDrawer<I extends InformationI> extends BinaryTreeDrawerA {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 400000;

	private Map<Integer, Color> color;

	private Map<Integer, Font> font;

	private Map<Integer, ShapeAC> shape;

	private Map<Integer, Color> lineColor;

	private Map<Integer, LineType> lineType;

	private Map<Integer, Float> lineThickness;

	private Double defaultShapeHeight, defaultShapeWidth;

	private Map<Integer, Point2D> nodeLocations = null;

	private Map<Integer, Dimension> subtreeSizes = null;

	private ViewPanelI viewPanel;

	private final int treeNodeVerticalDistance = Integer.parseInt(Main.config.getProperty("defaultTreeNodeVerticalDistance"));

	private final int treeNodeHorizontalDistance = Integer.parseInt(Main.config.getProperty("defaultTreeNodeHorizontalDistance"));

	public BinaryTreeGraphicDrawer(String name, ViewPanel viewPanel) {
		super(name);
		this.viewPanel = viewPanel;
	}

	private void calculateLocation(BinaryTreeI<I> n, int x, int y, int id) {
		if (n == null)
			return;
		Point2D p = new Point2D.Double(x, y);
		nodeLocations.put(id, p);
		Dimension ld = subtreeSizes.get(2 * id + 1);
		if (ld != null) {
			int backX = (treeNodeHorizontalDistance - (int) shape.get(2 * id + 1).getWidth()) / 2;
			if (backX < 0) {
				backX = (int) shape.get(2 * id + 1).getWidth();
			} else {
				backX = backX + (int) shape.get(2 * id + 1).getWidth();
			}
			int lY = y + treeNodeVerticalDistance + (int) shape.get(2 * id + 1).getHeight();
			Dimension lrd = subtreeSizes.get(2 * (2 * id + 1) + 2);
			if (lrd != null) {
				backX = backX + lrd.width;
			}
			calculateLocation(n.leftSubtree(), x - backX, lY, 2 * id + 1);
		}
		Dimension rd = subtreeSizes.get(2 * id + 2);
		if (rd != null) {
			int forwardX = (treeNodeHorizontalDistance - (int) shape.get(2 * id + 2).getWidth()) / 2;
			if (forwardX < 0) {
				forwardX = (int) shape.get(2 * id + 2).getWidth();
			} else {
				forwardX = forwardX + (int) shape.get(2 * id + 2).getWidth();
			}
			int lY = y + treeNodeVerticalDistance + (int) shape.get(2 * id + 2).getHeight();
			Dimension rld = subtreeSizes.get(2 * (2 * id + 2) + 1);
			if (rld != null) {
				forwardX = forwardX + rld.width;
			}
			calculateLocation(n.rightSubtree(), x + forwardX, lY, 2 * id + 2);
		}
	}

	private Dimension calculateSubtreeSize(BinaryTreeI<I> n, int id) {
		if (n.size() == 0)
			return new Dimension(0, 0);
		Dimension ld = calculateSubtreeSize(n.leftSubtree(), 2 * id + 1);
		Dimension rd = calculateSubtreeSize(n.rightSubtree(), 2 * id + 2);
		int h = (int) shape.get(id).getHeight() + treeNodeVerticalDistance + Math.max(ld.height, rd.height);
		int w = ld.width + (int) Math.max(shape.get(id).getWidth(), treeNodeHorizontalDistance) + rd.width;
		Dimension d = new Dimension(w, h);
		subtreeSizes.put(id, d);
		return d;
	}

	public void draw(BinaryTreeI<I> binaryTree, VisualBinaryTreeBean bean) {
		defaultShapeHeight = bean.getDefaultShapeHeight();
		defaultShapeWidth = bean.getDefaultShapeWidth();
		color = new HashMap<Integer, Color>();
		font = new HashMap<Integer, Font>();
		shape = new HashMap<Integer, ShapeAC>();
		lineColor = new HashMap<Integer, Color>();
		lineType = new HashMap<Integer, LineType>();
		lineThickness = new HashMap<Integer, Float>();
		setDefaultVisualizationValues(binaryTree, 0, bean);
		for (Integer i : bean.getElementColorCollection().keySet()) {
			color.put(i, bean.getElementColorCollection().get(i));
			font.put(i, bean.getElementFontCollection().get(i));
			try {
				shape.put(i, (ShapeAC) Class.forName(graphicPackagePrefix + bean.getElementShapeCollection().get(i) + shapeSuffix).getConstructor(Class.forName("java.lang.Double"), Class.forName("java.lang.Double")).newInstance(bean.getElementShapeWidthCollection().get(i), bean.getElementShapeHeightCollection().get(i)));
			} catch (Exception e) {
				MessageUtility.errorMessage(bean.getElementShapeCollection().get(i) + localResourceBundle.getString("errorMessage004"), localResourceBundle.getString("title008"), errorId + 1);
			}
		}
		for (Integer i : bean.getLineColorCollection().keySet()) {
			lineColor.put(i, bean.getLineColorCollection().get(i));
			lineType.put(i, bean.getLineTypeCollection().get(i));
			lineThickness.put(i, bean.getLineThicknessCollection().get(i));
		}
		setOriginX(!isMultiPane() ? bean.getOriginX() : getDefaultOriginX());
		setOriginY(!isMultiPane() ? bean.getOriginY() : getDefaultOriginY());
		draw(getOriginX(), getOriginY(), binaryTree);
	}

	private void draw(int x, int y, BinaryTreeI<I> binaryTree) {
		viewPanel.draw(visit(x, y, binaryTree));
	}

	public void drawMessage(String message) {
		Text messageText = new Text(message);
		viewPanel.drawMessage(messageText);
	}

	public ViewPanelI getPanel() {
		return viewPanel;
	}

	private void recursivelyCollectGraphicalObjects(BinaryTreeI<I> binaryTree, int id, Collection<PaintableI> graphicObjectCollection) {
		if (binaryTree.size() > 0) {
			recursivelyCollectGraphicalObjects(binaryTree.leftSubtree(), 2 * id + 1, graphicObjectCollection);
			recursivelyCollectGraphicalObjects(binaryTree.rightSubtree(), 2 * id + 2, graphicObjectCollection);
			int x = (int) nodeLocations.get(id).getX();
			int y = (int) nodeLocations.get(id).getY();
			VisualNode visualSourceNode = new VisualNode(binaryTree.root().stringValue(), shape.get(id), x, y);
			visualSourceNode.setFillColor(color.get(id));
			visualSourceNode.setFont(font.get(id));
			visualSourceNode.setId((int) binaryTree.createElementID(id));
			int viewPanelWidth = viewPanel.getPreferredSize().width;
			int viewPanelHeight = viewPanel.getPreferredSize().height;
			if (x + visualSourceNode.getWidth() > viewPanelWidth) {
				viewPanelWidth = x + (int) visualSourceNode.getWidth();
			}
			if (y > viewPanelHeight) {
				viewPanelHeight = y;
			}
			viewPanel.setPreferredSize(new Dimension(viewPanelWidth, viewPanelHeight));
			if (id > 0) {
				VisualNode visualFatherNode;
				if (nodeLocations.containsKey((id - 1) / 2)) {
					visualFatherNode = new VisualNode(" ", shape.get((id - 1) / 2), (int) nodeLocations.get((id - 1) / 2).getX(), (int) nodeLocations.get((id - 1) / 2).getY());
					visualFatherNode.setFillColor(color.get((id - 1) / 2));
					visualFatherNode.setFont(font.get((id - 1) / 2));
					visualFatherNode.setId((int) binaryTree.createElementID((id - 1) / 2));
				} else {
					visualFatherNode = new VisualNode(" ", shape.get((id - 2) / 2), (int) nodeLocations.get((id - 2) / 2).getX(), (int) nodeLocations.get((id - 2) / 2).getY());
					visualFatherNode.setFillColor(color.get((id - 2) / 2));
					visualFatherNode.setFont(font.get((id - 2) / 2));
					visualFatherNode.setId((int) binaryTree.createElementID((id - 2) / 2));
				}
				VisualLink visualLink = new VisualLink(visualSourceNode, visualFatherNode);
				visualLink.setLineColor(lineColor.get(id - 1));
				visualLink.setLineType(lineType.get(id - 1));
				visualLink.setLineThickness(lineThickness.get(id - 1));
				graphicObjectCollection.add(visualLink);
			}
			graphicObjectCollection.add(visualSourceNode);
		}
	}

	private void setDefaultVisualizationValues(BinaryTreeI<I> binaryTree, Integer position, VisualBinaryTreeBean bean) {
		if (binaryTree.size() > 0) {
			color.put(position, bean.getDefaultColor() != null ? bean.getDefaultColor() : getDefaultColor());
			font.put(position, bean.getDefaultFont() != null ? bean.getDefaultFont() : getDefaultFont());
			String shapeName = bean.getDefaultShape() != null ? bean.getDefaultShape() : getDefaultShape();
			try {
				shape.put(position, (ShapeAC) Class.forName(graphicPackagePrefix + shapeName + shapeSuffix).getConstructor(Class.forName("java.lang.Double"), Class.forName("java.lang.Double")).newInstance(defaultShapeWidth, defaultShapeHeight));
			} catch (Exception e) {
				MessageUtility.errorMessage(shapeName + localResourceBundle.getString("errorMessage004"), localResourceBundle.getString("title008"), errorId + 2);
			}
			if (position > 0) {
				lineColor.put(position - 1, bean.getDefaultLineColor() != null ? bean.getDefaultLineColor() : getDefaultLineColor());
				lineType.put(position - 1, bean.getDefaultLineType() != null ? bean.getDefaultLineType() : getDefaultLineType());
				lineThickness.put(position - 1, bean.getDefaultLineThickness() != null ? bean.getDefaultLineThickness() : getDefaultLineThickness());
			}
			setDefaultVisualizationValues(binaryTree.leftSubtree(), 2 * position + 1, bean);
			setDefaultVisualizationValues(binaryTree.rightSubtree(), 2 * position + 2, bean);
		}
	}

	public void setPanel(ViewPanel panel) {
		this.viewPanel = panel;
	}

	private Vector<PaintableI> visit(int x, int y, BinaryTreeI<I> binaryTree) {
		nodeLocations = new HashMap<Integer, Point2D>();
		subtreeSizes = new HashMap<Integer, Dimension>();
		if (binaryTree.size() > 0) {
			calculateSubtreeSize(binaryTree, 0);
			calculateLocation(binaryTree, x, y, 0);
		}
		Vector<PaintableI> graphicObjectCollection = new Vector<PaintableI>();
		recursivelyCollectGraphicalObjects(binaryTree, 0, graphicObjectCollection);
		return graphicObjectCollection;
	}
}
