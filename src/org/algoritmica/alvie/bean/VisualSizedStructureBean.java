package org.algoritmica.alvie.bean;

/*
 * This is the bean common to all data structures whose elements have shape dimensions (that is,
 * all data structures but the geometric figure). It allows us to manage the default element 
 * shape height and width.
 */
public class VisualSizedStructureBean extends VisualStructureBean {
	private Double defaultShapeHeight;

	private Double defaultShapeWidth;

	public Double getDefaultShapeHeight() {
		return defaultShapeHeight;
	}

	public Double getDefaultShapeWidth() {
		return defaultShapeWidth;
	}

	public void setDefaultShapeHeight(String defaultShapeHeight) {
		this.defaultShapeHeight = Double.parseDouble(defaultShapeHeight);
	}

	public void setDefaultShapeWidth(String defaultShapeWidth) {
		this.defaultShapeWidth = Double.parseDouble(defaultShapeWidth);
	}
}
