package org.algoritmica.alvie.loader;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;

import org.algoritmica.alvie.bean.ArrayBean;
import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.ArrayI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.MessageUtility;

/*
 * This is the class that loads the information contained into an array structure bean
 * into an array data structure. The loading method simply read all the element values
 * for the bean and stores them into the corresponding position in the array.
 */
public class ArrayLoader implements LoaderI<ArrayI<InformationI>> {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 800000;

	private String informationPackagePrefix = "org.algoritmica.alvie.information.";

	public ArrayI<InformationI> load(StructureBean structureBean) {
		ArrayI<InformationI> array = null;
		try {
			ArrayBean arrayBean = (ArrayBean) structureBean;
			Class<?> type = Class.forName(informationPackagePrefix + arrayBean.getType());
			Constructor<?> constructor = type.getConstructor(Class.forName("java.lang.String"));
			array = new Array<InformationI>(arrayBean.getSize(), (InformationI) type.getDeclaredConstructor().newInstance());
			array.setType(type.getSimpleName());
			for (Integer i : arrayBean.getElementCollection().keySet()) {
				array.setAt((InformationI) (constructor.newInstance(arrayBean.getElementCollection().get(i))), i);
			}
		} catch (Exception e) {
			MessageUtility.errorMessage("Array" + localResourceBundle.getString("errorMessage005"), localResourceBundle.getString("title009"), errorId + 1);
		}
		return array;
	}

}
