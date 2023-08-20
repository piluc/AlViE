package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.SortableI;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class SelectionSort<I extends SortableI<I>> {
	private class VisualSelectionSort extends VisualInnerClass {
		private ArrayXMLDrawerUtility<I> aDrawer;

		private Integer[] aIndex;

		private String[] aColor;

		private PseudocodeXMLDrawerUtility pseudocodeDrawerUtility;

		private int tmp;

		VisualSelectionSort(String visFile) {
			super("selectionSort", visFile);
		}

		private void ieEnd() {
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieEnd"));
			aDrawer.endStep();
			aDrawer.endXMLFile();
		}

		private void ieSelectionChanged(int i) {
			if (tmp >= 0) {
				aColor[tmp] = getResource("aColor");
			}
			aColor[i] = getResource("currentMinimumColor");
			tmp = i;
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieSelectionChanged"));
			pseudocodeDrawerUtility.draw("", new int[] { 6, 7 });
			aDrawer.endStep();
		}

		private void ieSelectionStarted(int i) {
			if (tmp >= 0) {
				aColor[tmp] = getResource("aColor");
			}
			aColor[i] = getResource("currentMinimumColor");
			tmp = i;
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieSelectionStarted"));
			pseudocodeDrawerUtility.draw("", new int[] { 2, 3 });
			aDrawer.endStep();
		}

		private void ieStart() {
			aDrawer.startXMLFile(getResource("algorithmName"));
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieStart"));
			pseudocodeDrawerUtility.draw("", null);
			aDrawer.endStep();
		}

		private void ieSwapDone(int i) {
			aColor[tmp] = getResource("aColor");
			aColor[i] = getResource("selectedElementColor");
			tmp = -1;
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieSwapDone"));
			pseudocodeDrawerUtility.draw("", new int[] { 1, 12 });
			aDrawer.endStep();
		}

		private void ieSwapToBeDone() {
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieSwapToBeDone"));
			pseudocodeDrawerUtility.draw("", new int[] { 10, 11 });
			aDrawer.endStep();
		}

		void init() {
			aDrawer = new ArrayXMLDrawerUtility<I>(a, getResource("aName"),
					getOutputStream());
			aDrawer.setOriginX(Integer
					.parseInt(getResource("aXMLDrawerOriginX")));
			aDrawer.setOriginY(Integer
					.parseInt(getResource("aXMLDrawerOriginY")));
			aDrawer.setDefaultFont(Font.decode(getResource("aFont")));
			pseudocodeDrawerUtility = new PseudocodeXMLDrawerUtility(this);
			initDifferentialDrawArrays();
			step = 0;
		}

		private void initDifferentialDrawArrays() {
			aIndex = new Integer[a.length()];
			aColor = new String[a.length()];
			for (int i = 0; i < a.length(); i++) {
				aIndex[i] = i;
				aColor[i] = getResource("aColor");
			}
		}
	}

	Array<I> a;

	private int i, indiceMinimo, j, n;

	private I minimo;

	VisualSelectionSort vss;

	public void execute(String visFile) {
		vss = new VisualSelectionSort(visFile);
		if (readInput()) {
			selectionSort();
			MessageUtility
					.algorithmTerminated(vss.getResource("algorithmName"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vss.getAlgorithmPath(), vss
				.getResource("algorithmFileName"));
		a = (Array) inputLoader.load("Array", vss
				.getResource("selectInputMessage"));
		if (a == null) {
			return false;
		}
		n = a.length();
		return true;
	}

	private void selectionSort() {
		vss.init();
		vss.ieStart();
		for (i = 0; i < n; i++) {
			minimo = a.elementAt(i);
			indiceMinimo = i;
			vss.ieSelectionStarted(i);
			for (j = i + 1; j < n; j++) {
				if (a.elementAt(j).isLessThan(minimo)) {
					minimo = a.elementAt(j);
					indiceMinimo = j;
					vss.ieSelectionChanged(j);
				}
			}
			vss.ieSwapToBeDone();
			a.setAt(a.elementAt(i), indiceMinimo);
			a.setAt(minimo, i);
			vss.ieSwapDone(i);
		}
		vss.ieEnd();
	}
}
