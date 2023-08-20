package org.algoritmica.alvie.algorithms;

import java.util.Collection;
import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.datastructure.Heap;
import org.algoritmica.alvie.datastructure.HeapI;
import org.algoritmica.alvie.datastructure.Queue;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.IntPairInformation;
import org.algoritmica.alvie.information.SortableI;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class Kruskal {
	public class HeapInformation implements SortableI<HeapInformation> {
		private IntPairInformation data;

		private int weight;

		public HeapInformation() {
			weight = -1;
			data = null;
		}

		public HeapInformation(IntPairInformation data, int weight) {
			this.weight = weight;
			this.data = data;
		}

		public IntPairInformation data() {
			return data;
		}

		public boolean isEqual(HeapInformation item) {
			return (weight == item.weight);
		}

		public boolean isGreaterThan(HeapInformation item) {
			return (weight > item.weight);
		}

		public boolean isLessThan(HeapInformation item) {
			return (weight < item.weight);
		}

		public String stringValue() {
			return "" + weight;
		}

		public int weight() {
			return weight;
		}
	}

	class VisualKruskal extends VisualInnerClass {
		private GraphXMLDrawerUtility<StringInformation, IntInformation> graphDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] nodeId;

		private String[] color;

		private Integer[] arcId;

		private String[] lineColor;

		private VisualKruskal(String visFile) {
			super("kruskal", visFile);
		}

		private void addArcId(int id) {
			if (arcId == null) {
				arcId = new Integer[1];
				arcId[0] = id;
			} else {
				int m = arcId.length;
				Integer[] tmp = new Integer[m + 1];
				for (int j = 0; j < m; j++) {
					tmp[j] = arcId[j];
				}
				tmp[m] = id;
				arcId = tmp;
			}
		}

		private void ieArcIncluded() {
			for (int i = 0; i < n; i++) {
				g.setNodeInformation(id[i], new StringInformation(""+set[i]));
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieArcIncluded"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 15, 16 });
			graphDrawer.endStep();
			lineColor[arcId.length - 1] = getResource("graphSelectedArcColor");
		}

		private void ieArcNotIncluded(IntPairInformation data) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieArcNotIncluded"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 18 });
			graphDrawer.endStep();
			removeArcId();
		}

		private void ieEnd() {
			initDifferentialDrawArrays();
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieEnd"));
			graphDrawer.endStep();
			graphDrawer.endXMLFile();
		}

		private void ieNewArcAnalyzed(IntPairInformation data) {
			addArcId(g.getArcId(data.firstValue(), data.secondValue()));
			lineColor[arcId.length - 1] = getResource("graphCurrentArcColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieNewArcAnalyzed"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 12 });
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

		private void ieVariableInitialized() {
			for (int i = 0; i < n; i++) {
				g.setNodeInformation(id[i], new StringInformation(""+set[i]));
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color,
					getResource("ieVariableInitialized"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1, 2, 3, 4, 5, 6, 7, 8,
						9, 10 });
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
			for (int j = 0; j < n; j++) {
				nodeId[j] = id[j];
				color[j] = getResource("graphColor");
			}
			lineColor = new String[2 * (n - 1)];
			for (int j = 0; j < 2 * (n - 1); j++) {
				lineColor[j] = getResource("graphSelectedArcColor");
			}
		}

		private void removeArcId() {
			int m = arcId.length;
			Integer[] tmp = new Integer[m - 1];
			for (int j = 0; j < m - 1; j++) {
				tmp[j] = arcId[j];
			}
			arcId = tmp;
		}
	}

	private int[] set;

	private Graph<StringInformation, IntInformation> g;

	private Integer[] id;

	private HashMap<Integer, Integer> idIndex;

	private HeapI<HeapInformation> pq;

	private int n;

	private Queue<IntPairInformation> mst;

	private VisualKruskal vk;

	public void execute(String visFile) {
		vk = new VisualKruskal(visFile);
		if (readInput()) {
			vk.init();
			vk.ieStart();
			kruskal();
			vk.ieEnd();
			MessageUtility.algorithmTerminated(vk.getResource("algorithmName"));
		}
	}

	private boolean find(int x, int y) {
		return set[x] == set[y];
	}

	private void kruskal() {
		Collection<Integer> adiacencyList;
		set = new int[n];
		for (int u = 0; u < n; u++) {
			adiacencyList = g.getAdiacentNodeIds(id[u]);
			for (Integer x : adiacencyList) {
				if (id[u] < x) {
					IntPairInformation data = new IntPairInformation(id[u], x);
					int w = g.getArcLabel(id[u], x).intValue();
					pq.enqueue(new HeapInformation(data, w));
				}
			}
			set[u] = u;
		}
		vk.ieVariableInitialized();
		HeapInformation element;
		while (!pq.empty()) {
			element = pq.dequeue();
			IntPairInformation data = element.data;
			vk.ieNewArcAnalyzed(data);
			if (!find(set[idIndex.get(data.firstValue())], set[idIndex.get(data
					.secondValue())])) {
				union(set[idIndex.get(data.firstValue())], set[idIndex.get(data
						.secondValue())]);
				mst.enqueue(data);
				vk.ieArcIncluded();
			} else {
				vk.ieArcNotIncluded(data);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vk.getAlgorithmPath(), vk
				.getResource("algorithmFileName"));
		g = (Graph) il.load("Graph", vk.getResource("selectInputMessage"));
		if (g == null) {
			return false;
		}
		Collection<Integer> nodeIds = g.getNodeIds();
		n = nodeIds.size();
		id = new Integer[n];
		idIndex = new HashMap<Integer, Integer>(n);
		int i = 0;
		for (Integer nodeId : nodeIds) {
			id[i] = nodeId;
			idIndex.put(nodeId, i);
			i = i + 1;
		}
		pq = new Heap(n, new HeapInformation());
		mst = new Queue<IntPairInformation>(new IntPairInformation());
		return true;
	}

	private void union(int x, int y) {
		int nx = 0;
		for (int i = 0; i < n; i++) {
			if (set[i] == set[x]) {
				nx++;
			}
		}
		int ny = 0;
		for (int i = 0; i < n; i++) {
			if (set[i] == set[y]) {
				ny++;
			}
		}
		int z = set[x];
		if (nx<ny) {
			z = set[y];
		}
		int xc = set[x];
		int yc = set[y];
		for (int i = 0; i < n; i++) {
			if (set[i] == yc || set[i] == xc) {
				set[i] = z;
			}
		}
	}
}
