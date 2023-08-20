package org.algoritmica.alvie.algorithms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.datastructure.Queue;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.QueueXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class BreadthFirstSearch {
	private class VisualBreadthFirstSearch extends VisualInnerClass {
		private ArrayI<IntInformation> reachedArray;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private GraphXMLDrawerUtility<IntInformation, IntInformation> graphDrawer;

		private QueueXMLDrawerUtility<IntInformation> queueDrawer;

		private ArrayXMLDrawerUtility<IntInformation> reachedArrayDrawer;

		private Integer[] nodeId;

		private Integer[] reachedArrayId;

		private String[] nodeColor, lineColor, queueColor;

		private String[] reachedArrayColor, reachedArrayFont,
				reachedArrayShape;

		private Map<Integer, Integer> graphIdIndexMap, reachedArrayIdIndexMap;

		private boolean isPseudocodeVisible;

		public VisualBreadthFirstSearch(String visFile) {
			super("breadthFirstSearch", visFile);
		}

		private void ieEnd() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, getResource("ieEnd"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			graphDrawer.endStep();
			graphDrawer.endXMLFile();
		}

		private void ieInitialNodeEnqueued(int s) {
			String previousColor = nodeColor[graphIdIndexMap.get(s)];
			nodeColor[graphIdIndexMap.get(s)] = getResource("enqueuedNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor,
					getResource("ieInitialNodeEnqueued"));
			if (queue.size() > 0) {
				queueDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 3 });
			graphDrawer.endStep();
			nodeColor[graphIdIndexMap.get(s)] = previousColor;
		}

		private void ieListAdiacencyExplorationEnded(int u) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor,
					getResource("ieListAdiacencyExplorationEnded"));
			if (queue.size() > 0) {
				queueDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 4, 13 });
			graphDrawer.endStep();
			nodeColor[graphIdIndexMap.get(u)] = getResource("reachedNodeColor");
		}

		private void ieNewNodeReached(int u) {
			reachedArrayColor[reachedArrayIdIndexMap.get(u)] = getResource("reachedNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer
					.draw(nodeId, nodeColor, getResource("ieNewNodeReached"));
			if (queue.size() > 0) {
				queueDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 7 });
			graphDrawer.endStep();
		}

		private void ieNextNodeToBeDequeued(int u) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor,
					getResource("ieNextNodeToBeDequeued"));
			queueDrawer.draw(new Integer[] { 0 }, queueColor, "");
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 5 });
			graphDrawer.endStep();
		}

		private void ieNodeHasBeenAlreadyReached(int u) {
			nodeColor[graphIdIndexMap.get(u)] = getResource("dequeuedNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor,
					getResource("ieNodeHasBeenAlreadyReached"));
			if (queue.size() > 0) {
				queueDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 4, 13 });
			graphDrawer.endStep();
			nodeColor[graphIdIndexMap.get(u)] = getResource("reachedNodeColor");
		}

		private void ieNodeHasBeenEnqueued(int u, int x) {
			Integer currentArcId = graph.getArcId(u, x);
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, new Integer[] { currentArcId },
					lineColor, getResource("ieNodeHasBeenEnqueued"));
			queueDrawer.draw("");
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 10 });
			graphDrawer.endStep();
		}

		private void ieNodeHasNotBeenReached(int u) {
			nodeColor[graphIdIndexMap.get(u)] = getResource("dequeuedNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor,
					getResource("ieNodeHasNotBeenReached"));
			if (queue.size() > 0) {
				queueDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 7, 8, 9, 10, 11 });
			graphDrawer.endStep();
		}

		private void ieNodeToBeEnqueued(int u, int x) {
			Integer currentArcId = graph.getArcId(u, x);
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, new Integer[] { currentArcId },
					lineColor, getResource("ieNodeToBeEnqueued"));
			if (queue.size() > 0) {
				queueDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 9 });
			graphDrawer.endStep();
		}

		private void ieReachedInitialized() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieReachedInitialized"));
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1, 2 });
			graphDrawer.endStep();
		}

		private void ieStart() {
			graphDrawer.startXMLFile(getResource("algorithmName"));
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			graphDrawer.endStep();
		}

		private void init() {
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			setReachedArray();
			graphDrawer = new GraphXMLDrawerUtility<IntInformation, IntInformation>(
					graph, getResource("graphTitle"), getOutputStream());
			graphDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			graphDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			graphDrawer.setDefaultShape("Elliptical");
			queueDrawer = new QueueXMLDrawerUtility<IntInformation>(queue,
					getResource("queueTitle"), getOutputStream());
			queueDrawer.setOriginX(Integer
					.parseInt(getResource("queueXMLDrawerOriginX")));
			queueDrawer.setOriginY(Integer
					.parseInt(getResource("queueXMLDrawerOriginY")));
			reachedArrayDrawer = new ArrayXMLDrawerUtility<IntInformation>(
					reachedArray, getResource("reachedArrayTitle"),
					getOutputStream());
			reachedArrayDrawer.setOriginX(Integer
					.parseInt(getResource("reachedArrayXMLDrawerOriginX")));
			reachedArrayDrawer.setOriginY(Integer
					.parseInt(getResource("reachedArrayXMLDrawerOriginY")));
			setDifferentialDrawArrays();
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("isPseudocodeVisible"));
		}

		private void setDifferentialDrawArrays() {
			Collection<Integer> nodeIdCollection = graph.getNodeIds();
			int n = nodeIdCollection.size();
			graphIdIndexMap = new HashMap<Integer, Integer>();
			nodeId = new Integer[n];
			nodeColor = new String[n];
			Iterator<Integer> nodeIdIterator = nodeIdCollection.iterator();
			for (int i = 0; i < n; i++) {
				nodeId[i] = nodeIdIterator.next();
				graphIdIndexMap.put(nodeId[i], i);
				nodeColor[i] = getResource("defaultNodeColor");
			}
			lineColor = new String[] { getResource("emphasizedLineColor") };
			queueColor = new String[] {
					getResource("emphasizedQueueElementColor"),
					getResource("emphasizedQueueElementColor") };
			reachedArrayId = new Integer[n];
			reachedArrayColor = new String[n];
			reachedArrayFont = new String[n];
			reachedArrayShape = new String[n];
			for (int i = 0; i < n; i++) {
				reachedArrayId[i] = i;
				reachedArrayIdIndexMap.put(
						reachedArray.elementAt(i).intValue(), i);
				reachedArrayColor[i] = getResource("defaultReachedArrayColor");
				reachedArrayFont[i] = getResource("defaultReachedArrayFont");
				reachedArrayShape[i] = getResource("defaultReachedArrayShape");
			}
		}

		private void setReachedArray() {
			Collection<Integer> nodeIdCollection = graph.getNodeIds();
			int n = nodeIdCollection.size();
			reachedArrayIdIndexMap = new HashMap<Integer, Integer>();
			reachedArray = new Array<IntInformation>(n, new IntInformation());
			Iterator<Integer> nodeIdIterator = nodeIdCollection.iterator();
			for (int i = 0; i < n; i++) {
				reachedArray
						.setAt(new IntInformation(nodeIdIterator.next()), i);
			}
			IntInformation prossimo;
			int i, j;
			for (i = 0; i < n; i = i + 1) {
				prossimo = reachedArray.elementAt(i);
				j = i;
				while ((j > 0)
						&& (reachedArray.elementAt(j - 1)
								.isGreaterThan(prossimo))) {
					reachedArray.setAt(reachedArray.elementAt(j - 1), j);
					reachedArray.setAt(prossimo, j - 1);
					j = j - 1;
				}
			}
		}
	}

	private Graph<IntInformation, IntInformation> graph;

	private Queue<IntInformation> queue;

	private Map<Integer, Boolean> reached;

	private VisualBreadthFirstSearch vbfs;

	private void breadthFirstSearch() {
		queue = new Queue<IntInformation>(new IntInformation());
		vbfs.init();
		vbfs.ieStart();
		reached = new HashMap<Integer, Boolean>();
		vbfs.ieReachedInitialized();
		int s = graph.getNodeIds().iterator().next();
		queue.enqueue(new IntInformation(s));
		vbfs.ieInitialNodeEnqueued(s);
		while (queue.size() > 0) {
			int u = queue.first().intValue();
			vbfs.ieNextNodeToBeDequeued(u);
			queue.dequeue();
			if (!reached.containsKey(u)) {
				vbfs.ieNodeHasNotBeenReached(u);
				reached.put(u, true);
				vbfs.ieNewNodeReached(u);
				for (int x : graph.getAdiacentNodeIds(u)) {
					vbfs.ieNodeToBeEnqueued(u, x);
					queue.enqueue(new IntInformation(x));
					vbfs.ieNodeHasBeenEnqueued(u, x);
				}
				vbfs.ieListAdiacencyExplorationEnded(u);
			} else {
				vbfs.ieNodeHasBeenAlreadyReached(u);
			}
		}
		vbfs.ieEnd();
	}

	public void execute(String visFile) {
		vbfs = new VisualBreadthFirstSearch(visFile);
		if (readInput()) {
			breadthFirstSearch();
			MessageUtility.algorithmTerminated(vbfs
					.getResource("algorithmName"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vbfs.getAlgorithmPath(), vbfs
				.getResource("algorithmFileName"));
		graph = (Graph) inputLoader.load("Graph", vbfs
				.getResource("selectInputMessage"));
		if (graph == null) {
			return false;
		}
		return true;
	}
}
