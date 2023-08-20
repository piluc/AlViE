package org.algoritmica.alvie.algorithms;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.BinaryTree;
import org.algoritmica.alvie.datastructure.BinaryTreeI;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.datastructure.Queue;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.BinaryTreeXMLDrawerUtility;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class Huffman {
	private class VisualHuffman extends VisualInnerClass {
		private Matrix<StringInformation> frequency;

		private Array<IntInformation> qf, qs;

		private MatrixXMLDrawerUtility<StringInformation> frequencyDrawer;

		private BinaryTreeXMLDrawerUtility<IntInformation>[] treeDrawer;

		private ArrayXMLDrawerUtility<IntInformation> fqDrawer, sqDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private Integer[] qfIndex, qsIndex;

		private Integer[][] treeIndex;

		private String[] color = { getResource("minimumColor"),
				getResource("minimumColor") };

		VisualHuffman(String visFile) {
			super("huffman", visFile);
		}

		private void computeCoding(BinaryTreeI<IntInformation> t, String s) {
			if (t.size() == 1) {
				IntInformation r = t.root();
				for (int i = 0; i < n; i++) {
					if (a.elementAt(i).isEqual(r)
							&& frequency.elementAt(2, i).isEqual(
									new StringInformation("X"))) {
						frequency.setAt(new StringInformation(s), 2, i);
						break;
					}
				}
			} else {
				computeCoding(t.leftSubtree(), s + "0");
				computeCoding(t.rightSubtree(), s + "1");
			}
		}

		private void createCoding() {
			frequency = new Matrix<StringInformation>(3, a.length(),
					new StringInformation(" "));
			for (int i = 0; i < frequency.width(); i++) {
				frequency.setAt(new StringInformation("S" + i), 0, i);
				frequency.setAt(new StringInformation(a.elementAt(i)
						.stringValue()), 1, i);
				frequency.setAt(new StringInformation("X"), 2, i);
			}
			frequencyDrawer = new MatrixXMLDrawerUtility<StringInformation>(
					frequency, getResource("frequencyName"), getOutputStream());
			frequencyDrawer.setOriginX(Integer
					.parseInt(getResource("frequencyX")));
			frequencyDrawer.setOriginY(Integer
					.parseInt(getResource("frequencyY")));
			frequencyDrawer.setDefaultColor(new Color(Integer.parseInt(
					getResource("frequencyColor"), 16)));
			frequencyDrawer.setDefaultFont(Font
					.decode(getResource("dataStructureFont")));
			frequencyDrawer.setDefaultShape("Rectangular");
			computeCoding(trees.get(sq.first().intValue()), "");
		}

		private void createQueues() {
			qf = new Array<IntInformation>(fq.size(), new IntInformation(0));
			qs = new Array<IntInformation>(sq.size(), new IntInformation(0));
			int fn = fq.size();
			for (int i = 0; i < fn; i++) {
				qf.setAt(trees.get(fq.first().intValue()).root(), i);
				fq.enqueue(fq.first());
				fq.dequeue();
			}
			int sn = sq.size();
			for (int i = 0; i < sn; i++) {
				qs.setAt(trees.get(sq.first().intValue()).root(), i);
				sq.enqueue(sq.first());
				sq.dequeue();
			}
			fqDrawer = new ArrayXMLDrawerUtility<IntInformation>(qf,
					getResource("fqName"), getOutputStream());
			fqDrawer.setOriginX(Integer.parseInt(getResource("fqX")));
			fqDrawer.setOriginY(Integer.parseInt(getResource("fqY")));
			fqDrawer.setDefaultColor(new Color(Integer.parseInt(
					getResource("fqColor"), 16)));
			fqDrawer.setDefaultFont(Font
					.decode(getResource("dataStructureFont")));
			fqDrawer.setDefaultShape("Rectangular");
			sqDrawer = new ArrayXMLDrawerUtility<IntInformation>(qs,
					getResource("sqName"), getOutputStream());
			sqDrawer.setOriginX(Integer.parseInt(getResource("sqX")));
			sqDrawer.setOriginY(Integer.parseInt(getResource("sqY")));
			sqDrawer.setDefaultColor(new Color(Integer.parseInt(
					getResource("sqColor"), 16)));
			sqDrawer.setDefaultFont(Font
					.decode(getResource("dataStructureFont")));
			sqDrawer.setDefaultShape("Rectangular");
		}

		@SuppressWarnings("unchecked")
		private void createTreeDrawers() {
			treeDrawer = (BinaryTreeXMLDrawerUtility<IntInformation>[]) new BinaryTreeXMLDrawerUtility[fq
					.size()
					+ sq.size()];
			treeIndex = new Integer[treeDrawer.length][];
			int fn = fq.size();
			int pn = 0;
			int dx = Integer.parseInt(getResource("treeX"));
			for (int i = 0; i < fn; i++) {
				treeDrawer[i] = new BinaryTreeXMLDrawerUtility<IntInformation>(
						trees.get(fq.first().intValue()),
						getResource("treeName") + " " + i, getOutputStream());
				if (i > 0) {
					dx = dx + (1 + (int) (Math.ceil(Math.log(pn))))
							* Integer.parseInt(getResource("treeWidth"));
				}
				pn = trees.get(fq.first().intValue()).size();
				treeIndex[i] = null;
				fq.enqueue(fq.first());
				fq.dequeue();
				treeDrawer[i].setOriginX(dx);
				treeDrawer[i]
						.setOriginY(Integer.parseInt(getResource("treeY")));
				treeDrawer[i].setDefaultColor(new Color(Integer.parseInt(
						getResource("treeColor"), 16)));
				treeDrawer[i].setDefaultFont(Font
						.decode(getResource("dataStructureFont")));
				treeDrawer[i].setDefaultShape("Elliptical");
			}
			int sn = sq.size();
			for (int i = 0; i < sn; i++) {
				treeDrawer[fn + i] = new BinaryTreeXMLDrawerUtility<IntInformation>(
						trees.get(sq.first().intValue()),
						getResource("treeName") + " " + (fn + i),
						getOutputStream());
				if (fn + i > 0) {
					dx = dx + (1 + (int) (Math.ceil(Math.log(pn))))
							* Integer.parseInt(getResource("treeWidth"));
				}
				pn = trees.get(sq.first().intValue()).size();
				treeIndex[fn + i] = null;
				sq.enqueue(sq.first());
				sq.dequeue();
				treeDrawer[fn + i].setOriginX(dx);
				treeDrawer[fn + i].setOriginY(Integer
						.parseInt(getResource("treeY")));
				treeDrawer[fn + i].setDefaultColor(new Color(Integer.parseInt(
						getResource("treeColor"), 16)));
				treeDrawer[fn + i].setDefaultFont(Font
						.decode(getResource("dataStructureFont")));
				treeDrawer[fn + i].setDefaultShape("Elliptical");
			}
		}

		private void ieEnd() {
			createTreeDrawers();
			createCoding();
			frequencyDrawer.startStep(step++);
			frequencyDrawer.draw(getResource("ieEnd"));
			treeDrawer[0].draw("");
			frequencyDrawer.endStep();
			frequencyDrawer.endXMLFile();
		}

		private void ieFirstQueueInitialized() {
			createQueues();
			frequencyDrawer.startStep(step++);
			frequencyDrawer.draw(getResource("ieFirstQueueInitialized"));
			fqDrawer.draw("");
			for (int i = 0; i < treeDrawer.length; i++) {
				treeDrawer[i].draw("");
			}
			pseudocodeDrawer.draw("", new int[] { 1 });
			frequencyDrawer.endStep();
		}

		private void ieLeavesCreated() {
			createTreeDrawers();
			frequencyDrawer.startStep(step++);
			frequencyDrawer.draw(getResource("ieLeavesCreated"));
			for (int i = 0; i < treeDrawer.length; i++) {
				treeDrawer[i].draw("");
			}
			pseudocodeDrawer.draw("", new int[] { 0 });
			frequencyDrawer.endStep();
		}

		private void ieNewNodeEnqueued() {
			createQueues();
			createTreeDrawers();
			frequencyDrawer.startStep(step++);
			frequencyDrawer.draw(getResource("ieNewNodeEnqueued"));
			if (qf.length() > 0) {
				fqDrawer.draw("");
			}
			if (qs.length() > 0) {
				sqDrawer.draw("");
			}
			for (int i = 0; i < treeDrawer.length; i++) {
				treeDrawer[i].draw("");
			}
			pseudocodeDrawer.draw("", new int[] { 6 });
			frequencyDrawer.endStep();
		}

		private void ieNewTreeCreated() {
			createTreeDrawers();
			frequencyDrawer.startStep(step++);
			frequencyDrawer.draw(getResource("ieNewTreeCreated"));
			if (qf.length() > 0) {
				fqDrawer.draw("");
			}
			if (qs.length() > 0) {
				sqDrawer.draw("");
			}
			for (int i = 0; i < treeDrawer.length; i++) {
				treeDrawer[i].draw("");
			}
			pseudocodeDrawer.draw("", new int[] { 4, 5 });
			frequencyDrawer.endStep();
		}

		private void ieNodesHaveBeenDequeued() {
			createQueues();
		}

		private void ieNodesMustBeDequeued() {
			frequencyDrawer.startStep(step++);
			frequencyDrawer.draw(getResource("ieNodesMustBeDequeued"));
			if (qf.length() > 0) {
				fqDrawer.draw(qfIndex, color, "");
			}
			if (qs.length() > 0) {
				sqDrawer.draw(qsIndex, color, "");
			}
			for (int i = 0; i < treeDrawer.length; i++) {
					treeDrawer[i].draw(treeIndex[i], color, "");
			}
			pseudocodeDrawer.draw("", new int[] { 3 });
			frequencyDrawer.endStep();
		}

		private void ieOneNodeRemained() {
			frequencyDrawer.startStep(step++);
			frequencyDrawer.draw(getResource("ieOneNodeRemained"));
			sqDrawer.draw("");
			for (int i = 0; i < treeDrawer.length; i++) {
				treeDrawer[i].draw("");
			}
			pseudocodeDrawer.draw("", new int[] { 7 });
			frequencyDrawer.endStep();
		}

		private void ieStart() {
			frequency = new Matrix<StringInformation>(2, a.length(),
					new StringInformation(" "));
			for (int i = 0; i < frequency.width(); i++) {
				frequency.setAt(new StringInformation("S" + i), 0, i);
				frequency.setAt(new StringInformation(a.elementAt(i)
						.stringValue()), 1, i);
			}
			frequencyDrawer = new MatrixXMLDrawerUtility<StringInformation>(
					frequency, getResource("frequencyName"), getOutputStream());
			frequencyDrawer.setOriginX(Integer
					.parseInt(getResource("frequencyX")));
			frequencyDrawer.setOriginY(Integer
					.parseInt(getResource("frequencyY")));
			frequencyDrawer.setDefaultColor(new Color(Integer.parseInt(
					getResource("frequencyColor"), 16)));
			frequencyDrawer.setDefaultFont(Font
					.decode(getResource("dataStructureFont")));
			frequencyDrawer.setDefaultShape("Rectangular");
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			frequencyDrawer.startXMLFile(getResource("algorithmName"));
			frequencyDrawer.startStep(step++);
			frequencyDrawer.draw(getResource("ieStart"));
			pseudocodeDrawer.draw("", null);
			frequencyDrawer.endStep();
		}

		private void ieWhileIterationBegins() {
			createQueues();
			frequencyDrawer.startStep(step++);
			frequencyDrawer.draw(getResource("ieWhileIterationBegins"));
			if (qf.length() > 0) {
				fqDrawer.draw("");
			}
			if (qs.length() > 0) {
				sqDrawer.draw("");
			}
			treeIndex = new Integer[treeDrawer.length][];
			for (int i = 0; i < treeDrawer.length; i++) {
				treeDrawer[i].draw("");
				treeIndex[i] = null;
			}
			pseudocodeDrawer.draw("", new int[] { 2 });
			frequencyDrawer.endStep();
			if (sq.size() == 0) {
				qfIndex = new Integer[2];
				qfIndex[0] = 0;
				qfIndex[1] = 1;
				qsIndex = null;
				treeIndex[0] = new Integer[] { 0 };
				treeIndex[1] = new Integer[] { 0 };
			} else if (fq.size() == 0) {
				qsIndex = new Integer[2];
				qsIndex[0] = 0;
				qsIndex[1] = 1;
				qfIndex = null;
				treeIndex[0] = new Integer[] { 0 };
				treeIndex[1] = new Integer[] { 0 };
			} else {
				int fi = 0, si = 0;
				while (fi < fq.size() && si < sq.size() && fi + si < 2) {
					if (qf.elementAt(fi).isLessThan(qs.elementAt(si))) {
						fi = fi + 1;
					} else {
						si = si + 1;
					}
				}
				if (fi == 2) {
					qfIndex = new Integer[2];
					qfIndex[0] = 0;
					qfIndex[1] = 1;
					qsIndex = null;
					treeIndex[0] = new Integer[] { 0 };
					treeIndex[1] = new Integer[] { 0 };
				} else if (si == 2) {
					qsIndex = new Integer[2];
					qsIndex[0] = 0;
					qsIndex[1] = 1;
					qfIndex = null;
					treeIndex[fq.size() + 0] = new Integer[] { 0 };
					treeIndex[fq.size() + 1] = new Integer[] { 0 };
				} else {
					qfIndex = new Integer[1];
					qfIndex[0] = 0;
					qsIndex = new Integer[1];
					qsIndex[0] = 0;
					treeIndex[0] = new Integer[] { 0 };
					treeIndex[fq.size() + 0] = new Integer[] { 0 };
				}
			}
		}
	}

	private Array<IntInformation> a;

	private int n;

	HashMap<Integer, BinaryTree<IntInformation>> trees;

	Queue<IntInformation> fq, sq;

	private VisualHuffman vh;

	int id;

	public void execute(String visFile) {
		vh = new VisualHuffman(visFile);
		if (readInput()) {
			vh.ieStart();
			huffman();
			vh.ieEnd();
			MessageUtility.algorithmTerminated(vh.getResource("algorithmName"));
		} else {
			MessageUtility
					.errorMessage(
							"The input must be a sorted array of at most 9 and at least 2 integer numbers",
							"Input error", 1001);
		}
	}

	private void huffman() {
		trees = new HashMap<Integer, BinaryTree<IntInformation>>();
		fq = new Queue<IntInformation>(new IntInformation(0));
		sq = new Queue<IntInformation>(new IntInformation(0));
		for (id = 0; id < n; id++) {
			BinaryTree<IntInformation> t = new BinaryTree<IntInformation>(
					new IntInformation());
			t.set(a.elementAt(id), new BinaryTree<IntInformation>(
					new IntInformation()), new BinaryTree<IntInformation>(
					new IntInformation()));
			trees.put(id, t);
			fq.enqueue(new IntInformation(id));
		}
		vh.ieLeavesCreated();
		vh.ieFirstQueueInitialized();
		IntInformation fn, sn;
		BinaryTree<IntInformation> ft, st;
		while ((fq.size() + sq.size()) > 1) {
			vh.ieWhileIterationBegins();
			if (sq.size() == 0) {
				fn = fq.first();
				fq.dequeue();
				sn = fq.first();
				fq.dequeue();
			} else if (fq.size() == 0) {
				fn = sq.first();
				sq.dequeue();
				sn = sq.first();
				sq.dequeue();
			} else {
				if (trees.get(fq.first().intValue()).root().isLessThan(
						trees.get(sq.first().intValue()).root())) {
					fn = fq.first();
					fq.dequeue();
					if (fq.size() == 0) {
						sn = sq.first();
						sq.dequeue();
					} else {
						if (trees.get(fq.first().intValue()).root().isLessThan(
								trees.get(sq.first().intValue()).root())) {
							sn = fq.first();
							fq.dequeue();
						} else {
							sn = sq.first();
							sq.dequeue();
						}
					}
				} else {
					fn = sq.first();
					sq.dequeue();
					if (sq.size() == 0) {
						sn = fq.first();
						fq.dequeue();
					} else {
						if (trees.get(fq.first().intValue()).root().isLessThan(
								trees.get(sq.first().intValue()).root())) {
							sn = fq.first();
							fq.dequeue();
						} else {
							sn = sq.first();
							sq.dequeue();
						}
					}
				}
			}
			vh.ieNodesMustBeDequeued();
			vh.ieNodesHaveBeenDequeued();
			ft = trees.get(fn.intValue());
			st = trees.get(sn.intValue());
			trees.remove(fn.intValue());
			trees.remove(sn.intValue());
			BinaryTree<IntInformation> t = new BinaryTree<IntInformation>(
					new IntInformation());
			t.set(new IntInformation(ft.root().intValue()
					+ st.root().intValue()), ft, st);
			trees.put(id, t);
			sq.enqueue(new IntInformation(id++));
			vh.ieNewTreeCreated();
			vh.ieNewNodeEnqueued();
		}
		vh.ieOneNodeRemained();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vh.getAlgorithmPath(), vh
				.getResource("algorithmFileName"));
		a = (Array) inputLoader.load("Array", vh
				.getResource("selectInputMessage"));
		if (a == null)
			return false;
		n = a.length();
		if (n < 2 || n > 9) {
			return false;
		}
		if (a.elementAt(0).intValue() <= 0) {
			return false;
		}
		for (int i = 0; i < n - 1; i++) {
			if (a.elementAt(i + 1).isLessThan(a.elementAt(i))
					|| a.elementAt(i + 1).intValue() <= 0) {
				return false;
			}
		}
		return true;
	}
}
