package org.algoritmica.alvie.algorithms;

import java.util.Collection;
import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.BooleanInformation;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.LongInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class TopologicalSort {
	private class VisualTopologicalSort extends VisualInnerClass {
		private GraphXMLDrawerUtility<IntInformation, IntInformation> graphDrawer;

		private ArrayXMLDrawerUtility<LongInformation> etaDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private String[] etaColor, etaFont, etaShape, graphColor, graphFont,
				graphShape;

		VisualTopologicalSort(String visFile) {
			super("topologicalSort", visFile);
		}

		private void ieEnd() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieEnd"));
			etaDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieNeighborRecursiveDFSOrderEnd(int u, int v) {
			graphColor[u] = getResource("sourceNodeColor");
			graphColor[v] = getResource("targetNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, graphColor,
					getResource("ieNeighborRecursiveDepthFirstSearchOrderEnd"));
			etaDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 11 });
			graphDrawer.endStep();
			graphColor[u] = getResource("graphColor");
			graphColor[v] = getResource("graphColor");
		}

		private void ieNeighborRecursiveDFSOrderStart(int u, int v) {
			graphColor[u] = getResource("sourceNodeColor");
			graphColor[v] = getResource("targetNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer
					.draw(
							nodeId,
							graphColor,
							getResource("ieNeighborRecursiveDepthFirstSearchOrderStart"));
			etaDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 11 });
			graphDrawer.endStep();
			graphColor[u] = getResource("graphColor");
			graphColor[v] = getResource("graphColor");
		}

		private void ieNewCounterElementBegin(int u) {
			graphColor[u] = getResource("nextNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, graphColor,
					getResource("ieNewCounterElementBegin"));
			etaDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 13, 14 });
			graphDrawer.endStep();
			graphColor[u] = getResource("graphColor");
		}

		private void ieNewCounterElementEnd(int u) {
			graphColor[u] = getResource("nextNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, graphColor,
					getResource("ieNewCounterElementEnd"));
			etaDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 13, 14 });
			graphDrawer.endStep();
			graphColor[u] = getResource("graphColor");
		}

		private void ieNewNodeReached(int u) {
			graphColor[u] = getResource("nextNodeColor");
			graphShape[u] = getResource("reachedNodeShape");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, graphColor,
					getResource("ieNewNodeReached"));
			etaDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 8 });
			graphDrawer.endStep();
			graphColor[u] = getResource("graphColor");
		}

		private void ieNextIteration(int s) {
			graphColor[s] = getResource("nextNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer
					.draw(nodeId, graphColor, getResource("ieNextIteration"));
			etaDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 5 });
			graphDrawer.endStep();
			graphColor[s] = getResource("graphColor");
		}

		private void ieRecursiveDFSOrderEnd(int s) {
			graphColor[s] = getResource("nextNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, graphColor,
					getResource("ieRecursiveDepthFirstSearchOrderEnd"));
			etaDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 5 });
			graphDrawer.endStep();
			graphColor[s] = getResource("graphColor");
		}

		private void ieRecursiveDFSOrderStart(int s) {
			graphColor[s] = getResource("nextNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, graphColor,
					getResource("ieRecursiveDepthFirstSearchOrderStart"));
			etaDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 7 });
			graphDrawer.endStep();
			graphColor[s] = getResource("graphColor");
		}

		private void ieStart() {
			graphDrawer.startXMLFile(getResource("algorithmName"));
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieStart"));
			etaDrawer.draw("");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1, 2, 3 });
			graphDrawer.endStep();
		}

		void init() {
			graphDrawer = new GraphXMLDrawerUtility<IntInformation, IntInformation>(
					graph, getResource("graphName"), getOutputStream());
			graphDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			graphDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			graphDrawer.setDefaultShape(getResource("graphNodeShape"));
			etaDrawer = new ArrayXMLDrawerUtility<LongInformation>(eta,
					getResource("etaName"), getOutputStream());
			etaDrawer.setOriginX(Integer
					.parseInt(getResource("etaXMLDrawerOriginX")));
			etaDrawer.setOriginY(Integer
					.parseInt(getResource("etaXMLDrawerOriginY")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setUpDifferentialDrawArrays();
		}

		private void setUpDifferentialDrawArrays() {
			etaColor = new String[n];
			etaFont = new String[n];
			etaShape = new String[n];
			graphColor = new String[n];
			graphFont = new String[n];
			graphShape = new String[n];
			for (int i = 0; i < n; i++) {
				etaColor[i] = getResource("etaColor");
				etaFont[i] = getResource("etaFont");
				etaShape[i] = "Rectangular";
				graphColor[i] = getResource("graphColor");
				graphFont[i] = getResource("graphFont");
				graphShape[i] = getResource("graphShape");
			}
		}

	}

	private Graph<IntInformation, IntInformation> graph;

	private Integer[] nodeId;

	private HashMap<Integer, Integer> idIndex;

	private Array<BooleanInformation> reached;

	private Array<LongInformation> eta;

	private int counter, n;

	private VisualTopologicalSort vts;

	public void execute(String visFile) {
		vts = new VisualTopologicalSort(visFile);
		if (readGraph()) {
			vts.init();
			vts.ieStart();
			topologicalSort();
			vts.ieEnd();
			MessageUtility.algorithmTerminated("TopologicalSort");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readGraph() {
		InputLoader il = new InputLoader(vts.getAlgorithmPath(), vts
				.getResource("algorithmFileName"));
		graph = (Graph) il.load("Graph", vts.getResource("selectInputMessage"));
		if (graph == null) {
			return false;
		}
		Collection<Integer> nodes = graph.getNodeIds();
		idIndex = new HashMap<Integer, Integer>();
		n = nodes.size();
		nodeId = new Integer[n];
		reached = new Array<BooleanInformation>(n, new BooleanInformation());
		eta = new Array<LongInformation>(n, new LongInformation());
		int i = 0;
		for (Integer id : nodes) {
			nodeId[i] = id;
			idIndex.put(id, i);
			reached.setAt(new BooleanInformation(false), i);
			eta.setAt(new LongInformation(-1), i);
			i = i + 1;
		}
		counter = n - 1;
		return true;
	}

	private void recursiveDFSOrder(int u) {
		reached.setAt(new BooleanInformation(true), u);
		vts.ieNewNodeReached(u);
		Collection<Integer> adiacencyList = graph.getAdiacentNodeIds(nodeId[u]);
		int v;
		for (Integer x : adiacencyList) {
			v = idIndex.get(x);
			if (!reached.elementAt(v).booleanValue()) {
				vts.ieNeighborRecursiveDFSOrderStart(u, v);
				recursiveDFSOrder(v);
				vts.ieNeighborRecursiveDFSOrderEnd(u, v);
			}
		}
		vts.ieNewCounterElementBegin(u);
		eta.setAt(new LongInformation(nodeId[u]), counter);
		counter = counter - 1;
		vts.ieNewCounterElementEnd(u);
	}

	private void topologicalSort() {
		for (int s = 0; s < n; s = s + 1) {
			vts.ieNextIteration(s);
			if (!reached.elementAt(s).booleanValue()) {
				vts.ieRecursiveDFSOrderStart(s);
				recursiveDFSOrder(s);
				vts.ieRecursiveDFSOrderEnd(s);
			}
		}
	}

}
