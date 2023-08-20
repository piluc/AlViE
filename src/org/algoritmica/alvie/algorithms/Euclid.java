package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class Euclid {
	private int a;

	private int b;

	private VisualEuclid ve;

	private class VisualEuclid extends VisualInnerClass {
		private Matrix<StringInformation> data;

		private MatrixXMLDrawerUtility<StringInformation> dataDrawer;

		private Integer[] dataIndex;

		private String[] dataColor;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private int currentRow;

		private VisualEuclid(String visFile) {
			super("euclid", visFile);
		}

		private void init() {
			int nr = (int) Math.ceil(Math.log(b) / Math.log(2));
			data = new Matrix<StringInformation>(nr, 7, new StringInformation());
			data.setAt(new StringInformation("a"), 0, 0);
			data.setAt(new StringInformation("b"), 0, 1);
			data.setAt(new StringInformation("a/b"), 0, 2);
			data.setAt(new StringInformation("a%b"), 0, 3);
			data.setAt(new StringInformation("d"), 0, 4);
			data.setAt(new StringInformation("x"), 0, 5);
			data.setAt(new StringInformation("y"), 0, 6);
			for (int r = 1; r < nr; r++) {
				for (int c = 0; c < 7; c++) {
					data.setAt(new StringInformation(" "), r, c);
				}
			}
			data.setAt(new StringInformation("" + a), 1, 0);
			data.setAt(new StringInformation("" + b), 1, 1);
			dataDrawer = new MatrixXMLDrawerUtility<StringInformation>(data,
					getResource("dataName"), getOutputStream());
			dataDrawer.setOriginX(Integer
					.parseInt(getResource("dataDrawerOriginX")));
			dataDrawer.setOriginY(Integer
					.parseInt(getResource("dataDrawerOriginY")));
			dataDrawer.setDefaultFont(Font.decode(getResource("dataFont")));
			dataDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("dataElementWidth")));
			dataDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("dataElementHeight")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			initDataDifferentialDrawArrays();
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			currentRow = 1;
		}

		private void initDataDifferentialDrawArrays() {
			dataIndex = new Integer[data.height() * 7];
			dataColor = new String[data.height() * 7];
			for (int j = 0; j < 7; j++) {
				dataIndex[j] = j;
				dataColor[j] = getResource("dataFirstRowElementColor");
			}
			for (int i = 1; i < data.height(); i++) {
				for (int j = 0; j < 7; j++) {
					dataIndex[i * data.height() + j] = i * data.height() + j;
					dataColor[i * data.height() + j] = getResource("dataElementColor");
				}
			}
		}

		private void ieBZero(int ca) {
			data.setAt(new StringInformation("--"), currentRow, 2);
			data.setAt(new StringInformation("--"), currentRow, 3);
			data.setAt(new StringInformation("" + ca), currentRow, 4);
			data.setAt(new StringInformation("" + 1), currentRow, 5);
			data.setAt(new StringInformation("" + 0), currentRow, 6);
			for (int c = 2; c < 7; c++) {
				dataColor[currentRow * 7 + c] = getResource("dataDoneElementColor");
			}
			dataDrawer.startXMLFile(getResource("algorithmName"));
			dataDrawer.startStep(step++);
			dataDrawer.draw(dataIndex, dataColor, getResource("ieBZero"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 2 });
			}
			dataDrawer.endStep();
		}

		private void ieQuotientComputed(int q, int r) {
			data.setAt(new StringInformation("" + q), currentRow, 2);
			data.setAt(new StringInformation("" + r), currentRow, 3);
			dataColor[currentRow * 7 + 2] = getResource("dataDoneElementColor");
			dataColor[currentRow * 7 + 3] = getResource("dataDoneElementColor");
			dataDrawer.startXMLFile(getResource("algorithmName"));
			dataDrawer.startStep(step++);
			dataDrawer.draw(dataIndex, dataColor,
					getResource("ieQuotientComputed"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 3, 4 });
			}
			dataDrawer.endStep();
		}

		private void ieAfterRecursiveCall(int d, int x, int y) {
			for (int c = 0; c < 7; c++) {
				dataColor[currentRow * 7 + c] = getResource("dataElementColor");
			}
			currentRow = currentRow - 1;
			data.setAt(new StringInformation("" + d), currentRow, 4);
			data.setAt(new StringInformation("" + x), currentRow, 5);
			data.setAt(new StringInformation("" + y), currentRow, 6);
			dataColor[currentRow * 7 + 4] = getResource("dataDoneElementColor");
			dataColor[currentRow * 7 + 5] = getResource("dataDoneElementColor");
			dataColor[currentRow * 7 + 6] = getResource("dataDoneElementColor");
			dataDrawer.startXMLFile(getResource("algorithmName"));
			dataDrawer.startStep(step++);
			dataDrawer.draw(dataIndex, dataColor,
					getResource("ieAfterRecursiveCall"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 6 });
			}
			dataDrawer.endStep();
		}

		private void ieBeforeRecursiveCall() {
			data.setAt(data.elementAt(currentRow, 1), currentRow + 1, 0);
			data.setAt(data.elementAt(currentRow, 3), currentRow + 1, 1);
			dataColor[currentRow * 7 + 1] = getResource("dataNewCallFirstColor");
			dataColor[currentRow * 7 + 3] = getResource("dataNewCallSecondColor");
			dataColor[(currentRow + 1) * 7 + 0] = getResource("dataNewCallFirstColor");
			dataColor[(currentRow + 1) * 7 + 1] = getResource("dataNewCallSecondColor");
			dataDrawer.startXMLFile(getResource("algorithmName"));
			dataDrawer.startStep(step++);
			dataDrawer.draw(dataIndex, dataColor,
					getResource("ieBeforeRecursiveCall"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 5 });
			}
			dataDrawer.endStep();
			dataColor[currentRow * 7 + 1] = getResource("dataDoneElementColor");
			dataColor[currentRow * 7 + 3] = getResource("dataDoneElementColor");
			dataColor[(currentRow + 1) * 7 + 0] = getResource("dataDoneElementColor");
			dataColor[(currentRow + 1) * 7 + 1] = getResource("dataDoneElementColor");
			currentRow = currentRow + 1;
		}

		private void ieEnd(int[] r) {
			dataDrawer.startStep(step++);
			dataDrawer.draw(dataIndex, dataColor, getResource("ieEnd"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", null);
			}
			dataDrawer.endStep();
			dataDrawer.endXMLFile();
		}

		private void ieStart() {
			dataColor[(currentRow) * 7] = getResource("dataDoneElementColor");
			dataColor[(currentRow) * 7 + 1] = getResource("dataDoneElementColor");
			dataDrawer.startXMLFile(getResource("algorithmName"));
			dataDrawer.startStep(step++);
			dataDrawer.draw(dataIndex, dataColor, getResource("ieStart"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", null);
			}
			dataDrawer.endStep();
		}
	}

	private int[] euclid(int ca, int cb) {
		if (cb == 0) {
			ve.ieBZero(ca);
			return new int[] { ca, 1, 0 };
		} else {
			int q = (ca / cb);
			int r = ca % cb;
			ve.ieQuotientComputed(q, r);
			ve.ieBeforeRecursiveCall();
			int[] p = euclid(cb, r);
			ve.ieAfterRecursiveCall(p[0], p[2], p[1] - q * p[2]);
			return new int[] { p[0], p[2], p[1] - q * p[2] };
		}
	}

	public void execute(String visFile) {
		ve = new VisualEuclid(visFile);
		if (readInput()) {
			ve.init();
			ve.ieStart();
			int[] r = euclid(a, b);
			ve.ieEnd(r);
			MessageUtility.algorithmTerminated(ve.getResource("algorithmName"));
		}
	}

	@SuppressWarnings("unchecked")
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(ve.getAlgorithmPath(), ve
				.getResource("algorithmFileName"));
		Array<IntInformation> t = (Array<IntInformation>) inputLoader.load(
				"Array", ve.getResource("selectInputMessage"));
		if ((t == null) || (t.length() != 2))
			return false;
		a = t.elementAt(0).intValue();
		b = t.elementAt(1).intValue();
		return true;
	}
}
