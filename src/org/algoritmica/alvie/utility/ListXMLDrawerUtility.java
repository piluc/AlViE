package org.algoritmica.alvie.utility;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.ListI;
import org.algoritmica.alvie.drawer.ListXMLDrawer;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.visualdatastructure.VisualList;

public class ListXMLDrawerUtility<I extends InformationI> extends
		StructureXMLDrawerUtility<ListI<I>, ListXMLDrawer<I>> {
	private VisualList<I> visualDataStructure;

	public ListXMLDrawerUtility(ListI<I> l, String name, OutputStream os) {
		super(l, os);
		visualDataStructure = new VisualList<I>();
		drawer = new ListXMLDrawer<I>(name, os);
		visualDataStructure.setXMLDrawer(drawer);
	}

	public void draw(Integer[] index, String[] color, String[] font,
			String[] shape, String[] height, String[] width,
			Integer[] lineIndex, String[] lineColor, String[] lineFont,
			String[] lineThickness, String message) {
		startGraphicStructure(drawer.getName(), LIST);
		drawer.draw(index, color, font, shape, height, width, lineIndex,
				lineColor, lineFont, lineThickness, dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}

	public void draw(Integer[] index, String[] color, String message) {
		if (index != null) {
			String[] font = new String[index.length];
			String[] height = new String[index.length];
			String[] shape = new String[index.length];
			String[] width = new String[index.length];
			for (int i = 0; i < index.length; i++) {
				font[i] = FontUtility.getStringFont(drawer.getDefaultFont());
				height[i] = drawer.getDefaultShapeHeight().toString();
				shape[i] = drawer.getDefaultShape();
				width[i] = drawer.getDefaultShapeWidth().toString();
			}
			draw(index, color, font, shape, height, width, null, null, null,
					null, message);
		} else {
			draw(null, null, null, null, null, null, null, null, null, null,
					message);
		}
	}

	public void draw(String message) {
		startGraphicStructure(drawer.getName(), LIST);
		drawer.draw(null, null, null, null, null, null, null, null, null, null,
				dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}
}
