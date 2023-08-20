package org.algoritmica.alvie.information;

public class IntInformation implements SortableI<IntInformation> {

	private int value;

	public IntInformation() {
		value = -1;
	}

	public IntInformation(int value) {
		this.value = value;
	}

	public IntInformation(String value) {
		this.value = Integer.parseInt(value);
	}

	public int intValue() {
		return value;
	}

	public boolean isEqual(IntInformation item) {
		return (value == item.value);
	}

	public boolean isGreaterThan(IntInformation item) {
		return (value > item.value);
	}

	public boolean isLessThan(IntInformation item) {
		return (value < item.value);
	}

	public String stringValue() {
		return "" + value;
	}

}
