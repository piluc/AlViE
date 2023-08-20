package org.algoritmica.alvie.algorithms;

import java.awt.Font;
import java.util.Stack;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.BinaryTree;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.BinaryTreeXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class InversionCounter {
	private class VisualInversionCounter extends VisualInnerClass {
		private ArrayXMLDrawerUtility<IntInformation> aDrawer, bDrawer;

		private BinaryTree<StringInformation> callTree;

		private Stack<BinaryTree<StringInformation>> callStack;

		private BinaryTreeXMLDrawerUtility<StringInformation> callTreeDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private Integer[] aIndex, bIndex;

		private String[] aColor, bColor, treeColor;

		private boolean isPseudocodeVisible = false;

		private int fatherId, leftSonId, rightSonId;

		VisualInversionCounter(String visFile) {
			super("inversionCounter", visFile);
		}

		private String arrayToString(int l, int r) {
			String s = "";
			for (int i = l; i < r; i++) {
				s = s + a.elementAt(i).stringValue() + ";";
			}
			s = s + a.elementAt(r).stringValue();
			return s;
		}

		private void ieAfterFirstRecursiveCall(int l, int r, int inv) {
			setColor(l, r);
			BinaryTree<StringInformation> currentTree = callStack.pop();
			currentTree.set(new StringInformation(inv + ""), currentTree
					.leftSubtree(), currentTree.rightSubtree());
			callStack.push(currentTree);
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor,
					getResource("ieAfterFirstRecursiveCall"));
			callTreeDrawer.draw(new Integer[] { fatherId }, treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 3 });
			}
			aDrawer.endStep();
			callStack.pop();
			fatherId = (fatherId - 1) / 2;
		}

		private void ieAfterLastReturn() {
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieAfterLastReturn"));
			callTreeDrawer.draw(new Integer[] { fatherId }, treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 6 });
			}
			aDrawer.endStep();
		}

		private void ieAfterSecondRecursiveCall(int inv) {
			BinaryTree<StringInformation> currentTree = callStack.pop();
			currentTree.set(new StringInformation(inv + ""), currentTree
					.leftSubtree(), currentTree.rightSubtree());
			callStack.push(currentTree);
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor,
					getResource("ieAfterFirstRecursiveCall"));
			callTreeDrawer.draw(new Integer[] { fatherId }, treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 4 });
			}
			aDrawer.endStep();
			callStack.pop();
			fatherId = (fatherId - 2) / 2;
		}

		private void ieBeforeFirstInvocation() {
			callTree.set(new StringInformation(arrayToString(0, n - 1)),
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			callStack.push(callTree);
			fatherId = 0;
			setColor(0, n - 1);
			aDrawer.startStep(step++);
			aDrawer
					.draw(aIndex, aColor,
							getResource("ieBeforeFirstInvocation"));
			callTreeDrawer.draw(new Integer[] { fatherId }, treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 0 });
			}
			aDrawer.endStep();
		}

		private void ieBeforeFirstRecursiveCall(int l, int r) {
			BinaryTree<StringInformation> currentTree = callStack.pop();
			BinaryTree<StringInformation> leftSubtree = new BinaryTree<StringInformation>(
					new StringInformation());
			leftSubtree.set(new StringInformation(arrayToString(l, r)),
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			currentTree.set(currentTree.root(), leftSubtree,
					new BinaryTree<StringInformation>(new StringInformation()));
			callStack.push(currentTree);
			callStack.push(leftSubtree);
			leftSonId = 2 * fatherId + 1;
			setColor(l, r);
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor,
					getResource("ieBeforeFirstRecursiveCall"));
			callTreeDrawer.draw(new Integer[] { leftSonId }, treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 3 });
			}
			aDrawer.endStep();
			fatherId = leftSonId;
		}

		private void ieBeforeReturn(int c) {
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieBeforeReturn"));
			callTreeDrawer.draw(
					new Integer[] { fatherId, leftSonId, rightSonId },
					treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 6 });
			}
			aDrawer.endStep();
			BinaryTree<StringInformation> currentTree = callStack.pop();
			currentTree.set(new StringInformation(c + ""),
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			callStack.push(currentTree);
		}

		private void ieBeforeSecondRecursiveCall(int l, int r) {
			BinaryTree<StringInformation> currentTree = callStack.pop();
			BinaryTree<StringInformation> rightSubtree = new BinaryTree<StringInformation>(
					new StringInformation());
			rightSubtree.set(new StringInformation(arrayToString(l, r)),
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			currentTree.set(currentTree.root(), currentTree.leftSubtree(),
					rightSubtree);
			callStack.push(currentTree);
			callStack.push(rightSubtree);
			rightSonId = 2 * fatherId + 2;
			callTreeDrawer.startStep(step++);
			setColor(l, r);
			aDrawer.draw(aIndex, aColor,
					getResource("ieBeforeSecondRecursiveCall"));
			callTreeDrawer.draw(new Integer[] { rightSonId }, treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 4 });
			}
			callTreeDrawer.endStep();
			fatherId = rightSonId;
		}

		private void ieEnd(int inv) {
			aDrawer = new ArrayXMLDrawerUtility<IntInformation>(t,
					getResource("aTitle"), getOutputStream());
			aDrawer.setOriginX(Integer.parseInt(getResource("aDrawerOriginX")));
			aDrawer.setOriginY(Integer.parseInt(getResource("aDrawerOriginY")));
			aDrawer.setDefaultFont(Font.decode(getResource("aFont")));
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieEnd") + inv);
			aDrawer.endStep();
			aDrawer.endXMLFile();
		}

		private void ieLeftSmaller(int i) {
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieLeftSmaller"));
			bDrawer.draw(bIndex, bColor, "");
			callTreeDrawer.draw(
					new Integer[] { fatherId, leftSonId, rightSonId },
					treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 14 });
			}
			aDrawer.endStep();
			aColor[i] = getResource("mergeDoneColor");
		}

		private void ieMergeEnding(int l, int r) {
			setColor(l, r);
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieMergeEnding"));
			bDrawer.draw(bIndex, bColor, "");
			callTreeDrawer.draw(
					new Integer[] { fatherId, leftSonId, rightSonId },
					treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 22 });
			}
			aDrawer.endStep();
		}

		private void ieMergeStart(int l, int m, int r) {
			BinaryTree<StringInformation> currentTree = callStack.pop();
			currentTree.set(new StringInformation("0"), currentTree
					.leftSubtree(), currentTree.rightSubtree());
			callStack.push(currentTree);
			setColor(l, m, r);
			leftSonId = 2 * fatherId + 1;
			rightSonId = 2 * fatherId + 2;
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieMergeStart"));
			bDrawer.draw(bIndex, bColor, "");
			callTreeDrawer.draw(
					new Integer[] { fatherId, leftSonId, rightSonId },
					treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 11 });
			}
			aDrawer.endStep();
		}

		private void ieOneElement() {
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieOneElement"));
			callTreeDrawer.draw(new Integer[] { fatherId }, treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 8 });
			}
			aDrawer.endStep();
		}

		private void ieRemainingMerge(int i, int m) {
			aDrawer.startStep(step++);
			if (i <= m) {
				aDrawer.draw(aIndex, aColor,
						getResource("ieRemainingMergeLeft"));
			} else {
				aDrawer.draw(aIndex, aColor,
						getResource("ieRemainingMergeRight"));
			}
			bDrawer.draw(bIndex, bColor, "");
			callTreeDrawer.draw(
					new Integer[] { fatherId, leftSonId, rightSonId },
					treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 20, 21 });
			}
			aDrawer.endStep();
		}

		private void ieRightSmaller(int i, int c) {
			aDrawer.startStep(step++);
			aDrawer.draw(aIndex, aColor, getResource("ieRightSmaller"));
			bDrawer.draw(bIndex, bColor, "");
			callTreeDrawer.draw(
					new Integer[] { fatherId, leftSonId, rightSonId },
					treeColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 16 });
			}
			aDrawer.endStep();
			aColor[i] = getResource("mergeDoneColor");
			BinaryTree<StringInformation> currentTree = callStack.pop();
			currentTree.set(new StringInformation("" + c), currentTree
					.leftSubtree(), currentTree.rightSubtree());
			callStack.push(currentTree);
		}

		private void ieStart() {
			aDrawer.startXMLFile(getResource("algorithmName"));
			aDrawer.startStep(step++);
			aDrawer.draw(getResource("ieStart"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", null);
			}
			aDrawer.endStep();
		}

		private void init() {
			aDrawer = new ArrayXMLDrawerUtility<IntInformation>(a,
					getResource("aTitle"), getOutputStream());
			aDrawer.setOriginX(Integer.parseInt(getResource("aDrawerOriginX")));
			aDrawer.setOriginY(Integer.parseInt(getResource("aDrawerOriginY")));
			aDrawer.setDefaultFont(Font.decode(getResource("aFont")));
			bDrawer = new ArrayXMLDrawerUtility<IntInformation>(b,
					getResource("bTitle"), getOutputStream());
			bDrawer.setOriginX(Integer.parseInt(getResource("bDrawerOriginX")));
			bDrawer.setOriginY(Integer.parseInt(getResource("bDrawerOriginY")));
			bDrawer.setDefaultFont(Font.decode(getResource("bFont")));
			callTree = new BinaryTree<StringInformation>(
					new StringInformation());
			callStack = new Stack<BinaryTree<StringInformation>>();
			callTreeDrawer = new BinaryTreeXMLDrawerUtility<StringInformation>(
					callTree, getResource("treeName"), getOutputStream());
			callTreeDrawer.setOriginX(Integer
					.parseInt(getResource("treeDrawerOriginX")));
			callTreeDrawer.setOriginY(Integer
					.parseInt(getResource("treeDrawerOriginY")));
			callTreeDrawer.setDefaultFont(Font.decode(getResource("treeFont")));
			callTreeDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("treeHeight")));
			callTreeDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("treeWidth")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setDifferentialDrawArrays();
			step = 0;
		}

		private void setColor(int l, int r) {
			for (int i = 0; i < l; i++) {
				aColor[i] = getResource("aInactiveColor");
			}
			for (int i = l; i <= r; i++) {
				aColor[i] = getResource("aActiveColor");
			}
			for (int i = r + 1; i < n; i++) {
				aColor[i] = getResource("aInactiveColor");
			}
		}

		private void setColor(int l, int m, int r) {
			for (int i = 0; i < l; i++) {
				aColor[i] = getResource("aInactiveColor");
			}
			for (int i = l; i <= m; i++) {
				aColor[i] = getResource("leftColor");
			}
			for (int i = m + 1; i <= r; i++) {
				aColor[i] = getResource("rightColor");
			}
			for (int i = r + 1; i < n; i++) {
				aColor[i] = getResource("aInactiveColor");
			}
			for (int i = 0; i <= r - l; i++) {
				b.setAt(new IntInformation(-1), i);
				bColor[i] = getResource("bActiveColor");
			}
			for (int i = r - l + 1; i < n; i++) {
				b.setAt(new IntInformation(-1), i);
				bColor[i] = getResource("bInactiveColor");
			}
		}

		private void setDifferentialDrawArrays() {
			aIndex = new Integer[n];
			bIndex = new Integer[n];
			aColor = new String[n];
			bColor = new String[n];
			for (int i = 0; i < n; i++) {
				aIndex[i] = i;
				bIndex[i] = i;
				aColor[i] = getResource("aColor");
				bColor[i] = getResource("bColor");
			}
			treeColor = new String[] { getResource("fatherColor"),
					getResource("leftColor"), getResource("rightColor") };
		}
	}

	private Array<IntInformation> a, b, t;

	private int n;

	private VisualInversionCounter vic;

	private void copyInput() {
		t = new Array<IntInformation>(n, new IntInformation());
		for (int i = 0; i < n; i++) {
			t.setAt(a.elementAt(i), i);
		}
	}

	private int count(int l, int r) {
		if (l == r) {
			vic.ieOneElement();
			return 0;
		} else {
			int m = (l + r) / 2;
			vic.ieBeforeFirstRecursiveCall(l, m);
			int invLeft = count(l, m);
			vic.ieAfterFirstRecursiveCall(l, m, invLeft);
			vic.ieBeforeSecondRecursiveCall(m + 1, r);
			int invRight = count(m + 1, r);
			vic.ieAfterSecondRecursiveCall(invRight);
			int invLeftRight = merge(l, m, r);
			vic.ieBeforeReturn(invLeft + invRight + invLeftRight);
			return invLeft + invRight + invLeftRight;
		}
	}

	public void execute(String visFile) {
		vic = new VisualInversionCounter(visFile);
		if (readInput()) {
			copyInput();
			vic.init();
			vic.ieStart();
			vic.ieBeforeFirstInvocation();
			int c = count(0, n - 1);
			vic.ieAfterLastReturn();
			vic.ieEnd(c);
			MessageUtility.algorithmTerminated(vic.getResource("algorithmName"));
		}
	}

	private int merge(int l, int m, int r) {
		vic.ieMergeStart(l, m, r);
		int c = 0;
		int i = l;
		int j = m + 1;
		int k = 0;
		while ((i <= m) && (j <= r)) {
			if (!a.elementAt(i).isGreaterThan(a.elementAt(j))) {
				vic.ieLeftSmaller(i);
				b.setAt(a.elementAt(i), k);
				i = i + 1;
			} else {
				vic.ieRightSmaller(j, c + (m - i) + 1);
				b.setAt(a.elementAt(j), k);
				j = j + 1;
				c = c + (m - i) + 1;
			}
			k = k + 1;
		}
		vic.ieRemainingMerge(i, m);
		for (; i <= m; i++, k++) {
			b.setAt(a.elementAt(i), k);
		}
		for (; j <= r; j++, k++) {
			b.setAt(a.elementAt(j), k);
		}
		vic.ieMergeEnding(l, r);
		for (i = l; i <= r; i++) {
			a.setAt(b.elementAt(i - l), i);
		}
		return c;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vic.getAlgorithmPath(), vic
				.getResource("algorithmFileName"));
		a = (Array) inputLoader.load("Array", vic
				.getResource("selectInputMessage"));
		if (a == null) {
			return false;
		}
		n = a.length();
		boolean[] isPresent = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (a.elementAt(i).intValue() < n) {
				isPresent[i] = true;
			} else {
				return false;
			}
		}
		for (int i = 0; i < n; i++) {
			if (!isPresent[i]) {
				return false;
			}
		}
		b = new Array<IntInformation>(n, new IntInformation());
		return true;
	}

}
