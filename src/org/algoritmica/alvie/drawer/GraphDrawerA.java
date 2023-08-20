package org.algoritmica.alvie.drawer;

import java.awt.Font;

import org.algoritmica.alvie.desktop.Main;

/*
 * Super-class of any graph drawer class. It defines a common constructor
 * and two methods for managing the font of an arc label. This class cannot be 
 * instantiated since it is abstract.
 */
public abstract class GraphDrawerA extends DrawerA {

	private Font arcLabelFont;

	public GraphDrawerA(String name) {
		super(name);
		arcLabelFont = Font.decode(Main.config.getProperty("defaultArcLabelFont"));
	}

	public Font getDefaultArcLabelFont() {
		return arcLabelFont;
	}

	public void setDefaultArcLabelFont(Font arcLabelFont) {
		this.arcLabelFont = arcLabelFont;
	}
}
