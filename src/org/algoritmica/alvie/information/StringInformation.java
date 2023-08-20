package org.algoritmica.alvie.information;

public class StringInformation implements SortableI<StringInformation> {

	private String value;

	public StringInformation() {
		this.value = "void";
	}

	public StringInformation(String value) {
		this.value = new String(value);
	}

	public boolean isEqual(StringInformation item) {
		return (value.equals(item.value));
	}

	public boolean isGreaterThan(StringInformation item) {
		return (value.compareTo(item.value) > 0);
	}

	public boolean isLessThan(StringInformation item) {
		return (value.compareTo(item.value) < 0);
	}

	public String stringValue() {
		return value;
	}

}
