package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.List;
import org.algoritmica.alvie.datastructure.ListI;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ListXMLDrawerUtility;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class MoveToFront {
	class VisualMoveToFront extends VisualInnerClass {
		private Matrix<StringInformation> data;

		private MatrixXMLDrawerUtility<StringInformation> dataDrawer;

		private ListXMLDrawerUtility<IntInformation> mtfDrawer, optDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private Integer[] indexData, indexMTF, indexOPT;

		private String[] colorData, colorMTF, colorOPT;

		private int imtf, iopt;

		private boolean isPseudocodeVisible;

		public VisualMoveToFront(String visFile) {
			super("moveToFront", visFile);
		}

		private void ieElementHasToBeMoved() {
			setUpDifferentialDrawArraysSSMTF();
			mtfDrawer.startStep(step++);
			mtfDrawer.draw(new Integer[] { imtf }, colorMTF,
					getResource("ieElementHasToBeMoved"));
			optDrawer.draw("");
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6, 7, 8, 9 });
			mtfDrawer.endStep();
		}

		private void ieElementMoved(int r) {
			mtfDrawer.setDataStructure(mtf);
			mtfDrawer.startStep(step++);
			mtfDrawer.draw(getResource("ieElementMoved"));
			optDrawer.draw("");
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 6, 7, 8, 9 });
			mtfDrawer.endStep();
			colorData[(r + 1) * 5] = getResource("dataComputedColor");
		}

		private void ieEnd() {
			mtfDrawer.startStep(step++);
			mtfDrawer.draw(getResource("ieEnd"));
			optDrawer.draw("");
			dataDrawer.draw(indexData, colorData, "");
			mtfDrawer.endStep();
			mtfDrawer.endXMLFile();
		}

		private void ieFoundMTF(int r) {
			data.setAt(new StringInformation("" + imtf), r + 1, 1);
			colorData[5 * (r + 1) + 1] = getResource("dataComputedColor");
			mtfDrawer.startStep(step++);
			mtfDrawer.draw(new Integer[] { imtf }, colorMTF,
					getResource("ieFoundMTF"));
			optDrawer.draw("");
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", null);
			mtfDrawer.endStep();
		}

		private void ieFoundOPT(int r) {
			data.setAt(new StringInformation("" + iopt), r + 1, 2);
			colorData[5 * (r + 1) + 2] = getResource("dataComputedColor");
			mtfDrawer.startStep(step++);
			mtfDrawer.draw(new Integer[] { imtf }, colorMTF,
					getResource("ieFoundOPT"));
			optDrawer.draw(new Integer[] { iopt }, colorOPT, "");
			dataDrawer.draw(indexData, colorData, "");
			mtfDrawer.endStep();
		}

		private void ieShowInversions(int r) {
			setUpDifferentialDrawArraysSI();
			ListI<IntInformation> cmtf, copt;
			cmtf = mtf;
			int i = 0, j;
			int f = 0, g = 0;
			while (i < imtf) {
				j = 0;
				copt = opt;
				while (!copt.head().isEqual(cmtf.head())) {
					copt = copt.tail();
					j = j + 1;
				}
				if (j > iopt) {
					colorMTF[i] = getResource("inversionElementColor");
					colorOPT[j] = getResource("inversionElementColor");
					f = f + 1;
				} else {
					colorMTF[i] = getResource("notInversionElementColor");
					colorOPT[j] = getResource("notInversionElementColor");
					g = g + 1;
				}
				cmtf = cmtf.tail();
				i = i + 1;
			}
			data.setAt(new StringInformation("" + (-f + g)), r + 1, 3);
			data.setAt(new StringInformation("" + 2 * g), r + 1, 4);
			colorData[5 * (r + 1) + 3] = getResource("dataComputedColor");
			colorData[5 * (r + 1) + 4] = getResource("dataComputedColor");
			mtfDrawer.startStep(step++);
			mtfDrawer.draw(indexMTF, colorMTF, getResource("ieShowInversions"));
			optDrawer.draw(indexOPT, colorOPT, "");
			dataDrawer.draw(indexData, colorData, "");
			mtfDrawer.endStep();
		}

		private void ieStart() {
			mtfDrawer.startXMLFile(getResource("algorithmName"));
			mtfDrawer.startStep(step++);
			mtfDrawer.draw(getResource("ieStart"));
			optDrawer.draw("");
			dataDrawer.draw(indexData, colorData, "");
			mtfDrawer.endStep();
		}

		private void ieStartSearchingMTF(int r) {
			colorData[(r + 1) * 5] = getResource("currentKeyColor");
			mtfDrawer.startStep(step++);
			mtfDrawer.draw(getResource("ieStartSearchingMTF"));
			optDrawer.draw("");
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 1 });
			mtfDrawer.endStep();
			imtf = 0;
			setUpDifferentialDrawArraysSSMTF();
		}

		private void ieStartSearchingOPT() {
			mtfDrawer.startStep(step++);
			mtfDrawer.draw(new Integer[] { imtf }, colorMTF,
					getResource("ieStartSearchingOPT"));
			optDrawer.draw("");
			dataDrawer.draw(indexData, colorData, "");
			mtfDrawer.endStep();
			iopt = 0;
			setUpDifferentialDrawArraysSSOPT();
		}

		private void ieStillSearchingMTF() {
			mtfDrawer.startStep(step++);
			mtfDrawer.draw(new Integer[] { imtf }, colorMTF,
					getResource("ieStillSearchingMTF"));
			optDrawer.draw("");
			dataDrawer.draw(indexData, colorData, "");
			if (isPseudocodeVisible)
				pseudocodeDrawer.draw("", new int[] { 4 });
			mtfDrawer.endStep();
			imtf = imtf + 1;
		}

		private void ieStillSearchingOPT() {
			mtfDrawer.startStep(step++);
			mtfDrawer.draw(new Integer[] { imtf }, colorMTF,
					getResource("ieStillSearchingOPT"));
			optDrawer.draw(new Integer[] { iopt }, colorOPT, "");
			dataDrawer.draw(indexData, colorData, "");
			mtfDrawer.endStep();
			iopt = iopt + 1;
		}

		private void init() {
			data = new Matrix<StringInformation>(numberExecutions + 1, 5,
					new StringInformation());
			data.setAt(new StringInformation("k"), 0, 0);
			data.setAt(new StringInformation("c"), 0, 1);
			data.setAt(new StringInformation("o"), 0, 2);
			data.setAt(new StringInformation("dp"), 0, 3);
			data.setAt(new StringInformation("a"), 0, 4);
			for (int r = 1; r <= numberExecutions; r++) {
				data.setAt(new StringInformation("" + request[r - 1]), r, 0);
				for (int c = 1; c < 5; c++) {
					data.setAt(new StringInformation(" "), r, c);
				}
			}
			dataDrawer = new MatrixXMLDrawerUtility<StringInformation>(data,
					getResource("dataName"), getOutputStream());
			dataDrawer.setOriginX(Integer
					.parseInt(getResource("dataXMLDrawerOriginX")));
			dataDrawer.setOriginY(Integer
					.parseInt(getResource("dataXMLDrawerOriginY")));
			dataDrawer.setDefaultFont(Font.decode(getResource("dataFont")));
			dataDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("dataElementWidth")));
			setUpDifferentialDrawArraysData();
			mtfDrawer = new ListXMLDrawerUtility<IntInformation>(mtf,
					getResource("mtfName"), getOutputStream());
			mtfDrawer.setOriginX(Integer
					.parseInt(getResource("mtfXMLDrawerOriginX")));
			mtfDrawer.setOriginY(Integer
					.parseInt(getResource("mtfXMLDrawerOriginY")));
			mtfDrawer.setDefaultFont(Font.decode(getResource("mtfFont")));
			optDrawer = new ListXMLDrawerUtility<IntInformation>(opt,
					getResource("optName"), getOutputStream());
			optDrawer.setOriginX(Integer
					.parseInt(getResource("optXMLDrawerOriginX")));
			optDrawer.setOriginY(Integer
					.parseInt(getResource("optXMLDrawerOriginY")));
			optDrawer.setDefaultFont(Font.decode(getResource("optFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			iopt = 0;
			step = 0;
		}

		private void setUpDifferentialDrawArraysData() {
			indexData = new Integer[(numberExecutions + 1) * 5];
			colorData = new String[(numberExecutions + 1) * 5];
			for (int c = 0; c < 5; c++) {
				indexData[c] = c;
				colorData[c] = getResource("dataFirstRowColor");
			}
			for (int r = 1; r <= numberExecutions; r++) {
				for (int c = 0; c < 5; c++) {
					indexData[5 * r + c] = 5 * r + c;
					colorData[5 * r + c] = getResource("dataHiddenColor");
				}
			}
		}

		private void setUpDifferentialDrawArraysSI() {
			indexMTF = new Integer[numberElements];
			colorMTF = new String[numberElements];
			indexOPT = new Integer[numberElements];
			colorOPT = new String[numberElements];
			for (int i = 0; i < numberElements; i++) {
				indexMTF[i] = i;
				indexOPT[i] = i;
				colorMTF[i] = getResource("mtfColor");
				colorOPT[i] = getResource("optColor");
			}
			colorMTF[imtf] = getResource("currentMTFElementColor");
			colorOPT[iopt] = getResource("currentOPTElementColor");
		}

		private void setUpDifferentialDrawArraysSSMTF() {
			colorMTF = new String[1];
			colorMTF[0] = getResource("currentMTFElementColor");
		}

		private void setUpDifferentialDrawArraysSSOPT() {
			colorOPT = new String[1];
			colorOPT[0] = getResource("currentOPTElementColor");
		}
	}

	private ListI<IntInformation> mtf, opt;

	private int numberElements, numberRequests, numberExecutions;

	private int[] request;

	private VisualMoveToFront vmtf;

	private ListI<IntInformation> delete(IntInformation i,
			ListI<IntInformation> l) {
		if (l.size() > 0) {
			if (l.head().isEqual(i)) {
				return l.tail();
			} else {
				l.set(l.head(), delete(i, l.tail()));
				return l;
			}
		} else {
			return l;
		}
	}

	public void execute(String visFile) {
		vmtf = new VisualMoveToFront(visFile);
		if (readInput()) {
			vmtf.init();
			vmtf.ieStart();
			ListI<IntInformation> cmtf, copt, tmp;
			for (int r = 0; r < numberExecutions; r++) {
				vmtf.ieStartSearchingMTF(r);
				cmtf = mtf;
				while (cmtf.head().intValue() != request[r]) {
					vmtf.ieStillSearchingMTF();
					cmtf = cmtf.tail();
				}
				vmtf.ieFoundMTF(r);
				vmtf.ieStartSearchingOPT();
				copt = opt;
				while (copt.head().intValue() != request[r]) {
					vmtf.ieStillSearchingOPT();
					copt = copt.tail();
				}
				vmtf.ieFoundOPT(r);
				vmtf.ieShowInversions(r);
				vmtf.ieElementHasToBeMoved();
				mtf = delete(cmtf.head(), mtf);
				tmp = new List<IntInformation>(new IntInformation());
				tmp.set(cmtf.head(), mtf);
				mtf = tmp;
				vmtf.ieElementMoved(r);
			}
			vmtf.ieEnd();
			MessageUtility.algorithmTerminated(vmtf.getResource("algorithmName"));
		}
	}

	@SuppressWarnings("unchecked")
	private boolean readInput() {
		InputLoader il = new InputLoader(vmtf.getAlgorithmPath(), vmtf
				.getResource("algorithmFileName"));
		Array<IntInformation> a = (Array<IntInformation>) il.load("Array", vmtf
				.getResource("selectInputMessage"));
		if (a == null || a.length() != 3) {
			return false;
		}
		mtf = new List<IntInformation>(new IntInformation());
		numberElements = a.elementAt(0).intValue();
		ListI<IntInformation> tmp;
		for (int i = 0; i < numberElements; i++) {
			tmp = new List<IntInformation>(new IntInformation());
			tmp.set(new IntInformation(i), mtf);
			mtf = tmp;
		}
		numberRequests = a.elementAt(1).intValue();
		request = new int[numberRequests];
		for (int i = 0; i < numberRequests; i++) {
			request[i] = (int) (Math.random() * numberElements);
		}
		int[] frequency = new int[numberElements];
		for (int i = 0; i < numberRequests; i++) {
			frequency[request[i]] = frequency[request[i]] + 1;
		}
		int[] sorted = new int[numberElements];
		for (int i = 0; i < numberElements; i++) {
			int max = i;
			for (int j = 0; j < numberElements; j++) {
				if (frequency[max] < frequency[j]) {
					max = j;
				}
			}
			sorted[i] = max;
			frequency[max] = -1;
		}
		opt = new List<IntInformation>(new IntInformation());
		for (int i = 0; i < numberElements; i++) {
			tmp = new List<IntInformation>(new IntInformation());
			tmp.set(new IntInformation(sorted[i]), opt);
			opt = tmp;
		}
		numberExecutions = a.elementAt(2).intValue();
		return true;
	}

}
