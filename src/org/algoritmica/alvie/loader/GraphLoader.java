package org.algoritmica.alvie.loader;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.ResourceBundle;

import org.algoritmica.alvie.bean.GraphBean;
import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.Graph;
import org.algoritmica.alvie.datastructure.GraphI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.MessageUtility;

/*
 * This is the class that loads the information contained into a graph structure bean
 * into a graph data structure. The loading method simply reads all the element values
 * for the bean and stores them into the corresponding nodes and arcs in the graph.
 */
public class GraphLoader implements LoaderI<GraphI<InformationI, InformationI>> {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 802000;

	private String informationPackagePrefix = "org.algoritmica.alvie.information.";

	public GraphI<InformationI, InformationI> load(StructureBean structureBean) {
		GraphI<InformationI, InformationI> graph = null;
		try {
			GraphBean graphBean = (GraphBean) structureBean;
			Collection<Integer> nodeIdCollection = graphBean.getNodeValueCollection().keySet();
			Collection<Integer> arcIdCollection = graphBean.getArcFirstNodeCollection().keySet();
			Class<?> type = Class.forName(informationPackagePrefix + graphBean.getType());
			Constructor<?> constructor = type.getConstructor(Class.forName("java.lang.String"));
			Class<?> labelType = Class.forName(informationPackagePrefix + graphBean.getArcLabelType());
			Constructor<?> labelConstructor = labelType.getConstructor(Class.forName("java.lang.String"));
			graph = new Graph<InformationI, InformationI>(graphBean.isOriented(), (InformationI) type.getDeclaredConstructor().newInstance(), (InformationI) labelType.getDeclaredConstructor().newInstance());
			for (int nodeId : nodeIdCollection) {
				graph.addNode((InformationI) (constructor.newInstance(graphBean.getNodeValueCollection().get(nodeId))), nodeId, graphBean.getNodeXCollection().get(nodeId), graphBean.getNodeYCollection().get(nodeId));
			}
			for (int arcId : arcIdCollection) {
				graph.addArc(graphBean.getArcFirstNodeCollection().get(arcId), graphBean.getArcSecondNodeCollection().get(arcId), arcId);
				if (graphBean.getArcLabelCollection().get(arcId) != null) {
					graph.setArcLabel(graphBean.getArcFirstNodeCollection().get(arcId), graphBean.getArcSecondNodeCollection().get(arcId), (InformationI) (labelConstructor.newInstance(graphBean.getArcLabelCollection().get(arcId))));
				}
			}

		} catch (Exception e) {
			MessageUtility.errorMessage("Graph" + localResourceBundle.getString("errorMessage005"), localResourceBundle.getString("title009"), errorId + 1);
		}
		return graph;
	}

}
