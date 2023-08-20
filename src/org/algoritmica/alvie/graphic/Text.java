package org.algoritmica.alvie.graphic;

import java.awt.geom.Point2D;

public class Text extends TextAC {

	private Point2D relativePosition;

	private String value;

	public Text(String value) {
		this.value = value;
	}

	public Text() {
	}

	public Point2D getRelativePosition() {
		return relativePosition;
	}

	public void setRelativePosition(Point2D origin) {
		relativePosition = origin;
	}

	public String toString() {
		return value;
	}
}
