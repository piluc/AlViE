package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.VisualBinaryTreeBean;
import org.apache.commons.digester.Digester;

/*
 * The XML declaration of a visual binary tree includes the element default color, font, shape,
 * and shape height and width, the origin coordinates (the origin is the bottom left
 * point of the binary tree), and, for each element which has to be drawn differently from the
 * default way, its color, font, shape, and shape height and width (the element is identified by
 * means of its id).
 */
public class VisualBinaryTreeParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/visualBinaryTree", VisualBinaryTreeBean.class);
		digester.addCallMethod("*/visualBinaryTree", "setLoaderClassName", 1);
		digester.addCallParam("*/visualBinaryTree", 0, "loaderClassName");
		digester.addCallMethod("*/visualBinaryTree", "setDefaultColor", 1);
		digester.addCallParam("*/visualBinaryTree", 0, "defaultColor");
		digester.addCallMethod("*/visualBinaryTree", "setDefaultFont", 1);
		digester.addCallParam("*/visualBinaryTree", 0, "defaultFont");
		digester.addCallMethod("*/visualBinaryTree", "setDefaultShape", 1);
		digester.addCallParam("*/visualBinaryTree", 0, "defaultShape");
		digester.addCallMethod("*/visualBinaryTree", "setDefaultShapeHeight", 1);
		digester.addCallParam("*/visualBinaryTree", 0, "defaultShapeHeight");
		digester.addCallMethod("*/visualBinaryTree", "setDefaultShapeWidth", 1);
		digester.addCallParam("*/visualBinaryTree", 0, "defaultShapeWidth");
		digester.addCallMethod("*/visualBinaryTree", "setDefaultLineColor", 1);
		digester.addCallParam("*/visualBinaryTree", 0, "defaultLineColor");
		digester.addCallMethod("*/visualBinaryTree", "setDefaultLineType", 1);
		digester.addCallParam("*/visualBinaryTree", 0, "defaultLineType");
		digester.addCallMethod("*/visualBinaryTree", "setDefaultLineThickness", 1);
		digester.addCallParam("*/visualBinaryTree", 0, "defaultLineThickness");
		digester.addCallMethod("*/visualBinaryTree", "setOriginX", 1);
		digester.addCallParam("*/visualBinaryTree", 0, "originX");
		digester.addCallMethod("*/visualBinaryTree", "setOriginY", 1);
		digester.addCallParam("*/visualBinaryTree", 0, "originY");
		digester.addCallMethod("*/visualBinaryTree/visualLinkedElement", "setElementColor", 2);
		digester.addCallParam("*/visualBinaryTree/visualLinkedElement", 0, "id");
		digester.addCallParam("*/visualBinaryTree/visualLinkedElement", 1, "color");
		digester.addCallMethod("*/visualBinaryTree/visualLinkedElement", "setElementFont", 2);
		digester.addCallParam("*/visualBinaryTree/visualLinkedElement", 0, "id");
		digester.addCallParam("*/visualBinaryTree/visualLinkedElement", 1, "font");
		digester.addCallMethod("*/visualBinaryTree/visualLinkedElement", "setElementShape", 2);
		digester.addCallParam("*/visualBinaryTree/visualLinkedElement", 0, "id");
		digester.addCallParam("*/visualBinaryTree/visualLinkedElement", 1, "shape");
		digester.addCallMethod("*/visualBinaryTree/visualLinkedElement", "setElementShapeHeight", 2);
		digester.addCallParam("*/visualBinaryTree/visualLinkedElement", 0, "id");
		digester.addCallParam("*/visualBinaryTree/visualLinkedElement", 1, "shapeHeight");
		digester.addCallMethod("*/visualBinaryTree/visualLinkedElement", "setElementShapeWidth", 2);
		digester.addCallParam("*/visualBinaryTree/visualLinkedElement", 0, "id");
		digester.addCallParam("*/visualBinaryTree/visualLinkedElement", 1, "shapeWidth");
		digester.addCallMethod("*/visualBinaryTree/visualLink", "setLineColor", 2);
		digester.addCallParam("*/visualBinaryTree/visualLink", 0, "id");
		digester.addCallParam("*/visualBinaryTree/visualLink", 1, "lineColor");
		digester.addCallMethod("*/visualBinaryTree/visualLink", "setLineType", 2);
		digester.addCallParam("*/visualBinaryTree/visualLink", 0, "id");
		digester.addCallParam("*/visualBinaryTree/visualLink", 1, "lineType");
		digester.addCallMethod("*/visualBinaryTree/visualLink", "setLineThickness", 2);
		digester.addCallParam("*/visualBinaryTree/visualLink", 0, "id");
		digester.addCallParam("*/visualBinaryTree/visualLink", 1, "lineThickness");
	}
}