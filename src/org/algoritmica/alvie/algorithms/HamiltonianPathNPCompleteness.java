package org.algoritmica.alvie.algorithms;

import java.awt.Color;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.BooleanInformation;
import org.algoritmica.alvie.information.GeometricObjectInformation;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.GeometricFigureXMLDrawerUtility;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class HamiltonianPathNPCompleteness {
	private class VisualHamiltonianPathNPCompleteness extends VisualInnerClass {
		private GeometricFigure text;
		private GeometricFigureXMLDrawerUtility textDrawer;
		private GraphXMLDrawerUtility<StringInformation, IntInformation> graphDrawer;
		private int diamondHeight;
		private int diamondNodeInterspace;
		private int nodeWidth;
		private Integer[] nodeId, arcId;
		private String[] nodeColor, arcColor;

		private VisualHamiltonianPathNPCompleteness(String visFile) {
			super("hamiltonianPathNPCompleteness", visFile);
		}

		private void createAssignmentText() {
			String as = "";
			for (int i = 0; i < assignment.length() - 1; i++) {
				as = as + "x" + (i + 1) + "="
						+ assignment.elementAt(i).stringValue() + ", ";
			}
			as = as
					+ "x"
					+ assignment.length()
					+ "="
					+ assignment.elementAt(assignment.length() - 1)
							.stringValue();
			text.add(new GeometricObjectInformation(as, 0, 20));
		}

		private void createFormulaText() {
			String fs = "";
			for (int r = 0; r < m; r++) {
				String cs = "(";
				for (int c = 0; c < 3; c++) {
					if (formula.elementAt(r, c).intValue() % 2 == 0) {
						cs = cs + "(NOT x"
								+ (formula.elementAt(r, c).intValue() / 2)
								+ ")";
					} else {
						cs = cs + "x"
								+ (1 + formula.elementAt(r, c).intValue() / 2);
					}
					if (c < 2) {
						cs = cs + " OR ";
					} else {
						cs = cs + ")";
					}
				}
				if (r < m - 1) {
					fs = fs + cs + " AND ";
				} else {
					fs = fs + cs;
				}
			}
			text = new GeometricFigure();
			text.add(new GeometricObjectInformation(fs, 0, 0));
			textDrawer = new GeometricFigureXMLDrawerUtility(text,
					getResource("formulaTitle"), getOutputStream());
			textDrawer.setOriginX(Integer
					.parseInt(getResource("formulaXMLDrawerOriginX")));
			textDrawer.setOriginY(Integer
					.parseInt(getResource("formulaXMLDrawerOriginY")));
			textDrawer.setDefaultColor(Color.BLACK);
		}

		private void ieUnsatAssignmentDefined() {
			text.remove(1);
			createAssignmentText();
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieUnsatAssignmentDefined"));
			textDrawer.draw("");
			graphDrawer.endStep();
			setDifferentialDrawArrays();
		}

		private void ieSatAssignmentDefined() {
			createAssignmentText();
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieSatAssignmentDefined"));
			textDrawer.draw("");
			graphDrawer.endStep();
			setDifferentialDrawArrays();
		}

		private void ieClauseNodesAdded() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieClauseNodesAdded"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieDetourEnded(int v, int w) {
			int cid = n * (1 + 2 * m) + 1 + w / 2;
			int aid = graph.getArcId(cid, v * (1 + 2 * m) + 1 + w);
			nodeColor[v * (1 + 2 * m) + 1 + w] = getResource("justReachedNodeColor");
			arcColor[aid] = getResource("visitedArcColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieDetourEnded"));
			textDrawer.draw("");
			graphDrawer.endStep();
			nodeColor[v * (1 + 2 * m) + 1 + w] = getResource("visitedNodeColor");
		}

		private void ieDetourStarted(int v, int w) {
			int cid = n * (1 + 2 * m) + 1 + w / 2;
			nodeColor[cid] = getResource("justReachedNodeColor");
			int aid = graph.getArcId(v * (1 + 2 * m) + 1 + w, cid);
			arcColor[aid] = getResource("visitedArcColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieDetourStarted"));
			textDrawer.draw("");
			graphDrawer.endStep();
			nodeColor[cid] = getResource("visitedNodeColor");
		}

		private void ieDiagonalNodeVisited(int v, int w, boolean ltr) {
			nodeColor[v * (1 + 2 * m) + 1 + w] = getResource("justReachedNodeColor");
			if (ltr && w > 0) {
				int aid = graph.getArcId(v * (1 + 2 * m) + 1 + w - 1, v
						* (1 + 2 * m) + 1 + w);
				arcColor[aid] = getResource("visitedArcColor");
			}
			if (!ltr && w < 2 * m - 1) {
				int aid = graph.getArcId(v * (1 + 2 * m) + 1 + w + 1, v
						* (1 + 2 * m) + 1 + w);
				arcColor[aid] = getResource("visitedArcColor");
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieDiagonalNodeVisited"));
			textDrawer.draw("");
			graphDrawer.endStep();
			nodeColor[v * (1 + 2 * m) + 1 + w] = getResource("visitedNodeColor");
		}

		private void ieEnd() {
			graphDrawer.endXMLFile();
		}

		private void ieFirstTriangleCreated() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieFirstTriangleCreated"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieGraphCreated() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieGraphCreated"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieHamiltonianPath() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieHamiltonianPath"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieLastEdge(boolean ltr) {
			nodeColor[n * (1 + 2 * m)] = getResource("justReachedNodeColor");
			if (ltr) {
				int aid = graph.getArcId(n * (1 + 2 * m) - 1, n * (1 + 2 * m));
				arcColor[aid] = getResource("visitedArcColor");
			} else {
				int aid = graph.getArcId(n * (1 + 2 * m) - 2 * m, n
						* (1 + 2 * m));
				arcColor[aid] = getResource("visitedArcColor");
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieLastEdge"));
			textDrawer.draw("");
			graphDrawer.endStep();
			nodeColor[n * (1 + 2 * m)] = getResource("visitedNodeColor");
		}

		private void ieLastRhombusCompleted() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieLastRhombusCompleted"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieNextTriangleCreated() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieNextTriangleCreated"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieNotHamiltonianPath() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieNotHamiltonianPath"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieStart() {
			createFormulaText();
			textDrawer.startXMLFile(getResource("algorithmName"));
			textDrawer.startStep(step++);
			textDrawer.draw(getResource("ieStart"));
			textDrawer.endStep();
		}

		private void ieTrianglesConnected() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieTrianglesConnected"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieVariableClauseConnected(int ni1, int ni2, int ni3,
				int ni4, int ni5, int ni6, int ni7, int aid) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(
					new Integer[] { ni1, ni2, ni3, ni4, ni5, ni6, ni7 },
					new String[] { getResource("literalNodeColor"),
							getResource("literalNodeColor"),
							getResource("literalNodeColor"),
							getResource("oppositeLiteralNodeColor"),
							getResource("oppositeLiteralNodeColor"),
							getResource("oppositeLiteralNodeColor"),
							getResource("clauseNodeColor") }, new Integer[] {
							aid, aid + 1, aid + 2, aid + 3, aid + 4, aid + 5 },
					new String[] { getResource("literalClauseEdgeColor"),
							getResource("clauseLiteralEdgeColor"),
							getResource("literalClauseEdgeColor"),
							getResource("clauseLiteralEdgeColor"),
							getResource("literalClauseEdgeColor"),
							getResource("clauseLiteralEdgeColor") },
					getResource("ieVariableClauseConnected"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieVariableFalse(int v) {
			int aid = graph.getArcId(v * (1 + 2 * m), v * (1 + 2 * m) + 2 * m);
			arcColor[aid] = getResource("visitedArcColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieVariableFalse"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieVariableNodeVisited(int v) {
			nodeColor[v * (1 + 2 * m)] = getResource("justReachedNodeColor");
			if (v > 0) {
				if (assignment.elementAt(v - 1).booleanValue()) {
					int aid = graph.getArcId(v * (1 + 2 * m) - 1, v
							* (1 + 2 * m));
					arcColor[aid] = getResource("visitedArcColor");
				} else {
					int aid = graph.getArcId(v * (1 + 2 * m) - 2 * m, v
							* (1 + 2 * m));
					arcColor[aid] = getResource("visitedArcColor");
				}
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieVariableNodeVisited"));
			textDrawer.draw("");
			graphDrawer.endStep();
			nodeColor[v * (1 + 2 * m)] = getResource("visitedNodeColor");
		}

		private void ieVariableTrue(int v) {
			int aid = graph.getArcId(v * (1 + 2 * m), v * (1 + 2 * m) + 1);
			arcColor[aid] = getResource("visitedArcColor");
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieVariableTrue"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void init() {
			graphDrawer = new GraphXMLDrawerUtility<StringInformation, IntInformation>(
					graph, getResource("graphName"), getOutputStream());
			graphDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			graphDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			graphDrawer.setDefaultShape(getResource("graphShape"));
			graphDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("nodeWidth")));
			diamondHeight = Integer.parseInt(getResource("diamondHeight"));
			diamondNodeInterspace = Integer
					.parseInt(getResource("diamondNodeInterspace"));
			nodeWidth = Integer.parseInt(getResource("nodeWidth"));
		}

		private void setClauseNodeCoordinate(int c) {
			int x = 2 * c * (nodeWidth + diamondNodeInterspace) + nodeWidth
					+ diamondNodeInterspace / 2;
			graph.setNodeCoordinates(n + n * 2 * m + (c + 1), x, (n + 1)
					* diamondHeight);
		}

		private void setDiagonalNodeCoordinate(int v, int w) {
			int pid = v + v * 2 * m + w + 1;
			int nid = v + v * 2 * m + w + 2;
			int xp = w * nodeWidth + w * diamondNodeInterspace;
			int xn = (w + 1) * (nodeWidth + diamondNodeInterspace);
			graph.setNodeCoordinates(pid, xp, v * diamondHeight + diamondHeight
					/ 2);
			graph.setNodeCoordinates(nid, xn, v * diamondHeight + diamondHeight
					/ 2);
		}

		private void setDifferentialDrawArrays() {
			nodeId = new Integer[(n + 1) + n * 2 * m + m];
			arcId = new Integer[n * (4 + 2 * (2 * m - 1)) + 6 * m];
			nodeColor = new String[(n + 1) + n * 2 * m + m];
			arcColor = new String[n * (4 + 2 * (2 * m - 1)) + 6 * m];
			for (int i = 0; i < nodeId.length; i++) {
				nodeId[i] = i;
				nodeColor[i] = getResource("unvisitedNodeColor");
			}
			for (int i = 0; i < arcId.length; i++) {
				arcId[i] = i;
				arcColor[i] = getResource("unvisitedArcColor");
			}
		}

		private void setVariableNodeCoordinate(int v) {
			int x = (2 * m * nodeWidth + (2 * m - 1) * diamondNodeInterspace) / 2;
			graph.setNodeCoordinates(v + v * 2 * m, x, v * diamondHeight);
		}
	}

	private VisualHamiltonianPathNPCompleteness vhpnpc;
	private Matrix<IntInformation> formula;
	private int n;
	private int m;
	private Graph<StringInformation, IntInformation> graph;
	private Array<BooleanInformation> assignment, secondAssignment;
	private boolean[] satisfiedClause;

	private void createGraph() {
		graph = new Graph<StringInformation, IntInformation>(true,
				new StringInformation(), new IntInformation());
		vhpnpc.init();
		vhpnpc.ieStart();
		// length of diagonal
		int d = 2 * m;
		// Variable node of first variable
		graph.addNode(new StringInformation("u[" + 1 + "]"), 0, 0, 0);
		vhpnpc.setVariableNodeCoordinate(0);
		// First pair of first variable diagonal
		graph.addNode(new StringInformation("p[" + 1 + "," + 1 + "]"), 1, 0, 0);
		vhpnpc.setDiagonalNodeCoordinate(0, 0);
		graph.addNode(new StringInformation("n[" + 1 + "," + 1 + "]"), 2, 0, 0);
		vhpnpc.setDiagonalNodeCoordinate(0, 1);
		// Upper edges of first rhomb
		graph.addArc(0, 1, 0);
		graph.addArc(0, d, 1);
		// Edges of first pair of first variable diagonal
		graph.addArc(1, 2, 2);
		graph.addArc(2, 1, d + 1);
		for (int w = 3; w < d; w = w + 2) {
			// Next pair of first variable diagonal
			graph.addNode(new StringInformation("p[" + 1 + "," + (w / 2 + 1)
					+ "]"), w, 0, 0);
			vhpnpc.setDiagonalNodeCoordinate(0, w - 1);
			graph.addNode(new StringInformation("n[" + 1 + "," + (w / 2 + 1)
					+ "]"), w + 1, 0, 0);
			vhpnpc.setDiagonalNodeCoordinate(0, w);
			// Edges between this pair and previous one of first variable
			// diagonal
			graph.addArc(w - 1, w, w);
			graph.addArc(w, w - 1, d + w - 1);
			// Edges of current pair of first variable diagonal
			graph.addArc(w, w + 1, w + 1);
			graph.addArc(w + 1, w, d + w);
		}
		vhpnpc.ieFirstTriangleCreated();
		for (int v = 1; v < n; v++) {
			int nodeIdOffset = v * d + v;
			int arcIdOffset = 2 * v * d + 2 * v;
			// Variable node of (v+1)-th variable
			graph.addNode(new StringInformation("u[" + (v + 1) + "]"),
					nodeIdOffset + 0, 0, 0);
			vhpnpc.setVariableNodeCoordinate(v);
			// First pair of (v+1)-th variable diagonal
			graph.addNode(
					new StringInformation("p[" + (v + 1) + "," + 1 + "]"),
					nodeIdOffset + 1, 0, 0);
			vhpnpc.setDiagonalNodeCoordinate(v, 0);
			graph.addNode(
					new StringInformation("n[" + (v + 1) + "," + 1 + "]"),
					nodeIdOffset + 2, 0, 0);
			vhpnpc.setDiagonalNodeCoordinate(v, 1);
			// Upper edges of (v+1)-th rhomb
			graph.addArc(nodeIdOffset + 0, nodeIdOffset + 1, arcIdOffset + 0);
			graph.addArc(nodeIdOffset + 0, nodeIdOffset + d, arcIdOffset + 1);
			// Edges of first pair of (v+1)-th variable diagonal
			graph.addArc(nodeIdOffset + 1, nodeIdOffset + 2, arcIdOffset + 2);
			graph.addArc(nodeIdOffset + 2, nodeIdOffset + 1, arcIdOffset + d
					+ 1);
			for (int w = 3; w < d; w = w + 2) {
				// Next pair of (v+1)-th variable diagonal
				graph.addNode(new StringInformation("p[" + (v + 1) + ","
						+ (w / 2 + 1) + "]"), nodeIdOffset + w, 0, 0);
				vhpnpc.setDiagonalNodeCoordinate(v, w - 1);
				graph.addNode(new StringInformation("n[" + (v + 1) + ","
						+ (w / 2 + 1) + "]"), nodeIdOffset + w + 1, 0, 0);
				vhpnpc.setDiagonalNodeCoordinate(v, w);
				// Edges between this pair and previous one of (v+1)-th variable
				// diagonal
				graph.addArc(nodeIdOffset + w - 1, nodeIdOffset + w,
						arcIdOffset + w);
				graph.addArc(nodeIdOffset + w, nodeIdOffset + w - 1,
						arcIdOffset + d + w - 1);
				// Edges of current pair of (v+1)-th variable diagonal
				graph.addArc(nodeIdOffset + w, nodeIdOffset + w + 1,
						arcIdOffset + w + 1);
				graph.addArc(nodeIdOffset + w + 1, nodeIdOffset + w,
						arcIdOffset + d + w);
			}
			vhpnpc.ieNextTriangleCreated();
			// Edges from previous rhomb
			graph.addArc(nodeIdOffset - d, nodeIdOffset, arcIdOffset - 2);
			graph.addArc(nodeIdOffset - 1, nodeIdOffset, arcIdOffset - 1);
			vhpnpc.ieTrianglesConnected();
		}
		int nodeIdOffset = n * d + n;
		int arcIdOffset = 2 * n * d + 2 * n;
		// Target node
		graph.addNode(new StringInformation("t"), nodeIdOffset + 0, 0, 0);
		vhpnpc.setVariableNodeCoordinate(n);
		// Edges from previous rhomb
		graph.addArc(nodeIdOffset - d, nodeIdOffset, arcIdOffset - 2);
		graph.addArc(nodeIdOffset - 1, nodeIdOffset, arcIdOffset - 1);
		vhpnpc.ieLastRhombusCompleted();
		nodeIdOffset = nodeIdOffset + 1;
		for (int c = 0; c < m; c++) {
			graph.addNode(new StringInformation("v[" + (c + 1) + "]"), nodeIdOffset
					+ c, 0, 0);
			vhpnpc.setClauseNodeCoordinate(c);
		}
		vhpnpc.ieClauseNodesAdded();
		for (int c = 0; c < m; c++) {
			int l = formula.elementAt(c, 0).intValue() - 1;
			int v = l / 2;
			int vid1 = v * d + v + 1 + 2 * c + (l % 2 == 0 ? 0 : 1);
			int wid1 = v * d + v + 1 + 2 * c + (l % 2 == 0 ? 1 : 0);
			graph.addArc(vid1, nodeIdOffset + c, arcIdOffset++);
			graph.addArc(nodeIdOffset + c, wid1, arcIdOffset++);
			l = formula.elementAt(c, 1).intValue() - 1;
			v = l / 2;
			int vid2 = v * d + v + 1 + 2 * c + (l % 2 == 0 ? 0 : 1);
			int wid2 = v * d + v + 1 + 2 * c + (l % 2 == 0 ? 1 : 0);
			graph.addArc(vid2, nodeIdOffset + c, arcIdOffset++);
			graph.addArc(nodeIdOffset + c, wid2, arcIdOffset++);
			l = formula.elementAt(c, 2).intValue() - 1;
			v = l / 2;
			int vid3 = v * d + v + 1 + 2 * c + (l % 2 == 0 ? 0 : 1);
			int wid3 = v * d + v + 1 + 2 * c + (l % 2 == 0 ? 1 : 0);
			graph.addArc(vid3, nodeIdOffset + c, arcIdOffset++);
			graph.addArc(nodeIdOffset + c, wid3, arcIdOffset++);
			vhpnpc.ieVariableClauseConnected(vid1, vid2, vid3, wid1, wid2,
					wid3, nodeIdOffset + c, arcIdOffset - 6);
		}
		vhpnpc.ieGraphCreated();
	}

	public void execute(String visFile) {
		vhpnpc = new VisualHamiltonianPathNPCompleteness(visFile);
		if (readInput()) {
			createGraph();
			vhpnpc.ieSatAssignmentDefined();
			visitGraph();
			assignment = secondAssignment;
			vhpnpc.ieUnsatAssignmentDefined();
			visitGraph();
			vhpnpc.ieEnd();
			MessageUtility.algorithmTerminated(vhpnpc
					.getResource("algorithmName"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vhpnpc.getAlgorithmPath(), vhpnpc
				.getResource("algorithmFileName"));
		formula = (Matrix) il.load("Matrix", vhpnpc
				.getResource("selectFirstInputMessage"));
		if (formula == null || formula.width() != 3) {
			return false;
		}
		m = formula.height();
		n = 0;
		for (int r = 0; r < m; r++) {
			for (int c = 0; c < 3; c++) {
				if (c < 2 && formula.elementAt(r, c).intValue() <= 0) {
					return false;
				}
				if (n < formula.elementAt(r, c).intValue()) {
					n = formula.elementAt(r, c).intValue();
				}
			}
		}
		if (n % 2 == 0) {
			n = n / 2;
		} else {
			n = (n + 1) / 2;
		}
		assignment = (Array) il.load("Array", vhpnpc
				.getResource("selectSecondInputMessage"));
		if (assignment.length() != n) {
			return false;
		}
		secondAssignment = (Array) il.load("Array", vhpnpc
				.getResource("selectThirdInputMessage"));
		if (secondAssignment.length() != n) {
			return false;
		}
		return true;
	}

	private void visitGraph() {
		satisfiedClause = new boolean[m];
		for (int c = 0; c < m; c++) {
			satisfiedClause[c] = false;
		}
		boolean leftToRight = true;
		for (int v = 0; v < n; v++) {
			vhpnpc.ieVariableNodeVisited(v);
			if (assignment.elementAt(v).booleanValue()) {
				leftToRight = true;
				vhpnpc.ieVariableTrue(v);
				for (int w = 0; w < 2 * m; w++) {
					vhpnpc.ieDiagonalNodeVisited(v, w, leftToRight);
					if (w % 2 == 0) {
						if (((formula.elementAt(w / 2, 0).intValue() == 2 * v + 1)
								|| (formula.elementAt(w / 2, 1).intValue() == 2 * v + 1) || (formula
								.elementAt(w / 2, 2).intValue() == 2 * v + 1))
								&& !satisfiedClause[w / 2]) {
							satisfiedClause[w / 2] = true;
							vhpnpc.ieDetourStarted(v, w);
							w = w + 1;
							vhpnpc.ieDetourEnded(v, w);
						}
					}
				}
			} else {
				leftToRight = false;
				vhpnpc.ieVariableFalse(v);
				for (int w = 2 * m - 1; w >= 0; w--) {
					vhpnpc.ieDiagonalNodeVisited(v, w, leftToRight);
					if (w % 2 != 0) {
						if (((formula.elementAt(w / 2, 0).intValue() == 2 * v + 2)
								|| (formula.elementAt(w / 2, 1).intValue() == 2 * v + 2) || (formula
								.elementAt(w / 2, 2).intValue() == 2 * v + 2))
								&& !satisfiedClause[w / 2]) {
							satisfiedClause[w / 2] = true;
							vhpnpc.ieDetourStarted(v, w);
							w = w - 1;
							vhpnpc.ieDetourEnded(v, w);
						}
					}
				}
			}
		}
		vhpnpc.ieLastEdge(leftToRight);
		for (int c = 0; c < m; c++) {
			if (!satisfiedClause[c]) {
				vhpnpc.ieNotHamiltonianPath();
				return;
			}
		}
		vhpnpc.ieHamiltonianPath();
	}
}
