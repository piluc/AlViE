package org.algoritmica.alvie.drawer;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;

import org.algoritmica.alvie.datastructure.GraphI;
import org.algoritmica.alvie.information.InformationI;

/*
 * This graph drawer prints the textual representation of a graph, that is the 
 * list of nodes with their labels followed by the list of arcs. Each arc is 
 * represented by the id of its first node, followed 
 * by the '-->' string, and the id of its second node: if the arc has a label,
 * then it is printed in the middle of the '-->' string.
 */
public class GraphTextDrawer<IN extends InformationI, IL extends InformationI> extends GraphDrawerA {

	protected PrintWriter printWriter;

	public GraphTextDrawer(String name, OutputStream outputStream) {
		super(name);
		printWriter = new PrintWriter(outputStream, true);
	}

	public void draw(GraphI<IN, IL> graph) {
		IN nodeLabel;
		IL arcLabel;
		Collection<Integer> nodeIdCollection = graph.getNodeIds();
		for (int nodeId : nodeIdCollection) {
			printWriter.print(nodeId);
			nodeLabel = graph.getNodeInformation(nodeId);
			if (nodeLabel != null) {
				printWriter.println(nodeId + ": " + nodeLabel.stringValue());
			} else {
				printWriter.println(nodeId);
			}
		}
		for (int nodeId : nodeIdCollection) {
			Collection<Integer> adiacentNodeIdCollection = graph.getAdiacentNodeIds(nodeId);
			for (int adiacentNodeId : adiacentNodeIdCollection) {
				printWriter.print(nodeId);
				arcLabel = graph.getArcLabel(nodeId, adiacentNodeId);
				if (arcLabel != null) {
					printWriter.print("-" + arcLabel.stringValue() + "->" + adiacentNodeId + " ");
				} else {
					printWriter.print("-->" + adiacentNodeId + " ");
				}
			}
			printWriter.println();
		}
	}

	public void drawMessage(String message) {
		printWriter.println(message);
	}
}
