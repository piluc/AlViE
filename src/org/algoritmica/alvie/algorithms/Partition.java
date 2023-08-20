package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.BooleanInformation;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class Partition {
	private class VisualPartition extends VisualInnerClass {
		private ArrayXMLDrawerUtility<IntInformation> aDrawer;

		private Matrix<StringInformation> vP;

		private MatrixXMLDrawerUtility<StringInformation> vPDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		String[] aColor, vPColor;

		private VisualPartition(String visFile) {
			super("partition", visFile);
		}

		private void ieEnd() {
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieEnd"));
			vPDrawer.draw(new Integer[] { (n + 1) * (s + 2) + s + 1 },
					vPColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 15 });
			}
			aDrawer.endStep();
			aDrawer.endXMLFile();
		}

		private void ieFirstIfSatisfied(int i, int j) {
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieFirstIfSatisfied"));
			vPDrawer.draw(new Integer[] { (i + 1) * (s + 2) + j + 1,
					i * (s + 2) + j + 1 }, vPColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 9 });
			}
			aDrawer.endStep();
			vP.setAt(new StringInformation("TRUE"), i + 1, j + 1);
		}

		private void iePHasBeenInitialized() {
			vP.setAt(new StringInformation("TRUE"), 1, 1);
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("iePHasBeenInitialized"));
			vPDrawer.draw("");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 1, 2, 3, 4, 5 });
			}
			aDrawer.endStep();
		}

		private void ieSecondIfSatisfied(int i, int j) {
			aDrawer.startStep(step++);
			aDrawer.draw(new Integer[] { i - 1 }, aColor,
					getResource("ieSecondIfSatisfied"));
			vPDrawer.draw(new Integer[] { (i + 1) * (s + 2) + j + 1,
					i * (s + 2) + j - a.elementAt(i - 1).intValue() + 1 },
					vPColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 12 });
			}
			aDrawer.endStep();
			vP.setAt(new StringInformation("TRUE"), i + 1, j + 1);
		}

		private void ieStart() {
			aDrawer.startXMLFile(getResource("algorithmName"));
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieStart"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 0 });
			}
			aDrawer.endStep();
		}

		private void init() {
			aDrawer = new ArrayXMLDrawerUtility<IntInformation>(a,
					getResource("aTitle"), getOutputStream());
			aDrawer.setOriginX(Integer
					.parseInt(getResource("aXMLDrawerOriginX")));
			aDrawer.setOriginY(Integer
					.parseInt(getResource("aXMLDrawerOriginY")));
			aDrawer.setDefaultFont(Font
					.decode(getResource("datastructureFont")));
			initVParts();
			vPDrawer = new MatrixXMLDrawerUtility<StringInformation>(
					vP, getResource("pTitle"), getOutputStream());
			vPDrawer.setOriginX(Integer
					.parseInt(getResource("pXMLDrawerOriginX")));
			vPDrawer.setOriginY(Integer
					.parseInt(getResource("pXMLDrawerOriginY")));
			vPDrawer.setDefaultFont(Font
					.decode(getResource("datastructureFont")));
			vPDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("pElementWidth")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setDifferentialDrawArrays();
			step = 0;
		}

		private void initVParts() {
			vP = new Matrix<StringInformation>(n + 2, s + 2,
					new StringInformation());
			vP.setAt(new StringInformation(" "), 0, 0);
			for (int c = 1; c < s + 2; c++) {
				vP.setAt(new StringInformation("" + (c - 1)), 0, c);
			}
			String t = "{";
			vP.setAt(new StringInformation(t + "}"), 1, 0);
			t = t + a.elementAt(0).stringValue();
			vP.setAt(new StringInformation(t + "}"), 2, 0);
			for (int r = 3; r < n + 2; r++) {
				t = t + "," + a.elementAt(r - 2).stringValue();
				vP.setAt(new StringInformation(t + "}"), r, 0);
			}
			for (int r = 1; r < n + 2; r++) {
				for (int c = 1; c < s + 2; c++) {
					vP.setAt(new StringInformation("FALSE"), r, c);
				}
			}
		}

		private void setDifferentialDrawArrays() {
			aColor = new String[1];
			aColor[0] = getResource("aUsedColor");
			vPColor = new String[2];
			vPColor[0] = getResource("pNewColor");
			vPColor[1] = getResource("pUsedColor");
		}
	}

	Array<IntInformation> a;

	Matrix<BooleanInformation> p;

	private int n;

	private int s;

	VisualPartition vp;

	public void execute(String visFile) {
		vp = new VisualPartition(visFile);
		if (readInput()) {
			partition();
			MessageUtility.algorithmTerminated("Partition");
		}
	}

	private void partition() {
		vp.init();
		vp.ieStart();
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= s; j++) {
				p.setAt(new BooleanInformation(false), i, j);
			}
		}
		p.setAt(new BooleanInformation(true), 0, 0);
		vp.iePHasBeenInitialized();
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= s; j++) {
				if (p.elementAt(i - 1, j).booleanValue()) {
					vp.ieFirstIfSatisfied(i, j);
					p.setAt(new BooleanInformation(true), i, j);
				}
				if ((!p.elementAt(i, j).booleanValue() && j >= a.elementAt(
						i - 1).intValue())
						&& (p.elementAt(i - 1, j
								- a.elementAt(i - 1).intValue()).booleanValue())) {
					vp.ieSecondIfSatisfied(i, j);
					p.setAt(new BooleanInformation(true), i, j);
				}
			}
		}
		vp.ieEnd();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vp.getAlgorithmPath(), vp
				.getResource("algorithmFileName"));
		a = (Array) inputLoader.load("Array", vp
				.getResource("selectInputMessage"));
		if (a == null) {
			return false;
		}
		n = a.length();
		s = sum() / 2;
		p = new Matrix<BooleanInformation>(n + 1, s + 1,
				new BooleanInformation());
		return true;
	}

	private int sum() {
		int result = 0;
		for (int i = 0; i < a.length(); i++) {
			result = result + a.elementAt(i).intValue();
		}
		return result;
	}

}
