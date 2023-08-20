package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.VisualListBean;
import org.apache.commons.digester.Digester;

/*
 * The XML declaration of a visual list includes the element default color, font, shape,
 * and shape height and width, the origin coordinates (the origin is the bottom left
 * point of the list), and, for each element which has to be drawn differently from the
 * default way, its color, font, shape, and shape height and width (the element is identified by
 * means of its position).
 */
public class VisualListParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/visualList", VisualListBean.class);
		digester.addCallMethod("*/visualList", "setLoaderClassName", 1);
		digester.addCallParam("*/visualList", 0, "loaderClassName");
		digester.addCallMethod("*/visualList", "setDefaultColor", 1);
		digester.addCallParam("*/visualList", 0, "defaultColor");
		digester.addCallMethod("*/visualList", "setDefaultFont", 1);
		digester.addCallParam("*/visualList", 0, "defaultFont");
		digester.addCallMethod("*/visualList", "setDefaultShape", 1);
		digester.addCallParam("*/visualList", 0, "defaultShape");
		digester.addCallMethod("*/visualList", "setDefaultShapeHeight", 1);
		digester.addCallParam("*/visualList", 0, "defaultShapeHeight");
		digester.addCallMethod("*/visualList", "setDefaultShapeWidth", 1);
		digester.addCallParam("*/visualList", 0, "defaultShapeWidth");
		digester.addCallMethod("*/visualList", "setDefaultLineColor", 1);
		digester.addCallParam("*/visualList", 0, "defaultLineColor");
		digester.addCallMethod("*/visualList", "setDefaultLineType", 1);
		digester.addCallParam("*/visualList", 0, "defaultLineType");
		digester.addCallMethod("*/visualList", "setDefaultLineThickness", 1);
		digester.addCallParam("*/visualList", 0, "defaultLineThickness");
		digester.addCallMethod("*/visualList", "setOriginX", 1);
		digester.addCallParam("*/visualList", 0, "originX");
		digester.addCallMethod("*/visualList", "setOriginY", 1);
		digester.addCallParam("*/visualList", 0, "originY");
		digester.addCallMethod("*/visualList/visualLinkedElement", "setElementColor", 2);
		digester.addCallParam("*/visualList/visualLinkedElement", 0, "id");
		digester.addCallParam("*/visualList/visualLinkedElement", 1, "color");
		digester.addCallMethod("*/visualList/visualLinkedElement", "setElementFont", 2);
		digester.addCallParam("*/visualList/visualLinkedElement", 0, "id");
		digester.addCallParam("*/visualList/visualLinkedElement", 1, "font");
		digester.addCallMethod("*/visualList/visualLinkedElement", "setElementShape", 2);
		digester.addCallParam("*/visualList/visualLinkedElement", 0, "id");
		digester.addCallParam("*/visualList/visualLinkedElement", 1, "shape");
		digester.addCallMethod("*/visualList/visualLinkedElement", "setElementShapeHeight", 2);
		digester.addCallParam("*/visualList/visualLinkedElement", 0, "id");
		digester.addCallParam("*/visualList/visualLinkedElement", 1, "shapeHeight");
		digester.addCallMethod("*/visualList/visualLinkedElement", "setElementShapeWidth", 2);
		digester.addCallParam("*/visualList/visualLinkedElement", 0, "id");
		digester.addCallParam("*/visualList/visualLinkedElement", 1, "shapeWidth");
		digester.addCallMethod("*/visualList/visualLink", "setLineColor", 2);
		digester.addCallParam("*/visualList/visualLink", 0, "id");
		digester.addCallParam("*/visualList/visualLink", 1, "lineColor");
		digester.addCallMethod("*/visualList/visualLink", "setLineType", 2);
		digester.addCallParam("*/visualList/visualLink", 0, "id");
		digester.addCallParam("*/visualList/visualLink", 1, "lineType");
		digester.addCallMethod("*/visualList/visualLink", "setLineThickness", 2);
		digester.addCallParam("*/visualList/visualLink", 0, "id");
		digester.addCallParam("*/visualList/visualLink", 1, "lineThickness");
	}
}