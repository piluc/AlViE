package org.algoritmica.alvie.loader;

import org.algoritmica.alvie.bean.VisualMalleableStructureBean;
import org.algoritmica.alvie.bean.VisualStackBean;
import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.drawer.StackGraphicDrawer;
import org.algoritmica.alvie.information.InformationI;

/*
 * This class loads the visual counter part of a stack: by making use of the information
 * contained in the corresponding visual stack bean, it sets the stack graphic drawer 
 * default color and font, and the stack graphic drawer origin coordinates.
 */
@SuppressWarnings({ "rawtypes" })
public class VisualStackLoader<I extends InformationI> implements VisualLoaderI {

	public void loadDrawer(VisualStructureBean visualStructureBean, DrawerA drawer) {
		VisualStackBean visualStackBean = (VisualStackBean) visualStructureBean;
		drawer.setOriginX(visualStackBean.getOriginX());
		drawer.setOriginY(visualStackBean.getOriginY());
		drawer.setDefaultColor(visualStackBean.getDefaultColor());
		drawer.setDefaultFont(visualStackBean.getDefaultFont());
		drawer.setDefaultShapeHeight(visualStackBean.getDefaultShapeHeight());
		drawer.setDefaultShapeWidth(visualStackBean.getDefaultShapeWidth());
	}

	public void loadDrawer(VisualMalleableStructureBean visualStructureBean, StackGraphicDrawer<I> drawer) {
		loadDrawer(visualStructureBean, (DrawerA) drawer);
	}
}
