package org.algoritmica.alvie.loader;

import org.algoritmica.alvie.bean.VisualPseudocodeBean;
import org.algoritmica.alvie.bean.VisualSizedStructureBean;
import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.drawer.PseudocodeGraphicDrawer;

/*
 * This class loads the visual counter part of a pseudo-code: by making use of the information
 * contained in the corresponding visual pseudo-code bean, it sets the pseudo-code graphic drawer 
 * default color and font, and the pseudo-code graphic drawer origin coordinates.
 */
@SuppressWarnings({ "rawtypes" })
public class VisualPseudocodeLoader implements VisualLoaderI {

	public void loadDrawer(VisualStructureBean visualStructureBean, DrawerA drawer) {
		VisualPseudocodeBean visualPseudocodeBean = (VisualPseudocodeBean) visualStructureBean;
		drawer.setOriginX(visualPseudocodeBean.getOriginX());
		drawer.setOriginY(visualPseudocodeBean.getOriginY());
		drawer.setDefaultColor(visualPseudocodeBean.getDefaultColor());
		drawer.setDefaultFont(visualPseudocodeBean.getDefaultFont());
	}

	public void loadDrawer(VisualSizedStructureBean visualStructureBean, PseudocodeGraphicDrawer drawer) {
		loadDrawer(visualStructureBean, (DrawerA) drawer);
	}
}
