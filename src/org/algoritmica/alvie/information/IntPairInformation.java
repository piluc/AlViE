package org.algoritmica.alvie.information;

public class IntPairInformation implements ComparableI<IntPairInformation> {

	private int firstValue;

	private int secondValue;

	public IntPairInformation() {
		firstValue = -1;

		secondValue = -1;
	}

	public IntPairInformation(int first, int second) {
		firstValue = first;
		secondValue = second;
	}

	public IntPairInformation(String value) {
		int index = value.indexOf(";");
		firstValue = Integer.parseInt(value.substring(1, index));
		secondValue = Integer.parseInt(value.substring(index + 1, value.length() - 1));
	}

	public boolean isEqual(IntPairInformation item) {
		return (firstValue == item.firstValue) && (secondValue == item.secondValue);
	}

	public String stringValue() {
		return "[" + firstValue + ";" + secondValue + "]";
	}

	public int firstValue() {
		return firstValue;
	}

	public int secondValue() {
		return secondValue;
	}
}
