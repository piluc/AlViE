package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the binary tree data structure. It includes the methods defined by the binary tree interface:
 * these methods are implemented by making use of an information root and two left and right binary tree 
 * fields (hence, accessing the root of a binary tree and inserting a left and a right child 
 * requires constant time). The class also includes some methods used by the graphical 
 * input developer application. 
 */
public class BinaryTree<I extends InformationI> extends DataStructureA<I> implements BinaryTreeI<I> {
	private int size;

	private I information;

	private BinaryTreeI<I> leftSubtree;

	private BinaryTreeI<I> rightSubtree;

	public BinaryTree(I o) {
		size = 0;
		setType(o.getClass().getSimpleName());
	}

	public int size() {
		return size;
	}

	public I root() {
		return information;
	}

	public BinaryTreeI<I> leftSubtree() {
		return leftSubtree;
	}

	public BinaryTreeI<I> rightSubtree() {
		return rightSubtree;
	}

	public void set(I information, BinaryTreeI<I> leftSubtree, BinaryTreeI<I> rightSubtree) {
		this.information = information;
		this.leftSubtree = leftSubtree;
		this.rightSubtree = rightSubtree;
		size = leftSubtree.size() + rightSubtree.size() + 1;
	}

	public long createElementID(long... position) {
		return position[0];
	}

	public long[] getPositionByID(long id) {
		long[] toret = new long[1];
		toret[0] = id;
		return toret;
	}

	public InformationI getElementByID(long id) {
		return getElementRecursively(this, getPositionByID(id)[0]).root();
	}

	private BinaryTreeI<I> getElementRecursively(BinaryTreeI<I> cursor, long position) {
		int l = (int) (Math.log(position + 1) / Math.log(2));
		if (l == 0)
			return cursor;
		if (position <= 3 * Math.pow(2, l - 1) - 2)
			return getElementRecursively(cursor.leftSubtree(), (int) (position - Math.pow(2, l - 1)));
		else
			return getElementRecursively(cursor.rightSubtree(), (int) (position - Math.pow(2, l)));
	}

	@SuppressWarnings("unchecked")
	public void setElementByID(InformationI newValue, long id) {
		BinaryTreeI<I> tree = getElementRecursively(this, getPositionByID(id)[0]);
		tree.set((I) newValue, tree.leftSubtree(), tree.rightSubtree());
	}
}
