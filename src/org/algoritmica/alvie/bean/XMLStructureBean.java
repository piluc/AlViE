package org.algoritmica.alvie.bean;

public class XMLStructureBean {

	private String name;

	private String type;

	private StructureBean structureBean;

	private VisualStructureBean visualStructureBean;

	private MessageBean messageBean;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MessageBean getMessageBean() {
		return messageBean;
	}

	public void setMessageBean(MessageBean messageBean) {
		this.messageBean = messageBean;
	}

	public StructureBean getStructureBean() {
		return structureBean;
	}

	public void setStructureBean(StructureBean structureBean) {
		this.structureBean = structureBean;
	}

	public VisualStructureBean getVisualStructureBean() {
		return visualStructureBean;
	}

	public void setVisualStructureBean(VisualStructureBean visualStructureBean) {
		this.visualStructureBean = visualStructureBean;
	}
}
