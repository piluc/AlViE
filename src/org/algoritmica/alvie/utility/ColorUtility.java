package org.algoritmica.alvie.utility;

import java.awt.Color;

public class ColorUtility {
	public static String getStringColor(Color color) {
		String redString = Integer.toHexString(color.getRed());
		if (redString.length() == 1) {
			redString = "0" + redString;
		}
		String greenString = Integer.toHexString(color.getGreen());
		if (greenString.length() == 1) {
			greenString = "0" + greenString;
		}
		String blueString = Integer.toHexString(color.getBlue());
		if (blueString.length() == 1) {
			blueString = "0" + blueString;
		}
		return (redString + greenString + blueString).toUpperCase();
	}

}
