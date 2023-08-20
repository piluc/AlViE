package org.algoritmica.alvie.algorithms;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.datastructure.Queue;
import org.algoritmica.alvie.datastructure.QueueI;
import org.algoritmica.alvie.datastructure.Stack;
import org.algoritmica.alvie.datastructure.StackI;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.ComparableI;
import org.algoritmica.alvie.information.DoubleInformation;
import org.algoritmica.alvie.information.GeometricObjectInformation;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.IntPairInformation;
import org.algoritmica.alvie.utility.GeometricFigureXMLDrawerUtility;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;
public class ClosestPair {
	private class DoublePairInformation implements
			ComparableI<DoublePairInformation> {

		private double firstValue;

		private double secondValue;

		private DoublePairInformation() {
			firstValue = -1;
			secondValue = -1;
		}

		private DoublePairInformation(double first, double second) {
			firstValue = first;
			secondValue = second;
		}

		private DoublePairInformation(String value) {
			int index = value.indexOf(";");
			firstValue = Double.parseDouble(value.substring(1, index));
			secondValue = Double.parseDouble(value.substring(index + 1, value
					.length() - 1));
		}

		public boolean isEqual(DoublePairInformation item) {
			return (firstValue == item.firstValue)
					&& (secondValue == item.secondValue);
		}

		public String stringValue() {
			return "[" + firstValue + ";" + secondValue + "]";
		}
	}

	private class VisualClosestPair extends VisualInnerClass {
		private GeometricFigure stripLine;
		private GeometricFigureXMLDrawerUtility stripLineDrawer;
		private PseudocodeXMLDrawerUtility pseudocodeDrawer,
				subroutinePseudocodeDrawer;
		private Graph<IntInformation, IntInformation> graph;
		private GraphXMLDrawerUtility<IntInformation, IntInformation> graphDrawer;
		private Integer[] oldIdInPosition, idInPosition;
		private String[] color, oneLineColor, threeLineColor, stripColor;
		private String[] font, lineType, stripFont;
		private String[] shape, lineThickness, stripLineThickness;
		private String[] shapeHeight, shapeWidth, arcLabelFont;
		private HashMap<Integer, Integer> positionOfId;
		private int currentMinimumFirstNode, currentMinimumSecondNode,
				leftMinimumFirstNode, leftMinimumSecondNode, minimumFirstNode,
				minimumSecondNode, rightMinimumFirstNode,
				rightMinimumSecondNode;
		private int candidateIndex, midPointIndex;
		private int[] qLIndex, qRIndex;
		private boolean leftCandidateSelection;
		private int arcId;
		private StackI<IntPairInformation> leftMinimumNodePair;
		private Integer[] idOfPositionInSorted;
		private boolean isPseudocodeVisible;
		private double maxX, maxY, minX, minY;
		private int rvx, lvx, offset;

		private VisualClosestPair(String visFile) {
			super("closestPair", visFile);
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			subroutinePseudocodeDrawer = new PseudocodeXMLDrawerUtility(this,
					"subroutinePseudocode");
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			stripLine = new GeometricFigure();
			offset = Integer.parseInt(getResource("splitLineOffset"));
			step = 0;
		}

		private void addHorizontalStrip(int i, int w) {
			int uy = (int) (p.elementAt(i).secondValue + w);
			uy = uy < maxY + offset ? uy : (int) (maxY + offset);
			int ly = (int) (p.elementAt(i).secondValue - w);
			ly = ly > minY - offset ? ly : (int) (minY - offset);
			stripLine.add(new GeometricObjectInformation(
					GeometricObjectInformation.RECTANGLE, lvx
							+ Integer.parseInt(shapeWidth[i]) / 2, maxY - uy,
					rvx - lvx, uy - ly), 1);
			stripLineDrawer = new GeometricFigureXMLDrawerUtility(stripLine,
					getResource("stripLineTitle"), getOutputStream());
			stripLineDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			stripLineDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			stripLineDrawer.setDefaultColor(new Color(Integer.parseInt(
					getResource("splitLineColor"), 16)));
			stripLineDrawer.setDefaultLineThickness(Float
					.parseFloat(getResource("splitLineThickness")));
		}

		private void addSplitLine(int m) {
			stripLine.add(new GeometricObjectInformation(
					GeometricObjectInformation.SEGMENT,
					p.elementAt(m).firstValue + Integer.parseInt(shapeWidth[m])
							/ 2, -offset, p.elementAt(m).firstValue
							+ Integer.parseInt(shapeWidth[m]) / 2, maxY - minY
							+ offset), 0);
			stripLineDrawer = new GeometricFigureXMLDrawerUtility(stripLine,
					getResource("stripLineTitle"), getOutputStream());
			stripLineDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			stripLineDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			stripLineDrawer.setDefaultColor(new Color(Integer.parseInt(
					getResource("splitLineColor"), 16)));
			stripLineDrawer.setDefaultLineThickness(Float
					.parseFloat(getResource("splitLineThickness")));
		}

