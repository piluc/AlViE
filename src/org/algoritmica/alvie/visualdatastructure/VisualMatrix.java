package org.algoritmica.alvie.visualdatastructure;

import org.algoritmica.alvie.bean.VisualMatrixBean;
import org.algoritmica.alvie.datastructure.MatrixI;
import org.algoritmica.alvie.drawer.DrawerI;
import org.algoritmica.alvie.drawer.MatrixGraphicDrawer;
import org.algoritmica.alvie.drawer.MatrixTextDrawer;
import org.algoritmica.alvie.drawer.MatrixXMLDrawer;
import org.algoritmica.alvie.information.InformationI;

public class VisualMatrix<I extends InformationI> implements VisualDataStructureI<MatrixI<I>, VisualMatrixBean> {
	MatrixGraphicDrawer<I> graphicDrawer;
	MatrixTextDrawer<I> textDrawer;
	MatrixXMLDrawer<I> xmlDrawer;

	@SuppressWarnings("unchecked")
	public void setGraphicDrawer(DrawerI drawer) {
		graphicDrawer = (MatrixGraphicDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setTextDrawer(DrawerI drawer) {
		textDrawer = (MatrixTextDrawer<I>) drawer;
	}

	@SuppressWarnings("unchecked")
	public void setXMLDrawer(DrawerI drawer) {
		xmlDrawer = (MatrixXMLDrawer<I>) drawer;
	}

	public void graphicDraw(MatrixI<I> dataStructure, VisualMatrixBean visualStructureBean) {
		graphicDrawer.draw(dataStructure, visualStructureBean);
	}

	public void textDraw(MatrixI<I> dataStructure) {
		textDrawer.draw(dataStructure);
	}

	public void xmlDraw(MatrixI<I> dataStructure) {
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
