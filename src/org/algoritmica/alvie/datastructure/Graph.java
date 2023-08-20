package org.algoritmica.alvie.datastructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.algoritmica.alvie.information.InformationI;

/*
 * This the graph data structure. It includes the methods defined
 * by the graph interface. These methods are implemented by making use
 * of two inner classes corresponding to arcs and nodes: the latter class
 * includes the adjacency list of a node. 
 * The class also includes some methods used by the graphical input developer application. 
 */
public class Graph<IN extends InformationI, IL extends InformationI> extends DataStructureA<IN> implements GraphI<IN, IL> {
	private class Arc {
		private IL label;

		private int firstNodeId;

		private int secondNodeId;

		private Arc(int firstNodeId, int secondNodeId) {
			this.firstNodeId = firstNodeId;
			this.secondNodeId = secondNodeId;
		}
	}

	public class Node {

		private Map<Integer, Integer> arcSet;

		private IN information;

		private int x;

		private int y;

		private Node(IN information, int x, int y) {
			this.information = information;
			this.x = x;
			this.y = y;
			arcSet = new HashMap<Integer, Integer>();
		}

		private void addArc(int nodeId, int arcId) {
			arcSet.put(nodeId, arcId);
		}

		private int getArcId(int nodeId) {
			return arcSet.get(nodeId);
		}

		private Collection<Integer> getLinkedNodeIds() {
			return arcSet.keySet();
		}

		private boolean isLinkedTo(int nodeId) {
			return arcSet.containsKey(nodeId);
		}

		private void removeArc(int nodeId) {
			arcSet.remove(nodeId);
		}

		private void setInformation(IN information) {
			this.information = information;
		}
	}

	private boolean isOriented;

	private Map<Integer, Node> nodeSet;

	private Map<Integer, Arc> arcSet;

	private int nextArcId, nextNodeId;

	private String arcType;

	public Graph(boolean isOriented, IN o1, IL o2) {
		nodeSet = new HashMap<Integer, Node>();
		arcSet = new HashMap<Integer, Arc>();
		this.isOriented = isOriented;
		nextNodeId = 0;
		nextArcId = 0;
		setType(o1.getClass().getSimpleName());
		setArcType(o2.getClass().getSimpleName());
	}

	/*
	 * This method adds a new arc to the graph, whose id is specified and whose
	 * source and destination nodes are also specified. In particular, an arc
	 * between the source node and the destination node is added to the
	 * collection of arcs. Moreover, the id of the arc is added to the adjacency
	 * list of the source node and, if the graph is not oriented, to the
	 * adjacency list of the destination node. Finally, the method returns the
	 * next available arc id, but if the specified id has already been used,
	 * then it returns null.
	 */
	public Integer addArc(int firstNodeId, int secondNodeId, int arcId) {
		if (!arcSet.containsKey(arcId)) {
			arcSet.put(arcId, new Arc(firstNodeId, secondNodeId));
			nodeSet.get(firstNodeId).addArc(secondNodeId, arcId);
			if (!isOriented) {
				nodeSet.get(secondNodeId).addArc(firstNodeId, arcId);
			}
			nextArcId = Math.max(arcId, nextArcId) + 1;
			return nextArcId;
		} else {
			return null;
		}
	}

	/*
	 * This method adds a new node to the graph, whose id is specified and whose
	 * information and coordinates are also specified. In particular, a node
	 * with the specified information and coordinates is added to the collection
	 * of nodes. Moreover, the method returns the next available node id, but if
	 * the specified id has already been used, then it returns null.
	 */
	public Integer addNode(IN info, int id, int x, int y) {
		if (!nodeSet.containsKey(id)) {
			Node node = new Node(info, x, y);
			nodeSet.put(id, node);
			nextNodeId = Math.max(id, nextNodeId) + 1;
			return nextNodeId;
		} else {
			return null;
		}
	}

	public Collection<Integer> getAdiacentNodeIds(int nodeId) {
		return nodeSet.get(nodeId).getLinkedNodeIds();
	}

	public Integer getArcId(int firstNodeId, int secondNodeId) {
		return nodeSet.get(firstNodeId).getArcId(secondNodeId);
	}

