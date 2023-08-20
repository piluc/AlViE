package org.algoritmica.alvie.visualdatastructure;

import org.algoritmica.alvie.bean.VisualBinaryTreeBean;
import org.algoritmica.alvie.datastructure.BinaryTreeI;
import org.algoritmica.alvie.drawer.BinaryTreeGraphicDrawer;
import org.algoritmica.alvie.drawer.BinaryTreeTextDrawer;
import org.algoritmica.alvie.drawer.BinaryTreeXMLDrawer;
import org.algoritmica.alvie.drawer.DrawerI;
import org.algoritmica.alvie.information.InformationI;

public class VisualBinaryTree<I extends InformationI> implements VisualDataStructureI<BinaryTreeI<I>, VisualBinaryTreeBean> {
	BinaryTreeGraphicDrawer<I> graphicDrawer;
	BinaryTreeTextDrawer<I> textDrawer;
	BinaryTreeXMLDrawer<I> xmlDrawer;

	@SuppressWarnings("unchecked")
	public void setGraphicDrawer(DrawerI drawer) {
		graphicDrawer = (BinaryTreeGraphicDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setTextDrawer(DrawerI drawer) {
		textDrawer = (BinaryTreeTextDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setXMLDrawer(DrawerI drawer) {
		xmlDrawer = (BinaryTreeXMLDrawer<I>) drawer;
	}

	public void graphicDraw(BinaryTreeI<I> dataStructure, VisualBinaryTreeBean visualStructureBean) {
		graphicDrawer.draw(dataStructure, visualStructureBean);
	}

	public void textDraw(BinaryTreeI<I> dataStructure) {
		textDrawer.draw(dataStructure);
	}

	public void xmlDraw(BinaryTreeI<I> dataStructure) {
		xmlDrawer.draw(dataStructure);
	}

	public void graphicMessage(String message) {
		graphicDrawer.drawMessage(message);
	}

	public void textMessage(String message) {
		textDrawer.drawMessage(message);
	}

	public void xmlMessage(String m) {
		xmlDrawer.drawMessage(m);
	}

}
