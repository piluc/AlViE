package org.algoritmica.alvie.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.algoritmica.alvie.bean.StepBean;
import org.algoritmica.alvie.bean.StructureBean;
import org.algoritmica.alvie.bean.VisualStructureBean;
import org.algoritmica.alvie.bean.XMLStructureBean;
import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.DataStructureI;
import org.algoritmica.alvie.desktop.Main;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.loader.LoaderI;
import org.algoritmica.alvie.loader.StructureLoader;
import org.algoritmica.alvie.loader.VisualLoaderI;
import org.algoritmica.alvie.utility.LazyErrorHandlerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.visualdatastructure.VisualDataStructureI;
import org.apache.commons.digester.Digester;

public class AlgorithmPlayer extends GraphicEnvironmentA {
	private ResourceBundle localResourceBundle = Configuration.getInstance()
			.getLocalConfigurationBundle();

	private final int errorId = 601000;

	private int maximumStepNumber = -1;

	private boolean[] stepHasBeenParsed;

	private String[] stepXMLCode;

	private String algorithmName;

	private StepBean[] stepBean;

	private boolean[] bookmarks;

	private int maxBookmarkPosition = -1;

	private int minBookmarkPosition = -1;

	private int stepNumber = -1;

	private int stepTotal = 0;

	private ViewPanel[] viewPanel;

	private boolean isMultiPaneView = Boolean.parseBoolean(Main.config
			.getProperty("defaultMultiPaneViewEnabled"));

	private int structureNumber = 0;

	private XMLStructureBean[] xmlStructureBean;

	private VisualStructureBean[] visualStructureBean;

	public AlgorithmPlayer() {
		frameOriginX = Integer.parseInt(Main.config
				.getProperty("alvieFrameOriginX"));
		frameOriginY = Integer.parseInt(Main.config
				.getProperty("alvieFrameOriginY"));
		frameTitle = Configuration.getInstance().getLocalConfigurationBundle()
				.getString("title030");
	}

	public void createStepBean(int n) {
		stepTotal = n;
		stepBean = new StepBean[n];
		bookmarks = new boolean[n];
		for (int i = 0; i < n; i++)
			bookmarks[i] = false;
	}

