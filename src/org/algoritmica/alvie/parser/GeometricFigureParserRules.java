package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.GeometricFigureBean;
import org.apache.commons.digester.Digester;

public class GeometricFigureParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/geometricFigure", GeometricFigureBean.class);
		digester.addCallMethod("*/geometricFigure", "setSize", 1);
		digester.addCallParam("*/geometricFigure", 0, "size");
		digester.addCallMethod("*/geometricFigure", "setLoaderClassName", 1);
		digester.addCallParam("*/geometricFigure", 0, "loaderClassName");
		digester.addCallMethod("*/geometricFigure/element", "setElementValue", 2);
		digester.addCallParam("*/geometricFigure/element", 0, "id");
		digester.addCallParam("*/geometricFigure/element", 1, "value");
	}
}