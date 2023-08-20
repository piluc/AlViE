package org.algoritmica.alvie.information;

public class LongInformation implements SortableI<LongInformation> {

	private long value;

	public LongInformation() {
		this.value = -1;
	}

	public LongInformation(long value) {
		this.value = value;
	}

	public LongInformation(String value) {
		this.value = Integer.parseInt(value);
	}

	public long longValue() {
		return value;
	}

	public boolean isEqual(LongInformation item) {
		return (value == item.value);
	}

	public boolean isGreaterThan(LongInformation item) {
		return (value > item.value);
	}

	public boolean isLessThan(LongInformation item) {
		return (value < item.value);
	}

	public String stringValue() {
		return "" + value;
	}

}
