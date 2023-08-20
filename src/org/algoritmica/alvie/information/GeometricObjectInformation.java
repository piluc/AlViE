package org.algoritmica.alvie.information;

public class GeometricObjectInformation implements InformationI {
	public final static int POINT = 0;
	public final static int CIRCLE = 1;
	public final static int SEGMENT = 2;
	public final static int RECTANGLE = 3;
	public final static int TEXT = 4;

	private int type;
	private double firstValue;
	private double secondValue;
	private double thirdValue;
	private double fourthValue;
	private String text;

	public GeometricObjectInformation() {
		type = -1;
		firstValue = -1;
		secondValue = -1;
		secondValue = -1;
		secondValue = -1;
	}

	public GeometricObjectInformation(double x, double y) {
		type = POINT;
		firstValue = x;
		secondValue = y;
	}

	public GeometricObjectInformation(double x, double y, double r) {
		type = CIRCLE;
		firstValue = x;
		secondValue = y;
		thirdValue = r;
	}

	public GeometricObjectInformation(int t, double x, double y, double w, double h) {
		if (t == SEGMENT || t == RECTANGLE) {
			type = t;
			firstValue = x;
			secondValue = y;
			thirdValue = w;
			fourthValue = h;
		}
	}

	public GeometricObjectInformation(String t, double x, double y) {
		type = TEXT;
		text = t;
		firstValue = x;
		secondValue = y;
	}

	public GeometricObjectInformation(String value) {
		int index1 = value.indexOf("[");
		String t = value.substring(0, index1);
		if (t.equals("Point")) {
			type = POINT;
			int index2 = value.indexOf(";");
			firstValue = Double.parseDouble(value.substring(index1 + 1, index2));
			index1 = value.indexOf(";", index2 + 1);
			secondValue = Double.parseDouble(value.substring(index2 + 1, value.length() - 1));
		} else if (t.equals("Circle")) {
			type = CIRCLE;
			int index2 = value.indexOf(";");
			firstValue = Double.parseDouble(value.substring(index1 + 1, index2));
			index1 = value.indexOf(";", index2 + 1);
			secondValue = Double.parseDouble(value.substring(index2 + 1, index1));
			index2 = value.indexOf(";", index1 + 1);
			thirdValue = Double.parseDouble(value.substring(index1 + 1, value.length() - 1));
		} else if (!t.equals("Text")) {
			int index2 = value.indexOf(";");
			firstValue = Double.parseDouble(value.substring(index1 + 1, index2));
			index1 = value.indexOf(";", index2 + 1);
			secondValue = Double.parseDouble(value.substring(index2 + 1, index1));
			index2 = value.indexOf(";", index1 + 1);
			thirdValue = Double.parseDouble(value.substring(index1 + 1, index2));
			index1 = value.indexOf(";", index2 + 1);
			fourthValue = Double.parseDouble(value.substring(index2 + 1, value.length() - 1));
			if (t.equals("Segment")) {
				type = SEGMENT;
			} else {
				type = RECTANGLE;
			}
		} else {
			type = TEXT;
			int index2 = value.indexOf(";");
			text = value.substring(index1 + 1, index2);
			index1 = value.indexOf(";", index2 + 1);
			firstValue = Double.parseDouble(value.substring(index2 + 1, index1));
			index2 = value.indexOf(";", index1 + 1);
			secondValue = Double.parseDouble(value.substring(index1 + 1, value.length() - 1));
		}
	}

	public String stringValue() {
		if (type == POINT) {
			return "Point[" + firstValue + ";" + secondValue + "]";
		} else if (type == CIRCLE) {
			return "Circle[" + firstValue + ";" + secondValue + ";" + thirdValue + "]";
		} else if (type == SEGMENT) {
			return "Segment[" + firstValue + ";" + secondValue + ";" + thirdValue + ";" + fourthValue + "]";
		} else if (type != TEXT) {
			return "Rectangle[" + firstValue + ";" + secondValue + ";" + thirdValue + ";" + fourthValue + "]";
		} else {
			return "Text[" + text + ";" + firstValue + ";" + secondValue + "]";
		}
	}

	public int getType() {
		return type;
	}

	public double getFirstValue() {
		return firstValue;
	}

	public double getSecondValue() {
		return secondValue;
	}

	public double getThirdValue() {
		return thirdValue;
	}

	public double getFourthValue() {
		return fourthValue;
	}

	public String getText() {
		return text;
	}
}
