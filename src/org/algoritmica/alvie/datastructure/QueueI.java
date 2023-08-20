package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the standard queue interface. It allows the programmer to get the size
 * of the queue and to manage its elements by accessing the head and the tail of
 * the queue.
 */
public interface QueueI<I extends InformationI> extends DataStructureI<I> {

	public int size();

	public void enqueue(I i);

	public I first();

	public void dequeue();
}
