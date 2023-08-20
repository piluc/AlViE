package org.algoritmica.alvie.loader;

import org.algoritmica.alvie.bean.VisualLinkedStructureBean;
import org.algoritmica.alvie.bean.VisualListBean;
import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.drawer.ListGraphicDrawer;
import org.algoritmica.alvie.information.InformationI;

@SuppressWarnings({ "rawtypes" })
/*
 * This class loads the visual counter part of a list: by making use of the
 * information contained in the corresponding visual list bean, it sets the list
 * graphic drawer default color, font, shape, element height and width, the list
 * graphic drawer origin coordinates, and the default link color, type, and
 * thickness.
 */
public class VisualListLoader<I extends InformationI> implements VisualLoaderI {

	public void loadDrawer(VisualStructureBean visualStructureBean, DrawerA drawer) {
		VisualListBean visualListBean = (VisualListBean) visualStructureBean;
		drawer.setOriginX(visualListBean.getOriginX());
		drawer.setOriginY(visualListBean.getOriginY());
		drawer.setDefaultColor(visualListBean.getDefaultColor());
		drawer.setDefaultFont(visualListBean.getDefaultFont());
		drawer.setDefaultShape(visualListBean.getDefaultShape());
		drawer.setDefaultShapeHeight(visualListBean.getDefaultShapeHeight());
		drawer.setDefaultShapeWidth(visualListBean.getDefaultShapeWidth());
		drawer.setDefaultLineColor(visualListBean.getDefaultLineColor());
		drawer.setDefaultLineThickness(visualListBean.getDefaultLineThickness());
		drawer.setDefaultLineType(visualListBean.getDefaultLineType());

	}

	public void loadDrawer(VisualLinkedStructureBean visualStructureBean, ListGraphicDrawer<I> drawer) {
		loadDrawer(visualStructureBean, (DrawerA) drawer);
	}

}
