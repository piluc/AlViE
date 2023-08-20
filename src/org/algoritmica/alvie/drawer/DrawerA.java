package org.algoritmica.alvie.drawer;

import java.awt.Color;
import java.awt.Font;

import org.algoritmica.alvie.desktop.Main;
import org.algoritmica.alvie.graphic.LineType;

/*
 * This abstract class implements all the methods of the drawer interface, but the message
 * drawing one (since this method depends on the kind of the drawer). The class is just
 * a collection of getter and setter method. The constructor simply initializes all graphical
 * properties according to the configuration property file. The difference between the default
 * origin and the origin is that the former is used in the multi-pane mode, while the latter
 * is used in the single pane mode (this makes sense only in the case of a graphic drawer).
 */
public abstract class DrawerA implements DrawerI {
	protected String graphicPackagePrefix = "org.algoritmica.alvie.graphic.";

	protected String shapeSuffix = "Shape";

	private String name;

	private Color defaultColor, defaultLineColor;

	private Font defaultFont;

	private Float defaultLineThickness;

	private String defaultShape;

	private Double defaultShapeHeight;

	private Double defaultShapeWidth;

	private LineType defaultLineType;

	private int originX, originY;

	private int defaultOriginX, defaultOriginY;

	private boolean isMultiPane;

	public DrawerA(String n) {
		name = n;
		defaultColor = new Color(Integer.parseInt(Main.config.getProperty("defaultVisualDataStructureColor"), 16));
		defaultFont = Font.decode(Main.config.getProperty("defaultVisualDataStructureFont"));
		defaultShape = Main.config.getProperty("defaultVisualDataStructureShape");
		defaultShapeHeight = Double.parseDouble(Main.config.getProperty("defaultShapeHeight"));
		defaultShapeWidth = Double.parseDouble(Main.config.getProperty("defaultShapeWidth"));
		defaultLineColor = new Color(Integer.parseInt(Main.config.getProperty("defaultVisualDataStructureLineColor"), 16));
		defaultLineThickness = Float.parseFloat(Main.config.getProperty("defaultVisualDataStructureLineThickness"));
		defaultLineType = LineType.valueOf(Main.config.getProperty("defaultVisualDataStructureLineType"));
		defaultOriginX = Integer.parseInt(Main.config.getProperty("defaultVisualDataStructureOriginX"));
		defaultOriginY = Integer.parseInt(Main.config.getProperty("defaultVisualDataStructureOriginY"));
		isMultiPane = Boolean.parseBoolean(Main.config.getProperty("defaultMultiPaneViewEnabled"));
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public Font getDefaultFont() {
		return defaultFont;
	}

	public Color getDefaultLineColor() {
		return defaultLineColor;
	}

	public Float getDefaultLineThickness() {
		return defaultLineThickness;
	}

	public LineType getDefaultLineType() {
		return defaultLineType;
	}

	public int getDefaultOriginX() {
		return defaultOriginX;
	}

	public int getDefaultOriginY() {
		return defaultOriginY;
	}

	public String getDefaultShape() {
		return defaultShape;
	}

	public Double getDefaultShapeHeight() {
		return defaultShapeHeight;
	}

	public Double getDefaultShapeWidth() {
		return defaultShapeWidth;
	}

	public String getName() {
		return name;
	}

	public int getOriginX() {
		return originX;
	}

	public int getOriginY() {
		return originY;
	}

	public boolean isMultiPane() {
		return isMultiPane;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	public void setDefaultFont(Font defaultFont) {
		this.defaultFont = defaultFont;
	}

	public void setDefaultLineColor(Color defaultLineColor) {
		this.defaultLineColor = defaultLineColor;
	}

	public void setDefaultLineThickness(Float defaultLineThickness) {
		this.defaultLineThickness = defaultLineThickness;
	}

	public void setDefaultLineType(LineType defaultLineType) {
		this.defaultLineType = defaultLineType;
	}

	public void setDefaultOriginX(int originX) {
		defaultOriginX = originX;
	}

	public void setDefaultOriginY(int originY) {
		defaultOriginY = originY;
	}

	public void setDefaultShape(String defaultShape) {
		this.defaultShape = defaultShape;
	}

	public void setDefaultShapeHeight(Double defaultShapeHeight) {
		this.defaultShapeHeight = defaultShapeHeight;
	}

	public void setDefaultShapeWidth(Double defaultShapeWidth) {
		this.defaultShapeWidth = defaultShapeWidth;
	}

	public void setIsMultiPane(boolean isMultiPane) {
		this.isMultiPane = isMultiPane;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}

}
