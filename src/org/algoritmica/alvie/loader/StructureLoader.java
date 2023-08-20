package org.algoritmica.alvie.loader;

import java.io.Reader;
import java.util.ResourceBundle;

import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.bean.XMLStructureBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.DataStructureI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.parser.MessageParserRules;
import org.algoritmica.alvie.parser.ParserRulesI;
import org.algoritmica.alvie.parser.XMLStructureParserRules;
import org.algoritmica.alvie.utility.LazyErrorHandlerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.apache.commons.digester.Digester;

public class StructureLoader {
	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final int errorId = 808000;

	private String loaderPackagePrefix = "org.algoritmica.alvie.loader.";

	private String parserPackagePrefix = "org.algoritmica.alvie.parser.";

	private Digester digester;

	private void resetDigester() {
		digester = new Digester();
		digester.setErrorHandler(new LazyErrorHandlerUtility());
	}

	public String getStructureType(Reader reader) {
		resetDigester();
		XMLStructureParserRules xmlStructureParserRules = new XMLStructureParserRules();
		xmlStructureParserRules.addParserRules(digester);
		String structureType = null;
		try {
			XMLStructureBean xmlStructureBean = (XMLStructureBean) digester.parse(reader);
			structureType = xmlStructureBean.getType();
		} catch (Exception e) {
			MessageUtility.errorMessage(localResourceBundle.getString("errorMessage006") + e.getMessage(), localResourceBundle.getString("title009"), errorId + 1);
		}
		return structureType;
	}

	@SuppressWarnings("unchecked")
	public DataStructureI<?> loadStructure(Reader reader, String structureType) {
		resetDigester();
		XMLStructureBean xmlStructureBean = null;
		try {
			xmlStructureBean = parseStructure(reader, structureType);
		} catch (Exception e) {
			MessageUtility.errorMessage(localResourceBundle.getString("errorMessage007") + e.getMessage(), localResourceBundle.getString("title010"), errorId + 2);
		}
		StructureBean structureBean = xmlStructureBean.getStructureBean();
		String loaderClassName = structureType + "Loader";
		DataStructureI<InformationI> dataStructure = null;
		try {
			LoaderI<DataStructureI<InformationI>> loader = (LoaderI<DataStructureI<InformationI>>) Class.forName(loaderPackagePrefix + loaderClassName).getDeclaredConstructor().newInstance();
			dataStructure = loader.load(structureBean);
		} catch (Exception e) {
			MessageUtility.errorMessage(localResourceBundle.getString("errorMessage006") + e.getMessage(), localResourceBundle.getString("title009"), errorId + 3);
		}
		return dataStructure;
	}

	public XMLStructureBean parseStructure(Reader reader, String structureType) {
		resetDigester();
		XMLStructureParserRules xmlStructureParserRules = new XMLStructureParserRules();
		xmlStructureParserRules.addParserRules(digester);
		try {
			ParserRulesI parserRules = (ParserRulesI) Class.forName(parserPackagePrefix + structureType + "ParserRules").getDeclaredConstructor().newInstance();
			parserRules.addParserRules(digester);
			digester.addSetNext("*/" + structureType.substring(0, 1).toLowerCase() + structureType.substring(1), "setStructureBean");
			parserRules = (ParserRulesI) Class.forName(parserPackagePrefix + "Visual" + structureType + "ParserRules").getDeclaredConstructor().newInstance();
			parserRules.addParserRules(digester);
			digester.addSetNext("*/visual" + structureType, "setVisualStructureBean");
			MessageParserRules messageParserRules = new MessageParserRules();
			messageParserRules.addParserRules(digester);
			digester.addSetNext("*/message", "setMessageBean");
		} catch (Exception e) {
			MessageUtility.errorMessage(structureType + localResourceBundle.getString("errorMessage008"), localResourceBundle.getString("title011"), errorId + 4);
		}
		XMLStructureBean xmlStructureBean = null;
		try {
			xmlStructureBean = (XMLStructureBean) digester.parse(reader);
		} catch (Exception e) {
			MessageUtility.errorMessage(localResourceBundle.getString("errorMessage007") + e.getMessage(), localResourceBundle.getString("title010"), errorId + 5);
		}
		return xmlStructureBean;
	}
}
