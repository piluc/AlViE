package org.algoritmica.alvie.algorithms;

import java.util.Collection;

import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class NearestNeighbor {
	private class VisualNearestNeighbor extends VisualInnerClass {
		private GraphXMLDrawerUtility<StringInformation, IntInformation> graphDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] nodeId;

		private String[] color;

		private VisualNearestNeighbor(String visFile) {
			super("nearestNeighbor", visFile);
		}

		private void initDifferentialDrawArrays() {
			nodeId = new Integer[n];
			color = new String[n];
			for (int j = 0; j < n; j++) {
				nodeId[j] = id[j];
				color[j] = getResource("graphColor");
			}
		}

		private void ieEnd() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieEnd"));
			graphDrawer.endStep();
			graphDrawer.endXMLFile();
		}

		private void ieTourStartNode(int x) {
			color[x] = getResource("startNodeColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, getResource("ieTourStartNode"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1, 2 });
			graphDrawer.endStep();
		}

		private void ieNewTourArc(int x, int y) {
			g.newArc(id[x], id[y]);
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, getResource("ieNewTourArc"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 4, 5 });
			graphDrawer.endStep();
			color[y] = getResource("visitedNodeColor");
		}

		private void ieLastTourArc(int x, int y) {
			g.newArc(id[x], id[y]);
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, color, getResource("ieLastTourArc"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 7 });
			graphDrawer.endStep();
			color[y] = getResource("visitedNodeColor");
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
	}

	private Graph<StringInformation, IntInformation> g;

	private int[] pred;

	private int n, s;

	Integer[] id;

	private VisualNearestNeighbor vnn;

	private void nearestNeighbor() {
		for (int u = 0; u < n; u++) {
			pred[u] = -1;
		}
		s = 0;
		vnn.ieTourStartNode(s);
		int i = 0, previous = s, next = 0;
		while (i < n) {
			int xp = g.getNodeAbscissa(id[previous]);
			int yp = g.getNodeOrdinate(id[previous]);
			double d = Double.MAX_VALUE;
			next = previous;
			for (int v = 0; v < n; v++) {
				if (v != s && pred[v] < 0) {
					int xv = g.getNodeAbscissa(id[v]);
					int yv = g.getNodeOrdinate(id[v]);
					double t = Math.sqrt((xp - xv) * (xp - xv) + (yp - yv)
							* (yp - yv));
					if (t < d) {
						d = t;
						next = v;
					}
				}
			}
			if (next != previous) {
				vnn.ieNewTourArc(previous, next);
				pred[next] = previous;
				previous = next;
			}
			i = i + 1;
		}
		vnn.ieLastTourArc(next, s);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vnn.getAlgorithmPath(), vnn
				.getResource("algorithmFileName"));
		g = (Graph) il.load("Graph", vnn.getResource("selectInputMessage"));
		if (g == null) {
			return false;
		}
		Collection<Integer> nodeIds = g.getNodeIds();
		n = nodeIds.size();
		pred = new int[n];
		id = new Integer[n];
		int i = 0;
		for (Integer nodeId : nodeIds) {
			id[i] = nodeId;
			i = i + 1;
		}
		return true;
	}

	public void execute(String visFile) {
		vnn = new VisualNearestNeighbor(visFile);
		if (readInput()) {
			vnn.init();
			vnn.ieStart();
			nearestNeighbor();
			vnn.ieEnd();
			MessageUtility
					.algorithmTerminated(vnn.getResource("algorithmName"));
		}
	}

}
