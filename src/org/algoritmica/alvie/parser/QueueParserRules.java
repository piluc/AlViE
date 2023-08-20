package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.QueueBean;
import org.apache.commons.digester.Digester;

public class QueueParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/queue", QueueBean.class);
		digester.addCallMethod("*/queue", "setSize", 1);
		digester.addCallParam("*/queue", 0, "size");
		digester.addCallMethod("*/queue", "setType", 1);
		digester.addCallParam("*/queue", 0, "type");
		digester.addCallMethod("*/queue", "setLoaderClassName", 1);
		digester.addCallParam("*/queue", 0, "loaderClassName");
		digester.addCallMethod("*/queue/element", "setElementValue", 2);
		digester.addCallParam("*/queue/element", 0, "id");
		digester.addCallParam("*/queue/element", 1, "value");
	}
}