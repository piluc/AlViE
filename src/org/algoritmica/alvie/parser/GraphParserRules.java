package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.GraphBean;
import org.apache.commons.digester.Digester;

public class GraphParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/graph", GraphBean.class);
		digester.addCallMethod("*/graph", "setType", 1);
		digester.addCallParam("*/graph", 0, "type");
		digester.addCallMethod("*/graph", "setArcLabelType", 1);
		digester.addCallParam("*/graph", 0, "arcLabelType");
		digester.addCallMethod("*/graph", "setOriented", 1);
		digester.addCallParam("*/graph", 0, "isOriented");
		digester.addCallMethod("*/graph", "setLoaderClassName", 1);
		digester.addCallParam("*/graph", 0, "loaderClassName");
		digester.addCallMethod("*/graph/node", "setNodeValue", 2);
		digester.addCallParam("*/graph/node", 0, "id");
		digester.addCallParam("*/graph/node", 1, "value");
		digester.addCallMethod("*/graph/node", "setNodeX", 2);
		digester.addCallParam("*/graph/node", 0, "id");
		digester.addCallParam("*/graph/node", 1, "x");
		digester.addCallMethod("*/graph/node", "setNodeY", 2);
		digester.addCallParam("*/graph/node", 0, "id");
		digester.addCallParam("*/graph/node", 1, "y");
		digester.addCallMethod("*/graph/arc", "setArcFirstNode", 2);
		digester.addCallParam("*/graph/arc", 0, "id");
		digester.addCallParam("*/graph/arc", 1, "idFirstNode");
		digester.addCallMethod("*/graph/arc", "setArcSecondNode", 2);
		digester.addCallParam("*/graph/arc", 0, "id");
		digester.addCallParam("*/graph/arc", 1, "idSecondNode");
		digester.addCallMethod("*/graph/arc", "setArcLabel", 2);
		digester.addCallParam("*/graph/arc", 0, "id");
		digester.addCallParam("*/graph/arc", 1, "label");
	}
}