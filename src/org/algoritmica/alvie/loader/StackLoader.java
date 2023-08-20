package org.algoritmica.alvie.loader;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.ResourceBundle;

import org.algoritmica.alvie.bean.StackBean;
import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.Stack;
import org.algoritmica.alvie.datastructure.StackI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.MessageUtility;

/*
 * This is the class that loads the information contained into a stack structure bean
 * into a stack data structure. The loading method simply read all the element values
 * for the bean and stores them into the corresponding position in the stack.
 */
public class StackLoader implements LoaderI<StackI<InformationI>> {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 807000;

	private String informationPackagePrefix = "org.algoritmica.alvie.information.";

	public StackI<InformationI> load(StructureBean structureBean) {
		StackI<InformationI> stack = null;
		try {
			StackBean stackBean = (StackBean) structureBean;
			Class<?> type = Class.forName(informationPackagePrefix + stackBean.getType());
			Constructor<?> constructor = type.getConstructor(Class.forName("java.lang.String"));
			stack = new Stack<InformationI>((InformationI) type.getDeclaredConstructor().newInstance());
			Map<Integer, String> elementCollection = stackBean.getElementCollection();
			int size = stackBean.getSize();
			for (int i = size - 1; i >= 0; i--) {
				stack.push((InformationI) (constructor.newInstance(elementCollection.get(i))));
			}
		} catch (Exception e) {
			MessageUtility.errorMessage("Stack" + localResourceBundle.getString("errorMessage005"), localResourceBundle.getString("title009"), errorId + 1);
		}
		return stack;
	}

}
