package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the array data structure. It includes the methods defined
 * by the array interface: these methods are implemented by making use
 * of the Java array type. The class also includes some methods used by the
 * graphical input developer application. 
 */
public class Array<I extends InformationI> extends DataStructureA<I> implements ArrayI<I> {

	private I[] a;

	@SuppressWarnings("unchecked")
	public Array(int l, I o) {
		a = (I[]) (new InformationI[l]);
		setType(o.getClass().getSimpleName());
	}

	public int length() {
		return a.length;
	}

	public I elementAt(int i) {
		return a[i];
	}

	public void setAt(I x, int i) {
		a[i] = x;
	}

	public InformationI getElementByID(long id) {
		return elementAt((int) id);
	}

	@SuppressWarnings("unchecked")
	public void setElementByID(InformationI newValue, long id) {
		setAt((I) newValue, (int) id);
	}

	public long createElementID(long... position) {
		return position[0];
	}

	public long[] getPositionByID(long id) {
		long[] toret = new long[1];
		toret[0] = id;
		return toret;
	}

}
