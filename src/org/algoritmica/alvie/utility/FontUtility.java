package org.algoritmica.alvie.utility;

import java.awt.Font;

public class FontUtility {
	public static String getStringFont(Font font) {
		String stringFont = font.getFamily();
		int fontStyle = font.getStyle();
		if (fontStyle == Font.ITALIC) {
			stringFont = stringFont + "-ITALIC";
		} else if (fontStyle == Font.BOLD) {
			stringFont = stringFont + "-BOLD";
		} else {
			stringFont = stringFont + "-PLAIN";
		}
		stringFont = stringFont + "-" + font.getSize();
		return stringFont;
	}

}
