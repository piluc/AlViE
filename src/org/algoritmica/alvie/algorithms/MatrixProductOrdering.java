package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.BinaryTree;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.BinaryTreeXMLDrawerUtility;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class MatrixProductOrdering {
	private class VisualMatrixProductOrdering extends VisualInnerClass {
		private BinaryTree<StringInformation> tree;

		private BinaryTreeXMLDrawerUtility<StringInformation> treeDrawer;

		private ArrayXMLDrawerUtility<IntInformation> dDrawer;

		private MatrixXMLDrawerUtility<StringInformation> costsDrawer;

		private MatrixXMLDrawerUtility<StringInformation> indexDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] dIndex, costsIndex, indexIndex, treeIndex;

		private String[] dColor, costsColor, indexColor, treeColor;

		String s;

		private VisualMatrixProductOrdering(String visFile) {
			super("matrixProductOrdering", visFile);
		}

		private BinaryTree<StringInformation> createTree(int i, int j) {
			if (i == j) {
				BinaryTree<StringInformation> result = new BinaryTree<StringInformation>(
						new StringInformation());
				result.set(new StringInformation(product(i, j)),
						new BinaryTree<StringInformation>(
								new StringInformation()),
						new BinaryTree<StringInformation>(
								new StringInformation()));
				return result;
			} else {
				BinaryTree<StringInformation> left = createTree(i, Integer
						.parseInt(index.elementAt(i, j).stringValue()));
				BinaryTree<StringInformation> right = createTree(Integer
						.parseInt(index.elementAt(i, j).stringValue()) + 1, j);
				BinaryTree<StringInformation> result = new BinaryTree<StringInformation>(
						new StringInformation());
				result.set(new StringInformation("x"), left, right);
				return result;
			}
		}

		private void createExpressionTree(int i, int j, int r) {
			BinaryTree<StringInformation> left = createTree(i, r);
			BinaryTree<StringInformation> right = createTree(r + 1, j);
			tree = new BinaryTree<StringInformation>(new StringInformation());
			tree.set(new StringInformation("x"), left, right);
			treeDrawer = new BinaryTreeXMLDrawerUtility<StringInformation>(
					tree, getResource("treeTitle"), getOutputStream());
			treeDrawer.setOriginX(Integer.parseInt(getResource("treeOriginX")));
			treeDrawer.setOriginY(Integer.parseInt(getResource("treeOriginY")));
			treeDrawer.setDefaultFont(Font
					.decode(getResource("dataStructureFont")));
			treeDrawer.setDefaultShape(getResource("treeShape"));
			treeDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("treeHeight")));
			treeDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("treeWidth")));
		}

		private void createTree(int i, int j, int r) {
			BinaryTree<StringInformation> left = createTree(i, r);
			left.set(
					new StringInformation(costs.elementAt(i, r).stringValue()),
					left.leftSubtree(), left.rightSubtree());
			BinaryTree<StringInformation> right = createTree(r + 1, j);
			right.set(new StringInformation(costs.elementAt(r + 1, j)
					.stringValue()), right.leftSubtree(), right.rightSubtree());
			tree = new BinaryTree<StringInformation>(new StringInformation());
			tree.set(new StringInformation(d.elementAt(i).stringValue() + "x"
					+ d.elementAt(r + 1).stringValue() + "x"
					+ d.elementAt(j + 1).stringValue()), left, right);
			treeDrawer = new BinaryTreeXMLDrawerUtility<StringInformation>(
					tree, getResource("treeTitle"), getOutputStream());
			treeDrawer.setOriginX(Integer.parseInt(getResource("treeOriginX")));
			treeDrawer.setOriginY(Integer.parseInt(getResource("treeOriginY")));
			treeDrawer.setDefaultFont(Font
					.decode(getResource("dataStructureFont")));
			treeDrawer.setDefaultShape(getResource("treeShape"));
			treeDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("treeHeight")));
			treeDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("treeWidth")));
		}

		private void ieComparisonHasToBeDone(int i, int j, int r) {
			createTree(i, j, r);
			costsColor[i * n + j] = getResource("newElementColor");
			costsColor[i * n + r] = getResource("usedCostsLeftElementColor");
			costsColor[(r + 1) * n + j] = getResource("usedCostsRightElementColor");
			dColor[i] = getResource("usedDElementColor");
			dColor[r + 1] = getResource("usedDElementColor");
			dColor[j + 1] = getResource("usedDElementColor");
			dDrawer.startStep(step++);
			dDrawer
					.draw(dIndex, dColor,
							getResource("ieComparisonHasToBeDone"));
			costsDrawer.draw(costsIndex, costsColor, "");
			indexDrawer.draw(indexIndex, indexColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 7 });
			}
			treeDrawer.draw(treeIndex, treeColor, "");
			dDrawer.endStep();
			costsColor[i * n + j] = getResource("dataStructureColor");
			costsColor[i * n + r] = getResource("dataStructureColor");
			costsColor[(r + 1) * n + j] = getResource("dataStructureColor");
			dColor[i] = getResource("dataStructureColor");
			dColor[r + 1] = getResource("dataStructureColor");
			dColor[j + 1] = getResource("dataStructureColor");
		}

		private void ieEnd() {
			costsColor[n - 1] = getResource("newElementColor");
			s = "";
			minimumProduct(0, n - 1);
			createExpressionTree(0, n - 1, Integer.parseInt(index.elementAt(0,
					n - 1).stringValue()));
			dDrawer.startStep(step++);
			dDrawer.draw(getResource("ieEnd"));
			costsDrawer.draw(costsIndex, costsColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 12 });
			}
			treeDrawer.draw("");
			dDrawer.endStep();
			dDrawer.endXMLFile();
		}

		private void ieMatricesInitialized() {
			dDrawer.startStep(step++);
			dDrawer.draw(getResource("ieMatricesInitialized"));
			costsDrawer.draw(costsIndex, costsColor, "");
			indexDrawer.draw(indexIndex, indexColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 1 });
			}
			dDrawer.endStep();
		}

		private void ieNewCostsElementHasToBeComputed(int i, int j) {
			costsColor[i * n + j] = getResource("newElementColor");
			dDrawer.startStep(step++);
			dDrawer.draw(getResource("ieNewCostsElementHasToBeComputed"));
			costsDrawer.draw(costsIndex, costsColor, "");
			indexDrawer.draw(indexIndex, indexColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 4, 5 });
			}
			dDrawer.endStep();
			costsColor[i * n + j] = getResource("dataStructureColor");
		}

		private void ieStart() {
			dDrawer.startXMLFile(getResource("algorithmName"));
			dDrawer.startStep(step++);
			dDrawer.draw(getResource("ieStart"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 0 });
			}
			dDrawer.endStep();
		}

		private void ieUpdateHasBeenDone(int i, int j, int r) {
			s = "";
			minimumProduct(i, j);
			costsColor[i * n + j] = getResource("newElementColor");
			indexColor[i * n + j] = getResource("newElementColor");
			createExpressionTree(i, j, r);
			dDrawer.startStep(step++);
			dDrawer.draw(getResource("ieUpdateHasBeenDone"));
			costsDrawer.draw(costsIndex, costsColor, "");
			indexDrawer.draw(indexIndex, indexColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 8, 9 });
			}
			treeDrawer.draw(new Integer[] { 0 },
					new String[] { getResource("newElementColor") }, "");
			dDrawer.endStep();
			costsColor[i * n + j] = getResource("dataStructureColor");
			indexColor[i * n + j] = getResource("dataStructureColor");
		}

		private void ieUpdateHasNotToBeDone(int i, int j, int r, int c) {
			costsColor[i * n + j] = getResource("newElementColor");
			costsColor[i * n + r] = getResource("usedCostsLeftElementColor");
			costsColor[(r + 1) * n + j] = getResource("usedCostsRightElementColor");
			dColor[i] = getResource("usedDElementColor");
			dColor[r + 1] = getResource("usedDElementColor");
			dColor[j + 1] = getResource("usedDElementColor");
			tree.set(new StringInformation("" + d.elementAt(i).intValue()
					* d.elementAt(r + 1).intValue()
					* d.elementAt(j + 1).intValue()), tree.leftSubtree(), tree
					.rightSubtree());
			dDrawer.startStep(step++);
			dDrawer.draw(dIndex, dColor, getResource("ieUpdateHasNotToBeDone"));
			costsDrawer.draw(costsIndex, costsColor, "");
			indexDrawer.draw(indexIndex, indexColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 10 });
			}
			treeDrawer.draw(treeIndex, treeColor, "");
			dDrawer.endStep();
			indexColor[i * n + j] = getResource("dataStructureColor");
			costsColor[i * n + j] = getResource("dataStructureColor");
			costsColor[i * n + r] = getResource("dataStructureColor");
			costsColor[(r + 1) * n + j] = getResource("dataStructureColor");
			dColor[i] = getResource("dataStructureColor");
			dColor[r + 1] = getResource("dataStructureColor");
			dColor[j + 1] = getResource("dataStructureColor");
		}

		private void ieUpdateHasToBeDone(int i, int j, int r, int c) {
			costsColor[i * n + j] = getResource("newElementColor");
			costsColor[i * n + r] = getResource("usedCostsLeftElementColor");
			costsColor[(r + 1) * n + j] = getResource("usedCostsRightElementColor");
			dColor[i] = getResource("usedDElementColor");
			dColor[r + 1] = getResource("usedDElementColor");
			dColor[j + 1] = getResource("usedDElementColor");
			tree.set(new StringInformation("" + d.elementAt(i).intValue()
					* d.elementAt(r + 1).intValue()
					* d.elementAt(j + 1).intValue()), tree.leftSubtree(), tree
					.rightSubtree());
			dDrawer.startStep(step++);
			dDrawer.draw(dIndex, dColor, getResource("ieUpdateHasToBeDone"));
			costsDrawer.draw(costsIndex, costsColor, "");
			indexDrawer.draw(indexIndex, indexColor, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 8, 9 });
			}
			treeDrawer.draw(treeIndex, treeColor, "");
			dDrawer.endStep();
			indexColor[i * n + j] = getResource("dataStructureColor");
			costsColor[i * n + j] = getResource("dataStructureColor");
			costsColor[i * n + r] = getResource("dataStructureColor");
			costsColor[(r + 1) * n + j] = getResource("dataStructureColor");
			dColor[i] = getResource("dataStructureColor");
			dColor[r + 1] = getResource("dataStructureColor");
			dColor[j + 1] = getResource("dataStructureColor");
		}

		private void init() {
			dDrawer = new ArrayXMLDrawerUtility<IntInformation>(d,
					getResource("dTitle"), getOutputStream());
			dDrawer.setOriginX(Integer
					.parseInt(getResource("dXMLDrawerOriginX")));
			dDrawer.setOriginY(Integer
					.parseInt(getResource("dXMLDrawerOriginY")));
			dDrawer.setDefaultFont(Font
					.decode(getResource("dataStructureFont")));
			dDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("dHeight")));
			dDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("dWidth")));
			costsDrawer = new MatrixXMLDrawerUtility<StringInformation>(costs,
					getResource("costsTitle"), getOutputStream());
			costsDrawer.setOriginX(Integer
					.parseInt(getResource("costsXMLDrawerOriginX")));
			costsDrawer.setOriginY(Integer
					.parseInt(getResource("costsXMLDrawerOriginY")));
			costsDrawer.setDefaultFont(Font
					.decode(getResource("dataStructureFont")));
			costsDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("costsHeight")));
			costsDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("costsWidth")));
			indexDrawer = new MatrixXMLDrawerUtility<StringInformation>(index,
					getResource("indexTitle"), getOutputStream());
			indexDrawer.setOriginX(Integer
					.parseInt(getResource("indexXMLDrawerOriginX")));
			indexDrawer.setOriginY(Integer
					.parseInt(getResource("indexXMLDrawerOriginY")));
			indexDrawer.setDefaultFont(Font
					.decode(getResource("dataStructureFont")));
			indexDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("indexHeight")));
			indexDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("indexWidth")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			initDifferentialDrawArrays();
			step = 0;
		}

		private void initDifferentialDrawArrays() {
			int l = d.length();
			dIndex = new Integer[l];
			dColor = new String[l];
			for (int i = 0; i < l; i++) {
				dIndex[i] = i;
				dColor[i] = getResource("dataStructureColor");
			}
			int w = n;
			int h = n;
			costsIndex = new Integer[w * h];
			costsColor = new String[w * h];
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					costsIndex[i * w + j] = i * w + j;
					if (i == j) {
						costsColor[i * w + j] = getResource("dataStructureColor");
					} else {
						costsColor[i * w + j] = getResource("hiddenElementColor");
					}
				}
			}
			indexIndex = new Integer[w * h];
			indexColor = new String[w * h];
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					indexIndex[i * w + j] = i * w + j;
					if (i == j) {
						indexColor[i * w + j] = getResource("dataStructureColor");
					} else {
						indexColor[i * w + j] = getResource("hiddenElementColor");
					}
				}
			}
			treeIndex = new Integer[] { 0, 1, 2 };
			treeColor = new String[] { getResource("usedDElementColor"),
					getResource("usedCostsLeftElementColor"),
					getResource("usedCostsRightElementColor") };
		}

		private void minimumProduct(int i, int j) {
			if (i == j) {
				s = s + "A" + i;
			} else {
				int r = Integer.parseInt(index.elementAt(i, j).stringValue());
				s = s + "(";
				minimumProduct(i, r);
				s = s + "x";
				minimumProduct(r + 1, j);
				s = s + ")";
			}
		}

		private String product(int i, int j) {
			String s = "";
			for (int k = i; k < j; k++) {
				s = s + "A" + k + "x";
			}
			s = s + "A" + j;
			return s;
		}

	}

	private Array<IntInformation> d;

	private Matrix<StringInformation> costs, index;

	private int diagonal, i, j, n, r;

	private int cost, maximumCost;

	VisualMatrixProductOrdering vmpo;

	public void execute(String visFile) {
		vmpo = new VisualMatrixProductOrdering(visFile);
		if (readInput()) {
			iterativeMinimumCost();
			MessageUtility.algorithmTerminated(vmpo.getResource("algorithmName"));
		}
	}

	private void iterativeMinimumCost() {
		vmpo.init();
		vmpo.ieStart();
		for (i = 0; i < n; i++) {
			costs.setAt(new StringInformation("" + 0), i, i);
			index.setAt(new StringInformation("" + i), i, i);
		}
		vmpo.ieMatricesInitialized();
		for (diagonal = 1; diagonal < n; diagonal++) {
			for (i = 0; i < n - diagonal; i++) {
				j = i + diagonal;
				costs.setAt(new StringInformation("oo"), i, j);
				vmpo.ieNewCostsElementHasToBeComputed(i, j);
				for (r = i; r < j; r++) {
					cost = Integer
							.parseInt(costs.elementAt(i, r).stringValue())
							+ Integer.parseInt(costs.elementAt(r + 1, j)
									.stringValue());
					cost = cost + d.elementAt(i).intValue()
							* d.elementAt(r + 1).intValue()
							* d.elementAt(j + 1).intValue();
					vmpo.ieComparisonHasToBeDone(i, j, r);
					if (costs.elementAt(i, j).isEqual(
							new StringInformation("oo"))
							|| cost < Integer.parseInt(costs.elementAt(i, j)
									.stringValue())) {
						vmpo.ieUpdateHasToBeDone(i, j, r, cost);
						costs.setAt(new StringInformation("" + cost), i, j);
						index.setAt(new StringInformation("" + r), i, j);
						vmpo.ieUpdateHasBeenDone(i, j, r);
					} else {
						vmpo.ieUpdateHasNotToBeDone(i, j, r, cost);
					}
				}
			}
		}
		vmpo.ieEnd();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vmpo.getAlgorithmPath(), vmpo
				.getResource("algorithmFileName"));
		d = (Array) inputLoader.load("Array", vmpo
				.getResource("selectInputMessage"));
		if (d == null) {
			return false;
		}
		n = d.length() - 1;
		costs = new Matrix<StringInformation>(n, n, new StringInformation());
		index = new Matrix<StringInformation>(n, n, new StringInformation());
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				costs.setAt(new StringInformation(" "), i, j);
				index.setAt(new StringInformation(" "), i, j);
			}
		}
		maximumCost = d.elementAt(0).intValue();
		for (i = 1; i <= n; i++) {
			maximumCost = maximumCost * d.elementAt(i).intValue();
		}
		return true;
	}

}
