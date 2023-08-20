package org.algoritmica.alvie.drawer;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.information.StringInformation;

/*
 * This is the textual pseudo-code drawer. It simply prints one after the other
 * the code lines.
 */
public class PseudocodeTextDrawer extends ArrayTextDrawer<StringInformation> {

	public PseudocodeTextDrawer(String name, OutputStream os) {
		super(name, os);
	}

	public void draw(ArrayI<StringInformation> array) {
		int l = array.length();
		for (int k = 0; k < l; k++) {
			if (array.elementAt(k) != null) {
				printWriter.println(array.elementAt(k).stringValue());
			} else {
				printWriter.println(" ");
			}
		}
	}
}
