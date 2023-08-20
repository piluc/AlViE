package org.algoritmica.alvie.datastructure;

import java.util.Collection;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the graph interface. It allows the programmer to manage the nodes and the
 * arcs of the graphs, their labels and the node coordinates. Each node and each
 * arc is identified by means of an integer id.
 */
public interface GraphI<IN extends InformationI, IL extends InformationI> extends DataStructureI<IN>, IdentifiableI, MovableI {

	public Integer addArc(int firstNodeId, int secondNodeId, int arcId);

	public Integer addNode(IN info, int id, int x, int y);

	public Collection<Integer> getAdiacentNodeIds(int nodeId);

	public Integer getArcId(int firstNodeId, int secondNodeId);

	public IL getArcLabel(int firstNodeId, int secondNodeId);

	public Collection<Integer> getNodeIds();

	public int getNodeAbscissa(int nodeId);

	public IN getNodeInformation(int nodeId);

	public int getNodeOrdinate(int nodeId);

	public boolean isArc(int firstNodeId, int secondNodeId);

	public boolean isArc(int arcId);

	public boolean isNode(int nodeId);

	public boolean isOriented();

	public Integer newArc(int firstNodeId, int secondNodeId);

	public Integer newNode(IN info, int x, int y);

	public Integer removeArc(int firstNodeId, int secondNodeId);

	public Integer removeNode(int nodeId);

	public Integer setArcLabel(int firstNodeId, int secondNodeId, IL label);

	public Integer setNodeCoordinates(int nodeId, int x, int y);

	public Integer setNodeInformation(int nodeId, IN info);

	public void setArcType(String type);

	public String getArcType();
}
