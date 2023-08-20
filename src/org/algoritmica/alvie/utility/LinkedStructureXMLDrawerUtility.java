package org.algoritmica.alvie.utility;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.DataStructureI;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.information.InformationI;

public abstract class LinkedStructureXMLDrawerUtility<DS extends DataStructureI<? extends InformationI>, D extends DrawerA> extends MalleableStructureXMLDrawerUtility<DS, D> {

	public LinkedStructureXMLDrawerUtility(DS ds, OutputStream os) {
		super(ds, os);
	}

	public void setDefaultLineThickness(Float lt) {
		drawer.setDefaultLineThickness(lt);
	}

	public void setDefaultShape(String shape) {
		drawer.setDefaultShape(shape);
	}

}
