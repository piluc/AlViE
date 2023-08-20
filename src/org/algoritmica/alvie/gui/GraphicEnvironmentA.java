package org.algoritmica.alvie.gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

import org.algoritmica.alvie.desktop.Main;

public abstract class GraphicEnvironmentA {

	String dataStructurePackagePrefix = "org.algoritmica.alvie.datastructure.";

	String loaderPackagePrefix = "org.algoritmica.alvie.loader.";

	String drawerPackagePrefix = "org.algoritmica.alvie.drawer.";

	String parserPackagePrefix = "org.algoritmica.alvie.parser.";

	String visualDataStructurePackagePrefix = "org.algoritmica.alvie.visualdatastructure.";

	String visualDataStructurePrefix = "Visual";

	String graphicDrawerSuffix = "GraphicDrawer";

	String loaderSuffix = "Loader";

	String viewPanelClassName = "org.algoritmica.alvie.gui.ViewPanel";

	String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";

	int originX = Integer.parseInt(Main.config.getProperty("defaultVisualDataStructureOriginX"));

	int originY = Integer.parseInt(Main.config.getProperty("defaultVisualDataStructureOriginY"));

	int frameOriginX;

	int frameOriginY;

	String frameTitle;

	JTabbedPane tabbedPane = new JTabbedPane();

	JInternalFrame mainFrame;

	public void createMainFrame() {
		mainFrame = new JInternalFrame(frameTitle);
		mainFrame.setLocation(frameOriginX, frameOriginY);
		mainFrame.add(tabbedPane, BorderLayout.CENTER);
		setSize();
		mainFrame.setVisible(true);
		mainFrame.setResizable(true);
	}

	public ViewPanel getCurrentViewPanel() {
		if (tabbedPane == null || tabbedPane.getComponentCount() == 0)
			return null;
		else
			return (ViewPanel) tabbedPane.getSelectedComponent();
	}

	public JInternalFrame getMainFrame() {
		return mainFrame;
	}

	public int getSelectedTabIndex() {
		if (tabbedPane == null || tabbedPane.getComponentCount() == 0)
			return -1;
		else
			return tabbedPane.getSelectedIndex();
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public abstract void setSize();

	public boolean tabbedPaneIsEmpty() {
		return getSelectedTabIndex() == -1;
	}
}
