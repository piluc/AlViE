package org.algoritmica.alvie.loader;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;

import org.algoritmica.alvie.bean.MatrixBean;
import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.datastructure.MatrixI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.MessageUtility;

/*
 * This is the class that loads the information contained into a matrix structure bean
 * into a matrix data structure. The loading method simply read all the element values
 * for the bean and stores them into the corresponding position in the matrix: the position
 * of an element is obtained by counting the elements from top to bottom and from left
 * to right.
 */
public class MatrixLoader implements LoaderI<MatrixI<InformationI>> {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 804000;

	private String informationPackagePrefix = "org.algoritmica.alvie.information.";

	public MatrixI<InformationI> load(StructureBean structureBean) {
		MatrixI<InformationI> matrix = null;
		try {
			MatrixBean matrixBean = (MatrixBean) structureBean;
			Class<?> type = Class.forName(informationPackagePrefix + matrixBean.getType());
			Constructor<?> constructor = type.getConstructor(Class.forName("java.lang.String"));
			matrix = new Matrix<InformationI>(matrixBean.getHeight(), matrixBean.getWidth(), (InformationI) type.getDeclaredConstructor().newInstance());
			for (Integer id : matrixBean.getElementCollection().keySet()) {
				int column = id % matrixBean.getWidth();
				int row = id / matrixBean.getWidth();
				matrix.setAt((InformationI) (constructor.newInstance(matrixBean.getElementCollection().get(id))), row, column);
			}
		} catch (Exception e) {
			MessageUtility.errorMessage("Matrix" + localResourceBundle.getString("errorMessage005"), localResourceBundle.getString("title009"), errorId + 1);
		}
		return matrix;

	}

}
