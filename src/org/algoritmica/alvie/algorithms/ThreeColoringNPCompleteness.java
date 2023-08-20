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
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class ThreeColoringNPCompleteness {
	private class VisualThreeColoringNPCompleteness extends VisualInnerClass {
		private GeometricFigure text;
		private GeometricFigureXMLDrawerUtility textDrawer;
		private Matrix<StringInformation> coloringStringRule;
		private MatrixXMLDrawerUtility<StringInformation> coloringStringRuleDrawer;
		private GraphXMLDrawerUtility<StringInformation, IntInformation> graphDrawer;
		private int nodePairDistance;
		private int nodeWidth;
		private Integer[] nodeId, elementId;
		private String[] nodeColor, elementColor;
		private String[] color;

		private VisualThreeColoringNPCompleteness(String visFile) {
			super("threeColoringNPCompleteness", visFile);
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

		private void createColoringRuleMatrix() {
			coloringStringRule = new Matrix<StringInformation>(8, 8,
					new StringInformation());
			elementId = new Integer[64];
			elementColor = new String[64];
			coloringStringRule.setAt(new StringInformation("l[1]"), 0, 0);
			coloringStringRule.setAt(new StringInformation("l[2]"), 0, 1);
			coloringStringRule.setAt(new StringInformation("l[3]"), 0, 2);
			coloringStringRule.setAt(new StringInformation("a[j]"), 0, 3);
			coloringStringRule.setAt(new StringInformation("b[j]"), 0, 4);
			coloringStringRule.setAt(new StringInformation("c[j]"), 0, 5);
			coloringStringRule.setAt(new StringInformation("d[j]"), 0, 6);
			coloringStringRule.setAt(new StringInformation("e[j]"), 0, 7);
			for (int c = 0; c < 8; c++) {
				elementId[c] = c;
				if (c < 3) {
					elementColor[c] = getResource("literalNodeLabelColor");
				} else {
					elementColor[c] = getResource("clauseNodeLabelColor");
				}
			}
			for (int r = 1; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					coloringStringRule.setAt(new StringInformation(" "), r, c);
					elementId[r * 8 + c] = r * 8 + c;
					elementColor[r * 8 + c] = color[coloringRule[r - 1][c]];
				}
			}
			coloringStringRuleDrawer = new MatrixXMLDrawerUtility<StringInformation>(
					coloringStringRule, getResource("coloringRuleTitle"),
					getOutputStream());
			coloringStringRuleDrawer.setOriginX(Integer
					.parseInt(getResource("coloringRuleXMLDrawerOriginX")));
			coloringStringRuleDrawer.setOriginY(Integer
					.parseInt(getResource("coloringRuleXMLDrawerOriginY")));
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

		private void ieClauseNodesColored(int c) {
			for (int cn = 0; cn < 5; cn++) {
				nodeColor[3 + 2 * n + 5 * c + cn] = color[assignedColor[3 + 2
						* n + 5 * c + cn]];
			}
			int b = formula.elementAt(c, 0).intValue();
			int offset = b % 2 == 0 ? 1 : 0;
			int arcId1 = graph.getArcId(3 + 2 * n + 5 * c, 3 + 2
					* ((b - 1) / 2) + offset);
			b = formula.elementAt(c, 1).intValue();
			offset = b % 2 == 0 ? 1 : 0;
			int arcId2 = graph.getArcId(3 + 2 * n + 5 * c + 1, 3 + 2
					* ((b - 1) / 2) + offset);
			b = formula.elementAt(c, 2).intValue();
			offset = b % 2 == 0 ? 1 : 0;
			int arcId3 = graph.getArcId(3 + 2 * n + 5 * c + 2, 3 + 2
					* ((b - 1) / 2) + offset);
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, new Integer[] { arcId1, arcId2,
					arcId3 }, new String[] { getResource("emphasizedArcColor"),
					getResource("emphasizedArcColor"),
					getResource("emphasizedArcColor") },
					getResource("ieClauseNodesColored"));
			textDrawer.draw("");
			coloringStringRuleDrawer.draw(elementId, elementColor, "");
			graphDrawer.endStep();
		}

		private void ieClauseNodesColoringStarted() {
			createColoringRuleMatrix();
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor,
					getResource("ieClauseNodesColoringStarted"));
			textDrawer.draw("");
			coloringStringRuleDrawer.draw(elementId, elementColor, "");
			graphDrawer.endStep();
		}

		private void ieClauseNodesCreated(int nodeId, int arcId) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { nodeId, nodeId + 1, nodeId + 2,
					nodeId + 3, nodeId + 4 }, new String[] {
					getResource("justCreatedNodeColor"),
					getResource("justCreatedNodeColor"),
					getResource("justCreatedNodeColor"),
					getResource("justCreatedNodeColor"),
					getResource("justCreatedNodeColor") }, new Integer[] {
					arcId, arcId + 1, arcId + 2, arcId + 3, arcId + 4,
					arcId + 5, arcId + 6, arcId + 7, arcId + 8, arcId + 9 },
					new String[] { getResource("emphasizedArcColor"),
							getResource("emphasizedArcColor"),
							getResource("emphasizedArcColor"),
							getResource("emphasizedArcColor"),
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

		private void ieColoring() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, getResource("ieColoring"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieEnd() {
			graphDrawer.endXMLFile();
		}

		private void ieFalseVariableNodesColored(int v) {
			nodeColor[3 + 2 * v] = color[assignedColor[3 + 2 * v]];
			nodeColor[3 + 2 * v + 1] = color[assignedColor[3 + 2 * v + 1]];
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor,
					getResource("ieFalseVariableNodesColored"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieFixedNodesColored() {
			nodeColor[0] = color[0];
			nodeColor[1] = color[1];
			nodeColor[2] = color[2];
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor,
					getResource("ieFixedNodesColored"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieFixedNodesCreated() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(new Integer[] { 0, 1, 2 }, new String[] {
					getResource("justCreatedNodeColor"),
					getResource("justCreatedNodeColor"),
					getResource("justCreatedNodeColor") }, new Integer[] { 0,
					1, 2 }, new String[] { getResource("emphasizedArcColor"),
					getResource("emphasizedArcColor"),
					getResource("emphasizedArcColor") },
					getResource("ieFixedNodesCreated"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieGraphCreated() {
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieGraphCreated"));
			textDrawer.draw("");
			graphDrawer.endStep();
		}

		private void ieNotColoring(int c) {
			nodeColor[3 + 2 * n + 5 * c + 2] = color[assignedColor[3 + 2 * n
					+ 5 * c + 2]];
			nodeColor[3 + 2 * n + 5 * c + 4] = color[assignedColor[3 + 2 * n
					+ 5 * c + 4]];
			int b = formula.elementAt(c, 0).intValue();
			int offset = b % 2 == 0 ? 1 : 0;
			int arcId1 = graph.getArcId(3 + 2 * n + 5 * c, 3 + 2
					* ((b - 1) / 2) + offset);
			b = formula.elementAt(c, 1).intValue();
			offset = b % 2 == 0 ? 1 : 0;
			int arcId2 = graph.getArcId(3 + 2 * n + 5 * c + 1, 3 + 2
					* ((b - 1) / 2) + offset);
			b = formula.elementAt(c, 2).intValue();
			offset = b % 2 == 0 ? 1 : 0;
			int arcId3 = graph.getArcId(3 + 2 * n + 5 * c + 2, 3 + 2
					* ((b - 1) / 2) + offset);
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor, new Integer[] { arcId1, arcId2,
					arcId3 }, new String[] { getResource("emphasizedArcColor"),
					getResource("emphasizedArcColor"),
					getResource("emphasizedArcColor") },
					getResource("ieNotColoring"));
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

		private void ieTrueVariableNodesColored(int v) {
			nodeColor[3 + 2 * v] = color[assignedColor[3 + 2 * v]];
			nodeColor[3 + 2 * v + 1] = color[assignedColor[3 + 2 * v + 1]];
			graphDrawer.startStep(step++);
			graphDrawer.draw(nodeId, nodeColor,
					getResource("ieTrueVariableNodesColored"));
			textDrawer.draw("");
			graphDrawer.endStep();
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
			color = new String[3];
			color[0] = getResource("trueNodeColor");
			color[1] = getResource("falseNodeColor");
			color[2] = getResource("nullNodeColor");
		}

		private void setClauseNodesCoordinate(int c) {
			int y = 3 * c * nodePairDistance;
			int x = 6 * nodePairDistance;
			int nodeId = 3 + 2 * n + 5 * c;
			graph.setNodeCoordinates(nodeId, x, y);
			graph.setNodeCoordinates(nodeId + 1, x, y + nodePairDistance);
			graph.setNodeCoordinates(nodeId + 2, x + 2 * nodePairDistance, y
					+ 2 * nodePairDistance);
			graph.setNodeCoordinates(nodeId + 3, x + nodePairDistance, y
					+ nodePairDistance / 2);
			graph.setNodeCoordinates(nodeId + 4, x + 2 * nodePairDistance, y
					+ nodePairDistance / 2);
		}

		private void setDifferentialDrawArrays() {
			nodeId = new Integer[numberNodes];
			nodeColor = new String[numberNodes];
			for (int i = 0; i < numberNodes; i++) {
				nodeId[i] = i;
				nodeColor[i] = getResource("uncoloredNodeColor");
			}
		}

		private void setFixedNodesCoordinate() {
			int y = ((2 * n - 1) * nodePairDistance) / 2;
			graph.setNodeCoordinates(0, 0, y - nodePairDistance / 2);
			graph.setNodeCoordinates(1, 0, y + nodePairDistance / 2);
			graph.setNodeCoordinates(2, nodePairDistance, y);
		}

		private void setVariableNodesCoordinate(int v) {
			int x = 3 * nodePairDistance;
			int y = 2 * v * nodePairDistance;
			graph.setNodeCoordinates(3 + 2 * v, x, y);
			graph.setNodeCoordinates(3 + 2 * v + 1, x, y + nodePairDistance);
		}
	}

	private VisualThreeColoringNPCompleteness vtcnpc;
	private Matrix<IntInformation> formula;
	private int n;
	private int m;
	private int numberNodes;
	private Graph<StringInformation, IntInformation> graph;
	private Array<BooleanInformation> assignment, secondAssignment;
	private int[] assignedColor;
	private int[][] coloringRule;

	private void colorGraph() {
		assignedColor = new int[numberNodes];
		for (int c = 0; c < numberNodes; c++) {
			assignedColor[c] = -1;
		}
		for (int c = 0; c < 3; c++) {
			assignedColor[c] = c;
		}
		vtcnpc.ieFixedNodesColored();
		for (int v = 0; v < n; v++) {
			if (assignment.elementAt(v).booleanValue()) {
				assignedColor[3 + 2 * v] = 0;
				assignedColor[3 + 2 * v + 1] = 1;
				vtcnpc.ieTrueVariableNodesColored(v);
			} else {
				assignedColor[3 + 2 * v] = 1;
				assignedColor[3 + 2 * v + 1] = 0;
				vtcnpc.ieFalseVariableNodesColored(v);
			}
		}
		createColoringRule();
		vtcnpc.ieClauseNodesColoringStarted();
		for (int c = 0; c < m; c++) {
			int[] l = new int[3];
			for (int i = 0; i < 3; i++) {
				int b = formula.elementAt(c, i).intValue();
				int offset = b % 2 == 0 ? 1 : 0;
				l[i] = assignedColor[3 + 2 * ((b - 1) / 2) + offset];
			}
			int[] cc = computeClauseNodesColor(l);
			if (cc != null) {
				for (int cn = 0; cn < 5; cn++) {
					assignedColor[3 + 2 * n + 5 * c + cn] = cc[cn];
				}
				vtcnpc.ieClauseNodesColored(c);
			} else {
				assignedColor[3 + 2 * n + 5 * c + 2] = 2;
				assignedColor[3 + 2 * n + 5 * c + 4] = 1;
				vtcnpc.ieNotColoring(c);
				return;
			}
		}
		vtcnpc.ieColoring();
	}

	private int[] computeClauseNodesColor(int[] l) {
		int r = 0;
		boolean found = false;
		while (!found && r < 7) {
			if (coloringRule[r][0] == l[0] && coloringRule[r][1] == l[1]
					&& coloringRule[r][2] == l[2]) {
				found = true;
			} else {
				r = r + 1;
			}
		}
		if (!found) {
			return null;
		}
		int[] rst = new int[5];
		for (int c = 0; c < 5; c++) {
			rst[c] = coloringRule[r][3 + c];
		}
		return rst;
	}

	private void createColoringRule() {
		coloringRule = new int[7][8];
		int row = 0;
		coloringRule[row][0] = 1;
		coloringRule[row][1] = 1;
		coloringRule[row][2] = 0;
		coloringRule[row][3] = 2;
		coloringRule[row][4] = 0;
		coloringRule[row][5] = 1;
		coloringRule[row][6] = 1;
		coloringRule[row][7] = 2;
		row = 1;
		coloringRule[row][0] = 1;
		coloringRule[row][1] = 0;
		coloringRule[row][2] = 1;
		coloringRule[row][3] = 0;
		coloringRule[row][4] = 1;
		coloringRule[row][5] = 2;
		coloringRule[row][6] = 2;
		coloringRule[row][7] = 1;
		row = 2;
		coloringRule[row][0] = 1;
		coloringRule[row][1] = 0;
		coloringRule[row][2] = 0;
		coloringRule[row][3] = 0;
		coloringRule[row][4] = 1;
		coloringRule[row][5] = 2;
		coloringRule[row][6] = 2;
		coloringRule[row][7] = 1;
		row = 3;
		coloringRule[row][0] = 0;
		coloringRule[row][1] = 1;
		coloringRule[row][2] = 1;
		coloringRule[row][3] = 1;
		coloringRule[row][4] = 0;
		coloringRule[row][5] = 2;
		coloringRule[row][6] = 2;
		coloringRule[row][7] = 1;
		row = 4;
		coloringRule[row][0] = 0;
		coloringRule[row][1] = 1;
		coloringRule[row][2] = 0;
		coloringRule[row][3] = 1;
		coloringRule[row][4] = 0;
		coloringRule[row][5] = 2;
		coloringRule[row][6] = 2;
		coloringRule[row][7] = 1;
		row = 5;
		coloringRule[row][0] = 0;
		coloringRule[row][1] = 0;
		coloringRule[row][2] = 1;
		coloringRule[row][3] = 2;
		coloringRule[row][4] = 1;
		coloringRule[row][5] = 2;
		coloringRule[row][6] = 0;
		coloringRule[row][7] = 1;
		row = 6;
		coloringRule[row][0] = 0;
		coloringRule[row][1] = 0;
		coloringRule[row][2] = 0;
		coloringRule[row][3] = 2;
		coloringRule[row][4] = 1;
		coloringRule[row][5] = 2;
		coloringRule[row][6] = 0;
		coloringRule[row][7] = 1;
	}

	private void createGraph() {
		graph = new Graph<StringInformation, IntInformation>(false,
				new StringInformation(), new IntInformation());
		vtcnpc.init();
		vtcnpc.ieStart();
		graph.addNode(new StringInformation("T"), 0, 0, 0);
		graph.addNode(new StringInformation("F"), 1, 0, 0);
		graph.addNode(new StringInformation("N"), 2, 0, 0);
		vtcnpc.setFixedNodesCoordinate();
		graph.addArc(0, 1, 0);
		graph.addArc(0, 2, 1);
		graph.addArc(1, 2, 2);
		vtcnpc.ieFixedNodesCreated();
		for (int v = 0; v < n; v++) {
			int nodeId = 3 + 2 * v;
			int arcId = 3 + 3 * v;
			graph.addNode(new StringInformation("p[" + (v + 1) + "]"), nodeId,
					0, 0);
			graph.addNode(new StringInformation("n[" + (v + 1) + "]"),
					nodeId + 1, 0, 0);
			vtcnpc.setVariableNodesCoordinate(v);
			graph.addArc(nodeId, 2, arcId);
			graph.addArc(nodeId + 1, 2, arcId + 1);
			graph.addArc(nodeId, nodeId + 1, arcId + 2);
			vtcnpc.ieVariableNodesCreated(nodeId, arcId);
		}
		for (int c = 0; c < m; c++) {
			int nodeId = 3 + 2 * n + 5 * c;
			int arcId = 3 + 3 * n + 10 * c;
			graph.addNode(new StringInformation("a[" + (c + 1) + "]"), nodeId,
					0, 0);
			graph.addNode(new StringInformation("b[" + (c + 1) + "]"),
					nodeId + 1, 0, 0);
			graph.addNode(new StringInformation("c[" + (c + 1) + "]"),
					nodeId + 2, 0, 0);
			graph.addNode(new StringInformation("d[" + (c + 1) + "]"),
					nodeId + 3, 0, 0);
			graph.addNode(new StringInformation("e[" + (c + 1) + "]"),
					nodeId + 4, 0, 0);
			graph.addArc(nodeId, nodeId + 1, arcId);
			graph.addArc(nodeId, nodeId + 3, arcId + 1);
			graph.addArc(nodeId + 1, nodeId + 3, arcId + 2);
			graph.addArc(nodeId + 3, nodeId + 4, arcId + 3);
			graph.addArc(nodeId + 4, nodeId + 2, arcId + 4);
			graph.addArc(nodeId + 2, 0, arcId + 5);
			graph.addArc(nodeId + 4, 0, arcId + 6);
			for (int l = 0; l < 3; l++) {
				int v = formula.elementAt(c, l).intValue();
				if (v % 2 == 0) {
					graph.addArc(nodeId + l, 4 + 2 * ((v - 1) / 2), arcId + 7
							+ l);
				} else {
					graph.addArc(nodeId + l, 3 + 2 * (v / 2), arcId + 7 + l);
				}
			}
			vtcnpc.setClauseNodesCoordinate(c);
			vtcnpc.ieClauseNodesCreated(nodeId, arcId);
		}
		vtcnpc.ieGraphCreated();
	}

	public void execute(String visFile) {
		vtcnpc = new VisualThreeColoringNPCompleteness(visFile);
		if (readInput()) {
			createGraph();
			createColoringRule();
			vtcnpc.ieSatAssignmentDefined();
			colorGraph();
			assignment = secondAssignment;
			vtcnpc.ieUnsatAssignmentDefined();
			colorGraph();
			vtcnpc.ieEnd();
			MessageUtility.algorithmTerminated(vtcnpc
					.getResource("algorithmName"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader il = new InputLoader(vtcnpc.getAlgorithmPath(), vtcnpc
				.getResource("algorithmFileName"));
		formula = (Matrix) il.load("Matrix", vtcnpc
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
		numberNodes = 3 + 2 * n + 5 * m;
		assignment = (Array) il.load("Array", vtcnpc
				.getResource("selectSecondInputMessage"));
		if (assignment.length() != n) {
			return false;
		}
		secondAssignment = (Array) il.load("Array", vtcnpc
				.getResource("selectThirdInputMessage"));
		if (secondAssignment.length() != n) {
			return false;
		}
		return true;
	}
}
