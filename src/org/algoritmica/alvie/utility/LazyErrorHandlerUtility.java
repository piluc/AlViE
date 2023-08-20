package org.algoritmica.alvie.utility;

import org.xml.sax.SAXParseException;

public class LazyErrorHandlerUtility implements org.xml.sax.ErrorHandler {
	public void warning(SAXParseException ex) throws SAXParseException {
		throw ex;
	}

	public void error(SAXParseException ex) throws SAXParseException {
		throw ex;
	}

	public void fatalError(SAXParseException ex) throws SAXParseException {
		throw ex;
	}
}