package org.algoritmica.alvie.datastructure;

import org.algoritmica.alvie.information.StringInformation;

/*
 * A pseudo-code is just an array of strings (that is, code lines). For this
 * reason, it initializes the type value within the constructor and does not
 * allow the other classes to change it (by overriding the type setting method.
 */
public class Pseudocode extends Array<StringInformation> {

	public Pseudocode(Integer l) {
		super(l, new StringInformation());
		type = "StringInformation";
	}

	public void setType(String type) {
	}
}
