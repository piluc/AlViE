package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.PseudocodeBean;
import org.apache.commons.digester.Digester;

/*
 * The XML declaration of a pseudo-code includes the number of code lines, and, 
 * for each code-line, its value (the code line is identified by
 * means of its position).
 */
public class PseudocodeParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/pseudocode", PseudocodeBean.class);
		digester.addCallMethod("*/pseudocode", "setSize", 1);
		digester.addCallParam("*/pseudocode", 0, "size");
		digester.addCallMethod("*/pseudocode/element", "setElementValue", 2);
		digester.addCallParam("*/pseudocode/element", 0, "id");
		digester.addCallParam("*/pseudocode/element", 1, "value");
	}
}