package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the standard stack interface. It allows the programmer to get the size
 * of the stack and to manage its elements by accessing the top of the stack.
 */
public interface StackI<I extends InformationI> extends DataStructureI<I> {

	public int size();

	public I top();

	public void pop();

	public void push(I i);
}
