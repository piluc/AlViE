package org.algoritmica.alvie.gui;

import java.io.File;
import java.io.FileReader;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.DataStructureI;
import org.algoritmica.alvie.loader.StructureLoader;
import org.algoritmica.alvie.utility.MessageUtility;

public class InputLoader {
	private final int errorId = 604000;

	private String inputFilePath;

	private String inputFileName;

	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private File file;

	public InputLoader(String algorithmPath, String algorithmName) {
		inputFilePath = algorithmPath + "input/";
		inputFileName = algorithmName + "Input.xml";
	}

	public DataStructureI<?> load(String structureType, String dialogTitle) {
		AlViEFileFilter xmlFileFilter = new AlViEFileFilter();
		xmlFileFilter.addExtension("xml");
		JFileChooser fileChooser;
		fileChooser = new JFileChooser(inputFilePath);
		fileChooser.setFileFilter(xmlFileFilter);
		fileChooser.setDialogTitle(dialogTitle);
		fileChooser.setSelectedFile(new File(inputFileName));
		int returnVal = fileChooser.showOpenDialog(null);
		DataStructureI<?> dataStructure = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile().getAbsoluteFile();
			StructureLoader structureLoader = new StructureLoader();
			try {
				if (!structureType.equals(structureLoader.getStructureType(new FileReader(file)))) {
					MessageUtility.errorMessage(localResourceBundle.getString("errorMessage002") + structureType, localResourceBundle.getString("title006"), errorId + 1);
				}
				dataStructure = structureLoader.loadStructure(new FileReader(file), structureType);
			} catch (Exception e) {
				MessageUtility.errorMessage(localResourceBundle.getString("errorMessage003"), localResourceBundle.getString("title007"), errorId + 2);
			}
		}
		return dataStructure;
	}
}
