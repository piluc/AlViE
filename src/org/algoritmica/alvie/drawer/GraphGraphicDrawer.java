package org.algoritmica.alvie.drawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import org.algoritmica.alvie.bean.VisualGraphBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.GraphI;
import org.algoritmica.alvie.desktop.Main;
import org.algoritmica.alvie.graphic.LineType;
import org.algoritmica.alvie.graphic.PaintableI;
import org.algoritmica.alvie.graphic.ShapeAC;
import org.algoritmica.alvie.graphic.Text;
import org.algoritmica.alvie.graphic.VisualArc;
import org.algoritmica.alvie.graphic.VisualNode;
import org.algoritmica.alvie.gui.ViewPanel;
import org.algoritmica.alvie.gui.ViewPanelI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.MessageUtility;

public class GraphGraphicDrawer<IN extends InformationI, IL extends InformationI> extends GraphDrawerA {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 401000;

	private Map<Integer, Color> color;

	private Map<Integer, Font> font;

	private Map<Integer, ShapeAC> shape;

	private Map<Integer, Font> arcFont;

	private Map<Integer, Color> arcColor;

	private Map<Integer, LineType> arcType;

	private Map<Integer, Float> arcThickness;

	private ViewPanelI viewPanel;

	private double defaultShapeWidth = Double.parseDouble(Main.config.getProperty("defaultShapeWidth"));

	private double defaultShapeHeight = Double.parseDouble(Main.config.getProperty("defaultShapeHeight"));

	private Color defaultLineColor;

	public GraphGraphicDrawer(String name, ViewPanel viewPanel) {
		super(name);
		this.viewPanel = viewPanel;
	}

	private int collectVisualArcs(GraphI<IN, IL> graph, Vector<VisualArc> visualArcCollection, Vector<VisualNode> visualNodeCollection, int x, int y) {
		int arcId;
		int k = 0;
		for (int i = 0; i < visualNodeCollection.size(); i++) {
			VisualNode visualFirstNode = visualNodeCollection.elementAt(i);
			int firstNodeId = visualFirstNode.getId();
			for (int j = i; j < visualNodeCollection.size(); j++) {
				VisualNode visualSecondNode = visualNodeCollection.elementAt(j);
				int secondNodeId = visualSecondNode.getId();
				if (graph.isArc(firstNodeId, secondNodeId)) {
					arcId = graph.getArcId(firstNodeId, secondNodeId);
					VisualArc visualArc = new VisualArc(visualFirstNode, visualSecondNode);
					visualArc.setLineColor(arcColor.get(arcId));
					visualArc.setLineType(arcType.get(arcId));
					visualArc.setLineThickness(arcThickness.get(arcId));
					visualArc.setFont(arcFont.get(arcId));
					visualArc.setOriented(graph.isOriented());
					if (graph.getArcLabel(firstNodeId, secondNodeId) != null) {
						visualArc.setWeighted(true);
						visualArc.setWeight(graph.getArcLabel(firstNodeId, secondNodeId).stringValue());
					}
					visualArc.setId(graph.createElementID(firstNodeId, secondNodeId));
					visualArcCollection.add(k++, visualArc);
				}
				if (graph.isOriented() && graph.isArc(secondNodeId, firstNodeId)) {
					arcId = graph.getArcId(secondNodeId, firstNodeId);
					VisualArc visualArc = new VisualArc(visualSecondNode, visualFirstNode);
					visualArc.setLineColor(arcColor.get(arcId));
					visualArc.setLineType(arcType.get(arcId));
					visualArc.setLineThickness(arcThickness.get(arcId));
					visualArc.setOriented(true);
					if (graph.getArcLabel(secondNodeId, firstNodeId) != null) {
						visualArc.setWeighted(true);
						visualArc.setWeight(graph.getArcLabel(secondNodeId, firstNodeId).stringValue());
					}
					visualArc.setId(graph.createElementID(secondNodeId, firstNodeId));
					visualArcCollection.add(k++, visualArc);
				}
			}
		}
		return k;
	}

