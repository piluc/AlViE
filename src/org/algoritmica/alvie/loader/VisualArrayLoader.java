package org.algoritmica.alvie.loader;

import org.algoritmica.alvie.bean.VisualArrayBean;
import org.algoritmica.alvie.bean.VisualMalleableStructureBean;
import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.drawer.ArrayGraphicDrawer;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.information.InformationI;

/*
 * This class loads the visual counter part of an array: by making use of the information
 * contained in the corresponding visual array bean, it sets the array graphic drawer default color
 * and font, and the array graphic drawer origin coordinates.
 */
@SuppressWarnings({ "rawtypes" })
public class VisualArrayLoader<I extends InformationI> implements VisualLoaderI {

	public void loadDrawer(VisualStructureBean visualStructureBean, DrawerA drawer) {
		VisualArrayBean visualArrayBean = (VisualArrayBean) visualStructureBean;
		drawer.setOriginX(visualArrayBean.getOriginX());
		drawer.setOriginY(visualArrayBean.getOriginY());
		drawer.setDefaultColor(visualArrayBean.getDefaultColor());
		drawer.setDefaultFont(visualArrayBean.getDefaultFont());
		drawer.setDefaultShapeHeight(visualArrayBean.getDefaultShapeHeight());
		drawer.setDefaultShapeWidth(visualArrayBean.getDefaultShapeWidth());
	}

	public void loadDrawer(VisualMalleableStructureBean visualStructureBean, ArrayGraphicDrawer<I> drawer) {
		loadDrawer(visualStructureBean, (DrawerA) drawer);
	}
}
