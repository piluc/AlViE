package org.algoritmica.alvie.drawer;

import java.awt.Color;
import java.awt.Font;

import org.algoritmica.alvie.datastructure.DataStructureI;
import org.algoritmica.alvie.desktop.Main;
import org.algoritmica.alvie.graphic.LineType;
import org.algoritmica.alvie.information.InformationI;

public abstract class StructureDrawerA<DS extends DataStructureI<? extends InformationI>> implements DrawerI {

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

	public StructureDrawerA(String n) {
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

	public String getName() {
		return name;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	public Font getDefaultFont() {
		return defaultFont;
	}

	public void setDefaultFont(Font defaultFont) {
		this.defaultFont = defaultFont;
	}

	public String getDefaultShape() {
		return defaultShape;
	}

	public void setDefaultShape(String defaultShape) {
		this.defaultShape = defaultShape;
	}

	public Double getDefaultShapeHeight() {
		return defaultShapeHeight;
	}

	public void setDefaultShapeHeight(Double defaultShapeHeight) {
		this.defaultShapeHeight = defaultShapeHeight;
	}

	public Double getDefaultShapeWidth() {
		return defaultShapeWidth;
	}

	public void setDefaultShapeWidth(Double defaultShapeWidth) {
		this.defaultShapeWidth = defaultShapeWidth;
	}

	public int getOriginX() {
		return originX;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}

	public int getOriginY() {
		return originY;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}

	public int getDefaultOriginX() {
		return defaultOriginX;
	}

	public void setDefaultOriginX(int originX) {
		defaultOriginX = originX;
	}

	public int getDefaultOriginY() {
		return defaultOriginY;
	}

	public void setDefaultOriginY(int originY) {
		defaultOriginY = originY;
	}

	public Color getDefaultLineColor() {
		return defaultLineColor;
	}

	public void setDefaultLineColor(Color defaultLineColor) {
		this.defaultLineColor = defaultLineColor;
	}

	public Float getDefaultLineThickness() {
		return defaultLineThickness;
	}

	public void setDefaultLineThickness(Float defaultLineThickness) {
		this.defaultLineThickness = defaultLineThickness;
	}

	public LineType getDefaultLineType() {
		return defaultLineType;
	}

	public void setDefaultLineType(LineType defaultLineType) {
		this.defaultLineType = defaultLineType;
	}

	public boolean isMultiPane() {
		return isMultiPane;
	}

	public void setIsMultiPane(boolean isMultiPane) {
		this.isMultiPane = isMultiPane;
	}

}
