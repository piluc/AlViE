package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.MatrixBean;
import org.apache.commons.digester.Digester;

public class MatrixParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/matrix", MatrixBean.class);
		digester.addCallMethod("*/matrix", "setHeight", 1);
		digester.addCallParam("*/matrix", 0, "height");
		digester.addCallMethod("*/matrix", "setWidth", 1);
		digester.addCallParam("*/matrix", 0, "width");
		digester.addCallMethod("*/matrix", "setType", 1);
		digester.addCallParam("*/matrix", 0, "type");
		digester.addCallMethod("*/matrix", "setLoaderClassName", 1);
		digester.addCallParam("*/matrix", 0, "loaderClassName");
		digester.addCallMethod("*/matrix/element", "setElementValue", 2);
		digester.addCallParam("*/matrix/element", 0, "id");
		digester.addCallParam("*/matrix/element", 1, "value");
	}
}
