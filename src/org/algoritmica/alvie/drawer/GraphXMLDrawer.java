package org.algoritmica.alvie.drawer;

import java.io.OutputStream;
import java.util.Collection;

import org.algoritmica.alvie.datastructure.GraphI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.ColorUtility;
import org.algoritmica.alvie.utility.FontUtility;

/*
 * This is the XML graph drawer. The XML graph, node, arc, visual graph, visual node,
 * and visual arc definition is specified in the algorithm XSD file. The message drawing is delegated
 * to an XML drawer.
 */
public class GraphXMLDrawer<IN extends InformationI, IL extends InformationI> extends GraphDrawerA {
	protected XMLDrawer xmlDrawer;

	public GraphXMLDrawer(String name, OutputStream outputStream) {
		super(name);
		xmlDrawer = new XMLDrawer(outputStream);
	}

	public void draw(GraphI<IN, IL> graph) {
		boolean isOriented = graph.isOriented();
		xmlDrawer.write("<graph isOriented=\"" + isOriented + "\" type=\"" + graph.getType() + "\" arcLabelType=\"" + graph.getArcType() + "\">");
		Collection<Integer> nodeIdCollection = graph.getNodeIds();
		for (int nodeId : nodeIdCollection) {
			xmlDrawer.write("<node id=\"" + nodeId + "\" value=\"" + graph.getNodeInformation(nodeId).stringValue() + "\" x=\"" + graph.getNodeAbscissa(nodeId) + "\" y=\"" + graph.getNodeOrdinate(nodeId) + "\"/>");
		}
		IL arcLabel;
		for (int nodeId : nodeIdCollection) {
			Collection<Integer> adiacentNodeIdCollection = graph.getAdiacentNodeIds(nodeId);
			for (int adiacentNodeId : adiacentNodeIdCollection) {
				arcLabel = graph.getArcLabel(nodeId, adiacentNodeId);
				xmlDrawer.write("<arc id=\"" + graph.getArcId(nodeId, adiacentNodeId) + "\" idFirstNode=\"" + nodeId + "\" idSecondNode=\"" + adiacentNodeId + "\"" + ((arcLabel != null) ? (" label=\"" + graph.getArcLabel(nodeId, adiacentNodeId).stringValue() + "\"") : "") + "/>");
			}
		}
		xmlDrawer.write("</graph>");
	}

	public void drawVisual(Integer[] id, String[] color, String[] font, String[] shape, String[] height, String[] width, Integer[] arcId, String[] arcLabelFont, String[] arcType, String[] arcColor, String[] arcThickness) {
		xmlDrawer.write("<visualGraph defaultColor=\"" + ColorUtility.getStringColor(getDefaultColor()) + "\" defaultFont=\"" + FontUtility.getStringFont(getDefaultFont()) + "\" defaultShape=\"" + getDefaultShape() + "\" defaultShapeHeight=\"" + getDefaultShapeHeight() + "\" defaultShapeWidth=\"" + getDefaultShapeWidth() + "\"  defaultLineColor=\"" + ColorUtility.getStringColor(getDefaultLineColor()) + "\" defaultLineType=\"" + getDefaultLineType() + "\" defaultLineThickness=\""
				+ getDefaultLineThickness() + "\" originX=\"" + getOriginX() + "\" originY=\"" + getOriginY() + "\">");
		if (id != null) {
			for (int c = 0; c < id.length; c++) {
				xmlDrawer.write("<visualNode id=\"" + id[c] + "\" color=\"" + color[c] + "\" font=\"" + font[c] + "\" shape=\"" + shape[c] + "\" shapeHeight=\"" + height[c] + "\" shapeWidth=\"" + width[c] + "\"/>");
			}
		}
		if (arcId != null) {
			for (int c = 0; c < arcId.length; c++) {
				xmlDrawer.write("<visualArc id=\"" + arcId[c] + "\" labelFont=\"" + arcLabelFont[c] + "\" lineColor=\"" + arcColor[c] + "\" lineType=\"" + arcType[c] + "\" lineThickness=\"" + arcThickness[c] + "\"/>");
			}
		}
		xmlDrawer.write("</visualGraph>");
	}

	public void draw(Integer[] id, String[] color, String[] font, String[] shape, String[] height, String[] width, Integer[] arcId, String[] arcLabelFont, String[] arcType, String[] arcColor, String[] arcThickness, GraphI<IN, IL> graph) {
		draw(graph);
		drawVisual(id, color, font, shape, height, width, arcId, arcLabelFont, arcType, arcColor, arcThickness);
	}

	public void drawMessage(String message) {
		xmlDrawer.drawMessage(message);
	}

	public XMLDrawer getXMLDrawer() {
		return xmlDrawer;
	}
}
