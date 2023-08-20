package org.algoritmica.alvie.algorithms;

import java.awt.Color;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.BooleanInformation;
import org.algoritmica.alvie.information.GeometricObjectInformation;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.GeometricFigureXMLDrawerUtility;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class SubsetSumNPCompleteness {
	private class VisualSubsetSumNPCompleteness extends VisualInnerClass {
		private GeometricFigure text;
		private GeometricFigureXMLDrawerUtility textDrawer;
		private MatrixXMLDrawerUtility<StringInformation> matrixDrawer;
		private Integer[] matrixId;
		private String[] matrixColor;

		private VisualSubsetSumNPCompleteness(String visFile) {
			super("subsetSumNPCompleteness", visFile);
		}

		private void createAssignmentText() {
			String as = "";
			for (int i = 0; i < assignment.length() - 1; i++) {
				as = as + "x" + (i + 1) + "="
						+ assignment.elementAt(i).stringValue() + ", ";
			}
			as = as
					+ "x"
					+ assignment.length()
					+ "="
					+ assignment.elementAt(assignment.length() - 1)
							.stringValue();
			text.add(new GeometricObjectInformation(as, 0, 20));
		}

		private void createFormulaText() {
			String fs = "";
			for (int r = 0; r < m; r++) {
				String cs = "[";
				for (int c = 0; c < 3; c++) {
					if (formula.elementAt(r, c).intValue() % 2 == 0) {
						cs = cs + "(NOT x"
								+ (formula.elementAt(r, c).intValue() / 2)
								+ ")";
					} else {
						cs = cs + "x"
								+ (1 + formula.elementAt(r, c).intValue() / 2);
					}
					if (c < 2) {
						cs = cs + " OR ";
					} else {
						cs = cs + "]";
					}
				}
				if (r < m - 1) {
					fs = fs + cs + " AND ";
				} else {
					fs = fs + cs;
				}
			}
			text = new GeometricFigure();
			text.add(new GeometricObjectInformation(fs, 0, 0));
			textDrawer = new GeometricFigureXMLDrawerUtility(text,
					getResource("formulaTitle"), getOutputStream());
			textDrawer.setOriginX(Integer
					.parseInt(getResource("formulaXMLDrawerOriginX")));
			textDrawer.setOriginY(Integer
					.parseInt(getResource("formulaXMLDrawerOriginY")));
			textDrawer.setDefaultColor(Color.BLACK);
		}

		private void ieClauseDigit(int w) {
			for (int c = 1; c < numberColumns; c++) {
				matrixColor[(2 * (n + w) + 1) * numberColumns + c] = getResource("justFilledElementColor");
				matrixColor[(2 * (n + w) + 2) * numberColumns + c] = getResource("justFilledElementColor");
			}
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieClauseDigit"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			for (int c = 1; c <= n + m; c++) {
				matrixColor[(2 * (n + w) + 1) * numberColumns + c] = getResource("filledElementColor");
				matrixColor[(2 * (n + w) + 2) * numberColumns + c] = getResource("filledElementColor");
			}
		}

		private void ieClauseWithOne(int w) {
			for (int c = 0; c < numberColumns; c++) {
				matrixColor[(2 * n + 2 * w + 1) * numberColumns + c] = getResource("justFilledElementColor");
				matrixColor[(2 * n + 2 * w + 2) * numberColumns + c] = getResource("justFilledElementColor");
			}
			matrixColor[(numberRows - 1) * numberColumns + n + w + 1] = getResource("reachedSumElementColor");
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieClauseWithOne"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			for (int c = 0; c < numberColumns; c++) {
				matrixColor[(2 * n + 2 * w + 1) * numberColumns + c] = getResource("filledElementColor");
				matrixColor[(2 * n + 2 * w + 2) * numberColumns + c] = getResource("filledElementColor");
			}
		}

		private void ieClauseWithThree(int w) {
			matrixColor[(numberRows - 1) * numberColumns + n + w + 1] = getResource("reachedSumElementColor");
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieClauseWithThree"));
			textDrawer.draw("");
			matrixDrawer.endStep();
		}

		private void ieClauseWithTwo(int w) {
			for (int c = 0; c < numberColumns; c++) {
				matrixColor[(2 * n + 2 * w + 1) * numberColumns + c] = getResource("justFilledElementColor");
			}
			matrixColor[(numberRows - 1) * numberColumns + n + w + 1] = getResource("reachedSumElementColor");
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieClauseWithTwo"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			for (int c = 0; c < numberColumns; c++) {
				matrixColor[(2 * n + 2 * w + 1) * numberColumns + c] = getResource("filledElementColor");
			}
		}

		private void ieEmptyNumbers() {
			setDifferentialDrawArrays();
			for (int r = 1; r < numberRows; r++) {
				matrixColor[r * numberColumns] = getResource("justFilledElementColor");
			}
			for (int c = 1; c < numberColumns; c++) {
				matrixColor[c] = getResource("firstRowElementColor");
			}
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieEmptyNumbers"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			for (int r = 1; r < numberRows; r++) {
				matrixColor[r * numberColumns] = getResource("filledElementColor");
			}
		}

		private void ieEnd() {
			matrixDrawer.endXMLFile();
		}

		private void ieFirstSumDigit() {
			for (int c = 1; c <= n; c++) {
				matrixColor[(numberRows - 1) * numberColumns + c] = getResource("justFilledElementColor");
			}
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieFirstSumDigit"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			for (int c = 1; c <= n; c++) {
				matrixColor[(numberRows - 1) * numberColumns + c] = getResource("filledElementColor");
			}
		}

		private void ieFirstVariableDigit(int v) {
			for (int c = 1; c <= n; c++) {
				matrixColor[(2 * v + 1) * numberColumns + c] = getResource("justFilledElementColor");
				matrixColor[(2 * v + 2) * numberColumns + c] = getResource("justFilledElementColor");
			}
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieFirstVariableDigit"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			for (int c = 1; c <= n; c++) {
				matrixColor[(2 * v + 1) * numberColumns + c] = getResource("filledElementColor");
				matrixColor[(2 * v + 2) * numberColumns + c] = getResource("filledElementColor");
			}
		}

		private void ieMatrixCreated() {
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor, getResource("ieMatrixCreated"));
			textDrawer.draw("");
			matrixDrawer.endStep();
		}

		private void ieNoSubset(int w) {
			matrixColor[(numberRows - 1) * numberColumns + n + w + 1] = getResource("notReachedSumElementColor");
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor, getResource("ieNoSubset"));
			textDrawer.draw("");
			matrixDrawer.endStep();
		}

		private void ieSatAssignmentDefined() {
			createAssignmentText();
			setDifferentialDrawArrays();
			for (int c = 1; c < numberColumns; c++) {
				matrixColor[c] = getResource("firstRowElementColor");
			}
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieSatAssignmentDefined"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			matrixColor[(numberRows - 1) * numberColumns] = getResource("filledElementColor");
		}

		private void ieSecondSumDigit() {
			for (int c = n + 1; c < numberColumns; c++) {
				matrixColor[(numberRows - 1) * numberColumns + c] = getResource("justFilledElementColor");
			}
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieSecondSumDigit"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			for (int c = n + 1; c < numberColumns; c++) {
				matrixColor[(numberRows - 1) * numberColumns + c] = getResource("filledElementColor");
			}
		}

		private void ieSecondVariableDigit(int v) {
			for (int c = n + 1; c < numberColumns; c++) {
				matrixColor[(2 * v + 1) * numberColumns + c] = getResource("justFilledElementColor");
				matrixColor[(2 * v + 2) * numberColumns + c] = getResource("justFilledElementColor");
			}
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieSecondVariableDigit"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			for (int c = n + 1; c < numberColumns; c++) {
				matrixColor[(2 * v + 1) * numberColumns + c] = getResource("filledElementColor");
				matrixColor[(2 * v + 2) * numberColumns + c] = getResource("filledElementColor");
			}
		}

		private void ieStart() {
			createFormulaText();
			textDrawer.startXMLFile(getResource("algorithmName"));
			textDrawer.startStep(step++);
			textDrawer.draw(getResource("ieStart"));
			textDrawer.endStep();
		}

		private void ieSubsetComputed() {
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieSubsetComputed"));
			textDrawer.draw("");
			matrixDrawer.endStep();
		}

		private void ieUnsatAssignmentDefined() {
			text.remove(1);
			createAssignmentText();
			setDifferentialDrawArrays();
			for (int c = 1; c < numberColumns; c++) {
				matrixColor[c] = getResource("firstRowElementColor");
			}
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieUnsatAssignmentDefined"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			matrixColor[(numberRows - 1) * numberColumns] = getResource("filledElementColor");
		}

		private void ieVariableFalse(int v) {
			for (int c = 0; c < numberColumns; c++) {
				matrixColor[(2 * v + 2) * numberColumns + c] = getResource("justFilledElementColor");
			}
			matrixColor[(numberRows - 1) * numberColumns + v + 1] = getResource("reachedSumElementColor");
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieVariableFalse"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			for (int c = 0; c < numberColumns; c++) {
				matrixColor[(2 * v + 2) * numberColumns + c] = getResource("filledElementColor");
			}
		}

		private void ieVariableTrue(int v) {
			for (int c = 0; c < numberColumns; c++) {
				matrixColor[(2 * v + 1) * numberColumns + c] = getResource("justFilledElementColor");
			}
			matrixColor[(numberRows - 1) * numberColumns + v + 1] = getResource("reachedSumElementColor");
			matrixDrawer.startStep(step++);
			matrixDrawer.draw(matrixId, matrixColor,
					getResource("ieVariableTrue"));
			textDrawer.draw("");
			matrixDrawer.endStep();
			for (int c = 0; c < numberColumns; c++) {
				matrixColor[(2 * v + 1) * numberColumns + c] = getResource("filledElementColor");
			}
		}

		private void init() {
			matrixDrawer = new MatrixXMLDrawerUtility<StringInformation>(
					matrix, getResource("matrixName"), getOutputStream());
			matrixDrawer.setOriginX(Integer
					.parseInt(getResource("matrixXMLDrawerOriginX")));
			matrixDrawer.setOriginY(Integer
					.parseInt(getResource("matrixXMLDrawerOriginY")));
			matrixDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("elementWidth")));
			matrixDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("elementHeight")));
		}

		private void setDifferentialDrawArrays() {
			matrixId = new Integer[numberRows * numberColumns];
			matrixColor = new String[numberRows * numberColumns];
			for (int r = 0; r < numberRows; r++) {
				for (int c = 0; c < numberColumns; c++) {
					matrixId[r * numberColumns + c] = r * numberColumns + c;
					matrixColor[r * numberColumns + c] = getResource("unusedNumberColor");
				}
			}
		}
	}

	private VisualSubsetSumNPCompleteness vssnpc;
	private Matrix<IntInformation> formula;
	private int n;
	private int m;
	private int numberRows;
	private int numberColumns;
	private Matrix<StringInformation> matrix;
	private Array<BooleanInformation> assignment, secondAssignment;
	private int[] satisfiedClause;

	private void computeSubset() {
		satisfiedClause = new int[m];
		for (int c = 0; c < m; c++) {
			satisfiedClause[c] = 0;
		}
		for (int v = 0; v < n; v++) {
			if (assignment.elementAt(v).booleanValue()) {
				vssnpc.ieVariableTrue(v);
				for (int w = 0; w < m; w++) {
					if ((formula.elementAt(w, 0).intValue() == 2 * v + 1)
							|| (formula.elementAt(w, 1).intValue() == 2 * v + 1)
							|| (formula.elementAt(w, 2).intValue() == 2 * v + 1)) {
						satisfiedClause[w] = satisfiedClause[w] + 1;
					}
				}
			} else {
				vssnpc.ieVariableFalse(v);
				for (int w = 0; w < m; w++) {
					if ((formula.elementAt(w, 0).intValue() == 2 * v + 2)
							|| (formula.elementAt(w, 1).intValue() == 2 * v + 2)
							|| (formula.elementAt(w, 2).intValue() == 2 * v + 2)) {
						satisfiedClause[w] = satisfiedClause[w] + 1;
					}
				}
			}
		}
		for (int c = 0; c < m; c++) {
			if (satisfiedClause[c] == 0) {
				vssnpc.ieNoSubset(c);
				return;
			} else if (satisfiedClause[c] == 1) {
				vssnpc.ieClauseWithOne(c);
			} else if (satisfiedClause[c] == 2) {
				vssnpc.ieClauseWithTwo(c);
			} else {
				vssnpc.ieClauseWithThree(c);
			}
		}
		vssnpc.ieSubsetComputed();
	}

	private void createMatrix() {
		matrix = new Matrix<StringInformation>(numberRows, numberColumns,
				new StringInformation());
		vssnpc.init();
		vssnpc.ieStart();
		matrix.setAt(new StringInformation(" "), 0, 0);
		matrix.setAt(new StringInformation("s"), numberRows - 1, 0);
		for (int v = 0; v < n; v++) {
			matrix.setAt(new StringInformation("p[" + (v + 1) + "]"),
					2 * v + 1, 0);
			matrix.setAt(new StringInformation("n[" + (v + 1) + "]"),
					2 * v + 2, 0);
			for (int c = 1; c <= n; c++) {
				matrix.setAt(new StringInformation("x" + c), 0, c);
				matrix.setAt(new StringInformation(" "), 2 * v + 1, c);
				matrix.setAt(new StringInformation(" "), 2 * v + 2, c);
				matrix.setAt(new StringInformation(" "), 2 * n + 2 * m + 1, c);
			}
			for (int c = n + 1; c <= n + m; c++) {
				matrix.setAt(new StringInformation("c" + (c - n)), 0, c);
				matrix.setAt(new StringInformation(" "), 2 * v + 1, c);
				matrix.setAt(new StringInformation(" "), 2 * v + 2, c);
				matrix.setAt(new StringInformation(" "), 2 * n + 2 * m + 1, c);
			}
		}
		for (int w = 0; w < m; w++) {
			matrix.setAt(new StringInformation("x[" + (w + 1) + "]"), 2 * n + 2
					* w + 1, 0);
			matrix.setAt(new StringInformation("y[" + (w + 1) + "]"), 2 * n + 2
					* w + 2, 0);
			for (int c = 1; c <= n + m; c++) {
				matrix.setAt(new StringInformation(" "), 2 * n + 2 * w + 1, c);
				matrix.setAt(new StringInformation(" "), 2 * n + 2 * w + 2, c);
			}
		}
		vssnpc.ieEmptyNumbers();
		for (int v = 0; v < n; v++) {
			for (int c = 1; c <= n; c++) {
				matrix.setAt(new StringInformation("0"), 2 * v + 1, c);
				matrix.setAt(new StringInformation("0"), 2 * v + 2, c);
			}
			matrix.setAt(new StringInformation("1"), 2 * v + 1, v + 1);
			matrix.setAt(new StringInformation("1"), 2 * v + 2, v + 1);
			vssnpc.ieFirstVariableDigit(v);
			for (int w = 1; w <= m; w++) {
				if ((formula.elementAt(w - 1, 0).intValue() == 2 * v + 1)
						|| (formula.elementAt(w - 1, 1).intValue() == 2 * v + 1)
						|| (formula.elementAt(w - 1, 2).intValue() == 2 * v + 1)) {
					matrix.setAt(new StringInformation("1"), 2 * v + 1, n + w);
				} else {
					matrix.setAt(new StringInformation("0"), 2 * v + 1, n + w);
				}
				if ((formula.elementAt(w - 1, 0).intValue() == 2 * v + 2)
						|| (formula.elementAt(w - 1, 1).intValue() == 2 * v + 2)
						|| (formula.elementAt(w - 1, 2).intValue() == 2 * v + 2)) {
					matrix.setAt(new StringInformation("1"), 2 * v + 2, n + w);
				} else {
					matrix.setAt(new StringInformation("0"), 2 * v + 2, n + w);
				}
			}
			vssnpc.ieSecondVariableDigit(v);
		}
		for (int w = 0; w < m; w++) {
			for (int c = 1; c <= n + m; c++) {
				matrix.setAt(new StringInformation("0"), 2 * n + 2 * w + 1, c);
				matrix.setAt(new StringInformation("0"), 2 * n + 2 * w + 2, c);
			}
			matrix.setAt(new StringInformation("1"), 2 * n + 2 * w + 1, n + w
					+ 1);
			matrix.setAt(new StringInformation("1"), 2 * n + 2 * w + 2, n + w
					+ 1);
			vssnpc.ieClauseDigit(w);
		}
		for (int c = 1; c <= n; c++) {
			matrix.setAt(new StringInformation("1"), 2 * n + 2 * m + 1, c);
		}
		vssnpc.ieFirstSumDigit();
		for (int c = n + 1; c <= n + m; c++) {
			matrix.setAt(new StringInformation("3"), 2 * n + 2 * m + 1, c);
		}
		vssnpc.ieSecondSumDigit();
		vssnpc.ieMatrixCreated();
	}

	public void execute(String visFile) {
		vssnpc = new VisualSubsetSumNPCompleteness(visFile);
		if (readInput()) {
			createMatrix();
			vssnpc.ieSatAssignmentDefined();
			computeSubset();
			assignment = secondAssignment;
			vssnpc.ieUnsatAssignmentDefined();
			computeSubset();
			vssnpc.ieEnd();
			MessageUtility.algorithmTerminated(vssnpc
					.getResource("algorithmName"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vssnpc.getAlgorithmPath(), vssnpc
				.getResource("algorithmFileName"));
		formula = (Matrix) il.load("Matrix", vssnpc
				.getResource("selectFirstInputMessage"));
		if (formula == null || formula.width() != 3) {
			return false;
		}
		m = formula.height();
		n = 0;
		for (int r = 0; r < m; r++) {
			for (int c = 0; c < 3; c++) {
				if (c < 2 && formula.elementAt(r, c).intValue() <= 0) {
					return false;
				}
				if (n < formula.elementAt(r, c).intValue()) {
					n = formula.elementAt(r, c).intValue();
				}
			}
		}
		if (n % 2 == 0) {
			n = n / 2;
		} else {
			n = (n + 1) / 2;
		}
		numberRows = 1 + 2 * n + 2 * m + 1;
		numberColumns = 1 + n + m;
		assignment = (Array) il.load("Array", vssnpc
				.getResource("selectSecondInputMessage"));
		if (assignment.length() != n) {
			return false;
		}
		secondAssignment = (Array) il.load("Array", vssnpc
				.getResource("selectThirdInputMessage"));
		if (secondAssignment.length() != n) {
			return false;
		}
		return true;
	}
}