	private void createTabbedPane() {
		Collection<XMLStructureBean> xmlStructureBeanCollection = stepBean[stepNumber]
				.getXMLStructureBeanCollection();
		int structureNumber = xmlStructureBeanCollection.size();
		int selectedTab = 0;
		if (stepHasBeenParsed[stepNumber] || (stepNumber > 0)) {
			selectedTab = tabbedPane.getSelectedIndex();
		}
		Iterator<XMLStructureBean> xmlStructureBeanIterator = xmlStructureBeanCollection
				.iterator();
		ViewPanel[] oldViewPanel = null;
		MouseListener mouseListener = tabbedPane.getMouseListeners()[tabbedPane
				.getMouseListeners().length - 1];
		if (tabbedPane != null) {
			oldViewPanel = new ViewPanel[tabbedPane.getComponentCount()];
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				oldViewPanel[i] = (ViewPanel) tabbedPane.getComponentAt(i);
			}
		}
		emptyTabbedPane();
		viewPanel = new ViewPanel[structureNumber];
		String dataStructurePanelTitle = Configuration.getInstance()
				.getLocalConfigurationBundle().getString("title028");
		String messagePanelTitle = Configuration.getInstance()
				.getLocalConfigurationBundle().getString("title029");
		if (isMultiPaneView) {
			for (int i = 0; i < structureNumber; i++) {
				if (oldViewPanel != null && i < oldViewPanel.length) {
					viewPanel[i] = oldViewPanel[i];
					viewPanel[i].cleanDrawingPanel();
					viewPanel[i].cleanMessagePanel();
				} else {
					viewPanel[i] = new ViewPanel(tabbedPane.getSize(),
							dataStructurePanelTitle, messagePanelTitle);
					viewPanel[i].setBackground(new Color(Integer
							.parseInt(Main.config
									.getProperty("viewPanelBackgroundColor"),
									16)));
				}
				tabbedPane.addTab(xmlStructureBeanIterator.next().getName(),
						viewPanel[i]);
				viewPanel[i].setMouseListener(mouseListener);
			}
		} else {
			if (oldViewPanel != null && oldViewPanel.length > 0) {
				viewPanel[0] = oldViewPanel[0];
				viewPanel[0].cleanDrawingPanel();
				viewPanel[0].cleanMessagePanel();
			} else {
				viewPanel[0] = new ViewPanel(tabbedPane.getSize(),
						dataStructurePanelTitle, messagePanelTitle);
				viewPanel[0].setBackground(new Color(Integer
						.parseInt(Main.config
								.getProperty("viewPanelBackgroundColor"), 16)));
			}
			tabbedPane.addTab(algorithmName, viewPanel[0]);
			viewPanel[0].setMouseListener(mouseListener);
			for (int i = 1; i < structureNumber; i++) {
				viewPanel[i] = viewPanel[0];
			}
		}
		selectedTab = (isMultiPaneView && (selectedTab < structureNumber)) ? selectedTab
				: 0;
		tabbedPane.setSelectedIndex(selectedTab);
		mainFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		mainFrame.setVisible(true);
	}

	public void emptyTabbedPane() {
		tabbedPane.removeAll();
	}

	public int getBookmarkPosition(String title) {
		return Integer.parseInt(title.substring(title.lastIndexOf('(') + 1,
				title.lastIndexOf(')')));
	}

	public String getBookmarkTitle(int position) {
		return algorithmName + " (" + position + ")";
	}

	public int getMaximumBookmark() {
		return maxBookmarkPosition;
	}

	public int getMaximumStepNumber() {
		return maximumStepNumber;
	}

	public int getMinimumBookmark() {
		return minBookmarkPosition;
	}

	public StepBean[] getStepBean() {
		return stepBean;
	}

	public int getStepNumber() {
		return stepNumber;
	}

	public String[] getStructureNames() {
		String[] toret = new String[structureNumber];
		for (int i = 0; i < structureNumber; i++)
			toret[i] = xmlStructureBean[i].getName();
		return toret;
	}

	public int getStructureNumber() {
		return structureNumber;
	}

	public VisualStructureBean getVisualStructureBean(int index) {
		if (xmlStructureBean != null && index >= 0 && index < structureNumber)
			return visualStructureBean[index];
		else
			return null;
	}

	public XMLStructureBean getXMLStructureBean(int index) {
		if (xmlStructureBean != null && index >= 0 && index < structureNumber)
			return xmlStructureBean[index];
		else
			return null;
	}

	public boolean isBookmarked(int position) {
		boolean toret = false;
		if (bookmarks != null && position >= 0 && position < stepTotal) {
			toret = bookmarks[position];
		}
		return toret;
	}

	public boolean isMultiPaneView() {
		return isMultiPaneView;
	}

	private StepBean parseStep(String xmlStep) {
		Digester digester = new Digester();
		digester.setErrorHandler(new LazyErrorHandlerUtility());
		digester.addObjectCreate("step", StepBean.class);
		digester.addCallMethod("*/step", "setNumber", 1);
		digester.addCallParam("*/step", 0, "number");
		StepBean stepBean = null;
		try {
			StringReader stringReader = new StringReader(xmlStep);
			stepBean = (StepBean) digester.parse(stringReader);
		} catch (Exception e) {
			MessageUtility.errorMessage(localResourceBundle
					.getString("errorMessage024")
					+ " " + e.getMessage(), localResourceBundle
					.getString("title010"), errorId + 4);
		}
		int beginStructureIndex, endstructureIndex;
		beginStructureIndex = xmlStep.indexOf("<structure");
		while (beginStructureIndex >= 0) {
			endstructureIndex = xmlStep.indexOf("</structure>",
					beginStructureIndex)
					+ "</structure>".length();
			stepBean.addXMLStructureBean(parseStructure(xmlStep.substring(
					beginStructureIndex, endstructureIndex)));
			beginStructureIndex = xmlStep.indexOf("<structure",
					endstructureIndex);
		}
		return stepBean;
	}

	private XMLStructureBean parseStructure(String xmlStructure) {
		StructureLoader structureLoader = new StructureLoader();
		String structureType = structureLoader
				.getStructureType(new StringReader(xmlHeader + xmlStructure));
		return structureLoader.parseStructure(new StringReader(xmlHeader
				+ xmlStructure), structureType);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void playCurrentStep() {
		if (!stepHasBeenParsed[stepNumber]) {
			StepBean stepBean = parseStep(stepXMLCode[stepNumber]);
			setStepBean(stepNumber, stepBean);
		}
		createTabbedPane();
		stepHasBeenParsed[stepNumber] = true;
		structureNumber = stepBean[stepNumber].getXMLStructureBeanCollection()
				.size();
		String[] loaderClassName = new String[structureNumber];
		String[] visualLoaderClassName = new String[structureNumber];
		String[] structureName = new String[structureNumber];
		String[] visualStructure = new String[structureNumber];
		String[] visualStructureName = new String[structureNumber];
		String[] className = new String[structureNumber];
		String[] drawerClassName = new String[structureNumber];
		StructureBean[] structureBean = new StructureBean[structureNumber];
		visualStructureBean = new VisualStructureBean[structureNumber];
		xmlStructureBean = new XMLStructureBean[structureNumber];
		int k = 0;
		for (XMLStructureBean xmlsb : stepBean[stepNumber]
				.getXMLStructureBeanCollection()) {
			xmlStructureBean[k] = xmlsb;
			structureBean[k] = xmlsb.getStructureBean();
			structureName[k] = xmlsb.getName();
			className[k] = xmlsb.getType();
			visualStructureBean[k] = xmlsb.getVisualStructureBean();
			loaderClassName[k] = xmlsb.getName() + "Loader";
			visualLoaderClassName[k] = "Visual" + xmlsb.getName() + "Loader";
			visualStructure[k] = "Visual" + xmlsb.getType();
			visualStructureName[k] = "Visual" + xmlsb.getType();
			drawerClassName[k] = xmlsb.getType() + "GraphicDrawer";
			k++;
		}
		k = 0;
		try {
			for (int r = 0; r < structureNumber; r++) {
				String type = xmlStructureBean[r].getType();
				LoaderI dataStructureLoader = (LoaderI) Class.forName(
						loaderPackagePrefix + type + loaderSuffix)
						.getDeclaredConstructor().newInstance();
				DataStructureI<InformationI> dataStructure = dataStructureLoader
						.load(xmlStructureBean[r].getStructureBean());
				VisualLoaderI visualDataStructureLoader = (VisualLoaderI) Class
						.forName(
								loaderPackagePrefix + visualDataStructurePrefix
										+ type + loaderSuffix).getDeclaredConstructor().newInstance();
				DrawerA graphicDrawer = (DrawerA) Class.forName(
						drawerPackagePrefix + type + graphicDrawerSuffix)
						.getConstructor(Class.forName("java.lang.String"),
								Class.forName(viewPanelClassName)).newInstance(
								structureName[r], viewPanel[r]);
				visualDataStructureLoader.loadDrawer(visualStructureBean[r],
						graphicDrawer);
				VisualDataStructureI<DataStructureI<InformationI>, VisualStructureBean> visualDataStructure = (VisualDataStructureI<DataStructureI<InformationI>, VisualStructureBean>) Class
						.forName(
								visualDataStructurePackagePrefix
										+ visualDataStructurePrefix + type)
						.getDeclaredConstructor().newInstance();
				visualDataStructure.setGraphicDrawer(graphicDrawer);
				graphicDrawer.setIsMultiPane(isMultiPaneView);
				visualDataStructure.graphicDraw(dataStructure,
						visualStructureBean[r]);
				if (xmlStructureBean[r].getMessageBean() != null) {
					visualDataStructure.graphicMessage(xmlStructureBean[r]
							.getMessageBean().getContent());
				}
			}
		} catch (Exception e) {
			MessageUtility.errorMessage(localResourceBundle
					.getString("errorMessage019")
					+ " " + e.getMessage(), localResourceBundle
					.getString("title007"), errorId + 3);
		}
	}

	public void playStep(int position) {
		if (position >= 0 && position < stepTotal) {
			setStepNumber(position);
			playCurrentStep();
		}
	}

	public void readSteps(File file) {
		Collection<String> stepCollection = new ArrayList<String>();
		int k = 0;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String line = bufferedReader.readLine();
			if (line == null) {
				maximumStepNumber = 0;
				bufferedReader.close();
				return;
			}
			line = bufferedReader.readLine();
			if (line.indexOf("algorithm") < 0) {
				MessageUtility.errorMessage(localResourceBundle
						.getString("errorMessage022"), localResourceBundle
						.getString("title010"), errorId + 1);
			} else {
				int algorithmNameBeginIndex = line.indexOf("=") + 2;
				int algorithmNameEndIndex = line.indexOf(">") - 1;
				algorithmName = line.substring(algorithmNameBeginIndex,
						algorithmNameEndIndex);
			}
			String xmlStep = xmlHeader;
			while (line != null) {
				if (line.contains("<step number=")) {
					k++;
					xmlStep = xmlStep + line + "\n";
					line = bufferedReader.readLine();
					while (!line.contains("/step")) {
						xmlStep = xmlStep + line + "\n";
						line = bufferedReader.readLine();
					}
					xmlStep = xmlStep + line + "\n";
					stepCollection.add(xmlStep);
					xmlStep = xmlHeader;
				}
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (Exception e) {
			MessageUtility.errorMessage(localResourceBundle
					.getString("errorMessage023")
					+ "\n" + e.getMessage(), localResourceBundle
					.getString("title038"), errorId + 2);
		}
		stepXMLCode = new String[k];
		stepHasBeenParsed = new boolean[k];
		maximumStepNumber = k - 1;
		stepNumber = 0;
		int i = 0;
		for (String s : stepCollection) {
			stepXMLCode[i] = s;
			stepHasBeenParsed[i] = false;
			i++;
		}
	}

	public void reset() {
		stepHasBeenParsed = null;
		stepXMLCode = null;
		stepBean = null;
		bookmarks = null;
		viewPanel = null;
		xmlStructureBean = null;
		visualStructureBean = null;
		maximumStepNumber = -1;
		stepNumber = -1;
		stepTotal = 0;
		maxBookmarkPosition = -1;
		minBookmarkPosition = -1;
		structureNumber = 0;
		System.gc();
	}

	public boolean setBookmark(int position) {
		boolean toret = false;
		if (toret = (bookmarks != null && position >= 0 && position < stepTotal && !bookmarks[position])) {
			bookmarks[position] = true;
			if (maxBookmarkPosition < position) {
				maxBookmarkPosition = position;
			}
			if (minBookmarkPosition < 0 || minBookmarkPosition > position) {
				minBookmarkPosition = position;
			}
		}
		return toret;
	}

	public void setFlash(boolean f) {
		viewPanel[0].setFlash(f);
	}

	public void setMultiPaneView(boolean b) {
		isMultiPaneView = b;
	}

	public void setSize() {
		int width = Integer
				.parseInt(Main.config.getProperty("alvieFrameWidth"));
		int height = Integer.parseInt(Main.config
				.getProperty("alvieFrameHeight"));
		mainFrame.setSize(width, height);
	}

	public void setStepBean(int i, StepBean stepBean) {
		this.stepBean[i] = stepBean;
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public boolean unsetBookmark(int position) {
		boolean toret = false;
		if (toret = (bookmarks != null && position >= 0 && position < stepTotal && bookmarks[position])) {
			bookmarks[position] = false;
			if (maxBookmarkPosition == position) {
				while (position >= 0 && !bookmarks[position]) {
					position--;
				}
				maxBookmarkPosition = position;
			}
			if (minBookmarkPosition == position) {
				while (position <= maximumStepNumber && !bookmarks[position]) {
					position++;
				}
				if (position <= maximumStepNumber) {
					minBookmarkPosition = position;
				} else {
					minBookmarkPosition = -1;
				}
			}
		}
		return toret;
	}

}
