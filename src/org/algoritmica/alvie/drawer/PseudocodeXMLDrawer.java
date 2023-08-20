package org.algoritmica.alvie.drawer;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ColorUtility;
import org.algoritmica.alvie.utility.FontUtility;

public class PseudocodeXMLDrawer extends ArrayXMLDrawer<StringInformation> {
	public PseudocodeXMLDrawer(String name, OutputStream outputStream) {
		super(name, outputStream);
	}

	public void draw(ArrayI<StringInformation> array) {
		int size = array.length();
		xmlDrawer.write("<pseudocode size=\"" + size + "\">");
		String codeLine;
		for (int c = 0; c < size; c++) {
			codeLine = array.elementAt(c).stringValue();
			codeLine = codeLine.replaceAll("<", "&#60;");
			codeLine = codeLine.replaceAll("&&", "&#38;&#38;");
			xmlDrawer.write("<element id=\"" + c + "\" value=\"" + codeLine + "\"/>");
		}
		xmlDrawer.write("</pseudocode>");
	}

	private void drawVisual(Integer[] index, String[] color, String[] font, String[] height, String[] width) {
		xmlDrawer.write("<visualPseudocode defaultColor=\"" + ColorUtility.getStringColor(getDefaultColor()) + "\" defaultFont=\"" + FontUtility.getStringFont(getDefaultFont()) + "\" defaultShapeHeight=\"" + getDefaultShapeHeight() + "\" defaultShapeWidth=\"" + getDefaultShapeWidth() + "\" originX=\"" + getOriginX() + "\" originY=\"" + getOriginY() + "\">");
		if (index != null) {
			for (int c = 0; c < index.length; c++) {
				xmlDrawer.write("<visualMalleableElement id=\"" + index[c] + "\" color=\"" + color[c] + "\" font=\"" + font[c] + "\" shapeHeight=\"" + height[c] + "\" shapeWidth=\"" + width[c] + "\"/>");
			}
		}
		xmlDrawer.write("</visualPseudocode>");
	}

	public void draw(Integer[] index, String[] color, String[] font, String[] height, String[] width, ArrayI<StringInformation> array) {
		draw(array);
		drawVisual(index, color, font, height, width);
	}

}
