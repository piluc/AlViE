package org.algoritmica.alvie.bean;

import java.util.HashMap;
import java.util.Map;

/*
 * This the graph bean which allows us to manage the type of arc information,
 * the collections of nodes, node coordinates, arc end-points, and arc labels,
 * and the fact whether the graph is oriented or not. 
 */
public class GraphBean extends StructureBean {
	private String arcLabelType;

	private boolean isOriented;

	private Map<Integer, String> nodeValueCollection = new HashMap<Integer, String>();

	private Map<Integer, Integer> nodeXCollection = new HashMap<Integer, Integer>();

	private Map<Integer, Integer> nodeYCollection = new HashMap<Integer, Integer>();

	private Map<Integer, Integer> arcFirstNodeCollection = new HashMap<Integer, Integer>();

	private Map<Integer, Integer> arcSecondNodeCollection = new HashMap<Integer, Integer>();

	private Map<Integer, String> arcLabelCollection = new HashMap<Integer, String>();

	public String getArcLabelType() {
		return arcLabelType;
	}

	public void setArcLabelType(String arcLabelType) {
		this.arcLabelType = arcLabelType;
	}

	public Map<Integer, String> getNodeValueCollection() {
		return nodeValueCollection;
	}

	public void setNodeValue(String id, String value) {
		nodeValueCollection.put(Integer.parseInt(id), value);
	}

	public Map<Integer, Integer> getNodeXCollection() {
		return nodeXCollection;
	}

	public void setNodeX(String id, String x) {
		nodeXCollection.put(Integer.parseInt(id), Integer.parseInt(x));
	}

	public Map<Integer, Integer> getNodeYCollection() {
		return nodeYCollection;
	}

	public void setNodeY(String id, String y) {
		nodeYCollection.put(Integer.parseInt(id), Integer.parseInt(y));
	}

	public boolean isOriented() {
		return isOriented;
	}

	public void setOriented(String isOriented) {
		this.isOriented = Boolean.parseBoolean(isOriented);
	}

	public Map<Integer, Integer> getArcFirstNodeCollection() {
		return arcFirstNodeCollection;
	}

	public void setArcFirstNode(String id, String nodeId) {
		arcFirstNodeCollection.put(Integer.parseInt(id), Integer.parseInt(nodeId));
	}

	public Map<Integer, Integer> getArcSecondNodeCollection() {
		return arcSecondNodeCollection;
	}

	public void setArcSecondNode(String id, String nodeId) {
		arcSecondNodeCollection.put(Integer.parseInt(id), Integer.parseInt(nodeId));
	}

	public Map<Integer, String> getArcLabelCollection() {
		return arcLabelCollection;
	}

	public void setArcLabel(String id, String label) {
		arcLabelCollection.put(Integer.parseInt(id), label);
	}
}
