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

public class CoinChange {
	private class VisualCoinChange extends VisualInnerClass {
		private Matrix<StringInformation> stringDPTable;

		private MatrixXMLDrawerUtility<StringInformation> dpTableDrawer;

		private Matrix<StringInformation> stringDenomination;

		private MatrixXMLDrawerUtility<StringInformation> denominationDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] denominationIndex, dpTableIndex;

		private String[] denominationColor, dpTableColor;

		private VisualCoinChange(String visFileName) {
			super("coinChange", visFileName);
		}

		private void ieCheckWhetherCurrentSolutionCanBeImproved(int m, int i) {
			dpTableColor[M + 1 + m - denomination.elementAt(i).intValue()] = getResource("previousSolutionColor");
			denominationColor[d + i] = getResource("usedDenominationColor");
			dpTableDrawer.startStep(step++);
			dpTableDrawer.draw(dpTableIndex, dpTableColor,
					getResource("ieCheckWhetherCurrentSolutionCanBeImproved"));
			denominationDrawer.draw(denominationIndex, denominationColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 6 });
			}
			dpTableDrawer.endStep();
		}

		private void ieCurrentSolutionComputationEnded(int m) {
			dpTableColor[M + 1 + m] = getResource("newSolutionColor");
			dpTableDrawer.startStep(step++);
			dpTableDrawer.draw(dpTableIndex, dpTableColor,
					getResource("ieCurrentSolutionComputationEnded"));
			denominationDrawer.draw(denominationIndex, denominationColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 2 });
			}
			dpTableDrawer.endStep();
			dpTableColor[M + 1 + m] = getResource("dpTableSecondLineColor");
		}

		private void ieNewSolutionComputationStarted(int s) {
			stringDPTable.setAt(new StringInformation("oo"), 1, s);
			dpTableColor[M + 1 + s] = getResource("currentSolutionColor");
			dpTableDrawer.startStep(step++);
			dpTableDrawer.draw(dpTableIndex, dpTableColor,
					getResource("ieNewSolutionComputationStarted"));
			denominationDrawer.draw(denominationIndex, denominationColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 3 });
			}
			dpTableDrawer.endStep();
		}

		private void ieEnd() {
			dpTableColor[2*M + 1] = getResource("newSolutionColor");
			dpTableDrawer.startStep(step++);
			dpTableDrawer
					.draw(dpTableIndex, dpTableColor, getResource("ieEnd"));
			denominationDrawer.draw(denominationIndex, denominationColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 8 });
			}
			dpTableDrawer.endStep();
			dpTableDrawer.endXMLFile();
		}

		private void ieFirstSolutionComputed() {
			stringDPTable.setAt(new StringInformation("0"), 1, 0);
			dpTableColor[M + 1] = getResource("foundSolutionColor");
			dpTableDrawer.startStep(step++);
			dpTableDrawer.draw(dpTableIndex, dpTableColor,
					getResource("ieFirstSolutionComputed"));
			denominationDrawer.draw(denominationIndex, denominationColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 1 });
			}
			dpTableDrawer.endStep();
			dpTableColor[M + 1] = getResource("dpTableSecondLineColor");
		}

		private void ieCurrentSolutionCanBeImproved(int m, int i) {
			dpTableDrawer.startStep(step++);
			dpTableDrawer.draw(dpTableIndex, dpTableColor,
					getResource("ieCurrentSolutionCanBeImproved"));
			denominationDrawer.draw(denominationIndex, denominationColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 7 });
			}
			dpTableDrawer.endStep();
		}

		private void ieCurrentSolutionCannotBeImproved(int m, int i) {
			dpTableDrawer.startStep(step++);
			dpTableDrawer.draw(dpTableIndex, dpTableColor,
					getResource("ieCurrentSolutionCannotBeImproved"));
			denominationDrawer.draw(denominationIndex, denominationColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 6 });
			}
			dpTableDrawer.endStep();
			dpTableColor[M + 1 + m - denomination.elementAt(i).intValue()] = getResource("dpTableSecondLineColor");
			denominationColor[d + i] = getResource("denominationSecondLineColor");
		}

		private void ieCurrentSolutionUpdated(int m, int i) {
			stringDPTable.setAt(new StringInformation(dpTable.elementAt(m)
					.stringValue()), 1, m);
			dpTableDrawer.startStep(step++);
			dpTableDrawer.draw(dpTableIndex, dpTableColor,
					getResource("ieCurrentSolutionUpdated"));
			denominationDrawer.draw(denominationIndex, denominationColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 7 });
			}
			dpTableDrawer.endStep();
			dpTableColor[M + 1 + m - denomination.elementAt(i).intValue()] = getResource("dpTableSecondLineColor");
			denominationColor[d + i] = getResource("denominationSecondLineColor");
		}

		private void ieStart() {
			dpTableDrawer.startXMLFile(getResource("algorithmName"));
			dpTableDrawer.startStep(step++);
			dpTableDrawer.draw(dpTableIndex, dpTableColor,
					getResource("ieStart"));
			denominationDrawer.draw(denominationIndex, denominationColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", null);
			}
			dpTableDrawer.endStep();
		}

		private void init() {
			stringDPTable = new Matrix<StringInformation>(2, M + 1,
					new StringInformation());
			for (int i = 0; i <= M; i++) {
				stringDPTable.setAt(new StringInformation("" + i), 0, i);
				stringDPTable.setAt(new StringInformation(" "), 1, i);
			}
			dpTableDrawer = new MatrixXMLDrawerUtility<StringInformation>(
					stringDPTable, getResource("dpTableTitle"),
					getOutputStream());
			dpTableDrawer.setOriginX(Integer
					.parseInt(getResource("dpTableXMLDrawerOriginX")));
			dpTableDrawer.setOriginY(Integer
					.parseInt(getResource("dpTableXMLDrawerOriginY")));
			dpTableDrawer.setDefaultFont(Font
					.decode(getResource("dpTableFont")));
			stringDenomination = new Matrix<StringInformation>(2, d,
					new StringInformation());
			for (int i = 0; i < d; i++) {
				stringDenomination.setAt(new StringInformation("c_" + i), 0, i);
				stringDenomination.setAt(new StringInformation(denomination
						.elementAt(i).stringValue()), 1, i);
			}
			denominationDrawer = new MatrixXMLDrawerUtility<StringInformation>(
					stringDenomination, getResource("denominationTitle"),
					getOutputStream());
			denominationDrawer.setOriginX(Integer
					.parseInt(getResource("denominationXMLDrawerOriginX")));
			denominationDrawer.setOriginY(Integer
					.parseInt(getResource("denominationXMLDrawerOriginY")));
			denominationDrawer.setDefaultFont(Font
					.decode(getResource("denominationFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setArraysDifferentialDrawArrays();
			step = 0;
		}

		private void setArraysDifferentialDrawArrays() {
			dpTableIndex = new Integer[2 * (M + 1)];
			dpTableColor = new String[2 * (M + 1)];
			for (int i = 0; i < M + 1; i++) {
				dpTableIndex[i] = i;
				dpTableColor[i] = getResource("dpTableFirstLineColor");
			}
			for (int i = M + 1; i < 2 * (M + 1); i++) {
				dpTableIndex[i] = i;
				dpTableColor[i] = getResource("dpTableSecondLineColor");
			}
			denominationIndex = new Integer[2 * d];
			denominationColor = new String[2 * d];
			for (int i = 0; i < d; i++) {
				denominationIndex[i] = i;
				denominationColor[i] = getResource("denominationFirstLineColor");
			}
			for (int i = d; i < 2 * d; i++) {
				denominationIndex[i] = i;
				denominationColor[i] = getResource("denominationSecondLineColor");
			}
		}

	}

	private int d, M;

	private Array<IntInformation> denomination, dpTable;

	private VisualCoinChange vcg;

	public void execute(String visFileName) {
		vcg = new VisualCoinChange(visFileName);
		if (readInput()) {
			dpChange();
			MessageUtility
					.algorithmTerminated(vcg.getResource("algorithmName"));
		}
	}

	private IntInformation dpChange() {
		dpTable = new Array<IntInformation>(M + 1, new IntInformation());
		for (int i = 0; i <= M; i++) {
			dpTable.setAt(new IntInformation(-1), i);
		}
		vcg.init();
		vcg.ieStart();
		dpTable.setAt(new IntInformation(0), 0);
		vcg.ieFirstSolutionComputed();
		for (int m = 1; m <= M; m++) {
			vcg.ieNewSolutionComputationStarted(m);
			dpTable.setAt(new IntInformation(M + 1), m);
			for (int i = 0; i < d; i++) {
				if (m >= denomination.elementAt(i).intValue()) {
					vcg.ieCheckWhetherCurrentSolutionCanBeImproved(m, i);
					if (dpTable.elementAt(
							m - denomination.elementAt(i).intValue())
							.intValue() + 1 < dpTable.elementAt(m).intValue()) {
						vcg.ieCurrentSolutionCanBeImproved(m, i);
						dpTable.setAt(
								new IntInformation(
										dpTable.elementAt(
												m
														- denomination
																.elementAt(i)
																.intValue())
												.intValue() + 1), m);
						vcg.ieCurrentSolutionUpdated(m, i);
					} else {
						vcg.ieCurrentSolutionCannotBeImproved(m, i);
					}
				}
			}
			vcg.ieCurrentSolutionComputationEnded(m);
		}
		vcg.ieEnd();
		return dpTable.elementAt(M);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader algorithmLoader = new InputLoader(vcg.getAlgorithmPath(),
				vcg.getResource("algorithmFileName"));
		Array<IntInformation> input = (Array) algorithmLoader.load("Array",
				vcg.getResource("selectInputMessage"));
		if (input == null || input.length() < 2) {
			return false;
		}
		if (input.elementAt(0).intValue() <= 0) {
			return false;
		}
		M = input.elementAt(0).intValue();
		d = input.length() - 1;
		denomination = new Array<IntInformation>(d, new IntInformation());
		for (int i = 0; i < d; i++) {
			if (input.elementAt(i + 1).intValue() <= 0) {
				return false;
			}
			denomination.setAt(input.elementAt(i + 1), i);
		}
		return true;
	}
}
