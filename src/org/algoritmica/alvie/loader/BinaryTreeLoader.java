package org.algoritmica.alvie.loader;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;

import org.algoritmica.alvie.bean.BinaryTreeBean;
import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.BinaryTree;
import org.algoritmica.alvie.datastructure.BinaryTreeI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.MessageUtility;

/*
 * This is the class that loads the information contained into a binary tree structure bean
 * into a binary tree data structure. The loading method simply read all the element values
 * for the bean and stores them into the corresponding position in the binary tree.
 */
public class BinaryTreeLoader implements LoaderI<BinaryTreeI<InformationI>> {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 801000;

	private String informationPackagePrefix = "org.algoritmica.alvie.information.";

	private BinaryTreeI<InformationI> recursivelyBuildBinaryTree(BinaryTreeBean binaryTreeBean, Integer position, Constructor<?> constructor, Class<?> type) {
		BinaryTreeI<InformationI> binaryTree = null;
		try {
			binaryTree = new BinaryTree<InformationI>((InformationI) type.getDeclaredConstructor().newInstance());
			if (binaryTreeBean.getElementCollection().keySet().contains(position)) {
				BinaryTreeI<InformationI> leftSubtree = recursivelyBuildBinaryTree(binaryTreeBean, 2 * position + 1, constructor, type);
				BinaryTreeI<InformationI> rightSubtree = recursivelyBuildBinaryTree(binaryTreeBean, 2 * position + 2, constructor, type);
				InformationI information = (InformationI) (constructor.newInstance(binaryTreeBean.getElementCollection().get(position)));
				binaryTree.set(information, leftSubtree, rightSubtree);
			}
		} catch (Exception e) {
			MessageUtility.errorMessage("BinaryTree" + localResourceBundle.getString("errorMessage005"), localResourceBundle.getString("title009"), errorId + 1);
		}
		return binaryTree;
	}

	public BinaryTreeI<InformationI> load(StructureBean structureBean) {
		BinaryTreeBean binaryTreeBean = (BinaryTreeBean) structureBean;
		BinaryTreeI<InformationI> binaryTree = null;
		try {
			Class<?> type = Class.forName(informationPackagePrefix + binaryTreeBean.getType());
			Constructor<?> constructor = type.getConstructor(Class.forName("java.lang.String"));
			binaryTree = recursivelyBuildBinaryTree(binaryTreeBean, 0, constructor, type);
			binaryTree.setType(type.getSimpleName());
		} catch (Exception e) {
			MessageUtility.errorMessage("BinaryTree" + localResourceBundle.getString("errorMessage005"), localResourceBundle.getString("title009"), errorId + 2);
		}
		return binaryTree;
	}

}
