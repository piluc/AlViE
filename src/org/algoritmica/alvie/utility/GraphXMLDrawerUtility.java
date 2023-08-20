package org.algoritmica.alvie.utility;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.GraphI;
import org.algoritmica.alvie.drawer.GraphXMLDrawer;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.visualdatastructure.VisualGraph;

public class GraphXMLDrawerUtility<IN extends InformationI, IL extends InformationI>
		extends
		LinkedStructureXMLDrawerUtility<GraphI<IN, IL>, GraphXMLDrawer<IN, IL>> {
	private VisualGraph<IN, IL> visualDataStructure;

	public GraphXMLDrawerUtility(GraphI<IN, IL> g, String name, OutputStream os) {
		super(g, os);
		visualDataStructure = new VisualGraph<IN, IL>();
		drawer = new GraphXMLDrawer<IN, IL>(name, os);
		visualDataStructure.setXMLDrawer(drawer);
	}

	public void draw(Integer[] id, String[] color, String[] font,
			String[] shape, String[] height, String[] width, Integer[] arcId,
			String[] arcLabelFont, String[] arcType, String[] arcColor,
			String[] arcThickness, String message) {
		startGraphicStructure(drawer.getName(), GRAPH);
		drawer.draw(id, color, font, shape, height, width, arcId, arcLabelFont,
				arcType, arcColor, arcThickness, dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}

	public void draw(Integer[] id, String[] color, String message) {
		if (id != null) {
			String[] font = new String[id.length];
			String[] shape = new String[id.length];
			String[] height = new String[id.length];
			String[] width = new String[id.length];
			for (int i = 0; i < id.length; i++) {
				font[i] = FontUtility.getStringFont(drawer.getDefaultFont());
				shape[i] = drawer.getDefaultShape();
				height[i] = drawer.getDefaultShapeHeight().toString();
				width[i] = drawer.getDefaultShapeWidth().toString();
			}
			draw(id, color, font, shape, height, width, null, null, null, null,
					null, message);
		} else {
			draw(null, null, null, null, null, null, null, null, null, null,
					null, message);
		}
	}

	public void draw(Integer[] id, String[] color, Integer[] arcId,
			String[] arcColor, String message) {
		String[] font = null;
		String[] shape = null;
		String[] height = null;
		String[] width = null;
		String[] arcLabelFont = null;
		String[] arcType = null;
		String[] arcThickness = null;
		if (id != null) {
			font = new String[id.length];
			shape = new String[id.length];
			height = new String[id.length];
			width = new String[id.length];
			for (int i = 0; i < id.length; i++) {
				font[i] = FontUtility.getStringFont(drawer.getDefaultFont());
				shape[i] = drawer.getDefaultShape();
				height[i] = drawer.getDefaultShapeHeight().toString();
				width[i] = drawer.getDefaultShapeWidth().toString();
			}
		}
		if (arcId != null) {
			arcLabelFont = new String[arcId.length];
			arcType = new String[arcId.length];
			arcThickness = new String[arcId.length];
			for (int i = 0; i < arcId.length; i++) {
				arcLabelFont[i] = FontUtility.getStringFont(drawer
						.getDefaultArcLabelFont());
				arcType[i] = drawer.getDefaultLineType().toString();
				arcThickness[i] = drawer.getDefaultLineThickness().toString();
			}
		}
		draw(id, color, font, shape, height, width, arcId, arcLabelFont,
				arcType, arcColor, arcThickness, message);
	}

	public void draw(String message) {
		startGraphicStructure(drawer.getName(), GRAPH);
		drawer.draw(null, null, null, null, null, null, null, null, null, null,
				null, dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}

	public void setDefaultShapeHeight(double h) {
		drawer.setDefaultShapeHeight(h);
	}

	public void setDefaultShapeWidth(double w) {
		drawer.setDefaultShapeWidth(w);
	}
}
