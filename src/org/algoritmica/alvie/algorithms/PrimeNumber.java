package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class PrimeNumber {
	private class VisualPrimeNumber extends VisualInnerClass {
		private Matrix<IntInformation> matrix;

		private MatrixXMLDrawerUtility<IntInformation> matrixDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] index;

		private String[] color;

		private int currentColumn;

		private boolean isPrime;

		VisualPrimeNumber(String visFile) {
			super("primeNumber", visFile);
		}

		void ieEnd(int n) {
			String message = getResource("ieEnd1")
					+ " "
					+ number
					+ " "
					+ ((isPrime) ? getResource("ieEnd2True")
							: getResource("ieEnd2False"));
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(index, color, message);
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			matrixDrawer.endStep();
			matrixDrawer.endXMLFile();
		}

		void ieEndWhile(int factor) {
			if (factor == number) {
				isPrime = true;
			}
			if (currentColumn > 0) {
				if (currentColumn < matrix.width()) {
					color[currentColumn] = getResource("reachedNumberColor");
					color[matrix.width() + currentColumn] = getResource("reachedNumberColor");
					matrix.setAt(new IntInformation(number % factor), 1,
							currentColumn);
				} else {
					color[currentColumn - 1] = getResource("reachedNumberColor");
					color[matrix.width() + (currentColumn - 1)] = getResource("reachedNumberColor");
					matrix.setAt(new IntInformation(number % (factor - 1)), 1,
							currentColumn - 1);
				}
			}
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(index, color, getResource("ieEndWhile1") + number
					+ " " + getResource("ieEndWhile2"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 4 });
			matrixDrawer.endStep();
		}

		void ieNotAFactor(int factor) {
			matrix.setAt(new IntInformation(number % factor), 1, currentColumn);
			color[matrix.width() + currentColumn] = getResource("notAFactorColor");
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(index, color, getResource("ieNotAFactor1")
					+ number + " " + getResource("ieNotAFactor2"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 2 });
			matrixDrawer.endStep();
		}

		void ieStart(int n) {
			matrixDrawer.startXMLFile(getResource("algorithmName"));
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(index, color, getResource("ieStartMessage1")
					+ number + " " + getResource("ieStartMessage2"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			matrixDrawer.endStep();
		}

		void ieTryFirstNumber() {
			currentColumn = currentColumn + 1;
			color[currentColumn] = getResource("newNumberColor");
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(index, color, getResource("ieTryFirstNumber"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1 });
			matrixDrawer.endStep();
		}

		void ieTryNewNumber(int factor) {
			currentColumn = currentColumn + 1;
			if (currentColumn < matrix.width()) {
				if (currentColumn > 0) {
					color[currentColumn - 1] = getResource("reachedNumberColor");
					color[matrix.width() + (currentColumn - 1)] = getResource("reachedNumberColor");
				}
				color[currentColumn] = getResource("newNumberColor");
				matrixDrawer.startStep(step++);
				matrixDrawer.draw(index, color, getResource("ieTryNewFactor"));
				if (isPseudocodeVisible)
					pseudocodeDrawer.draw("", new int[] { 3 });
				matrixDrawer.endStep();
			}
		}

		void init() {
			matrix = new Matrix<IntInformation>(2, number - 2,
					new IntInformation());
			for (int i = 0; i < number - 2; i++) {
				matrix.setAt(new IntInformation(i + 2), 0, i);
				matrix.setAt(new IntInformation(-1), 1, i);
			}
			matrixDrawer = new MatrixXMLDrawerUtility<IntInformation>(matrix,
					getResource("matrixName"), getOutputStream());
			matrixDrawer.setOriginX(Integer
					.parseInt(getResource("matrixXMLDrawerOriginX")));
			matrixDrawer.setOriginY(Integer
					.parseInt(getResource("matrixXMLDrawerOriginY")));
			matrixDrawer.setDefaultFont(Font.decode(getResource("matrixFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			initMatrixDifferentialDrawingArrays();
			currentColumn = -1;
			isPrime = false;
		}

		private void initMatrixDifferentialDrawingArrays() {
			index = new Integer[2 * matrix.width()];
			color = new String[2 * matrix.width()];
			for (int i = 0; i < 2 * matrix.width(); i = i + 1) {
				index[i] = i;
				color[i] = getResource("notReachedNumberColor");
			}
		}

	}

	Array<IntInformation> input;

	int number;

	private VisualPrimeNumber vpn;

	public void execute(String visFile) {
		vpn = new VisualPrimeNumber(visFile);
		if (readInput()) {
			isPrime();
			MessageUtility
					.algorithmTerminated(vpn.getResource("algorithmName"));
		}
	}

	boolean isPrime() {
		vpn.init();
		vpn.ieStart(number);
		int factor = 2;
		vpn.ieTryFirstNumber();
		while (number % factor != 0) {
			vpn.ieNotAFactor(factor);
			factor = factor + 1;
			vpn.ieTryNewNumber(factor);
		}
		vpn.ieEndWhile(factor);
		vpn.ieEnd(number);
		return (factor == number);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vpn.getAlgorithmPath(), vpn
				.getResource("algorithmFileName"));
		input = (Array) inputLoader.load("Array", vpn
				.getResource("selectInputMessage"));
		if (input == null) {
			return false;
		}
		number = input.elementAt(0).intValue();
		return true;
	}
}
