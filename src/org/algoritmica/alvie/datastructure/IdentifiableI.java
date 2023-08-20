package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

public interface IdentifiableI {

	public long createElementID(long... position);

	public long[] getPositionByID(long id);

	public InformationI getElementByID(long id);

	public void setElementByID(InformationI newValue, long id);

}
