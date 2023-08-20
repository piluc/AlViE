package org.algoritmica.alvie.algorithms;

import java.awt.Font;
import java.util.Stack;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.BinaryTree;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.DoubleInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.BinaryTreeXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class FastFourierTransform {
	private class Complex {
		private final double re;
		private final double im;

		public Complex(double real, double imag) {
			re = real;
			im = imag;
		}

		public Complex minus(Complex b) {
			Complex a = this;
			double real = a.re - b.re;
			double imag = a.im - b.im;
			return new Complex(real, imag);
		}

		public Complex plus(Complex b) {
			Complex a = this;
			double real = a.re + b.re;
			double imag = a.im + b.im;
			return new Complex(real, imag);
		}

		public Complex times(Complex b) {
			Complex a = this;
			double real = a.re * b.re - a.im * b.im;
			double imag = a.re * b.im + a.im * b.re;
			return new Complex(real, imag);
		}

		public String toString() {
			if (im == 0)
				return Math.round(re * 10) / 10.0 + "";
			if (re == 0)
				return "i" + Math.round(im * 10) / 10.0;
			if (im < 0)
				return Math.round(re * 10) / 10.0 + "-i"
						+ Math.round((-im) * 10) / 10.0;
			return Math.round(re * 10) / 10.0 + "+i" + Math.round(im * 10)
					/ 10.0;
		}
	}

	private class VisualFastFourierTransform extends VisualInnerClass {
		private ArrayXMLDrawerUtility<DoubleInformation> xDrawer;

		private BinaryTree<StringInformation> callTree;

		private Stack<BinaryTree<StringInformation>> callStack;

		private BinaryTreeXMLDrawerUtility<StringInformation> callTreeDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private String[] color;

		private int fatherId, sonId;

		private boolean isPseudocodeVisible;

		VisualFastFourierTransform(String visFile) {
			super("fastFourierTransform", visFile);
		}

		private String complexArrayToString(Complex[] c) {
			String r = "";
			for (int i = 0; i < c.length - 1; i++) {
				r = r + c[i].toString() + ";";
			}
			r = r + c[c.length - 1].toString();
			return r;
		}

		private String arrayToString(Array<DoubleInformation> d) {
			String r = "";
			for (int i = 0; i < d.length() - 1; i++) {
				r = r + d.elementAt(i).stringValue() + ";";
			}
			r = r + d.elementAt(d.length() - 1).stringValue();
			return r;
		}

		private void ieAfterFirstRecursiveCall() {
			sonId = fatherId;
			fatherId = (fatherId - 1) / 2;
			callTreeDrawer.startStep(step++);
			callTreeDrawer.draw(new Integer[] { fatherId, sonId }, color,
					getResource("ieAfterFirstRecursiveCall"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 5 });
			}
			callTreeDrawer.endStep();
			callStack.pop();
		}

		private void ieAfterSecondRecursiveCall() {
			sonId = fatherId;
			fatherId = (fatherId - 2) / 2;
			callTreeDrawer.startStep(step++);
			callTreeDrawer.draw(new Integer[] { fatherId, sonId }, color,
					getResource("ieAfterSecondRecursiveCall"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 8 });
			}
			callTreeDrawer.endStep();
			callStack.pop();
		}

		private void ieBeforeFirstInvocation() {
			callTree.set(new StringInformation(arrayToString(a)),
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			callStack.push(callTree);
			fatherId = 0;
			callTreeDrawer.startStep(step++);
			callTreeDrawer.draw(getResource("ieBeforeFirstInvocation"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 0 });
			}
			callTreeDrawer.endStep();
		}

		private void ieBeforeFirstRecursiveCall(Array<DoubleInformation> d) {
			BinaryTree<StringInformation> currentTree = callStack.pop();
			BinaryTree<StringInformation> leftSubtree = new BinaryTree<StringInformation>(
					new StringInformation());
			leftSubtree.set(new StringInformation(arrayToString(d)),
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			currentTree.set(currentTree.root(), leftSubtree,
					new BinaryTree<StringInformation>(new StringInformation()));
			callStack.push(currentTree);
			callStack.push(leftSubtree);
			sonId = 2 * fatherId + 1;
			callTreeDrawer.startStep(step++);
			callTreeDrawer.draw(new Integer[] { fatherId, sonId }, color,
					getResource("ieBeforeFirstRecursiveCall"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 3, 4 });
			}
			callTreeDrawer.endStep();
			fatherId = sonId;
		}

		private void ieBeforeReturn(Complex[] c) {
			BinaryTree<StringInformation> currentTree = callStack.pop();
			currentTree.set(new StringInformation(complexArrayToString(c)),
					currentTree.leftSubtree(), currentTree.rightSubtree());
			callStack.push(currentTree);
			callTreeDrawer.startStep(step++);
			callTreeDrawer.draw(new Integer[] { fatherId }, color,
					getResource("ieBeforeReturn"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 9, 10, 11, 12, 13 });
			}
			callTreeDrawer.endStep();
		}

		private void ieBeforeSecondRecursiveCall(Array<DoubleInformation> d) {
			BinaryTree<StringInformation> currentTree = callStack.pop();
			BinaryTree<StringInformation> rightSubtree = new BinaryTree<StringInformation>(
					new StringInformation());
			rightSubtree.set(new StringInformation(arrayToString(d)),
					new BinaryTree<StringInformation>(new StringInformation()),
					new BinaryTree<StringInformation>(new StringInformation()));
			currentTree.set(currentTree.root(), currentTree.leftSubtree(),
					rightSubtree);
			callStack.push(currentTree);
			callStack.push(rightSubtree);
			sonId = 2 * fatherId + 2;
			callTreeDrawer.startStep(step++);
			callTreeDrawer.draw(new Integer[] { fatherId, sonId }, color,
					getResource("ieBeforeSecondRecursiveCall"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 6, 7 });
			}
			callTreeDrawer.endStep();
			fatherId = sonId;
		}

		private void ieEnd() {
			xDrawer.startStep(step++);
			xDrawer.draw(getResource("ieEnd") + callTree.root().stringValue());
			xDrawer.endStep();
			xDrawer.endXMLFile();
		}

		private void ieSingleNumber() {
			callTreeDrawer.startStep(step++);
			callTreeDrawer.draw(new Integer[] { fatherId }, color,
					getResource("ieSingleNumber"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 2 });
			}
			callTreeDrawer.endStep();
		}

		private void ieStart() {
			xDrawer.startXMLFile(getResource("algorithmName"));
			xDrawer.startStep(step++);
			xDrawer.draw(getResource("ieStart"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", null);
			}
			xDrawer.endStep();
		}

		private void init() {
			xDrawer = new ArrayXMLDrawerUtility<DoubleInformation>(a,
					getResource("aTitle"), getOutputStream());
			xDrawer.setOriginX(Integer
					.parseInt(getResource("aXMLDrawerOriginX")));
			xDrawer.setOriginY(Integer
					.parseInt(getResource("aXMLDrawerOriginY")));
			xDrawer.setDefaultFont(Font.decode(getResource("aFont")));
			xDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("aHeight")));
			xDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("aWidth")));
			callTree = new BinaryTree<StringInformation>(
					new StringInformation());
			callStack = new Stack<BinaryTree<StringInformation>>();
			callTreeDrawer = new BinaryTreeXMLDrawerUtility<StringInformation>(
					callTree, getResource("treeName"), getOutputStream());
			callTreeDrawer.setOriginX(Integer
					.parseInt(getResource("treeXMLDrawerOriginX")));
			callTreeDrawer.setOriginY(Integer
					.parseInt(getResource("treeXMLDrawerOriginY")));
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

		private void setDifferentialDrawArrays() {
			color = new String[] { getResource("fatherColor"),
					getResource("sonColor") };
		}
	}

	private Array<DoubleInformation> a;

	private VisualFastFourierTransform vfft;

	public void execute(String visFile) {
		vfft = new VisualFastFourierTransform(visFile);
		if (readInput()) {
			vfft.init();
			vfft.ieStart();
			vfft.ieBeforeFirstInvocation();
			fft(a);
			vfft.ieEnd();
			MessageUtility.algorithmTerminated(vfft.getResource("algorithmName"));
		}
	}

	public Complex[] fft(Array<DoubleInformation> x) {
		int n = x.length();
		if (n == 1) {
			vfft.ieSingleNumber();
			return new Complex[] { new Complex(x.elementAt(0).doubleValue(),
					0.0) };
		}
		Array<DoubleInformation> half = new Array<DoubleInformation>(n / 2,
				new DoubleInformation());
		for (int k = 0; k < n / 2; k++) {
			half.setAt(x.elementAt(2 * k), k);
		}
		vfft.ieBeforeFirstRecursiveCall(half);
		Complex[] q = fft(half);
		vfft.ieAfterFirstRecursiveCall();
		for (int k = 0; k < n / 2; k++) {
			half.setAt(x.elementAt(2 * k + 1), k);
		}
		vfft.ieBeforeSecondRecursiveCall(half);
		Complex[] r = fft(half);
		vfft.ieAfterSecondRecursiveCall();
		Complex[] y = new Complex[n];
		for (int k = 0; k < n / 2; k++) {
			double kth = -2 * k * Math.PI / n;
			Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
			y[k] = q[k].plus(wk.times(r[k]));
			y[k + n / 2] = q[k].minus(wk.times(r[k]));
		}
		vfft.ieBeforeReturn(y);
		return y;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vfft.getAlgorithmPath(), vfft
				.getResource("algorithmFileName"));
		a = (Array) inputLoader.load("Array", vfft
				.getResource("selectInputMessage"));
		if (a == null) {
			return false;
		}
		int n = a.length();
		if (Math.pow(2, Math.log(n) / Math.log(2)) != n) {
			return false;
		}
		return true;
	}

}
