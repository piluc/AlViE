package org.algoritmica.alvie.utility;

import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.desktop.Main;

public class MessageUtility {
	private static ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private static String splitString(String s) {
		int numberCharacters = Integer.parseInt(Main.config.getProperty("dialogRowNumberCharacters"));
		if (s.length() <= numberCharacters) {
			return s;
		}
		String trimmedString = s.trim();
		StringTokenizer st = new StringTokenizer(trimmedString, " ");
		String rst = "";
		String ss = "";
		while (st.hasMoreTokens()) {
			if (ss.length() > numberCharacters) {
				rst = rst + ss + "\n";
				ss = "";
			} else {
				String t = st.nextToken();
				ss = ss + " " + t;
			}
		}
		if (ss.length() > 0) {
			return rst + ss;
		} else {
			return rst.substring(0, rst.length() - 1);
		}
	}

	public static void algorithmTerminated(String algorithmName) {
		informationMessage(splitString(algorithmName + localResourceBundle.getString("message005")), localResourceBundle.getString("title033"));
	}

	public static void informationMessage(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void errorMessage(String message, String title, int code) {
		JOptionPane.showMessageDialog(null, splitString(message), title + " (" + code + ")", JOptionPane.ERROR_MESSAGE);
		System.exit(-code);
	}
}
