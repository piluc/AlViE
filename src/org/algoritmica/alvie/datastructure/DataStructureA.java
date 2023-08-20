package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the abstract implementation of the data structure interface, which implements
 * the type manager methods in a straightforward way. Even all methods are concrete,
 * the class is abstract since it has not to be instantiated.
 */
public abstract class DataStructureA<I extends InformationI> implements DataStructureI<I> {

	protected String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
