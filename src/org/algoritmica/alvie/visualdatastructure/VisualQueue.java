package org.algoritmica.alvie.visualdatastructure;

import org.algoritmica.alvie.bean.VisualQueueBean;
import org.algoritmica.alvie.datastructure.QueueI;
import org.algoritmica.alvie.drawer.DrawerI;
import org.algoritmica.alvie.drawer.QueueGraphicDrawer;
import org.algoritmica.alvie.drawer.QueueTextDrawer;
import org.algoritmica.alvie.drawer.QueueXMLDrawer;
import org.algoritmica.alvie.information.InformationI;

public class VisualQueue<I extends InformationI> implements VisualDataStructureI<QueueI<I>, VisualQueueBean> {
	QueueGraphicDrawer<I> graphicDrawer;
	QueueTextDrawer<I> textDrawer;
	QueueXMLDrawer<I> xmlDrawer;

	@SuppressWarnings("unchecked")
	public void setGraphicDrawer(DrawerI drawer) {
		graphicDrawer = (QueueGraphicDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setTextDrawer(DrawerI drawer) {
		textDrawer = (QueueTextDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setXMLDrawer(DrawerI drawer) {
		xmlDrawer = (QueueXMLDrawer<I>) drawer;
	}

	public void graphicDraw(QueueI<I> dataStructure, VisualQueueBean visualStructureBean) {
		graphicDrawer.draw(dataStructure, visualStructureBean);
	}

	public void textDraw(QueueI<I> dataStructure) {
		textDrawer.draw(dataStructure);
	}

	public void xmlDraw(QueueI<I> dataStructure) {
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
