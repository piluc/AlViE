package org.algoritmica.alvie.visualdatastructure;

import org.algoritmica.alvie.bean.VisualStackBean;
import org.algoritmica.alvie.datastructure.StackI;
import org.algoritmica.alvie.drawer.DrawerI;
import org.algoritmica.alvie.drawer.StackGraphicDrawer;
import org.algoritmica.alvie.drawer.StackTextDrawer;
import org.algoritmica.alvie.drawer.StackXMLDrawer;
import org.algoritmica.alvie.information.InformationI;

public class VisualStack<I extends InformationI> implements VisualDataStructureI<StackI<I>, VisualStackBean> {
	StackGraphicDrawer<I> graphicDrawer;
	StackTextDrawer<I> textDrawer;
	StackXMLDrawer<I> xmlDrawer;

	@SuppressWarnings("unchecked")
	public void setGraphicDrawer(DrawerI drawer) {
		graphicDrawer = (StackGraphicDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setTextDrawer(DrawerI drawer) {
		textDrawer = (StackTextDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setXMLDrawer(DrawerI drawer) {
		xmlDrawer = (StackXMLDrawer<I>) drawer;
	}

	public void graphicDraw(StackI<I> dataStructure, VisualStackBean visualStructureBean) {
		graphicDrawer.draw(dataStructure, visualStructureBean);
	}

	public void textDraw(StackI<I> dataStructure) {
		textDrawer.draw(dataStructure);
	}

	public void xmlDraw(StackI<I> dataStructure) {
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
