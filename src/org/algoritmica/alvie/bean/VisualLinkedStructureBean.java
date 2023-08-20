package org.algoritmica.alvie.bean;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.algoritmica.alvie.graphic.LineType;

/*
 * This class extends the visual malleable structure bean, by adding functionalities common
 * to linked data structures (that is, list, binary tree, and graph). In the case
 * of these data structures we have to manage the default element shape, the default
 * link line type (that is, continue or dashed), line color, and line thickness. Moreover,
 * this class manages, for each element, its shape and, for each link, its line color, line
 * type, and line thickness.
 */
public class VisualLinkedStructureBean extends VisualMalleableStructureBean {
	private String defaultShape;

	private LineType defaultLineType;

	private Color defaultLineColor;

	private Float defaultLineThickness;

	private Map<Integer, String> elementShapeCollection = new HashMap<Integer, String>();

	private Map<Integer, Color> lineColorCollection = new HashMap<Integer, Color>();

	private Map<Integer, LineType> lineTypeCollection = new HashMap<Integer, LineType>();

	private Map<Integer, Float> lineThicknessCollection = new HashMap<Integer, Float>();

	public Color getDefaultLineColor() {
		return defaultLineColor;
	}

	public Float getDefaultLineThickness() {
		return defaultLineThickness;
	}

	public LineType getDefaultLineType() {
		return defaultLineType;
	}

	public String getDefaultShape() {
		return defaultShape;
	}

	public Map<Integer, String> getElementShapeCollection() {
		return elementShapeCollection;
	}

	public Map<Integer, Color> getLineColorCollection() {
		return lineColorCollection;
	}

	public Map<Integer, Float> getLineThicknessCollection() {
		return lineThicknessCollection;
	}

	public Map<Integer, LineType> getLineTypeCollection() {
		return lineTypeCollection;
	}

	public void setDefaultLineColor(String defaultLineColor) {
		this.defaultLineColor = new Color(Integer.parseInt(defaultLineColor, 16));
	}

	public void setDefaultLineThickness(String defaultLineThickness) {
		this.defaultLineThickness = Float.parseFloat(defaultLineThickness);
	}

	public void setDefaultLineType(String defaultLineType) {
		this.defaultLineType = LineType.valueOf(defaultLineType);
	}

	public void setDefaultShape(String defaultShape) {
		this.defaultShape = defaultShape;
	}

	public void setElementShape(String position, String shapeString) {
		this.elementShapeCollection.put(Integer.parseInt(position), shapeString);
	}

	public void setLineColor(String position, String colorString) {
		Color color = new Color(Integer.parseInt(colorString, 16));
		this.lineColorCollection.put(Integer.parseInt(position), color);
	}

	public void setLineThickness(String position, String thicknessString) {
		Float thickness = Float.parseFloat(thicknessString);
		this.lineThicknessCollection.put(Integer.parseInt(position), thickness);
	}

	public void setLineType(String position, String typeString) {
		LineType type = LineType.valueOf(typeString);
		this.lineTypeCollection.put(Integer.parseInt(position), type);
	}
}
