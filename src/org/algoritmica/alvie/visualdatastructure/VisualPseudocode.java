package org.algoritmica.alvie.visualdatastructure;

import org.algoritmica.alvie.bean.VisualPseudocodeBean;
import org.algoritmica.alvie.datastructure.Pseudocode;
import org.algoritmica.alvie.drawer.DrawerI;
import org.algoritmica.alvie.drawer.PseudocodeGraphicDrawer;
import org.algoritmica.alvie.drawer.PseudocodeTextDrawer;
import org.algoritmica.alvie.drawer.PseudocodeXMLDrawer;

public class VisualPseudocode implements VisualDataStructureI<Pseudocode, VisualPseudocodeBean> {
	PseudocodeGraphicDrawer graphicDrawer;
	PseudocodeTextDrawer textDrawer;
	PseudocodeXMLDrawer xmlDrawer;

	public void setGraphicDrawer(DrawerI drawer) {
		graphicDrawer = (PseudocodeGraphicDrawer) drawer;
	}

	public void setTextDrawer(DrawerI drawer) {
		textDrawer = (PseudocodeTextDrawer) drawer;
	}

	public void setXMLDrawer(DrawerI drawer) {
		xmlDrawer = (PseudocodeXMLDrawer) drawer;
	}

	public void graphicDraw(Pseudocode dataStructure, VisualPseudocodeBean bean) {
		graphicDrawer.draw(dataStructure, bean);
	}

	public void textDraw(Pseudocode dataStructure) {
		textDrawer.draw(dataStructure);
	}

	public void xmlDraw(Pseudocode dataStructure) {
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
