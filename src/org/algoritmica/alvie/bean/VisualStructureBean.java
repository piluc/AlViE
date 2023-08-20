package org.algoritmica.alvie.bean;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

/*
 * This is the bean common to all data structures, both random access (that is, array, matrix, stack, and queue) 
 * and linked ones (that is, list, binary tree, and graph). It allows us to manage the loader class name, the data
 * structure default color and font, the data structure origin coordinates, and, for each data structure element, 
 * its color and font.
 */
public class VisualStructureBean {
	private String loaderClassName;

	private Color defaultColor;

	private Font defaultFont;

	private int originX;

	private int originY;

	private Map<Integer, Color> elementColorCollection = new HashMap<Integer, Color>();

	private Map<Integer, Font> elementFontCollection = new HashMap<Integer, Font>();

	public Color getDefaultColor() {
		return defaultColor;
	}

	public Font getDefaultFont() {
		return defaultFont;
	}

	public Map<Integer, Color> getElementColorCollection() {
		return elementColorCollection;
	}

	public Map<Integer, Font> getElementFontCollection() {
		return elementFontCollection;
	}

	public String getLoaderClassName() {
		return loaderClassName;
	}

	public int getOriginX() {
		return originX;
	}

	public int getOriginY() {
		return originY;
	}

	public void setDefaultColor(String defaultColor) {
		this.defaultColor = new Color(Integer.parseInt(defaultColor, 16));
	}

	public void setDefaultFont(String defaultFont) {
		this.defaultFont = Font.decode(defaultFont);
	}

	public void setElementColor(String position, String colorString) {
		Color color = new Color(Integer.parseInt(colorString, 16));
		this.elementColorCollection.put(Integer.parseInt(position), color);
	}

	public void setElementFont(String position, String fontString) {
		Font font = Font.decode(fontString);
		this.elementFontCollection.put(Integer.parseInt(position), font);
	}

	public void setLoaderClassName(String loaderClassName) {
		this.loaderClassName = loaderClassName;
	}

	public void setOriginX(String originX) {
		this.originX = Integer.parseInt(originX);
	}

	public void setOriginY(String originY) {
		this.originY = Integer.parseInt(originY);
	}
}
