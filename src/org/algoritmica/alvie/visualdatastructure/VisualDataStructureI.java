package org.algoritmica.alvie.visualdatastructure;

import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.datastructure.DataStructureI;
import org.algoritmica.alvie.drawer.DrawerI;
import org.algoritmica.alvie.information.InformationI;

/*
 * To each visual data structure three kinds of drawer can be associated: the text 
 * drawer (which prints on the standard output), the XML drawer (which writes into
 * an XML file), and the graphic drawer (which draws into a view panel). Each drawer
 * can be used to 'draw' the data structure and to 'draw' a message.
 */
public interface VisualDataStructureI<DS extends DataStructureI<? extends InformationI>, VSB extends VisualStructureBean> {
	public void graphicDraw(DS dataStructure, VSB visualStructureBean);

	public void graphicMessage(String message);

	public void setGraphicDrawer(DrawerI drawer);

	public void setTextDrawer(DrawerI drawer);

	public void setXMLDrawer(DrawerI drawer);

	public void textDraw(DS dataStructure);

	public void textMessage(String message);

	public void xmlDraw(DS dataStructure);

	public void xmlMessage(String message);
}
