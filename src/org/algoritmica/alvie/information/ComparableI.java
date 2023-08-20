package org.algoritmica.alvie.information;

public interface ComparableI<C extends ComparableI<C>> extends InformationI {

	public boolean isEqual(C item);

}
