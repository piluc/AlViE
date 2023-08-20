package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.XMLStructureBean;
import org.apache.commons.digester.Digester;

public class XMLStructureParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/structure", XMLStructureBean.class);
		digester.addCallMethod("*/structure", "setName", 1);
		digester.addCallParam("*/structure", 0, "name");
		digester.addCallMethod("*/structure", "setType", 1);
		digester.addCallParam("*/structure", 0, "type");
		digester.addCallMethod("*/structure", "setClassName", 1);
		digester.addCallParam("*/structure", 0, "className");
		digester.addCallMethod("*/structure", "setVisualClassName", 1);
		digester.addCallParam("*/structure", 0, "visualClassName");
		digester.addCallMethod("*/structure", "setDrawerClassName", 1);
		digester.addCallParam("*/structure", 0, "drawerClassName");
	}
}