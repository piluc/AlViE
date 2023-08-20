package org.algoritmica.alvie.drawer;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.QueueI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.ColorUtility;
import org.algoritmica.alvie.utility.FontUtility;

/*
 * This is the XML queue drawer. The XML queue, element, visual queue and visual element
 * definition is specified in the algorithm XSD file. In order to access all elements of the queue,
 * a temporary array has to be used. The message drawing is delegated
 * to an XML drawer.
 */
public class QueueXMLDrawer<I extends InformationI> extends QueueDrawerA {
	protected XMLDrawer xmlDrawer;

	public QueueXMLDrawer(String name, OutputStream outputStream) {
		super(name);
		xmlDrawer = new XMLDrawer(outputStream);
	}

	@SuppressWarnings("unchecked")
	public void draw(QueueI<I> queue) {
		int size = queue.size();
		I[] a = (I[]) (new InformationI[size]);
		int i = 0;
		while (queue.size() > 0) {
			a[i] = queue.first();
			queue.dequeue();
			i = i + 1;
		}
		xmlDrawer.write("<queue size=\"" + size + "\" type=\""
				+ queue.getType() + "\">");
		for (int c = 0; c < size; c++) {
			xmlDrawer.write("<element id=\"" + c + "\" value=\""
					+ a[c].stringValue() + "\"/>");
		}
		xmlDrawer.write("</queue>");
		for (int k = 0; k < size; k++) {
			queue.enqueue(a[k]);
		}
	}

	private void drawVisual(Integer[] index, String[] color, String[] font,
			String[] height, String[] width) {
		xmlDrawer.write("<visualQueue defaultColor=\""
				+ ColorUtility.getStringColor(getDefaultColor())
				+ "\" defaultFont=\""
				+ FontUtility.getStringFont(getDefaultFont())
				+ "\" defaultShapeHeight=\"" + getDefaultShapeHeight()
				+ "\" defaultShapeWidth=\"" + getDefaultShapeWidth()
				+ "\" originX=\"" + getOriginX() + "\" originY=\""
				+ getOriginY() + "\">");
		if (index != null) {
			for (int c = 0; c < index.length; c++) {
				xmlDrawer.write("<visualMalleableElement id=\"" + index[c]
						+ "\" color=\"" + color[c] + "\" font=\"" + font[c]
						+ "\" shapeHeight=\"" + height[c] + "\" shapeWidth=\""
						+ width[c] + "\"/>");
			}
		}
		xmlDrawer.write("</visualQueue>");
	}

	public void draw(Integer[] index, String[] color, String[] font,
			String[] height, String[] width, QueueI<I> queue) {
		draw(queue);
		drawVisual(index, color, font, height, width);
	}

	public void drawMessage(String message) {
		xmlDrawer.drawMessage(message);
	}

	public XMLDrawer getXMLDrawer() {
		return xmlDrawer;
	}

}
