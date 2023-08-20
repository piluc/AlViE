package org.algoritmica.alvie.bean;

import java.util.HashMap;
import java.util.Map;

/*
 * This is the bean common to all malleable data structures, both random access (that is, 
 * array, stack, and queue) and linked ones (that is, list, binary tree, and graph). It allows 
 * us to manage the data structure element shape height and width (in the case of the 
 * matrix, all elements must have the same height and width).
 */
public class VisualMalleableStructureBean extends VisualSizedStructureBean {
	private Map<Integer, Double> elementShapeHeight = new HashMap<Integer, Double>();

	private Map<Integer, Double> elementShapeWidth = new HashMap<Integer, Double>();

	public Map<Integer, Double> getElementShapeHeightCollection() {
		return elementShapeHeight;
	}

	public Map<Integer, Double> getElementShapeWidthCollection() {
		return elementShapeWidth;
	}

	public void setElementShapeHeight(String id, String shapeHeight) {
		this.elementShapeHeight.put(Integer.parseInt(id), Double.parseDouble(shapeHeight));
	}

	public void setElementShapeWidth(String id, String shapeWidth) {
		this.elementShapeWidth.put(Integer.parseInt(id), Double.parseDouble(shapeWidth));
	}
}