	private int collectVisualNodes(GraphI<IN, IL> graph, Vector<VisualNode> visualNodeCollection, int x, int y) {
		Collection<Integer> nodeIdCollection = graph.getNodeIds();
		VisualNode visualNode;
		int i = 0;
		for (int nodeId : nodeIdCollection) {
			visualNode = new VisualNode(graph.getNodeInformation(nodeId).stringValue(), shape.get(nodeId), x + graph.getNodeAbscissa(nodeId), y + graph.getNodeOrdinate(nodeId));
			visualNode.setFillColor(color.get(nodeId));
			visualNode.setFont(font.get(nodeId));
			visualNode.setId((int) graph.createElementID(nodeId));
			visualNodeCollection.add(i++, visualNode);
		}
		return i;
	}

	public void draw(GraphI<IN, IL> graph, VisualGraphBean bean) {
		color = new HashMap<Integer, Color>();
		font = new HashMap<Integer, Font>();
		shape = new HashMap<Integer, ShapeAC>();
		arcFont = new HashMap<Integer, Font>();
		arcColor = new HashMap<Integer, Color>();
		arcType = new HashMap<Integer, LineType>();
		arcThickness = new HashMap<Integer, Float>();
		setDefaultVisualizationValues(graph, bean);
		for (int i : bean.getElementColorCollection().keySet()) {
			color.put(i, bean.getElementColorCollection().get(i));
			font.put(i, bean.getElementFontCollection().get(i));
			Double shapeHeight = bean.getElementShapeHeightCollection().get(i);
			Double shapeWidth = bean.getElementShapeWidthCollection().get(i);
			try {
				Class<?> shapeClass = Class.forName(graphicPackagePrefix + bean.getElementShapeCollection().get(i) + shapeSuffix);
				shape.put(i, (ShapeAC) (shapeClass.getConstructor(Class.forName("java.lang.Double"), Class.forName("java.lang.Double")).newInstance(shapeWidth, shapeHeight)));
			} catch (Exception e) {
				MessageUtility.errorMessage(bean.getElementShapeCollection().get(i) + localResourceBundle.getString("errorMessage004"), localResourceBundle.getString("title008"), errorId + 1);
			}
		}
		for (int i : bean.getLineColorCollection().keySet()) {
			arcColor.put(i, bean.getLineColorCollection().get(i));
			arcType.put(i, bean.getLineTypeCollection().get(i));
			arcThickness.put(i, bean.getLineThicknessCollection().get(i));
		}
		setOriginX(!isMultiPane() ? bean.getOriginX() : getDefaultOriginX());
		setOriginY(!isMultiPane() ? bean.getOriginY() : getDefaultOriginY());
		draw(getOriginX(), getOriginY(), graph);
	}

	private void draw(int x, int y, GraphI<IN, IL> graph) {
		viewPanel.draw(visit(x, y, graph));
	}

	public void drawMessage(String message) {
		Text messageText = new Text(message);
		viewPanel.drawMessage(messageText);
	}

	public ViewPanelI getPanel() {
		return viewPanel;
	}

