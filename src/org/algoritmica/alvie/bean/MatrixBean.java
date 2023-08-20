package org.algoritmica.alvie.bean;

import java.util.HashMap;
import java.util.Map;

/*
 * This the matrix bean which allows us to manage the number of rows and the number of columns
 * of the matrix and the collection of its element values. The id of an element is its
 * position while listing from top to bottom and from left to right.
 */
public class MatrixBean extends StructureBean {

	private int height;

	private int width;

	private Map<Integer, String> elementCollection = new HashMap<Integer, String>();

	public Map<Integer, String> getElementCollection() {
		return elementCollection;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setElementValue(String id, String information) {
		elementCollection.put(Integer.parseInt(id), information);
	}

	public void setHeight(String height) {
		this.height = Integer.parseInt(height);
	}

	public void setWidth(String width) {
		this.width = Integer.parseInt(width);
	}

}
