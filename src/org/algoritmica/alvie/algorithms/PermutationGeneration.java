package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class PermutationGeneration {
	private class VisualPermutationGeneration extends VisualInnerClass {
		private Matrix<StringInformation> data;
		
		private MatrixXMLDrawerUtility<StringInformation> dataDrawer;
		
		private ArrayXMLDrawerUtility<StringInformation> aDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] aIndex;

		private String[] aColor;

		private String[] aFont;

		private String[] aShape;

		VisualPermutationGeneration(String visFile) {
			super("permutationGeneration", visFile);
		}
		
		private void addRow(int p) {
			Matrix<StringInformation> tmp = data;
			data = new Matrix<StringInformation>(tmp.height()+1, 2, new StringInformation());
			int r;
			for (r = 0; r <tmp.height(); r++) {
				data.setAt(tmp.elementAt(r, 0), r, 0);
				data.setAt(tmp.elementAt(r, 1), r, 1);
			}
			data.setAt(new StringInformation(""+p), r, 0);
			data.setAt(new StringInformation(" "), r, 1);
			dataDrawer = new MatrixXMLDrawerUtility<StringInformation>(data, getResource("dataTitle"), getOutputStream());
			dataDrawer.setOriginX(Integer
					.parseInt(getResource("dataXMLDrawerOriginX")));
			dataDrawer.setOriginY(Integer
					.parseInt(getResource("dataXMLDrawerOriginY")));
			dataDrawer.setDefaultFont(Font.decode(getResource("dataFont")));
		}

		private void deleteRow() {
			Matrix<StringInformation> tmp = data;
			data = new Matrix<StringInformation>(tmp.height()-1, 2, new StringInformation());
			int r;
			for (r = 0; r <tmp.height()-1; r++) {
				data.setAt(tmp.elementAt(r, 0), r, 0);
				data.setAt(tmp.elementAt(r, 1), r, 1);
			}
			dataDrawer = new MatrixXMLDrawerUtility<StringInformation>(data, getResource("dataTitle"), getOutputStream());
			dataDrawer.setOriginX(Integer
					.parseInt(getResource("dataXMLDrawerOriginX")));
			dataDrawer.setOriginY(Integer
					.parseInt(getResource("dataXMLDrawerOriginY")));
			dataDrawer.setDefaultFont(Font.decode(getResource("dataFont")));
		}

		private void ieAfterFirstSwap(int b, int c) {
			for (int i = n - 1; i >= b; i--) {
				aColor[i] = getResource("fixedCharacterColor");
			}
			aColor[b - 1] = getResource("swapCharacterColor");
			aColor[c] = getResource("swapCharacterColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieAfterFirstSwap"));
			dataDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 5 });
			aDrawer.endStep();
			for (int i = n - 1; i >= b - 1; i--) {
				aColor[i] = getResource("aColor");
			}
			aColor[c] = getResource("aColor");
		}

		private void ieAfterSecondSwap(int b, int c) {
			for (int i = n - 1; i >= b; i--) {
				aColor[i] = getResource("fixedCharacterColor");
			}
			aColor[b - 1] = getResource("swapCharacterColor");
			aColor[c] = getResource("swapCharacterColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieAfterSecondSwap"));
			dataDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 7 });
			aDrawer.endStep();
			for (int i = a.length() - 1; i >= b - 1; i--) {
				aColor[i] = getResource("aColor");
			}
			aColor[c] = getResource("aColor");
		}

		private void ieBeforeFirstSwap(int b, int c) {
			data.setAt(new StringInformation(""+c), data.height()-1, 1);
			for (int i = n - 1; i >= b; i--) {
				aColor[i] = getResource("fixedCharacterColor");
			}
			aColor[b - 1] = getResource("swapCharacterColor");
			aColor[c] = getResource("swapCharacterColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieBeforeFirstSwap"));
			dataDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 5 });
			aDrawer.endStep();
			for (int i = a.length() - 1; i >= b - 1; i--) {
				aColor[i] = getResource("aColor");
			}
		}

		private void ieBeforeRecursiveCall(int b) {
			for (int i = n - 1; i >= b; i--) {
				aColor[i] = getResource("fixedCharacterColor");
			}
			for (int i = b - 1; i >= 0; i--) {
				aColor[i] = getResource("nextSubArrayColor");
			}
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieBeforeRecursiveCall"));
			dataDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6 });
			aDrawer.endStep();
			for (int i = n - 1; i >= 0; i--) {
				aColor[i] = getResource("aColor");
			}
		}

		private void ieBeforeSecondSwap(int b, int c) {
			data.setAt(new StringInformation(""+c), data.height()-1, 1);
			for (int i = n - 1; i >= b; i--) {
				aColor[i] = getResource("fixedCharacterColor");
			}
			aColor[b - 1] = getResource("swapCharacterColor");
			aColor[c] = getResource("swapCharacterColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieBeforeSecondSwap"));
			dataDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 7 });
			aDrawer.endStep();
			for (int i = n - 1; i >= b - 1; i--) {
				aColor[i] = getResource("aColor");
			}
			aColor[c] = getResource("aColor");
		}

		void ieEnd() {
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieEnd"));
			aDrawer.endStep();
			aDrawer.endXMLFile();
		}

		private void ieNewRecursiveCall(int p) {
			addRow(p);
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieNewRecursiveCall"));
			dataDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 0 });
			aDrawer.endStep();
		}

		private void ieOneGenerationFinished() {
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieOneGenerationFinished"));
			dataDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 2 });
			aDrawer.endStep();
		}

		private void ieRecursiveCallEnd() {
			deleteRow();
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieRecursiveCallEnd"));
			dataDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 9 });
			aDrawer.endStep();
		}

		void ieStart() {
			aDrawer.startXMLFile(getResource("algorithmName"));
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieStart"));
			dataDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			aDrawer.endStep();
		}

		void init() {
			aDrawer = new ArrayXMLDrawerUtility<StringInformation>(a,
					getResource("aTitle"), getOutputStream());
			aDrawer.setOriginX(Integer
					.parseInt(getResource("aXMLDrawerOriginX")));
			aDrawer.setOriginY(Integer
					.parseInt(getResource("aXMLDrawerOriginY")));
			aDrawer.setDefaultFont(Font.decode(getResource("aFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			data = new Matrix<StringInformation>(1, 2, new StringInformation());
			data.setAt(new StringInformation("p"), 0, 0);
			data.setAt(new StringInformation("i"), 0, 1);
			dataDrawer = new MatrixXMLDrawerUtility<StringInformation>(data, getResource("dataTitle"), getOutputStream());
			dataDrawer.setOriginX(Integer
					.parseInt(getResource("dataXMLDrawerOriginX")));
			dataDrawer.setOriginY(Integer
					.parseInt(getResource("dataXMLDrawerOriginY")));
			dataDrawer.setDefaultFont(Font.decode(getResource("dataFont")));
			setArraysDifferentialDrawArrays();
		}

		private void setArraysDifferentialDrawArrays() {
			aIndex = new Integer[a.length()];
			aColor = new String[a.length()];
			aFont = new String[a.length()];
			aShape = new String[a.length()];
			for (int i = 0; i < a.length(); i++) {
				aIndex[i] = i;
				aColor[i] = getResource("aColor");
				aFont[i] = getResource("aFont");
				aShape[i] = "Rectangular";
			}
		}

	}

	private Array<StringInformation> a;

	private int n;

	private VisualPermutationGeneration vpg;

	public void execute(String visFile) {
		vpg = new VisualPermutationGeneration(visFile);
		if (readInput()) {
			start();
			MessageUtility.algorithmTerminated(vpg.getResource("algorithmName"));
		}
	}

	private void generatePermutations(int p) {
		vpg.ieNewRecursiveCall(p);
		if (p == 0) {
			vpg.ieOneGenerationFinished();
		} else {
			for (int i = p - 1; i >= 0; i--) {
				vpg.ieBeforeFirstSwap(p, i);
				swap(i, p - 1);
				vpg.ieAfterFirstSwap(p, i);
				vpg.ieBeforeRecursiveCall(p - 1);
				generatePermutations(p - 1);
				vpg.ieBeforeSecondSwap(p, i);
				swap(i, p - 1);
				vpg.ieAfterSecondSwap(p, i);
			}
		}
		vpg.ieRecursiveCallEnd();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vpg.getAlgorithmPath(), vpg
				.getResource("algorithmFileName"));
		a = (Array) inputLoader.load("Array", vpg
				.getResource("selectInputMessage"));
		if (a == null) {
			return false;
		}
		n = a.length();
		return true;
	}

	private void start() {
		vpg.init();
		vpg.ieStart();
		generatePermutations(n);
		vpg.ieEnd();
	}

	private void swap(int i, int j) {
		StringInformation t = a.elementAt(i);
		a.setAt(a.elementAt(j), i);
		a.setAt(t, j);
	}
}
