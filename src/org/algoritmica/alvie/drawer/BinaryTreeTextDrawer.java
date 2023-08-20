package org.algoritmica.alvie.drawer;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.algoritmica.alvie.datastructure.BinaryTreeI;
import org.algoritmica.alvie.information.InformationI;

/*
 * This binary tree drawer prints the textual representation of a binary tree, which is
 * its parenthesized textual representation (children are separated by a semi-column and 
 * trees are enclosed in brackets). Empty trees are represented by the "nil" string.
 */
public class BinaryTreeTextDrawer<I extends InformationI> extends BinaryTreeDrawerA {

	protected PrintWriter printWriter;

	public BinaryTreeTextDrawer(String name, OutputStream outputStream) {
		super(name);
		printWriter = new PrintWriter(outputStream, true);
	}

	private void recursivelyDraw(BinaryTreeI<I> binaryTree) {
		if (binaryTree.size() == 0) {
			printWriter.print("nil");
		} else {
			printWriter.print("[");
			printWriter.print(binaryTree.root().stringValue());
			printWriter.print(" ; ");
			recursivelyDraw(binaryTree.leftSubtree());
			printWriter.print(" ; ");
			recursivelyDraw(binaryTree.rightSubtree());
			printWriter.print("]");
		}
	}

	public void draw(BinaryTreeI<I> binaryTree) {
		recursivelyDraw(binaryTree);
		printWriter.println();
	}

	public void drawMessage(String message) {
		printWriter.println(message);
	}
}
