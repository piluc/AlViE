package org.algoritmica.alvie.drawer;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.utility.ColorUtility;
import org.algoritmica.alvie.utility.FontUtility;

/*
 * This is the XML geometric figure drawer. The XML figure, element, visual figure and visual element
 * definition is specified in the algorithm XSD file. The message drawing is delegated
 * to an XML drawer.
 */
public class GeometricFigureXMLDrawer extends GeometricFigureDrawerA {
	protected XMLDrawer xmlDrawer;

	public GeometricFigureXMLDrawer(String name, OutputStream outputStream) {
		super(name);
		xmlDrawer = new XMLDrawer(outputStream);
	}

	public void draw(GeometricFigure figure) {
		int size = figure.length();
		xmlDrawer.write("<geometricFigure size=\"" + size + "\">");
		for (int c = 0; c < size; c++) {
			xmlDrawer.write("<element id=\"" + c + "\" value=\"" + figure.elementAt(c).stringValue() + "\"/>");
		}
		xmlDrawer.write("</geometricFigure>");
	}

	private void drawVisual(Integer[] position, String[] color, String[] font, String[] lineThickness) {
		xmlDrawer.write("<visualGeometricFigure defaultColor=\"" + ColorUtility.getStringColor(getDefaultColor()) + "\" defaultFont=\"" + FontUtility.getStringFont(getDefaultFont()) + "\" defaultLineThickness=\"" + getDefaultLineThickness() + "\" originX=\"" + getOriginX() + "\" originY=\"" + getOriginY() + "\">");
		if (position != null) {
			for (int c = 0; c < position.length; c++) {
				xmlDrawer.write("<visualGeometricObject id=\"" + position[c] + "\" color=\"" + color[c] + "\" font=\"" + font[c] + "\" lineThickness=\"" + lineThickness[c] + "\"/>");
			}
		}
		xmlDrawer.write("</visualGeometricFigure>");
	}

	public void draw(Integer[] position, String[] color, String[] font, String[] lineThickness, GeometricFigure figure) {
		draw(figure);
		drawVisual(position, color, font, lineThickness);
	}

	public void drawMessage(String message) {
		xmlDrawer.drawMessage(message);
	}

	public XMLDrawer getXMLDrawer() {
		return xmlDrawer;
	}

}
