package org.algoritmica.alvie.algorithms;

import java.awt.Font;
import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.BinaryTree;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.SortableI;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.BinaryTreeXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class QuickSort<I extends SortableI<I>> {

	private class VisualQuickSort extends VisualInnerClass {
		private ArrayXMLDrawerUtility<I> aDrawer;

		private BinaryTree<StringInformation> recursiveCallBinaryTree;

		private BinaryTreeXMLDrawerUtility<StringInformation> recursiveCallBinaryTreeDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawerUtility,
				distributionPseudocodeDrawerUtility;

		private Integer[] aIndex;

		private String[] aColor;

		private String[] color;

		private BinaryTree<StringInformation> currentBinaryTree;

		private int currentPosition;

		private HashMap<Integer, BinaryTree<StringInformation>> father;

		private boolean allDistributionVisible;

		VisualQuickSort(String visFile) {
			super("quickSort", visFile);
		}

		private void ieAfterDistribution(int left, int right, int pivot) {
			StringInformation nodeInformation = subArrayStringRepresentation(
					left, right, pivot);
			currentBinaryTree.set(nodeInformation, currentBinaryTree
					.leftSubtree(), currentBinaryTree.rightSubtree());
			recursiveCallBinaryTreeDrawer.startStep(step++);
			recursiveCallBinaryTreeDrawer.draw(
					new Integer[] { currentPosition }, color,
					getResource("ieAfterDistribution"));
			recursiveCallBinaryTreeDrawer.endStep();
		}

		private void ieAfterLeftRecursiveInvocation(int left, int middle,
				int right) {
			if (left < middle) {
				currentBinaryTree = father.get(currentPosition);
				currentPosition = (currentPosition - 1) / 2;
				StringInformation nodeInformation = subArrayStringRepresentation(
						left, right, middle + 1);
				currentBinaryTree.set(nodeInformation, currentBinaryTree
						.leftSubtree(), currentBinaryTree.rightSubtree());
				recursiveCallBinaryTreeDrawer.startStep(step++);
				recursiveCallBinaryTreeDrawer.draw(
						new Integer[] { currentPosition }, color,
						getResource("ieAfterLeftRecursiveInvocation"));
				recursiveCallBinaryTreeDrawer.endStep();
			}
		}

		private void ieAfterRightRecursiveInvocation(int left, int middle,
				int right) {
			if (middle < right) {
				currentBinaryTree = father.get(currentPosition);
				currentPosition = (currentPosition - 2) / 2;
				StringInformation nodeInformation = subArrayStringRepresentation(
						left, right);
				currentBinaryTree.set(nodeInformation, currentBinaryTree
						.leftSubtree(), currentBinaryTree.rightSubtree());
				recursiveCallBinaryTreeDrawer.startStep(step++);
				recursiveCallBinaryTreeDrawer.draw(
						new Integer[] { currentPosition }, color,
						getResource("ieAfterRightRecursiveInvocation"));
				recursiveCallBinaryTreeDrawer.endStep();
			} else {
				StringInformation nodeInformation = subArrayStringRepresentation(
						left, right);
				currentBinaryTree.set(nodeInformation, currentBinaryTree
						.leftSubtree(), currentBinaryTree.rightSubtree());
			}
		}

		private void ieAfterSwap(int i, int j, int left, int right) {
			if (allDistributionVisible || (left == 0)
					&& (right == (a.length() - 1))) {
				aDrawer.startStep(step++);
				aDrawer.draw(aIndex, aColor, getResource("ieAfterSwap"));
				distributionPseudocodeDrawerUtility.draw("", new int[] { 9 });
				aDrawer.endStep();
			}
		}

		private void ieBeforeDistribution(int left, int right, int pivot) {
			StringInformation nodeInformation = subArrayStringRepresentation(
					left, right, pivot);
			currentBinaryTree.set(nodeInformation, currentBinaryTree
					.leftSubtree(), currentBinaryTree.rightSubtree());
			recursiveCallBinaryTreeDrawer.startStep(step++);
			recursiveCallBinaryTreeDrawer.draw(
					new Integer[] { currentPosition }, color,
					getResource("ieBeforeDistribution"));
			recursiveCallBinaryTreeDrawer.endStep();
		}

		private void ieBeforeLeftRecursiveInvocation(int left, int right) {
			if (left < right) {
				BinaryTree<StringInformation> leftSonBinaryTree = new BinaryTree<StringInformation>(
						new StringInformation());
				StringInformation nodeInformation = subArrayStringRepresentation(
						left, right);
				leftSonBinaryTree.set(nodeInformation,
						new BinaryTree<StringInformation>(
								new StringInformation()),
						new BinaryTree<StringInformation>(
								new StringInformation()));
				currentBinaryTree.set(currentBinaryTree.root(),
						leftSonBinaryTree, currentBinaryTree.rightSubtree());
				recursiveCallBinaryTreeDrawer.startStep(step++);
				recursiveCallBinaryTreeDrawer.draw(new Integer[] {
						currentPosition, 2 * currentPosition + 1 }, color,
						getResource("ieBeforeLeftRecursiveInvocation"));
				recursiveCallBinaryTreeDrawer.endStep();
				currentPosition = 2 * currentPosition + 1;
				father.put(currentPosition, currentBinaryTree);
				currentBinaryTree = leftSonBinaryTree;
			}
		}

		private void ieBeforeRightRecursiveInvocation(int left, int right) {
			if (left < right) {
				BinaryTree<StringInformation> rightSonBinaryTree = new BinaryTree<StringInformation>(
						new StringInformation());
				StringInformation nodeInformation = subArrayStringRepresentation(
						left, right);
				rightSonBinaryTree.set(nodeInformation,
						new BinaryTree<StringInformation>(
								new StringInformation()),
						new BinaryTree<StringInformation>(
								new StringInformation()));
				currentBinaryTree.set(currentBinaryTree.root(),
						currentBinaryTree.leftSubtree(), rightSonBinaryTree);
				recursiveCallBinaryTreeDrawer.startStep(step++);
				recursiveCallBinaryTreeDrawer.draw(new Integer[] {
						currentPosition, 2 * currentPosition + 2 }, color,
						getResource("ieBeforeRightRecursiveInvocation"));
				recursiveCallBinaryTreeDrawer.endStep();
				currentPosition = 2 * currentPosition + 2;
				father.put(currentPosition, currentBinaryTree);
				currentBinaryTree = rightSonBinaryTree;
			}
		}

		private void ieBeforeSwap(int i, int j, int left, int right) {
			if (allDistributionVisible || (left == 0)
					&& (right == (a.length() - 1))) {
				aDrawer.startStep(step++);
				aDrawer.draw(aIndex, aColor, getResource("ieBeforeSwap"));
				distributionPseudocodeDrawerUtility.draw("", new int[] { 9 });
				aDrawer.endStep();
			}
		}

		private void ieCursorPositioned(int i, int j, int left, int right) {
			if (allDistributionVisible || (left == 0)
					&& (right == (a.length() - 1))) {
				aColor[i] = getResource("aLeftCursorColor");
				aColor[j] = getResource("aRightCursorColor");
				aDrawer.startStep(step++);
				aDrawer.draw(aIndex, aColor, getResource("ieCursorPositioned"));
				distributionPseudocodeDrawerUtility
						.draw("", new int[] { 2, 3 });
				aDrawer.endStep();
			}
		}

		private void ieDistributionEnd(int i, int j, int left, int right) {
			if (allDistributionVisible || (left == 0)
					&& (right == (a.length() - 1))) {
				aColor[right] = getResource("aColor");
				if (j >= left) {
					aColor[j] = getResource("aColor");
				}
				aColor[i] = getResource("aPivotColor");
				aDrawer.startStep(step++);
				aDrawer.draw(aIndex, aColor, getResource("ieDistributionEnd"));
				distributionPseudocodeDrawerUtility.draw("", new int[] { 12 });
				aDrawer.endStep();
			}
		}

		private void ieDistributionStart(int left, int pivot, int right) {
			if (allDistributionVisible || (left == 0)
					&& (right == (a.length() - 1))) {
				for (int i = 0; i < left; i++) {
					aColor[i] = getResource("aUnusedColor");
				}
				for (int i = left; i < pivot; i++) {
					aColor[i] = getResource("aColor");
				}
				aColor[pivot] = getResource("aPivotColor");
				for (int i = pivot + 1; i < right + 1; i++) {
					aColor[i] = getResource("aColor");
				}
				for (int i = right + 1; i < a.length(); i++) {
					aColor[i] = getResource("aUnusedColor");
				}
				aDrawer.startStep(step++);
				aDrawer
						.draw(aIndex, aColor,
								getResource("ieDistributionStart"));
				distributionPseudocodeDrawerUtility.draw("", null);
				aDrawer.endStep();
			}
		}

		private void ieEnd() {
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieEnd"));
			aDrawer.endStep();
			aDrawer.endXMLFile();
		}

		private void ieFirstRecursiveInvocation() {
			recursiveCallBinaryTree = new BinaryTree<StringInformation>(
					new StringInformation());
			StringInformation nodeInformation = subArrayStringRepresentation(0,
					a.length() - 1);
			recursiveCallBinaryTree.set(nodeInformation,
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			recursiveCallBinaryTreeDrawer = new BinaryTreeXMLDrawerUtility<StringInformation>(
					recursiveCallBinaryTree,
					getResource("recursiveCallBinaryTreeTitle"),
					getOutputStream());
			recursiveCallBinaryTreeDrawer
					.setOriginX(Integer
							.parseInt(getResource("recursiveCallBinaryTreeXMLDrawerOriginX")));
			recursiveCallBinaryTreeDrawer
					.setOriginY(Integer
							.parseInt(getResource("recursiveCallBinaryTreeXMLDrawerOriginY")));
			recursiveCallBinaryTreeDrawer.setDefaultShape("Rectangular");
			recursiveCallBinaryTreeDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("nodeHeight")));
			recursiveCallBinaryTreeDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("nodeWidth")));
			recursiveCallBinaryTreeDrawer.startStep(step++);
			recursiveCallBinaryTreeDrawer
					.draw(getResource("ieFirstRecursiveInvocation"));
			recursiveCallBinaryTreeDrawer.endStep();
			currentBinaryTree = recursiveCallBinaryTree;
			currentPosition = 0;
			father = new HashMap<Integer, BinaryTree<StringInformation>>();
			setRecursiveCallBinaryTreeDifferentialDrawArrays();
		}

		private void iePivotHasToBeMovedAtEnd(int left, int pivot, int right) {
			if (allDistributionVisible || (left == 0)
					&& (right == (a.length() - 1))) {
				aColor[right] = getResource("aLastColor");
				aColor[pivot] = getResource("aPivotColor");
				aDrawer.startStep(step++);
				aDrawer.draw(aIndex, aColor,
						getResource("iePivotHasToBeMovedAtEnd"));
				distributionPseudocodeDrawerUtility.draw("", new int[] { 1 });
				aDrawer.endStep();
				aColor[pivot] = getResource("aColor");
			}
		}

		private void ieLastSwapDone(int i, int left, int right) {
			if (allDistributionVisible || (left == 0)
					&& (right == (a.length() - 1))) {
				aColor[i] = getResource("aPivotColor");
				aColor[right] = getResource("aLeftCursorColor");
				aDrawer.startStep(step++);
				aDrawer.draw(aIndex, aColor, getResource("ieLastSwapDone"));
				distributionPseudocodeDrawerUtility.draw("", new int[] { 11 });
				aDrawer.endStep();
			}
		}

		private void ieLastSwapNeeded(int i, int j, int left, int right) {
			if (allDistributionVisible || (left == 0)
					&& (right == (a.length() - 1))) {
				if (j >= left) {
					aColor[j] = getResource("aColor");
				}
				aDrawer.startStep(step++);
				aDrawer.draw(aIndex, aColor, getResource("ieLastSwapNeeded"));
				distributionPseudocodeDrawerUtility.draw("", new int[] { 11 });
				aDrawer.endStep();
			}
		}

		private void ieLeftCursorAdvanced(int i, int j, int left, int right) {
			if (allDistributionVisible || (left == 0)
					&& (right == (a.length() - 1))) {
				aColor[i - 1] = getResource("aColor");
				aColor[j] = getResource("aRightCursorColor");
				aColor[i] = getResource("aLeftCursorColor");
				aDrawer.startStep(step++);
				aDrawer.draw(aIndex, aColor,
						getResource("ieLeftCursorAdvanced"));
				distributionPseudocodeDrawerUtility.draw("", new int[] { 6 });
				aDrawer.endStep();
			}
		}

		private void iePivotMovedAtEnd(int left, int pivot, int right) {
			if (allDistributionVisible || (left == 0)
					&& (right == (a.length() - 1))) {
				aColor[right] = getResource("aPivotColor");
				aColor[pivot] = getResource("aMiddleColor");
				aDrawer.startStep(step++);
				aDrawer.draw(aIndex, aColor, getResource("iePivotMovedAtEnd"));
				distributionPseudocodeDrawerUtility.draw("", new int[] { 1 });
				aDrawer.endStep();
				aColor[pivot] = getResource("aColor");
			}
		}

		private void ieRightCursorBacked(int i, int j, int left, int right) {
			if (allDistributionVisible || (left == 0)
					&& (right == (a.length() - 1))) {
				if (j >= left) {
					aColor[j] = getResource("aRightCursorColor");
				}
				aColor[j + 1] = getResource("aColor");
				aColor[i] = getResource("aLeftCursorColor");
				aDrawer.startStep(step++);
				aDrawer
						.draw(aIndex, aColor,
								getResource("ieRightCursorBacked"));
				distributionPseudocodeDrawerUtility.draw("", new int[] { 8 });
				aDrawer.endStep();
			}
		}

		private void ieStart() {
			aDrawer.startXMLFile(getResource("algorithmName"));
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieStart"));
			pseudocodeDrawerUtility.draw("", null);
			aDrawer.endStep();
		}

		private void init() {
			aDrawer = new ArrayXMLDrawerUtility<I>(a, getResource("aTitle"),
					getOutputStream());
			aDrawer.setOriginX(Integer
					.parseInt(getResource("aXMLDrawerOriginX")));
			aDrawer.setOriginY(Integer
					.parseInt(getResource("aXMLDrawerOriginY")));
			aDrawer.setDefaultFont(Font.decode(getResource("aFont")));
			pseudocodeDrawerUtility = new PseudocodeXMLDrawerUtility(this);
			distributionPseudocodeDrawerUtility = new PseudocodeXMLDrawerUtility(
					this, "distributionPseudocode");
			setArraysDifferentialDrawArrays();
			allDistributionVisible = Boolean
					.parseBoolean(getResource("allDistributionVisible"));
			step = 0;
		}

		private void setArraysDifferentialDrawArrays() {
			aIndex = new Integer[a.length()];
			aColor = new String[a.length()];
			for (int i = 0; i < a.length(); i++) {
				aIndex[i] = i;
				aColor[i] = getResource("aColor");
			}
		}

		private void setRecursiveCallBinaryTreeDifferentialDrawArrays() {
			color = new String[] { getResource("fatherColor"),
					getResource("sonColor"), getResource("sonColor") };
		}

		private StringInformation subArrayStringRepresentation(int left,
				int right) {
			String stringRepresentation = "";
			for (int i = left; i < right; i++) {
				stringRepresentation = stringRepresentation
						+ a.elementAt(i).stringValue() + ";";
			}
			stringRepresentation = stringRepresentation
					+ a.elementAt(right).stringValue();
			return new StringInformation(stringRepresentation);
		}

		private StringInformation subArrayStringRepresentation(int left,
				int right, int pivot) {
			String stringRepresentation = "[";
			for (int i = left; i < pivot - 1; i++) {
				stringRepresentation = stringRepresentation
						+ a.elementAt(i).stringValue() + ";";
			}
			if (pivot == left) {
				stringRepresentation = stringRepresentation + "];"
						+ a.elementAt(pivot).stringValue() + ";[";
			} else {
				stringRepresentation = stringRepresentation
						+ a.elementAt(pivot - 1).stringValue() + "];"
						+ a.elementAt(pivot).stringValue() + ";[";
			}
			for (int i = pivot + 1; i < right; i++) {
				stringRepresentation = stringRepresentation
						+ a.elementAt(i).stringValue() + ";";
			}
			if (pivot == right) {
				stringRepresentation = stringRepresentation + "]";
			} else {
				stringRepresentation = stringRepresentation
						+ a.elementAt(right).stringValue() + "]";
			}
			return new StringInformation(stringRepresentation);
		}
	}

	private Array<I> a;

	VisualQuickSort vqs;

	private int distribution(int left, int pivot, int right) {
		vqs.ieDistributionStart(left, pivot, right);
		if (pivot != right) {
			vqs.iePivotHasToBeMovedAtEnd(left, pivot, right);
			swap(pivot, right);
			vqs.iePivotMovedAtEnd(left, pivot, right);
		}
		int i = left;
		int j = right - 1;
		vqs.ieCursorPositioned(i, j, left, right);
		while (i <= j) {
			while ((i <= j)
					&& (!a.elementAt(i).isGreaterThan(a.elementAt(right)))) {
				i = i + 1;
				vqs.ieLeftCursorAdvanced(i, j, left, right);
			}
			while ((i <= j) && (!a.elementAt(j).isLessThan(a.elementAt(right)))) {
				j = j - 1;
				vqs.ieRightCursorBacked(i, j, left, right);
			}
			if (i < j) {
				vqs.ieBeforeSwap(i, j, left, right);
				swap(i, j);
				vqs.ieAfterSwap(i, j, left, right);
			}
		}
		if (i != right) {
			vqs.ieLastSwapNeeded(i, j, left, right);
			swap(i, right);
			vqs.ieLastSwapDone(i, left, right);
		}
		vqs.ieDistributionEnd(i, j, left, right);
		return i;
	}

	public void execute(String visFile) {
		vqs = new VisualQuickSort(visFile);
		if (readInput()) {
			sort();
			MessageUtility.algorithmTerminated(vqs.getResource("algorithmName"));
		}
	}

	public void quickSort(int left, int right) {
		if (left < right) {
			int pivot = left + (int) (Math.random() * (right - left + 1));
			vqs.ieBeforeDistribution(left, right, pivot);
			pivot = distribution(left, pivot, right);
			vqs.ieAfterDistribution(left, right, pivot);
			vqs.ieBeforeLeftRecursiveInvocation(left, pivot - 1);
			quickSort(left, pivot - 1);
			vqs.ieAfterLeftRecursiveInvocation(left, pivot - 1, right);
			vqs.ieBeforeRightRecursiveInvocation(pivot + 1, right);
			quickSort(pivot + 1, right);
			vqs.ieAfterRightRecursiveInvocation(left, pivot + 1, right);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vqs.getAlgorithmPath(), vqs
				.getResource("algorithmFileName"));
		a = (Array) inputLoader.load("Array", vqs
				.getResource("selectInputMessage"));
		if (a == null) {
			return false;
		}
		return true;
	}

	private void sort() {
		vqs.init();
		vqs.ieStart();
		vqs.ieFirstRecursiveInvocation();
		quickSort(0, a.length() - 1);
		vqs.ieEnd();
	}

	private void swap(int i, int j) {
		I tmp = a.elementAt(j);
		a.setAt(a.elementAt(i), j);
		a.setAt(tmp, i);
	}
}
