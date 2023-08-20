package org.algoritmica.alvie.bean;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

/*
 * This the visual graph bean which adds to the visual linked data structure bean
 * the possibility of managing the default arc label font and, for each arc, its 
 * label font.
 */
public class VisualGraphBean extends VisualLinkedStructureBean {
	private Font defaultArcLabelFont;

	private Map<Integer, Font> arcLabelFontCollection = new HashMap<Integer, Font>();

	public Map<Integer, Font> getArcLabelFontCollection() {
		return arcLabelFontCollection;
	}

	public Font getDefaultArcLabelFont() {
		return defaultArcLabelFont;
	}

	public void setArcLabelFont(String position, String fontString) {
		Font font = Font.decode(fontString);
		this.arcLabelFontCollection.put(Integer.parseInt(position), font);
	}

	public void setDefaultArcLabelFont(String fontString) {
		Font font = Font.decode(fontString);
		defaultArcLabelFont = font;
	}
}
