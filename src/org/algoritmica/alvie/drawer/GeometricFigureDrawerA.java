package org.algoritmica.alvie.drawer;

/*
 * Super-class of any geoemtricFigure drawer class. It simply defines a common constructor
 * and it cannot be instantiated since it is abstract.
 */
public abstract class GeometricFigureDrawerA extends DrawerA {
	public GeometricFigureDrawerA(String name) {
		super(name);
	}
}
