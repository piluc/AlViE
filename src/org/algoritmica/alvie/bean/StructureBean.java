package org.algoritmica.alvie.bean;

/*
 * This the common data structure bean, which has to be extended from all other 
 * data structure beans: it simply allows us to manage the type of the information
 * contained in the data structure elements.
 */
public class StructureBean {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
