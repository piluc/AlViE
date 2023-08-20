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

public class FastExponentiation {
	private int a;

	private int k;

	private int[] b;

	private int n;

	private VisualFastExponentiation vfe;

	private class VisualFastExponentiation extends VisualInnerClass {
		private Matrix<StringInformation> data;

		private MatrixXMLDrawerUtility<StringInformation> dataDrawer;

		private Integer[] dataIndex;

		private String[] dataColor;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private VisualFastExponentiation(String visFile) {
			super("fastExponentiation", visFile);
		}

		private void init() {
			data = new Matrix<StringInformation>(4, k + 1,
					new StringInformation());
			data.setAt(new StringInformation("i"), 0, 0);
			data.setAt(new StringInformation("b[i]"), 1, 0);
			data.setAt(new StringInformation("c"), 2, 0);
			data.setAt(new StringInformation("d"), 3, 0);
			for (int c = 1; c < k + 1; c++) {
				data.setAt(new StringInformation("" + (k - c)), 0, c);
				data.setAt(new StringInformation("" + b[k - c]), 1, c);
			}
			for (int r = 2; r < 4; r++) {
				for (int c = 1; c < k + 1; c++) {
					data.setAt(new StringInformation(" "), r, c);
				}
			}
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
		}

		private void initDataDifferentialDrawArrays() {
			dataIndex = new Integer[4 * (k + 1)];
			dataColor = new String[4 * (k + 1)];
			for (int r = 0; r < 4; r++) {
				dataIndex[r * (k + 1)] = r * (k + 1);
				dataColor[r * (k + 1)] = getResource("dataFirstColumnElementColor");
			}
			for (int r = 0; r < 4; r++) {
				for (int c = 1; c < k + 1; c++) {
					dataIndex[r * (k + 1) + c] = r * (k + 1) + c;
					dataColor[r * (k + 1) + c] = getResource("dataElementColor");
				}
			}
		}

		private void ieIterationEnd(int i, int c, int d, int bit) {
			data.setAt(new StringInformation("" + c), 2, k - i);
			data.setAt(new StringInformation("" + d), 3, k - i);
			for (int r = 0; r < 4; r++) {
				dataColor[r * (k + 1) + k - i] = getResource("dataDoneElementColor");
			}
			dataDrawer.startXMLFile(getResource("algorithmName"));
			dataDrawer.startStep(step++);
			dataDrawer
					.draw(dataIndex, dataColor, getResource("ieIterationEnd"));
			if (isPseudocodeVisible) {
				if (bit == 0) {
					pseudocodeDrawer.draw("", new int[] { 3 });
				} else {
					pseudocodeDrawer.draw("", new int[] { 3, 5 });
				}
			}
			dataDrawer.endStep();
		}

		private void ieEnd() {
			dataDrawer.startStep(step++);
			dataDrawer.draw(dataIndex, dataColor, getResource("ieEnd"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", null);
			}
			dataDrawer.endStep();
			dataDrawer.endXMLFile();
		}

		private void ieStart() {
			dataDrawer.startXMLFile(getResource("algorithmName"));
			dataDrawer.startStep(step++);
			dataDrawer.draw(dataIndex, dataColor, getResource("ieStart"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 1 });
			}
			dataDrawer.endStep();
		}
	}

	private void fastExponentiation() {
		int c = 0;
		int d = 1;
		for (int i = k - 1; i >= 0; i--) {
			c = 2 * c;
			d = (d * d) % n;
			if (b[i] == 1) {
				c = c + 1;
				d = (d * a) % n;
			}
			vfe.ieIterationEnd(i, c, d, b[i]);
		}
	}

	public void execute(String visFile) {
		vfe = new VisualFastExponentiation(visFile);
		if (readInput()) {
			vfe.init();
			vfe.ieStart();
			fastExponentiation();
			vfe.ieEnd();
			MessageUtility
					.algorithmTerminated(vfe.getResource("algorithmName"));
		}
	}

	@SuppressWarnings("unchecked")
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vfe.getAlgorithmPath(), vfe
				.getResource("algorithmFileName"));
		Array<IntInformation> t = (Array<IntInformation>) inputLoader.load(
				"Array", vfe.getResource("selectInputMessage"));
		if ((t == null) || (t.length() != 3))
			return false;
		a = t.elementAt(0).intValue();
		int x = t.elementAt(1).intValue();
		k = (int) Math.ceil(Math.log(x) / Math.log(2));
		b = new int[k];
		for (int i = k - 1; i >= 0; i--) {
			b[k - 1 - i] = x % 2;
			x = x / 2;
		}
		n = t.elementAt(2).intValue();
		return true;
	}
}
