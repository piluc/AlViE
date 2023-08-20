package org.algoritmica.alvie.algorithms;

import java.awt.Color;
import java.util.Collection;

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

public class VertexCoverNPCompleteness {
	private class VisualVertexCoverNPCompleteness extends VisualInnerClass {
		private GeometricFigure text;
		private GeometricFigureXMLDrawerUtility textDrawer;
		private GraphXMLDrawerUtility<StringInformation, IntInformation> graphDrawer;
		private int nodePairDistance;
		private int nodeWidth;
		private int variableClauseGadgetDistance;
		private Integer[] nodeId, arcId;
		private String[] nodeColor, arcColor;

		private VisualVertexCoverNPCompleteness(String visFile) {
			super("vertexCoverNPCompleteness", visFile);
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

		private void ieClauseNodesTaken(int c, int f) {
			int g = (1 + f) % 3;
			int h = 3 - f - g;
			int id1 = 2 * n + 3 * c + g;
			int id2 = 2 * n + 3 * c + h;
			nodeColor[id1] = getResource("justTakenNodeColor");
			Collection<Integer> anid = graph.getAdiacentNodeIds(id1);
			for (Integer id : anid) {
				arcColor[graph.getArcId(id1, id)] = getResource("coveredArcColor");
			}
			nodeColor[id2] = getResource("justTakenNodeColor");
			anid = graph.getAdiacentNodeIds(id2);
			for (Integer id : anid) {
				arcColor[graph.getArcId(id2, id)] = getResource("coveredArcColor");
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieClauseNodesTaken"));
			textDrawer.draw("");
			graphDrawer.endStep();
			nodeColor[id1] = getResource("takenNodeColor");
			nodeColor[id2] = getResource("takenNodeColor");
		}

		private void ieClauseNodesCreated(int nodeId, int arcId) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { nodeId, nodeId + 1, nodeId + 2, },
					new String[] { getResource("justCreatedNodeColor"),
							getResource("justCreatedNodeColor"),
							getResource("justCreatedNodeColor") },
					new Integer[] { arcId, arcId + 1, arcId + 2, arcId + 3,
							arcId + 4, arcId + 5 }, new String[] {
							getResource("emphasizedArcColor"),
							getResource("emphasizedArcColor"),
							getResource("emphasizedArcColor"),
							getResource("emphasizedArcColor"),
							getResource("emphasizedArcColor"),
							getResource("emphasizedArcColor") },
					getResource("ieClauseNodesCreated"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieCovered() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieCovered"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieEnd() {
			graphDrawer.endXMLFile();
		}

		private void ieFalseVariableNodeTaken(int v) {
			nodeColor[2 * v + 1] = getResource("justTakenNodeColor");
			Collection<Integer> anid = graph.getAdiacentNodeIds(2 * v + 1);
			for (Integer id : anid) {
				arcColor[graph.getArcId(2 * v + 1, id)] = getResource("coveredArcColor");
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieFalseVariableNodeTaken"));
			textDrawer.draw("");
			graphDrawer.endStep();
			nodeColor[2 * v + 1] = getResource("takenNodeColor");
		}

		private void ieGraphCreated() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieGraphCreated"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieNotCovered(int c) {
			for (int cn = 0; cn < 3; cn++) {
				nodeColor[2 * n + 3 * c + cn] = getResource("justTakenNodeColor");
				Collection<Integer> anid = graph.getAdiacentNodeIds(2 * n + 3
						* c + cn);
				for (Integer id : anid) {
					arcColor[graph.getArcId(2 * n + 3 * c + cn, id)] = getResource("coveredArcColor");
				}
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieNotCovered"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieSatAssignmentDefined() {
			createAssignmentText();
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieSatAssignmentDefined"));
			textDrawer.draw("");
			graphDrawer.endStep();
			setDifferentialDrawArrays();
		}

		private void ieStart() {
			createFormulaText();
			textDrawer.startXMLFile(getResource("algorithmName"));
			textDrawer.startStep(step++);
			textDrawer.draw(getResource("ieStart"));
			textDrawer.endStep();
		}

		private void ieTrueVariableNodeTaken(int v) {
			nodeColor[2 * v] = getResource("justTakenNodeColor");
			Collection<Integer> anid = graph.getAdiacentNodeIds(2 * v);
			for (Integer id : anid) {
				arcColor[graph.getArcId(2 * v, id)] = getResource("coveredArcColor");
			}
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, arcId, arcColor,
					getResource("ieTrueVariableNodeTaken"));
			textDrawer.draw("");
			graphDrawer.endStep();
			nodeColor[2 * v] = getResource("takenNodeColor");
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

		private void ieVariableNodesCreated(int nodeId, int arcId) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { nodeId, nodeId + 1 },
					new String[] { getResource("justCreatedNodeColor"),
							getResource("justCreatedNodeColor") },
					new Integer[] { arcId, arcId + 1, arcId + 2 },
					new String[] { getResource("emphasizedArcColor"),
							getResource("emphasizedArcColor"),
							getResource("emphasizedArcColor") },
					getResource("ieVariableNodesCreated"));
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
			nodeWidth = Integer.parseInt(getResource("nodeWidth"));
			graphDrawer.setDefaultShapeWidth(nodeWidth);
			nodePairDistance = Integer
					.parseInt(getResource("nodePairDistance"));
			variableClauseGadgetDistance = Integer
					.parseInt(getResource("variableClauseGadgetDistance"));
		}

		private void setClauseNodesCoordinate(int c) {
			int y = variableClauseGadgetDistance;
			int x = 2 * nodePairDistance * c + nodePairDistance / 2;
			int nodeId = 2 * n + 3 * c;
			graph.setNodeCoordinates(nodeId, x, y);
			graph.setNodeCoordinates(nodeId + 1, x - nodePairDistance / 2, y
					+ nodePairDistance);
			graph.setNodeCoordinates(nodeId + 2, x + nodePairDistance / 2, y
					+ nodePairDistance);
		}

		private void setDifferentialDrawArrays() {
			nodeId = new Integer[numberNodes];
			nodeColor = new String[numberNodes];
			arcId = new Integer[numberEdges];
			arcColor = new String[numberEdges];
			for (int i = 0; i < numberNodes; i++) {
				nodeId[i] = i;
				nodeColor[i] = getResource("untakenNodeColor");
			}
			for (int i = 0; i < numberEdges; i++) {
				arcId[i] = i;
				arcColor[i] = getResource("uncoveredArcColor");
			}
		}

		private void setVariableNodesCoordinate(int v) {
			int x = 2 * nodePairDistance * v;
			int y = 0;
			graph.setNodeCoordinates(2 * v, x, y);
			graph.setNodeCoordinates(2 * v + 1, x + nodePairDistance, y);
		}
	}

