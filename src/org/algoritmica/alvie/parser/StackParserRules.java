package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.StackBean;
import org.apache.commons.digester.Digester;

public class StackParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/stack", StackBean.class);
		digester.addCallMethod("*/stack", "setSize", 1);
		digester.addCallParam("*/stack", 0, "size");
		digester.addCallMethod("*/stack", "setType", 1);
		digester.addCallParam("*/stack", 0, "type");
		digester.addCallMethod("*/stack", "setLoaderClassName", 1);
		digester.addCallParam("*/stack", 0, "loaderClassName");
		digester.addCallMethod("*/stack/element", "setElementValue", 2);
		digester.addCallParam("*/stack/element", 0, "id");
		digester.addCallParam("*/stack/element", 1, "value");
	}
}