package org.algoritmica.alvie.information;

public class DoubleInformation implements SortableI<DoubleInformation> {

	private double value;

	public DoubleInformation() {
		value = -1;
	}

	public DoubleInformation(double value) {
		this.value = value;
	}

	public DoubleInformation(String value) {
		this.value = Double.parseDouble(value);
	}

	public double doubleValue() {
		return value;
	}

	public boolean isEqual(DoubleInformation item) {
		return (value == item.value);
	}

	public boolean isGreaterThan(DoubleInformation item) {
		return (value > item.value);
	}

	public boolean isLessThan(DoubleInformation item) {
		return (value < item.value);
	}

	public String stringValue() {
		return "" + value;
	}

}
