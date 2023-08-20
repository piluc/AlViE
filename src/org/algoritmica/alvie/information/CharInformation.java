package org.algoritmica.alvie.information;

public class CharInformation implements ComparableI<CharInformation> {

	private char value;

	public CharInformation() {
		this.value = 'a';
	}

	public CharInformation(char value) {
		this.value = value;
	}

	public CharInformation(String value) {
		this.value = value.charAt(0);
	}

	public char charValue() {
		return value;
	}

	public boolean isEqual(CharInformation item) {
		return (value == item.value);
	}

	public String stringValue() {
		return "" + value;
	}

}
