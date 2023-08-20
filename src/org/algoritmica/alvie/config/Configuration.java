package org.algoritmica.alvie.config;

import java.util.ResourceBundle;

import org.algoritmica.alvie.desktop.Main;

public class Configuration {
	private static Configuration instance;

	private ResourceBundle localConfigurationBundle;

	private String language;

	public synchronized static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}

	private Configuration() {
		language = Main.config.getProperty("language");
		localConfigurationBundle = ResourceBundle.getBundle("configuration." + language + ".alvie");
	}

	public ResourceBundle getLocalConfigurationBundle() {
		return localConfigurationBundle;
	}

	public String getLanguage() {
		return language;
	}

}
