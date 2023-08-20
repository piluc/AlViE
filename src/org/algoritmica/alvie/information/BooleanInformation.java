package org.algoritmica.alvie.information;

public class BooleanInformation implements ComparableI<BooleanInformation> {

	private boolean booleanValue;

	public BooleanInformation() {
		this.booleanValue = false;
	}

	public BooleanInformation(boolean value) {
		this.booleanValue = value;
	}

	public BooleanInformation(String value) {
		this.booleanValue = Boolean.parseBoolean(value);
	}

	public boolean isEqual(BooleanInformation item) {
		return (booleanValue == item.booleanValue);
	}

	public String stringValue() {
		return booleanValue ? "true" : "false";
	}

	public boolean booleanValue() {
		return booleanValue;
	}

	public void switchValue() {
		booleanValue = !booleanValue;
	}

}
