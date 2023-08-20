package org.algoritmica.alvie.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.desktop.Main;

public class VisualInnerClass {
	private String algorithmPath;

	private OutputStream outputStream;

	private Properties properties;

	private int errorId = 212000;

	public int step;

	public VisualInnerClass(String algorithmName, String visFile) {
		try {
			algorithmPath = Main.algDirPath + algorithmName + "/";
			properties = new Properties();
			properties.load(new FileInputStream(
					new File(algorithmPath + "config/visualization_"
							+ Configuration.getInstance().getLanguage()
							+ ".properties")));
			outputStream = StructureXMLDrawerUtility.openXMLFile(visFile);
			step = 0;
		} catch (Exception e) {
			MessageUtility.errorMessage(Configuration.getInstance()
					.getLocalConfigurationBundle().getString("errorMessage031")
					+ ". " + e.getCause(), Configuration.getInstance()
					.getLocalConfigurationBundle().getString("title055"),
					errorId + 1);
		}
	}

	public String getAlgorithmPath() {
		return algorithmPath;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public String getResource(String name) {
		return properties.getProperty(name);
	}
}
