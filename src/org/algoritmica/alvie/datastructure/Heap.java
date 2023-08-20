package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.SortableI;

public class Heap<I extends SortableI<I>> implements HeapI<I> {
	private Array<I> heapArray;
	private int heapSize;
	private I dummyObject;

	public Heap(int initialDimension, I o) {
		heapArray = new Array<I>(initialDimension, o);
		heapSize = 0;
		dummyObject = o;
	}

	private void checkDoubling() {
		if (heapSize == heapArray.length()) {
			Array<I> b = new Array<I>(2 * heapArray.length(), dummyObject);
			for (int i = 0; i < heapArray.length(); i++) {
				b.setAt(heapArray.elementAt(i), i);
			}
			heapArray = b;
		}
	}

	private void checkHalving() {
		if (heapSize == heapArray.length() / 4) {
			if (heapArray.length() >= 2) {
				Array<I> b = new Array<I>(heapArray.length() / 2, dummyObject);
				for (int i = 0; i < heapSize; i++) {
					b.setAt(heapArray.elementAt(i), i);
				}
				heapArray = b;
			}
		}
	}

	private int father(int i) {
		return (i - 1) / 2;
	}

	private int leftSon(int i) {
		return 2 * i + 1;
	}

	private void swap(int i, int j) {
		I tmp = heapArray.elementAt(i);
		heapArray.setAt(heapArray.elementAt(j), i);
		heapArray.setAt(tmp, j);
	}

	private int minFatherSons(int i) {
		int j = leftSon(i);
		int k = j;
		if (k + 1 < heapSize) {
			k = k + 1;
		}
		if (heapArray.elementAt(k).isLessThan(heapArray.elementAt(j))) {
			j = k;
		}
		if (heapArray.elementAt(i).isLessThan(heapArray.elementAt(j))) {
			j = i;
		}
		return j;
	}

	private void heapify(int i) {
		while (i > 0 && heapArray.elementAt(i).isLessThan(heapArray.elementAt(father(i)))) {
			swap(i, father(i));
			i = father(i);
		}
		int son;
		while (leftSon(i) < heapSize && i != minFatherSons(i)) {
			son = minFatherSons(i);
			swap(i, son);
			i = son;
		}
	}

	public I dequeue() {
		if (!empty()) {
			I minimum = heapArray.elementAt(0);
			heapArray.setAt(heapArray.elementAt(heapSize - 1), 0);
			heapSize = heapSize - 1;
			heapify(0);
			checkHalving();
			return minimum;
		}
		return null;
	}

	public boolean empty() {
		return heapSize == 0;
	}

	public void enqueue(I e) {
		checkDoubling();
		heapArray.setAt(e, heapSize);
		heapSize = heapSize + 1;
		heapify(heapSize - 1);
	}

	public I first() {
		return heapArray.elementAt(0);
	}

}
