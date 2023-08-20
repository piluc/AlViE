package org.algoritmica.alvie.loader;

import org.algoritmica.alvie.bean.VisualGeometricFigureBean;
import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.drawer.GeometricFigureGraphicDrawer;

@SuppressWarnings({ "rawtypes" })
/*
 * This class loads the visual counter part of a geometric figure: by making use
 * of the information contained in the corresponding visual geometric figure
 * bean, it sets the geometric figure graphic drawer default color, font, and
 * line thickness, and the geometric figure graphic drawer origin coordinates.
 */
public class VisualGeometricFigureLoader implements VisualLoaderI {

	public void loadDrawer(VisualStructureBean visualStructureBean, DrawerA drawer) {
		VisualGeometricFigureBean visualGeometricFigureBean = (VisualGeometricFigureBean) visualStructureBean;
		drawer.setOriginX(visualGeometricFigureBean.getOriginX());
		drawer.setOriginY(visualGeometricFigureBean.getOriginY());
		drawer.setDefaultColor(visualGeometricFigureBean.getDefaultColor());
		drawer.setDefaultFont(visualGeometricFigureBean.getDefaultFont());
		drawer.setDefaultLineThickness(visualGeometricFigureBean.getDefaultLineThickness());
	}

	public void loadDrawer(VisualStructureBean visualStructureBean, GeometricFigureGraphicDrawer drawer) {
		loadDrawer(visualStructureBean, (DrawerA) drawer);
	}

}
