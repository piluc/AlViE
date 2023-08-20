package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the list data structure. It includes the methods defined by the list interface:
 * these methods are implemented by making use of an information head and a list tail 
 * fields (hence, accessing the head of a list and inserting an element at the beginning of 
 * a list requires constant time). The class also includes some methods used by the graphical 
 * input developer application. 
 */
public class List<I extends InformationI> extends DataStructureA<I> implements ListI<I> {
	private int size;

	private I head;

	private ListI<I> tail;

	public List(I o) {
		size = 0;
		setType(o.getClass().getSimpleName());
	}

	public int size() {
		return size;
	}

	public I head() {
		return head;
	}

	public ListI<I> tail() {
		return tail;
	}

	public void set(I head, ListI<I> tail) {
		this.head = head;
		this.tail = tail;
		size = tail.size() + 1;
	}

	public long createElementID(long... position) {
		return position[0];
	}

	public InformationI getElementByID(long id) {
		ListI<I> position = this;
		for (int i = 0; i < id; i++)
			position = position.tail();
		return position.head();
	}

	@SuppressWarnings("unchecked")
	public void setElementByID(InformationI newValue, long id) {
		ListI<I> position = this;
		for (int i = 0; i < id; i++)
			position = position.tail();
		position.set((I) newValue, position.tail());
	}

	public long[] getPositionByID(long id) {
		long[] toret = new long[1];
		toret[0] = id;
		return toret;
	}

}
