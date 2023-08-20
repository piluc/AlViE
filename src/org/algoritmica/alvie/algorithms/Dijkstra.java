package org.algoritmica.alvie.algorithms;

import java.util.Collection;
import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class Dijkstra {
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

	private class VisualDijkstra extends VisualInnerClass {
		private GraphXMLDrawerUtility<StringInformation, IntInformation> graphDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] nodeId;

		private String[] color, font, shape;

		private Integer[] arcId;

		private String[] lineColor, lineType, lineThickness, arcLabelFont;

		private String previousColor;

		private VisualDijkstra(String visFile) {
			super("dijkstra", visFile);
		}

		private void ieDistancesInitialized() {
			setUpArcIdArray();
			for (int i = 0; i < n; i++) {
				if (dist[i] < Integer.MAX_VALUE) {
					g.setNodeInformation(nodeId[i], new StringInformation(""
							+ dist[i]));
				} else {
					g.setNodeInformation(nodeId[i],
							new StringInformation("+oo"));
				}
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieDistancesInitialized"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1, 2, 3, 4, 5, 6 });
			graphDrawer.endStep();
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
			color[u] = previousColor;
		}

		private void ieNeighborNodeAnalysisStart(int u) {
			previousColor = color[u];
			color[u] = getResource("neighborNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieNeighborNodeAnalysis"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 16 });
			graphDrawer.endStep();
		}

		private void ieNewNodeAnalysisEnd(int v) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieNewNodeAnalysisEnd"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 23 });
			graphDrawer.endStep();
			if (v != s) {
				color[v] = getResource("examinedNodeColor");
			} else {
				color[v] = getResource("startNodeColor");
			}
		}

		private void ieSetNewDistance(int u, int v) {
			setUpArcIdArray();
			g
					.setNodeInformation(nodeId[u], new StringInformation(""
							+ dist[u]));
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieSetNewDistance"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 18, 19, 20 });
			graphDrawer.endStep();
		}

		private void ieStart() {
			color[s] = getResource("startNodeColor");
			graphDrawer.startXMLFile(getResource("algorithmName"));
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, getResource("ieStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			graphDrawer.endStep();
		}

		private void ieStartNewNodeAnalysis(int v) {
			color[v] = getResource("newNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieStartNewNodeAnalysis"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 13, 14 });
			graphDrawer.endStep();
		}
		
		private void init() {
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
			font = new String[n];
			shape = new String[n];
			for (int j = 0; j < n; j++) {
				nodeId[j] = id[j];
				color[j] = getResource("graphColor");
				font[j] = getResource("graphFont");
				shape[j] = getResource("graphShape");
			}
			arcLabelFont = new String[n - 1];
			lineColor = new String[n - 1];
			lineType = new String[n - 1];
			lineThickness = new String[n - 1];
			for (int j = 0; j < n - 1; j++) {
				arcLabelFont[j] = getResource("graphArcFont");
				lineColor[j] = getResource("graphArcColor");
				lineType[j] = getResource("graphArcType");
				lineThickness[j] = getResource("graphArcThickness");
			}
		}

		private void setUpArcIdArray() {
			int p = 0;
			for (int j = 0; j < n; j++) {
				if ((pred[j] != j) && (pred[j] >= 0)) {
					p = p + 1;
				}
			}
			if (p == 0) {
				arcId = null;
			} else {
				arcId = new Integer[p];
				int k = 0;
				for (int j = 0; j < n; j++) {
					if (pred[j] != j && pred[j] >= 0) {
						arcId[k] = g.getArcId(id[pred[j]], id[j]);
						k = k + 1;
					}
				}
			}
		}

	}

	private Graph<StringInformation, IntInformation> g;

	private int[] pred, dist;

	private int n, s;

	private ExtendedHeap pq;

	private HashMap<Integer, Integer> idIndex;

	private Integer[] id;

	private VisualDijkstra vd;

	private void dijkstra() {
		for (int u = 0; u < n; u++) {
			pred[u] = -1;
			dist[u] = Integer.MAX_VALUE;
		}
		pred[s] = s;
		dist[s] = 0;
		vd.ieDistancesInitialized();
		for (int u = 0; u < n; u++) {
			pq.enqueue(new ExtendedHeapInformation(u, dist[u]));
		}
		ExtendedHeapInformation e;
		Collection<Integer> adiacencyList;
		int u, v;
		while (!pq.empty()) {
			e = pq.dequeue();
			v = e.data;
			vd.ieStartNewNodeAnalysis(v);
			adiacencyList = g.getAdiacentNodeIds(id[v]);
			for (Integer x : adiacencyList) {
				u = idIndex.get(x);
				vd.ieNeighborNodeAnalysisStart(u);
				if (dist[u] > dist[v] + g.getArcLabel(id[v], id[u]).intValue()) {
					dist[u] = dist[v] + g.getArcLabel(id[v], id[u]).intValue();
					pred[u] = v;
					pq.decreaseKey(u, dist[u]);
					vd.ieSetNewDistance(u, v);
				}
				vd.ieNeighborNodeAnalysisEnd(u);
			}
			vd.ieNewNodeAnalysisEnd(v);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vd.getAlgorithmPath(), vd
				.getResource("algorithmFileName"));
		g = (Graph) il
				.load("Graph",
						"Seleziona il grafo di cui si vogliono calcolare i cammini minimi");
		if (g == null) {
			return false;
		}
		Collection<Integer> nodeIds = g.getNodeIds();
		n = nodeIds.size();
		pred = new int[n];
		dist = new int[n];
		id = new Integer[n];
		idIndex = new HashMap<Integer, Integer>(n);
		int i = 0;
		for (Integer nodeId : nodeIds) {
			id[i] = nodeId;
			idIndex.put(nodeId, i);
			i = i + 1;
		}
		s = idIndex.get(0);
		pq = new ExtendedHeap(n);
		return true;
	}

	public void execute(String visFile) {
		vd = new VisualDijkstra(visFile);
		if (readInput()) {
			vd.init();
			vd.ieStart();
			dijkstra();
			vd.ieEnd();
			MessageUtility.algorithmTerminated(vd.getResource("algorithmName"));
		}
	}

}
