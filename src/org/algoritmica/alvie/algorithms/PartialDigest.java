package org.algoritmica.alvie.algorithms;

import java.util.HashMap;
import java.util.Vector;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.BinaryTree;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.SortableI;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.BinaryTreeXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class PartialDigest<I extends SortableI<I>> {

	private class VisualPartialDigest extends VisualInnerClass {
		private BinaryTree<StringInformation> recursiveCallBinaryTree;

		private BinaryTreeXMLDrawerUtility<StringInformation> recursiveCallBinaryTreeDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private String[] color;

		private BinaryTree<StringInformation> currentBinaryTree;

		private int currentPosition;

		private HashMap<Integer, BinaryTree<StringInformation>> father;

		VisualPartialDigest(String visFile) {
			super("partialDigest", visFile);
		}

		private void ieAfterLeftRecursiveInvocation() {
			currentBinaryTree = father.get(currentPosition);
			currentPosition = (currentPosition - 1) / 2;
			recursiveCallBinaryTreeDrawer.startStep(step++);
			recursiveCallBinaryTreeDrawer
					.draw(getResource("ieAfterLeftRecursiveInvocation"));
			recursiveCallBinaryTreeDrawer.endStep();
		}

		private void ieAfterRightRecursiveInvocation() {
			currentBinaryTree = father.get(currentPosition);
			currentPosition = (currentPosition - 2) / 2;
			recursiveCallBinaryTreeDrawer.startStep(step++);
			recursiveCallBinaryTreeDrawer
					.draw(getResource("ieAfterRightRecursiveInvocation"));
			recursiveCallBinaryTreeDrawer.endStep();
		}

		private void ieBeforeLeftRecursiveInvocation() {
			BinaryTree<StringInformation> leftSonBinaryTree = new BinaryTree<StringInformation>(
					new StringInformation());
			StringInformation nodeInformation = xlStringRepresentation();
			leftSonBinaryTree.set(nodeInformation,
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			currentBinaryTree.set(currentBinaryTree.root(), leftSonBinaryTree,
					currentBinaryTree.rightSubtree());
			recursiveCallBinaryTreeDrawer.startStep(step++);
			recursiveCallBinaryTreeDrawer.draw(new Integer[] { currentPosition,
					2 * currentPosition + 1 }, color,
					getResource("ieBeforeLeftRecursiveInvocation"));
			recursiveCallBinaryTreeDrawer.endStep();
			currentPosition = 2 * currentPosition + 1;
			father.put(currentPosition, currentBinaryTree);
			currentBinaryTree = leftSonBinaryTree;
		}

		private void ieBeforeRightRecursiveInvocation() {
			BinaryTree<StringInformation> rightSonBinaryTree = new BinaryTree<StringInformation>(
					new StringInformation());
			StringInformation nodeInformation = xlStringRepresentation();
			rightSonBinaryTree.set(nodeInformation,
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			currentBinaryTree.set(currentBinaryTree.root(), currentBinaryTree
					.leftSubtree(), rightSonBinaryTree);
			recursiveCallBinaryTreeDrawer.startStep(step++);
			recursiveCallBinaryTreeDrawer.draw(new Integer[] { currentPosition,
					2 * currentPosition + 2 }, color,
					getResource("ieBeforeRightRecursiveInvocation"));
			recursiveCallBinaryTreeDrawer.endStep();
			currentPosition = 2 * currentPosition + 2;
			father.put(currentPosition, currentBinaryTree);
			currentBinaryTree = rightSonBinaryTree;
		}

		private void ieEnd() {
			recursiveCallBinaryTreeDrawer.startStep(step++);
			recursiveCallBinaryTreeDrawer.draw(getResource("ieEnd"));
			recursiveCallBinaryTreeDrawer.endStep();
			recursiveCallBinaryTreeDrawer.endXMLFile();
		}

		private void ieStart() {
			recursiveCallBinaryTreeDrawer
					.startXMLFile(getResource("algorithmName"));
			recursiveCallBinaryTreeDrawer.startStep(step++);
			recursiveCallBinaryTreeDrawer
					.draw(getResource("ieFirstRecursiveInvocation"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			recursiveCallBinaryTreeDrawer.endStep();
			currentBinaryTree = recursiveCallBinaryTree;
			currentPosition = 0;
			father = new HashMap<Integer, BinaryTree<StringInformation>>();
		}

		private void init() {
			recursiveCallBinaryTree = new BinaryTree<StringInformation>(
					new StringInformation());
			StringInformation nodeInformation = xlStringRepresentation();
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
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setRecursiveCallBinaryTreeDifferentialDrawArrays();
			step = 0;
		}

		private void setRecursiveCallBinaryTreeDifferentialDrawArrays() {
			color = new String[] { getResource("fatherColor"),
					getResource("sonColor"), getResource("sonColor") };
		}

		private StringInformation xlStringRepresentation() {
			String stringRepresentation = "X={";
			for (int i = 0; i < X.size() - 1; i++) {
				stringRepresentation = stringRepresentation + X.elementAt(i)
						+ ",";
			}
			if (!L.isEmpty()) {
				stringRepresentation = stringRepresentation
						+ X.elementAt(X.size() - 1) + "}\n L={";
				for (int i = 0; i < L.size() - 1; i++) {
					stringRepresentation = stringRepresentation
							+ L.elementAt(i) + ",";
				}
				stringRepresentation = stringRepresentation
						+ L.elementAt(L.size() - 1) + "}";
			} else {
				stringRepresentation = stringRepresentation
						+ X.elementAt(X.size() - 1) + "}\n L={}";
			}
			return new StringInformation(stringRepresentation);
		}
	}

	private Vector<Integer> L, X;

	private int width;

	private VisualPartialDigest vpd;

	private void addDelta(int y) {
		for (int i = 0; i < X.size(); i++) {
			int d = Math.abs(y - X.elementAt(i));
			L.add((Integer) d);
		}
	}

	private boolean deltaContained(int y) {
		Vector<Integer> T = new Vector<Integer>();
		for (int j = 0; j < L.size(); j++) {
			T.add(j, L.elementAt(j).intValue());
		}
		for (int i = 0; i < X.size(); i++) {
			boolean found = false;
			int d = Math.abs(y - X.elementAt(i));
			for (int j = 0; j < T.size(); j++) {
				if (d == T.elementAt(j)) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			} else {
				T.remove((Integer) d);
			}
		}
		return true;
	}

	public void execute(String visFile) {
		vpd = new VisualPartialDigest(visFile);
		if (readInput()) {
			partialDigest();
			MessageUtility
					.algorithmTerminated(vpd.getResource("algorithmName"));
		}
	}

	private int max() {
		int r = 0;
		int m = L.elementAt(r);
		for (int i = 1; i < L.size(); i++) {
			if (L.elementAt(i) > m) {
				r = i;
				m = L.elementAt(r);
			}
		}
		return r;
	}

	private void partialDigest() {
		int m = max();
		width = L.elementAt(m);
		L.remove(m);
		X = new Vector<Integer>();
		X.add(0, 0);
		X.add(1, width);
		vpd.init();
		vpd.ieStart();
		place();
		vpd.ieEnd();
	}

	private void place() {
		if (!L.isEmpty()) {
			int yi = max();
			int y = L.elementAt(yi);
			if (deltaContained(y)) {
				removeDelta(y);
				X.add((Integer) y);
				sort(L);
				sort(X);
				vpd.ieBeforeLeftRecursiveInvocation();
				place();
				vpd.ieAfterLeftRecursiveInvocation();
				X.remove((Integer) y);
				addDelta(y);
				sort(L);
				sort(X);
			}
			if (deltaContained(width - y)) {
				removeDelta(width - y);
				X.add((Integer) (width - y));
				sort(L);
				sort(X);
				vpd.ieBeforeRightRecursiveInvocation();
				place();
				vpd.ieAfterRightRecursiveInvocation();
				X.remove((Integer) (width - y));
				addDelta(width - y);
				sort(L);
				sort(X);
			}
		}
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vpd.getAlgorithmPath(), vpd
				.getResource("algorithmFileName"));
		Array<IntInformation> a = (Array) inputLoader.load("Array", vpd
				.getResource("selectInputMessage"));
		if (a == null) {
			return false;
		}
		int t = 8 * a.length() + 1;
		int tt = (int) Math.sqrt(t);
		if (tt * tt != t) {
			return false;
		}
		L = new Vector<Integer>(a.length());
		for (int i = 0; i < a.length(); i++) {
			if (a.elementAt(i).intValue() <= 0) {
				return false;
			}
			L.add(i, a.elementAt(i).intValue());
		}
		sort(L);
		return true;
	}

	private void removeDelta(int y) {
		for (int i = 0; i < X.size(); i++) {
			int d = Math.abs(y - X.elementAt(i));
			L.remove((Integer) d);
		}
	}

	private void sort(Vector<Integer> V) {
		for (int i = 0; i < V.size(); i = i + 1) {
			int next = V.elementAt(i);
			int j = i;
			while ((j > 0) && (V.elementAt(j - 1) > next)) {
				V.set(j, V.elementAt(j - 1));
				j = j - 1;
			}
			V.set(j, next);
		}

	}
}
