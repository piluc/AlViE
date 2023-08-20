package org.algoritmica.alvie.algorithms;

import java.awt.Font;
import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.BinaryTree;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.BinaryTreeXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class BinaryStringGeneration {
	private class VisualBinaryStringGeneration extends VisualInnerClass {
		private BinaryTree<StringInformation> callTree;

		private BinaryTreeXMLDrawerUtility<StringInformation> callTreeXMLDrawer;

		private BinaryTree<StringInformation> currentBinaryTree;

		private int currentPosition;

		private HashMap<Integer, BinaryTree<StringInformation>> father;

		private PseudocodeXMLDrawerUtility pseudocodeDrawerUtility;

		private boolean isPseudocodeVisible;

		private String[] color;

		VisualBinaryStringGeneration(String visFile) {
			super("binaryStringGeneration", visFile);
		}

		private StringInformation arrayStringRepresentation() {
			String stringRepresentation = "";
			for (int i = 0; i < a.length() - 1; i++) {
				stringRepresentation = stringRepresentation
						+ a.elementAt(i).stringValue() + ";";
			}
			stringRepresentation = stringRepresentation
					+ a.elementAt(a.length() - 1).stringValue();
			return new StringInformation(stringRepresentation);
		}

		private void ieAfterSecondRecursiveCall() {
			currentBinaryTree = father.get(currentPosition);
			currentPosition = (currentPosition - 2) / 2;
		}

		private void ieBeforeFirstRecursiveCall(int b) {
			BinaryTree<StringInformation> leftSonBinaryTree = new BinaryTree<StringInformation>(
					new StringInformation());
			leftSonBinaryTree.set(arrayStringRepresentation(),
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			currentBinaryTree.set(currentBinaryTree.root(), leftSonBinaryTree,
					currentBinaryTree.rightSubtree());
			currentPosition = 2 * currentPosition + 1;
			father.put(currentPosition, currentBinaryTree);
			currentBinaryTree = leftSonBinaryTree;
			callTreeXMLDrawer.startStep(step++);
			callTreeXMLDrawer.draw(getResource("ieBeforeFirstRecursiveCall"));
			if (isPseudocodeVisible)
				pseudocodeDrawerUtility.draw("", new int[] { 4, 5 });
			callTreeXMLDrawer.endStep();
		}

		private void ieBeforeSecondRecursiveCall(int b) {
			currentBinaryTree = father.get(currentPosition);
			currentPosition = (currentPosition - 1) / 2;
			BinaryTree<StringInformation> rightSonBinaryTree = new BinaryTree<StringInformation>(
					new StringInformation());
			rightSonBinaryTree.set(arrayStringRepresentation(),
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			currentBinaryTree.set(currentBinaryTree.root(), currentBinaryTree
					.leftSubtree(), rightSonBinaryTree);
			currentPosition = 2 * currentPosition + 2;
			father.put(currentPosition, currentBinaryTree);
			currentBinaryTree = rightSonBinaryTree;
			callTreeXMLDrawer.startStep(step++);
			callTreeXMLDrawer.draw(getResource("ieBeforeSecondRecursiveCall"));
			if (isPseudocodeVisible)
				pseudocodeDrawerUtility.draw("", new int[] { 6, 7 });
			callTreeXMLDrawer.endStep();
		}

		void ieEnd() {
			callTreeXMLDrawer.startStep(step++);
			callTreeXMLDrawer.draw(getResource("ieEnd"));
			callTreeXMLDrawer.endStep();
			callTreeXMLDrawer.endXMLFile();
		}

		void ieFunctionStart() {
			callTreeXMLDrawer.startStep(step++);
			callTreeXMLDrawer.draw(new Integer[] { currentPosition }, color,
					getResource("ieFunctionStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawerUtility.draw("", new int[] { 0 });
			callTreeXMLDrawer.endStep();
		}

		private void ieOneGenerationFinished() {
			callTreeXMLDrawer.startStep(step++);
			callTreeXMLDrawer.draw(new Integer[] { currentPosition }, color,
					getResource("ieOneGenerationFinished"));
			if (isPseudocodeVisible)
				pseudocodeDrawerUtility.draw("", new int[] { 2 });
			callTreeXMLDrawer.endStep();
		}

		void ieStart() {
			callTreeXMLDrawer.startXMLFile(getResource("algorithmName"));
			callTreeXMLDrawer.startStep(step++);
			callTreeXMLDrawer.draw(getResource("ieStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawerUtility.draw("", null);
			callTreeXMLDrawer.endStep();
		}

		void init() {
			callTree = new BinaryTree<StringInformation>(
					new StringInformation());
			callTree.set(arrayStringRepresentation(),
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			callTreeXMLDrawer = new BinaryTreeXMLDrawerUtility<StringInformation>(
					callTree, getResource("callTreeTitle"), getOutputStream());
			callTreeXMLDrawer.setOriginX(Integer
					.parseInt(getResource("callTreeXMLDrawerOriginX")));
			callTreeXMLDrawer.setOriginY(Integer
					.parseInt(getResource("callTreeXMLDrawerOriginY")));
			callTreeXMLDrawer.setDefaultFont(Font
					.decode(getResource("callTreeFont")));
			callTreeXMLDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("callTreeXMLNodeHeight")));
			callTreeXMLDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("callTreeXMLNodeWidth")));
			currentBinaryTree = callTree;
			currentPosition = 0;
			father = new HashMap<Integer, BinaryTree<StringInformation>>();
			pseudocodeDrawerUtility = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setCallTreeDifferentialDrawArrays();
			step = 0;
		}

		private void setCallTreeDifferentialDrawArrays() {
			color = new String[] { getResource("fatherColor"),
					getResource("sonColor"), getResource("sonColor") };
		}

	}

	private Array<StringInformation> a;

	private int n;

	private VisualBinaryStringGeneration vbsg;

	private void binaryStringGeneration(int b) {
		vbsg.ieFunctionStart();
		if (b == 0) {
			vbsg.ieOneGenerationFinished();
		} else {
			a.setAt(new StringInformation("0"), b - 1);
			vbsg.ieBeforeFirstRecursiveCall(b - 1);
			binaryStringGeneration(b - 1);
			a.setAt(new StringInformation("1"), b - 1);
			vbsg.ieBeforeSecondRecursiveCall(b - 1);
			binaryStringGeneration(b - 1);
			a.setAt(new StringInformation("X"), b - 1);
			vbsg.ieAfterSecondRecursiveCall();
		}
	}

	public void execute(String visFile) {
		vbsg = new VisualBinaryStringGeneration(visFile);
		if (readInput(visFile)) {
			initArray();
			start();
			MessageUtility.algorithmTerminated(vbsg.getResource("algorithmName"));
		}
	}

	private void initArray() {
		a = new Array<StringInformation>(n, new StringInformation());
		for (int i = 0; i < n; i++) {
			a.setAt(new StringInformation("X"), i);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput(String visFile) {
		Array<IntInformation> input;
		InputLoader inputLoader = new InputLoader(vbsg.getAlgorithmPath(), vbsg
				.getResource("algorithmFileName"));
		input = (Array) inputLoader.load("Array", vbsg
				.getResource("selectInputMessage"));
		if (input == null) {
			return false;
		}
		n = input.elementAt(0).intValue();
		if (n <= 0) {
			return false;
		}
		return true;
	}

	private void start() {
		vbsg.init();
		vbsg.ieStart();
		binaryStringGeneration(n);
		vbsg.ieEnd();
	}
}
