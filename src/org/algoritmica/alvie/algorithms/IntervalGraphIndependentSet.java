package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.IntPairInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class IntervalGraphIndependentSet {
	class VisualIntervalGraphIndependentSet extends VisualInnerClass {
		private MatrixXMLDrawerUtility<IntInformation> rightEndPointDrawer;

		private ArrayXMLDrawerUtility<IntPairInformation> intervalDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] rIndex, iIndex;

		private String[] rColor, iColor;

		private VisualIntervalGraphIndependentSet(String visFile) {
			super("intervalGraphIndependentSet", visFile);
		}

		private void ieEnd() {
			for (int j = 0; j < n; j++) {
				iColor[j] = assign[j] ? getResource("assignedIntervalColor")
						: getResource("intervalColor");
			}
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor, getResource("ieEnd"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 15 });
			intervalDrawer.endStep();
			intervalDrawer.endXMLFile();
		}

		private void ieIntervalAssigned(int i, int j) {
			iColor[i] = getResource("lastAssignedIntervalColor");
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor,
					getResource("ieIntervalAssigned"));
			rightEndPointDrawer.draw(rIndex, rColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 9, 10 });
			intervalDrawer.endStep();
			rColor[j] = getResource("examinedRightEndPointColor");
			rColor[n + j] = getResource("examinedRightEndPointColor");
		}

		private void ieIntervalCanBeAssigned() {
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor,
					getResource("ieIntervalCanBeAssigned"));
			rightEndPointDrawer.draw(rIndex, rColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 8 });
			intervalDrawer.endStep();
			if (last >= 0) {
				iColor[last] = getResource("assignedIntervalColor");
			}
		}

		private void ieIntervalCannotBeAssigned() {
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor,
					getResource("ieIntervalCannotBeAssigned"));
			rightEndPointDrawer.draw(rIndex, rColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 8 });
			intervalDrawer.endStep();
		}

		private void ieIntervalNotAssigned(int i, int j) {
			iColor[i] = getResource("notAssignedIntervalColor");
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor,
					getResource("ieIntervalNotAssigned"));
			rightEndPointDrawer.draw(rIndex, rColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 12 });
			intervalDrawer.endStep();
			rColor[j] = getResource("examinedRightEndPointColor");
			rColor[n + j] = getResource("examinedRightEndPointColor");
		}

		private void ieNewIntervalExamined(int i, int j) {
			rColor[j] = getResource("newRightEndPointColor");
			rColor[n + j] = getResource("newRightEndPointColor");
			iColor[i] = getResource("correspondingIntervalColor");
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor,
					getResource("ieNewIntervalExamined"));
			rightEndPointDrawer.draw(rIndex, rColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 7 });
			intervalDrawer.endStep();
		}

		private void ieRightEndPointComputed() {
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(getResource("ieRightEndPointComputed"));
			rightEndPointDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1, 2, 3 });
			intervalDrawer.endStep();
		}

		private void ieSortingFinished() {
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(getResource("ieSortingFinished"));
			rightEndPointDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 4 });
			intervalDrawer.endStep();
		}

		private void ieStart() {
			intervalDrawer.startXMLFile(getResource("algorithmName"));
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(getResource("ieStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			intervalDrawer.endStep();
		}

		private void init() {
			intervalDrawer = new ArrayXMLDrawerUtility<IntPairInformation>(
					interval, getResource("intervalName"), getOutputStream());
			intervalDrawer.setOriginX(Integer
					.parseInt(getResource("intervalXMLDrawerOriginX")));
			intervalDrawer.setOriginY(Integer
					.parseInt(getResource("intervalXMLDrawerOriginY")));
			intervalDrawer.setDefaultFont(Font
					.decode(getResource("intervalFont")));
			intervalDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("intervalElementWidth")));
			rightEndPointDrawer = new MatrixXMLDrawerUtility<IntInformation>(
					rightEndPoint, getResource("rightEndPointName"),
					getOutputStream());
			rightEndPointDrawer.setOriginX(Integer
					.parseInt(getResource("rightEndPointXMLDrawerOriginX")));
			rightEndPointDrawer.setOriginY(Integer
					.parseInt(getResource("rightEndPointXMLDrawerOriginY")));
			rightEndPointDrawer.setDefaultFont(Font
					.decode(getResource("rightEndPointFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setUpDifferentialDrawArrays();
			step = 0;
		}

		private void setUpDifferentialDrawArrays() {
			rIndex = new Integer[2 * n];
			iIndex = new Integer[n];
			rColor = new String[2 * n];
			iColor = new String[n];
			for (int j = 0; j < n; j++) {
				iIndex[j] = j;
				iColor[j] = getResource("intervalColor");
			}
			for (int r = 0; r < 2; r++) {
				for (int c = 0; c < n; c++) {
					rIndex[r * n + c] = r * n + c;
					rColor[r * n + c] = getResource("rightEndPointColor");
				}
			}
		}
	}

	Array<IntPairInformation> interval;

	Matrix<IntInformation> rightEndPoint, b;

	boolean[] assign;

	int last, n;

	VisualIntervalGraphIndependentSet vigis;

	public void execute(String visFile) {
		vigis = new VisualIntervalGraphIndependentSet(visFile);
		if (readInput()) {
			vigis.init();
			vigis.ieStart();
			independentSet();
			vigis.ieEnd();
			MessageUtility.algorithmTerminated(vigis.getResource("algorithmName"));
		}
	}

	private void independentSet() {
		vigis.ieRightEndPointComputed();
		sortRequests(0, rightEndPoint.width() - 1);
		vigis.ieSortingFinished();
		int i;
		for (int j = 0; j < n; j++) {
			i = rightEndPoint.elementAt(1, j).intValue();
			vigis.ieNewIntervalExamined(i, j);
			if ((last < 0)
					|| (interval.elementAt(i).firstValue() > interval
							.elementAt(last).secondValue())) {
				vigis.ieIntervalCanBeAssigned();
				assign[i] = true;
				last = i;
				vigis.ieIntervalAssigned(i, j);
			} else {
				vigis.ieIntervalCannotBeAssigned();
				assign[i] = false;
				vigis.ieIntervalNotAssigned(i, j);
			}
		}
	}

	private void merge(int s, int c, int d) {
		int i = s;
		int j = c + 1;
		int k = 0;
		while ((i <= c) && (j <= d)) {
			if (rightEndPoint.elementAt(0, i).isLessThan(
					rightEndPoint.elementAt(0, j))) {
				b.setAt(rightEndPoint.elementAt(0, i), 0, k);
				b.setAt(rightEndPoint.elementAt(1, i), 1, k);
				i = i + 1;
				k = k + 1;
			} else {
				b.setAt(rightEndPoint.elementAt(0, j), 0, k);
				b.setAt(rightEndPoint.elementAt(1, j), 1, k);
				j = j + 1;
				k = k + 1;
			}
		}
		for (; i <= c; i = i + 1, k = k + 1) {
			b.setAt(rightEndPoint.elementAt(0, i), 0, k);
			b.setAt(rightEndPoint.elementAt(1, i), 1, k);
		}
		for (; j <= d; j = j + 1, k = k + 1) {
			b.setAt(rightEndPoint.elementAt(0, j), 0, k);
			b.setAt(rightEndPoint.elementAt(1, j), 1, k);
		}
		for (i = s; i <= d; i = i + 1) {
			rightEndPoint.setAt(b.elementAt(0, i - s), 0, i);
			rightEndPoint.setAt(b.elementAt(1, i - s), 1, i);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean readInput() {
		InputLoader il = new InputLoader(vigis.getAlgorithmPath(), vigis
				.getResource("algorithmFileName"));
		interval = (Array<IntPairInformation>) il.load("Array",
				"Seleziona l'array degli intervalli");
		if (interval == null) {
			return false;
		}
		n = interval.length();
		assign = new boolean[n];
		b = new Matrix<IntInformation>(2, n, new IntInformation());
		rightEndPoint = new Matrix<IntInformation>(2, n, new IntInformation());
		for (int i = 0; i < n; i++) {
			rightEndPoint.setAt(new IntInformation(interval.elementAt(i)
					.secondValue()), 0, i);
			rightEndPoint.setAt(new IntInformation(i), 1, i);
			assign[i] = false;
		}
		last = -1;
		return true;
	}

	private void sortRequests(int left, int right) {
		if (left >= right)
			return;
		int middle = (left + right) / 2;
		sortRequests(left, middle);
		sortRequests(middle + 1, right);
		merge(left, middle, right);
	}

}
