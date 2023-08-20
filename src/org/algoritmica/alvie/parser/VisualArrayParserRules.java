package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.VisualArrayBean;
import org.apache.commons.digester.Digester;

/*
 * The XML declaration of a visual array includes the element default color, font, 
 * and shape height and width, the origin coordinates (the origin is the bottom left
 * point of the array), and, for each element which has to be drawn differently from the
 * default way, its color, font, and shape height and width (the element is identified by
 * means of its position).
 */
public class VisualArrayParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/visualArray", VisualArrayBean.class);
		digester.addCallMethod("*/visualArray", "setDefaultColor", 1);
		digester.addCallParam("*/visualArray", 0, "defaultColor");
		digester.addCallMethod("*/visualArray", "setDefaultFont", 1);
		digester.addCallParam("*/visualArray", 0, "defaultFont");
		digester.addCallMethod("*/visualArray", "setDefaultShapeHeight", 1);
		digester.addCallParam("*/visualArray", 0, "defaultShapeHeight");
		digester.addCallMethod("*/visualArray", "setDefaultShapeWidth", 1);
		digester.addCallParam("*/visualArray", 0, "defaultShapeWidth");
		digester.addCallMethod("*/visualArray", "setOriginX", 1);
		digester.addCallParam("*/visualArray", 0, "originX");
		digester.addCallMethod("*/visualArray", "setOriginY", 1);
		digester.addCallParam("*/visualArray", 0, "originY");
		digester.addCallMethod("*/visualArray/visualMalleableElement", "setElementColor", 2);
		digester.addCallParam("*/visualArray/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualArray/visualMalleableElement", 1, "color");
		digester.addCallMethod("*/visualArray/visualMalleableElement", "setElementFont", 2);
		digester.addCallParam("*/visualArray/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualArray/visualMalleableElement", 1, "font");
		digester.addCallMethod("*/visualArray/visualMalleableElement", "setElementShapeHeight", 2);
		digester.addCallParam("*/visualArray/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualArray/visualMalleableElement", 1, "shapeHeight");
		digester.addCallMethod("*/visualArray/visualMalleableElement", "setElementShapeWidth", 2);
		digester.addCallParam("*/visualArray/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualArray/visualMalleableElement", 1, "shapeWidth");
	}
}