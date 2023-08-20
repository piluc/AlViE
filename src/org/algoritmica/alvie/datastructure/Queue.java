package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the queue data structure. It includes the methods defined
 * by the queue interface: these methods are implemented by making use
 * of a list and of a pointer to the tail of the list. 
 */
public class Queue<I extends InformationI> extends DataStructureA<I> implements QueueI<I> {
	private ListI<I> head;
	private ListI<I> tail;
	private int size;

	public Queue(I o) {
		head = new List<I>(o);
		tail = head;
		setType(o.getClass().getSimpleName());
		size = 0;
	}

	public int size() {
		return size;
	}

	public void enqueue(I i) {
		ListI<I> tmp = new List<I>(i);
		tmp.set(i, new List<I>(i));
		if (size == 0) {
			head = tmp;
			tail = head;
		} else {
			tail.set(tail.head(), tmp);
			tail = tail.tail();
		}
		size++;
	}

	public I first() {
		return head.head();
	}

	public void dequeue() {
		head = head.tail();
		size--;
		if (size == 0) {
			tail = head;
		}
	}
}
