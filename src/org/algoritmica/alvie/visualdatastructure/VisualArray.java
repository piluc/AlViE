package org.algoritmica.alvie.visualdatastructure;

import org.algoritmica.alvie.bean.VisualArrayBean;
import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.drawer.ArrayGraphicDrawer;
import org.algoritmica.alvie.drawer.ArrayTextDrawer;
import org.algoritmica.alvie.drawer.ArrayXMLDrawer;
import org.algoritmica.alvie.drawer.DrawerI;
import org.algoritmica.alvie.information.InformationI;

public class VisualArray<I extends InformationI> implements VisualDataStructureI<ArrayI<I>, VisualArrayBean> {
	ArrayGraphicDrawer<I> graphicDrawer;
	ArrayTextDrawer<I> textDrawer;
	ArrayXMLDrawer<I> xmlDrawer;

	public void graphicDraw(ArrayI<I> ds, VisualArrayBean bean) {
		graphicDrawer.draw(ds, bean);
	}

	public void graphicMessage(String m) {
		graphicDrawer.drawMessage(m);
	}

	@SuppressWarnings("unchecked")
	public void setGraphicDrawer(DrawerI drawer) {
		graphicDrawer = (ArrayGraphicDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setTextDrawer(DrawerI drawer) {
		textDrawer = (ArrayTextDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setXMLDrawer(DrawerI drawer) {
		xmlDrawer = (ArrayXMLDrawer<I>) drawer;
	}

	public void textDraw(ArrayI<I> ds) {
		textDrawer.draw(ds);
	}

	public void textMessage(String m) {
		textDrawer.drawMessage(m);
	}

	public void xmlDraw(ArrayI<I> ds) {
		xmlDrawer.draw(ds);
	}

	public void xmlMessage(String m) {
		xmlDrawer.drawMessage(m);
	}

}
