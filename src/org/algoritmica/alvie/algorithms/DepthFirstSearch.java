package org.algoritmica.alvie.algorithms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.datastructure.Stack;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.StackXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class DepthFirstSearch {
	private class VisualDepthFirstSearch extends VisualInnerClass {
		private ArrayI<IntInformation> reachedArray;

		private GraphXMLDrawerUtility<IntInformation, IntInformation> graphDrawer;

		private StackXMLDrawerUtility<IntInformation> stackDrawer;

		private ArrayXMLDrawerUtility<IntInformation> reachedArrayDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] nodeId, arcId;

		private Integer[] reachedArrayId;

		private String[] nodeColor, lineColor, stackColor, reachedArrayColor;

		private Map<Integer, Integer> graphIdIndexMap, reachedArrayIdIndexMap;

		private Integer[] father;

		public VisualDepthFirstSearch(String visFile) {
			super("depthFirstSearch", visFile);
		}

		private void ieEnd() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, getResource("ieEnd"));
			graphDrawer.endStep();
			graphDrawer.endXMLFile();
		}

		private void ieInitialNodePushed(int s) {
			String previousColor = nodeColor[graphIdIndexMap.get(s)];
			nodeColor[graphIdIndexMap.get(s)] = getResource("pushedNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor,
					getResource("ieInitialNodePushed"));
			if (stack.size() > 0) {
				stackDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 3 });
			graphDrawer.endStep();
			nodeColor[graphIdIndexMap.get(s)] = previousColor;
			father[reachedArrayIdIndexMap.get(s)] = null;
		}

		private void ieListAdiacencyExplorationEnded(int u) {
			setDifferentialDrawArcArrays(u);
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, lineColor,
					getResource("ieListAdiacencyExplorationEnded"));
			if (stack.size() > 0) {
				stackDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 4, 13 });
			graphDrawer.endStep();
			nodeColor[graphIdIndexMap.get(u)] = getResource("reachedNodeColor");
		}

		private void ieNextNodeHasBeenPopped(int u) {
			setDifferentialDrawArcArrays(u);
			nodeColor[graphIdIndexMap.get(u)] = getResource("poppedNodeColor");
			graphDrawer.startStep(step++);
			if (arcId != null) {
				graphDrawer.draw(nodeId, nodeColor, arcId, lineColor,
						getResource("ieNextNodeHasBeenPopped"));
			} else {
				graphDrawer.draw(nodeId, nodeColor,
						getResource("ieNextNodeHasBeenPopped"));
			}
			if (stack.size() > 0) {
				stackDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 5 });
			graphDrawer.endStep();
		}

		private void ieNextNodeToBePopped(int u) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor,
					getResource("ieNextNodeToBePopped"));
			if (stack.size() > 0) {
				stackDrawer.draw(new Integer[] { 0 }, stackColor, "");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 5 });
			graphDrawer.endStep();
		}

		private void ieNodeHasBeenAlreadyReached(int u) {
			setDifferentialDrawArcArrays(u);
			nodeColor[graphIdIndexMap.get(u)] = getResource("poppedNodeColor");
			graphDrawer.startStep(step++);
			if (arcId != null) {
				graphDrawer.draw(nodeId, nodeColor, arcId, lineColor,
						getResource("ieNodeHasBeenAlreadyReached"));
			} else {
				graphDrawer.draw(nodeId, nodeColor,
						getResource("ieNodeHasBeenAlreadyReached"));
			}
			if (stack.size() > 0) {
				stackDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6, 12 });
			graphDrawer.endStep();
		}

		private void ieNodeHasBeenPushed(int u, int x) {
			setDifferentialDrawArcArrays(u, x);
			graphDrawer.startStep(step++);
			if (arcId != null) {
				graphDrawer.draw(nodeId, nodeColor, arcId, lineColor,
						getResource("ieNodeHasBeenPushed"));
			} else {
				graphDrawer.draw(nodeId, nodeColor,
						getResource("ieNodeHasBeenPushed"));
			}
			if (stack.size() > 0) {
				stackDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 9, 10 });
			graphDrawer.endStep();
		}

		private void ieNodeHasBeenReached(int u) {
			setDifferentialDrawArcArrays(u);
			reachedArrayColor[reachedArrayIdIndexMap.get(u)] = getResource("reachedNodeColor");
			graphDrawer.startStep(step++);
			if (arcId != null) {
				graphDrawer.draw(nodeId, nodeColor, arcId, lineColor,
						getResource("ieNodeHasBeenReached"));
			} else {
				graphDrawer.draw(nodeId, nodeColor,
						getResource("ieNodeHasBeenReached"));
			}
			if (stack.size() > 0) {
				stackDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 7 });
			graphDrawer.endStep();
		}

		private void ieNodeToBePushed(int u, int x) {
			if (reached.get(x) == null || !reached.get(x)) {
				father[reachedArrayIdIndexMap.get(x)] = u;
			}
			setDifferentialDrawArcArrays(u, x);
			graphDrawer.startStep(step++);
			if (arcId != null) {
				graphDrawer.draw(nodeId, nodeColor, arcId, lineColor,
						getResource("ieNodeToBePushed"));
			} else {
				graphDrawer.draw(nodeId, nodeColor,
						getResource("ieNodeToBePushed"));
			}
			if (stack.size() > 0) {
				stackDrawer.draw("");
			}
			reachedArrayDrawer.draw(reachedArrayId, reachedArrayColor, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 9, 10 });
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
			setReachedArray();
			setFatherArray();
			graphDrawer = new GraphXMLDrawerUtility<IntInformation, IntInformation>(
					graph, getResource("graphTitle"), getOutputStream());
			graphDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			graphDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			graphDrawer.setDefaultShape("Elliptical");
			stackDrawer = new StackXMLDrawerUtility<IntInformation>(stack,
					getResource("stackTitle"), getOutputStream());
			stackDrawer.setOriginX(Integer
					.parseInt(getResource("stackXMLDrawerOriginX")));
			stackDrawer.setOriginY(Integer
					.parseInt(getResource("stackXMLDrawerOriginY")));
			reachedArrayDrawer = new ArrayXMLDrawerUtility<IntInformation>(
					reachedArray, getResource("reachedArrayTitle"),
					getOutputStream());
			reachedArrayDrawer.setOriginX(Integer
					.parseInt(getResource("reachedArrayXMLDrawerOriginX")));
			reachedArrayDrawer.setOriginY(Integer
					.parseInt(getResource("reachedArrayXMLDrawerOriginY")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setDifferentialDrawArrays();
		}

		private void setDifferentialDrawArcArrays(int x, int u) {
			int n = 0;
			int t = x;
			while (father[reachedArrayIdIndexMap.get(t)] != null) {
				n = n + 1;
				t = father[reachedArrayIdIndexMap.get(t)];
			}
			arcId = new Integer[2 * n + 2];
			lineColor = new String[2 * n + 2];
			t = x;
			int i = 0;
			while (father[reachedArrayIdIndexMap.get(t)] != null) {
				arcId[i] = graph.getArcId(
						father[reachedArrayIdIndexMap.get(t)], t);
				arcId[i + 1] = graph.getArcId(t, father[reachedArrayIdIndexMap
						.get(t)]);
				lineColor[i] = getResource("pathLineColor");
				lineColor[i + 1] = getResource("pathLineColor");
				i = i + 2;
				t = father[reachedArrayIdIndexMap.get(t)];
			}
			arcId[2 * n] = graph.getArcId(x, u);
			arcId[2 * n + 1] = graph.getArcId(u, x);
			lineColor[2 * n] = getResource("emphasizedLineColor");
			lineColor[2 * n + 1] = getResource("emphasizedLineColor");
		}

		private void setDifferentialDrawArcArrays(Integer x) {
			arcId = null;
			if (x != null) {
				int n = 0;
				int t = x;
				while (father[reachedArrayIdIndexMap.get(t)] != null) {
					n = n + 1;
					t = father[reachedArrayIdIndexMap.get(t)];
				}
				if (n > 0) {
					arcId = new Integer[2 * n];
					lineColor = new String[2 * n];
					int i = 0;
					while (father[reachedArrayIdIndexMap.get(x)] != null) {
						arcId[i] = graph.getArcId(father[reachedArrayIdIndexMap
								.get(x)], x);
						arcId[i + 1] = graph.getArcId(x,
								father[reachedArrayIdIndexMap.get(x)]);
						lineColor[i] = getResource("pathLineColor");
						lineColor[i + 1] = getResource("pathLineColor");
						i = i + 2;
						x = father[reachedArrayIdIndexMap.get(x)];
					}
				}
			}
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
			stackColor = new String[] {
					getResource("emphasizedStackElementColor"),
					getResource("emphasizedStackElementColor") };
			reachedArrayId = new Integer[n];
			reachedArrayColor = new String[n];
			reachedArrayIdIndexMap = new HashMap<Integer, Integer>();
			for (int i = 0; i < n; i++) {
				reachedArrayId[i] = i;
				reachedArrayColor[i] = getResource("defaultReachedArrayColor");
				reachedArrayIdIndexMap.put(
						reachedArray.elementAt(i).intValue(), i);
			}
		}

		private void setFatherArray() {
			Collection<Integer> nodeIdCollection = graph.getNodeIds();
			int n = nodeIdCollection.size();
			father = new Integer[n];
		}

		private void setReachedArray() {
			Collection<Integer> nodeIdCollection = graph.getNodeIds();
			int n = nodeIdCollection.size();
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

	private Stack<IntInformation> stack;

	private Map<Integer, Boolean> reached;

	private VisualDepthFirstSearch vdfs;

	private void depthFirstSearch() {
		stack = new Stack<IntInformation>(new IntInformation());
		vdfs.init();
		vdfs.ieStart();
		reached = new HashMap<Integer, Boolean>();
		vdfs.ieReachedInitialized();
		int s = graph.getNodeIds().iterator().next();
		stack.push(new IntInformation(s));
		vdfs.ieInitialNodePushed(s);
		while (stack.size() > 0) {
			int u = stack.top().intValue();
			vdfs.ieNextNodeToBePopped(u);
			stack.pop();
			vdfs.ieNextNodeHasBeenPopped(u);
			if (!reached.containsKey(u)) {
				reached.put(u, true);
				vdfs.ieNodeHasBeenReached(u);
				for (int x : graph.getAdiacentNodeIds(u)) {
					vdfs.ieNodeToBePushed(u, x);
					stack.push(new IntInformation(x));
					vdfs.ieNodeHasBeenPushed(u, x);
				}
			} else {
				vdfs.ieNodeHasBeenAlreadyReached(u);
			}
			vdfs.ieListAdiacencyExplorationEnded(u);
		}
		vdfs.ieEnd();
	}

	public void execute(String visFile) {
		vdfs = new VisualDepthFirstSearch(visFile);
		if (readGraph()) {
			depthFirstSearch();
			MessageUtility.algorithmTerminated(vdfs
					.getResource("algorithmName"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readGraph() {
		InputLoader inputLoader = new InputLoader(vdfs.getAlgorithmPath(), vdfs
				.getResource("algorithmFileName"));
		graph = (Graph) inputLoader.load("Graph", vdfs
				.getResource("selectInputMessage"));
		if (graph == null) {
			return false;
		}
		return true;
	}
}
