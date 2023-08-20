package org.algoritmica.alvie.algorithms;

import java.awt.Color;
import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.GeometricObjectInformation;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.utility.GeometricFigureXMLDrawerUtility;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class ReversalSort {
	class VisualReversalSort extends VisualInnerClass {
		private GeometricFigure labels;

		private GeometricFigureXMLDrawerUtility labelsDrawer;

		private Matrix<IntInformation> downMatrix, invpiMatrix, piMatrix,
				upMatrix;

		private MatrixXMLDrawerUtility<IntInformation> downMatrixDrawer,
				invpiMatrixDrawer, piMatrixDrawer, upMatrixDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		Integer[] index, downIndex, invIndex, upIndex;

		String[] color, downColor, invColor, upColor;

		private boolean isPseudocodeVisible;

		public VisualReversalSort(String visFile) {
			super("reversalSort", visFile);
		}

		private void ieBreakpointExists(int bpp) {
			color[n + 2 + bpp] = getResource("breakpointColor");
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer
					.draw(index, color, getResource("ieBreakpointExists"));
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 2, 3, 4, 5, 6, 7, 8, 9,
						10 });
			piMatrixDrawer.endStep();
			color[n + 2 + bpp] = getResource("permutationSecondLineColor");
		}

		private void ieDownComputed() {
			labels
					.add(new GeometricObjectInformation(
							getResource("downName"),
							Integer
									.parseInt(getResource("downXMLDrawerOriginX")),
							Integer
									.parseInt(getResource("downXMLDrawerOriginY")) - 35));
			for (int i = 0; i < n + 2; i++) {
				downMatrix.setAt(down.elementAt(i), 1, i);
			}
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer.draw(index, color, getResource("ieDownComputed"));
			invpiMatrixDrawer.draw(invIndex, invColor, "");
			downMatrixDrawer.draw(downIndex, downColor, "");
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 5 });
			piMatrixDrawer.endStep();
		}

		private void ieEnd() {
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer.draw(index, color, getResource("ieEnd"));
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 11 });
			piMatrixDrawer.endStep();
			piMatrixDrawer.endXMLFile();
		}

		private void ieInversePermutationComputed() {
			labels
					.add(new GeometricObjectInformation(
							getResource("inversePermutationName"),
							Integer
									.parseInt(getResource("inversePermutationXMLDrawerOriginX")),
							Integer
									.parseInt(getResource("inversePermutationXMLDrawerOriginY")) - 35));
			for (int i = 0; i < n + 2; i++) {
				invpiMatrix.setAt(invpi.elementAt(i), 1, i);
			}
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer.draw(index, color,
					getResource("ieInversePermutationComputed"));
			invpiMatrixDrawer.draw(invIndex, invColor, "");
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 2 });
			piMatrixDrawer.endStep();
		}

		private void ieNextArrayGenerated() {
			for (int i = 0; i < n + 2; i++) {
				piMatrix.setAt(pi.elementAt(i), 1, i);
			}
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer.draw(index, color,
					getResource("ieNextArrayGenerated"));
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1 });
			piMatrixDrawer.endStep();
		}

		private void ieOneReversalBreakpointFoundCaseA(int rho[], int shift) {
			color[n + 2 + rho[0] - 1] = getResource("breakpointColor");
			color[n + 2 + rho[1]] = getResource("breakpointColor");
			invColor[n + 2 + pi.elementAt(rho[0] - 1).intValue() + shift] = getResource("breakpointColor");
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer.draw(index, color,
					getResource("ieOneReversalBreakpointFoundCaseA"));
			invpiMatrixDrawer.draw(invIndex, invColor, "");
			downMatrixDrawer.draw(downIndex, downColor, "");
			upMatrixDrawer.draw(upIndex, upColor, "");
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 8 });
			piMatrixDrawer.endStep();
			for (int i = n + 2; i < 2 * (n + 2); i = i + 1) {
				color[i] = getResource("permutationSecondLineColor");
				invColor[i] = getResource("inversePermutationSecondLineColor");
				downColor[i] = getResource("downSecondLineColor");
				upColor[i] = getResource("upSecondLineColor");
			}
			labels.remove(3);
			labels.remove(2);
			labels.remove(1);
		}

		private void ieOneReversalBreakpointFoundCaseB(int rho[], int shift) {
			color[n + 2 + rho[0] - 1] = getResource("breakpointColor");
			color[n + 2 + rho[1] - 1] = getResource("breakpointColor");
			invColor[n + 2 + pi.elementAt(rho[0] - 1).intValue() + shift] = getResource("breakpointColor");
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer.draw(index, color,
					getResource("ieOneReversalBreakpointFoundCaseB"));
			invpiMatrixDrawer.draw(invIndex, invColor, "");
			downMatrixDrawer.draw(downIndex, downColor, "");
			upMatrixDrawer.draw(upIndex, upColor, "");
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 8 });
			piMatrixDrawer.endStep();
			for (int i = n + 2; i < 2 * (n + 2); i = i + 1) {
				color[i] = getResource("permutationSecondLineColor");
				invColor[i] = getResource("inversePermutationSecondLineColor");
				downColor[i] = getResource("downSecondLineColor");
				upColor[i] = getResource("upSecondLineColor");
			}
			labels.remove(3);
			labels.remove(2);
			labels.remove(1);
		}

		private void ieOneReversalFound(int rho[]) {
			for (int i = rho[0]; i <= rho[1]; i++) {
				color[n + 2 + i] = getResource("reversalColor");
			}
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer
					.draw(index, color, getResource("ieOneReversalFound"));
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 8 });
			piMatrixDrawer.endStep();
			for (int i = rho[0]; i <= rho[1]; i++) {
				color[n + 2 + i] = getResource("permutationSecondLineColor");
			}
		}

		private void ieOneReversalWithDecreasingStripBreakpointFoundCaseA(
				int rho[], int shift) {
			if (shift > 0) {
				if (down.elementAt(1).intValue() < rho[0] - 1) {
					downColor[n + 2 + 1] = getResource("breakpointColor");
					int i = down.elementAt(1).intValue();
					color[n + 2 + i] = getResource("decreasingStripColor");
					while (pi.elementAt(i).intValue() == pi.elementAt(i + 1)
							.intValue() + 1) {
						i = i + 1;
						color[n + 2 + i] = getResource("decreasingStripColor");
					}
				} else if (up.elementAt(rho[0]).intValue() < rho[1]) {
					upColor[n + 2 + rho[0]] = getResource("breakpointColor");
					int i = up.elementAt(rho[0]).intValue();
					color[n + 2 + i] = getResource("increasingStripColor");
					while (pi.elementAt(i).intValue() == pi.elementAt(i + 1)
							.intValue() - 1) {
						i = i + 1;
						color[n + 2 + i] = getResource("increasingStripColor");
					}
				} else if (down.elementAt(rho[1] + 1).intValue() <= n) {
					downColor[n + 2 + rho[1] + 1] = getResource("breakpointColor");
					int i = down.elementAt(rho[1] + 1).intValue();
					color[n + 2 + i] = getResource("decreasingStripColor");
					while (pi.elementAt(i).intValue() == pi.elementAt(i + 1)
							.intValue() + 1) {
						i = i + 1;
						color[n + 2 + i] = getResource("decreasingStripColor");
					}
				}
			}
			color[n + 2 + rho[0] - 1] = getResource("breakpointColor");
			color[n + 2 + rho[1]] = getResource("breakpointColor");
			invColor[n + 2 + pi.elementAt(rho[0] - 1).intValue() + shift] = getResource("breakpointColor");
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer
					.draw(
							index,
							color,
							getResource("ieOneReversalWithDecreasingStripBreakpointFoundCaseA"));
			invpiMatrixDrawer.draw(invIndex, invColor, "");
			downMatrixDrawer.draw(downIndex, downColor, "");
			upMatrixDrawer.draw(upIndex, upColor, "");
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6 });
			piMatrixDrawer.endStep();
			for (int i = n + 2; i < 2 * (n + 2); i = i + 1) {
				color[i] = getResource("permutationSecondLineColor");
				invColor[i] = getResource("inversePermutationSecondLineColor");
				downColor[i] = getResource("downSecondLineColor");
				upColor[i] = getResource("upSecondLineColor");
			}
			labels.remove(3);
			labels.remove(2);
			labels.remove(1);
		}

		private void ieOneReversalWithDecreasingStripBreakpointFoundCaseB(
				int rho[], int shift, boolean ij) {
			if (ij) {
				color[n + 2 + rho[0] - 1] = getResource("breakpointColor");
				color[n + 2 + rho[1] - 1] = getResource("breakpointColor");
				invColor[n + 2 + pi.elementAt(rho[0] - 1).intValue() + shift] = getResource("breakpointColor");
			} else {
				color[n + 2 + rho[0] - 2] = getResource("breakpointColor");
				color[n + 2 + rho[1]] = getResource("breakpointColor");
				invColor[n + 2 + pi.elementAt(rho[1]).intValue() + shift] = getResource("breakpointColor");
			}
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer
					.draw(
							index,
							color,
							getResource("ieOneReversalWithDecreasingStripBreakpointFoundCaseB"));
			invpiMatrixDrawer.draw(invIndex, invColor, "");
			downMatrixDrawer.draw(downIndex, downColor, "");
			upMatrixDrawer.draw(upIndex, upColor, "");
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6 });
			piMatrixDrawer.endStep();
			for (int i = n + 2; i < 2 * (n + 2); i = i + 1) {
				color[i] = getResource("permutationSecondLineColor");
				invColor[i] = getResource("inversePermutationSecondLineColor");
				downColor[i] = getResource("downSecondLineColor");
				upColor[i] = getResource("upSecondLineColor");
			}
			labels.remove(3);
			labels.remove(2);
			labels.remove(1);
		}

		private void ieOneReversalWithDecreasingStripFound(int rho[]) {
			for (int i = rho[0]; i <= rho[1]; i++) {
				color[n + 2 + i] = getResource("reversalColor");
			}
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer
					.draw(index, color, getResource("ieOneReversalWithDecreasingStripFound"));
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6 });
			piMatrixDrawer.endStep();
			for (int i = rho[0]; i <= rho[1]; i++) {
				color[n + 2 + i] = getResource("permutationSecondLineColor");
			}
		}

		private void ieStart() {
			piMatrixDrawer.startXMLFile(getResource("algorithmName"));
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer.draw(index, color, getResource("ieStart"));
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			piMatrixDrawer.endStep();
		}

		private void ieTwoReversalBreakpointsFound(int rho[], int shift) {
			color[n + 2 + rho[0] - 1] = getResource("breakpointColor");
			color[n + 2 + rho[1]] = getResource("breakpointColor");
			invColor[n + 2 + pi.elementAt(rho[0] - 1).intValue() + shift] = getResource("breakpointColor");
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer.draw(index, color,
					getResource("ieTwoReversalBreakpointsFound"));
			invpiMatrixDrawer.draw(invIndex, invColor, "");
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 3 });
			piMatrixDrawer.endStep();
			color[n + 2 + rho[0] - 1] = getResource("permutationSecondLineColor");
			color[n + 2 + rho[1]] = getResource("permutationSecondLineColor");
			invColor[n + 2 + pi.elementAt(rho[0] - 1).intValue() + shift] = getResource("inversePermutationSecondLineColor");
			labels.remove(1);
		}

		private void ieTwoReversalFound(int rho[]) {
			for (int i = rho[0]; i <= rho[1]; i++) {
				color[n + 2 + i] = getResource("reversalColor");
			}
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer
					.draw(index, color, getResource("ieTwoReversalFound"));
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 3 });
			piMatrixDrawer.endStep();
			for (int i = rho[0]; i <= rho[1]; i++) {
				color[n + 2 + i] = getResource("permutationSecondLineColor");
			}
		}

		private void ieUpComputed() {
			labels
			.add(new GeometricObjectInformation(
					getResource("upName"),
					Integer
							.parseInt(getResource("upXMLDrawerOriginX")),
					Integer
							.parseInt(getResource("upXMLDrawerOriginY")) - 35));
			for (int i = 0; i < n + 2; i++) {
				upMatrix.setAt(up.elementAt(i), 1, i);
			}
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer.draw(index, color, getResource("ieDownComputed"));
			invpiMatrixDrawer.draw(invIndex, invColor, "");
			downMatrixDrawer.draw(downIndex, downColor, "");
			upMatrixDrawer.draw(upIndex, upColor, "");
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 5 });
			piMatrixDrawer.endStep();
		}

		private void ieZeroReversalFound(int rho[]) {
			for (int i = rho[0]; i <= rho[1]; i++) {
				color[n + 2 + i] = getResource("reversalColor");
			}
			piMatrixDrawer.startStep(step++);
			piMatrixDrawer.draw(index, color,
					getResource("ieZeroReversalFound"));
			labelsDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 10 });
			piMatrixDrawer.endStep();
			for (int i = rho[0]; i <= rho[1]; i++) {
				color[n + 2 + i] = getResource("permutationSecondLineColor");
			}
		}

		private void init() {
			piMatrix = new Matrix<IntInformation>(2, n + 2,
					new IntInformation());
			for (int i = 0; i < n + 2; i++) {
				piMatrix.setAt(new IntInformation(i), 0, i);
				piMatrix.setAt(pi.elementAt(i), 1, i);
			}
			piMatrixDrawer = new MatrixXMLDrawerUtility<IntInformation>(
					piMatrix, getResource("permutationName"), getOutputStream());
			piMatrixDrawer.setOriginX(Integer
					.parseInt(getResource("permutationXMLDrawerOriginX")));
			piMatrixDrawer.setOriginY(Integer
					.parseInt(getResource("permutationXMLDrawerOriginY")));
			piMatrixDrawer.setDefaultFont(Font
					.decode(getResource("permutationFont")));
			invpiMatrix = new Matrix<IntInformation>(2, n + 2,
					new IntInformation());
			for (int i = 0; i < n + 2; i++) {
				invpiMatrix.setAt(new IntInformation(i), 0, i);
				invpiMatrix.setAt(invpi.elementAt(i), 1, i);
			}
			invpiMatrixDrawer = new MatrixXMLDrawerUtility<IntInformation>(
					invpiMatrix, getResource("inversePermutationName"),
					getOutputStream());
			invpiMatrixDrawer
					.setOriginX(Integer
							.parseInt(getResource("inversePermutationXMLDrawerOriginX")));
			invpiMatrixDrawer
					.setOriginY(Integer
							.parseInt(getResource("inversePermutationXMLDrawerOriginY")));
			invpiMatrixDrawer.setDefaultFont(Font
					.decode(getResource("inversePermutationFont")));
			downMatrix = new Matrix<IntInformation>(2, n + 2,
					new IntInformation());
			for (int i = 0; i < n + 2; i++) {
				downMatrix.setAt(new IntInformation(i), 0, i);
				downMatrix.setAt(down.elementAt(i), 1, i);
			}
			downMatrixDrawer = new MatrixXMLDrawerUtility<IntInformation>(
					downMatrix, getResource("downName"), getOutputStream());
			downMatrixDrawer.setOriginX(Integer
					.parseInt(getResource("downXMLDrawerOriginX")));
			downMatrixDrawer.setOriginY(Integer
					.parseInt(getResource("downXMLDrawerOriginY")));
			downMatrixDrawer.setDefaultFont(Font
					.decode(getResource("downFont")));
			upMatrix = new Matrix<IntInformation>(2, n + 2,
					new IntInformation());
			for (int i = 0; i < n + 2; i++) {
				upMatrix.setAt(new IntInformation(i), 0, i);
				upMatrix.setAt(up.elementAt(i), 1, i);
			}
			upMatrixDrawer = new MatrixXMLDrawerUtility<IntInformation>(
					upMatrix, getResource("upName"), getOutputStream());
			upMatrixDrawer.setOriginX(Integer
					.parseInt(getResource("upXMLDrawerOriginX")));
			upMatrixDrawer.setOriginY(Integer
					.parseInt(getResource("upXMLDrawerOriginY")));
			upMatrixDrawer.setDefaultFont(Font.decode(getResource("upFont")));
			labels = new GeometricFigure();
			labels
					.add(new GeometricObjectInformation(
							getResource("permutationName"),
							Integer
									.parseInt(getResource("permutationXMLDrawerOriginX")),
							Integer
									.parseInt(getResource("permutationXMLDrawerOriginY")) - 35));
			labelsDrawer = new GeometricFigureXMLDrawerUtility(labels,
					getResource("labelsTitle"), getOutputStream());
			labelsDrawer.setDefaultColor(new Color(Integer.parseInt(
					getResource("labelsColor"), 16)));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setUpDifferentialDrawArrays();
		}

		private void setUpDifferentialDrawArrays() {
			index = new Integer[2 * (n + 2)];
			color = new String[2 * (n + 2)];
			invIndex = new Integer[2 * (n + 2)];
			invColor = new String[2 * (n + 2)];
			downIndex = new Integer[2 * (n + 2)];
			downColor = new String[2 * (n + 2)];
			upIndex = new Integer[2 * (n + 2)];
			upColor = new String[2 * (n + 2)];
			for (int i = 0; i < n + 2; i = i + 1) {
				index[i] = i;
				color[i] = getResource("permutationFirstLineColor");
				invIndex[i] = i;
				invColor[i] = getResource("inversePermutationFirstLineColor");
				downIndex[i] = i;
				downColor[i] = getResource("downFirstLineColor");
				upIndex[i] = i;
				upColor[i] = getResource("upFirstLineColor");
			}
			for (int i = n + 2; i < 2 * (n + 2); i = i + 1) {
				index[i] = i;
				color[i] = getResource("permutationSecondLineColor");
				invIndex[i] = i;
				invColor[i] = getResource("inversePermutationSecondLineColor");
				downIndex[i] = i;
				downColor[i] = getResource("downSecondLineColor");
				upIndex[i] = i;
				upColor[i] = getResource("upSecondLineColor");
			}
		}

	}

	private ArrayI<IntInformation> down, invpi, pi, up;

	private int n;

	private VisualReversalSort vrs;

	private void applyReversal(int[] rho) {
		int i = rho[0];
		int j = rho[1];
		while (i < j) {
			IntInformation tmp = pi.elementAt(i);
			pi.setAt(pi.elementAt(j), i);
			pi.setAt(tmp, j);
			i = i + 1;
			j = j - 1;
		}
	}

	private int breakPointPosition(ArrayI<IntInformation> a) {
		for (int i = 0; i < n + 1; i++) {
			if (a.elementAt(i).intValue() != a.elementAt(i + 1).intValue() - 1
					&& a.elementAt(i).intValue() != a.elementAt(i + 1)
							.intValue() + 1) {
				// vrs.ieBreakpointPosition(i);
				return i;
			} else {
				// vrs.ieNoBreakpointPosition(i);
			}
		}
		return -1;
	}

	private void computeDown() {
		down.setAt(new IntInformation(n + 1), n + 1);
		for (int i = n; i > 0; i--) {
			if (Math.abs(pi.elementAt(i).intValue()
					- pi.elementAt(i - 1).intValue()) > 1
					&& Math.abs(pi.elementAt(i).intValue()
							- pi.elementAt(i + 1).intValue()) > 1) {
				down.setAt(new IntInformation(i), i);
			} else if (Math.abs(pi.elementAt(i).intValue()
					- pi.elementAt(i - 1).intValue()) > 1
					&& Math.abs(pi.elementAt(i).intValue()
							- pi.elementAt(i + 1).intValue()) == 1
					&& pi.elementAt(i).isGreaterThan(pi.elementAt(i + 1))) {
				down.setAt(new IntInformation(i), i);
			} else {
				down
						.setAt(new IntInformation(down.elementAt(i + 1)
								.intValue()), i);
			}
		}
		down.setAt(new IntInformation(down.elementAt(1).intValue()), 0);
	}

	private void computeInversePermutation() {
		for (int i = 0; i < n + 2; i++) {
			invpi.setAt(new IntInformation(i), pi.elementAt(i).intValue());
		}
	}

	private void computeUp() {
		up.setAt(new IntInformation(n + 1), n + 1);
		for (int i = n; i > 0; i--) {
			if (Math.abs(pi.elementAt(i).intValue()
					- pi.elementAt(i - 1).intValue()) > 1
					&& Math.abs(pi.elementAt(i).intValue()
							- pi.elementAt(i + 1).intValue()) > 1) {
				up.setAt(new IntInformation(up.elementAt(i + 1).intValue()), i);
			} else if (Math.abs(pi.elementAt(i).intValue()
					- pi.elementAt(i - 1).intValue()) > 1
					&& Math.abs(pi.elementAt(i).intValue()
							- pi.elementAt(i + 1).intValue()) == 1
					&& pi.elementAt(i).isLessThan(pi.elementAt(i + 1))) {
				up.setAt(new IntInformation(i), i);
			} else {
				up.setAt(new IntInformation(up.elementAt(i + 1).intValue()), i);
			}
		}
		up.setAt(new IntInformation(0), 0);
	}

	public void execute(String visFile) {
		vrs = new VisualReversalSort(visFile);
		if (readInput()) {
			vrs.init();
			vrs.ieStart();
			findSolution();
			vrs.ieEnd();
			MessageUtility
					.algorithmTerminated(vrs.getResource("algorithmName"));
		}
	}

	private void findSolution() {
		int bpp = breakPointPosition(pi);
		while (bpp >= 0) {
			vrs.ieBreakpointExists(bpp);
			computeInversePermutation();
			vrs.ieInversePermutationComputed();
			int[] rho = twoReversal();
			if (rho != null) {
				vrs.ieTwoReversalFound(rho);
				applyReversal(rho);
			} else {
				computeDown();
				vrs.ieDownComputed();
				computeUp();
				vrs.ieUpComputed();
				rho = oneReversalWithDecreasingStrip();
				if (rho != null) {
					vrs.ieOneReversalWithDecreasingStripFound(rho);
					applyReversal(rho);
				} else {
					rho = oneReversal();
					if (rho != null) {
						vrs.ieOneReversalFound(rho);
						applyReversal(rho);
					} else {
						int i = leastOutOfPlace();
						rho = new int[2];
						rho[0] = i;
						rho[1] = invpi.elementAt(i).intValue();
						vrs.ieZeroReversalFound(rho);
						applyReversal(rho);
					}
				}
			}
			vrs.ieNextArrayGenerated();
			bpp = breakPointPosition(pi);
		}
	}

	private int leastOutOfPlace() {
		int i = 1;
		while (pi.elementAt(i).intValue() == i) {
			i = i + 1;
		}
		return i;
	}

	private int[] oneReversal() {
		int[] rho = new int[2];
		for (int i = 0; i < n + 1; i++) {
			if (Math.abs(pi.elementAt(i).intValue()
					- pi.elementAt(i + 1).intValue()) > 1) {
				int j = invpi.elementAt(pi.elementAt(i).intValue() + 1)
						.intValue();
				if (j > i && j < n + 1) {
					if (Math.abs(pi.elementAt(j).intValue()
							- pi.elementAt(j + 1).intValue()) > 1) {
						rho[0] = i + 1;
						rho[1] = j;
						vrs.ieOneReversalBreakpointFoundCaseA(rho, 1);
						return rho;
					} else if (pi.elementAt(i + 1).intValue() == pi.elementAt(
							j + 1).intValue() + 1) {
						rho[0] = i + 1;
						rho[1] = j;
						vrs.ieOneReversalBreakpointFoundCaseB(rho, 1);
						return rho;
					}
				}
				if (i > 0) {
					j = invpi.elementAt(pi.elementAt(i).intValue() - 1)
							.intValue();
					if (j > i) {
						if (Math.abs(pi.elementAt(j).intValue()
								- pi.elementAt(j + 1).intValue()) > 1) {
							rho[0] = i + 1;
							rho[1] = j;
							vrs.ieOneReversalBreakpointFoundCaseA(rho, -1);
							return rho;
						} else if (pi.elementAt(i + 1).intValue() == pi
								.elementAt(j + 1).intValue() - 1) {
							rho[0] = i + 1;
							rho[1] = j;
							vrs.ieOneReversalBreakpointFoundCaseB(rho, -1);
							return rho;
						}
					}
				}
			}
		}
		return null;
	}

	private int[] oneReversalWithDecreasingStrip() {
		int[] rho = new int[2];
		for (int i = 0; i < n + 1; i++) {
			if (Math.abs(pi.elementAt(i).intValue()
					- pi.elementAt(i + 1).intValue()) > 1) {
				int j = invpi.elementAt(pi.elementAt(i).intValue() + 1)
						.intValue();
				if (j > i && j < n + 1) {
					if (Math.abs(pi.elementAt(j).intValue()
							- pi.elementAt(j + 1).intValue()) > 1) {
						if (down.elementAt(1).intValue() < i
								|| up.elementAt(i).intValue() < j
								|| down.elementAt(j + 1).intValue() <= n) {
							rho[0] = i + 1;
							rho[1] = j;
							vrs
									.ieOneReversalWithDecreasingStripBreakpointFoundCaseA(
											rho, 1);
							return rho;
						}
					} else if (pi.elementAt(i + 1).intValue() == pi.elementAt(
							j + 1).intValue() + 1) {
						rho[0] = i + 1;
						rho[1] = j;
						vrs
								.ieOneReversalWithDecreasingStripBreakpointFoundCaseB(
										rho, 1, true);
						return rho;
					}
				} else if (j < i) {
					if (pi.elementAt(j + 1).intValue() == pi.elementAt(j)
							.intValue() + 1
							&& pi.elementAt(i + 1).intValue() == pi.elementAt(
									j + 1).intValue() + 1) {
						rho[0] = j + 1;
						rho[1] = i;
						vrs
								.ieOneReversalWithDecreasingStripBreakpointFoundCaseB(
										rho, 1, false);
						return rho;
					}
				}
				if (i > 0) {
					j = invpi.elementAt(pi.elementAt(i).intValue() - 1)
							.intValue();
					if (j > i) {
						if (Math.abs(pi.elementAt(j).intValue()
								- pi.elementAt(j + 1).intValue()) > 1) {
							rho[0] = i + 1;
							rho[1] = j;
							vrs
									.ieOneReversalWithDecreasingStripBreakpointFoundCaseA(
											rho, -1);
							return rho;
						} else if (pi.elementAt(i + 1).intValue() == pi
								.elementAt(j + 1).intValue() - 1) {
							rho[0] = i + 1;
							rho[1] = j;
							vrs
									.ieOneReversalWithDecreasingStripBreakpointFoundCaseB(
											rho, -1, true);
							return rho;
						}
					} else if (j < i) {
						if (pi.elementAt(j).intValue() == pi.elementAt(j + 1)
								.intValue() + 1
								&& pi.elementAt(j + 1).intValue() == pi
										.elementAt(i + 1).intValue() + 1) {
							rho[0] = j + 1;
							rho[1] = i;
							vrs
									.ieOneReversalWithDecreasingStripBreakpointFoundCaseB(
											rho, -1, false);
							return rho;
						}
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private boolean readInput() {
		InputLoader il = new InputLoader(vrs.getAlgorithmPath(), vrs
				.getResource("algorithmFileName"));
		pi = (Array<IntInformation>) il.load("Array", vrs
				.getResource("selectInputMessage"));
		if (pi == null || pi.length() < 3) {
			return false;
		}
		n = pi.length() - 2;
		if (pi.elementAt(0).intValue() != 0
				|| pi.elementAt(n + 1).intValue() != n + 1) {
			return false;
		}
		boolean[] present = new boolean[n];
		for (int i = 1; i <= n; i++) {
			if (pi.elementAt(i).intValue() > 0
					&& pi.elementAt(i).intValue() <= n) {
				present[pi.elementAt(i).intValue() - 1] = true;
			}
		}
		for (int i = 0; i < n; i++) {
			if (!present[i]) {
				return false;
			}
		}
		invpi = new Array<IntInformation>(pi.length(), new IntInformation());
		down = new Array<IntInformation>(pi.length(), new IntInformation());
		up = new Array<IntInformation>(pi.length(), new IntInformation());
		return true;
	}

	private int[] twoReversal() {
		int[] rho = new int[2];
		for (int i = 0; i < n + 1; i++) {
			if (Math.abs(pi.elementAt(i).intValue()
					- pi.elementAt(i + 1).intValue()) > 1) {
				int j = invpi.elementAt(pi.elementAt(i).intValue() + 1)
						.intValue();
				if (j > i
						&& j < n + 1
						&& Math.abs(pi.elementAt(j).intValue()
								- pi.elementAt(j + 1).intValue()) > 1
						&& Math.abs(pi.elementAt(j).intValue()
								- pi.elementAt(i).intValue()) == 1
						&& Math.abs(pi.elementAt(j + 1).intValue()
								- pi.elementAt(i + 1).intValue()) == 1) {
					rho[0] = i + 1;
					rho[1] = j;
					vrs.ieTwoReversalBreakpointsFound(rho, +1);
					return rho;
				}
				if (i > 0) {
					j = invpi.elementAt(pi.elementAt(i).intValue() - 1)
							.intValue();
					if (j > i
							&& Math.abs(pi.elementAt(j).intValue()
									- pi.elementAt(j + 1).intValue()) > 1
							&& Math.abs(pi.elementAt(j).intValue()
									- pi.elementAt(i).intValue()) == 1
							&& Math.abs(pi.elementAt(j + 1).intValue()
									- pi.elementAt(i + 1).intValue()) == 1) {
						rho[0] = i + 1;
						rho[1] = j;
						vrs.ieTwoReversalBreakpointsFound(rho, -1);
						return rho;
					}
				}
			}
		}
		return null;
	}

}
