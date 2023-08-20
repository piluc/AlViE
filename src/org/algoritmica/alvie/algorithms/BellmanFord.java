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

public class BellmanFord {
	private class VisualBellmanFord extends VisualInnerClass {
		private GraphXMLDrawerUtility<StringInformation, IntInformation> graphDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] nodeId;

		private String[] color;

		private Integer[] arcId;

		private String[] lineColor;

		private String previousColor;

		private VisualBellmanFord(String visFile) {
			super("bellmanFord", visFile);
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
			color[s] = getResource("graphColor");
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
				pseudocodeDrawer.draw("", new int[] { 10 });
			graphDrawer.endStep();
		}

		private void ieNewNodeAnalysisEnd(int v) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieNewNodeAnalysisEnd"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 16 });
			graphDrawer.endStep();
			color[v] = getResource("examinedNodeColor");
		}

		private void ieNewNodeAnalysisStart(int v) {
			color[v] = getResource("newNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, arcId, lineColor,
					getResource("ieNewNodeAnalysisStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("",
						new int[] { 9, 10, 11, 12, 13, 14, 15 });
			graphDrawer.endStep();
		}

		private void ieOuterForIterationEnd() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(null, null, arcId, lineColor,
					getResource("ieOuterForIterationEnd"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			graphDrawer.endStep();
		}

		private void ieOuterForIterationStart() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(null, null, arcId, lineColor,
					getResource("ieOuterForIterationStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 8, 9, 10, 11, 12, 13, 14,
						15, 16 });
			graphDrawer.endStep();
			initColorArray();
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
				pseudocodeDrawer.draw("", new int[] { 12, 13 });
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

		private void initColorArray() {
			for (int j = 0; j < n; j++) {
				color[j] = getResource("graphColor");
			}
		}

		private void initDifferentialDrawArrays() {
			nodeId = new Integer[n];
			color = new String[n];
			for (int j = 0; j < n; j++) {
				nodeId[j] = id[j];
				color[j] = getResource("graphColor");
			}
			lineColor = new String[n - 1];
			for (int j = 0; j < n - 1; j++) {
				lineColor[j] = getResource("graphArcColor");
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

	private HashMap<Integer, Integer> idIndex;

	Integer[] id;

	private VisualBellmanFord vbf;

	private void bellmanFord() {
		for (int u = 0; u < n; u++) {
			pred[u] = -1;
			dist[u] = Integer.MAX_VALUE;
		}
		pred[s] = s;
		dist[s] = 0;
		vbf.ieDistancesInitialized();
		int i, u, v;
		Collection<Integer> adiacencyList;
		for (i = 0; i < n; i++) {
			vbf.ieOuterForIterationStart();
			for (v = 0; v < n; v++) {
				vbf.ieNewNodeAnalysisStart(v);
				adiacencyList = g.getAdiacentNodeIds(id[v]);
				for (Integer x : adiacencyList) {
					u = idIndex.get(x);
					vbf.ieNeighborNodeAnalysisStart(u);
					if ((dist[v] < Integer.MAX_VALUE)
							&& (dist[u] > dist[v]
									+ g.getArcLabel(id[v], id[u]).intValue())) {
						dist[u] = dist[v]
								+ g.getArcLabel(id[v], id[u]).intValue();
						pred[u] = v;
						vbf.ieSetNewDistance(u, v);
					}
					vbf.ieNeighborNodeAnalysisEnd(u);
				}
				vbf.ieNewNodeAnalysisEnd(v);
			}
			vbf.ieOuterForIterationEnd();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vbf.getAlgorithmPath(), vbf
				.getResource("algorithmFileName"));
		g = (Graph) il
				.load("Graph", vbf.getResource("selectInputMessage"));
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
		return true;
	}

	public void execute(String visFile) {
		vbf = new VisualBellmanFord(visFile);
		if (readInput()) {
			vbf.init();
			vbf.ieStart();
			bellmanFord();
			vbf.ieEnd();
			MessageUtility.algorithmTerminated(vbf.getResource("algorithmName"));
		}
	}

}
