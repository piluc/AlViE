package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.VisualMatrixBean;
import org.apache.commons.digester.Digester;

public class VisualMatrixParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/visualMatrix", VisualMatrixBean.class);
		digester.addCallMethod("*/visualMatrix", "setLoaderClassName", 1);
		digester.addCallParam("*/visualMatrix", 0, "loaderClassName");
		digester.addCallMethod("*/visualMatrix", "setDefaultColor", 1);
		digester.addCallParam("*/visualMatrix", 0, "defaultColor");
		digester.addCallMethod("*/visualMatrix", "setDefaultFont", 1);
		digester.addCallParam("*/visualMatrix", 0, "defaultFont");
		digester.addCallMethod("*/visualMatrix", "setDefaultShapeHeight", 1);
		digester.addCallParam("*/visualMatrix", 0, "defaultShapeHeight");
		digester.addCallMethod("*/visualMatrix", "setDefaultShapeWidth", 1);
		digester.addCallParam("*/visualMatrix", 0, "defaultShapeWidth");
		digester.addCallMethod("*/visualMatrix", "setOriginX", 1);
		digester.addCallParam("*/visualMatrix", 0, "originX");
		digester.addCallMethod("*/visualMatrix", "setOriginY", 1);
		digester.addCallParam("*/visualMatrix", 0, "originY");
		digester.addCallMethod("*/visualMatrix/visualElement", "setElementColor", 2);
		digester.addCallParam("*/visualMatrix/visualElement", 0, "id");
		digester.addCallParam("*/visualMatrix/visualElement", 1, "color");
		digester.addCallMethod("*/visualMatrix/visualElement", "setElementFont", 2);
		digester.addCallParam("*/visualMatrix/visualElement", 0, "id");
		digester.addCallParam("*/visualMatrix/visualElement", 1, "font");
	}
}
