package org.algoritmica.alvie.algorithms;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.InputUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class SkipList {
	class SLInformation implements InformationI {

		private int value;

		private SLInformation succ;

		private SLInformation down;

		public SLInformation(int value, SLInformation s, SLInformation d) {
			this.value = value;
			this.succ = s;
			this.down = d;
		}

		public String stringValue() {
			return "" + value;
		}
	}

	private int keyToBeSearched;

	private int numberOfKeys;

	private int maxKeyValue;

	private int minKeyValue;

	Collection<SLInformation> skipList;

	Array<IntInformation> element;

	VisualSkipList vsl;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vsl.getAlgorithmPath(), vsl.getResource("algorithmFileName"));
		Array<IntInformation> a = (Array) il.load("Array", vsl.getResource("selectInputMessage"));
		if (a == null || a.length() != 4) {
			return false;
		}
		numberOfKeys = a.elementAt(0).intValue();
		minKeyValue = a.elementAt(1).intValue();
		maxKeyValue = a.elementAt(2).intValue();
		keyToBeSearched = a.elementAt(3).intValue();
		skipList = new ArrayList<SLInformation>();
		SLInformation n0 = new SLInformation(maxKeyValue + 1, null, null);
		skipList.add(new SLInformation(minKeyValue - 1, n0, null));
		element = InputUtility.randomDistinctIntArray(numberOfKeys, minKeyValue, maxKeyValue);
		return true;
	}

	private void insert(IntInformation ii) {
		vsl.ieStartInsertion(ii);
		Object[] ha = skipList.toArray();
		int h = ha.length;
		SLInformation[] predecessor = new SLInformation[h];
		SLInformation p = (SLInformation) ha[h - 1];
		int i = h - 1;
		while (p != null) {
			while (p.succ.value <= ii.intValue()) {
				p = p.succ;
			}
			predecessor[i] = p;
			p = p.down;
			i = i - 1;
		}
		vsl.iePredecessorFound(predecessor, ii);
		int r = 1;
		double b = Math.random();
		while (b < 0.5) {
			r = r + 1;
			b = Math.random();
		}
		vsl.ieNumberOfCopiesDecided((r > h) ? h + 1 : r, ii);
		int l = 0;
		SLInformation n = new SLInformation(ii.intValue(), predecessor[l].succ, null);
		predecessor[l].succ = n;
		vsl.ieOneCopyInserted(n, predecessor[l], ii);
		for (i = 2; (i <= r) && (i <= h); i++) {
			l = l + 1;
			n = new SLInformation(ii.intValue(), predecessor[l].succ, n);
			predecessor[l].succ = n;
			vsl.ieOneCopyInserted(n, predecessor[l], ii);
		}
		if (r > h) {
			p = predecessor[h - 1];
			while (p.value <= maxKeyValue) {
				p = p.succ;
			}
			SLInformation n1 = new SLInformation(maxKeyValue + 1, null, p);
			SLInformation n2 = new SLInformation(ii.intValue(), n1, predecessor[h - 1].succ);
			SLInformation n3 = new SLInformation(minKeyValue - 1, n2, (SLInformation) ha[h - 1]);
			skipList.add(n3);
			vsl.ieOneNewListInserted(n3, n2, n1);
		}
		vsl.ieInsertionFinished(ii);
	}

	private void executeInsertions() {
		for (int i = 0; i < element.length(); i++) {
			insert(element.elementAt(i));
		}
	}

	private void search() {
		Object[] ha = skipList.toArray();
		SLInformation cursor = (SLInformation) ha[ha.length - 1], predecessor = cursor;
		vsl.ieStartSearch(cursor, keyToBeSearched);
		while (cursor != null) {
			while (cursor.succ.value <= keyToBeSearched) {
				cursor = cursor.succ;
				vsl.ieHorizontalStepDone(cursor);
			}
			predecessor = cursor;
			cursor = cursor.down;
			if (cursor != null) {
				vsl.ieVerticalStepDone(cursor);
			}
		}
		vsl.ieEndSearch(keyToBeSearched, predecessor.value == keyToBeSearched);
		vsl.ieEnd();
	}

	public void execute(String visFile) {
		vsl = new VisualSkipList(visFile);
		if (readInput()) {
			vsl.init();
			vsl.ieStart();
			executeInsertions();
			search();
			MessageUtility.algorithmTerminated(vsl.getResource("algorithmName"));
		}
	}

	class VisualSkipList extends VisualInnerClass {
		private Graph<StringInformation, StringInformation> graph;

		private GraphXMLDrawerUtility<StringInformation, StringInformation> graphDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private Integer[] id;

		private String[] color;

		private String[] font;

		private String[] shape;

		HashMap<Integer, Integer> map;

		HashMap<SLInformation, Integer> skipListIdMap;

		private int startX = 10;

		private int startY = 50;

		private int deltaX = 75;

		private int deltaY = 50;

		private boolean isPseudocodeVisible;

		public VisualSkipList(String visFile) {
			super("skipList", visFile);
		}

		void init() {
			deltaX = Integer.parseInt(getResource("deltaX"));
			deltaY = Integer.parseInt(getResource("deltaY"));
			startX = Integer.parseInt(getResource("startX"));
			startY = Integer.parseInt(getResource("startY"));
			createGraph();
			graphDrawer = new GraphXMLDrawerUtility<StringInformation, StringInformation>(graph,
					getResource("skipListName"), getOutputStream());
			graphDrawer.setOriginX(Integer.parseInt(getResource("graphXMLDrawerOriginX")));
			graphDrawer.setOriginY(Integer.parseInt(getResource("graphXMLDrawerOriginY")));
			graphDrawer.setDefaultFont(Font.decode(getResource("graphFont")));
			graphDrawer.setDefaultShape("Rectangular");
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean.parseBoolean(getResource("pseudocodeVisible"));
			step = 0;
		}

		void ieStart() {
			graphDrawer.startXMLFile(getResource("algorithmName"));
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieStart"));
			graphDrawer.endStep();
		}

		void ieStartInsertion(IntInformation ii) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieStartInsertion") + ii.intValue() + ".");
			graphDrawer.endStep();
		}

		void iePredecessorFound(SLInformation[] p, IntInformation ii) {
			createGraph();
			graphDrawer.setDataStructure(graph);
			Integer nodeId;
			int nodeIndex;
			for (int i = 0; i < p.length; i++) {
				nodeId = skipListIdMap.get(p[i]);
				nodeIndex = map.get(nodeId);
				color[nodeIndex] = getResource("predecessorColor");
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(id, color, getResource("iePredecessorFound") + ii.intValue() + ".");
			graphDrawer.endStep();
		}

		void ieNumberOfCopiesDecided(int r, IntInformation ii) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(id, color,
					getResource("ieNumberOfCopiesDecided1") + r + " "
							+ (r > 1 ? getResource("copies") : getResource("copy"))
							+ getResource("ieNumberOfCopiesDecided2") + ii.intValue() + ".");
			graphDrawer.endStep();
		}

		void ieOneCopyInserted(SLInformation n, SLInformation p, IntInformation ii) {
			createGraph();
			graphDrawer.setDataStructure(graph);
			Integer nodeId = skipListIdMap.get(p);
			int nodeIndex = map.get(nodeId);
			color[nodeIndex] = getResource("graphColor");
			nodeId = skipListIdMap.get(n);
			nodeIndex = map.get(nodeId);
			color[nodeIndex] = getResource("newCopyColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(id, color, getResource("ieOneCopyInserted") + ii.intValue() + ".");
			graphDrawer.endStep();
		}

		void ieOneNewListInserted(SLInformation l, SLInformation c, SLInformation d) {
			createGraph();
			graphDrawer.setDataStructure(graph);
			Integer nodeId = skipListIdMap.get(l);
			int nodeIndex = map.get(nodeId);
			color[nodeIndex] = getResource("newListColor");
			nodeId = skipListIdMap.get(c);
			nodeIndex = map.get(nodeId);
			color[nodeIndex] = getResource("newListColor");
			nodeId = skipListIdMap.get(d);
			nodeIndex = map.get(nodeId);
			color[nodeIndex] = getResource("newListColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(id, color, getResource("ieOneNewListInserted"));
			graphDrawer.endStep();
		}

		void ieInsertionFinished(IntInformation ii) {
			createGraph();
			graphDrawer.setDataStructure(graph);
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieInsertionFinished") + ii.intValue() + ".");
			graphDrawer.endStep();
		}

		void ieEndInsertions() {
			createGraph();
			graphDrawer.setDataStructure(graph);
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieEndInsertions"));
			graphDrawer.endStep();
		}

		void ieStartSearch(SLInformation n, int i) {
			Integer nodeId = skipListIdMap.get(n);
			int nodeIndex = map.get(nodeId);
			color[nodeIndex] = getResource("searchColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(id, color, getResource("ieStartSearch") + i + ".");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1 });
			graphDrawer.endStep();
		}

		void ieHorizontalStepDone(SLInformation n) {
			Integer nodeId = skipListIdMap.get(n);
			int nodeIndex = map.get(nodeId);
			color[nodeIndex] = getResource("searchColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(id, color, getResource("ieHorizontalStepDone"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 4 });
			graphDrawer.endStep();
		}

		void ieVerticalStepDone(SLInformation n) {
			Integer nodeId = skipListIdMap.get(n);
			int nodeIndex = map.get(nodeId);
			color[nodeIndex] = getResource("searchColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(id, color, getResource("ieVerticalStepDone"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 5, 6 });
			graphDrawer.endStep();
		}

		void ieEndSearch(int k, boolean f) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(id, color, getResource("ieEndSearch1") + k + " " + getResource("ieEndSearch2")
					+ (f ? getResource("success") : getResource("failure")));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 8 });
			graphDrawer.endStep();
		}

		void ieEnd() {
			graphDrawer.endXMLFile();
		}

		private void createGraph() {
			graph = new Graph<StringInformation, StringInformation>(true, new StringInformation(),
					new StringInformation());
			skipListIdMap = new HashMap<SLInformation, Integer>();
			SLInformation cursor, firstListCursor = skipList.iterator().next();
			int column, row = 0;
			Integer nodeId, prevNodeId, downNodeId;
			for (SLInformation sli : skipList) {
				cursor = sli;
				column = 0;
				nodeId = (row + column + 1) * (row + column) / 2 + (row + 1);
				graph.addNode(new StringInformation("L" + row), nodeId, startX + column * deltaX,
						startY + row * deltaY);
				skipListIdMap.put(cursor, nodeId);
				while (cursor.succ.value <= maxKeyValue) {
					prevNodeId = nodeId;
					cursor = cursor.succ;
					if (firstListCursor == null) {
						column = column + 1;
					} else {
						while (firstListCursor.value != cursor.value) {
							column = column + 1;
							firstListCursor = firstListCursor.succ;
						}
					}
					nodeId = (row + column + 1) * (row + column) / 2 + (row + 1);
					graph.addNode(new StringInformation("" + cursor.value), nodeId, startX + column * deltaX,
							startY + row * deltaY);
					skipListIdMap.put(cursor, nodeId);
					graph.newArc(prevNodeId, nodeId);
					if (row > 0) {
						downNodeId = (row + column) * (row - 1 + column) / 2 + row;
						graph.newArc(nodeId, downNodeId);
					}
				}
				prevNodeId = nodeId;
				cursor = cursor.succ;
				if (firstListCursor == null) {
					column = column + 1;
				} else {
					while (firstListCursor.value != cursor.value) {
						column = column + 1;
						firstListCursor = firstListCursor.succ;
					}
				}
				nodeId = (row + column + 1) * (row + column) / 2 + (row + 1);
				graph.addNode(new StringInformation("L" + row), nodeId, startX + column * deltaX,
						startY + row * deltaY);
				skipListIdMap.put(cursor, nodeId);
				graph.newArc(prevNodeId, nodeId);
				row = row + 1;
				firstListCursor = skipList.iterator().next();
			}
			map = new HashMap<Integer, Integer>();
			Collection<Integer> nodeIds = graph.getNodeIds();
			int numNodes = nodeIds.size();
			int i = 0;
			id = new Integer[numNodes];
			for (Integer cursorId : nodeIds) {
				id[i] = cursorId;
				map.put(id[i], i);
				i = i + 1;
			}
			font = new String[numNodes];
			color = new String[numNodes];
			shape = new String[numNodes];
			for (i = 0; i < numNodes; i++) {
				color[i] = getResource("graphColor");
				font[i] = getResource("graphFont");
				shape[i] = "Rectangular";
			}
		}
	}

}
