package org.algoritmica.alvie.algorithms;

import java.util.Collection;
import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.datastructure.Queue;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.IntPairInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class JarnikPrim {
	private class ExtendedHeap {

		private ExtendedHeapInformation[] heapArray;

		private int heapSize;

		private int[] heapArrayPosition;

		public ExtendedHeap(int size) {
			heapArray = new ExtendedHeapInformation[size];
			heapSize = 0;
			heapArrayPosition = new int[size];
		}

		public void decreaseKey(int data, int weight) {
			int i = heapArrayPosition[data];
			heapArray[i].weight = weight;
			heapify(i);
		}

		public ExtendedHeapInformation dequeue() {
			if (!empty()) {
				ExtendedHeapInformation minimum = heapArray[0];
				heapArray[0] = heapArray[heapSize - 1];
				heapSize = heapSize - 1;
				heapify(0);
				return minimum;
			} else {
				return null;
			}
		}

		public boolean empty() {
			return heapSize == 0;
		}

		public void enqueue(ExtendedHeapInformation e) {
			heapArray[heapSize] = e;
			heapArrayPosition[e.data] = heapSize;
			heapSize = heapSize + 1;
			heapify(heapSize - 1);
		}

		private int father(int i) {
			return (i - 1) / 2;
		}

		private void heapify(int i) {
			while (i > 0 && heapArray[i].weight < heapArray[father(i)].weight) {
				swap(i, father(i));
				i = father(i);
			}
			int son;
			while (leftSon(i) < heapSize && i != minFatherSons(i)) {
				son = minFatherSons(i);
				swap(i, son);
				i = son;
			}
		}

		private int leftSon(int i) {
			return 2 * i + 1;
		}

		private int minFatherSons(int i) {
			int j = leftSon(i);
			int k = j;
			if (k + 1 < heapSize) {
				k = k + 1;
			}
			if (heapArray[k].weight < heapArray[j].weight) {
				j = k;
			}
			if (heapArray[i].weight < heapArray[j].weight) {
				j = i;
			}
			return j;
		}

		private void swap(int i, int j) {
			ExtendedHeapInformation tmp = heapArray[i];
			heapArray[i] = heapArray[j];
			heapArray[j] = tmp;
			heapArrayPosition[heapArray[i].data] = i;
			heapArrayPosition[heapArray[j].data] = j;
		}

	}

	private class ExtendedHeapInformation {
		private int data;

		private int weight;

		public ExtendedHeapInformation(int data, int weight) {
			this.data = data;
			this.weight = weight;
		}
	}

	class VisualJarnikPrim extends VisualInnerClass {
		private GraphXMLDrawerUtility<StringInformation, IntInformation> graphDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] nodeId;

		private String[] color;

		private Integer[] arcId;

		private String[] lineColor;

		private String previousColor;

		private VisualJarnikPrim(String visFile) {
			super("jarnikPrim", visFile);
		}

		private void ieEnd() {
			initDifferentialDrawArrays();
			setUpArcIdArray();
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieEnd"));
			graphDrawer.endStep();
			graphDrawer.endXMLFile();
		}

		private void ieNeighborNodeAnalysisEnd(int u) {
			if (!included[u]) {
				color[u] = previousColor;
			}
		}

		private void ieNeighborNodeAnalysisStart(int u) {
			if (!included[u]) {
				previousColor = color[u];
				color[u] = getResource("neighborNodeColor");
				graphDrawer.startStep(step++);
				graphDrawer.draw(nodeId, color, arcId, lineColor,
						getResource("ieNeighborNodeAnalysisStart"));
				if (isPseudocodeVisible)
					pseudocodeDrawer.draw("", new int[] { 14, 15, 16, 17, 18,
							19 });
				graphDrawer.endStep();
			}
		}

		private void ieNewNodeAnalysisEnd(int v) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieNewNodeAnalysisEnd"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			graphDrawer.endStep();
			color[v] = getResource("graphMSTColor");

		}

		void ieNewNodeAnalysisStart(int v) {
			color[v] = getResource("newNodeColor");
			g.setNodeInformation(id[v], new StringInformation("" + weight[v]));
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieNewNodeAnalysisStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 9, 10, 11, 12 });
			graphDrawer.endStep();
		}

		private void ieSetNewWeight(int u, int v) {
			setUpArcIdArray();
			g.setNodeInformation(nodeId[u], new StringInformation(""
					+ weight[u]));
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieSetNewWeight"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 16, 17, 18 });
			graphDrawer.endStep();
		}

		private void ieStart() {
			graphDrawer.startXMLFile(getResource("algorithmName"));
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, getResource("ieStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			graphDrawer.endStep();
		}

		private void ieWeightsInitialized() {
			initializeNodeLabels();
			graphDrawer.startStep(step++);
			graphDrawer
					.draw(nodeId, color, getResource("ieWeightsInitialized"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1, 2, 3, 4, 5, 6, 7 });
			graphDrawer.endStep();
		}

		void init() {
			graphDrawer = new GraphXMLDrawerUtility<StringInformation, IntInformation>(
					g, getResource("graphName"), getOutputStream());
			graphDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			graphDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			graphDrawer.setDefaultShape(getResource("graphShape"));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			initDifferentialDrawArrays();
		}

		private void initDifferentialDrawArrays() {
			nodeId = new Integer[n];
			color = new String[n];
			for (int j = 0; j < n; j++) {
				nodeId[j] = id[j];
				color[j] = getResource("graphColor");
			}
			lineColor = new String[2 * (n - 1)];
			for (int j = 0; j < 2 * (n - 1); j++) {
				lineColor[j] = getResource("graphArcColor");
			}
		}

		private void initializeNodeLabels() {
			for (int i = 0; i < n; i++) {
				g.setNodeInformation(id[i], new StringInformation("+oo"));
			}
		}

		private void setUpArcIdArray() {
			int p = 0;
			for (int j = 0; j < n; j++) {
				if (pred[j] != j) {
					p = p + 1;
				}
			}
			if (p == 0) {
				arcId = null;
			} else {
				arcId = new Integer[2 * p];
				int k = 0;
				for (int j = 0; j < n; j++) {
					if (pred[j] != j) {
						arcId[k] = g.getArcId(id[pred[j]], id[j]);
						k = k + 1;
						arcId[k] = g.getArcId(id[j], id[pred[j]]);
						k = k + 1;
					}
				}
			}
		}
	}

	private int[] pred, weight;

	private boolean[] included;

	private Graph<StringInformation, IntInformation> g;

	private Integer[] id;

	private HashMap<Integer, Integer> idIndex;

	private ExtendedHeap pq;

	private int n;

	private Queue<IntPairInformation> mst;

	private VisualJarnikPrim vjp;

	private void jarnikPrim() {
		for (int u = 0; u < n; u++) {
			included[u] = false;
			pred[u] = u;
			weight[u] = Integer.MAX_VALUE;
			pq.enqueue(new ExtendedHeapInformation(u, weight[u]));
		}
		vjp.ieWeightsInitialized();
		ExtendedHeapInformation element;
		int u, v;
		Collection<Integer> adiacencyList;
		while (!pq.empty()) {
			element = pq.dequeue();
			v = element.data;
			included[v] = true;
			if (pred[v] != v) {
				mst.enqueue(new IntPairInformation(pred[v], v));
			} else {
				weight[v] = 0;
			}
			vjp.ieNewNodeAnalysisStart(v);
			adiacencyList = g.getAdiacentNodeIds(id[v]);
			for (Integer x : adiacencyList) {
				u = idIndex.get(x);
				vjp.ieNeighborNodeAnalysisStart(u);
				if (!included[u]
						&& g.getArcLabel(id[v], id[u]).intValue() < weight[u]) {
					pred[u] = v;
					weight[u] = g.getArcLabel(id[v], id[u]).intValue();
					pq.decreaseKey(u, weight[u]);
					vjp.ieSetNewWeight(u, v);
				}
				vjp.ieNeighborNodeAnalysisEnd(u);
			}
			vjp.ieNewNodeAnalysisEnd(v);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vjp.getAlgorithmPath(), vjp
				.getResource("algorithmFileName"));
		g = (Graph) il.load("Graph", vjp.getResource("selectInputMessage"));
		if (g == null) {
			return false;
		}
		Collection<Integer> nodeIds = g.getNodeIds();
		n = nodeIds.size();
		pred = new int[n];
		weight = new int[n];
		included = new boolean[n];
		id = new Integer[n];
		idIndex = new HashMap<Integer, Integer>(n);
		int i = 0;
		for (Integer nodeId : nodeIds) {
			id[i] = nodeId;
			idIndex.put(nodeId, i);
			i = i + 1;
		}
		pq = new ExtendedHeap(n);
		mst = new Queue<IntPairInformation>(new IntPairInformation());
		return true;
	}

	public void execute(String visFile) {
		vjp = new VisualJarnikPrim(visFile);
		if (readInput()) {
			vjp.init();
			vjp.ieStart();
			jarnikPrim();
			vjp.ieEnd();
			MessageUtility
					.algorithmTerminated(vjp.getResource("algorithmName"));
		}
	}
}
