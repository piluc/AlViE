package org.algoritmica.alvie.parser;

import org.algoritmica.alvie.bean.MessageBean;
import org.apache.commons.digester.Digester;

public class MessageParserRules implements ParserRulesI {
	public void addParserRules(Digester digester) {
		digester.addObjectCreate("*/message", MessageBean.class);
		digester.addCallMethod("*/message", "setContent", 1);
		digester.addCallParam("*/message", 0, "content");
	}
}