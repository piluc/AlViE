package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.VisualGeometricFigureBean;
import org.apache.commons.digester.Digester;

public class VisualGeometricFigureParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/visualGeometricFigure", VisualGeometricFigureBean.class);
		digester.addCallMethod("*/visualGeometricFigure", "setLoaderClassName", 1);
		digester.addCallParam("*/visualGeometricFigure", 0, "loaderClassName");
		digester.addCallMethod("*/visualGeometricFigure", "setDefaultColor", 1);
		digester.addCallParam("*/visualGeometricFigure", 0, "defaultColor");
		digester.addCallMethod("*/visualGeometricFigure", "setDefaultFont", 1);
		digester.addCallParam("*/visualGeometricFigure", 0, "defaultFont");
		digester.addCallMethod("*/visualGeometricFigure", "setDefaultLineThickness", 1);
		digester.addCallParam("*/visualGeometricFigure", 0, "defaultLineThickness");
		digester.addCallMethod("*/visualGeometricFigure", "setOriginX", 1);
		digester.addCallParam("*/visualGeometricFigure", 0, "originX");
		digester.addCallMethod("*/visualGeometricFigure", "setOriginY", 1);
		digester.addCallParam("*/visualGeometricFigure", 0, "originY");
		digester.addCallMethod("*/visualGeometricFigure/visualGeometricObject", "setElementColor", 2);
		digester.addCallParam("*/visualGeometricFigure/visualGeometricObject", 0, "id");
		digester.addCallParam("*/visualGeometricFigure/visualGeometricObject", 1, "color");
		digester.addCallMethod("*/visualGeometricFigure/visualGeometricObject", "setElementFont", 2);
		digester.addCallParam("*/visualGeometricFigure/visualGeometricObject", 0, "id");
		digester.addCallParam("*/visualGeometricFigure/visualGeometricObject", 1, "font");
		digester.addCallMethod("*/visualGeometricFigure/visualGeometricObject", "setLineThickness", 2);
		digester.addCallParam("*/visualGeometricFigure/visualGeometricObject", 0, "id");
		digester.addCallParam("*/visualGeometricFigure/visualGeometricObject", 1, "lineThickness");
	}
}