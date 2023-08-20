package org.algoritmica.alvie.drawer;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.ListI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.ColorUtility;
import org.algoritmica.alvie.utility.FontUtility;

/*
 * This is the XML list drawer. The XML list, element, visual list and visual element
 * definition is specified in the algorithm XSD file. The message drawing is delegated
 * to an XML drawer.
 */
public class ListXMLDrawer<I extends InformationI> extends ListDrawerA {
	protected XMLDrawer xmlDrawer;

	public ListXMLDrawer(String name, OutputStream outputStream) {
		super(name);
		xmlDrawer = new XMLDrawer(outputStream);
	}

	public void draw(ListI<I> list) {
		int size = list.size();
		xmlDrawer.write("<list size=\"" + size + "\" type=\"" + list.getType() + "\">");
		for (int c = 0; c < size; c++) {
			xmlDrawer.write("<element id=\"" + c + "\" value=\"" + list.head().stringValue() + "\"/>");
			list = list.tail();
		}
		xmlDrawer.write("</list>");
	}

	private void drawVisual(Integer[] position, String[] color, String[] font, String[] shape, String[] height, String[] width, Integer[] linePosition, String[] lineType, String[] lineColor, String[] lineThickness) {
		xmlDrawer.write("<visualList defaultColor=\"" + ColorUtility.getStringColor(getDefaultColor()) + "\" defaultFont=\"" + FontUtility.getStringFont(getDefaultFont()) + "\" defaultShape=\"" + getDefaultShape() + "\" defaultShapeHeight=\"" + getDefaultShapeHeight() + "\" defaultShapeWidth=\"" + getDefaultShapeWidth() + "\"  defaultLineColor=\"" + ColorUtility.getStringColor(getDefaultLineColor()) + "\" defaultLineType=\"" + getDefaultLineType() + "\" defaultLineThickness=\""
				+ getDefaultLineThickness() + "\" originX=\"" + getOriginX() + "\" originY=\"" + getOriginY() + "\">");
		if (position != null) {
			for (int c = 0; c < position.length; c++) {
				xmlDrawer.write("<visualLinkedElement id=\"" + position[c] + "\" color=\"" + color[c] + "\" font=\"" + font[c] + "\" shape=\"" + shape[c] + "\" shapeHeight=\"" + height[c] + "\" shapeWidth=\"" + width[c] + "\"/>");

			}
		}
		if (linePosition != null) {
			for (int c = 0; c < linePosition.length; c++) {
				xmlDrawer.write("<visualLink id=\"" + linePosition[c] + "\" lineColor=\"" + lineColor[c] + "\" lineType=\"" + lineType[c] + "\" lineThickness=\"" + lineThickness[c] + "\"/>");
			}
		}
		xmlDrawer.write("</visualList>");
	}

	public void draw(Integer[] position, String[] color, String[] font, String[] shape, String[] height, String[] width, Integer[] linePosition, String[] lineType, String[] lineColor, String[] lineThickness, ListI<I> list) {
		draw(list);
		drawVisual(position, color, font, shape, height, width, linePosition, lineType, lineColor, lineThickness);
	}

	public void drawMessage(String message) {
		xmlDrawer.drawMessage(message);
	}

	public XMLDrawer getXMLDrawer() {
		return xmlDrawer;
	}

}
