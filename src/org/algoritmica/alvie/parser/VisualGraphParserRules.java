package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.VisualGraphBean;
import org.apache.commons.digester.Digester;

public class VisualGraphParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/visualGraph", VisualGraphBean.class);
		digester.addCallMethod("*/visualGraph", "setLoaderClassName", 1);
		digester.addCallParam("*/visualGraph", 0, "loaderClassName");
		digester.addCallMethod("*/visualGraph", "setDefaultColor", 1);
		digester.addCallParam("*/visualGraph", 0, "defaultColor");
		digester.addCallMethod("*/visualGraph", "setDefaultFont", 1);
		digester.addCallParam("*/visualGraph", 0, "defaultFont");
		digester.addCallMethod("*/visualGraph", "setDefaultShape", 1);
		digester.addCallParam("*/visualGraph", 0, "defaultShape");
		digester.addCallMethod("*/visualGraph", "setDefaultShapeHeight", 1);
		digester.addCallParam("*/visualGraph", 0, "defaultShapeHeight");
		digester.addCallMethod("*/visualGraph", "setDefaultShapeWidth", 1);
		digester.addCallParam("*/visualGraph", 0, "defaultShapeWidth");
		digester.addCallMethod("*/visualGraph", "setDefaultArcLabelFont", 1);
		digester.addCallParam("*/visualGraph", 0, "defaultArcLabelFont");
		digester.addCallMethod("*/visualGraph", "setDefaultLineColor", 1);
		digester.addCallParam("*/visualGraph", 0, "defaultLineColor");
		digester.addCallMethod("*/visualGraph", "setDefaultLineType", 1);
		digester.addCallParam("*/visualGraph", 0, "defaultLineType");
		digester.addCallMethod("*/visualGraph", "setDefaultLineThickness", 1);
		digester.addCallParam("*/visualGraph", 0, "defaultLineThickness");
		digester.addCallMethod("*/visualGraph", "setOriginX", 1);
		digester.addCallParam("*/visualGraph", 0, "originX");
		digester.addCallMethod("*/visualGraph", "setOriginY", 1);
		digester.addCallParam("*/visualGraph", 0, "originY");
		digester.addCallMethod("*/visualGraph/visualNode", "setElementColor", 2);
		digester.addCallParam("*/visualGraph/visualNode", 0, "id");
		digester.addCallParam("*/visualGraph/visualNode", 1, "color");
		digester.addCallMethod("*/visualGraph/visualNode", "setElementFont", 2);
		digester.addCallParam("*/visualGraph/visualNode", 0, "id");
		digester.addCallParam("*/visualGraph/visualNode", 1, "font");
		digester.addCallMethod("*/visualGraph/visualNode", "setElementShape", 2);
		digester.addCallParam("*/visualGraph/visualNode", 0, "id");
		digester.addCallParam("*/visualGraph/visualNode", 1, "shape");
		digester.addCallMethod("*/visualGraph/visualNode", "setElementShapeHeight", 2);
		digester.addCallParam("*/visualGraph/visualNode", 0, "id");
		digester.addCallParam("*/visualGraph/visualNode", 1, "shapeHeight");
		digester.addCallMethod("*/visualGraph/visualNode", "setElementShapeWidth", 2);
		digester.addCallParam("*/visualGraph/visualNode", 0, "id");
		digester.addCallParam("*/visualGraph/visualNode", 1, "shapeWidth");
		digester.addCallMethod("*/visualGraph/visualArc", "setArcLabelFont", 2);
		digester.addCallParam("*/visualGraph/visualArc", 0, "id");
		digester.addCallParam("*/visualGraph/visualArc", 1, "labelFont");
		digester.addCallMethod("*/visualGraph/visualArc", "setLineColor", 2);
		digester.addCallParam("*/visualGraph/visualArc", 0, "id");
		digester.addCallParam("*/visualGraph/visualArc", 1, "lineColor");
		digester.addCallMethod("*/visualGraph/visualArc", "setLineType", 2);
		digester.addCallParam("*/visualGraph/visualArc", 0, "id");
		digester.addCallParam("*/visualGraph/visualArc", 1, "lineType");
		digester.addCallMethod("*/visualGraph/visualArc", "setLineThickness", 2);
		digester.addCallParam("*/visualGraph/visualArc", 0, "id");
		digester.addCallParam("*/visualGraph/visualArc", 1, "lineThickness");
	}
}