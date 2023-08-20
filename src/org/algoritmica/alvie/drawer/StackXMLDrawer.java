package org.algoritmica.alvie.drawer;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.StackI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.ColorUtility;
import org.algoritmica.alvie.utility.FontUtility;

/*
 * This is the XML stack drawer. The XML stack, element, visual stack and visual element
 * definition is specified in the algorithm XSD file. In order to access all elements of the stack,
 * a temporary array has to be used. The message drawing is delegated
 * to an XML drawer.
 */
public class StackXMLDrawer<I extends InformationI> extends StackDrawerA {
	protected XMLDrawer xmlDrawer;

	public StackXMLDrawer(String name, OutputStream outputStream) {
		super(name);
		xmlDrawer = new XMLDrawer(outputStream);
	}

	@SuppressWarnings("unchecked")
	public void draw(StackI<I> stack) {
		int size = stack.size();
		I[] a = (I[]) (new InformationI[size]);
		int i = size - 1;
		while (stack.size() > 0) {
			a[i] = stack.top();
			stack.pop();
			i = i - 1;
		}
		xmlDrawer.write("<stack size=\"" + size + "\" type=\"" + stack.getType() + "\">");
		for (int c = 0; c < size; c++) {
			xmlDrawer.write("<element id=\"" + c + "\" value=\"" + a[c].stringValue() + "\"/>");
		}
		xmlDrawer.write("</stack>");
		for (int k = 0; k < size; k++) {
			stack.push(a[k]);
		}
	}

	private void drawVisual(Integer[] index, String[] color, String[] font, String[] height, String[] width) {
		xmlDrawer.write("<visualStack defaultColor=\"" + ColorUtility.getStringColor(getDefaultColor()) + "\" defaultFont=\"" + FontUtility.getStringFont(getDefaultFont()) + "\" defaultShapeHeight=\"" + getDefaultShapeHeight() + "\" defaultShapeWidth=\"" + getDefaultShapeWidth() + "\" originX=\"" + getOriginX() + "\" originY=\"" + getOriginY() + "\">");
		if (index != null) {
			for (int c = 0; c < index.length; c++) {
				xmlDrawer.write("<visualMalleableElement id=\"" + index[c] + "\" color=\"" + color[c] + "\" font=\"" + font[c] + "\" shapeHeight=\"" + height[c] + "\" shapeWidth=\"" + width[c] + "\"/>");
			}
		}
		xmlDrawer.write("</visualStack>");
	}

	public void draw(Integer[] index, String[] color, String[] font, String[] height, String[] width, StackI<I> stack) {
		draw(stack);
		drawVisual(index, color, font, height, width);
	}

	public void drawMessage(String message) {
		xmlDrawer.drawMessage(message);
	}

	public XMLDrawer getXMLDrawer() {
		return xmlDrawer;
	}

}
