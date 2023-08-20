package org.algoritmica.alvie.algorithms;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.GeometricObjectInformation;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.GeometricFigureXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class FirstFitDecreasing {
	private class Bin {
		private Vector<Integer> items;
		private int size;

		private Bin() {
			items = new Vector<Integer>();
			size = 0;
		}

		private void add(int item) {
			items.add(item);
			size = size + item;
		}
	}

	private class VisualFirstFitDecreasing extends VisualInnerClass {
		private ArrayXMLDrawerUtility<IntInformation> aDrawer;

		private GeometricFigure binSet;

		private GeometricFigureXMLDrawerUtility binSetDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawerUtility;

		private Integer[] aIndex, binIndex;

		private String[] aColor, binColor;

		private boolean isPseudocodeVisible;

		private int binWidth, binScale, offset, interBinSpace;

		VisualFirstFitDecreasing(String visFile) {
			super("firstFitDecreasing", visFile);
		}

		private void ieEnd() {
			aDrawer.startStep(step++);
			binSetDrawer.draw(binIndex, binColor, getResource("ieEnd"));
			aDrawer.endStep();
			aDrawer.endXMLFile();
		}

		private void ieItemAdded(int i, int j) {
			setBinDifferentialDrawArrays(i);
			int p = solution.size() - 1;
			for (int b = 0; b <= j; b++) {
				p = p + solution.elementAt(b).items.size();
			}
			binSet.add(new GeometricObjectInformation(
					GeometricObjectInformation.RECTANGLE, offset + j
							* (binWidth + interBinSpace), binScale
							* (solution.elementAt(j).size - a.elementAt(i)
									.intValue()), binWidth, a.elementAt(i)
							.intValue()
							* binScale), p);
			binSet.add(new GeometricObjectInformation(""
					+ a.elementAt(i).intValue(), offset + j
					* (binWidth + interBinSpace) + binWidth / 4,
					binScale
							* (solution.elementAt(j).size - a.elementAt(i)
									.intValue() / 2)));
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieItemAdded"));
			if (isPseudocodeVisible) {
				pseudocodeDrawerUtility.draw("", new int[] { 3 });
			}
			binSetDrawer.draw(binIndex, binColor, "");
			aDrawer.endStep();
			aColor[i] = getResource("assignedItemColor");
		}

		private void ieItemAndBinAdded(int i, int j) {
			setBinDifferentialDrawArrays(i);
			binSet.add(new GeometricObjectInformation(
					GeometricObjectInformation.RECTANGLE, offset + j
							* (binWidth + interBinSpace), 0, binWidth, a
							.elementAt(0).intValue()
							* binScale), j);
			int p = solution.size() - 1;
			for (int b = 0; b <= j; b++) {
				p = p + solution.elementAt(b).items.size();
			}
			binSet.add(new GeometricObjectInformation(
					GeometricObjectInformation.RECTANGLE, offset + j
							* (binWidth + interBinSpace), binScale
							* (solution.elementAt(j).size - a.elementAt(i)
									.intValue()), binWidth, a.elementAt(i)
							.intValue()
							* binScale), p);
			binSet.add(new GeometricObjectInformation(""
					+ a.elementAt(i).intValue(), offset + j
					* (binWidth + interBinSpace) + binWidth / 4,
					binScale
							* (solution.elementAt(j).size - a.elementAt(i)
									.intValue() / 2)));
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieItemAndBinAdded"));
			if (isPseudocodeVisible) {
				pseudocodeDrawerUtility.draw("", new int[] { 4 });
			}
			binSetDrawer.draw(binIndex, binColor, "");
			aDrawer.endStep();
			aColor[i] = getResource("assignedItemColor");
		}

		private void ieItemsSorted() {
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieItemsSorted"));
			if (isPseudocodeVisible) {
				pseudocodeDrawerUtility.draw("", new int[] { 1 });
			}
			binSetDrawer.draw("");
			aDrawer.endStep();
		}

		private void ieNewItemAnalyzed(int i) {
			aColor[i] = getResource("currentItemColor");
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieNewItemAnalyzed"));
			if (isPseudocodeVisible) {
				pseudocodeDrawerUtility.draw("", new int[] { 2 });
			}
			if (solution.size() > 0) {
				binSetDrawer.draw(binIndex, binColor, "");
			}
			aDrawer.endStep();
		}

		private void ieStart() {
			aDrawer.startXMLFile(getResource("algorithmName"));
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieStart"));
			if (isPseudocodeVisible) {
				pseudocodeDrawerUtility.draw("", null);
			}
			binSetDrawer.draw("");
			aDrawer.endStep();
		}

		private void init() {
			aDrawer = new ArrayXMLDrawerUtility<IntInformation>(a,
					getResource("aTitle"), getOutputStream());
			aDrawer.setOriginX(Integer
					.parseInt(getResource("aXMLDrawerOriginX")));
			aDrawer.setOriginY(Integer
					.parseInt(getResource("aXMLDrawerOriginY")));
			aDrawer.setDefaultFont(Font.decode(getResource("aFont")));
			aDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("aHeight")));
			aDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("aWidth")));
			pseudocodeDrawerUtility = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			binSet = new GeometricFigure();
			binSetDrawer = new GeometricFigureXMLDrawerUtility(binSet,
					getResource("binSetTitle"), getOutputStream());
			binSetDrawer.setOriginX(Integer
					.parseInt(getResource("binSetXMLDrawerOriginX")));
			binSetDrawer.setOriginY(Integer
					.parseInt(getResource("binSetXMLDrawerOriginY")));
			binSetDrawer.setDefaultColor(new Color(Integer.parseInt(
					getResource("binColor"), 16)));
			binSetDrawer.setDefaultLineThickness(1.0F);
			interBinSpace = Integer.parseInt(getResource("spaceBetweenBins"));
			offset = Integer.parseInt(getResource("binXOffset"));
			binWidth = Integer.parseInt(getResource("binWidth"));
			binScale = Integer.parseInt(getResource("binScale"));
			setArraysDifferentialDrawArrays();
			step = 0;
		}

		private void setArraysDifferentialDrawArrays() {
			aIndex = new Integer[a.length()];
			aColor = new String[a.length()];
			for (int i = 0; i < a.length(); i++) {
				aIndex[i] = i;
				aColor[i] = getResource("aColor");
			}
			aColor[0] = getResource("binSizeColor");
		}

		private void setBinDifferentialDrawArrays(int i) {
			binIndex = new Integer[solution.size() + 2 * i];
			binColor = new String[solution.size() + 2 * i];
			for (int b = 0; b < solution.size(); b++) {
				binIndex[b] = b;
				binColor[b] = getResource("binColor");
			}
			int j = solution.size();
			for (int b = 0; b < solution.size(); b++) {
				Bin bin = solution.elementAt(b);
				for (int k = 0; k < bin.items.size(); k++) {
					binIndex[j] = j;
					if (k % 2 == 0) {
						binColor[j++] = getResource("evenItemInBinColor");
					} else {
						binColor[j++] = getResource("oddItemInBinColor");
					}
				}
			}
			for (; j < solution.size() + 2 * i; j++) {
				binIndex[j] = j;
				binColor[j] = "000000";
			}
		}
	}

	private Array<IntInformation> a;

	private Vector<Bin> solution;

	private VisualFirstFitDecreasing vffd;

	private int distribution(int left, int pivot, int right) {
		if (pivot != right) {
			swap(pivot, right);
		}
		int i = left;
		int j = right - 1;
		while (i <= j) {
			while ((i <= j) && (!a.elementAt(i).isLessThan(a.elementAt(right)))) {
				i = i + 1;
			}
			while ((i <= j)
					&& (!a.elementAt(j).isGreaterThan(a.elementAt(right)))) {
				j = j - 1;
			}
			if (i < j) {
				swap(i, j);
			}
		}
		if (i != right) {
			swap(i, right);
		}
		return i;
	}

	public void execute(String visFile) {
		vffd = new VisualFirstFitDecreasing(visFile);
		if (readInput()) {
			vffd.init();
			ffd();
			MessageUtility.algorithmTerminated(vffd
					.getResource("algorithmName"));
		}
	}

	private void ffd() {
		swap(0, a.length() - 1);
		vffd.ieStart();
		solution = new Vector<Bin>();
		quickSort(0, a.length() - 1);
		vffd.ieItemsSorted();
		for (int i = 1; i < a.length(); i++) {
			vffd.ieNewItemAnalyzed(i);
			boolean assigned = false;
			for (int j = 0; !assigned && j < solution.size(); j++) {
				if (solution.elementAt(j).size + a.elementAt(i).intValue() <= a
						.elementAt(0).intValue()) {
					solution.elementAt(j).add(a.elementAt(i).intValue());
					assigned = true;
					vffd.ieItemAdded(i, j);
				}
			}
			if (!assigned) {
				Bin b = new Bin();
				b.add(a.elementAt(i).intValue());
				solution.add(b);
				vffd.ieItemAndBinAdded(i, solution.size() - 1);
			}
		}
		vffd.ieEnd();
	}

	private void quickSort(int left, int right) {
		if (left < right) {
			int pivot = left + (int) (Math.random() * (right - left + 1));
			pivot = distribution(left, pivot, right);
			quickSort(left, pivot - 1);
			quickSort(pivot + 1, right);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vffd.getAlgorithmPath(), vffd
				.getResource("algorithmFileName"));
		a = (Array) inputLoader.load("Array", vffd
				.getResource("selectInputMessage"));
		if (a == null) {
			return false;
		}
		return true;
	}

	private void swap(int i, int j) {
		IntInformation tmp = a.elementAt(j);
		a.setAt(a.elementAt(i), j);
		a.setAt(tmp, i);
	}
}
