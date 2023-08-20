package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.VisualQueueBean;
import org.apache.commons.digester.Digester;

/*
 * The XML declaration of a visual queue includes the element default color, font, 
 * and shape height and width, the origin coordinates (the origin is the bottom left
 * point of the head of the queue), and, for each element which has to be drawn differently from the
 * default way, its color, font, and shape height and width (the element is identified by
 * means of its position).
 */
public class VisualQueueParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/visualQueue", VisualQueueBean.class);
		digester.addCallMethod("*/visualQueue", "setLoaderClassName", 1);
		digester.addCallParam("*/visualQueue", 0, "loaderClassName");
		digester.addCallMethod("*/visualQueue", "setDefaultColor", 1);
		digester.addCallParam("*/visualQueue", 0, "defaultColor");
		digester.addCallMethod("*/visualQueue", "setDefaultFont", 1);
		digester.addCallParam("*/visualQueue", 0, "defaultFont");
		digester.addCallMethod("*/visualQueue", "setDefaultShapeHeight", 1);
		digester.addCallParam("*/visualQueue", 0, "defaultShapeHeight");
		digester.addCallMethod("*/visualQueue", "setDefaultShapeWidth", 1);
		digester.addCallParam("*/visualQueue", 0, "defaultShapeWidth");
		digester.addCallMethod("*/visualQueue", "setOriginX", 1);
		digester.addCallParam("*/visualQueue", 0, "originX");
		digester.addCallMethod("*/visualQueue", "setOriginY", 1);
		digester.addCallParam("*/visualQueue", 0, "originY");
		digester.addCallMethod("*/visualQueue/visualMalleableElement", "setElementColor", 2);
		digester.addCallParam("*/visualQueue/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualQueue/visualMalleableElement", 1, "color");
		digester.addCallMethod("*/visualQueue/visualMalleableElement", "setElementFont", 2);
		digester.addCallParam("*/visualQueue/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualQueue/visualMalleableElement", 1, "font");
		digester.addCallMethod("*/visualQueue/visualMalleableElement", "setElementShapeHeight", 2);
		digester.addCallParam("*/visualQueue/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualQueue/visualMalleableElement", 1, "shapeHeight");
		digester.addCallMethod("*/visualQueue/visualMalleableElement", "setElementShapeWidth", 2);
		digester.addCallParam("*/visualQueue/visualMalleableElement", 0, "id");
		digester.addCallParam("*/visualQueue/visualMalleableElement", 1, "shapeWidth");
	}
}