package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the matrix data structure. It includes the methods defined
 * by the matrix interface: these methods are implemented by making use
 * of the Java array type. The class also includes some methods used by the
 * graphical input developer application. 
 */
public class Matrix<I extends InformationI> extends DataStructureA<I> implements MatrixI<I> {

	private I[][] a;

	@SuppressWarnings("unchecked")
	public Matrix(int h, int w, I o) {
		a = (I[][]) (new InformationI[h][w]);
		setType(o.getClass().getSimpleName());
	}

	public int height() {
		return a.length;
	}

	public int width() {
		if (height() == 0)
			return 0;
		else
			return a[0].length;
	}

	public I elementAt(int i, int j) {
		return a[i][j];
	}

	public void setAt(I x, int i, int j) {
		a[i][j] = x;
	}

	public long createElementID(long... position) {
		long row = position[0];
		long col = position[1];
		return col + row * width();
	}

	public long[] getPositionByID(long id) {
		long[] toret = new long[2];
		toret[0] = id / width();
		toret[1] = id % width();
		return toret;
	}

	public InformationI getElementByID(long id) {
		long[] position = getPositionByID(id);
		return a[(int) position[0]][(int) position[1]];
	}

	@SuppressWarnings("unchecked")
	public void setElementByID(InformationI newValue, long id) {
		long[] position = getPositionByID(id);
		a[(int) position[0]][(int) position[1]] = (I) newValue;
	}

}
