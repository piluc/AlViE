package org.algoritmica.alvie.utility;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.drawer.ArrayXMLDrawer;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.visualdatastructure.VisualArray;

public class ArrayXMLDrawerUtility<I extends InformationI> extends
		SizedStructureXMLDrawerUtility<ArrayI<I>, ArrayXMLDrawer<I>> {
	private VisualArray<I> visualDataStructure;

	public ArrayXMLDrawerUtility(ArrayI<I> a, String name, OutputStream os) {
		super(a, os);
		visualDataStructure = new VisualArray<I>();
		drawer = new ArrayXMLDrawer<I>(name, os);
		visualDataStructure.setXMLDrawer(drawer);
	}

	public void draw(Integer[] index, String[] color, String[] font,
			String[] height, String[] width, String message) {
		startGraphicStructure(drawer.getName(), ARRAY);
		drawer.draw(index, color, font, height, width, dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}

	public void draw(Integer[] index, String[] color, String message) {
		if (index != null) {
			String[] font = new String[index.length];
			String[] height = new String[index.length];
			String[] width = new String[index.length];
			for (int i = 0; i < index.length; i++) {
				font[i] = FontUtility.getStringFont(drawer.getDefaultFont());
				height[i] = drawer.getDefaultShapeHeight().toString();
				width[i] = drawer.getDefaultShapeWidth().toString();
			}
			draw(index, color, font, height, width, message);
		} else {
			draw(null, null, null, null, null, message);
		}
	}

	public void draw(String message) {
		startGraphicStructure(drawer.getName(), ARRAY);
		drawer.draw(null, null, null, null, null, dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}
}
