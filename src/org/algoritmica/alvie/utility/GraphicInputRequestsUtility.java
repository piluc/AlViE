package org.algoritmica.alvie.utility;

import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.information.CharInformation;
import org.algoritmica.alvie.information.DoubleInformation;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.LongInformation;
import org.algoritmica.alvie.information.StringInformation;

public class GraphicInputRequestsUtility {
	private static ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private static JRadioButton[] addRadioButtons(JComponent container, String[] choices, int selectedIndex) {
		JRadioButton[] toret = new JRadioButton[choices.length];
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < choices.length; i++) {
			toret[i] = new JRadioButton(choices[i]);
			group.add(toret[i]);
			container.add(toret[i]);
		}
		if (selectedIndex >= 0 && selectedIndex < choices.length)
			toret[selectedIndex].setSelected(true);
		return toret;
	}

	public static int insertCBoxChoice(String message, String title, String[] choices, int questionType, int defaultSelected) {
		String choice = (String) JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE, null, choices, choices[defaultSelected]);
		int toret = -1;
		int i = 0;
		while (i < choices.length && toret == -1) {
			if (choices[i].equals(choice))
				toret = i;
			i++;
		}
		return toret;
	}

	public static int insertRadioChoice(String title, String[] choices, int questionType, int defaultSelected) {
		JPanel qpane = new JPanel(new GridLayout(0, 1));
		qpane.add(new JLabel(localResourceBundle.getString("option009")));
		JRadioButton[] rb = addRadioButtons(qpane, choices, defaultSelected);
		JOptionPane.showConfirmDialog(null, qpane, title, questionType, JOptionPane.QUESTION_MESSAGE);
		int toret = -1;
		int i = 0;
		while (i < choices.length && toret == -1) {
			if (rb[i].isSelected())
				toret = i;
			i++;
		}
		return toret;
	}

	public static boolean createAreUSureFrame(String title, String question) {
		int option = JOptionPane.showConfirmDialog(null, question, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
		return option == JOptionPane.YES_OPTION;
	}

	public static int createConfirmFrame(String title, String question) {
		return JOptionPane.showConfirmDialog(null, title, question, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null);
	}

	public static int insertPositiveNumericValue(String title) {
		boolean isValid = false;
		int inserted = Integer.MIN_VALUE;
		while (!isValid) {
			String toParse = JOptionPane.showInputDialog(null, localResourceBundle.getString("question004"), title, JOptionPane.QUESTION_MESSAGE);
			if (toParse != null) {
				try {
					inserted = Integer.parseInt(toParse);
					if (inserted < 0 && inserted != Integer.MIN_VALUE)
						throw new NumberFormatException();
					isValid = true;
				} catch (NumberFormatException e1) {
					MessageUtility.informationMessage(localResourceBundle.getString("errorMessage012"), localResourceBundle.getString("title023"));
				}
			} else {
				inserted = Integer.MIN_VALUE;
				isValid = true;
			}
		}
		return inserted;
	}

	public static String insertStringValue(String title) {
		String inserted = JOptionPane.showInputDialog(null, localResourceBundle.getString("question005"), title, JOptionPane.QUESTION_MESSAGE);
		if (inserted != null && inserted.length() == 0)
			inserted = null;
		return inserted;
	}

	public static IntInformation insertIntInformationValue() {
		String title = localResourceBundle.getString("title026") + "IntInformation";
		boolean isValid = false;
		int inserted = Integer.MIN_VALUE;
		while (!isValid) {
			String toParse = JOptionPane.showInputDialog(null, localResourceBundle.getString("question006"), title, JOptionPane.QUESTION_MESSAGE);
			if (toParse != null) {
				try {
					inserted = Integer.parseInt(toParse);
					isValid = true;
				} catch (NumberFormatException e1) {
					MessageUtility.informationMessage(localResourceBundle.getString("errorMessage013"), localResourceBundle.getString("title023"));
				}
			} else {
				inserted = Integer.MIN_VALUE;
				isValid = true;
			}
		}

		IntInformation toret = null;
		if (inserted != Integer.MIN_VALUE)
			toret = new IntInformation(inserted);
		return toret;
	}

	public static DoubleInformation insertDoubleInformationValue() {
		String title = localResourceBundle.getString("title026") + "DoubleInformation";
		boolean isValid = false;
		double inserted = Double.MIN_VALUE;
		while (!isValid) {
			String toParse = JOptionPane.showInputDialog(null, localResourceBundle.getString("question007"), title, JOptionPane.QUESTION_MESSAGE);
			if (toParse != null) {
				try {
					inserted = Double.parseDouble(toParse);
					isValid = true;
				} catch (NumberFormatException e1) {
					MessageUtility.informationMessage(localResourceBundle.getString("errorMessage014"), localResourceBundle.getString("title023"));
				}
			} else {
				inserted = Double.MIN_VALUE;
				isValid = true;
			}
		}
		DoubleInformation toret = null;
		if (inserted != Double.MIN_VALUE)
			toret = new DoubleInformation(inserted);
		return toret;
	}

	public static LongInformation insertLongInformationValue() {
		String title = localResourceBundle.getString("title026") + "LongInformation";
		boolean isValid = false;
		long inserted = Long.MIN_VALUE;
		while (!isValid) {
			String toParse = JOptionPane.showInputDialog(null, localResourceBundle.getString("question006"), title, JOptionPane.QUESTION_MESSAGE);
			if (toParse != null) {
				try {
					inserted = Long.parseLong(toParse);
					isValid = true;
				} catch (NumberFormatException e1) {
					MessageUtility.informationMessage(localResourceBundle.getString("errorMessage013"), localResourceBundle.getString("title023"));
				}
			} else {
				inserted = Long.MIN_VALUE;
				isValid = true;
			}
		}
		LongInformation toret = null;
		if (inserted != Integer.MIN_VALUE)
			toret = new LongInformation(inserted);
		return toret;
	}

	public static StringInformation insertStringInformationValue() {
		String title = localResourceBundle.getString("title026") + "StringInformation";
		String inserted = JOptionPane.showInputDialog(null, localResourceBundle.getString("question005"), title, JOptionPane.QUESTION_MESSAGE);
		StringInformation toret = null;
		if (inserted != null && inserted.length() != 0)
			toret = new StringInformation(inserted);
		return toret;
	}

	public static CharInformation insertCharInformationValue() {
		String title = localResourceBundle.getString("title026") + "CharInformation";
		String inserted = "";
		while (inserted != null && inserted.length() != 1) {
			inserted = JOptionPane.showInputDialog(null, localResourceBundle.getString("question008"), title, JOptionPane.QUESTION_MESSAGE);
			if (inserted != null && inserted.length() != 1)
				MessageUtility.informationMessage(localResourceBundle.getString("errorMessage015"), localResourceBundle.getString("title025"));
		}

		CharInformation toret = null;
		if (inserted != null)
			toret = new CharInformation(inserted.charAt(0));
		return toret;
	}

}
