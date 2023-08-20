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

public class IntervalGraphColoring {
	class VisualIntervalGraphColoring extends VisualInnerClass {
		private MatrixXMLDrawerUtility<IntInformation> endPointDrawer;

		private ArrayXMLDrawerUtility<IntInformation> bucketDrawer,
				colorDrawer;

		private ArrayXMLDrawerUtility<IntPairInformation> intervalDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] bIndex, cIndex, eIndex, iIndex;

		private String[] bColor, cColor, eColor, iColor;

		public VisualIntervalGraphColoring(String visFile) {
			super("intervalGraphColoring", visFile);
		}

		void ieColorReleased(int i, int j) {
			bColor[freeColors - 1] = getResource("bucketFreeColor");
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor, getResource("ieColorReleased"));
			bucketDrawer.draw(bIndex, bColor, "");
			colorDrawer.draw(cIndex, cColor, "");
			endPointDrawer.draw(eIndex, eColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 10, 11 });
			intervalDrawer.endStep();
			cColor[i] = getResource("colorColor");
			eColor[j] = getResource("examinedEndPointColor");
			eColor[2 * n + j] = getResource("examinedEndPointColor");
			eColor[2 * n * 2 + j] = getResource("examinedEndPointColor");
			iColor[i] = getResource("intervalColor");
		}

		void ieEnd() {
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(getResource("ieEnd"));
			colorDrawer.draw("");
			intervalDrawer.endStep();
			intervalDrawer.endXMLFile();
		}

		void ieEndPointComputed() {
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(getResource("ieEndPointComputed"));
			bucketDrawer.draw(bIndex, bColor, "");
			colorDrawer.draw("");
			endPointDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1, 2, 3, 4, 5 });
			intervalDrawer.endStep();
		}

		void ieFreeColorAssigned(int j, int i) {
			bColor[freeColors] = getResource("bucketColor");
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor,
					getResource("ieFreeColorAssigned"));
			bucketDrawer.draw(bIndex, bColor, "");
			colorDrawer.draw(cIndex, cColor, "");
			endPointDrawer.draw(eIndex, eColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 13, 14 });
			intervalDrawer.endStep();
			cColor[i] = getResource("colorColor");
			eColor[j] = getResource("examinedEndPointColor");
			eColor[2 * n + j] = getResource("examinedEndPointColor");
			eColor[2 * n * 2 + j] = getResource("examinedEndPointColor");
			iColor[i] = getResource("intervalColor");
		}

		void ieLeftEndPointWithFreeColor() {
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor,
					getResource("ieLeftEndPointWithFreeColor"));
			bucketDrawer.draw(bIndex, bColor, "");
			colorDrawer.draw(cIndex, cColor, "");
			endPointDrawer.draw(eIndex, eColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 13, 14 });
			intervalDrawer.endStep();
		}

		void ieLeftEndPointWithNoFreeColor() {
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor,
					getResource("ieLeftEndPointWithNoFreeColor"));
			bucketDrawer.draw(bIndex, bColor, "");
			colorDrawer.draw(cIndex, cColor, "");
			endPointDrawer.draw(eIndex, eColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 16, 17 });
			intervalDrawer.endStep();
		}

		void ieNewColorAssigned(int i, int j) {
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor,
					getResource("ieNewColorAssigned"));
			bucketDrawer.draw(bIndex, bColor, "");
			colorDrawer.draw(cIndex, cColor, "");
			endPointDrawer.draw(eIndex, eColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 16, 17 });
			intervalDrawer.endStep();
			cColor[i] = getResource("colorColor");
			eColor[j] = getResource("examinedEndPointColor");
			eColor[2 * n + j] = getResource("examinedEndPointColor");
			eColor[2 * n * 2 + j] = getResource("examinedEndPointColor");
			iColor[i] = getResource("intervalColor");
		}

		void ieNewEndPointExamined(int i, int j) {
			cColor[i] = getResource("correspondingColorColor");
			eColor[j] = getResource("newEndPointColor");
			eColor[2 * n + j] = getResource("newEndPointColor");
			eColor[2 * n * 2 + j] = getResource("newEndPointColor");
			iColor[i] = getResource("correspondingIntervalColor");
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor,
					getResource("ieNewEndPointExamined"));
			bucketDrawer.draw(bIndex, bColor, "");
			colorDrawer.draw(cIndex, cColor, "");
			endPointDrawer.draw(eIndex, eColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 8 });
			intervalDrawer.endStep();
		}

		void ieRightEndPoint() {
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(iIndex, iColor, getResource("ieRightEndPoint"));
			bucketDrawer.draw(bIndex, bColor, "");
			colorDrawer.draw(cIndex, cColor, "");
			endPointDrawer.draw(eIndex, eColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 10, 11 });
			intervalDrawer.endStep();
		}

		void ieSortingFinished() {
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(getResource("ieSortingFinished"));
			bucketDrawer.draw(bIndex, bColor, "");
			colorDrawer.draw("");
			endPointDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6 });
			intervalDrawer.endStep();
		}

		void ieStart() {
			intervalDrawer.startXMLFile(getResource("algorithmName"));
			intervalDrawer.startStep(step++);
			intervalDrawer.draw(getResource("ieStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			intervalDrawer.endStep();
		}

		void init() {
			bucketDrawer = new ArrayXMLDrawerUtility<IntInformation>(bucket,
					getResource("bucketName"), getOutputStream());
			bucketDrawer.setOriginX(Integer
					.parseInt(getResource("bucketXMLDrawerOriginX")));
			bucketDrawer.setOriginY(Integer
					.parseInt(getResource("bucketXMLDrawerOriginY")));
			bucketDrawer.setDefaultFont(Font.decode(getResource("bucketFont")));
			colorDrawer = new ArrayXMLDrawerUtility<IntInformation>(color,
					getResource("colorName"), getOutputStream());
			colorDrawer.setOriginX(Integer
					.parseInt(getResource("colorXMLDrawerOriginX")));
			colorDrawer.setOriginY(Integer
					.parseInt(getResource("colorXMLDrawerOriginY")));
			colorDrawer.setDefaultFont(Font.decode(getResource("colorFont")));
			intervalDrawer = new ArrayXMLDrawerUtility<IntPairInformation>(
					interval, getResource("intervalName"), getOutputStream());
			intervalDrawer.setOriginX(Integer
					.parseInt(getResource("intervalXMLDrawerOriginX")));
			intervalDrawer.setOriginY(Integer
					.parseInt(getResource("intervalXMLDrawerOriginY")));
			intervalDrawer.setDefaultFont(Font
					.decode(getResource("intervalFont")));
			intervalDrawer.setDefaultShapeWidth(Double.parseDouble(getResource("intervalElementWidth")));
			endPointDrawer = new MatrixXMLDrawerUtility<IntInformation>(
					endPoint, getResource("endPointName"), getOutputStream());
			endPointDrawer.setOriginX(Integer
					.parseInt(getResource("endPointXMLDrawerOriginX")));
			endPointDrawer.setOriginY(Integer
					.parseInt(getResource("endPointXMLDrawerOriginY")));
			endPointDrawer.setDefaultFont(Font
					.decode(getResource("endPointFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setUpDifferentialDrawArrays();
		}

		private void setUpDifferentialDrawArrays() {
			bIndex = new Integer[n];
			cIndex = new Integer[n];
			eIndex = new Integer[6 * n];
			iIndex = new Integer[n];
			bColor = new String[n];
			cColor = new String[n];
			eColor = new String[6 * n];
			iColor = new String[n];
			for (int j = 0; j < n; j++) {
				bIndex[j] = j;
				cIndex[j] = j;
				iIndex[j] = j;
				bColor[j] = getResource("bucketColor");
				cColor[j] = getResource("colorColor");
				iColor[j] = getResource("intervalColor");
			}
			for (int r = 0; r < 3; r++) {
				for (int c = 0; c < 2 * n; c++) {
					eIndex[2 * n * r + c] = 2 * n * r + c;
					eColor[2 * n * r + c] = getResource("endPointColor");
				}
			}
		}
	}

	Array<IntPairInformation> interval;

	Matrix<IntInformation> endPoint, b;

	Array<IntInformation> bucket, color;

	int colorNumber, freeColors, n;

	VisualIntervalGraphColoring vigc;

	private void coloring() {
		vigc.ieEndPointComputed();
		sortRequests(0, endPoint.width() - 1);
		vigc.ieSortingFinished();
		int rightEndPoint, i;
		for (int j = 0; j < 2 * n; j++) {
			rightEndPoint = endPoint.elementAt(1, j).intValue();
			i = endPoint.elementAt(2, j).intValue();
			vigc.ieNewEndPointExamined(i, j);
			if (rightEndPoint == 1) {
				vigc.ieRightEndPoint();
				bucket.setAt(color.elementAt(i), freeColors);
				freeColors = freeColors + 1;
				vigc.ieColorReleased(i, j);
			} else if (freeColors > 0) {
				vigc.ieLeftEndPointWithFreeColor();
				color.setAt(bucket.elementAt(freeColors - 1), i);
				freeColors = freeColors - 1;
				vigc.ieFreeColorAssigned(j, i);
			} else {
				vigc.ieLeftEndPointWithNoFreeColor();
				color.setAt(new IntInformation(colorNumber), i);
				colorNumber = colorNumber + 1;
				vigc.ieNewColorAssigned(i, j);
			}
		}
	}

	private void merge(int s, int c, int d) {
		int i = s;
		int j = c + 1;
		int k = 0;
		while ((i <= c) && (j <= d)) {
			if (endPoint.elementAt(0, i).isLessThan(endPoint.elementAt(0, j))
					|| (endPoint.elementAt(0, i).isEqual(
							endPoint.elementAt(0, j)) && endPoint.elementAt(1,
							i).isLessThan(endPoint.elementAt(1, j)))) {
				b.setAt(endPoint.elementAt(0, i), 0, k);
				b.setAt(endPoint.elementAt(1, i), 1, k);
				b.setAt(endPoint.elementAt(2, i), 2, k);
				i = i + 1;
				k = k + 1;
			} else {
				b.setAt(endPoint.elementAt(0, j), 0, k);
				b.setAt(endPoint.elementAt(1, j), 1, k);
				b.setAt(endPoint.elementAt(2, j), 2, k);
				j = j + 1;
				k = k + 1;
			}
		}
		for (; i <= c; i = i + 1, k = k + 1) {
			b.setAt(endPoint.elementAt(0, i), 0, k);
			b.setAt(endPoint.elementAt(1, i), 1, k);
			b.setAt(endPoint.elementAt(2, i), 2, k);
		}
		for (; j <= d; j = j + 1, k = k + 1) {
			b.setAt(endPoint.elementAt(0, j), 0, k);
			b.setAt(endPoint.elementAt(1, j), 1, k);
			b.setAt(endPoint.elementAt(2, j), 2, k);
		}
		for (i = s; i <= d; i = i + 1) {
			endPoint.setAt(b.elementAt(0, i - s), 0, i);
			endPoint.setAt(b.elementAt(1, i - s), 1, i);
			endPoint.setAt(b.elementAt(2, i - s), 2, i);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vigc.getAlgorithmPath(), vigc
				.getResource("algorithmFileName"));
		interval = (Array) il.load("Array", vigc
				.getResource("selectInputMessage"));
		if (interval == null) {
			return false;
		}
		n = interval.length();
		bucket = new Array<IntInformation>(n, new IntInformation());
		color = new Array<IntInformation>(n, new IntInformation());
		b = new Matrix<IntInformation>(3, 2 * n, new IntInformation());
		endPoint = new Matrix<IntInformation>(3, 2 * n, new IntInformation());
		for (int i = 0; i < n; i++) {
			bucket.setAt(new IntInformation(-1), i);
			color.setAt(new IntInformation(-1), i);
			endPoint.setAt(new IntInformation(interval.elementAt(i)
					.firstValue()), 0, 2 * i);
			endPoint.setAt(new IntInformation(0), 1, 2 * i);
			endPoint.setAt(new IntInformation(i), 2, 2 * i);
			endPoint.setAt(new IntInformation(interval.elementAt(i)
					.secondValue()), 0, 2 * i + 1);
			endPoint.setAt(new IntInformation(1), 1, 2 * i + 1);
			endPoint.setAt(new IntInformation(i), 2, 2 * i + 1);
		}
		colorNumber = 0;
		freeColors = 0;
		return true;
	}

	public void execute(String visFile) {
		vigc = new VisualIntervalGraphColoring(visFile);
		if (readInput()) {
			vigc.init();
			vigc.ieStart();
			coloring();
			vigc.ieEnd();
			MessageUtility.algorithmTerminated(vigc.getResource("algorithmName"));
		}
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
