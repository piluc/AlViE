package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.VisualStackBean;
import org.apache.commons.digester.Digester;

/*
 * The XML declaration of a visual stack includes the element default color, font, 
 * and shape height and width, the origin coordinates (the origin is the bottom left
 * point of the top of the stack), and, for each element which has to be drawn differently from the
 * default way, its color, font, and shape height and width (the element is identified by
 * means of its position).
 */

public class VisualStackParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/visualStack", VisualStackBean.class);
		digester.addCallMethod("*/visualStack", "setLoaderClassName", 1);
		digester.addCallParam("*/visualStack", 0, "loaderClassName");
		digester.addCallMethod("*/visualStack", "setDefaultColor", 1);
		digester.addCallParam("*/visualStack", 0, "defaultColor");
		digester.addCallMethod("*/visualStack", "setDefaultFont", 1);
		digester.addCallParam("*/visualStack", 0, "defaultFont");
		digester.addCallMethod("*/visualStack", "setDefaultShapeHeight", 1);
		digester.addCallParam("*/visualStack", 0, "defaultShapeHeight");
		digester.addCallMethod("*/visualStack", "setDefaultShapeWidth", 1);
		digester.addCallParam("*/visualStack", 0, "defaultShapeWidth");
		digester.addCallMethod("*/visualStack", "setOriginX", 1);
		digester.addCallParam("*/visualStack", 0, "originX");
		digester.addCallMethod("*/visualStack", "setOriginY", 1);
		digester.addCallParam("*/visualStack", 0, "originY");
		digester.addCallMethod("*/visualStack/visualMalleableElement", "setElementColor", 2);
		digester.addCallParam("*/visualStack/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualStack/visualMalleableElement", 1, "color");
		digester.addCallMethod("*/visualStack/visualMalleableElement", "setElementFont", 2);
		digester.addCallParam("*/visualStack/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualStack/visualMalleableElement", 1, "font");
		digester.addCallMethod("*/visualStack/visualMalleableElement", "setElementShapeHeight", 2);
		digester.addCallParam("*/visualStack/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualStack/visualMalleableElement", 1, "shapeHeight");
		digester.addCallMethod("*/visualStack/visualMalleableElement", "setElementShapeWidth", 2);
		digester.addCallParam("*/visualStack/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualStack/visualMalleableElement", 1, "shapeWidth");
	}
}