package org.algoritmica.alvie.algorithms;

import java.awt.Font;
import java.util.HashMap;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.GraphXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class UnionFind {
	class UFInformation extends IntInformation {

		private UFList list;

		private UFInformation succ;

		UFInformation(int value) {
			super(value);
		}
	}

	class UFList {
		private UFInformation start;

		private UFInformation end;

		private int length;
	}

	class VisualUnionFind extends VisualInnerClass {
		private GraphXMLDrawerUtility<StringInformation, StringInformation> gDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private Integer[] id;

		private String[] color;

		private HashMap<Integer, Integer> idIndexMap;

		private boolean isPseudocodeVisible;

		public VisualUnionFind(String visFile) {
			super("unionFind", visFile);
		}

		private void ieEnd() {
			gDrawer.startStep(step++);
			gDrawer.draw(getResource("ieEnd"));
			gDrawer.endStep();
			gDrawer.endXMLFile();
		}

		private void ieListsHaveToBeJoined(UFInformation l, UFInformation s) {
			gDrawer.startStep(step++);
			gDrawer.draw(id, color, getResource("ieListsHaveToBeJoined"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 20, 21, 22 });
			gDrawer.endStep();
		}

		private void ieListsJoined(UFInformation l, UFInformation s) {
			set.newArc(l.intValue(), s.intValue());
			setUpShorterListCoordinates(l, s);
			gDrawer.startStep(step++);
			gDrawer.draw(id, color, getResource("ieListsJoined"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 20, 21, 22 });
			gDrawer.endStep();
		}

		private void ieShorterListElementChanged(UFInformation z) {
			color[idIndexMap.get(z.intValue())] = getResource("longerListColor");
			gDrawer.startStep(step++);
			gDrawer.draw(id, color, getResource("ieShorterListElementChanged"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 17, 18 });
			gDrawer.endStep();
		}

		private void ieShorterListFound(UFInformation s, UFInformation l) {
			setUpDifferentialDrawArraysSLF(s, l);
			gDrawer.startStep(step++);
			gDrawer.draw(id, color, getResource("ieShorterListFound"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 15 });
			gDrawer.endStep();
		}

		private void ieStart() {
			setUpCoordinates();
			gDrawer.startXMLFile(getResource("algorithmName"));
			gDrawer.startStep(step++);
			gDrawer.draw(getResource("ieStart"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1, 2, 3, 4 });
			gDrawer.endStep();
		}

		private void ieStartUnion(UFInformation x, UFInformation y) {
			setUpDifferentialDrawArraysSU(x, y);
			gDrawer.startStep(step++);
			gDrawer.draw(id, color, getResource("ieStartUnion"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 7 });
			gDrawer.endStep();
		}

		private void ieUnionDone() {
			gDrawer.startStep(step++);
			gDrawer.draw(getResource("ieUnionDone"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			gDrawer.endStep();
		}

		private void ieUnionNotToBeDone(UFInformation x, UFInformation y) {
			setUpDifferentialDrawArraysSU(x, y);
			gDrawer.startStep(step++);
			gDrawer.draw(id, color, getResource("ieUnionNotToBeDone"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6 });
			gDrawer.endStep();
		}

		private void ieUnionToBeDone(UFInformation x, UFInformation y) {
			setUpDifferentialDrawArraysSU(x, y);
			gDrawer.startStep(step++);
			gDrawer.draw(id, color, getResource("ieUnionToBeDone"));
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6 });
			gDrawer.endStep();
		}

		void init() {
			gDrawer = new GraphXMLDrawerUtility<StringInformation, StringInformation>(
					set, getResource("graphName"), getOutputStream());
			gDrawer.setOriginX(Integer
					.parseInt(getResource("graphXMLDrawerOriginX")));
			gDrawer.setOriginY(Integer
					.parseInt(getResource("graphXMLDrawerOriginY")));
			gDrawer.setDefaultFont(Font.decode(getResource("graphFont")));
			gDrawer.setDefaultShape(getResource("graphShape"));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			step = 0;
		}

		private void setUpCoordinates() {
			int x = Integer.parseInt(getResource("startX"));
			int deltaY = Integer.parseInt(getResource("deltaY"));
			for (int i = 0; i < n; i++) {
				set.setNodeCoordinates(i, x, deltaY * i);
			}
		}

		private void setUpDifferentialDrawArraysSLF(UFInformation s,
				UFInformation l) {
			int sl = s.list.length;
			int ll = l.list.length;
			id = new Integer[sl + ll];
			color = new String[sl + ll];
			int i = 0;
			idIndexMap = new HashMap<Integer, Integer>(sl + ll);
			while (s != null) {
				id[i] = s.intValue();
				idIndexMap.put(id[i], i);
				color[i] = getResource("shorterListColor");
				s = s.succ;
				i = i + 1;
			}
			while (l != null) {
				id[i] = l.intValue();
				idIndexMap.put(id[i], i);
				color[i] = getResource("longerListColor");
				l = l.succ;
				i = i + 1;
			}
		}

		private void setUpDifferentialDrawArraysSU(UFInformation x,
				UFInformation y) {
			id = new Integer[2];
			color = new String[2];
			id[0] = x.intValue();
			color[0] = getResource("selectedElementColor");
			id[1] = y.intValue();
			color[1] = getResource("selectedElementColor");
		}

		private void setUpShorterListCoordinates(UFInformation l,
				UFInformation s) {
			int x = set.getNodeAbscissa(l.intValue());
			int y = set.getNodeOrdinate(l.intValue());
			int deltaX = Integer.parseInt(getResource("deltaX"));
			while (s != null) {
				x = x + deltaX;
				set.setNodeCoordinates(s.intValue(), x, y);
				s = s.succ;
			}
		}

	}

	private UFList list, longer, shorter;

	private UFInformation z;

	private Graph<StringInformation, StringInformation> set;

	private Array<IntInformation> a;

	private int n;

	private VisualUnionFind vuf;

	private void create(UFInformation x) {
		list = new UFList();
		list.start = x;
		list.end = x;
		list.length = 1;
		x.list = list;
		x.succ = null;
	}

	public void execute(String visFile) {
		vuf = new VisualUnionFind(visFile);
		if (readInput()) {
			HashMap<Integer, UFInformation> map = new HashMap<Integer, UFInformation>();
			set = new Graph<StringInformation, StringInformation>(true,
					new StringInformation(), new StringInformation());
			UFInformation ufi;
			for (int i = 0; i < n; i++) {
				ufi = new UFInformation(i);
				create(ufi);
				set.addNode(new StringInformation("" + i), i, 0, 0);
				map.put(i, ufi);
			}
			vuf.init();
			vuf.ieStart();
			UFInformation ufi1, ufi2;
			for (int i = 1; i < a.length(); i = i + 2) {
				ufi1 = map.get(a.elementAt(i).intValue());
				ufi2 = map.get(a.elementAt(i + 1).intValue());
				if (!find(ufi1, ufi2)) {
					vuf.ieUnionToBeDone(ufi1, ufi2);
					union(ufi1, ufi2);
				} else {
					vuf.ieUnionNotToBeDone(ufi1, ufi2);
				}
			}
			vuf.ieEnd();
			MessageUtility.algorithmTerminated("UnionFind");
		}
	}

	private boolean find(UFInformation x, UFInformation y) {
		return x.list == y.list;
	}

	@SuppressWarnings("unchecked")
	private boolean readInput() {
		InputLoader il = new InputLoader(vuf.getAlgorithmPath(), vuf
				.getResource("algorithmFileName"));
		a = (Array<IntInformation>) il.load("Array", vuf
				.getResource("selectInputMessage"));
		if (a == null || a.length() < 1) {
			return false;
		} else {
			n = a.elementAt(0).intValue();
			return true;
		}
	}

	private void union(UFInformation x, UFInformation y) {
		vuf.ieStartUnion(x, y);
		if (x.list.length <= y.list.length) {
			shorter = x.list;
			longer = y.list;
		} else {
			shorter = y.list;
			longer = x.list;
		}
		vuf.ieShorterListFound(shorter.start, longer.start);
		z = shorter.start;
		while (z != null) {
			z.list = longer;
			vuf.ieShorterListElementChanged(z);
			z = z.succ;
		}
		vuf.ieListsHaveToBeJoined(longer.end, shorter.start);
		longer.end.succ = shorter.start;
		vuf.ieListsJoined(longer.end, shorter.start);
		longer.end = shorter.end;
		longer.length = shorter.length + longer.length;
		vuf.ieUnionDone();
	}

}
