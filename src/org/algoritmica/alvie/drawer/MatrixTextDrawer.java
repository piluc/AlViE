package org.algoritmica.alvie.drawer;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.algoritmica.alvie.datastructure.MatrixI;
import org.algoritmica.alvie.information.InformationI;

/*
 * This matrix drawer prints the textual representation of a matrix, which is a list of 
 * rows each containing the textual representation of the values stored in the row elements,
 * separated by a space and and enclosed in bars.
 */
public class MatrixTextDrawer<I extends InformationI> extends MatrixDrawerA {

	private PrintWriter printWriter;

	public MatrixTextDrawer(String name, OutputStream outputStream) {
		super(name);
		printWriter = new PrintWriter(outputStream, true);
	}

	public void draw(MatrixI<I> matrix) {
		int h = matrix.height();
		int w = matrix.width();
		for (int i = 0; i < h; i++) {
			printWriter.print("| ");
			for (int j = 0; j < w; j++) {
				printWriter.print(matrix.elementAt(i, j).stringValue() + " ");
			}
			printWriter.println("|");
		}
	}

	public void drawMessage(String message) {
		printWriter.println(message);
	}
}
