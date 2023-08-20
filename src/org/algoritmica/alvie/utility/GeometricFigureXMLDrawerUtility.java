package org.algoritmica.alvie.utility;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.drawer.GeometricFigureXMLDrawer;
import org.algoritmica.alvie.visualdatastructure.VisualGeometricFigure;

public class GeometricFigureXMLDrawerUtility extends StructureXMLDrawerUtility<GeometricFigure, GeometricFigureXMLDrawer> {
	private VisualGeometricFigure visualDataStructure;

	public GeometricFigureXMLDrawerUtility(GeometricFigure l, String name, OutputStream os) {
		super(l, os);
		visualDataStructure = new VisualGeometricFigure();
		drawer = new GeometricFigureXMLDrawer(name, os);
		visualDataStructure.setXMLDrawer(drawer);
	}

	public void draw(Integer[] index, String[] color, String[] font, String[] lineThickness, String message) {
		startGraphicStructure(drawer.getName(), GEOMETRICFIGURE);
		drawer.draw(index, color, font, lineThickness, dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}

	public void draw(Integer[] index, String[] color, String message) {
		String[] font = new String[index.length];
		String[] lineThickness = new String[index.length];
		for (int i = 0; i < index.length; i++) {
			font[i] = FontUtility.getStringFont(drawer.getDefaultFont());
			lineThickness[i] = drawer.getDefaultLineThickness().toString();
		}
		draw(index, color, font, lineThickness, message);
	}

	public void draw(String message) {
		startGraphicStructure(drawer.getName(), GEOMETRICFIGURE);
		drawer.draw(null, null, null, null, dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}

	public void setDefaultLineThickness(Float t) {
		drawer.setDefaultLineThickness(t);
	}
}
