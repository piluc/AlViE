package org.algoritmica.alvie.bean;

import java.util.ArrayList;
import java.util.Collection;

public class StepBean {

	private int number;

	private Collection<XMLStructureBean> xmlStructureBeanCollection = new ArrayList<XMLStructureBean>();

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Collection<XMLStructureBean> getXMLStructureBeanCollection() {
		return xmlStructureBeanCollection;
	}

	public void addXMLStructureBean(XMLStructureBean xmlStructureBean) {
		xmlStructureBeanCollection.add(xmlStructureBean);
	}
}
