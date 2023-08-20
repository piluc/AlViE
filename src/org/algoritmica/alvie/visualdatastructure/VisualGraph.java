package org.algoritmica.alvie.visualdatastructure;

import org.algoritmica.alvie.bean.VisualGraphBean;
import org.algoritmica.alvie.datastructure.GraphI;
import org.algoritmica.alvie.drawer.DrawerI;
import org.algoritmica.alvie.drawer.GraphGraphicDrawer;
import org.algoritmica.alvie.drawer.GraphTextDrawer;
import org.algoritmica.alvie.drawer.GraphXMLDrawer;
import org.algoritmica.alvie.information.InformationI;

public class VisualGraph<IN extends InformationI, IL extends InformationI> implements VisualDataStructureI<GraphI<IN, IL>, VisualGraphBean> {
	GraphGraphicDrawer<IN, IL> graphicDrawer;
	GraphTextDrawer<IN, IL> textDrawer;
	GraphXMLDrawer<IN, IL> xmlDrawer;

	public void graphicDraw(GraphI<IN, IL> dataStructure, VisualGraphBean visualStructureBean) {
		graphicDrawer.draw(dataStructure, visualStructureBean);
	}

	public void graphicMessage(String message) {
		graphicDrawer.drawMessage(message);
	}

	@SuppressWarnings("unchecked")
	public void setGraphicDrawer(DrawerI drawer) {
		graphicDrawer = (GraphGraphicDrawer<IN, IL>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setTextDrawer(DrawerI drawer) {
		textDrawer = (GraphTextDrawer<IN, IL>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setXMLDrawer(DrawerI drawer) {
		xmlDrawer = (GraphXMLDrawer<IN, IL>) drawer;
	}

	public void textDraw(GraphI<IN, IL> dataStructure) {
		textDrawer.draw(dataStructure);
	}

	public void textMessage(String message) {
		textDrawer.drawMessage(message);
	}

	public void xmlDraw(GraphI<IN, IL> dataStructure) {
		xmlDrawer.draw(dataStructure);
	}

	public void xmlMessage(String message) {
		xmlDrawer.drawMessage(message);
	}

}
