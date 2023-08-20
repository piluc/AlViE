package org.algoritmica.alvie.loader;

import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.drawer.DrawerA;

/*
 * All visual data structure loaders have to implement this interface, that is, they have
 * to implement a method loading the visual data structure starting from its corresponding
 * bean.
 */
public interface VisualLoaderI<D extends DrawerA> {
	public void loadDrawer(VisualStructureBean visualStructureBean, D drawer);
}
