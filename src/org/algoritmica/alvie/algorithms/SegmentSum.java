package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class SegmentSum {
	class VisualSegmentSum extends VisualInnerClass {
		private ArrayXMLDrawerUtility<IntInformation> arrayDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private int currentLeft, currentRight, left, right;

		private Integer[] arrayIndex;

		private String[] arrayColor;

		private VisualSegmentSum(String visFile) {
			super("segmentSum", visFile);
		}

		private void ieCheckSum() {
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(arrayIndex, arrayColor, getResource("ieCheckSum1")
					+ sum + " " + getResource("ieCheckSum2") + max
					+ getResource("ieCheckSum3"));
			pseudocodeDrawer.draw("", new int[] { 9 });
			arrayDrawer.endStep();
		}

		private void ieEnd() {
			for (int i = currentLeft; i <= currentRight; i++) {
				arrayColor[i] = getResource("arrayColor");
			}
			for (int i = left; i <= right; i++) {
				arrayColor[i] = getResource("maxSegmentColor");
			}
			arrayDrawer.startStep(step++);
			arrayDrawer
					.draw(arrayIndex, arrayColor, getResource("ieEnd") + max);
			arrayDrawer.endStep();
			arrayDrawer.endXMLFile();
		}

		private void ieStart() {
			arrayDrawer.startXMLFile(getResource("algorithmName"));
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(arrayIndex, arrayColor, getResource("ieStart"));
			pseudocodeDrawer.draw("", null);
			arrayDrawer.endStep();
			currentLeft = 0;
			currentRight = -1;
		}

		private void ieSumGreaterThanMax() {
			for (int i = currentLeft; i <= currentRight; i++) {
				arrayColor[i] = getResource("maxSegmentColor");
			}
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(arrayIndex, arrayColor,
					getResource("ieSumGreaterThanMax"));
			pseudocodeDrawer.draw("", new int[] { 9 });
			arrayDrawer.endStep();
			left = currentLeft;
			right = currentRight;
			for (int i = currentLeft; i <= currentRight; i++) {
				arrayColor[i] = getResource("segmentSumColor");
			}
		}

		private void ieSumGreaterThanZero() {
			arrayColor[j] = getResource("newElementColor");
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(arrayIndex, arrayColor,
					getResource("ieSumGreaterThanZero1") + sum
							+ getResource("ieSumGreaterThanZero2"));
			pseudocodeDrawer.draw("", new int[] { 5 });
			arrayDrawer.endStep();
			arrayColor[j] = getResource("segmentSumColor");
			currentRight = j;
		}

		private void ieSumLessThanZero() {
			arrayColor[j] = getResource("newElementColor");
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(arrayIndex, arrayColor,
					getResource("ieSumLessThanZero1") + sum
							+ getResource("ieSumLessThanZero2"));
			pseudocodeDrawer.draw("", new int[] { 7 });
			arrayDrawer.endStep();
			for (int i = currentLeft; i <= currentRight; i++) {
				arrayColor[i] = getResource("arrayColor");
			}
			arrayColor[j] = getResource("segmentSumColor");
			currentLeft = j;
			currentRight = j;
		}

		void init() {
			arrayDrawer = new ArrayXMLDrawerUtility<IntInformation>(array,
					getResource("arrayName"), getOutputStream());
			arrayDrawer.setOriginX(Integer
					.parseInt(getResource("arrayXMLDrawerOriginX")));
			arrayDrawer.setOriginY(Integer
					.parseInt(getResource("arrayXMLDrawerOriginY")));
			arrayDrawer.setDefaultFont(Font.decode(getResource("arrayFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			initDifferentialDrawArrays();
			step = 0;
		}

		private void initDifferentialDrawArrays() {
			arrayIndex = new Integer[array.length()];
			arrayColor = new String[array.length()];
			for (int i = 0; i < array.length(); i++) {
				arrayIndex[i] = i;
				arrayColor[i] = getResource("arrayColor");
			}
		}
	}

	private Array<IntInformation> array;

	private int j, max, n, sum;

	private VisualSegmentSum vss;

	public void execute(String visFile) {
		vss = new VisualSegmentSum(visFile);
		if (readInput()) {
			segmentSum();
			MessageUtility.algorithmTerminated(vss.getResource("algorithmName"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader algorithmLoader = new InputLoader(vss.getAlgorithmPath(), vss
				.getResource("algorithmFileName"));
		array = (Array) algorithmLoader.load("Array", vss
				.getResource("selectInputMessage"));
		if (array == null) {
			return false;
		}
		n = array.length();
		return true;
	}

	private void segmentSum() {
		vss.init();
		vss.ieStart();
		max = 0;
		sum = max;
		for (j = 0; j < n; j++) {
			if (sum > 0) {
				vss.ieSumGreaterThanZero();
				sum = sum + array.elementAt(j).intValue();
			} else {
				vss.ieSumLessThanZero();
				sum = array.elementAt(j).intValue();
			}
			vss.ieCheckSum();
			if (sum > max) {
				max = sum;
				vss.ieSumGreaterThanMax();
			}
		}
		vss.ieEnd();
	}

}