	private VisualVertexCoverNPCompleteness vvcnpc;
	private Matrix<IntInformation> formula;
	private int n;
	private int m;
	private int numberNodes;
	private int numberEdges;
	private Graph<StringInformation, IntInformation> graph;
	private Array<BooleanInformation> assignment, secondAssignment;
	private boolean[] taken;

	private void coverGraph() {
		taken = new boolean[numberNodes];
		for (int c = 0; c < numberNodes; c++) {
			taken[c] = false;
		}
		for (int v = 0; v < n; v++) {
			if (assignment.elementAt(v).booleanValue()) {
				taken[2 * v] = true;
				vvcnpc.ieTrueVariableNodeTaken(v);
			} else {
				taken[2 * v + 1] = true;
				vvcnpc.ieFalseVariableNodeTaken(v);
			}
		}
		for (int c = 0; c < m; c++) {
			for (int i = 0; i < 3; i++) {
				taken[2 * n + 3 * c + i] = true;
			}
			int f = 0;
			boolean found = false;
			while (!found && f < 3) {
				int b = formula.elementAt(c, f).intValue();
				int offset = b % 2 == 0 ? 1 : 0;
				if (taken[2 * ((b - 1) / 2) + offset]) {
					found = true;
				} else {
					f = f + 1;
				}
			}
			if (found) {
				taken[2 * n + 3 * c + f] = false;
				vvcnpc.ieClauseNodesTaken(c, f);
			} else {
				vvcnpc.ieNotCovered(c);
				return;
			}
		}
		vvcnpc.ieCovered();
	}

	private void createGraph() {
		graph = new Graph<StringInformation, IntInformation>(false,
				new StringInformation(), new IntInformation());
		vvcnpc.init();
		vvcnpc.ieStart();
		for (int v = 0; v < n; v++) {
			int nodeId = 2 * v;
			int arcId = v;
			graph.addNode(new StringInformation("p[" + (v + 1) + "]"), nodeId,
					0, 0);
			graph.addNode(new StringInformation("n[" + (v + 1) + "]"),
					nodeId + 1, 0, 0);
			vvcnpc.setVariableNodesCoordinate(v);
			graph.addArc(nodeId, nodeId + 1, arcId);
			vvcnpc.ieVariableNodesCreated(nodeId, arcId);
		}
		for (int c = 0; c < m; c++) {
			int nodeId = 2 * n + 3 * c;
			int arcId = n + 6 * c;
			graph.addNode(new StringInformation("f[" + (c + 1) + "]"), nodeId,
					0, 0);
			graph.addNode(new StringInformation("s[" + (c + 1) + "]"),
					nodeId + 1, 0, 0);
			graph.addNode(new StringInformation("t[" + (c + 1) + "]"),
					nodeId + 2, 0, 0);
			graph.addArc(nodeId, nodeId + 1, arcId);
			graph.addArc(nodeId, nodeId + 2, arcId + 1);
			graph.addArc(nodeId + 1, nodeId + 2, arcId + 2);
			for (int l = 0; l < 3; l++) {
				int v = formula.elementAt(c, l).intValue();
				if (v % 2 == 0) {
					graph.addArc(nodeId + l, 1 + 2 * ((v - 1) / 2), arcId + 3
							+ l);
				} else {
					graph.addArc(nodeId + l, 2 * (v / 2), arcId + 3 + l);
				}
			}
			vvcnpc.setClauseNodesCoordinate(c);
			vvcnpc.ieClauseNodesCreated(nodeId, arcId);
		}
		vvcnpc.ieGraphCreated();
	}

	public void execute(String visFile) {
		vvcnpc = new VisualVertexCoverNPCompleteness(visFile);
		if (readInput()) {
			createGraph();
			vvcnpc.ieSatAssignmentDefined();
			coverGraph();
			assignment = secondAssignment;
			vvcnpc.ieUnsatAssignmentDefined();
			coverGraph();
			vvcnpc.ieEnd();
			MessageUtility.algorithmTerminated(vvcnpc
					.getResource("algorithmName"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vvcnpc.getAlgorithmPath(), vvcnpc
				.getResource("algorithmFileName"));
		formula = (Matrix) il.load("Matrix", vvcnpc
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
		numberNodes = 2 * n + 3 * m;
		numberEdges = n + 6 * m;
		assignment = (Array) il.load("Array", vvcnpc
				.getResource("selectSecondInputMessage"));
		if (assignment.length() != n) {
			return false;
		}
		secondAssignment = (Array) il.load("Array", vvcnpc
				.getResource("selectThirdInputMessage"));
		if (secondAssignment.length() != n) {
			return false;
		}
		return true;
	}
}
