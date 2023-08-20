package org.algoritmica.alvie.loader;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;

import org.algoritmica.alvie.bean.GeometricFigureBean;
import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.datastructure.GeometricFigureI;
import org.algoritmica.alvie.information.GeometricObjectInformation;
import org.algoritmica.alvie.utility.MessageUtility;

/*
 * This is the class that loads the geometric figure contained into a geometric figure bean
 * into a geometric figure data structure. The loading method simply read all the element values
 * for the bean and stores them into the corresponding position in the figure.
 */
public class GeometricFigureLoader implements LoaderI<GeometricFigureI> {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 803000;

	private String informationPackagePrefix = "org.algoritmica.alvie.information.";

	public GeometricFigure load(StructureBean structureBean) {
		GeometricFigure figure = new GeometricFigure();
		try {
			GeometricFigureBean figureBean = (GeometricFigureBean) structureBean;
			Class<?> type = Class.forName(informationPackagePrefix + "GeometricObjectInformation");
			Constructor<?> constructor = type.getConstructor(Class.forName("java.lang.String"));
			int size = figureBean.getElementCollection().keySet().size();
			GeometricObjectInformation[] figureArray = new GeometricObjectInformation[size];
			for (Integer i : figureBean.getElementCollection().keySet()) {
				figureArray[i] = (GeometricObjectInformation) (constructor.newInstance(figureBean.getElementCollection().get(i)));
			}
			for (int i = 0; i < size; i++) {
				figure.add(figureArray[i]);
			}
		} catch (Exception e) {
			MessageUtility.errorMessage("Geometric figure" + localResourceBundle.getString("errorMessage005") + ": " + e.getMessage(), localResourceBundle.getString("title009"), errorId + 1);
		}
		return figure;
	}

}
