package org.algoritmica.alvie.drawer;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.algoritmica.alvie.datastructure.ListI;
import org.algoritmica.alvie.information.InformationI;

/*
 * This list drawer prints the textual representation of a list, which is a list of 
 * the textual representation of the values stored in its elements, separated by a 
 * '=>' string and enclosed in brackets.
 */
public class ListTextDrawer<I extends InformationI> extends ListDrawerA {

	protected PrintWriter printWriter;

	public ListTextDrawer(String name, OutputStream outputStream) {
		super(name);
		printWriter = new PrintWriter(outputStream, true);
	}

	public void draw(ListI<I> list) {
		int l = list.size();
		for (int k = 0; k < l - 1; k++) {
			printWriter.print("[" + list.head().stringValue() + "]");
			printWriter.print(" => ");
			list = list.tail();
		}
		printWriter.println("[" + list.head().stringValue() + "]");
	}

	public void drawMessage(String message) {
		printWriter.println(message);
	}
}