		private void addVerticalStrip(int l, int m, int r, int w) {
			rvx = (p.elementAt(m).firstValue + w) < p.elementAt(r).firstValue ? (int) (p
					.elementAt(m).firstValue + w)
					: (int) (p.elementAt(r).firstValue);
			lvx = (p.elementAt(m).firstValue - w) > p.elementAt(l).firstValue ? (int) (p
					.elementAt(m).firstValue - w)
					: (int) (p.elementAt(l).firstValue);
			stripLine.add(new GeometricObjectInformation(
					GeometricObjectInformation.RECTANGLE, lvx
							+ Integer.parseInt(shapeWidth[m]) / 2, -offset, rvx
							- lvx, maxY - minY + 2 * offset), 0);
			stripLineDrawer = new GeometricFigureXMLDrawerUtility(stripLine,
					getResource("stripLineTitle"), getOutputStream());
			stripLineDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			stripLineDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			stripLineDrawer.setDefaultColor(new Color(Integer.parseInt(
					getResource("splitLineColor"), 16)));
			stripLineDrawer.setDefaultLineThickness(Float
					.parseFloat(getResource("splitLineThickness")));
		}

		private void createGraph() {
			maxX = p.elementAt(0).firstValue;
			maxY = p.elementAt(0).secondValue;
			minX = maxX;
			minY = maxY;
			for (int i = 1; i < n; i++) {
				if (p.elementAt(i).firstValue > maxX) {
					maxX = p.elementAt(i).firstValue;
				}
				if (p.elementAt(i).secondValue > maxY) {
					maxY = p.elementAt(i).secondValue;
				}
				if (p.elementAt(i).firstValue < minX) {
					minX = p.elementAt(i).firstValue;
				}
				if (p.elementAt(i).secondValue < minY) {
					minY = p.elementAt(i).secondValue;
				}
			}
			graph = new Graph<IntInformation, IntInformation>(false,
					new IntInformation(), new IntInformation());
			positionOfId = new HashMap<Integer, Integer>();
			idInPosition = new Integer[n];
			oldIdInPosition = new Integer[n];
			for (int i = 0; i < n; i++) {
				graph.addNode(new IntInformation(i), i, (int) Math.floor(p
						.elementAt(i).firstValue), (int) (maxY - Math.floor(p
						.elementAt(i).secondValue)));
				positionOfId.put(i, i);
				idInPosition[i] = i;
			}
			qLIndex = new int[n];
			qRIndex = new int[n];
			arcId = 0;
			leftMinimumNodePair = new Stack<IntPairInformation>(
					new IntPairInformation());
			graphDrawer = new GraphXMLDrawerUtility<IntInformation, IntInformation>(
					graph, getResource("graphTitle"), getOutputStream());
			graphDrawer.setDefaultFont(Font.decode(getResource("nodeFont")));
			graphDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			graphDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			graphDrawer.setDefaultShape("Elliptical");
			graphDrawer.setDefaultShapeHeight(Float
					.parseFloat(getResource("nodeHeight")));
			graphDrawer.setDefaultShapeWidth(Float
					.parseFloat(getResource("nodeWidth")));
			setDifferentialDrawArrays();
		}

		private void ieAfterTwoCalls(int l, int m, int r) {
			rightMinimumFirstNode = minimumFirstNode;
			rightMinimumSecondNode = minimumSecondNode;
			leftMinimumFirstNode = leftMinimumNodePair.top().firstValue();
			leftMinimumSecondNode = leftMinimumNodePair.top().secondValue();
			leftMinimumNodePair.pop();
			for (int i = l; i <= m; i++) {
				color[i] = getResource("mergeWaitingLeftNodeColor");
			}
			for (int i = m + 1; i <= r; i++) {
				color[i] = getResource("mergeWaitingRightNodeColor");
			}
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color,
					getResource("ieAfterTwoCalls"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 15 });
			graphDrawer.endStep();
		}

		private void ieBeforeFirstCall(int l, int m, int r) {
			for (int i = l; i <= m; i++) {
				color[i] = getResource("nodeColor");
			}
			for (int i = m + 1; i <= r; i++) {
				color[i] = getResource("inactiveNodeColor");
			}
		}

