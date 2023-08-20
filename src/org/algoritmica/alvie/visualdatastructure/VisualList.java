package org.algoritmica.alvie.visualdatastructure;

import org.algoritmica.alvie.bean.VisualListBean;
import org.algoritmica.alvie.datastructure.ListI;
import org.algoritmica.alvie.drawer.DrawerI;
import org.algoritmica.alvie.drawer.ListGraphicDrawer;
import org.algoritmica.alvie.drawer.ListTextDrawer;
import org.algoritmica.alvie.drawer.ListXMLDrawer;
import org.algoritmica.alvie.information.InformationI;

public class VisualList<I extends InformationI> implements VisualDataStructureI<ListI<I>, VisualListBean> {
	ListGraphicDrawer<I> graphicDrawer;
	ListTextDrawer<I> textDrawer;
	ListXMLDrawer<I> xmlDrawer;

	@SuppressWarnings("unchecked")
	public void setGraphicDrawer(DrawerI drawer) {
		graphicDrawer = (ListGraphicDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setTextDrawer(DrawerI drawer) {
		textDrawer = (ListTextDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setXMLDrawer(DrawerI drawer) {
		xmlDrawer = (ListXMLDrawer<I>) drawer;
	}

	public void graphicDraw(ListI<I> dataStructure, VisualListBean visualStructureBean) {
		graphicDrawer.draw(dataStructure, visualStructureBean);
	}

	public void textDraw(ListI<I> dataStructure) {
		textDrawer.draw(dataStructure);
	}

	public void xmlDraw(ListI<I> dataStructure) {
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
