package org.algoritmica.alvie.bean;

import java.util.HashMap;
import java.util.Map;

/*
 * This the binary tree bean which allows us to manage the size of a tree and
 * the collection of its element values.
 */
public class BinaryTreeBean extends StructureBean {
	private int size;

	private Map<Integer, String> elementCollection = new HashMap<Integer, String>();

	public int getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = Integer.parseInt(size);
	}

	public Map<Integer, String> getElementCollection() {
		return elementCollection;
	}

	public void setElementValue(String position, String value) {
		elementCollection.put(Integer.parseInt(position), value);
	}

}
