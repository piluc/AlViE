package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the standard array interface. It allows the programmer to get the size
 * of the array and to manage its elements by referring to their index.
 */
public interface ArrayI<I extends InformationI> extends DataStructureI<I>, IdentifiableI {

	public int length();

	public I elementAt(int i);

	public void setAt(I x, int i);

}
