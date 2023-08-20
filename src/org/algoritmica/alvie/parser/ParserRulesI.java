package org.algoritmica.alvie.parser;

import org.apache.commons.digester.Digester;

/*
 * All (visual) data structure XML parsers have to implement this interface, that is, they
 * have to implement a method declaring all the rules necessary in order to correctly
 * parse the XML file.
 */
public interface ParserRulesI {
	public void addParserRules(Digester digester);
}
