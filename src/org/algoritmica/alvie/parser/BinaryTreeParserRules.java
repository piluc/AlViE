package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.BinaryTreeBean;
import org.apache.commons.digester.Digester;

/*
 * The XML declaration of a binary tree includes the binary tree size, the type of the
 * information stored in the binary tree elements, and, for each element, a textual 
 * representation of the information it contains (the element is identified by
 * means of its position).
 */
public class BinaryTreeParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/binaryTree", BinaryTreeBean.class);
		digester.addCallMethod("*/binaryTree", "setSize", 1);
		digester.addCallParam("*/binaryTree", 0, "size");
		digester.addCallMethod("*/binaryTree", "setType", 1);
		digester.addCallParam("*/binaryTree", 0, "type");
		digester.addCallMethod("*/binaryTree", "setLoaderClassName", 1);
		digester.addCallParam("*/binaryTree", 0, "loaderClassName");
		digester.addCallMethod("*/binaryTree/element", "setElementValue", 2);
		digester.addCallParam("*/binaryTree/element", 0, "id");
		digester.addCallParam("*/binaryTree/element", 1, "value");
	}
}