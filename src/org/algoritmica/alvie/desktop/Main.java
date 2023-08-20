package org.algoritmica.alvie.desktop;

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.gui.AlViE;

@SuppressWarnings("serial")
public class Main extends JFrame {
	private int errorId = 600000;
	private ResourceBundle localResourceBundle;
	private final ImageIcon icon;
	private AlViE alvie;
	public static Properties config;
	public static final String algDirPath = "algorithms/";

	public Main() {
		super("Algorithm execution and visualization desktop");
		setConfig();
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e0) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e1) {
				try {
					UIManager.setLookAndFeel("apple.laf.AquaLookAndFeel");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, localResourceBundle.getString("errorMessage001"), localResourceBundle.getString("title005") + "(code: " + (errorId + 1) + ")", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		ClassLoader cl = this.getClass().getClassLoader();
		icon = new ImageIcon(cl.getResource("img/alvies.jpg"));
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDesktopPane desktop = new JDesktopPane() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		alvie = new AlViE(this);
		alvie.getAlgorithmPlayer().getMainFrame().setIconifiable(true);
		desktop.add(alvie.getAlgorithmPlayer().getMainFrame());
		setContentPane(desktop);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int verticalInsets = 10;
		setBounds(Integer.parseInt(config.getProperty("desktopPaneOriginX")), Integer.parseInt(config.getProperty("desktopPaneOriginY")), alvie.getAlgorithmPlayer().getMainFrame().getWidth() + 2 * Integer.parseInt(config.getProperty("alvieFrameOriginX")), 2 * verticalInsets + alvie.getAlgorithmPlayer().getMainFrame().getHeight() + 2 * Integer.parseInt(config.getProperty("alvieFrameOriginY")));
		setVisible(true);
		alvie.setFocus();
	}
	
	public void setInternalFramesVisible(boolean f) {
		alvie.getAlgorithmPlayer().getMainFrame().setVisible(f);
	}

	private void setConfig() {
		config = new Properties();
		config.setProperty("algorithmPlayerButtonSize", "20");
		config.setProperty("alvieFrameHeight", "700");
		config.setProperty("alvieFrameOriginX", "30");
		config.setProperty("alvieFrameOriginY", "30");
		config.setProperty("alvieFrameWidth", "1000");
		config.setProperty("arcArrowDistanceFromNode", "2");
		config.setProperty("arcArrowLength", "10");
		config.setProperty("arcLabelDistanceFromNode", "30");
		config.setProperty("arcLabelFont", "Courier-PLAIN-14");
		config.setProperty("borderInformationHorizontalSeparator", "1");
		config.setProperty("borderInformationVerticalSeparator", "1");
		config.setProperty("dataStructureMessageRatioNumerator", "9");
		config.setProperty("dataStructureMessageRatioDenominator", "10");
		config.setProperty("defaultArcLabelFont", "Courier-PLAIN-14");
		config.setProperty("defaultMultiPaneViewEnabled", "false");
		config.setProperty("defaultPseudocodeFont", "Courier-PLAIN-20");
		config.setProperty("defaultShapeHeight", "30");
		config.setProperty("defaultShapeWidth", "30");
		config.setProperty("defaultTreeNodeHorizontalDistance", "10");
		config.setProperty("defaultTreeNodeVerticalDistance", "10");
		config.setProperty("defaultVisualDataStructureColor", "FFFFFF");
		config.setProperty("defaultVisualDataStructureFont", "Courier-PLAIN-16");
		config.setProperty("defaultVisualDataStructureLineColor", "000000");
		config.setProperty("defaultVisualDataStructureLineThickness", "0.5");
		config.setProperty("defaultVisualDataStructureLineType", "CONTINUE");
		config.setProperty("defaultVisualDataStructureOriginX", "50");
		config.setProperty("defaultVisualDataStructureOriginY", "50");
		config.setProperty("defaultVisualDataStructureShape", "Rectangular");
		config.setProperty("desktopPaneOriginX", "50");
		config.setProperty("desktopPaneOriginY", "50");
		config.setProperty("dialogRowNumberCharacters", "40");
		config.setProperty("interListElementSpace", "25");
		config.setProperty("language", "en");
		config.setProperty("messageFont", "Courier-PLAIN-18");
		config.setProperty("messageRowNumber", "3");
		config.setProperty("shapeHeight", "30");
		config.setProperty("shapeWidth", "30");
		config.setProperty("treeNodeHorizontalDistance", "10");
		config.setProperty("treeNodeVerticalDistance", "40");
		config.setProperty("viewPanelBackgroundColor", "FFFFFF");
		File configFile = new File(System.getProperty("user.home"), ".alviesettings");
		try {
			if (configFile.exists()) {
				config.load(new FileInputStream(configFile));
			} else {
				config.store(new FileOutputStream(configFile), "AlViE Configuration");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Reading or writing the ALViE setting file generated and error: using English as default locale", "Warning: code " + (errorId + 2), JOptionPane.ERROR_MESSAGE);
		}
		localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();
		File algDir = new File(algDirPath);
		if (!algDir.exists()) {
			algDir.mkdir();
		}
	}

	public static void storeConfig() {
		try {
			File configFile = new File(System.getProperty("user.home"), ".alviesettings");
			config.store(new FileOutputStream(configFile), "AlViE Configuration");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Writing the ALViE setting file generated and error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
