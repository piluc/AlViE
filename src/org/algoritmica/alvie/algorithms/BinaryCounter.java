package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class BinaryCounter {
	class VisualBinaryCounter extends VisualInnerClass {
		private Matrix<StringInformation> data;

		private MatrixXMLDrawerUtility<StringInformation> dataDrawer;

		private ArrayXMLDrawerUtility<IntInformation> counterDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		Integer[] index, indexData;

		String[] color, colorData;

		int credit, n, tn;

		private boolean isPseudocodeVisible;

		public VisualBinaryCounter(String visFile) {
			super("binaryCounter", visFile);
		}

		private void ieBitOneComplemented(int i) {
			counterDrawer.startStep(step++);
			counterDrawer.draw(index, color, getResource("ieBitOneComplemented"));
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 3, 4 });
			counterDrawer.endStep();
			color[i] = getResource("counterColor");
			colorData[numberBits+3+i+1] = getResource("dataColor");
		}

		private void ieBitOneFound(int i) {
			tn = tn + 1;
			credit = credit - 1;
			color[i] = getResource("currentBitColor");
			colorData[numberBits+3+i+1] = getResource("currentBitColor");
			counterDrawer.startStep(step++);
			counterDrawer.draw(index, color, getResource("ieBitOneFound"));
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 3, 4 });
			counterDrawer.endStep();
			int ti = Integer.parseInt(data.elementAt(1, i+1).stringValue());
			ti = ti+1;
			data.setAt(new StringInformation(""+ti), 1, i+1);
			data.setAt(new StringInformation(""+tn), 1, numberBits+1);
			data.setAt(new StringInformation(""+credit), 1, numberBits+2);
		}

		private void ieBitZeroFound(int i) {
			tn = tn + 1;
			credit = credit + 1;
			color[i] = getResource("currentBitColor");
			colorData[numberBits+3+i+1] = getResource("currentBitColor");
			counterDrawer.startStep(step++);
			counterDrawer.draw(index, color, getResource("ieBitZeroFound"));
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6 });
			counterDrawer.endStep();
			int ti = Integer.parseInt(data.elementAt(1, i+1).stringValue());
			ti = ti+1;
			data.setAt(new StringInformation(""+ti), 1, i+1);
			data.setAt(new StringInformation(""+tn), 1, numberBits+1);
			data.setAt(new StringInformation(""+credit), 1, numberBits+2);
		}

		private void ieBitZeroComplemented(int i) {
			counterDrawer.startStep(step++);
			counterDrawer.draw(index, color, getResource("ieBitZeroComplemented"));
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 3, 4 });
			counterDrawer.endStep();
			color[i] = getResource("counterColor");
			colorData[numberBits+3+i+1] = getResource("dataColor");
		}

		private void ieEnd() {
			counterDrawer.startStep(step++);
			counterDrawer.draw(getResource("ieEnd"));
			dataDrawer.draw(indexData, colorData, "");
			counterDrawer.endStep();
			counterDrawer.endXMLFile();
		}

		private void ieNextValueGenerated() {
			counterDrawer.startStep(step++);
			counterDrawer.draw(getResource("ieNextValueGenerated"));
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 0 });
			counterDrawer.endStep();
		}

		private void ieNextValueStarted() {
			n = n + 1;
			data.setAt(new StringInformation(""+n), 1, 0);
			counterDrawer.startStep(step++);
			counterDrawer.draw(getResource("ieNextValueStarted"));
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 0 });
			counterDrawer.endStep();
		}

		private void ieStart() {
			counterDrawer.startXMLFile(getResource("algorithmName"));
			counterDrawer.startStep(step++);
			counterDrawer.draw(getResource("ieStart"));
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			counterDrawer.endStep();
		}

		private void init() {
			data = new Matrix<StringInformation>(2, numberBits + 3,
					new StringInformation());
			data.setAt(new StringInformation("i"), 0, 0);
			for (int c = 0; c < numberBits; c++) {
				data.setAt(new StringInformation("T" + c), 0, c + 1);
			}
			data.setAt(new StringInformation("T(i)"), 0, numberBits + 1);
			data.setAt(new StringInformation("c"), 0, numberBits + 2);
			data.setAt(new StringInformation(" "), 1, 0);
			for (int c = 1; c < numberBits + 3; c++) {
				data.setAt(new StringInformation("0"), 1, c);
			}
			dataDrawer = new MatrixXMLDrawerUtility<StringInformation>(data,
					getResource("dataName"), getOutputStream());
			dataDrawer.setOriginX(Integer
					.parseInt(getResource("dataXMLDrawerOriginX")));
			dataDrawer.setOriginY(Integer
					.parseInt(getResource("dataXMLDrawerOriginY")));
			dataDrawer.setDefaultFont(Font.decode(getResource("dataFont")));
			dataDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("dataElementWidth")));
			setUpDifferentialDrawArraysData();
			counterDrawer = new ArrayXMLDrawerUtility<IntInformation>(counter,
					getResource("counterName"), getOutputStream());
			counterDrawer.setOriginX(Integer
					.parseInt(getResource("counterXMLDrawerOriginX")));
			counterDrawer.setOriginY(Integer
					.parseInt(getResource("counterXMLDrawerOriginY")));
			counterDrawer.setDefaultFont(Font
					.decode(getResource("counterFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			tn = 0;
			n = 0;
			credit = 0;
			step = 0;
			setUpDifferentialDrawArrays();
		}

		private void setUpDifferentialDrawArraysData() {
			indexData = new Integer[2 * (numberBits + 3)];
			colorData = new String[2 * (numberBits + 3)];
			for (int c = 0; c < numberBits + 3; c++) {
				indexData[c] = c;
				colorData[c] = getResource("dataFirstRowColor");
				indexData[c + numberBits + 3] = c + numberBits + 3;
				colorData[c + numberBits + 3] = getResource("dataColor");
			}
		}

		private void setUpDifferentialDrawArrays() {
			int l = counter.length();
			index = new Integer[l];
			color = new String[l];
			for (int i = 0; i < l; i = i + 1) {
				index[i] = i;
				color[i] = getResource("counterColor");
			}
		}

	}

	private ArrayI<IntInformation> counter;

	private int numberBits;

	private VisualBinaryCounter vbc;

	public void execute(String visFile) {
		vbc = new VisualBinaryCounter(visFile);
		if (readInput()) {
			vbc.init();
			vbc.ieStart();
			while (increment()) {
				vbc.ieNextValueGenerated();
			}
			vbc.ieEnd();
			MessageUtility.algorithmTerminated(vbc.getResource("algorithmName"));
		}
	}

	private boolean increment() {
		vbc.ieNextValueStarted();
		int i = numberBits - 1;
		while ((i >= 0) && (counter.elementAt(i).intValue() == 1)) {
			vbc.ieBitOneFound(i);
			counter.setAt(new IntInformation(0), i);
			vbc.ieBitOneComplemented(i);
			i = i - 1;
		}
		if (i >= 0) {
			vbc.ieBitZeroFound(i);
			counter.setAt(new IntInformation(1), i);
			vbc.ieBitZeroComplemented(i);
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private boolean readInput() {
		InputLoader il = new InputLoader(vbc.getAlgorithmPath(), vbc
				.getResource("algorithmFileName"));
		Array<IntInformation> a = (Array<IntInformation>) il.load("Array", vbc
				.getResource("selectInputMessage"));
		if (a == null || a.length() != 1 || a.elementAt(0).intValue() <= 0) {
			return false;
		}
		numberBits = a.elementAt(0).intValue();
		counter = new Array<IntInformation>(numberBits, new IntInformation());
		for (int i = 0; i < numberBits; i++) {
			counter.setAt(new IntInformation(0), i);
		}
		return true;
	}

}
