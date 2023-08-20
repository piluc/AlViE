package org.algoritmica.alvie.drawer;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.algoritmica.alvie.datastructure.QueueI;
import org.algoritmica.alvie.information.InformationI;

/*
 * This queue drawer prints the textual representation of a queue, which is a list of 
 * the textual representation of the values stored in its elements, separated by a 
 * semi-column and enclosed in brackets. In order to access all elements of the queue,
 * a temporary array has to be used.
 */
public class QueueTextDrawer<I extends InformationI> extends QueueDrawerA {

	protected PrintWriter printWriter;

	public QueueTextDrawer(String name, OutputStream outputStream) {
		super(name);
		printWriter = new PrintWriter(outputStream, true);
	}

	@SuppressWarnings("unchecked")
	public void draw(QueueI<I> queue) {
		int size = queue.size();
		I[] a = (I[]) (new InformationI[size]);
		int i = 0;
		while (queue.size() > 0) {
			a[i] = queue.first();
			queue.dequeue();
			i = i + 1;
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
			queue.enqueue(a[k]);
		}
	}

	public void drawMessage(String message) {
		printWriter.println(message);
	}
}
