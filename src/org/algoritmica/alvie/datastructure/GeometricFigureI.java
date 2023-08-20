package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.GeometricObjectInformation;

/*
 * This the geometric figure interface. It allows the programmer to get the number
 * of geometric objects and to manage them by referring to their index. Differently
 * from an array, removal and insertion in the middle is allowed.
 */
public interface GeometricFigureI extends DataStructureI<GeometricObjectInformation> {

	public void add(GeometricObjectInformation x);

	public void add(GeometricObjectInformation x, int i);

	public GeometricObjectInformation elementAt(int i);

	public int length();

	public void remove(int i);

	public void setAt(GeometricObjectInformation x, int i);
}