	public IL getArcLabel(int firstNodeId, int secondNodeId) {
		return arcSet.get(nodeSet.get(firstNodeId).getArcId(secondNodeId)).label;
	}

	public Node getNode(int id) {
		return nodeSet.get(id);
	}

	public Collection<Integer> getNodeIds() {
		return nodeSet.keySet();
	}

	public int getNodeAbscissa(int nodeId) {
		return nodeSet.get(nodeId).x;
	}

	public IN getNodeInformation(int nodeId) {
		return nodeSet.get(nodeId).information;
	}

	public int getNodeOrdinate(int nodeId) {
		return nodeSet.get(nodeId).y;
	}

	public boolean isArc(int firstNodeId, int secondNodeId) {
		return nodeSet.get(firstNodeId).isLinkedTo(secondNodeId);
	}

	public boolean isArc(int arcId) {
		return arcSet.containsKey(arcId);
	}

	public boolean isNode(int nodeId) {
		return arcSet.containsKey(nodeId);
	}

	public boolean isOriented() {
		return isOriented;
	}

	/*
	 * This method adds a new arc to the graph, whose source and destination
	 * nodes are specified. In particular, an arc between the source node and
	 * the destination node is added to the collection of arcs: the id of this
	 * arc is the next available id. Moreover, the id of the arc is added to the
	 * adjacency list of the source node and, if the graph is not oriented, to
	 * the adjacency list of the destination node. Finally, the method returns
	 * the arc id.
	 */
	public Integer newArc(int firstNodeId, int secondNodeId) {
		arcSet.put(nextArcId, new Arc(firstNodeId, secondNodeId));
		nodeSet.get(firstNodeId).addArc(secondNodeId, nextArcId);
		if (!isOriented) {
			nodeSet.get(secondNodeId).addArc(firstNodeId, nextArcId);
		}
		nextArcId = nextArcId + 1;
		return nextArcId - 1;
	}

	/*
	 * This method adds a new node to the graph, whose information and
	 * coordinates are specified. In particular, a node with the specified
	 * information and coordinates is added to the collection of nodes.
	 * Moreover, the method returns the node id.
	 */
	public Integer newNode(IN info, int x, int y) {
		Node node = new Node(info, x, y);
		nodeSet.put(nextNodeId, node);
		nextNodeId = nextNodeId + 1;
		return nextNodeId - 1;
	}

	/*
	 * This method removes an arc from the graph, whose source and destination
	 * nodes are specified. In particular, the id of the arc is removed from the
	 * collection of arc ids and from the adjacency list of the source node and,
	 * if the graph is not oriented, from the adjacency list of the destination
	 * node. Moreover, the next available arc id is updated in case the arc is
	 * the last inserted. Finally, the method returns the arc id, but if the arc
	 * does not exists then it returns null.
	 */
	public Integer removeArc(int firstNodeId, int secondNodeId) {
		Integer arcId = nodeSet.get(firstNodeId).getArcId(secondNodeId);
		if (arcId != null) {
			arcSet.remove(arcId);
			nodeSet.get(firstNodeId).removeArc(secondNodeId);
			if (!isOriented) {
				nodeSet.get(secondNodeId).removeArc(firstNodeId);
			}
			if (arcId == nextArcId - 1) {
				nextArcId = arcId;
			}
		}
		return arcId;
	}

	/*
	 * This method removes an arc from the graph, whose id is specified. In
	 * particular, the id of the arc is removed from the adjacency list of the
	 * source node and, if the graph is not oriented, from the adjacency list of
	 * the destination node. The id is also removed from the collection of arc
	 * ids. Moreover, the next available arc id is updated in case the arc is
	 * the last inserted. Finally, the method returns the next available arc id,
	 * but if the arc does not exists then it returns null.
	 */
	public Integer removeArc(int arcId) {
		if (arcSet.containsKey(arcId)) {
			nodeSet.get(arcSet.get(arcId).firstNodeId).removeArc(arcSet.get(arcId).secondNodeId);
			if (!isOriented) {
				nodeSet.get(arcSet.get(arcId).secondNodeId).removeArc(arcSet.get(arcId).firstNodeId);
			}
			arcSet.remove(arcId);
			if (arcId == nextArcId - 1) {
				nextArcId = arcId;
			}
			return nextArcId;
		} else {
			return null;
		}
	}

