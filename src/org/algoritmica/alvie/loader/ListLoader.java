package org.algoritmica.alvie.loader;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;

import org.algoritmica.alvie.bean.ListBean;
import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.List;
import org.algoritmica.alvie.datastructure.ListI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.MessageUtility;

/*
 * This is the class that loads the information contained into a list structure bean
 * into a list data structure. The loading method simply reads all the element values
 * for the bean and stores them into the corresponding position in the list: to this aim,
 * the method passes trough the construction of an array so that the elements can be
 * inserted in the right order (this is due to the fact that the list interface only allows
 * insertion at its head position).
 */
public class ListLoader implements LoaderI<ListI<InformationI>> {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 803000;

	private String informationPackagePrefix = "org.algoritmica.alvie.information.";

	public ListI<InformationI> load(StructureBean structureBean) {
		ListI<InformationI> list = null;
		try {
			ListBean listBean = (ListBean) structureBean;
			Class<?> type = Class.forName(informationPackagePrefix + listBean.getType());
			Constructor<?> constructor = type.getConstructor(Class.forName("java.lang.String"));
			list = new List<InformationI>((InformationI) type.getDeclaredConstructor().newInstance());
			int size = listBean.getElementCollection().keySet().size();
			InformationI[] listArray = new InformationI[size];
			for (Integer i : listBean.getElementCollection().keySet()) {
				listArray[i] = (InformationI) (constructor.newInstance(listBean.getElementCollection().get(i)));
			}
			ListI<InformationI> newHead;
			for (int i = size - 1; i >= 0; i--) {
				newHead = new List<InformationI>((InformationI) type.getDeclaredConstructor().newInstance());
				newHead.set(listArray[i], list);
				newHead.setType(type.getSimpleName());
				list = newHead;
			}
		} catch (Exception e) {
			MessageUtility.errorMessage("List" + localResourceBundle.getString("errorMessage005"), localResourceBundle.getString("title009"), errorId + 1);
		}
		return list;
	}

}
