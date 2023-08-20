package org.algoritmica.alvie.information;

public interface SortableI<S extends SortableI<S>> extends ComparableI<S> {

	public boolean isGreaterThan(S item);

	public boolean isLessThan(S item);

}
