package org.algoritmica.alvie.utility;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.DataStructureI;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.information.InformationI;

public abstract class SizedStructureXMLDrawerUtility<DS extends DataStructureI<? extends InformationI>, D extends DrawerA> extends StructureXMLDrawerUtility<DS, D> {

	public SizedStructureXMLDrawerUtility(DS ds, OutputStream os) {
		super(ds, os);
	}

}
