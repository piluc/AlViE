package org.algoritmica.alvie.utility;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.DataStructureI;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.information.InformationI;

public abstract class MalleableStructureXMLDrawerUtility<DS extends DataStructureI<? extends InformationI>, D extends DrawerA> extends SizedStructureXMLDrawerUtility<DS, D> {

	public MalleableStructureXMLDrawerUtility(DS ds, OutputStream os) {
		super(ds, os);
	}
}
