package org.algoritmica.alvie.gui;

import java.io.File;
import java.io.FileReader;
import java.util.ResourceBundle;

import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.Pseudocode;
import org.algoritmica.alvie.loader.StructureLoader;
import org.algoritmica.alvie.utility.MessageUtility;

public class PseudocodeReader {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 606000;

	public Pseudocode load(File file) {
		Pseudocode pseudocode = null;
		StructureLoader structureLoader = new StructureLoader();
		try {
			if (!"Pseudocode".equals(structureLoader.getStructureType(new FileReader(file)))) {
				MessageUtility.errorMessage(localResourceBundle.getString("errorMessage025"), localResourceBundle.getString("title039"), errorId + 1);
			}
			pseudocode = (Pseudocode) structureLoader.loadStructure(new FileReader(file), "Pseudocode");
		} catch (Exception e) {
			MessageUtility.errorMessage(localResourceBundle.getString("errorMessage026") + e.getMessage(), localResourceBundle.getString("title039"), errorId + 2);
		}
		return pseudocode;
	}
}
