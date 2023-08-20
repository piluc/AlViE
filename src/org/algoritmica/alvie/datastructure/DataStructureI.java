package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the common data structure interface, which has to be implemented by all other 
 * data structures: it simply states that a data structure has to manage the type of
 * the information contained in its elements.
 */
public interface DataStructureI<I extends InformationI> {

	public String getType();

	public void setType(String type);

}
