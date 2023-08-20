package org.algoritmica.alvie.bean;

import java.util.HashMap;
import java.util.Map;

/*
 * This the visual geometric figure bean which adds to the visual structure data structure bean 
 * only the possibility of managing the line thickness of segments, since all the elements have no
 * explicit shape and size, which are instead determined by their corresponding 
 * geometric objects.
 */
public class VisualGeometricFigureBean extends VisualStructureBean {
	private Float defaultLineThickness;

	private Map<Integer, Float> lineThicknessCollection = new HashMap<Integer, Float>();

	public Float getDefaultLineThickness() {
		return defaultLineThickness;
	}

	public Map<Integer, Float> getLineThicknessCollection() {
		return lineThicknessCollection;
	}

	public void setDefaultLineThickness(String defaultLineThickness) {
		this.defaultLineThickness = Float.parseFloat(defaultLineThickness);
	}

	public void setLineThickness(String position, String thicknessString) {
		Float thickness = Float.parseFloat(thicknessString);
		this.lineThicknessCollection.put(Integer.parseInt(position), thickness);
	}
}
