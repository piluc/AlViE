package org.algoritmica.alvie.loader;

import org.algoritmica.alvie.bean.VisualMalleableStructureBean;
import org.algoritmica.alvie.bean.VisualQueueBean;
import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.drawer.QueueGraphicDrawer;
import org.algoritmica.alvie.information.InformationI;

/*
 * This class loads the visual counter part of a queue: by making use of the information
 * contained in the corresponding visual queue bean, it sets the queue graphic drawer 
 * default color and font, and the queue graphic drawer origin coordinates.
 */
@SuppressWarnings({ "rawtypes" })
public class VisualQueueLoader<I extends InformationI> implements VisualLoaderI {

	public void loadDrawer(VisualStructureBean visualStructureBean, DrawerA drawer) {
		VisualQueueBean visualQueueBean = (VisualQueueBean) visualStructureBean;
		drawer.setOriginX(visualQueueBean.getOriginX());
		drawer.setOriginY(visualQueueBean.getOriginY());
		drawer.setDefaultColor(visualQueueBean.getDefaultColor());
		drawer.setDefaultFont(visualQueueBean.getDefaultFont());
		drawer.setDefaultShapeHeight(visualQueueBean.getDefaultShapeHeight());
		drawer.setDefaultShapeWidth(visualQueueBean.getDefaultShapeWidth());
	}

	public void loadDrawer(VisualMalleableStructureBean visualStructureBean, QueueGraphicDrawer<I> drawer) {
		loadDrawer(visualStructureBean, (DrawerA) drawer);
	}
}
