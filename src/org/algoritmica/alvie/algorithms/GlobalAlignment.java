package org.algoritmica.alvie.algorithms;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.GeometricObjectInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.GeometricFigureXMLDrawerUtility;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class GlobalAlignment {
	private class VisualGlobalAlignment extends VisualInnerClass {
		private GeometricFigure labels;

		private GeometricFigureXMLDrawerUtility labelsDrawer;

		private Graph<StringInformation, StringInformation> editGraph;

		private GraphXMLDrawerUtility<StringInformation, StringInformation> editGraphDrawer;

		private Integer[] index;

		private String[] color;

		private String[] dir = { ";N", ";W", ";NW", ";NW" };

		private Vector<Integer> indexVector;

		private Vector<String> colorVector;

		private VisualGlobalAlignment(String visFile) {
			super("globalAlignment", visFile);
		}

		private void createGraph() {
			int deltaX = Integer.parseInt(getResource("deltaX"));
			int deltaY = Integer.parseInt(getResource("deltaY"));
			editGraph = new Graph<StringInformation, StringInformation>(true,
					new StringInformation(), new StringInformation());
			for (int i = 0; i <= n + 1; i++) {
				for (int j = 0; j <= m + 1; j++) {
					editGraph.addNode(new StringInformation(" "), (m + 2) * i
							+ j, deltaX * j, deltaY * i);
				}
			}
			for (int i = 1; i <= n + 1; i++) {
				for (int j = 1; j <= m; j++) {
					editGraph.addArc((m + 2) * i + j, (m + 2) * i + j + 1,
							(m + 1) * i + j);
				}
			}
			for (int j = 1; j <= m + 1; j++) {
				for (int i = 1; i <= n; i++) {
					editGraph.addArc((m + 2) * i + j, (m + 2) * (i + 1) + j,
							(n + 2) * (m + 1) + (n + 1) * j + i);
				}
			}
			int k = 1;
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= m; j++) {
					editGraph.addArc((m + 2) * i + j,
							(m + 2) * (i + 1) + j + 1, (n + 2) * (m + 1)
									+ (n + 1) * (m + 2) + k++);
				}
			}
			editGraph.setNodeInformation(1, new StringInformation("e"));
			editGraph.setNodeInformation(m + 2, new StringInformation("e"));
			for (int i = 2; i <= n + 1; i++) {
				editGraph.setNodeInformation((m + 2) * i,
						new StringInformation("" + v.charAt(i - 2)));
			}
			for (int j = 2; j <= m + 1; j++) {
				editGraph.setNodeInformation(j,
						new StringInformation("" + w.charAt(j - 2)));
			}
			editGraph.removeNode(0);
			editGraph.removeArc(0);
			editGraph.removeArc((n + 2) * (m + 1));
		}

		private void ieFromTop(int i, int j, int f) {
			int firstChar = (m + 2) * (i + 1);
			int secondChar = j + 1;
			int newNode = (m + 2) * (i + 1) + (j + 1);
			int previousNorthNode = (m + 2) * i + j + 1;
			int previousWestNode = (m + 2) * (i + 1) + j;
			int previousNorthWestNode = (m + 2) * i + j;
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(new Integer[] { firstChar, secondChar,
					newNode, previousNorthNode, previousWestNode,
					previousNorthWestNode }, color,
					getResource("ieDifferentCharacters"));
			labelsDrawer.draw("");
			editGraphDrawer.endStep();
		}

		private void ieFromLeft(int i, int j, int f) {
			int firstChar = (m + 2) * (i + 1);
			int secondChar = j + 1;
			int newNode = (m + 2) * (i + 1) + (j + 1);
			int previousNorthNode = (m + 2) * i + j + 1;
			int previousWestNode = (m + 2) * (i + 1) + j;
			int previousNorthWestNode = (m + 2) * i + j;
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(new Integer[] { firstChar, secondChar,
					newNode, previousNorthNode, previousWestNode,
					previousNorthWestNode }, color,
					getResource("ieDifferentCharacters"));
			labelsDrawer.draw("");
			editGraphDrawer.endStep();
		}

		private void ieDifferentCharacters(int i, int j, int f) {
			int firstChar = (m + 2) * (i + 1);
			int secondChar = j + 1;
			int newNode = (m + 2) * (i + 1) + (j + 1);
			int previousNorthNode = (m + 2) * i + j + 1;
			int previousWestNode = (m + 2) * (i + 1) + j;
			int previousNorthWestNode = (m + 2) * i + j;
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(new Integer[] { firstChar, secondChar,
					newNode, previousNorthNode, previousWestNode,
					previousNorthWestNode }, color,
					getResource("ieDifferentCharacters"));
			labelsDrawer.draw("");
			editGraphDrawer.endStep();
		}

		private void ieElementComputed(int i, int j, int d) {
			int firstChar = (m + 2) * (i + 1);
			int secondChar = j + 1;
			int newNode = (m + 2) * (i + 1) + j + 1;
			int previousNode = (m + 2) * i + j + 1;
			if (d == 1) {
				previousNode = (m + 2) * (i + 1) + j;
			}
			if (d == 2) {
				previousNode = (m + 2) * i + j;
			}
			editGraph.setNodeInformation(newNode, new StringInformation(""
					+ s[i][j] + dir[d]));
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(new Integer[] { firstChar, secondChar,
					newNode, previousNode }, color,
					getResource("ieElementComputed"));
			labelsDrawer.draw("");
			editGraphDrawer.endStep();
		}

		private void ieNextCharInSequence(int i, int j, int d) {
			if (d == 2 && i > 0 && j > 0) {
				int firstChar = (m + 2) * (i + 1);
				int secondChar = j + 1;
				indexVector.add(firstChar);
				colorVector.add(getResource("sequenceChar"));
				indexVector.add(secondChar);
				colorVector.add(getResource("sequenceChar"));
			}
			int node = (m + 2) * (i + 1) + j + 1;
			indexVector.add(node);
			colorVector.add(getResource("sequenceNode"));
		}

		private void ieGlobalALignmentEnded() {
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(new Integer[] { (m + 2) * (n + 2) - 1 },
					color, getResource("ieGlobalALignmentEnded"));
			labelsDrawer.draw("");
			editGraphDrawer.endStep();
			indexVector = new Vector<Integer>();
			colorVector = new Vector<String>();
		}

		private void ieEnd() {
			index = new Integer[indexVector.size()];
			color = new String[colorVector.size()];
			for (int i = 0; i < index.length; i++) {
				index[i] = indexVector.elementAt(i);
				color[i] = colorVector.elementAt(i);
			}
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(index, color, getResource("ieEnd"));
			editGraphDrawer.endStep();
			labelsDrawer.draw("");
			editGraphDrawer.endXMLFile();
		}

		private void ieEqualCharacters(int i, int j) {
			int firstChar = (m + 2) * (i + 1);
			int secondChar = j + 1;
			int newNode = (m + 2) * (i + 1) + j + 1;
			int previousNorthNode = (m + 2) * i + j + 1;
			int previousWestNode = (m + 2) * (i + 1) + j;
			int previousNorthWestNode = (m + 2) * i + j;
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(new Integer[] { firstChar, secondChar,
					newNode, previousNorthNode, previousWestNode,
					previousNorthWestNode }, color,
					getResource("ieEqualCharacters"));
			labelsDrawer.draw("");
			editGraphDrawer.endStep();
		}

		private void ieSInitialized() {
			editGraph.setNodeInformation((m + 2) + 1,
					new StringInformation("0"));
			for (int i = 2; i <= n + 1; i++) {
				editGraph.setNodeInformation((m + 2) * i + 1,
						new StringInformation(s[i - 1][0] + ";N"));
			}
			for (int j = 2; j <= m + 1; j++) {
				editGraph.setNodeInformation((m + 2) + j,
						new StringInformation(s[0][j - 1] + ";W"));
			}
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(getResource("ieSInitialized"));
			labelsDrawer.draw("");
			editGraphDrawer.endStep();
		}

		private void ieStart() {
			editGraphDrawer.startXMLFile(getResource("algorithmName"));
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(getResource("ieStart"));
			labelsDrawer.draw("");
			editGraphDrawer.endStep();
		}

		private void init() {
			createGraph();
			editGraphDrawer = new GraphXMLDrawerUtility<StringInformation, StringInformation>(
					editGraph, getResource("editGraphTitle"), getOutputStream());
			editGraphDrawer.setOriginX(Integer
					.parseInt(getResource("editGraphXMLDrawerOriginX")));
			editGraphDrawer.setOriginY(Integer
					.parseInt(getResource("editGraphXMLDrawerOriginY")));
			editGraphDrawer.setDefaultFont(Font
					.decode(getResource("editGraphFont")));
			labels = new GeometricFigure();
			labels
					.add(new GeometricObjectInformation(
							getResource("matchPremiumName")+": "+getResource("matchPremium"),
							Integer
									.parseInt(getResource("labelsXMLDrawerOriginX")),
							Integer
									.parseInt(getResource("labelsXMLDrawerOriginY"))));
			labels
			.add(new GeometricObjectInformation(
					getResource("mismatchPenaltyName")+": "+getResource("mismatchPenalty"),
					Integer
							.parseInt(getResource("labelsXMLDrawerOriginX")),
					Integer
							.parseInt(getResource("labelsXMLDrawerOriginY")) + 50));
			labels
			.add(new GeometricObjectInformation(
					getResource("indelPenaltyName")+": "+getResource("indelPenalty"),
					Integer
							.parseInt(getResource("labelsXMLDrawerOriginX")),
					Integer
							.parseInt(getResource("labelsXMLDrawerOriginY")) + 100));
			labelsDrawer = new GeometricFigureXMLDrawerUtility(labels,
					getResource("labelsTitle"), getOutputStream());
			labelsDrawer.setDefaultColor(new Color(Integer.parseInt(
					getResource("labelsColor"), 16)));
			step = 0;
			initDifferentialDrawArrays();
		}

		private void initDifferentialDrawArrays() {
			color = new String[6];
			color[0] = getResource("editGraphCharNodeColor");
			color[1] = getResource("editGraphCharNodeColor");
			color[2] = getResource("editGraphNewNodeColor");
			color[3] = getResource("editGraphPreviousNorthNodeColor");
			color[4] = getResource("editGraphPreviousWestNodeColor");
			color[5] = getResource("editGraphPreviousNorthWestNodeColor");
		}
	}

	private String v, w;

	private int[][] s;

	private int[][] b;

	private int m, n;

	private int pi, mu, sigma;

	VisualGlobalAlignment vlcs;

	public void execute(String visFile) {
		vlcs = new VisualGlobalAlignment(visFile);
		if (readInput()) {
			globalALignment();
			printLCS(n, m);
			vlcs.ieEnd();
			MessageUtility.algorithmTerminated(vlcs
					.getResource("algorithmName"));
		}
	}

	private void printLCS(int i, int j) {
		if (i < 0 || j < 0) {
			return;
		}
		vlcs.ieNextCharInSequence(i, j, b[i][j]);
		if (b[i][j] == 2) {
			printLCS(i - 1, j - 1);
		} else if (b[i][j] == 1) {
			printLCS(i, j - 1);
		} else {
			printLCS(i - 1, j);
		}
	}

	private int max(int i, int j) {
		if (v.charAt(i - 1) == w.charAt(j - 1)) {
			if (s[i - 1][j] - sigma > s[i][j - 1] - sigma
					&& s[i - 1][j] - sigma > s[i - 1][j - 1] + pi) {
				return 1;
			} else if (s[i][j - 1] - sigma > s[i - 1][j] - sigma
					&& s[i][j - 1] - sigma > s[i - 1][j - 1] + pi) {
				return 2;
			} else
				return 4;
		} else {
			if (s[i - 1][j] - sigma > s[i][j - 1] - sigma
					&& s[i - 1][j] - sigma > s[i - 1][j - 1] - mu) {
				return 1;
			} else if (s[i][j - 1] - sigma > s[i - 1][j] - sigma
					&& s[i][j - 1] - sigma > s[i - 1][j - 1] - mu) {
				return 2;
			} else
				return 3;
		}
	}

	private void globalALignment() {
		vlcs.init();
		vlcs.ieStart();
		for (int i = 0; i <= n; i++) {
			s[i][0] = -mu * i;
		}
		for (int j = 0; j <= m; j++) {
			s[0][j] = -sigma * j;
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				s[i][j] = -Integer.MAX_VALUE;
			}
		}
		vlcs.ieSInitialized();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				int max = max(i, j);
				if (max == 4) {
					vlcs.ieEqualCharacters(i, j);
					s[i][j] = s[i - 1][j - 1] + pi;
					b[i][j] = 2;
				} else if (max == 3) {
					vlcs.ieDifferentCharacters(i, j, 0);
					s[i][j] = s[i - 1][j - 1] - mu;
					b[i][j] = 2;
				} else if (max == 2) {
					vlcs.ieFromLeft(i, j, 0);
					s[i][j] = s[i][j - 1] - sigma;
					b[i][j] = 1;
				} else {
					vlcs.ieFromTop(i, j, 1);
					s[i][j] = s[i - 1][j] - sigma;
					b[i][j] = 0;
				}
				vlcs.ieElementComputed(i, j, b[i][j]);
			}
		}
		vlcs.ieGlobalALignmentEnded();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vlcs.getAlgorithmPath(),
				vlcs.getResource("algorithmFileName"));
		Array<StringInformation> input = (Array) inputLoader.load("Array",
				vlcs.getResource("selectInputMessage"));
		if (input == null || input.length() != 2) {
			return false;
		}
		v = input.elementAt(0).stringValue();
		w = input.elementAt(1).stringValue();
		n = v.length();
		m = w.length();
		s = new int[n + 1][m + 1];
		b = new int[n + 1][m + 1];
		pi = Integer.parseInt(vlcs.getResource("matchPremium"));
		mu = Integer.parseInt(vlcs.getResource("mismatchPenalty"));
		sigma = Integer.parseInt(vlcs.getResource("indelPenalty"));
		return true;
	}
}
