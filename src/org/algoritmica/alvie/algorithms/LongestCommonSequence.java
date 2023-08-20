package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.CharInformation;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.IntPairInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class LongestCommonSequence {
	private class VisualLongestCommonSequence extends VisualInnerClass {
		private ArrayXMLDrawerUtility<CharInformation> aDrawer;

		private ArrayXMLDrawerUtility<CharInformation> bDrawer;

		private MatrixXMLDrawerUtility<IntInformation> lengthDrawer;

		private Integer[] aIndex, bIndex;

		private String[] aColor, bColor, lengthColor;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private VisualLongestCommonSequence(String visFile) {
			super("longestCommonSequence", visFile);
		}

		private void ieDifferentCharacters(int i, int j, int f) {
			aColor[i - 1] = getResource("ijColor");
			bColor[j - 1] = getResource("ijColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieDifferentCharacters"));
			bDrawer.draw(bIndex, bColor, "");
			lengthDrawer.draw(new Integer[] { i * (n + 1) + j,
					i * (n + 1) + j - 1, (i - 1) * (n + 1) + j }, lengthColor,
					"");
			if (isPseudocodeVisible) {
				if (f == 0) {
					pseudocodeDrawer.draw("", new int[] { 10 });
				} else {
					pseudocodeDrawer.draw("", new int[] { 12 });
				}
			}
			aDrawer.endStep();
			aColor[i - 1] = getResource("datastructureColor");
			bColor[j - 1] = getResource("datastructureColor");
		}

		private void ieElementComputed(int i, int j) {
			for (int h = 0; h < i; h++) {
				aColor[h] = getResource("unsequenceColor");
			}
			for (int h = 0; h < j; h++) {
				bColor[h] = getResource("unsequenceColor");
			}
			setABDifferentialDrawArrays(i, j);
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieElementComputed"));
			bDrawer.draw(bIndex, bColor, "");
			lengthDrawer.draw(new Integer[] { i * (n + 1) + j }, lengthColor,
					"");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 6, 14 });
			}
			aDrawer.endStep();
			for (int h = 0; h < i; h++) {
				aColor[h] = getResource("datastructureColor");
			}
			for (int h = 0; h < j; h++) {
				bColor[h] = getResource("datastructureColor");
			}
			resetABDifferentialDrawArrays(i, j);
		}

		private void ieEnd() {
			setABDifferentialDrawArrays(m, n);
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieEnd"));
			bDrawer.draw(bIndex, bColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 15 });
			}
			aDrawer.endStep();
			aDrawer.endXMLFile();
		}

		private void ieEqualCharacters(int i, int j) {
			aColor[i - 1] = getResource("ijColor");
			bColor[j - 1] = getResource("ijColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieEqualCharacters"));
			bDrawer.draw(bIndex, bColor, "");
			lengthDrawer.draw(new Integer[] { i * (n + 1) + j,
					(i - 1) * (n + 1) + j - 1 }, lengthColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 8 });
			}
			aDrawer.endStep();
			aColor[i - 1] = getResource("datastructureColor");
			bColor[j - 1] = getResource("datastructureColor");
		}

		private void ieLengthInitialized() {
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieLengthInitialized"));
			bDrawer.draw("");
			lengthDrawer.draw("");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 1, 2, 3, 4 });
			}
			aDrawer.endStep();
		}

		private void ieStart() {
			aDrawer.startXMLFile(getResource("algorithmName"));
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieStart"));
			bDrawer.draw("");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 0 });
			}
			aDrawer.endStep();
		}

		private void init() {
			aDrawer = new ArrayXMLDrawerUtility<CharInformation>(a,
					getResource("aTitle"), getOutputStream());
			aDrawer.setOriginX(Integer
					.parseInt(getResource("aXMLDrawerOriginX")));
			aDrawer.setOriginY(Integer
					.parseInt(getResource("aXMLDrawerOriginY")));
			aDrawer.setDefaultFont(Font
					.decode(getResource("datastructureFont")));
			bDrawer = new ArrayXMLDrawerUtility<CharInformation>(b,
					getResource("bTitle"), getOutputStream());
			bDrawer.setOriginX(Integer
					.parseInt(getResource("bXMLDrawerOriginX")));
			bDrawer.setOriginY(Integer
					.parseInt(getResource("bXMLDrawerOriginY")));
			bDrawer.setDefaultFont(Font
					.decode(getResource("datastructureFont")));
			lengthDrawer = new MatrixXMLDrawerUtility<IntInformation>(length,
					getResource("lengthTitle"), getOutputStream());
			lengthDrawer.setOriginX(Integer
					.parseInt(getResource("lengthXMLDrawerOriginX")));
			lengthDrawer.setOriginY(Integer
					.parseInt(getResource("lengthXMLDrawerOriginY")));
			lengthDrawer.setDefaultFont(Font
					.decode(getResource("datastructureFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			step = 0;
			initDifferentialDrawArrays();
		}

		private void initDifferentialDrawArrays() {
			aIndex = new Integer[m];
			aColor = new String[m];
			for (int i = 0; i < m; i++) {
				aIndex[i] = i;
				aColor[i] = getResource("datastructureColor");
			}
			bIndex = new Integer[n];
			bColor = new String[n];
			for (int i = 0; i < n; i++) {
				bIndex[i] = i;
				bColor[i] = getResource("datastructureColor");
			}
			lengthColor = new String[3];
			lengthColor[0] = getResource("ijColor");
			lengthColor[1] = getResource("ij-1Color");
			lengthColor[2] = getResource("i-1jColor");
		}

		private void resetABDifferentialDrawArrays(int i, int j) {
			if ((i > 0) && (j > 0)) {
				IntPairInformation p = index.elementAt(i, j);
				resetABDifferentialDrawArrays(p.firstValue(), p.secondValue());
				if ((p.firstValue() == i - 1) && (p.secondValue() == j - 1)) {
					aColor[i - 1] = getResource("datastructureColor");
					bColor[j - 1] = getResource("datastructureColor");
				}
			}
		}

		private void setABDifferentialDrawArrays(int i, int j) {
			if ((i > 0) && (j > 0)) {
				IntPairInformation p = index.elementAt(i, j);
				setABDifferentialDrawArrays(p.firstValue(), p.secondValue());
				if ((p.firstValue() == i - 1) && (p.secondValue() == j - 1)) {
					aColor[i - 1] = getResource("sequenceColor");
					bColor[j - 1] = getResource("sequenceColor");
				}
			}
		}
	}

	private Array<CharInformation> a;

	private Array<CharInformation> b;

	private Matrix<IntInformation> length;

	private Matrix<IntPairInformation> index;

	private int i, j;

	private int m, n;

	VisualLongestCommonSequence vlcs;

	public void execute(String visFile) {
		vlcs = new VisualLongestCommonSequence(visFile);
		if (readInput()) {
			lcs();
			MessageUtility.algorithmTerminated("LongestCommonSequence");
		}
	}

	private void lcs() {
		vlcs.init();
		vlcs.ieStart();
		for (i = 0; i <= m; i++) {
			length.setAt(new IntInformation(0), i, 0);
		}
		for (j = 0; j <= n; j++) {
			length.setAt(new IntInformation(0), 0, j);
		}
		for (i = 1; i <= m; i++) {
			for (j = 1; j <= n; j++) {
				length.setAt(new IntInformation(-1), i, j);
			}
		}
		vlcs.ieLengthInitialized();
		for (i = 1; i <= m; i++) {
			for (j = 1; j <= n; j++) {
				if (a.elementAt(i - 1).isEqual(b.elementAt(j - 1))) {
					vlcs.ieEqualCharacters(i, j);
					length.setAt(new IntInformation(length.elementAt(i - 1,
							j - 1).intValue() + 1), i, j);
					index.setAt(new IntPairInformation(i - 1, j - 1), i, j);
				} else if (length.elementAt(i, j - 1).isGreaterThan(
						length.elementAt(i - 1, j))) {
					vlcs.ieDifferentCharacters(i, j, 0);
					length.setAt(new IntInformation(length.elementAt(i, j - 1)
							.intValue()), i, j);
					index.setAt(new IntPairInformation(i, j - 1), i, j);
				} else {
					vlcs.ieDifferentCharacters(i, j, 1);
					length.setAt(new IntInformation(length.elementAt(i - 1, j)
							.intValue()), i, j);
					index.setAt(new IntPairInformation(i - 1, j), i, j);
				}
				vlcs.ieElementComputed(i, j);
			}
		}
		vlcs.ieEnd();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vlcs.getAlgorithmPath(), vlcs
				.getResource("algorithmFileName"));
		Array<StringInformation> input = (Array) inputLoader.load("Array", vlcs
				.getResource("selectInputMessage"));
		if (input == null || input.length() != 2) {
			return false;
		}
		String fs = input.elementAt(0).stringValue();
		a = new Array<CharInformation>(fs.length(), new CharInformation());
		for (int i = 0; i < a.length(); i++) {
			a.setAt(new CharInformation(fs.charAt(i)), i);
		}
		String ss = input.elementAt(1).stringValue();
		b = new Array<CharInformation>(ss.length(), new CharInformation());
		for (int i = 0; i < b.length(); i++) {
			b.setAt(new CharInformation(ss.charAt(i)), i);
		}
		m = a.length();
		n = b.length();
		length = new Matrix<IntInformation>(m + 1, n + 1, new IntInformation());
		index = new Matrix<IntPairInformation>(m + 1, n + 1,
				new IntPairInformation());
		return true;
	}
}
