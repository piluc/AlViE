package org.algoritmica.alvie.algorithms;

import java.awt.Font;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.IntInformation;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.ArrayXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class DynamicArray {
	class VisualDynamicArray extends VisualInnerClass {
		private ArrayXMLDrawerUtility<StringInformation> arrayDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private Integer[] index;

		private String[] color;

		public VisualDynamicArray(String visFile) {
			super("dynamicArray", visFile);
		}

		public void ieArrayChanged() {
			arrayDrawer.setDataStructure(array);
		}

		void ieDeleteEnded() {
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(getResource("ieDeleteEnded"));
			pseudocodeDrawer.draw("", null);
			arrayDrawer.endStep();
		}

		void ieDeleteOperation() {
			if (elementNumber > 0) {
				index[0] = elementNumber - 1;
				arrayDrawer.startStep(step++);
				arrayDrawer
						.draw(index, color, getResource("ieDeleteOperation"));
				pseudocodeDrawer.draw("", null);
				arrayDrawer.endStep();
			}
		}

		void ieDoublingNotRequired() {
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(getResource("ieDoublingNotRequired"));
			pseudocodeDrawer.draw("", new int[] { 1 });
			arrayDrawer.endStep();
		}

		void ieDoublingRequired() {
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(getResource("ieDoublingRequired"));
			pseudocodeDrawer.draw("", new int[] { 2, 3, 4, 5 });
			arrayDrawer.endStep();
		}

		void ieEnd() {
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(getResource("ieEnd"));
			arrayDrawer.endStep();
			arrayDrawer.endXMLFile();
		}

		void ieHalvingNotRequired() {
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(getResource("ieHalvingNotRequired"));
			pseudocodeDrawer.draw("", new int[] { 8 });
			arrayDrawer.endStep();
		}

		void ieHalvingRequired() {
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(getResource("ieHalvingRequired"));
			pseudocodeDrawer.draw("", new int[] { 9, 10, 11, 12 });
			arrayDrawer.endStep();
		}

		void ieInsertEnded() {
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(getResource("ieInsertEnded"));
			pseudocodeDrawer.draw("", null);
			arrayDrawer.endStep();
		}

		void ieInsertOperation(int e) {
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(getResource("ieInsertOperation1") + e + " " + getResource("ieInsertOperation2"));
			pseudocodeDrawer.draw("", null);
			arrayDrawer.endStep();
		}

		void ieStart() {
			initDifferentialDrawArrays();
			arrayDrawer.startXMLFile(getResource("algorithmName"));
			arrayDrawer.startStep(step++);
			arrayDrawer.draw(getResource("ieStart"));
			pseudocodeDrawer.draw("", null);
			arrayDrawer.endStep();
		}

		void init() {
			arrayDrawer = new ArrayXMLDrawerUtility<StringInformation>(array,
					getResource("arrayName"), getOutputStream());
			arrayDrawer.setOriginX(Integer
					.parseInt(getResource("arrayXMLDrawerOriginX")));
			arrayDrawer.setOriginY(Integer
					.parseInt(getResource("arrayXMLDrawerOriginY")));
			arrayDrawer.setDefaultFont(Font.decode(getResource("arrayFont")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			pseudocodeDrawer
					.setEmphasizedLineColor(getResource("pseudocodeEmphasizedLineColor"));
			pseudocodeDrawer.setFont(getResource("pseudocodeFont"));
			step = 0;
		}

		private void initDifferentialDrawArrays() {
			index = new Integer[1];
			color = new String[1];
			color[0] = getResource("elementToBeDeletedColor");
		}

	}

	private int initialDimension;

	private int operationNumber;

	private final StringInformation EMPTY = new StringInformation(" ");

	private Array<IntInformation> operationArray;

	private Array<StringInformation> array;

	private Array<StringInformation> temporaryArray;

	private int elementNumber;

	private VisualDynamicArray vda;

	private void checkDoubling() {
		if (elementNumber == array.length()) {
			vda.ieDoublingRequired();
			temporaryArray = new Array<StringInformation>(2 * array.length(),
					new StringInformation(""));
			for (int i = 0; i < array.length(); i++) {
				temporaryArray.setAt(array.elementAt(i), i);
			}
			for (int i = array.length(); i < temporaryArray.length(); i++) {
				temporaryArray.setAt(EMPTY, i);
			}
			array = temporaryArray;
			vda.ieArrayChanged();
		} else {
			vda.ieDoublingNotRequired();
		}
	}

	private void checkHalving() {
		if ((array.length() > 1) && (elementNumber == array.length() / 4)) {
			vda.ieHalvingRequired();
			temporaryArray = new Array<StringInformation>(array.length() / 2,
					new StringInformation(""));
			for (int i = 0; i < elementNumber; i++) {
				temporaryArray.setAt(array.elementAt(i), i);
			}
			for (int i = elementNumber; i < temporaryArray.length(); i++) {
				temporaryArray.setAt(EMPTY, i);
			}
			array = temporaryArray;
			vda.ieArrayChanged();
		} else {
			vda.ieHalvingNotRequired();
		}
	}

	private void delete() {
		if (elementNumber > 0) {
			array.setAt(EMPTY, elementNumber - 1);
			elementNumber = elementNumber - 1;
			checkHalving();
			vda.ieDeleteEnded();
		}
	}

	public void execute(String visFile) {
		vda = new VisualDynamicArray(visFile);
		if (readInput()) {
			initArray();
			executeOperations();
			MessageUtility
					.algorithmTerminated(vda.getResource("algorithmName"));
		}
	}

	private void executeOperations() {
		vda.init();
		vda.ieStart();
		for (int i = 0; i < operationNumber; i++) {
			if (operationArray.elementAt(i).intValue() < 0) {
				vda.ieDeleteOperation();
				delete();
			} else {
				vda.ieInsertOperation(operationArray.elementAt(i).intValue());
				insert(operationArray.elementAt(i));
			}
		}
		vda.ieEnd();
	}

	private void initArray() {
		array = new Array<StringInformation>(initialDimension,
				new StringInformation(""));
		for (int i = 0; i < initialDimension; i++) {
			array.setAt(EMPTY, i);
		}
		elementNumber = 0;
		temporaryArray = new Array<StringInformation>(0, new StringInformation(
				""));
	}

	private void insert(IntInformation v) {
		checkDoubling();
		array.setAt(new StringInformation(v.stringValue()), elementNumber);
		elementNumber = elementNumber + 1;
		vda.ieInsertEnded();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vda.getAlgorithmPath(), vda
				.getResource("algorithmFileName"));
		operationArray = (Array) inputLoader.load("Array", vda
				.getResource("selectInputMessage"));
		if (operationArray == null
				|| operationArray.length() < 1
				|| operationArray.elementAt(operationArray.length() - 1)
						.intValue() <= 0) {
			return false;
		}
		operationNumber = operationArray.length() - 1;
		initialDimension = operationArray.elementAt(operationNumber).intValue();
		return true;
	}

}
