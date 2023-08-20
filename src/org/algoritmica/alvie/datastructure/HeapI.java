package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.SortableI;

public interface HeapI<I extends SortableI<I>> {

	public boolean empty();

	public I first();

	public void enqueue(I e);

	public I dequeue();

}
