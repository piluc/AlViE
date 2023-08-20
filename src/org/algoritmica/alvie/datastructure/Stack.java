package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the stack data structure. It includes the methods defined
 * by the stack interface: these methods are implemented by making use
 * of a list. 
 */
public class Stack<I extends InformationI> extends DataStructureA<I> implements StackI<I> {
	private ListI<I> stack;

	private int size;

	public Stack(I o) {
		stack = new List<I>(o);
		setType(o.getClass().getSimpleName());
		size = 0;
	}

	public int size() {
		return size;
	}

	public I top() {
		return stack.head();
	}

	public void pop() {
		stack = stack.tail();
		size--;
	}

	public void push(I i) {
		ListI<I> head = new List<I>(i);
		head.set(i, stack);
		stack = head;
		size++;
	}
}
