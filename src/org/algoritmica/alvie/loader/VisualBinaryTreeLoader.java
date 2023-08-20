package org.algoritmica.alvie.loader;

import org.algoritmica.alvie.bean.VisualBinaryTreeBean;
import org.algoritmica.alvie.bean.VisualLinkedStructureBean;
import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.drawer.BinaryTreeGraphicDrawer;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.information.InformationI;

/*
 * This class loads the visual counter part of a binary tree: by making use of
 * the information contained in the corresponding visual binary tree bean, it
 * sets the binary tree graphic drawer default color, font, shape, element
 * height and width, the binary tree graphic drawer origin coordinates, and the
 * default link color, type, and thickness.
 */
@SuppressWarnings({ "rawtypes" })
public class VisualBinaryTreeLoader<I extends InformationI> implements VisualLoaderI {

	public void loadDrawer(VisualStructureBean visualStructureBean, DrawerA drawer) {
		VisualBinaryTreeBean visualBinaryTreeBean = (VisualBinaryTreeBean) visualStructureBean;
		drawer.setOriginX(visualBinaryTreeBean.getOriginX());
		drawer.setOriginY(visualBinaryTreeBean.getOriginY());
		drawer.setDefaultColor(visualBinaryTreeBean.getDefaultColor());
		drawer.setDefaultFont(visualBinaryTreeBean.getDefaultFont());
		drawer.setDefaultShape(visualBinaryTreeBean.getDefaultShape());
		drawer.setDefaultShapeHeight(visualBinaryTreeBean.getDefaultShapeHeight());
		drawer.setDefaultShapeWidth(visualBinaryTreeBean.getDefaultShapeWidth());
		drawer.setDefaultLineColor(visualBinaryTreeBean.getDefaultLineColor());
		drawer.setDefaultLineThickness(visualBinaryTreeBean.getDefaultLineThickness());
		drawer.setDefaultLineType(visualBinaryTreeBean.getDefaultLineType());

	}

	public void loadDrawer(VisualLinkedStructureBean visualStructureBean, BinaryTreeGraphicDrawer<I> drawer) {
		loadDrawer(visualStructureBean, (DrawerA) drawer);
	}
}
