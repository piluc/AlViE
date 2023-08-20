package org.algoritmica.alvie.loader;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.ResourceBundle;

import org.algoritmica.alvie.bean.QueueBean;
import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.Queue;
import org.algoritmica.alvie.datastructure.QueueI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.MessageUtility;

/*
 * This is the class that loads the information contained into a queue structure bean
 * into a queue data structure. The loading method simply read all the element values
 * for the bean and stores them into the corresponding position in the queue.
 */
public class QueueLoader implements LoaderI<QueueI<InformationI>> {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 806000;

	private String informationPackagePrefix = "org.algoritmica.alvie.information.";

	public QueueI<InformationI> load(StructureBean structureBean) {
		QueueI<InformationI> queue = null;
		try {
			QueueBean queueBean = (QueueBean) structureBean;
			Class<?> type = Class.forName(informationPackagePrefix + queueBean.getType());
			Constructor<?> constructor = type.getConstructor(Class.forName("java.lang.String"));
			queue = new Queue<InformationI>((InformationI) type.getDeclaredConstructor().newInstance());
			Map<Integer, String> elementCollection = queueBean.getElementCollection();
			int size = queueBean.getSize();
			for (int i = 0; i < size; i++) {
				queue.enqueue((InformationI) (constructor.newInstance(elementCollection.get(i))));
			}
		} catch (Exception e) {
			MessageUtility.errorMessage("Pseudocode" + localResourceBundle.getString("errorMessage005"), localResourceBundle.getString("title009"), errorId + 1);
		}
		return queue;
	}

}
