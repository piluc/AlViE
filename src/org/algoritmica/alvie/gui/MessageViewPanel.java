package org.algoritmica.alvie.gui;

import java.awt.Font;

import javax.swing.JTextArea;

import org.algoritmica.alvie.desktop.Main;
import org.algoritmica.alvie.graphic.PositionedI;

@SuppressWarnings("serial")
public class MessageViewPanel extends JTextArea {

	public MessageViewPanel() {
		Font textAreaFont = Font.decode(Main.config.getProperty("messageFont"));
		setFont(textAreaFont);
		setEditable(false);
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	public void drawMessage(PositionedI message) {
		append(message.toString());
	}

	public void clean() {
		this.setText(null);
	}
}
