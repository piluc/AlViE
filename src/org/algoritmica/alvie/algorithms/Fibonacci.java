package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class Fibonacci {
	private class VisualFibonacci extends VisualInnerClass {
		private Array<StringInformation> stringA;

		private ArrayXMLDrawerUtility<StringInformation> aDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] aIndex;

		private String[] aColor;

		private String[] aFont;

		private String[] aShape;

		private VisualFibonacci(String visFileName) {
			super("fibonacci", visFileName);
		}

		private void createStringArray() {
			stringA = new Array<StringInformation>(a.length(),
					new StringInformation());
			for (int i = 0; i < a.length(); i++) {
				if (a.elementAt(i).intValue() >= 0) {
					stringA.setAt(new StringInformation(a.elementAt(i)
							.stringValue()), i);
				} else {
					stringA.setAt(new StringInformation(" "), i);
				}
			}
		}

		private void createVisualArray() {
			aDrawer = new ArrayXMLDrawerUtility<StringInformation>(stringA,
					getResource("aTitle"), getOutputStream());
			aDrawer.setOriginX(Integer
					.parseInt(getResource("aXMLDrawerOriginX")));
			aDrawer.setOriginY(Integer
					.parseInt(getResource("aXMLDrawerOriginY")));
			aDrawer.setDefaultFont(Font.decode(getResource("aFont")));
		}

		private void ieAfterNewNumber(int s) {
			stringA.setAt(new StringInformation(a.elementAt(s).stringValue()),
					s);
			aColor[s - 1] = getResource("aKnownNumberColor");
			aColor[s - 2] = getResource("aKnownNumberColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieAfterNewNumber"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 4 });
			}
			aDrawer.endStep();
			aColor[s] = getResource("aKnownNumberColor");
		}

		private void ieBeforeNewNumber(int s) {
			aColor[s] = getResource("aNewNumberColor");
			aColor[s - 1] = getResource("aPreviousNumberColor");
			aColor[s - 2] = getResource("aPreviousNumberColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieBeforeNewNumber"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 4 });
			}
			aDrawer.endStep();
		}

		private void ieEnd() {
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieEnd"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 5 });
			}
			aDrawer.endStep();
			aDrawer.endXMLFile();
		}

		private void ieFirstTwoNumbers() {
			stringA.setAt(new StringInformation(a.elementAt(0).stringValue()),
					0);
			stringA.setAt(new StringInformation(a.elementAt(1).stringValue()),
					1);
			aColor[0] = getResource("aKnownNumberColor");
			aColor[1] = getResource("aKnownNumberColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieFirstTwoNumbers"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 1, 2 });
			}
			aDrawer.endStep();
		}

		private void ieStart() {
			aDrawer.startXMLFile(getResource("algorithmName"));
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieStart"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", null);
			}
			aDrawer.endStep();
		}

		private void init() {
			createStringArray();
			createVisualArray();
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setArraysDifferentialDrawArrays();
			step = 0;
		}

		private void setArraysDifferentialDrawArrays() {
			int n = a.length();
			aIndex = new Integer[n];
			aColor = new String[n];
			aFont = new String[n];
			aShape = new String[n];
			for (int i = 0; i < n; i++) {
				aIndex[i] = i;
				aFont[i] = getResource("aFont");
				aShape[i] = "Rectangular";
				aColor[i] = getResource("aUnknownNumberColor");
			}
		}

	}

	private int numberIndex;

	private Array<IntInformation> a;

	private VisualFibonacci vf;

	public void execute(String visFileName) {
		vf = new VisualFibonacci(visFileName);
		if (readInput()) {
			fibonacci();
			MessageUtility.algorithmTerminated("Fibonacci");
		}
	}

	private IntInformation fibonacci() {
		int i;
		a = new Array<IntInformation>(numberIndex + 1, new IntInformation());
		for (i = 0; i <= numberIndex; i++) {
			a.setAt(new IntInformation(-1), i);
		}
		vf.init();
		vf.ieStart();
		a.setAt(new IntInformation(0), 0);
		a.setAt(new IntInformation(1), 1);
		vf.ieFirstTwoNumbers();
		for (i = 2; i <= numberIndex; i++) {
			vf.ieBeforeNewNumber(i);
			a.setAt(new IntInformation(a.elementAt(i - 1).intValue()
					+ a.elementAt(i - 2).intValue()), i);
			vf.ieAfterNewNumber(i);
		}
		vf.ieEnd();
		return a.elementAt(numberIndex);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader algorithmLoader = new InputLoader(vf.getAlgorithmPath(), vf
				.getResource("algorithmFileName"));
		Array<IntInformation> input = (Array) algorithmLoader.load("Array", vf
				.getResource("selectInputMessage"));
		if (input == null) {
			return false;
		}
		numberIndex = input.elementAt(0).intValue();
		return true;
	}
}
