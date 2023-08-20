package org.algoritmica.alvie.drawer;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.MatrixI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.ColorUtility;
import org.algoritmica.alvie.utility.FontUtility;

/*
 * This is the XML matrix drawer. The XML matrix, element, visual matrix, and visual element
 * definition is specified in the algorithm XSD file. The message drawing is delegated
 * to an XML drawer.
 */
public class MatrixXMLDrawer<I extends InformationI> extends MatrixDrawerA {
	protected XMLDrawer xmlDrawer;

	public MatrixXMLDrawer(String name, OutputStream outputStream) {
		super(name);
		xmlDrawer = new XMLDrawer(outputStream);
	}

	public void draw(MatrixI<I> matrix) {
		int height = matrix.height();
		int width = matrix.width();
		xmlDrawer.write("<matrix height=\"" + height + "\" width=\"" + width + "\" type=\"" + matrix.getType() + "\">");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				xmlDrawer.write("	<element id=\"" + (i * width + j) + "\" value=\"" + matrix.elementAt(i, j).stringValue() + "\"/>");
			}
		}
		xmlDrawer.write("</matrix>");
	}

	private void drawVisual(Integer[] index, String[] color, String[] font) {
		xmlDrawer.write("<visualMatrix defaultColor=\"" + ColorUtility.getStringColor(getDefaultColor()) + "\" defaultFont=\"" + FontUtility.getStringFont(getDefaultFont()) + "\" defaultShapeHeight=\"" + getDefaultShapeHeight() + "\" defaultShapeWidth=\"" + getDefaultShapeWidth() + "\" originX=\"" + getOriginX() + "\" originY=\"" + getOriginY() + "\">");
		if (index != null)
			for (int c = 0; c < index.length; c++) {
				xmlDrawer.write("<visualElement id=\"" + index[c] + "\" color=\"" + color[c] + "\" font=\"" + font[c] + "\"/>");

			}
		xmlDrawer.write("</visualMatrix>");
	}

	public void draw(Integer[] index, String[] color, String[] font, MatrixI<I> matrix) {
		draw(matrix);
		drawVisual(index, color, font);
	}

	public void drawMessage(String message) {
		xmlDrawer.drawMessage(message);
	}

	public XMLDrawer getXMLDrawer() {
		return xmlDrawer;
	}

}
