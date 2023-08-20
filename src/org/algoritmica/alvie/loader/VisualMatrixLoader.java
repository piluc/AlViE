package org.algoritmica.alvie.loader;

import org.algoritmica.alvie.bean.VisualMatrixBean;
import org.algoritmica.alvie.bean.VisualSizedStructureBean;
import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.drawer.MatrixGraphicDrawer;
import org.algoritmica.alvie.information.InformationI;

/*
 * This class loads the visual counter part of a matrix: by making use of the information
 * contained in the corresponding visual matrix bean, it sets the matrix graphic drawer default color
 * and font, and the matrix graphic drawer origin coordinates.
 */
@SuppressWarnings({ "rawtypes" })
public class VisualMatrixLoader<I extends InformationI> implements VisualLoaderI {

	public void loadDrawer(VisualStructureBean visualStructureBean, DrawerA drawer) {
		VisualMatrixBean visualMatrixBean = (VisualMatrixBean) visualStructureBean;
		drawer.setOriginX(visualMatrixBean.getOriginX());
		drawer.setOriginY(visualMatrixBean.getOriginY());
		drawer.setDefaultColor(visualMatrixBean.getDefaultColor());
		drawer.setDefaultFont(visualMatrixBean.getDefaultFont());
		drawer.setDefaultShapeHeight(visualMatrixBean.getDefaultShapeHeight());
		drawer.setDefaultShapeWidth(visualMatrixBean.getDefaultShapeWidth());
	}

	public void loadDrawer(VisualSizedStructureBean visualStructureBean, MatrixGraphicDrawer<I> drawer) {
		loadDrawer(visualStructureBean, (DrawerA) drawer);
	}

}
