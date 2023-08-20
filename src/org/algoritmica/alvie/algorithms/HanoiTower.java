package org.algoritmica.alvie.algorithms;

import java.awt.Font;
import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.BinaryTree;
import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.GeometricObjectInformation;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.BinaryTreeXMLDrawerUtility;
import org.algoritmica.alvie.utility.GeometricFigureXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class HanoiTower {
	class VisualHanoiTower extends VisualInnerClass {
		private GeometricFigure towerFigure;

		private GeometricFigureXMLDrawerUtility towerFigureDrawer;

		private BinaryTree<StringInformation> callTree;

		private BinaryTreeXMLDrawerUtility<StringInformation> callTreeDrawer;

		private Integer[] towerIndex;

		private String[] towerColor;

		private int[] towerX;

		private int[] diskWidth;

		private int currentPosition;

		private BinaryTree<StringInformation> currentBinaryTree;

		private HashMap<Integer, BinaryTree<StringInformation>> father;

		private String[] callTreeColor, callTreeFont, callTreeHeight,
				callTreeShape, callTreeWidth;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		public VisualHanoiTower(String visFile) {
			super("hanoiTower", visFile);
		}

		void ieAfterFirstRecursiveCall(int s, int d) {
			towerColor[s] = getResource("sourceTowerColor");
			towerColor[d] = getResource("destinationTowerColor");
			towerColor[towerNumber + diskNumber - 1 - tower[top[s]][s]] = getResource("movingDiskColor");
			currentBinaryTree = father.get(currentPosition);
			currentPosition = (currentPosition - 1) / 2;
			towerFigureDrawer.startStep(step++);
			towerFigureDrawer.draw(towerIndex, towerColor,
					getResource("ieAfterFirstRecursiveCall"));
			callTreeDrawer.draw(new Integer[] { currentPosition },
					callTreeColor, callTreeFont, callTreeShape, callTreeHeight,
					callTreeWidth, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 5 });
			}
			towerFigureDrawer.endStep();
		}

		void ieAfterSecondRecursiveCall() {
			currentBinaryTree = father.get(currentPosition);
			currentPosition = (currentPosition - 2) / 2;
			towerFigureDrawer.startStep(step++);
			towerFigureDrawer.draw(towerIndex, towerColor,
					getResource("ieAfterSecondRecursiveCall"));
			callTreeDrawer.draw(new Integer[] { currentPosition },
					callTreeColor, callTreeFont, callTreeShape, callTreeHeight,
					callTreeWidth, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 6 });
			}
			towerFigureDrawer.endStep();
		}

		void ieBeforeFirstRecursiveCall(int n, int s, int d) {
			towerFigureDrawer.startStep(step++);
			towerFigureDrawer.draw(towerIndex, towerColor,
					getResource("ieBeforeFirstRecursiveCall1") + n + " "
							+ getResource("ieBeforeFirstRecursiveCall2") + s
							+ " " + getResource("ieBeforeFirstRecursiveCall3")
							+ d + getResource("ieBeforeFirstRecursiveCall4"));
			callTreeDrawer.draw(new Integer[] { currentPosition },
					callTreeColor, callTreeFont, callTreeShape, callTreeHeight,
					callTreeWidth, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 4 });
			}
			towerFigureDrawer.endStep();
			BinaryTree<StringInformation> leftSonBinaryTree = new BinaryTree<StringInformation>(
					new StringInformation(""));
			leftSonBinaryTree
					.set(new StringInformation(n + "," + s + "," + d),
							new BinaryTree<StringInformation>(
									new StringInformation("")),
							new BinaryTree<StringInformation>(
									new StringInformation("")));
			currentBinaryTree.set(currentBinaryTree.root(), leftSonBinaryTree,
					currentBinaryTree.rightSubtree());
			currentPosition = 2 * currentPosition + 1;
			father.put(currentPosition, currentBinaryTree);
			currentBinaryTree = leftSonBinaryTree;
		}

		void ieBeforeSecondRecursiveCall(int n, int s, int d) {
			towerFigureDrawer.startStep(step++);
			towerFigureDrawer.draw(towerIndex, towerColor,
					getResource("ieBeforeSecondRecursiveCall1") + n + " "
							+ getResource("ieBeforeSecondRecursiveCall2") + s
							+ " " + getResource("ieBeforeSecondRecursiveCall3")
							+ d + getResource("ieBeforeSecondRecursiveCall4"));
			callTreeDrawer.draw(new Integer[] { currentPosition },
					callTreeColor, callTreeFont, callTreeShape, callTreeHeight,
					callTreeWidth, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 6 });
			}
			towerFigureDrawer.endStep();
			BinaryTree<StringInformation> rightSonBinaryTree = new BinaryTree<StringInformation>(
					new StringInformation(""));
			rightSonBinaryTree
					.set(new StringInformation(n + "," + s + "," + d),
							new BinaryTree<StringInformation>(
									new StringInformation("")),
							new BinaryTree<StringInformation>(
									new StringInformation("")));
			currentBinaryTree.set(currentBinaryTree.root(), currentBinaryTree
					.leftSubtree(), rightSonBinaryTree);
			currentPosition = 2 * currentPosition + 2;
			father.put(currentPosition, currentBinaryTree);
			currentBinaryTree = rightSonBinaryTree;
		}

		void ieDiskHasBeenMoved(int s, int d) {
			int diskIndex = tower[top[d]][d];
			int deltaY = Integer.parseInt(getResource("interDiskSpace"));
			int h = Integer.parseInt(getResource("diskHeight"));
			int w = diskWidth[diskIndex];
			int x = towerX[d] - w / 2;
			int y = -h - (diskNumber - 1 - top[d]) * (h + deltaY);
			towerFigure.setAt(new GeometricObjectInformation(
					GeometricObjectInformation.RECTANGLE, x, y, w, h),
					towerNumber + diskNumber - 1 - diskIndex);
			towerColor[towerNumber + diskNumber - 1 - diskIndex] = getResource("movedDiskColor");
			towerFigureDrawer.startStep(step++);
			towerFigureDrawer.draw(towerIndex, towerColor,
					getResource("ieDiskHasBeenMoved"));
			callTreeDrawer.draw(new Integer[] { currentPosition },
					callTreeColor, callTreeFont, callTreeShape, callTreeHeight,
					callTreeWidth, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 2 });
			}
			towerFigureDrawer.endStep();
			towerColor[s] = getResource("towerColor");
			towerColor[d] = getResource("towerColor");
			towerColor[towerNumber + diskNumber - 1 - diskIndex] = getResource("diskColor");
		}

		void ieDiskMustBeMoved(int s, int d) {
			towerColor[s] = getResource("sourceTowerColor");
			towerColor[d] = getResource("destinationTowerColor");
			towerColor[towerNumber + diskNumber - 1 - tower[top[s]][s]] = getResource("movingDiskColor");
			towerFigureDrawer.startStep(step++);
			towerFigureDrawer.draw(towerIndex, towerColor,
					getResource("ieDiskMustBeMoved"));
			callTreeDrawer.draw(new Integer[] { currentPosition },
					callTreeColor, callTreeFont, callTreeShape, callTreeHeight,
					callTreeWidth, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 2 });
			}
			towerFigureDrawer.endStep();
		}

		void ieEnd() {
			towerFigureDrawer.startStep(step++);
			towerFigureDrawer
					.draw(towerIndex, towerColor, getResource("ieEnd"));
			towerFigureDrawer.endStep();
			towerFigureDrawer.endXMLFile();
		}

		void ieFunctionStart() {
			towerFigureDrawer.startStep(step++);
			towerFigureDrawer.draw(towerIndex, towerColor,
					getResource("ieFunctionStart"));
			callTreeDrawer.draw(new Integer[] { currentPosition },
					callTreeColor, callTreeFont, callTreeShape, callTreeHeight,
					callTreeWidth, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 0 });
			}
			towerFigureDrawer.endStep();
		}

		void ieSecondMove(int s, int d) {
			int diskIndex = tower[top[d]][d];
			int deltaY = Integer.parseInt(getResource("interDiskSpace"));
			int h = Integer.parseInt(getResource("diskHeight"));
			int w = diskWidth[diskIndex];
			int x = towerX[d] - w / 2;
			int y = -h - (diskNumber - 1 - top[d]) * (h + deltaY);
			towerFigure.setAt(new GeometricObjectInformation(
					GeometricObjectInformation.RECTANGLE, x, y, w, h),
					towerNumber + diskNumber - 1 - diskIndex);
			towerColor[towerNumber + diskNumber - 1 - diskIndex] = getResource("movedDiskColor");
			towerFigureDrawer.startStep(step++);
			towerFigureDrawer.draw(towerIndex, towerColor,
					getResource("ieSecondMove"));
			callTreeDrawer.draw(new Integer[] { currentPosition },
					callTreeColor, callTreeFont, callTreeShape, callTreeHeight,
					callTreeWidth, "");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 5 });
			}
			towerFigureDrawer.endStep();
			towerColor[s] = getResource("towerColor");
			towerColor[d] = getResource("towerColor");
			towerColor[towerNumber + diskNumber - 1 - diskIndex] = getResource("diskColor");
		}

		void ieStart() {
			towerFigureDrawer.startXMLFile(getResource("algorithmName"));
			towerFigureDrawer.startStep(step++);
			towerFigureDrawer.draw(towerIndex, towerColor,
					getResource("ieStart"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", null);
			}
			towerFigureDrawer.endStep();
		}

		void init() {
			towerFigure = new GeometricFigure();
			int deltaX = Integer.parseInt(getResource("diskWidthDifference"));
			int w = Integer.parseInt(getResource("diskMinimumWidth"))
					+ diskNumber * deltaX;
			deltaX = Integer.parseInt(getResource("interTowerSpace")) + w;
			int deltaY = Integer.parseInt(getResource("interDiskSpace"));
			int h = Integer.parseInt(getResource("diskHeight"));
			towerX = new int[towerNumber];
			for (int i = 0; i < towerNumber; i++) {
				towerFigure.add(new GeometricObjectInformation(
						GeometricObjectInformation.SEGMENT, w / 2 + i * deltaX,
						0, w / 2 + i * deltaX, -diskNumber * (h + deltaY)));
				towerX[i] = w / 2 + i * deltaX;
			}
			diskWidth = new int[diskNumber];
			deltaX = Integer.parseInt(getResource("diskWidthDifference"));
			w = Integer.parseInt(getResource("diskMinimumWidth")) + diskNumber
					* deltaX;
			int x = 0;
			int y = -h;
			for (int i = 0; i < diskNumber; i++) {
				diskWidth[diskNumber - i - 1] = w;
				towerFigure.add(new GeometricObjectInformation(
						GeometricObjectInformation.RECTANGLE, x, y, w, h));
				x = x + deltaX / 2;
				y = y - h - deltaY;
				w = w - deltaX;
			}
			towerFigureDrawer = new GeometricFigureXMLDrawerUtility(
					towerFigure, getResource("towerName"), getOutputStream());
			towerFigureDrawer.setOriginX(Integer
					.parseInt(getResource("towerOriginX")));
			towerFigureDrawer.setOriginY(Integer
					.parseInt(getResource("towerOriginY")));
			towerFigureDrawer.setDefaultLineThickness(Float
					.parseFloat(getResource("towerThickness")));
			callTree = new BinaryTree<StringInformation>(new StringInformation(
					""));
			callTree
					.set(new StringInformation(diskNumber + ",0,"
							+ (towerNumber - 1)),
							new BinaryTree<StringInformation>(
									new StringInformation("")),
							new BinaryTree<StringInformation>(
									new StringInformation("")));
			callTreeDrawer = new BinaryTreeXMLDrawerUtility<StringInformation>(
					callTree, getResource("callTreeName"), getOutputStream());
			callTreeDrawer.setOriginX(Integer
					.parseInt(getResource("callTreeXMLDrawerOriginX")));
			callTreeDrawer.setOriginY(Integer
					.parseInt(getResource("callTreeXMLDrawerOriginY")));
			callTreeDrawer.setDefaultFont(Font
					.decode(getResource("callTreeFont")));
			callTreeDrawer.setDefaultShape(getResource("callTreeShape"));
			callTreeDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("callTreeElementHeight")));
			callTreeDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("callTreeElementWidth")));
			currentBinaryTree = callTree;
			currentPosition = 0;
			father = new HashMap<Integer, BinaryTree<StringInformation>>();
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			initBoardDifferentialDrawArrays();
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setCallTreeDifferentialDrawArrays();
			step = 0;
		}

		private void initBoardDifferentialDrawArrays() {
			towerIndex = new Integer[towerNumber + diskNumber];
			towerColor = new String[towerNumber + diskNumber];
			for (int i = 0; i < towerNumber; i++) {
				towerIndex[i] = i;
				towerColor[i] = getResource("towerColor");
			}
			for (int i = towerNumber; i < diskNumber + towerNumber; i++) {
				towerIndex[i] = i;
				towerColor[i] = getResource("diskColor");
			}
		}

		private void setCallTreeDifferentialDrawArrays() {
			callTreeColor = new String[] { getResource("callTreeEmphasizedElementColor") };
			callTreeFont = new String[] { getResource("callTreeEmphasizedElementFont") };
			callTreeHeight = new String[] { getResource("callTreeEmphasizedElementHeight") };
			callTreeShape = new String[] { getResource("callTreeShape") };
			callTreeWidth = new String[] { getResource("callTreeEmphasizedElementWidth") };
		}

	}

	private final int towerNumber = 3;

	private int diskNumber;

	private int[][] tower;

	private int[] top;

	private VisualHanoiTower vht;

	private void createTower() {
		tower = new int[diskNumber][towerNumber];
		for (int i = diskNumber - 1; i >= 0; i--) {
			tower[i][0] = i;
		}
		for (int i = 0; i < diskNumber; i++) {
			for (int j = 1; j < towerNumber; j++) {
				tower[i][j] = -1;
			}
		}
		top = new int[towerNumber];
		top[0] = 0;
		for (int i = 1; i < towerNumber; i++) {
			top[i] = diskNumber;
		}
	}

	public void execute(String visFile) {
		vht = new VisualHanoiTower(visFile);
		if (readInput()) {
			createTower();
			hanoiTower();
			MessageUtility.algorithmTerminated(vht.getResource("algorithmName"));
		}
	}

	private void hanoiTower() {
		vht.init();
		vht.ieStart();
		hanoiTower(diskNumber, 0, 2);
		vht.ieEnd();
	}

	private void hanoiTower(int n, int s, int d) {
		vht.ieFunctionStart();
		if (n == 1) {
			vht.ieDiskMustBeMoved(s, d);
			top[d]--;
			tower[top[d]][d] = tower[top[s]][s];
			tower[top[s]][s] = -1;
			vht.ieDiskHasBeenMoved(s, d);
			top[s] = top[s] + 1;
		} else {
			vht.ieBeforeFirstRecursiveCall(n - 1, s, 3 - s - d);
			hanoiTower(n - 1, s, 3 - s - d);
			vht.ieAfterFirstRecursiveCall(s, d);
			top[d]--;
			tower[top[d]][d] = tower[top[s]][s];
			tower[top[s]][s] = -1;
			vht.ieSecondMove(s, d);
			top[s] = top[s] + 1;
			vht.ieBeforeSecondRecursiveCall(n - 1, 3 - s - d, d);
			hanoiTower(n - 1, 3 - s - d, d);
			vht.ieAfterSecondRecursiveCall();
		}
	}

	@SuppressWarnings("unchecked")
	private boolean readInput() {
		Array<IntInformation> input;
		InputLoader inputLoader = new InputLoader(vht.getAlgorithmPath(), vht
				.getResource("algorithmFileName"));
		input = (Array<IntInformation>) inputLoader.load("Array", vht
				.getResource("selectInputMessage"));
		if (input == null || input.elementAt(0).intValue() <= 0) {
			return false;
		}
		diskNumber = input.elementAt(0).intValue();
		return true;
	}
}
