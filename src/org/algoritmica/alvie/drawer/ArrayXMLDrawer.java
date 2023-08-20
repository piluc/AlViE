package org.algoritmica.alvie.drawer;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.ColorUtility;
import org.algoritmica.alvie.utility.FontUtility;

/*
 * This is the XML array drawer. The XML array, element, visual array and visual element
 * definition is specified in the algorithm XSD file. The message drawing is delegated
 * to an XML drawer.
 */
public class ArrayXMLDrawer<I extends InformationI> extends ArrayDrawerA {
	protected XMLDrawer xmlDrawer;

	public ArrayXMLDrawer(String name, OutputStream outputStream) {
		super(name);
		xmlDrawer = new XMLDrawer(outputStream);
	}

	public void draw(ArrayI<I> array) {
		int size = array.length();
		xmlDrawer.write("<array size=\"" + size + "\" type=\"" + array.getType() + "\">");
		for (int c = 0; c < size; c++) {
			xmlDrawer.write("<element id=\"" + c + "\" value=\"" + array.elementAt(c).stringValue() + "\"/>");
		}
		xmlDrawer.write("</array>");
	}

	private void drawVisual(Integer[] index, String[] color, String[] font, String[] height, String[] width) {
		xmlDrawer.write("<visualArray defaultColor=\"" + ColorUtility.getStringColor(getDefaultColor()) + "\" defaultFont=\"" + FontUtility.getStringFont(getDefaultFont()) + "\" defaultShapeHeight=\"" + getDefaultShapeHeight() + "\" defaultShapeWidth=\"" + getDefaultShapeWidth() + "\" originX=\"" + getOriginX() + "\" originY=\"" + getOriginY() + "\">");
		if (index != null) {
			for (int c = 0; c < index.length; c++) {
				xmlDrawer.write("<visualMalleableElement id=\"" + index[c] + "\" color=\"" + color[c] + "\" font=\"" + font[c] + "\" shapeHeight=\"" + height[c] + "\" shapeWidth=\"" + width[c] + "\"/>");
			}
		}
		xmlDrawer.write("</visualArray>");
	}

	public void draw(Integer[] index, String[] color, String[] font, String[] height, String[] width, ArrayI<I> array) {
		draw(array);
		drawVisual(index, color, font, height, width);
	}

	public void drawMessage(String message) {
		xmlDrawer.drawMessage(message);
	}

	public XMLDrawer getXMLDrawer() {
		return xmlDrawer;
	}

}
