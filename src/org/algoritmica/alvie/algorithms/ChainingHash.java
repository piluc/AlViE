package org.algoritmica.alvie.algorithms;

import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.ComparableI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class ChainingHash {
	private class DoubleListInformation<I extends ComparableI<I>> implements
			InformationI {
		class ListNode<C extends ComparableI<C>> {
			ListNode<C> pred, succ;
			I info;

			public I getInfo() {
				return info;
			}

			public ListNode<C> getPred() {
				return pred;
			}

			public ListNode<C> getSucc() {
				return succ;
			}
		}

		ListNode<I> end, begin;

		int length;

		void delete(I k) {
			ListNode<I> p = search(k);
			if (p != null) {
				if (length == 1) {
					end = null;
					begin = null;
				} else if (p.pred == null) {
					p.succ.pred = null;
					begin = p.succ;
				} else if (p.succ == null) {
					p.pred.succ = null;
					end = p.pred;
				} else {
					p.succ.pred = p.pred;
					p.pred.succ = p.succ;
				}
			}
			length = length - 1;
		}

		ListNode<I> getBegin() {
			return begin;
		}

		int getLength() {
			return length;
		}

		void insertTail(I e) {
			ListNode<I> p = new ListNode<I>();
			p.info = e;
			if (length == 0) {
				p.succ = null;
				p.pred = null;
				begin = p;
				end = p;
			} else {
				p.succ = null;
				p.pred = end;
				end.succ = p;
				end = p;
			}
			length = length + 1;
		}

		ListNode<I> search(I k) {
			ListNode<I> p = begin;
			while ((p != null) && (!p.info.isEqual(k))) {
				p = p.succ;
			}
			return p;
		}

		public String stringValue() {
			if (length == 0) {
				return "[]";
			}
			String rst = "";
			ListNode<I> ln = begin;
			while (ln != null) {
				rst = rst + "[" + ln.info.stringValue() + "]<->";
				ln = ln.succ;
			}
			return rst.substring(0, rst.length() - 3);
		}
	}

	private class VisualChainingHash extends VisualInnerClass {
		private Graph<IntInformation, StringInformation> graph;

		private GraphXMLDrawerUtility<IntInformation, StringInformation> graphDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private String[] color;

		private Integer currentElementId, currentRowId;

		private HashMap<Integer, Integer> infoId;

		private VisualChainingHash(String visFile) {
			super("chainingHash", visFile);
		}
		
		private void init() {
			createGraph();
			graphDrawer = new GraphXMLDrawerUtility<IntInformation, StringInformation>(
					graph, getResource("graphName"), getOutputStream());
			graphDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			graphDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			graphDrawer.setDefaultShape(getResource("graphNodeShape"));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setDifferentialDrawArrays();
		}

		private void createGraph() {
			infoId = new HashMap<Integer, Integer>();
			graph = new Graph<IntInformation, StringInformation>(false,
					new IntInformation(), new StringInformation());
			int x = Integer.parseInt(getResource("hashTableOriginX"));
			int y = Integer.parseInt(getResource("hashTableOriginY"));
			int deltaX = Integer.parseInt(getResource("hashTableDeltaX"));
			int deltaY = Integer.parseInt(getResource("hashTableDeltaY"));
			DoubleListInformation<IntInformation> list;
			DoubleListInformation<IntInformation>.ListNode<IntInformation> cursor;
			Integer id = 0;
			for (int i = 0; i < numberRows; i++) {
				graph.addNode(new IntInformation(i), id, x, y);
				id = id + 1;
				x = x + deltaX;
				list = hashTable.elementAt(i);
				cursor = list.getBegin();
				while (cursor != null) {
					graph.addNode(cursor.getInfo(), id, x, y);
					infoId.put(cursor.getInfo().intValue(), id);
					if (cursor.getPred() != null) {
						graph.newArc(id, id - 1);
					}
					id = id + 1;
					x = x + deltaX;
					cursor = cursor.getSucc();
				}
				x = Integer.parseInt(getResource("hashTableOriginX"));
				y = y + deltaY;
			}
		}

		private void ieDeleteRowSelected(int h) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { currentRowId, currentElementId },
					color, getResource("ieDeleteRowSelected"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 11, 12 });
			graphDrawer.endStep();
			currentElementId = currentRowId + 1;
		}

		private void ieDeletionBegin(IntInformation v) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieDeletionBegin") + v.intValue());
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 9 });
			graphDrawer.endStep();
		}

		private void ieDeletionEnd(IntInformation v) {
			createGraph();
			graphDrawer.setDataStructure(graph);
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieDeletionEnd") + v.intValue());
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 13 });
			graphDrawer.endStep();
		}

		private void ieEnd() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieEnd"));
			graphDrawer.endStep();
			graphDrawer.endXMLFile();
		}

		private void ieInsertionBegin(IntInformation v) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieInsertionBegin") + v.intValue());
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 4 });
			graphDrawer.endStep();
		}

		private void ieInsertionEnd(IntInformation v) {
			createGraph();
			graphDrawer.setDataStructure(graph);
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieInsertionEnd") + v.intValue());
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 8 });
			graphDrawer.endStep();
		}

		private void ieInsertRowSelected(int h) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { currentRowId }, color,
					getResource("ieInsertRowSelected"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6, 7 });
			graphDrawer.endStep();
		}

		private void ieKeyFound(
				DoubleListInformation<IntInformation>.ListNode<IntInformation> p) {
			currentElementId = infoId.get(p.getInfo().intValue());
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { currentRowId, currentElementId },
					color, getResource("ieKeyFound"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 2, 3 });
			graphDrawer.endStep();
		}

		private void ieKeyNotFound() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { currentRowId }, color,
					getResource("ieKeyNotFound"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 2, 3 });
			graphDrawer.endStep();
		}

		private void ieSearchBegin(IntInformation v) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieSearchBegin") + v.intValue());
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 0 });
			graphDrawer.endStep();
		}

		private void ieSearchRowSelected(int h) {
			currentRowId = 0;
			for (int i = 0; i < h; i++) {
				currentRowId = currentRowId + 1
						+ hashTable.elementAt(i).getLength();
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { currentRowId }, color,
					getResource("ieSearchRowSelected"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1 });
			graphDrawer.endStep();
			currentElementId = currentRowId + 1;
		}

		private void ieStart() {
			graphDrawer.startXMLFile(getResource("algorithmName"));
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieStart") + numberRows);
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			graphDrawer.endStep();
		}

		private void setDifferentialDrawArrays() {
			color = new String[] { getResource("currentRowColor"),
					getResource("currentNodeColor") };
		}
	}

	private Array<DoubleListInformation<IntInformation>> hashTable;

	private int numberRows;

	private Array<StringInformation> a;

	private VisualChainingHash vch;

	private void delete(IntInformation k) {
		if (search(k) != null) {
			int h = hash(k.intValue());
			vch.ieDeleteRowSelected(h);
			hashTable.elementAt(h).delete(k);
		}
	}

	public void execute(String visFile) {
		vch = new VisualChainingHash(visFile);
		if (readInput()) {
			vch.init();
			vch.ieStart();
			IntInformation value;
			for (int i = 0; i < a.length() - 1; i++) {
				if (a.elementAt(i).stringValue().charAt(0) == '+') {
					value = new IntInformation(Integer.parseInt(a.elementAt(i)
							.stringValue().substring(1)));
					vch.ieInsertionBegin(value);
					insert(value);
					vch.ieInsertionEnd(value);
				} else if (a.elementAt(i).stringValue().charAt(0) == '-') {
					value = new IntInformation(Integer.parseInt(a.elementAt(i)
							.stringValue().substring(1)));
					vch.ieDeletionBegin(value);
					delete(value);
					vch.ieDeletionEnd(value);
				} else {
					value = new IntInformation(Integer.parseInt(a.elementAt(i)
							.stringValue()));
					search(value);
				}
			}
			vch.ieEnd();
			MessageUtility.algorithmTerminated(vch.getResource("algorithmName"));
		}
	}

	private int hash(int k) {
		return k % numberRows;
	}

	private void insert(IntInformation k) {
		if (search(k) == null) {
			int h = hash(k.intValue());
			vch.ieInsertRowSelected(h);
			hashTable.elementAt(h).insertTail(k);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vch.getAlgorithmPath(), vch
				.getResource("algorithmFileName"));
		a = (Array) il.load("Array", vch.getResource("selectInputMessage"));
		if (a == null) {
			return false;
		}
		numberRows = Integer
				.parseInt(a.elementAt(a.length() - 1).stringValue());
		hashTable = new Array<DoubleListInformation<IntInformation>>(
				numberRows, new DoubleListInformation<IntInformation>());
		for (int i = 0; i < numberRows; i++) {
			hashTable.setAt(new DoubleListInformation<IntInformation>(), i);
		}
		return true;
	}

	private IntInformation search(IntInformation k) {
		vch.ieSearchBegin(k);
		int h = hash(k.intValue());
		vch.ieSearchRowSelected(h);
		DoubleListInformation<IntInformation>.ListNode<IntInformation> p = hashTable
				.elementAt(h).search(k);
		if (p != null) {
			vch.ieKeyFound(p);
			return p.getInfo();
		} else {
			vch.ieKeyNotFound();
			return null;
		}
	}
}
