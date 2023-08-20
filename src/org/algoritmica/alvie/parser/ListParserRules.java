package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.ListBean;
import org.apache.commons.digester.Digester;

/*
 * The XML declaration of a list includes the list size, the type of the
 * information stored in the list elements, and, for each element, a textual 
 * representation of the information it contains (the element is identified by
 * means of its position).
 */
public class ListParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/list", ListBean.class);
		digester.addCallMethod("*/list", "setSize", 1);
		digester.addCallParam("*/list", 0, "size");
		digester.addCallMethod("*/list", "setType", 1);
		digester.addCallParam("*/list", 0, "type");
		digester.addCallMethod("*/list", "setLoaderClassName", 1);
		digester.addCallParam("*/list", 0, "loaderClassName");
		digester.addCallMethod("*/list/element", "setElementValue", 2);
		digester.addCallParam("*/list/element", 0, "id");
		digester.addCallParam("*/list/element", 1, "value");
	}
}