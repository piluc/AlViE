package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the standard list interface. It allows the programmer to get the size
 * of the list, and to manage its head element and its tail list.
 */
public interface ListI<I extends InformationI> extends DataStructureI<I>, IdentifiableI {

	public int size();

	public I head();

	public ListI<I> tail();

	public void set(I head, ListI<I> tail);
}