	/*
	 * This method removes a node from the graph, whose id is specified. In
	 * particular, the id of the arc is removed from the adjacency list of all
	 * nodes adjacent to the specified node. The id is also removed from the
	 * collection of node ids. Moreover, the next available node id is updated
	 * in case the node is the last inserted. Finally, the method returns the
	 * next available node id, but if the node does not exists then it returns
	 * null.
	 */
	public Integer removeNode(int nodeId) {
		if (nodeSet.containsKey(nodeId)) {
			Collection<Integer> allNodes = getNodeIds();
			Collection<Integer> successorNodes = new Vector<Integer>();
			Collection<Integer> predecessorNodes = new Vector<Integer>();
			for (int id : allNodes) {
				if (isArc(id, nodeId)) {
					predecessorNodes.add(id);
				}
				if (isArc(nodeId, id)) {
					successorNodes.add(id);
				}
			}
			for (int id : predecessorNodes) {
				removeArc(id, nodeId);
			}
			for (int id : successorNodes) {
				removeArc(nodeId, id);
			}
			nodeSet.remove(nodeId);
			if (nodeId == nextNodeId - 1) {
				nextNodeId = nodeId;
			}
			return nextNodeId;
		} else {
			return null;
		}
	}

	/*
	 * This method returns the arc id if the arc exists, null otherwise.
	 */
	public Integer setArcLabel(int firstNodeId, int secondNodeId, IL info) {
		Integer arcId = nodeSet.get(firstNodeId).getArcId(secondNodeId);
		if (arcId != null) {
			arcSet.get(nodeSet.get(firstNodeId).getArcId(secondNodeId)).label = info;
		}
		return arcId;
	}

	/*
	 * This method returns the node id if the node exists, null otherwise.
	 */
	public Integer setNodeCoordinates(int nodeId, int x, int y) {
		if (nodeSet.containsKey(nodeId)) {
			nodeSet.get(nodeId).x = x;
			nodeSet.get(nodeId).y = y;
			return nodeId;
		} else {
			return null;
		}
	}

	/*
	 * This method returns the node id if the node exists, null otherwise.
	 */
	public Integer setNodeInformation(int nodeId, IN info) {
		if (nodeSet.containsKey(nodeId)) {
			nodeSet.get(nodeId).setInformation(info);
			return nodeId;
		} else {
			return null;
		}
	}

	public long createElementID(long... position) {
		long toret = position[0];
		if (position.length == 2) {
			toret = nextNodeId + getArcId((int) position[0], (int) position[1]) + 1;
		}
		return toret;
	}

	public InformationI getElementByID(long id) {
		InformationI toret = null;
		long[] position = getPositionByID(id);
		if (position.length == 1)
			toret = getNodeInformation((int) position[0]);
		else if (position.length == 2)
			toret = getArcLabel((int) position[0], (int) position[1]);
		return toret;
	}

	public long[] getPositionByID(long id) {
		long[] toret = null;
		if (id <= nextNodeId) {
			toret = new long[1];
			toret[0] = id;
		} else {
			toret = new long[2];
			id -= nextNodeId + 1;
			for (Integer nodeId : nodeSet.keySet()) {
				for (Integer idDest : nodeSet.get(nodeId).getLinkedNodeIds()) {
					if (nodeSet.get(nodeId).getArcId(idDest.intValue()) == id) {
						toret[0] = nodeId;
						toret[1] = idDest.longValue();
					}
				}
			}
		}
		return toret;
	}

	@SuppressWarnings("unchecked")
	public void setElementByID(InformationI newValue, long id) {
		long[] position = getPositionByID(id);
		if (position.length == 1)
			setNodeInformation((int) position[0], (IN) newValue);
		else if (position.length == 2)
			setArcLabel((int) position[0], (int) position[1], (IL) newValue);
	}

	public void changeLocation(long elementID, int newX, int newY) {
		Node toMove = getNode((int) elementID);
		if (toMove != null) {
			toMove.x = newX;
			toMove.y = newY;
		}
	}

	public String getArcType() {
		return arcType;
	}

	public void setArcType(String type) {
		arcType = type;
	}

}
