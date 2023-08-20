package org.algoritmica.alvie.drawer;

import java.io.OutputStream;
import java.io.PrintWriter;

public class XMLDrawer {

	protected PrintWriter printWriter;

	public XMLDrawer(OutputStream outputStream) {
		printWriter = new PrintWriter(outputStream, true);
	}

	public void drawMessage(String message) {
		printWriter.println("<message content=\"" + message + "\">");
		printWriter.println("</message>");
	}

	public void endStep() {
		printWriter.println("</step>");
	}

	public void endStructure() {
		printWriter.println("</structure>");
	}

	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	public void startStep(int stepNumber) {
		printWriter.println("<step number=\"" + stepNumber + "\">");
	}

	public void startStructure(String name, String type, String visualClassName, String className, String drawerClassName) {
		printWriter.println("<structure name=\"" + name + "\" type=\"" + type + "\">");
	}

	public void startXML() {
		printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	}

	public void write(String string) {
		printWriter.println(string);
	}

}
