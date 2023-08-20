package org.algoritmica.alvie.drawer;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.algoritmica.alvie.datastructure.GeometricFigure;

/*
 * This geometric figure drawer prints the textual representation of the figure, which 
 * is a list of the textual representation of its geometric objects, separated by a 
 * semi-column and enclosed in brackets.
 */
public class GeometricFigureTextDrawer extends GeometricFigureDrawerA {

	protected PrintWriter printWriter;

	public GeometricFigureTextDrawer(String name, OutputStream outputStream) {
		super(name);
		printWriter = new PrintWriter(outputStream, true);
	}

	public void draw(GeometricFigure figure) {
		int l = figure.length();
		for (int k = 0; k < l - 1; k++) {
			printWriter.print("{" + figure.elementAt(k).stringValue());
			printWriter.print(";");
		}
		printWriter.println(figure.elementAt(l - 1).stringValue() + "}");
	}

	public void drawMessage(String message) {
		printWriter.println(message);
	}
}
