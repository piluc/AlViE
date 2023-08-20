package org.algoritmica.alvie.algorithms;

import java.awt.Font;
import java.util.Vector;

import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class LCS {
	private class VisualLongestCommonSequence extends VisualInnerClass {
		private Graph<StringInformation, StringInformation> editGraph;

		private GraphXMLDrawerUtility<StringInformation, StringInformation> editGraphDrawer;

		private Integer[] index;

		private String[] color;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private String[] dir = { ";N", ";W", ";NW" };

		private Vector<Integer> indexVector;

		private Vector<String> colorVector;

		private VisualLongestCommonSequence(String visFile) {
			super("lcs", visFile);
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
					if (v.charAt(i - 1) == w.charAt(j - 1)) {
						editGraph.addArc((m + 2) * i + j, (m + 2) * (i + 1) + j
								+ 1, (n + 2) * (m + 1) + (n + 1) * (m + 2)
								+ k++);
					}
				}
			}
			editGraph.setNodeInformation(1, new StringInformation("e"));
			editGraph.setNodeInformation(m + 2, new StringInformation("e"));
			for (int i = 2; i <= n + 1; i++) {
				editGraph.setNodeInformation((m + 2) * i,
						new StringInformation("" + v.charAt(i - 2)));
			}
			for (int j = 2; j <= m + 1; j++) {
				editGraph.setNodeInformation(j, new StringInformation(""
						+ w.charAt(j - 2)));
			}
			editGraph.removeNode(0);
			editGraph.removeArc(0);
			editGraph.removeArc((n + 2) * (m + 1));
		}

		private void ieDifferentCharacters(int i, int j, int f) {
			int firstChar = (m + 2) * (i + 1);
			int secondChar = j + 1;
			int newNode = (m + 2) * (i + 1) + (j + 1);
			int firstPreviousNode = (m + 2) * (i + 1) + j;
			int secondPreviousNode = (m + 2) * i + j + 1;
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(new Integer[] { firstChar, secondChar,
					newNode, firstPreviousNode, secondPreviousNode }, color,
					getResource("ieDifferentCharacters"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 10, 11, 12, 13, 14, 15,
						16 });
			}
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
			if (isPseudocodeVisible) {
				if (d == 0) {
					pseudocodeDrawer.draw("", new int[] { 14, 15 });
				} else if (d == 1) {
					pseudocodeDrawer.draw("", new int[] { 11, 12 });
				} else {
					pseudocodeDrawer.draw("", new int[] { 8, 9 });
				}
			}
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

		private void ieLCSEnded() {
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(new Integer[] { (m + 2) * (n + 2) - 1 },
					color, getResource("ieLCSEnded"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
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
			editGraphDrawer.endXMLFile();
		}

		private void ieEqualCharacters(int i, int j) {
			int firstChar = (m + 2) * (i + 1);
			int secondChar = j + 1;
			int newNode = (m + 2) * (i + 1) + j + 1;
			int previousNode = (m + 2) * i + j;
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(new Integer[] { firstChar, secondChar,
					newNode, previousNode }, color,
					getResource("ieEqualCharacters"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 8, 9 });
			}
			editGraphDrawer.endStep();
		}

		private void ieSInitialized() {
			editGraph.setNodeInformation((m + 2) + 1,
					new StringInformation("0"));
			for (int i = 2; i <= n + 1; i++) {
				editGraph.setNodeInformation((m + 2) * i + 1,
						new StringInformation("0;N"));
			}
			for (int j = 2; j <= m + 1; j++) {
				editGraph.setNodeInformation((m + 2) + j,
						new StringInformation("0;W"));
			}
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(getResource("ieSInitialized"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 1, 2, 3, 4 });
			}
			editGraphDrawer.endStep();
		}

		private void ieStart() {
			editGraphDrawer.startXMLFile(getResource("algorithmName"));
			editGraphDrawer.startStep(step++);
			editGraphDrawer.draw(getResource("ieStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
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
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			step = 0;
			initDifferentialDrawArrays();
		}

		private void initDifferentialDrawArrays() {
			color = new String[5];
			color[0] = getResource("editGraphCharNodeColor");
			color[1] = getResource("editGraphCharNodeColor");
			color[2] = getResource("editGraphNewNodeColor");
			color[3] = getResource("editGraphPreviousNodeColor");
			color[4] = getResource("editGraphPreviousNodeColor");
		}
	}

	private String v, w;

	private int[][] s;

	private int[][] b;

	private int m, n;

	VisualLongestCommonSequence vlcs;

	public void execute(String visFile) {
		vlcs = new VisualLongestCommonSequence(visFile);
		if (readInput()) {
			lcs();
			printLCS(n, m);
			vlcs.ieEnd();
			MessageUtility.algorithmTerminated(vlcs.getResource("algorithmName"));
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

	private void lcs() {
		vlcs.init();
		vlcs.ieStart();
		for (int i = 0; i <= n; i++) {
			s[i][0] = 0;
		}
		for (int j = 0; j <= m; j++) {
			s[0][j] = 0;
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				s[i][j] = -1;
			}
		}
		vlcs.ieSInitialized();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				if (v.charAt(i - 1) == w.charAt(j - 1)) {
					vlcs.ieEqualCharacters(i, j);
					s[i][j] = s[i - 1][j - 1] + 1;
					b[i][j] = 2;
				} else if (s[i][j - 1] > s[i - 1][j]) {
					vlcs.ieDifferentCharacters(i, j, 0);
					s[i][j] = s[i][j - 1];
					b[i][j] = 1;
				} else {
					vlcs.ieDifferentCharacters(i, j, 1);
					s[i][j] = s[i - 1][j];
					b[i][j] = 0;
				}
				vlcs.ieElementComputed(i, j, b[i][j]);
			}
		}
		vlcs.ieLCSEnded();
	}

	private boolean readInput() {
		v = vlcs.getResource("v");
		w = vlcs.getResource("w");
		if (v == null || w == null) {
			return false;
		}
		n = v.length();
		m = w.length();
		s = new int[n + 1][m + 1];
		b = new int[n + 1][m + 1];
		return true;
	}
}
