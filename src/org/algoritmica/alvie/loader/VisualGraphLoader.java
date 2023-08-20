package org.algoritmica.alvie.loader;

import org.algoritmica.alvie.bean.VisualGraphBean;
import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.drawer.GraphGraphicDrawer;
import org.algoritmica.alvie.information.InformationI;

@SuppressWarnings({ "rawtypes" })
/*
 * This class loads the visual counter part of a graph: by making use of the
 * information contained in the corresponding visual graph bean, it sets the
 * graph graphic drawer default color, font, shape, element height and width,
 * the list graphic drawer origin coordinates, and the default link color, type,
 * and thickness.
 */
public class VisualGraphLoader<IN extends InformationI, IL extends InformationI> implements VisualLoaderI {

	public void loadDrawer(VisualStructureBean visualStructureBean, DrawerA drawer) {
		VisualGraphBean visualGraphBean = (VisualGraphBean) visualStructureBean;
		drawer.setOriginX(visualGraphBean.getOriginX());
		drawer.setOriginY(visualGraphBean.getOriginY());
		drawer.setDefaultColor(visualGraphBean.getDefaultColor());
		drawer.setDefaultFont(visualGraphBean.getDefaultFont());
		drawer.setDefaultShape(visualGraphBean.getDefaultShape());
		drawer.setDefaultLineColor(visualGraphBean.getDefaultLineColor());
		drawer.setDefaultLineThickness(visualGraphBean.getDefaultLineThickness());
		drawer.setDefaultLineType(visualGraphBean.getDefaultLineType());

	}

	public void loadDrawer(VisualGraphBean visualStructureBean, GraphGraphicDrawer<IN, IL> drawer) {
		loadDrawer(visualStructureBean, (DrawerA) drawer);
	}

}
