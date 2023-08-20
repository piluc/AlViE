package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the standard matrix interface. It allows the programmer to get the height
 * and the width of the matrix and to manage its elements by referring to their row and
 * column indices.
 */
public interface MatrixI<I extends InformationI> extends DataStructureI<I>, IdentifiableI {

	public int height();

	public int width();

	public I elementAt(int i, int j);

	public void setAt(I x, int i, int j);

}
