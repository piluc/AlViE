package org.algoritmica.alvie.drawer;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.information.InformationI;

/*
 * This array drawer prints the textual representation of an array, which is a list of 
 * the textual representation of the values stored in its elements, separated by a 
 * semi-column and enclosed in brackets.
 */
public class ArrayTextDrawer<I extends InformationI> extends ArrayDrawerA {

	protected PrintWriter printWriter;

	public ArrayTextDrawer(String name, OutputStream outputStream) {
		super(name);
		printWriter = new PrintWriter(outputStream, true);
	}

	public void draw(ArrayI<I> array) {
		int l = array.length();
		printWriter.print("[");
		for (int k = 0; k < l - 1; k++) {
			if (array.elementAt(k) != null) {
				printWriter.print(array.elementAt(k).stringValue());
			} else {
				printWriter.print(" ");
			}
			printWriter.print(" ; ");
		}
		if (array.elementAt(l - 1) != null) {
			printWriter.println(array.elementAt(l - 1).stringValue() + "]");
		} else {
			printWriter.println(" ]");
		}
	}

	public void drawMessage(String message) {
		printWriter.println(message);
	}
}
