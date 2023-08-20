package org.algoritmica.alvie.drawer;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.algoritmica.alvie.datastructure.StackI;
import org.algoritmica.alvie.information.InformationI;

/*
 * This stack drawer prints the textual representation of a stack, which is a list of 
 * the textual representation of the values stored in its elements, separated by a 
 * semi-column and enclosed in brackets. In order to access all elements of the stack,
 * a temporary array has to be used.
 */
public class StackTextDrawer<I extends InformationI> extends StackDrawerA {

	protected PrintWriter printWriter;

	public StackTextDrawer(String name, OutputStream outputStream) {
		super(name);
		printWriter = new PrintWriter(outputStream, true);
	}

	@SuppressWarnings("unchecked")
	public void draw(StackI<I> stack) {
		int size = stack.size();
		I[] a = (I[]) (new InformationI[size]);
		int i = size - 1;
		while (stack.size() > 0) {
			a[i] = stack.top();
			stack.pop();
			i = i - 1;
		}
		printWriter.print("[");
		for (int k = size - 1; k > 0; k--) {
			if (a[k] != null) {
				printWriter.print(a[k].stringValue());
			} else {
				printWriter.print(" ");
			}
			printWriter.print(" ; ");
		}
		if (a[0] != null) {
			printWriter.println(a[0].stringValue() + "]");
		} else {
			printWriter.println(" ]");
		}
		for (int k = 0; k < size; k++) {
			stack.push(a[k]);
		}
	}

	public void drawMessage(String message) {
		printWriter.println(message);
	}
}
