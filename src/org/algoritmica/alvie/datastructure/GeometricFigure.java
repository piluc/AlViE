package org.algoritmica.alvie.datastructure;

import java.util.Vector;

import org.algoritmica.alvie.information.GeometricObjectInformation;

/*
 * This the geometric figure data structure. It includes the methods defined
 * by the geometric figure interface: these methods are implemented by making use
 * of the Java Vector class in a straightforward way. 
 */
public class GeometricFigure implements GeometricFigureI {
	private Vector<GeometricObjectInformation> figure;

	public GeometricFigure() {
		figure = new Vector<GeometricObjectInformation>();
	}

	public void add(GeometricObjectInformation x) {
		figure.add(x);
	}

	public void add(GeometricObjectInformation x, int i) {
		figure.add(i, x);
	}

	public GeometricObjectInformation elementAt(int i) {
		return figure.elementAt(i);
	}

	public String getType() {
		return "GeometricFigure";
	}

	public int length() {
		return figure.size();
	}

	public void remove(int i) {
		figure.removeElementAt(i);
	}

	public void setAt(GeometricObjectInformation x, int i) {
		figure.set(i, x);
	}

	public void setType(String type) {
	}
}
