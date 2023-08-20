package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class LongestPath {
	private class VisualLongestPath extends VisualInnerClass {
		private MatrixXMLDrawerUtility<StringInformation> gridDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private boolean isPseudocodeVisible;

		private Integer[] index;

		private String[] color;

		private VisualLongestPath(String visFileName) {
			super("longestPath", visFileName);
		}

		private void ieEnd() {
			color[(2 * n + 1) * (2 * m + 1)-1] = getResource("newLongestPathColor");
			gridDrawer.startStep(step++);
			gridDrawer.draw(index, color, getResource("ieEnd"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 9 });
			}
			gridDrawer.endStep();
			gridDrawer.endXMLFile();
		}

		private void ieFirstLongestPathComputed() {
			color[0] = getResource("newLongestPathColor");
			gridDrawer.startStep(step++);
			gridDrawer.draw(index, color,
					getResource("ieFirstLongestPathComputed"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 1 });
			}
			gridDrawer.endStep();
			color[0] = getResource("gridNodeColor");
		}

		private void ieNewLongestPathComputationEnded(int x, int y, int z) {
			color[x] = getResource("newElementColor");
			color[y] = getResource("previousElementColor");
			color[z] = getResource("previousElementColor");
			gridDrawer.startStep(step++);
			gridDrawer.draw(index, color,
					getResource("ieNewLongestPathComputationEnded"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 8 });
			}
			gridDrawer.endStep();
			color[x] = getResource("gridNodeColor");
			color[y] = getResource("gridNodeColor");
			color[z] = getResource("gridNodeColor");
		}

		private void ieNewLongestPathComputationStarted(int x, int y, int z) {
			color[x] = getResource("newElementColor");
			color[y] = getResource("previousElementColor");
			color[z] = getResource("previousElementColor");
			gridDrawer.startStep(step++);
			gridDrawer.draw(index, color,
					getResource("ieNewLongestPathComputationStarted"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 8 });
			}
			gridDrawer.endStep();
		}

		private void ieNewFirstColumnElementComputed(int x, int y) {
			color[x] = getResource("newElementColor");
			color[y] = getResource("previousElementColor");
			gridDrawer.startStep(step++);
			gridDrawer.draw(index, color,
					getResource("ieNewFirstColumnElementComputed"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 3 });
			}
			gridDrawer.endStep();
			color[x] = getResource("gridNodeColor");
			color[y] = getResource("gridNodeColor");
		}

		private void ieNewFirstRowElementComputed(int x, int y) {
			color[x] = getResource("newElementColor");
			color[y] = getResource("previousElementColor");
			gridDrawer.startStep(step++);
			gridDrawer.draw(index, color,
					getResource("ieNewFirstRowElementComputed"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", new int[] { 5 });
			}
			gridDrawer.endStep();
			color[x] = getResource("gridNodeColor");
			color[y] = getResource("gridNodeColor");
		}

		private void ieStart() {
			gridDrawer.startXMLFile(getResource("algorithmName"));
			gridDrawer.startStep(step++);
			gridDrawer.draw(index, color, getResource("ieStart"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", null);
			}
			gridDrawer.endStep();
		}

		private void init() {
			gridDrawer = new MatrixXMLDrawerUtility<StringInformation>(grid,
					getResource("gridTitle"), getOutputStream());
			gridDrawer.setOriginX(Integer
					.parseInt(getResource("gridXMLDrawerOriginX")));
			gridDrawer.setOriginY(Integer
					.parseInt(getResource("gridXMLDrawerOriginY")));
			gridDrawer.setDefaultFont(Font.decode(getResource("gridFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			setArraysDifferentialDrawArrays();
			step = 0;
		}

		private void setArraysDifferentialDrawArrays() {
			index = new Integer[(2 * n + 1) * (2 * m + 1)];
			color = new String[(2 * n + 1) * (2 * m + 1)];
			for (int i = 0; i < 2 * n + 1; i++) {
				for (int j = 0; j < 2 * m + 1; j++) {
					index[(2 * m + 1) * i + j] = (2 * m + 1) * i + j;
					color[(2 * m + 1) * i + j] = getResource("gridNodeColor");
				}
			}
			for (int i = 0; i <= n; i++) {
				for (int j = 0; j < m; j++) {
					color[(2 * m + 1) * 2 * i + 2 * j + 1] = getResource("gridEdgeColor");
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j <= m; j++) {
					color[(2 * m + 1) * (2 * i + 1) + 2 * j] = getResource("gridEdgeColor");
					if (j < m) {
						color[(2 * m + 1) * (2 * i + 1) + 2 * j + 1] = getResource("emptyCellColor");
					}
				}
			}
		}
	}

	private int n, m;

	private Matrix<StringInformation> grid;

	private VisualLongestPath vlp;

	private void computeLongestPath() {
		vlp.init();
		vlp.ieStart();
		grid.setAt(new StringInformation("0"), 0, 0);
		vlp.ieFirstLongestPathComputed();
		for (int i = 1; i <= n; i++) {
			int p = Integer.parseInt(grid.elementAt(2 * (i - 1), 0)
					.stringValue());
			int e = Integer.parseInt(grid.elementAt(2 * (i - 1) + 1, 0)
					.stringValue());
			grid.setAt(new StringInformation("" + (p + e)), 2 * i, 0);
			vlp.ieNewFirstColumnElementComputed((2 * m + 1) * 2 * i,
					(2 * m + 1) * 2 * (i - 1));
		}
		for (int j = 1; j <= m; j++) {
			int p = Integer.parseInt(grid.elementAt(0, 2 * (j - 1))
					.stringValue());
			int e = Integer.parseInt(grid.elementAt(0, 2 * (j - 1) + 1)
					.stringValue());
			grid.setAt(new StringInformation("" + (p + e)), 0, 2 * j);
			vlp.ieNewFirstRowElementComputed(2 * j, 2 * (j - 1));
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				vlp.ieNewLongestPathComputationStarted((2 * m + 1) * 2 * i + 2 * j,
						(2 * m + 1) * 2 * (i - 1) + 2 * j, (2 * m + 1) * 2 * i
								+ 2 * (j - 1));
				int up = Integer.parseInt(grid.elementAt(2 * (i - 1), 2 * j)
						.stringValue())
						+ Integer.parseInt(grid.elementAt(2 * (i - 1) + 1,
								2 * j).stringValue());
				int left = Integer.parseInt(grid.elementAt(2 * i, 2 * (j - 1))
						.stringValue())
						+ Integer.parseInt(grid.elementAt(2 * i,
								2 * (j - 1) + 1).stringValue());
				if (up > left) {
					grid.setAt(new StringInformation("" + up), 2 * i, 2 * j);
				} else {
					grid.setAt(new StringInformation("" + left), 2 * i, 2 * j);
				}
				vlp.ieNewLongestPathComputationEnded((2 * m + 1) * 2 * i + 2 * j,
						(2 * m + 1) * 2 * (i - 1) + 2 * j, (2 * m + 1) * 2 * i
								+ 2 * (j - 1));
			}
		}
		vlp.ieEnd();
	}

	public void execute(String visFileName) {
		vlp = new VisualLongestPath(visFileName);
		if (readInput()) {
			computeLongestPath();
			MessageUtility
					.algorithmTerminated(vlp.getResource("algorithmName"));
		}
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader algorithmLoader = new InputLoader(vlp.getAlgorithmPath(),
				vlp.getResource("algorithmFileName"));
		grid = (Matrix) algorithmLoader.load("Matrix", vlp
				.getResource("selectInputMessage"));
		if (grid == null || grid.width() < 3 || grid.height() < 3) {
			return false;
		}
		n = grid.height() / 2;
		m = grid.width() / 2;
		for (int i = 0; i < 2 * n + 1; i++) {
			for (int j = 0; j < 2 * m + 1; j++) {
				if (grid.elementAt(i, j) == null) {
					grid.setAt(new StringInformation(" "), i, j);
				}
			}
		}
		return true;
	}
}
