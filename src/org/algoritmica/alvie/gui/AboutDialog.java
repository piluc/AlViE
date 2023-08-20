package org.algoritmica.alvie.gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.algoritmica.alvie.config.Configuration;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
	private ResourceBundle localResourceBundle = Configuration.getInstance()
			.getLocalConfigurationBundle();

	public AboutDialog(JFrame parent) {
		super(parent, "", true);
		setTitle(localResourceBundle.getString("aboutTitle"));
		ClassLoader cl = parent.getClass().getClassLoader();
		Box b = Box.createVerticalBox();
		b.add(Box.createGlue());
		ImageIcon icon = new ImageIcon(new ImageIcon(cl
				.getResource("img/alvie.gif")).getImage().getScaledInstance(50,
				50, Image.SCALE_AREA_AVERAGING));
		b.add(new JLabel(localResourceBundle.getString("aboutText1"),
				icon, JLabel.CENTER));
		b.add(new JLabel(" "));
		b
				.add(new JLabel(localResourceBundle.getString("aboutText2")));
		b.add(new JLabel(localResourceBundle.getString("aboutText3")));
		b.add(new JLabel(localResourceBundle.getString("aboutText4")));
		b.add(new JLabel(localResourceBundle.getString("aboutText5")));
		b.add(new JLabel(localResourceBundle.getString("aboutText6")));
		b.add(new JLabel(localResourceBundle.getString("aboutText7")));
		b.add(new JLabel(localResourceBundle.getString("aboutText8")));
		b.add(new JLabel(localResourceBundle.getString("aboutText9")));
		b.add(new JLabel(localResourceBundle.getString("aboutText14")));
		b.add(new JLabel(localResourceBundle.getString("aboutText15")));
		b.add(new JLabel(localResourceBundle.getString("aboutText16")));
		b.add(new JLabel(localResourceBundle.getString("aboutText17")));
		b.add(Box.createGlue());
		getContentPane().add(b, "Center");

		JPanel p2 = new JPanel();
		JButton ok = new JButton("Ok");
		p2.add(ok);
		getContentPane().add(p2, "South");

		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});
	}
}
