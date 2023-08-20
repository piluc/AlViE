package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.ArrayBean;
import org.apache.commons.digester.Digester;

/*
 * The XML declaration of an array includes the array size, the type of the
 * information stored in the array elements, and, for each element, a textual 
 * representation of the information it contains (the element is identified by
 * means of its position).
 */
public class ArrayParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/array", ArrayBean.class);
		digester.addCallMethod("*/array", "setSize", 1);
		digester.addCallParam("*/array", 0, "size");
		digester.addCallMethod("*/array", "setType", 1);
		digester.addCallParam("*/array", 0, "type");
		digester.addCallMethod("*/array", "setLoaderClassName", 1);
		digester.addCallParam("*/array", 0, "loaderClassName");
		digester.addCallMethod("*/array/element", "setElementValue", 2);
		digester.addCallParam("*/array/element", 0, "id");
		digester.addCallParam("*/array/element", 1, "value");
	}
}