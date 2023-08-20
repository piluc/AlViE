package org.algoritmica.alvie.visualdatastructure;

import org.algoritmica.alvie.bean.VisualGeometricFigureBean;
import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.drawer.DrawerI;
import org.algoritmica.alvie.drawer.GeometricFigureGraphicDrawer;
import org.algoritmica.alvie.drawer.GeometricFigureTextDrawer;
import org.algoritmica.alvie.drawer.GeometricFigureXMLDrawer;

public class VisualGeometricFigure implements VisualDataStructureI<GeometricFigure, VisualGeometricFigureBean> {
	GeometricFigureGraphicDrawer graphicDrawer;
	GeometricFigureTextDrawer textDrawer;
	GeometricFigureXMLDrawer xmlDrawer;

	public void setGraphicDrawer(DrawerI drawer) {
		graphicDrawer = (GeometricFigureGraphicDrawer) drawer;
	}

	public void setTextDrawer(DrawerI drawer) {
		textDrawer = (GeometricFigureTextDrawer) drawer;
	}

	public void setXMLDrawer(DrawerI drawer) {
		xmlDrawer = (GeometricFigureXMLDrawer) drawer;
	}

	public void graphicDraw(GeometricFigure dataStructure, VisualGeometricFigureBean visualStructureBean) {
		graphicDrawer.draw(dataStructure, visualStructureBean);
	}

	public void textDraw(GeometricFigure dataStructure) {
		textDrawer.draw(dataStructure);
	}

	public void xmlDraw(GeometricFigure dataStructure) {
		xmlDrawer.draw(dataStructure);
	}

	public void graphicMessage(String message) {
		graphicDrawer.drawMessage(message);
	}

	public void textMessage(String message) {
		textDrawer.drawMessage(message);
	}

	public void xmlMessage(String message) {
		xmlDrawer.drawMessage(message);
	}

}
