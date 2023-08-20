package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.VisualPseudocodeBean;
import org.apache.commons.digester.Digester;

/*
 * The XML declaration of a visual pseudo-code includes the code line default color, font, 
 * and shape height and width, the origin coordinates (the origin is the top left
 * point of the pseudo-code), and, for each code line which has to be drawn differently from the
 * default way, its color, font, and shape height and width (the code line is identified by
 * means of its position).
 */
public class VisualPseudocodeParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/visualPseudocode", VisualPseudocodeBean.class);
		digester.addCallMethod("*/visualPseudocode", "setDefaultColor", 1);
		digester.addCallParam("*/visualPseudocode", 0, "defaultColor");
		digester.addCallMethod("*/visualPseudocode", "setDefaultFont", 1);
		digester.addCallParam("*/visualPseudocode", 0, "defaultFont");
		digester.addCallMethod("*/visualPseudocode", "setDefaultShapeHeight", 1);
		digester.addCallParam("*/visualPseudocode", 0, "defaultShapeHeight");
		digester.addCallMethod("*/visualPseudocode", "setDefaultShapeWidth", 1);
		digester.addCallParam("*/visualPseudocode", 0, "defaultShapeWidth");
		digester.addCallMethod("*/visualPseudocode", "setOriginX", 1);
		digester.addCallParam("*/visualPseudocode", 0, "originX");
		digester.addCallMethod("*/visualPseudocode", "setOriginY", 1);
		digester.addCallParam("*/visualPseudocode", 0, "originY");
		digester.addCallMethod("*/visualPseudocode/visualMalleableElement", "setElementColor", 2);
		digester.addCallParam("*/visualPseudocode/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualPseudocode/visualMalleableElement", 1, "color");
		digester.addCallMethod("*/visualPseudocode/visualMalleableElement", "setElementFont", 2);
		digester.addCallParam("*/visualPseudocode/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualPseudocode/visualMalleableElement", 1, "font");
		digester.addCallMethod("*/visualPseudocode/visualMalleableElement", "setElementShapeHeight", 2);
		digester.addCallParam("*/visualPseudocode/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualPseudocode/visualMalleableElement", 1, "shapeHeight");
		digester.addCallMethod("*/visualPseudocode/visualMalleableElement", "setElementShapeWidth", 2);
		digester.addCallParam("*/visualPseudocode/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualPseudocode/visualMalleableElement", 1, "shapeWidth");
	}
}