		private void ieBetweenTwoCalls(int l, int m, int r) {
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { graph.getArcId(
							minimumFirstNode, minimumSecondNode) },
					arcLabelFont, lineType, oneLineColor, lineThickness,
					getResource("ieBetweenTwoCalls"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 14 });
			graphDrawer.endStep();
			leftMinimumNodePair.push(new IntPairInformation(minimumFirstNode,
					minimumSecondNode));
			leftMinimumFirstNode = minimumFirstNode;
			leftMinimumSecondNode = minimumSecondNode;
			for (int i = l; i <= m; i++) {
				color[i] = getResource("inactiveNodeColor");
			}
			for (int i = m + 1; i <= r; i++) {
				color[i] = getResource("nodeColor");
			}
		}

		private void ieCandidateSelectionEnd(int qls, int qrs) {
			for (int i = 0; i < qls; i++) {
				color[qLIndex[i]] = getResource("mergeWaitingLeftNodeColor");
			}
			for (int i = 0; i < qrs; i++) {
				color[qRIndex[i]] = getResource("mergeWaitingRightNodeColor");
			}
			graphDrawer.startStep(step++);
			stripLineDrawer.draw(new Integer[] { 0 }, stripColor, stripFont,
					stripLineThickness, "");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { graph.getArcId(
							minimumFirstNode, minimumSecondNode) },
					arcLabelFont, lineType, oneLineColor, lineThickness,
					getResource("ieCandidateSelectionEnd"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 19 });
			graphDrawer.endStep();
			currentMinimumFirstNode = minimumFirstNode;
			currentMinimumSecondNode = minimumSecondNode;
		}

		private void ieEnd(double cpd) {
			graphDrawer.startStep(step++);
			graphDrawer.draw(null, null, null, null, null, null,
					new Integer[] { graph.getArcId(minimumFirstNode,
							minimumSecondNode) }, arcLabelFont, lineType,
					oneLineColor, lineThickness, getResource("ieEnd"));
			graphDrawer.endStep();
			graphDrawer.endXMLFile();
		}

		private void ieFirstDistanceComputed(int l) {
			graph.addArc(idInPosition[l], idInPosition[l + 1], arcId++);
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { arcId - 1 }, arcLabelFont,
					lineType, threeLineColor, lineThickness,
					getResource("ieFirstDistanceComputed"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 11 });
			graphDrawer.endStep();
			minimumFirstNode = idInPosition[l];
			minimumSecondNode = idInPosition[l + 1];
		}

		private void ieFirstDistanceLessThanSecond(int l) {
			graph.addArc(idInPosition[l], idInPosition[l + 2], arcId++);
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { arcId - 2, arcId - 1 },
					arcLabelFont, lineType, threeLineColor, lineThickness,
					getResource("ieFirstDistanceLessThanSecond"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 11 });
			graphDrawer.endStep();
			graph.removeArc(idInPosition[l], idInPosition[l + 2]);
		}

		private void ieLeftCandidateEnd() {
			removeSecondGraphicObject();
		}

		private void ieLeftCandidateSelectionStart(int l, int m, int r, double d) {
			addVerticalStrip(positionOfId.get(idOfPositionInSorted[l]),
					positionOfId.get(idOfPositionInSorted[m]), positionOfId
							.get(idOfPositionInSorted[r]), (int) d);
			midPointIndex = positionOfId.get(idOfPositionInSorted[m]);
			color[midPointIndex] = getResource("emphasizedNodeColor");
			graphDrawer.startStep(step++);
			stripLineDrawer.draw(new Integer[] { 0 }, stripColor, stripFont,
					stripLineThickness, "");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { graph.getArcId(
							minimumFirstNode, minimumSecondNode) },
					arcLabelFont, lineType, oneLineColor, lineThickness,
					getResource("ieLeftCandidateSelectionStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 17 });
			graphDrawer.endStep();
			leftCandidateSelection = true;
			candidateIndex = 0;
		}

		private void ieMergeStart(int l, int r) {
			for (int i = l; i <= r; i++) {
				oldIdInPosition[i] = idInPosition[i];
			}
		}

		private void ieMinimumDistanceComputed(int l, int m, int r) {
			removeFirstGraphicObject();
			if (minimumFirstNode != currentMinimumFirstNode
					|| minimumSecondNode != currentMinimumSecondNode) {
				graph.removeArc(minimumFirstNode, minimumSecondNode);
			}
			minimumFirstNode = currentMinimumFirstNode;
			minimumSecondNode = currentMinimumSecondNode;
			for (int i = l; i <= m; i++) {
				color[i] = getResource("mergeWaitingLeftNodeColor");
			}
			for (int i = m + 1; i <= r; i++) {
				color[i] = getResource("mergeWaitingRightNodeColor");
			}
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { graph.getArcId(
							minimumFirstNode, minimumSecondNode) },
					arcLabelFont, lineType, threeLineColor, lineThickness,
					getResource("ieMinimumDistanceFound"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 19 });
			graphDrawer.endStep();
			for (int i = l; i <= r; i++) {
				color[i] = getResource("nodeColor");
			}
			if (stripLine.length() > 0) {
				removeFirstGraphicObject();
			}
		}

		private void ieNewDistanceNotMinimum(int l, int r) {
			color[qRIndex[r]] = getResource("emphasizedNodeColor");
			graph.addArc(idInPosition[qLIndex[l]], idInPosition[qRIndex[r]],
					arcId++);
			graphDrawer.startStep(step++);
			stripLineDrawer.draw(new Integer[] { 0, 1 }, stripColor, stripFont,
					stripLineThickness, "");
			if (minimumFirstNode == currentMinimumFirstNode
					&& minimumSecondNode == currentMinimumSecondNode) {
				graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
						shapeWidth, new Integer[] {
								graph.getArcId(currentMinimumFirstNode,
										currentMinimumSecondNode), arcId - 1 },
						arcLabelFont, lineType, threeLineColor, lineThickness,
						getResource("ieNewDistanceNotMinimum"));
			} else {
				graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
						shapeWidth, new Integer[] {
								graph.getArcId(currentMinimumFirstNode,
										currentMinimumSecondNode),
								arcId - 1,
								graph.getArcId(minimumFirstNode,
										minimumSecondNode) }, arcLabelFont,
						lineType, threeLineColor, lineThickness,
						getResource("ieNewDistanceNotMinimum"));
			}
			if (isPseudocodeVisible)
				subroutinePseudocodeDrawer.draw("", new int[] { 13 });
			graphDrawer.endStep();
			color[qRIndex[r]] = getResource("mergeWaitingRightNodeColor");
			graph.removeArc(idInPosition[qLIndex[l]], idInPosition[qRIndex[r]]);
			graph.addArc(minimumFirstNode, minimumSecondNode, arcId++);
		}

		private void ieNewMinimumDistanceFound(int l, int r) {
			color[qRIndex[r]] = getResource("emphasizedNodeColor");
			graph.addArc(idInPosition[qLIndex[l]], idInPosition[qRIndex[r]],
					arcId++);
			graphDrawer.startStep(step++);
			stripLineDrawer.draw(new Integer[] { 0, 1 }, stripColor, stripFont,
					stripLineThickness, "");
			if (minimumFirstNode == currentMinimumFirstNode
					&& minimumSecondNode == currentMinimumSecondNode) {
				graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
						shapeWidth, new Integer[] {
								arcId - 1,
								graph.getArcId(currentMinimumFirstNode,
										currentMinimumSecondNode) },
						arcLabelFont, lineType, threeLineColor, lineThickness,
						getResource("ieNewMinimumDistanceFound"));
			} else {
				graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
						shapeWidth, new Integer[] {
								arcId - 1,
								graph.getArcId(currentMinimumFirstNode,
										currentMinimumSecondNode),
								graph.getArcId(minimumFirstNode,
										minimumSecondNode) }, arcLabelFont,
						lineType, threeLineColor, lineThickness,
						getResource("ieNewMinimumDistanceFound"));
			}
			if (isPseudocodeVisible)
				subroutinePseudocodeDrawer.draw("", new int[] { 13 });
			graphDrawer.endStep();
			graph.removeArc(currentMinimumFirstNode, currentMinimumSecondNode);
			graph.addArc(minimumFirstNode, minimumSecondNode, arcId++);
			currentMinimumFirstNode = idInPosition[qLIndex[l]];
			currentMinimumSecondNode = idInPosition[qRIndex[r]];
			color[positionOfId.get(currentMinimumSecondNode)] = getResource("mergeWaitingRightNodeColor");
		}

		private void ieNewPointPosition(int i, int j) {
			positionOfId.put(oldIdInPosition[i], j);
			idInPosition[j] = oldIdInPosition[i];
		}

		private void ieNewRecursiveCall(int l, int r) {
			boolean isFirst = l == 0 && r == n - 1;
			String msg = getResource("ieNewRecursiveCall");
			if (isFirst) {
				msg = getResource("ieFirstRecursiveCall");
			}
			graphDrawer.startStep(step++);
			if (!isFirst)
				stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, null, null, null, null, null, msg);
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 4 });
			graphDrawer.endStep();
		}

		private void ieNextLeftCandidate(int i, double d) {
			addHorizontalStrip(qLIndex[i], (int) d);
			if (i > 0) {
				color[qLIndex[i - 1]] = getResource("mergeWaitingLeftNodeColor");
			}
			color[qLIndex[i]] = getResource("emphasizedNodeColor");
			graphDrawer.startStep(step++);
			stripLineDrawer.draw(new Integer[] { 0, 1 }, stripColor, stripFont,
					stripLineThickness, "");
			graphDrawer
					.draw(idInPosition, color, font, shape, shapeHeight,
							shapeWidth, new Integer[] {
									graph.getArcId(currentMinimumFirstNode,
											currentMinimumSecondNode),
									graph.getArcId(minimumFirstNode,
											minimumSecondNode),
									graph.getArcId(minimumFirstNode,
											minimumSecondNode) }, arcLabelFont,
							lineType, threeLineColor, lineThickness,
							getResource("ieNextLeftCandidate"));
			if (isPseudocodeVisible)
				subroutinePseudocodeDrawer.draw("", new int[] { 8 });
			graphDrawer.endStep();
		}

		private void ieNextRightCandidateAbove(int j) {
			color[qRIndex[j]] = getResource("nodeColor");
			graphDrawer.startStep(step++);
			stripLineDrawer.draw(new Integer[] { 0, 1 }, stripColor, stripFont,
					stripLineThickness, "");
			graphDrawer
					.draw(idInPosition, color, font, shape, shapeHeight,
							shapeWidth, new Integer[] {
									graph.getArcId(currentMinimumFirstNode,
											currentMinimumSecondNode),
									graph.getArcId(minimumFirstNode,
											minimumSecondNode),
									graph.getArcId(minimumFirstNode,
											minimumSecondNode) }, arcLabelFont,
							lineType, threeLineColor, lineThickness,
							getResource("ieNextRightCandidateAbove"));
			if (isPseudocodeVisible)
				subroutinePseudocodeDrawer.draw("", new int[] { 12 });
			graphDrawer.endStep();
			color[qRIndex[j]] = getResource("mergeWaitingRightNodeColor");
		}

		private void ieNextRightCandidateBelow(int j) {
			color[qRIndex[j]] = getResource("nodeColor");
			graphDrawer.startStep(step++);
			stripLineDrawer.draw(new Integer[] { 0, 1 }, stripColor, stripFont,
					stripLineThickness, "");
			graphDrawer
					.draw(idInPosition, color, font, shape, shapeHeight,
							shapeWidth, new Integer[] {
									graph.getArcId(currentMinimumFirstNode,
											currentMinimumSecondNode),
									graph.getArcId(minimumFirstNode,
											minimumSecondNode),
									graph.getArcId(minimumFirstNode,
											minimumSecondNode) }, arcLabelFont,
							lineType, threeLineColor, lineThickness,
							getResource("ieNextRightCandidateBelow"));
			if (isPseudocodeVisible)
				subroutinePseudocodeDrawer.draw("", new int[] { 10 });
			graphDrawer.endStep();
			color[qRIndex[j]] = getResource("notCandidateNodeColor");
		}

		private void ieNodeIsCandidate(int i) {
			color[i] = getResource("candidateNodeColor");
			graphDrawer.startStep(step++);
			stripLineDrawer.draw(new Integer[] { 0 }, stripColor, stripFont,
					stripLineThickness, "");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { graph.getArcId(
							minimumFirstNode, minimumSecondNode) },
					arcLabelFont, lineType, oneLineColor, lineThickness,
					getResource("ieNodeIsCandidate"));
			if (isPseudocodeVisible)
				subroutinePseudocodeDrawer.draw("", new int[] { 3 });
			graphDrawer.endStep();
			if (leftCandidateSelection) {
				qLIndex[candidateIndex++] = i;
				color[i] = getResource("mergeWaitingLeftNodeColor");
			} else {
				qRIndex[candidateIndex++] = i;
				color[i] = getResource("mergeWaitingRightNodeColor");
			}
			color[midPointIndex] = getResource("emphasizedNodeColor");
		}

		private void ieNodeIsNotCandidate(int i) {
			color[i] = getResource("nodeColor");
			graphDrawer.startStep(step++);
			stripLineDrawer.draw(new Integer[] { 0 }, stripColor, stripFont,
					stripLineThickness, "");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { graph.getArcId(
							minimumFirstNode, minimumSecondNode) },
					arcLabelFont, lineType, oneLineColor, lineThickness,
					getResource("ieNodeIsNotCandidate"));
			if (isPseudocodeVisible)
				subroutinePseudocodeDrawer.draw("", new int[] { 3 });
			graphDrawer.endStep();
			color[i] = getResource("notCandidateNodeColor");
		}

		private void iePointPositionExchanged(int i, int j) {
			int ti = idInPosition[i];
			int tj = idInPosition[j];
			idInPosition[i] = tj;
			idInPosition[j] = ti;
			positionOfId.put(ti, j);
			positionOfId.put(tj, i);
		}

		private void iePointsSorted(int l, int r, boolean onX, int type) {
			String msg1 = getResource("ieSortingPointsOnX");
			String msg2 = getResource("iePointsSortedOnX");
			int[] pseudocodeLine = new int[] { 1 };
			if (!onX) {
				msg1 = getResource("ieSortingPointsOnY");
				msg2 = getResource("iePointsSortedOnY");
			}
			switch (type) {
			case 0:
				break;
			case 1:
				msg2 = msg2 + " " + getResource("constantSortTime");
				pseudocodeLine = new int[] { 6 };
				break;
			case 2:
				msg2 = msg2 + " " + getResource("constantSortTime");
				pseudocodeLine = new int[] { 10 };
				break;
			case 3:
				msg2 = msg2 + " " + getResource("linearSortTime");
				pseudocodeLine = new int[] { 20 };
			}
			for (int i = l; i <= r; i++) {
				graph
						.setNodeInformation(idInPosition[i],
								new IntInformation(i));
				if (Boolean.parseBoolean(getResource("showSortSteps"))) {
					color[i] = getResource("emphasizedNodeColor");
					graphDrawer.startStep(step++);
					if (!onX) {
						stripLineDrawer.draw("");
					}
					graphDrawer.draw(idInPosition, color, null, null, msg1);
					if (isPseudocodeVisible)
						pseudocodeDrawer.draw("", pseudocodeLine);
					graphDrawer.endStep();
					color[i] = getResource("nodeColor");
				}
			}
			graphDrawer.startStep(step++);
			if (!onX) {
				stripLineDrawer.draw("");
			}
			graphDrawer.draw(idInPosition, color, null, null, msg2);
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", pseudocodeLine);
			graphDrawer.endStep();
			if (onX) {
				idOfPositionInSorted = new Integer[n];
				for (int i = 0; i < n; i++) {
					idOfPositionInSorted[i] = idInPosition[i];
				}
			}
		}

		private void ieRightCandidateSelectionStart() {
			graphDrawer.startStep(step++);
			stripLineDrawer.draw(new Integer[] { 0 }, stripColor, stripFont,
					stripLineThickness, "");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { graph.getArcId(
							minimumFirstNode, minimumSecondNode) },
					arcLabelFont, lineType, oneLineColor, lineThickness,
					getResource("ieRightCandidateSelectionStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 18 });
			graphDrawer.endStep();
			leftCandidateSelection = false;
			candidateIndex = 0;
		}

		private void ieSecondDistanceLessThanFirst(int l) {
			graph.addArc(idInPosition[l], idInPosition[l + 2], arcId++);
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { arcId - 1, arcId - 2 },
					arcLabelFont, lineType, threeLineColor, lineThickness,
					getResource("ieSecondDistanceLessThanFirst"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 11 });
			graphDrawer.endStep();
			graph.removeArc(minimumFirstNode, minimumSecondNode);
			minimumFirstNode = idInPosition[l];
			minimumSecondNode = idInPosition[l + 2];
		}

		private void ieSetHalved(int l, int m, int r) {
			addSplitLine(m);
			for (int i = 0; i < l; i++) {
				color[i] = getResource("inactiveNodeColor");
			}
			for (int i = l; i <= m; i++) {
				color[i] = getResource("leftNodeColor");
			}
			for (int i = m + 1; i <= r; i++) {
				color[i] = getResource("rightNodeColor");
			}
			for (int i = r + 1; i < n; i++) {
				color[i] = getResource("inactiveNodeColor");
			}
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color, null, null,
					getResource("ieSetHalved"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 13 });
			graphDrawer.endStep();
		}

		private void ieSmallSortStart(int l, int r) {
			for (int i = l; i <= r; i++) {
				oldIdInPosition[i] = idInPosition[i];
			}
		}

		private void ieStart() {
			createGraph();
			graphDrawer.startXMLFile(getResource("algorithmName"));
			graphDrawer.startStep(step++);
			graphDrawer.draw(getResource("ieStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 0 });
			graphDrawer.endStep();
		}

		private void ieStripWidthComputed(double dl, double dr) {
			boolean left = dl < dr;
			graphDrawer.startStep(step++);
			if (left) {
				stripLineDrawer.draw("");
				graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
						shapeWidth, new Integer[] {
								graph.getArcId(leftMinimumFirstNode,
										leftMinimumSecondNode),
								graph.getArcId(rightMinimumFirstNode,
										rightMinimumSecondNode) },
						arcLabelFont, lineType, threeLineColor, lineThickness,
						getResource("ieStripWidthComputed"));
				if (isPseudocodeVisible)
					pseudocodeDrawer.draw("", new int[] { 16 });
				graphDrawer.endStep();
				graph.removeArc(rightMinimumFirstNode, rightMinimumSecondNode);
				minimumFirstNode = leftMinimumFirstNode;
				minimumSecondNode = leftMinimumSecondNode;
			} else {
				stripLineDrawer.draw("");
				graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
						shapeWidth, new Integer[] {
								graph.getArcId(rightMinimumFirstNode,
										rightMinimumSecondNode),
								graph.getArcId(leftMinimumFirstNode,
										leftMinimumSecondNode) }, arcLabelFont,
						lineType, threeLineColor, lineThickness,
						getResource("ieStripWidthComputed"));
				if (isPseudocodeVisible)
					pseudocodeDrawer.draw("", new int[] { 16 });
				graphDrawer.endStep();
				graph.removeArc(leftMinimumFirstNode, leftMinimumSecondNode);
				minimumFirstNode = rightMinimumFirstNode;
				minimumSecondNode = rightMinimumSecondNode;
			}
		}

		private void ieThirdDistanceMinimum(int l) {
			graph.addArc(idInPosition[l + 1], idInPosition[l + 2], arcId++);
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer
					.draw(idInPosition, color, font, shape, shapeHeight,
							shapeWidth, new Integer[] {
									arcId - 1,
									graph.getArcId(minimumFirstNode,
											minimumSecondNode) }, arcLabelFont,
							lineType, threeLineColor, lineThickness,
							getResource("ieThirdDistanceMinimum"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 11 });
			graphDrawer.endStep();
			graph.removeArc(minimumFirstNode, minimumSecondNode);
			minimumFirstNode = idInPosition[l + 1];
			minimumSecondNode = idInPosition[l + 2];
		}

		private void ieThirdDistanceNotMinimum(int l) {
			graph.addArc(idInPosition[l + 1], idInPosition[l + 2], arcId++);
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer
					.draw(idInPosition, color, font, shape, shapeHeight,
							shapeWidth, new Integer[] {
									graph.getArcId(minimumFirstNode,
											minimumSecondNode), arcId - 1 },
							arcLabelFont, lineType, threeLineColor,
							lineThickness,
							getResource("ieThirdDistanceNotMinimum"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 11 });
			graphDrawer.endStep();
			graph.removeArc(idInPosition[l + 1], idInPosition[l + 2]);
		}

		private void ieThreeNodeMinimumDistanceComputed() {
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { graph.getArcId(
							minimumFirstNode, minimumSecondNode) },
					arcLabelFont, lineType, threeLineColor, lineThickness,
					getResource("ieThreeNodeMinimumDistanceComputed"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 11 });
			graphDrawer.endStep();
		}

		private void ieThreeNodeSet(int l) {
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color, null, null,
					getResource("ieThreeNodeSet"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 10, 11 });
			graphDrawer.endStep();
		}

		private void ieTwoNodeMinimumDistanceComputed() {
			graph.addArc(minimumFirstNode, minimumSecondNode, arcId++);
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color, font, shape, shapeHeight,
					shapeWidth, new Integer[] { graph.getArcId(
							minimumFirstNode, minimumSecondNode) },
					arcLabelFont, lineType, threeLineColor, lineThickness,
					getResource("ieTwoNodeMinimumDistanceComputed"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 7 });
			graphDrawer.endStep();
		}

		private void ieTwoNodeSet(int l) {
			graphDrawer.startStep(step++);
			stripLineDrawer.draw("");
			graphDrawer.draw(idInPosition, color, null, null,
					getResource("ieTwoNodeSet"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6, 7 });
			graphDrawer.endStep();
			minimumFirstNode = idInPosition[l];
			minimumSecondNode = idInPosition[l + 1];
		}

		private void removeFirstGraphicObject() {
			stripLine.remove(0);
			// stripLineDrawer = new GeometricFigureXMLDrawerUtility(stripLine,
			// getResource("stripLineTitle"), getOutputStream());
			// stripLineDrawer.setOriginX(Integer
			// .parseInt(getResource("graphXMLDrawerOriginX")));
			// stripLineDrawer.setOriginY(Integer
			// .parseInt(getResource("graphXMLDrawerOriginY")));
			// stripLineDrawer.setDefaultColor(new Color(Integer.parseInt(
			// getResource("splitLineColor"), 16)));
			// stripLineDrawer.setDefaultLineThickness(Float
			// .parseFloat(getResource("splitLineThickness")));
		}

		private void removeSecondGraphicObject() {
			stripLine.remove(1);
			// stripLineDrawer = new GeometricFigureXMLDrawerUtility(stripLine,
			// getResource("stripLineTitle"), getOutputStream());
			// stripLineDrawer.setOriginX(Integer
			// .parseInt(getResource("graphXMLDrawerOriginX")));
			// stripLineDrawer.setOriginY(Integer
			// .parseInt(getResource("graphXMLDrawerOriginY")));
			// stripLineDrawer.setDefaultColor(new Color(Integer.parseInt(
			// getResource("splitLineColor"), 16)));
			// stripLineDrawer.setDefaultLineThickness(Float
			// .parseFloat(getResource("splitLineThickness")));
		}

		private void setDifferentialDrawArrays() {
			color = new String[n];
			font = new String[n];
			shape = new String[n];
			shapeHeight = new String[n];
			shapeWidth = new String[n];
			for (int i = 0; i < n; i++) {
				color[i] = getResource("nodeColor");
				font[i] = getResource("nodeFont");
				shape[i] = getResource("nodeShape");
				shapeHeight[i] = getResource("nodeHeight");
				shapeWidth[i] = getResource("nodeWidth");
			}
			oneLineColor = new String[] { getResource("stripWidthLineColor") };
			threeLineColor = new String[] { getResource("firstLineColor"),
					getResource("secondLineColor"),
					getResource("stripWidthLineColor") };
			lineType = new String[] { getResource("emphasizedLineType"),
					getResource("emphasizedLineType"),
					getResource("emphasizedLineType") };
			lineThickness = new String[] {
					getResource("emphasizedLineThickness"),
					getResource("emphasizedLineThickness"),
					getResource("emphasizedLineThickness") };
			arcLabelFont = new String[] {
					getResource("emphasizedArcLabelFont"),
					getResource("emphasizedArcLabelFont"),
					getResource("emphasizedArcLabelFont") };
			stripColor = new String[] { getResource("verticalStripColor"),
					getResource("horizontalStripColor") };
			stripLineThickness = new String[] {
					getResource("splitLineThickness"),
					getResource("splitLineThickness") };
			stripFont = new String[] { getResource("nodeFont"),
					getResource("nodeFont") };
		}
	}

	private int n;

	private ArrayI<DoublePairInformation> b, p;
	private VisualClosestPair vcp;

	private double closestPair(int l, int r) {
		vcp.ieNewRecursiveCall(l, r);
		if (r == l + 1) {
			vcp.ieTwoNodeSet(l);
			smallSort(l, r);
			vcp.iePointsSorted(l, r, false, 1);
			double t = distance(l, r);
			vcp.ieTwoNodeMinimumDistanceComputed();
			return t;
		}
		if (r == l + 2) {
			vcp.ieThreeNodeSet(l);
			smallSort(l, r);
			vcp.iePointsSorted(l, r, false, 2);
			double t = distance(l, l + 1);
			vcp.ieFirstDistanceComputed(l);
			if (distance(l, l + 2) < t) {
				vcp.ieSecondDistanceLessThanFirst(l);
				t = distance(l, l + 2);
			} else {
				vcp.ieFirstDistanceLessThanSecond(l);
			}
			if (distance(l + 1, l + 2) < t) {
				vcp.ieThirdDistanceMinimum(l);
				t = distance(l + 1, l + 2);
			} else {
				vcp.ieThirdDistanceNotMinimum(l);
			}
			vcp.ieThreeNodeMinimumDistanceComputed();
			return t;
		}
		int mid = (l + r) / 2;
		vcp.ieSetHalved(l, mid, r);
		double midx = p.elementAt(mid).firstValue;
		vcp.ieBeforeFirstCall(l, mid, r);
		double dl = closestPair(l, mid);
		vcp.ieBetweenTwoCalls(l, mid, r);
		double dr = closestPair(mid + 1, r);
		vcp.ieAfterTwoCalls(l, mid, r);
		double delta = dl < dr ? dl : dr;
		vcp.ieStripWidthComputed(dl, dr);
		vcp.ieLeftCandidateSelectionStart(l, mid, r, delta);
		ArrayI<DoublePairInformation> qL = selectCandidates(l, mid, delta, midx);
		vcp.ieRightCandidateSelectionStart();
		ArrayI<DoublePairInformation> qR = selectCandidates(mid + 1, r, delta,
				midx);
		vcp.ieCandidateSelectionEnd(qL.length(), qR.length());
		double dm = deltaM(qL, qR, delta);
		vcp.ieMinimumDistanceComputed(l, mid, r);
		merge(l, mid, r, false);
		vcp.iePointsSorted(l, r, false, 3);
		return dm < delta ? dm : delta;
	}

	private void compareAndExchange(int i, int j) {
		if (p.elementAt(i).secondValue > p.elementAt(j).secondValue) {
			DoublePairInformation tmp;
			tmp = p.elementAt(i);
			p.setAt(p.elementAt(j), i);
			p.setAt(tmp, j);
			vcp.iePointPositionExchanged(i, j);
		}
	}

	private double deltaM(ArrayI<DoublePairInformation> qL,
			ArrayI<DoublePairInformation> qR, double delta) {
		int j = 0;
		double dm = delta;
		for (int i = 0; i < qL.length(); i++) {
			vcp.ieNextLeftCandidate(i, delta);
			DoublePairInformation p = qL.elementAt(i);
			while (j < qR.length()
					&& qR.elementAt(j).secondValue < p.secondValue - delta) {
				vcp.ieNextRightCandidateBelow(j);
				j = j + 1;
			}
			int k = j;
			while ((k < qR.length())
					&& (qR.elementAt(k).secondValue <= p.secondValue + delta)) {
				if (distance(p, qR.elementAt(k)) < dm) {
					vcp.ieNewMinimumDistanceFound(i, k);
					dm = distance(p, qR.elementAt(k));
				} else {
					vcp.ieNewDistanceNotMinimum(i, k);
				}
				k = k + 1;
			}
			if (k < qR.length()) {
				vcp.ieNextRightCandidateAbove(k);
			}
			vcp.ieLeftCandidateEnd();
		}
		return dm;
	}

	private double distance(DoublePairInformation p, DoublePairInformation q) {
		double deltaX = p.firstValue - q.firstValue;
		double deltaY = p.secondValue - q.secondValue;
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	private double distance(int i, int j) {
		double deltaX = p.elementAt(i).firstValue - p.elementAt(j).firstValue;
		double deltaY = p.elementAt(i).secondValue - p.elementAt(j).secondValue;
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	public void execute(String visFile) {
		vcp = new VisualClosestPair(visFile);
		if (readInput()) {
			vcp.ieStart();
			mergeSort(0, p.length() - 1);
			vcp.iePointsSorted(0, p.length() - 1, true, 0);
			double cpd = closestPair(0, p.length() - 1);
			vcp.ieEnd(cpd);
			MessageUtility
					.algorithmTerminated(vcp.getResource("algorithmName"));
		}
	}

	private void merge(int left, int middle, int right, boolean onX) {
		vcp.ieMergeStart(left, right);
		int i = left;
		int j = middle + 1;
		int k = 0;
		while ((i <= middle) && (j <= right)) {
			if ((onX && p.elementAt(i).firstValue < p.elementAt(j).firstValue)
					|| (!onX && p.elementAt(i).secondValue < p.elementAt(j).secondValue)) {
				b.setAt(p.elementAt(i), k);
				vcp.ieNewPointPosition(i, left + k);
				i = i + 1;
				k = k + 1;
			} else {
				b.setAt(p.elementAt(j), k);
				vcp.ieNewPointPosition(j, left + k);
				j = j + 1;
				k = k + 1;
			}
		}
		for (; i <= middle; i = i + 1, k = k + 1) {
			b.setAt(p.elementAt(i), k);
			vcp.ieNewPointPosition(i, left + k);
		}
		for (; j <= right; j = j + 1, k = k + 1) {
			b.setAt(p.elementAt(j), k);
			vcp.ieNewPointPosition(j, left + k);
		}
		for (i = left; i <= right; i = i + 1) {
			p.setAt(b.elementAt(i - left), i);
		}
	}

	private void mergeSort(int left, int right) {
		if (left < right) {
			int middle = (left + right) / 2;
			mergeSort(left, middle);
			mergeSort(middle + 1, right);
			merge(left, middle, right, true);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vcp.getAlgorithmPath(), vcp
				.getResource("algorithmFileName"));
		Matrix<DoubleInformation> t = (Matrix<DoubleInformation>) inputLoader
				.load("Matrix", vcp.getResource("selectInputMessage"));
		if ((t == null) || (t.width() != 2) || (t.height() == 1))
			return false;
		n = t.height();
		p = new Array<DoublePairInformation>(n, new DoublePairInformation());
		b = new Array<DoublePairInformation>(n, new DoublePairInformation());
		for (int i = 0; i < n; i++) {
			p.setAt(new DoublePairInformation(t.elementAt(i, 0).doubleValue(),
					t.elementAt(i, 1).doubleValue()), i);
			b.setAt(new DoublePairInformation(t.elementAt(i, 0).doubleValue(),
					t.elementAt(i, 1).doubleValue()), i);
		}
		return true;
	}

	private ArrayI<DoublePairInformation> selectCandidates(int l, int r,
			double delta, double midX) {
		QueueI<DoublePairInformation> q = new Queue<DoublePairInformation>(
				new DoublePairInformation());
		for (int i = l; i <= r; i++) {
			if (Math.abs(p.elementAt(i).firstValue - midX) <= delta) {
				q.enqueue(p.elementAt(i));
				vcp.ieNodeIsCandidate(i);
			} else {
				vcp.ieNodeIsNotCandidate(i);
			}
		}
		ArrayI<DoublePairInformation> a = new Array<DoublePairInformation>(q
				.size(), new DoublePairInformation());
		int i = 0;
		while (q.size() > 0) {
			a.setAt(q.first(), i++);
			q.dequeue();
		}
		return a;
	}

	private void smallSort(int l, int r) {
		vcp.ieSmallSortStart(l, r);
		compareAndExchange(l, l + 1);
		if ((r - l) > 1) {
			compareAndExchange(l, l + 2);
			compareAndExchange(l + 1, l + 2);
		}
	}
}
