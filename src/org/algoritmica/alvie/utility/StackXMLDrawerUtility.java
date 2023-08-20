package org.algoritmica.alvie.utility;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.StackI;
import org.algoritmica.alvie.drawer.StackXMLDrawer;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.visualdatastructure.VisualStack;

public class StackXMLDrawerUtility<I extends InformationI> extends
		StructureXMLDrawerUtility<StackI<I>, StackXMLDrawer<I>> {
	private VisualStack<I> visualDataStructure;

	public StackXMLDrawerUtility(StackI<I> s, String name, OutputStream os) {
		super(s, os);
		visualDataStructure = new VisualStack<I>();
		drawer = new StackXMLDrawer<I>(name, os);
		visualDataStructure.setXMLDrawer(drawer);
	}

	public void draw(Integer[] index, String[] color, String[] font,
			String[] height, String[] width, String message) {
		startGraphicStructure(drawer.getName(), STACK);
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
		startGraphicStructure(drawer.getName(), STACK);
		drawer.draw(null, null, null, null, null, dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}
}
