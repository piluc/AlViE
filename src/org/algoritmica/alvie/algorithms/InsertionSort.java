package org.algoritmica.alvie.algorithms;

import java.awt.Color;
import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.SortableI;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class InsertionSort<I extends SortableI<I>> {
	private Array<I> a;

	private int i, j, n;

	private I prossimo;

	@SuppressWarnings("unchecked")
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vis.getAlgorithmPath(), vis
				.getResource("algorithmFileName"));
		a = (Array<I>) inputLoader.load("Array", vis
				.getResource("selectInputMessage"));
		if (a == null) {
			return false;
		}
		n = a.length();
		return true;
	}

	public void insertionSort() {
		vis.init();
		vis.ieStart();
		for (i = 0; i < n; i = i + 1) {
			prossimo = a.elementAt(i);
			vis.ieNext();
			j = i;
			while ((j > 0) && (a.elementAt(j - 1).isGreaterThan(prossimo))) {
				vis.ieBeforeShift();
				a.setAt(a.elementAt(j - 1), j);
				j = j - 1;
				vis.ieAfterShift();
			}
			vis.ieBeforeLastAssignment();
			a.setAt(prossimo, j);
			vis.ieInsertionFinished();
		}
		vis.ieEnd();
	}

	public void execute(String visFile) {
		vis = new VisualInsertionSort(visFile);
		if (readInput()) {
			insertionSort();
			MessageUtility
					.algorithmTerminated(vis.getResource("algorithmName"));
		}
	}

	private VisualInsertionSort vis;

	private class VisualInsertionSort extends VisualInnerClass {
		private ArrayXMLDrawerUtility<I> aDrawer;

		private Integer[] aIndex;

		private String[] aColor;

		private Array<StringInformation> next;

		private ArrayXMLDrawerUtility<StringInformation> nextDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawerUtility;

		VisualInsertionSort(String visFile) {
			super("insertionSort", visFile);
		}

		void init() {
			aDrawer = new ArrayXMLDrawerUtility<I>(a, getResource("aName"),
					getOutputStream());
			aDrawer.setOriginX(Integer
					.parseInt(getResource("aXMLDrawerOriginX")));
			aDrawer.setOriginY(Integer
					.parseInt(getResource("aXMLDrawerOriginY")));
			aDrawer.setDefaultFont(Font.decode(getResource("aFont")));
			next = new Array<StringInformation>(1, new StringInformation());
			nextDrawer = new ArrayXMLDrawerUtility<StringInformation>(next,
					getResource("nextName"), getOutputStream());
			nextDrawer.setOriginX(Integer
					.parseInt(getResource("nextXMLDrawerOriginX")));
			nextDrawer.setOriginY(Integer
					.parseInt(getResource("nextXMLDrawerOriginY")));
			nextDrawer.setDefaultFont(Font.decode(getResource("nextFont")));
			nextDrawer.setDefaultColor(new Color(Integer.parseInt(
					getResource("nextColor"), 16)));
			pseudocodeDrawerUtility = new PseudocodeXMLDrawerUtility(this);
			initDifferentialDrawArrays();
			step = 0;
		}

		private void ieStart() {
			aDrawer.startXMLFile(getResource("algorithmName"));
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieStart"));
			pseudocodeDrawerUtility.draw("", new int[] { 0 });
			aDrawer.endStep();
		}

		private void ieNext() {
			next.setAt(new StringInformation(prossimo.stringValue()), 0);
			aColor[i] = getResource("currentElementColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieNext"));
			pseudocodeDrawerUtility.draw("", new int[] { 2 });
			aDrawer.endStep();
		}

		private void ieBeforeShift() {
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieBeforeShift"));
			nextDrawer.draw("");
			pseudocodeDrawerUtility.draw("", new int[] { 5, 6 });
			aDrawer.endStep();
		}

		private void ieAfterShift() {
			aColor[j] = getResource("currentElementColor");
			aColor[j + 1] = getResource("insertedElementColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieAfterShift"));
			nextDrawer.draw("");
			pseudocodeDrawerUtility.draw("", new int[] { 4, 7 });
			aDrawer.endStep();
		}

		private void ieBeforeLastAssignment() {
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieBeforeLastAssignment"));
			nextDrawer.draw("");
			pseudocodeDrawerUtility.draw("", new int[] { 8 });
			aDrawer.endStep();
		}

		private void ieInsertionFinished() {
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieInsertionFinished"));
			pseudocodeDrawerUtility.draw("", new int[] { 1, 9 });
			aDrawer.endStep();
			aColor[j] = getResource("insertedElementColor");
		}

		private void ieEnd() {
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieEnd"));
			aDrawer.endStep();
			aDrawer.endXMLFile();
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
}
