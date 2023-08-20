package org.algoritmica.alvie.algorithms;

import java.awt.Font;
import java.util.Collection;
import java.util.Stack;
import java.util.Vector;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.LongInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class FastIntegerProduct {
	private class VisualFastIntegerProduct extends VisualInnerClass {
		private ArrayXMLDrawerUtility<IntInformation> xDrawer;

		private ArrayXMLDrawerUtility<IntInformation> yDrawer;

		private ArrayXMLDrawerUtility<IntInformation> pDrawer;

		private Graph<StringInformation, LongInformation> recursiveCallGraph;

		private GraphXMLDrawerUtility<StringInformation, LongInformation> graphDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private String[] color;

		private int newNodeId;

		private int currentX, currentY;

		private int deltaX, deltaY, rootX, rootY;

		private Stack<Integer> callStack;

		private boolean isPseudocodeVisible;

		VisualFastIntegerProduct(String visFile) {
			super("fastIntegerProduct", visFile);
		}

		private void deleteSons(int id) {
			Collection<Integer> sons = recursiveCallGraph
					.getAdiacentNodeIds(id);
			Collection<Integer> toBeDeleted = new Vector<Integer>();
			for (int sonId : sons) {
				toBeDeleted.add(sonId);
			}
			for (int sonId : toBeDeleted) {
				recursiveCallGraph.removeNode(sonId);
			}
		}

		private void ieAfterFirstRecursiveCall(Array<IntInformation> p, int n) {
			currentX = currentX - deltaX;
			currentY = currentY + deltaY;
			int topId = callStack.pop();
			recursiveCallGraph.setNodeInformation(topId,
					intStringRepresentation(p));
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { callStack.peek(), topId }, color,
					getResource("ieAfterFirstRecursiveCall"));
			if (isPseudocodeVisible && n == a.length()-1) {
				pseudocodeDrawer.draw("", new int[] { 10 });
			}
			graphDrawer.endStep();
		}

		private void ieAfterSecondRecursiveCall(Array<IntInformation> p, int n) {
			currentX = currentX - deltaX;
			int topId = callStack.pop();
			recursiveCallGraph.setNodeInformation(topId,
					intStringRepresentation(p));
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { callStack.peek(), topId }, color,
					getResource("ieAfterSecondRecursiveCall"));
			if (isPseudocodeVisible && n == a.length()-1) {
				pseudocodeDrawer.draw("", new int[] { 13 });
			}
			graphDrawer.endStep();
		}

		private void ieAfterThirdRecursiveCall(Array<IntInformation> p, int n) {
			currentX = currentX - deltaX;
			currentY = currentY - deltaY;
			int topId = callStack.pop();
			recursiveCallGraph.setNodeInformation(topId,
					intStringRepresentation(p));
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { callStack.peek(), topId }, color,
					getResource("ieAfterThirdRecursiveCall"));
			if (isPseudocodeVisible && n == a.length()-1) {
				pseudocodeDrawer.draw("", new int[] { 15 });
			}
			graphDrawer.endStep();
		}

		private void ieBeforeFirstInvocation() {
			recursiveCallGraph = new Graph<StringInformation, LongInformation>(
					true, new StringInformation(), new LongInformation());
			graphDrawer = new GraphXMLDrawerUtility<StringInformation, LongInformation>(
					recursiveCallGraph, getResource("graphTitle"),
					getOutputStream());
			graphDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			graphDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			graphDrawer.setDefaultShape("Rectangular");
			graphDrawer.setDefaultFont(Font.decode(getResource("nodeFont")));
			graphDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("nodeHeight")));
			graphDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("nodeWidth")));
			newNodeId = 0;
			currentX = rootX;
			currentY = rootY;
			StringInformation nodeInformation = twoIntStringRepresentation(a, b);
			recursiveCallGraph.addNode(nodeInformation, newNodeId, currentX,
					currentY);
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieBeforeFirstInvocation"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 0 });
			}
			graphDrawer.endStep();
			setDifferentialDrawArrays();
			callStack = new Stack<Integer>();
			callStack.push(newNodeId);
		}

		private void ieBeforeFirstRecursiveCall(Array<IntInformation> x,
				Array<IntInformation> y, int n) {
			StringInformation nodeInformation = twoIntStringRepresentation(x, y);
			currentX = currentX + deltaX;
			currentY = currentY - deltaY;
			newNodeId = newNodeId + 1;
			recursiveCallGraph.addNode(nodeInformation, newNodeId, currentX,
					currentY);
			int topId = callStack.peek();
			recursiveCallGraph.newArc(topId, newNodeId);
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { topId, newNodeId }, color,
					getResource("ieBeforeFirstRecursiveCall"));
			if (isPseudocodeVisible && n == a.length()-1) {
				pseudocodeDrawer.draw("", new int[] { 5, 6, 7, 8, 9 });
			}
			graphDrawer.endStep();
			callStack.push(newNodeId);
		}

		private void ieBeforeProductComputation(int n,
				Array<IntInformation> a1, Array<IntInformation> a2,
				Array<IntInformation> a3) {
			long s1 = Long.parseLong(intStringRepresentation(a1).stringValue());
			long s2 = Long.parseLong(intStringRepresentation(a2).stringValue());
			long s3 = Long.parseLong(intStringRepresentation(a3).stringValue());
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { callStack.peek() }, color,
					getResource("ieBeforeProductComputation1")
							+ ((int) Math.pow(10, n)) + " (" + s1 + ")"
							+ getResource("ieBeforeProductComputation2")
							+ ((int) Math.pow(10, n / 2)) + " (" + s2 + ") "
							+ getResource("ieBeforeProductComputation3") + " ("
							+ s3 + ").");
			if (isPseudocodeVisible && n == a.length()-1) {
				pseudocodeDrawer.draw("", new int[] { 16, 17, 18, 19, 20, 21 });
			}
			graphDrawer.endStep();
		}

		private void ieBeforeReturn(Array<IntInformation> p, int n) {
			int topId = callStack.peek();
			deleteSons(topId);
			recursiveCallGraph.setNodeInformation(topId,
					intStringRepresentation(p));
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { topId }, color,
					getResource("ieBeforeReturn"));
			if (isPseudocodeVisible && n == a.length()-1) {
				pseudocodeDrawer.draw("", new int[] { 23, 24 });
			}
			graphDrawer.endStep();
		}

		private void ieBeforeSecondRecursiveCall(Array<IntInformation> x,
				Array<IntInformation> y, int n) {
			StringInformation nodeInformation = twoIntStringRepresentation(x, y);
			currentX = currentX + deltaX;
			newNodeId = newNodeId + 1;
			recursiveCallGraph.addNode(nodeInformation, newNodeId, currentX,
					currentY);
			int topId = callStack.peek();
			recursiveCallGraph.newArc(topId, newNodeId);
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { callStack.peek(), newNodeId },
					color, getResource("ieBeforeSecondRecursiveCall"));
			if (isPseudocodeVisible && n == a.length()-1) {
				pseudocodeDrawer.draw("", new int[] { 11, 12 });
			}
			graphDrawer.endStep();
			callStack.push(newNodeId);
		}

		private void ieBeforeThirdRecursiveCall(Array<IntInformation> x,
				Array<IntInformation> y, int n) {
			StringInformation nodeInformation = twoIntStringRepresentation(x, y);
			currentX = currentX + deltaX;
			currentY = currentY + deltaY;
			newNodeId = newNodeId + 1;
			recursiveCallGraph.addNode(nodeInformation, newNodeId, currentX,
					currentY);
			int topId = callStack.peek();
			recursiveCallGraph.newArc(topId, newNodeId);
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { callStack.peek(), newNodeId },
					color, getResource("ieBeforeThirdRecursiveCall"));
			if (isPseudocodeVisible && n == a.length()-1) {
				pseudocodeDrawer.draw("", new int[] { 14, 15 });
			}
			graphDrawer.endStep();
			callStack.push(newNodeId);
		}

		private void ieEnd() {
			pDrawer = new ArrayXMLDrawerUtility<IntInformation>(p,
					getResource("pTitle"), getOutputStream());
			pDrawer.setOriginX(Integer
					.parseInt(getResource("pXMLDrawerOriginX")));
			pDrawer.setOriginY(Integer
					.parseInt(getResource("pXMLDrawerOriginY")));
			pDrawer.setDefaultFont(Font.decode(getResource("pFont")));
			pDrawer.startStep(step++);
			xDrawer.draw(getResource("ieEnd"));
			yDrawer.draw("");
			pDrawer.draw("");
			pDrawer.endStep();
			pDrawer.endXMLFile();
		}

		private void ieStart() {
			xDrawer.startXMLFile(getResource("algorithmName"));
			xDrawer.startStep(step++);
			xDrawer.draw(getResource("ieStart"));
			yDrawer.draw("");
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", null);
			}
			xDrawer.endStep();
		}

		private void init() {
			xDrawer = new ArrayXMLDrawerUtility<IntInformation>(a,
					getResource("xTitle"), getOutputStream());
			xDrawer.setOriginX(Integer
					.parseInt(getResource("xXMLDrawerOriginX")));
			xDrawer.setOriginY(Integer
					.parseInt(getResource("xXMLDrawerOriginY")));
			xDrawer.setDefaultFont(Font.decode(getResource("xFont")));
			yDrawer = new ArrayXMLDrawerUtility<IntInformation>(b,
					getResource("yTitle"), getOutputStream());
			yDrawer.setOriginX(Integer
					.parseInt(getResource("yXMLDrawerOriginX")));
			yDrawer.setOriginY(Integer
					.parseInt(getResource("yXMLDrawerOriginY")));
			yDrawer.setDefaultFont(Font.decode(getResource("yFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			rootX = Integer.parseInt(getResource("rootX"));
			rootY = Integer.parseInt(getResource("rootY"));
			deltaX = Integer.parseInt(getResource("deltaX"));
			deltaY = Integer.parseInt(getResource("deltaY"));
			step = 0;
		}

		private StringInformation intStringRepresentation(
				Array<IntInformation> x) {
			int n = x.length();
			int i = 1;
			while ((i < n) && (x.elementAt(i).intValue() == 0)) {
				i = i + 1;
			}
			if (i == n) {
				return new StringInformation("0");
			}
			String stringRepresentation = x.elementAt(i).stringValue();
			i = i + 1;
			while (i < n) {
				stringRepresentation = stringRepresentation
						+ x.elementAt(i).stringValue();
				i = i + 1;
			}
			if (x.elementAt(0).intValue() < 0) {
				stringRepresentation = "-" + stringRepresentation;
			}
			return new StringInformation(stringRepresentation);
		}

		private void setDifferentialDrawArrays() {
			color = new String[] { getResource("fatherColor"),
					getResource("sonColor") };
		}

		private StringInformation twoIntStringRepresentation(
				Array<IntInformation> x, Array<IntInformation> y) {
			String stringRepresentation = "";
			if (x.elementAt(0).intValue() < 0) {
				stringRepresentation = "-";
			}
			int n = x.length();
			for (int i = 1; i < n; i++) {
				stringRepresentation = stringRepresentation
						+ x.elementAt(i).stringValue();
			}
			stringRepresentation = stringRepresentation + ";";
			if (y.elementAt(0).intValue() < 0) {
				stringRepresentation = stringRepresentation + "-";
			}
			n = y.length();
			for (int i = 1; i < n; i++) {
				stringRepresentation = stringRepresentation
						+ y.elementAt(i).stringValue();
			}
			return new StringInformation(stringRepresentation);
		}

	}

	private Array<IntInformation> p, a, b;

	private VisualFastIntegerProduct vfip;

	private void computeProduct() {
		vfip.init();
		vfip.ieStart();
		vfip.ieBeforeFirstInvocation();
		p = fastProduct(a, b, a.length() - 1);
		vfip.ieEnd();
	}

	public void execute(String visFile) {
		vfip = new VisualFastIntegerProduct(visFile);
		if (readInput()) {
			computeProduct();
			MessageUtility.algorithmTerminated(vfip.getResource("algorithmName"));
		}
	}

	private Array<IntInformation> extend(Array<IntInformation> x, int l) {
		int n = x.length() - 1;
		Array<IntInformation> result = new Array<IntInformation>(l + 1,
				new IntInformation());
		result.setAt(x.elementAt(0), 0);
		for (int i = 1; i <= l - n; i = i + 1) {
			result.setAt(new IntInformation(0), i);
		}
		for (int i = 1; i <= n; i = i + 1) {
			result.setAt(x.elementAt(i), i + (l - n));
		}
		return result;
	}

	private Array<IntInformation> fastProduct(Array<IntInformation> x,
			Array<IntInformation> y, int n) {
		Array<IntInformation> product = new Array<IntInformation>(2 * n + 1,
				new IntInformation());
		if (n == 1) {
			product.setAt(new IntInformation((x.elementAt(1).intValue() * y
					.elementAt(1).intValue()) / 10), 1);
			product.setAt(new IntInformation((x.elementAt(1).intValue() * y
					.elementAt(1).intValue()) % 10), 2);
		} else {
			Array<IntInformation> xs = new Array<IntInformation>(n / 2 + 1,
					new IntInformation());
			Array<IntInformation> xd = new Array<IntInformation>(n / 2 + 1,
					new IntInformation());
			Array<IntInformation> ys = new Array<IntInformation>(n / 2 + 1,
					new IntInformation());
			Array<IntInformation> yd = new Array<IntInformation>(n / 2 + 1,
					new IntInformation());
			xs.setAt(new IntInformation(1), 0);
			xd.setAt(new IntInformation(1), 0);
			ys.setAt(new IntInformation(1), 0);
			yd.setAt(new IntInformation(1), 0);
			for (int i = 1; i <= n / 2; i++) {
				xs.setAt(x.elementAt(i), i);
				ys.setAt(y.elementAt(i), i);
				xd.setAt(x.elementAt(i + n / 2), i);
				yd.setAt(y.elementAt(i + n / 2), i);
			}
			vfip.ieBeforeFirstRecursiveCall(xs, ys, n);
			Array<IntInformation> p1 = fastProduct(xs, ys, n / 2);
			vfip.ieAfterFirstRecursiveCall(p1, n);
			for (int i = 0; i <= n; i++) {
				product.setAt(p1.elementAt(i), i);
				product.setAt(new IntInformation(0), i + n);
			}
			vfip.ieBeforeSecondRecursiveCall(xd, yd, n);
			Array<IntInformation> p2 = fastProduct(xd, yd, n / 2);
			vfip.ieAfterSecondRecursiveCall(p2, n);
			xd.setAt(new IntInformation(-1), 0);
			yd.setAt(new IntInformation(-1), 0);
			vfip.ieBeforeThirdRecursiveCall(sum(xs, xd, n / 2), sum(ys, yd,
					n / 2), n);
			Array<IntInformation> p3 = fastProduct(sum(xs, xd, n / 2), sum(ys,
					yd, n / 2), n / 2);
			vfip.ieAfterThirdRecursiveCall(p3, n);
			p3.setAt(new IntInformation(-p3.elementAt(0).intValue()), 0);
			Array<IntInformation> add = sum(sum(p1, p2, 2 * n), extend(p3,
					2 * n), 2 * n);
			Array<IntInformation> partial = new Array<IntInformation>(
					2 * n + 1, new IntInformation());
			for (int i = 1; i <= 3 * (n / 2); i++) {
				partial.setAt(add.elementAt(i + n / 2), i);
				partial.setAt(new IntInformation(0), i + n / 2);
			}
			partial.setAt(add.elementAt(0), 0);
			vfip.ieBeforeProductComputation(n, product, partial, p2);
			product = sum(sum(product, partial, 2 * n), extend(p2, 2 * n),
					2 * n);
		}
		product.setAt(new IntInformation(x.elementAt(0).intValue()
				* y.elementAt(0).intValue()), 0);
		vfip.ieBeforeReturn(product, n);
		return product;
	}

	private boolean isGreaterThan(Array<IntInformation> x,
			Array<IntInformation> y) {
		int i = 1;
		while ((i < x.length()) && (x.elementAt(i).equals(y.elementAt(i)))) {
			i = i + 1;
		}
		if ((i < x.length()) && (x.elementAt(i).isGreaterThan(y.elementAt(i)))) {
			return true;
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vfip.getAlgorithmPath(), vfip
				.getResource("algorithmFileName"));
		a = (Array) inputLoader.load("Array", vfip
				.getResource("selectInputMessage1"));
		if (a == null) {
			return false;
		}
		b = (Array) inputLoader.load("Array", vfip
				.getResource("selectInputMessage2"));
		if (b == null) {
			return false;
		}
		return true;
	}

	private Array<IntInformation> sum(Array<IntInformation> x,
			Array<IntInformation> y, int l) {
		Array<IntInformation> result = new Array<IntInformation>(l + 1,
				new IntInformation());
		int iXY = x.length() - 1;
		int iResult = l;
		int carry = 0;
		int digitSum;
		if (x.elementAt(0).isEqual(y.elementAt(0))) {
			while (iXY > 0) {
				digitSum = x.elementAt(iXY).intValue()
						+ y.elementAt(iXY).intValue() + carry;
				result.setAt(new IntInformation(digitSum % 10), iResult);
				carry = digitSum / 10;
				iXY = iXY - 1;
				iResult = iResult - 1;
			}
			result.setAt(new IntInformation(carry), iResult);
			iResult = iResult - 1;
			while (iResult > 0) {
				result.setAt(new IntInformation(0), iResult);
				iResult = iResult - 1;
			}
			result.setAt(x.elementAt(0), 0);
		} else {
			Array<IntInformation> firstOperand, secondOperand;
			boolean yGreaterThanX = isGreaterThan(y, x);
			int sign;
			if (!yGreaterThanX) {
				firstOperand = x;
				secondOperand = y;
				if (x.elementAt(0).intValue() > 0) {
					sign = 1;
				} else {
					sign = -1;
				}
			} else {
				firstOperand = y;
				secondOperand = x;
				if (y.elementAt(0).intValue() > 0) {
					sign = 1;
				} else {
					sign = -1;
				}
			}
			while (iXY > 0) {
				digitSum = firstOperand.elementAt(iXY).intValue()
						- secondOperand.elementAt(iXY).intValue() + carry;
				if (digitSum >= 0) {
					result.setAt(new IntInformation(digitSum), iResult);
					carry = 0;
				} else {
					result.setAt(new IntInformation(10 + digitSum), iResult);
					carry = -1;
				}
				iXY = iXY - 1;
				iResult = iResult - 1;
			}
			while (iResult > 0) {
				result.setAt(new IntInformation(0), iResult);
				iResult = iResult - 1;
			}
			result.setAt(new IntInformation(sign), 0);
		}
		return result;
	}
}
