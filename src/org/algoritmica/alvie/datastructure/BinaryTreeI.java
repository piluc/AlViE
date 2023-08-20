package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the standard binary tree interface. It allows the programmer to get the size
 * of the list, and to manage its root element and its left and right subtree.
 */
public interface BinaryTreeI<I extends InformationI> extends DataStructureI<I>, IdentifiableI {

	public int size();

	public I root();

	public BinaryTreeI<I> leftSubtree();

	public BinaryTreeI<I> rightSubtree();

	public void set(I i, BinaryTreeI<I> l, BinaryTreeI<I> r);

}
