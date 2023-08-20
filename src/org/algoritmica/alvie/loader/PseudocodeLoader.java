package org.algoritmica.alvie.loader;

import java.util.ResourceBundle;

import org.algoritmica.alvie.bean.PseudocodeBean;
import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.Pseudocode;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.MessageUtility;

/*
 * This is the class that loads the information contained into a pseudo-code structure bean
 * into a pseudo-code data structure. The loading method simply read all the element values
 * for the bean and stores them into the corresponding position in the pseudo-code.
 */
public class PseudocodeLoader implements LoaderI<Pseudocode> {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 805000;

	public Pseudocode load(StructureBean structureBean) {
		Pseudocode pseudocode = null;
		try {
			PseudocodeBean pseudocodeBean = (PseudocodeBean) structureBean;
			pseudocode = new Pseudocode(pseudocodeBean.getSize());
			for (Integer i : pseudocodeBean.getElementCollection().keySet()) {
				pseudocode.setAt(new StringInformation(pseudocodeBean.getElementCollection().get(i)), i);
			}
		} catch (Exception e) {
			MessageUtility.errorMessage("Pseudocode" + localResourceBundle.getString("errorMessage005"), localResourceBundle.getString("title009"), errorId + 1);
		}
		return pseudocode;
	}

}