	private void setDefaultVisualizationValues(GraphI<IN, IL> graph, VisualGraphBean bean) {
		Collection<Integer> nodeIdCollection = graph.getNodeIds();
		defaultShapeHeight = bean.getDefaultShapeHeight() != null ? bean.getDefaultShapeHeight() : defaultShapeHeight;
		defaultShapeWidth = bean.getDefaultShapeWidth() != null ? bean.getDefaultShapeWidth() : defaultShapeWidth;
		defaultLineColor = bean.getDefaultLineColor();
		for (int nodeId : nodeIdCollection) {
			color.put(nodeId, bean.getDefaultColor() != null ? bean.getDefaultColor() : getDefaultColor());
			font.put(nodeId, bean.getDefaultFont() != null ? bean.getDefaultFont() : getDefaultFont());
			String shapeName = bean.getDefaultShape() != null ? bean.getDefaultShape() : getDefaultShape();
			try {
				shape.put(nodeId, (ShapeAC) Class.forName(graphicPackagePrefix + shapeName + shapeSuffix).getConstructor(Class.forName("java.lang.Double"), Class.forName("java.lang.Double")).newInstance(defaultShapeWidth, defaultShapeHeight));
			} catch (Exception e) {
				MessageUtility.errorMessage(shapeName + localResourceBundle.getString("errorMessage004"), localResourceBundle.getString("title008"), errorId + 2);
			}
			Collection<Integer> adiacentNodeIdCollection = graph.getAdiacentNodeIds(nodeId);
			int arcId;
			for (int adiacentNodeId : adiacentNodeIdCollection) {
				arcId = graph.getArcId(nodeId, adiacentNodeId);
				arcFont.put(arcId, bean.getDefaultArcLabelFont() != null ? bean.getDefaultArcLabelFont() : getDefaultArcLabelFont());
				arcColor.put(arcId, bean.getDefaultLineColor() != null ? bean.getDefaultLineColor() : getDefaultLineColor());
				arcType.put(arcId, bean.getDefaultLineType() != null ? bean.getDefaultLineType() : getDefaultLineType());
				arcThickness.put(arcId, bean.getDefaultLineThickness() != null ? bean.getDefaultLineThickness() : getDefaultLineThickness());
			}
		}
	}

	public void setPanel(ViewPanel panel) {
		this.viewPanel = panel;
	}

	private void sort(Vector<VisualNode> visualNodeCollection) {
		for (int i = 0; i < visualNodeCollection.size(); i = i + 1) {
			VisualNode next = visualNodeCollection.elementAt(i);
			int j = i;
			while ((j > 0) && (visualNodeCollection.elementAt(j - 1).getId() > next.getId())) {
				visualNodeCollection.set(j, visualNodeCollection.elementAt(j - 1));
				j = j - 1;
			}
			visualNodeCollection.set(j, next);
		}
	}

	private Vector<PaintableI> visit(int x, int y, GraphI<IN, IL> graph) {
		int maxX = 0;
		int maxY = 0;
		Vector<PaintableI> graphicObjectCollection = new Vector<PaintableI>();
		Vector<VisualNode> visualNodeCollection = new Vector<VisualNode>();
		int n = collectVisualNodes(graph, visualNodeCollection, x, y);
		sort(visualNodeCollection);
		Vector<VisualArc> visualArcCollection = new Vector<VisualArc>();
		int m = collectVisualArcs(graph, visualArcCollection, visualNodeCollection, x, y);
		for (int i = 0; i < m; i++) {
			if (visualArcCollection.elementAt(i).getLineColor().equals(defaultLineColor)) {
				graphicObjectCollection.add(visualArcCollection.elementAt(i));
			}
		}
		for (int i = 0; i < m; i++) {
			if (!visualArcCollection.elementAt(i).getLineColor().equals(defaultLineColor)) {
				graphicObjectCollection.add(visualArcCollection.elementAt(i));
			}
		}
		for (int i = 0; i < n; i++) {
			if (x + visualNodeCollection.elementAt(i).getShape().getWidth() > maxX) {
				maxX = x + (int) visualNodeCollection.elementAt(i).getShape().getWidth();
			}
			if (y + visualNodeCollection.elementAt(i).getShape().getHeight() > maxY) {
				maxY = y + (int) visualNodeCollection.elementAt(i).getShape().getHeight();
			}
			graphicObjectCollection.add(visualNodeCollection.elementAt(i));
		}
		int viewPanelWidth = viewPanel.getPreferredSize().width;
		int viewPanelHeight = viewPanel.getPreferredSize().height;
		if (maxX > viewPanelWidth) {
			viewPanelWidth = maxX;
		}
		if (maxY > viewPanelHeight) {
			viewPanelHeight = maxY;
		}
		viewPanel.setPreferredSize(new Dimension(viewPanelWidth, viewPanelHeight));
		return graphicObjectCollection;
	}
}
