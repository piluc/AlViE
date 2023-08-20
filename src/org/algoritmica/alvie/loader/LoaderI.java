package org.algoritmica.alvie.loader;

import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.datastructure.DataStructureI;
import org.algoritmica.alvie.information.InformationI;

/*
 * All data structure loaders have to implement this interface, that is, they have
 * to implement a method loading the data structure starting from its corresponding
 * bean.
 */
public interface LoaderI<DS extends DataStructureI<? extends InformationI>> {

	public DS load(StructureBean structureBean);
}
