package org.algoritmica.alvie.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.desktop.Main;
import org.algoritmica.alvie.utility.GraphicInputRequestsUtility;
import org.algoritmica.alvie.utility.MessageUtility;

@SuppressWarnings("serial")
public class AlViE extends JFrame {
	private class AlgorithmExecutor extends ProgressTask {
		private Object object;
		private String visFileName;
		Method executeMethod;

		public AlgorithmExecutor(Object object, String visFileName, Method executeMethod) {
			this.object = object;
			this.visFileName = visFileName;
			this.executeMethod = executeMethod;
		}

		public void execute() {
			try {
				executeMethod.invoke(object, new Object[] { visFileName });
				visualizationFile = new File(visFileName);
				algorithmPlayer.readSteps(visualizationFile);
			} catch (Exception e) {
				MessageUtility.errorMessage(localResourceBundle.getString("errorMessage021") + ". " + e.getCause(),
						localResourceBundle.getString("title036"), errorId + 1);
			}
		}
	}

	private class VisualizationLoader extends ProgressTask {
		public void execute() {
			algorithmPlayer.reset();
			algorithmPlayer.readSteps(visualizationFile);
			algorithmPlayer.createStepBean(algorithmPlayer.getMaximumStepNumber() + 1);
		}
	}

	private int errorId = 600000;

	private static final int ICON_SIZE = Integer.parseInt(Main.config.getProperty("algorithmPlayerButtonSize"));

	private ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private boolean isFirstVisualization;

	private boolean isMultiPaneView = Boolean.parseBoolean(Main.config.getProperty("defaultMultiPaneViewEnabled"));

	private Vector<String> bookmarks;

	private Action aboutAction, backwardAction, beginAction, configAction, endAction, forwardAction,
			globalConfigAction, singleTabAction, recordAction, multiTabAction, viewAction,
			setBookmarkAction, unsetBookmarkAction, zoomOutAction, resetZoomAction, zoomInAction,
			bookmarkBackwardAction, bookmarkForwardAction;

	private AlgorithmPlayer algorithmPlayer;

	private File visualizationFile;

	private JToolBar toolBar;

	private Main parentFrame;

	public AlViE(Main frame) {
		parentFrame = frame;
		Locale.setDefault(new Locale(Configuration.getInstance().getLanguage(),
				Configuration.getInstance().getLanguage().toUpperCase(), ""));
		createActions();
		toolBar = createToolBar();
		createAlgorithmPlayer();
		isFirstVisualization = true;
		buttonUpdate();
		toolBar.requestFocus();
	}

	private JMenuItem addBookmark(JMenu menu, String title) {
		JMenuItem toAdd = new JMenuItem(title);
		toAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				algorithmPlayer.playStep(algorithmPlayer.getBookmarkPosition(((JMenuItem) e.getSource()).getText()));
				bookmarkButtonsChangeRoutine();
			}
		});
		menu.add(toAdd);
		return toAdd;
	}

	private void aboutAction() {
		AboutDialog ad = new AboutDialog(parentFrame);
		ad.setBounds(parentFrame.getX() + parentFrame.getWidth() / 4, parentFrame.getY() + parentFrame.getHeight() / 4,
				parentFrame.getWidth() / 4, parentFrame.getHeight() / 3);
		ad.setResizable(false);
		ad.setVisible(true);
	}

	private void backwardAction() {
		int stepNumber = algorithmPlayer.getStepNumber();
		if (stepNumber > 0) {
			stepNumber--;
			algorithmPlayer.setStepNumber(stepNumber);
			algorithmPlayer.playCurrentStep();
		}
	}

	private void beginAction() {
		if (algorithmPlayer.getMaximumStepNumber() > 0) {
			algorithmPlayer.setStepNumber(0);
			algorithmPlayer.playCurrentStep();
			buttonUpdate();
		}
	}

	private void bookmarkBackwardAction() {
		int index = -1;
		for (String title : bookmarks) {
			int i = algorithmPlayer.getBookmarkPosition(title);
			if (i > index && i < algorithmPlayer.getStepNumber()) {
				index = i;
			}
		}
		if (index != -1) {
			algorithmPlayer.playStep(index);
		}
	}

	private void bookmarkButtonsChangeRoutine() {
		if (algorithmPlayer.getMaximumStepNumber() >= 0) {
			JTabbedPane tabSpace = algorithmPlayer.getTabbedPane();
			if (tabSpace.getTabCount() > 0) {
				if (algorithmPlayer.isBookmarked(algorithmPlayer.getStepNumber())) {
					unsetBookmarkAction.setEnabled(true);
					setBookmarkAction.setEnabled(false);
				} else {
					unsetBookmarkAction.setEnabled(false);
					setBookmarkAction.setEnabled(true);
				}
				if (algorithmPlayer.getStepNumber() < algorithmPlayer.getMaximumBookmark()) {
					bookmarkForwardAction.setEnabled(true);
				} else {
					bookmarkForwardAction.setEnabled(false);
				}
				if (algorithmPlayer.getMinimumBookmark() >= 0
						&& algorithmPlayer.getStepNumber() > algorithmPlayer.getMinimumBookmark()) {
					bookmarkBackwardAction.setEnabled(true);
				} else {
					bookmarkBackwardAction.setEnabled(false);
				}
				toolBar.repaint();
			}
		} else {
			unsetBookmarkAction.setEnabled(false);
			setBookmarkAction.setEnabled(false);
			bookmarkBackwardAction.setEnabled(false);
			bookmarkForwardAction.setEnabled(false);
		}
	}

	private void bookmarkForwardAction() {
		int index = algorithmPlayer.getMaximumStepNumber() + 1;
		for (String title : bookmarks) {
			int i = algorithmPlayer.getBookmarkPosition(title);
			if (i < index && i > algorithmPlayer.getStepNumber()) {
				index = i;
			}
		}
		if (index <= algorithmPlayer.getMaximumStepNumber()) {
			algorithmPlayer.playStep(index);
		}
	}

	private void buttonDisable() {
		aboutAction.setEnabled(false);
		recordAction.setEnabled(false);
		viewAction.setEnabled(false);
		configAction.setEnabled(false);
		backwardAction.setEnabled(false);
		beginAction.setEnabled(false);
		endAction.setEnabled(false);
		forwardAction.setEnabled(false);
		globalConfigAction.setEnabled(false);
		zoomInAction.setEnabled(false);
		resetZoomAction.setEnabled(false);
		zoomOutAction.setEnabled(false);
		unsetBookmarkAction.setEnabled(false);
		setBookmarkAction.setEnabled(false);
		bookmarkBackwardAction.setEnabled(false);
		bookmarkForwardAction.setEnabled(false);
		multiTabAction.setEnabled(false);
		singleTabAction.setEnabled(false);
	}

	private void buttonUpdate() {
		aboutAction.setEnabled(true);
		globalConfigAction.setEnabled(true);
		recordAction.setEnabled(true);
		viewAction.setEnabled(true);
		if (algorithmPlayer.getStepNumber() > 0) {
			backwardAction.setEnabled(true);
			beginAction.setEnabled(true);
		} else {
			backwardAction.setEnabled(false);
			beginAction.setEnabled(false);
		}
		if (algorithmPlayer.getStepNumber() < algorithmPlayer.getMaximumStepNumber()) {
			endAction.setEnabled(true);
			forwardAction.setEnabled(true);
		} else {
			endAction.setEnabled(false);
			forwardAction.setEnabled(false);
		}
		if (algorithmPlayer.getMaximumStepNumber() >= 0) {
			zoomInAction.setEnabled(true);
			resetZoomAction.setEnabled(true);
			zoomOutAction.setEnabled(true);
			configAction.setEnabled(true);
		} else {
			zoomInAction.setEnabled(false);
			resetZoomAction.setEnabled(false);
			zoomOutAction.setEnabled(false);
			configAction.setEnabled(false);
		}
		bookmarkButtonsChangeRoutine();
		modalityButtonsChangeRoutine();
	}

	private void configAction() {
		String filePathName = new File(visualizationFile.getParent()).getParent();
		String fileName = filePathName + "/config/visualization_" + Main.config.getProperty("language") + ".properties";
		Properties config = new Properties();
		File configFile = new File(fileName);
		try {
			if (configFile.exists()) {
				config.load(new FileInputStream(configFile));
				Enumeration<?> propertyNames = config.propertyNames();
				Vector<String> pn = new Vector<String>();
				while (propertyNames.hasMoreElements()) {
					pn.add((String) propertyNames.nextElement());
				}
				int n = pn.size();
				String[] propertyName = new String[n];
				for (int i = 0; i < n; i++) {
					propertyName[i] = pn.elementAt(i);
				}
				for (int i = 1; i < n; i++) {
					int j = i;
					while (j > 0 && propertyName[j - 1].compareTo(propertyName[j]) > 0) {
						String t = propertyName[j];
						propertyName[j] = propertyName[j - 1];
						propertyName[j - 1] = t;
						j = j - 1;
					}
				}
				String[] propertyValue = new String[n];
				for (int i = 0; i < n; i++) {
					propertyValue[i] = config.getProperty(propertyName[i]);
				}
				PropertyEditor propertyEditor = new PropertyEditor(localResourceBundle.getString("title057"),
						localResourceBundle.getString("label002"), propertyName, propertyValue, this);
				boolean exit = false;
				do {
					int returnVal = propertyEditor.showDialog();
					if (returnVal == PropertyEditor.APPROVE) {
						propertyValue = propertyEditor.getValues();
						for (int i = 0; i < propertyName.length; i++) {
							config.setProperty(propertyName[i], propertyValue[i]);
						}
						try {
							FileOutputStream fos = new FileOutputStream(configFile);
							config.store(fos, "Visualization configuration");
							fos.close();
						} catch (IOException e) {
							MessageUtility.errorMessage(
									localResourceBundle.getString("errorMessage032") + ". " + e.getCause(),
									localResourceBundle.getString("title056"), errorId + 1);
						}
						propertyEditor.dispose();
						exit = true;
					} else if (returnVal == PropertyEditor.CANCEL) {
						propertyEditor.dispose();
						return;
					}
				} while (!exit);
				MessageUtility.informationMessage(localResourceBundle.getString("message014"),
						localResourceBundle.getString("title059"));
			} else {
				MessageUtility.informationMessage(localResourceBundle.getString("message015"),
						localResourceBundle.getString("title060"));
			}
		} catch (IOException e) {
			MessageUtility.errorMessage(localResourceBundle.getString("errorMessage031") + ". " + e.getCause(),
					localResourceBundle.getString("title055"), errorId + 1);
		}
	}

	private void createActions() {
		ClassLoader cl = this.getClass().getClassLoader();
		aboutAction = new AbstractAction("About", new ImageIcon(new ImageIcon(cl.getResource("img/about.jpg"))
				.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				aboutAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		backwardAction = new AbstractAction("Forward", new ImageIcon(new ImageIcon(cl.getResource("img/left.jpg"))
				.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				backwardAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		beginAction = new AbstractAction("Begin", new ImageIcon(new ImageIcon(cl.getResource("img/begin.jpg"))
				.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				beginAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		bookmarkBackwardAction = new AbstractAction("Previous bookmark",
				new ImageIcon(new ImageIcon(cl.getResource("img/bookmarkbackward.jpg")).getImage()
						.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				bookmarkBackwardAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		bookmarkForwardAction = new AbstractAction("Next bookmark",
				new ImageIcon(new ImageIcon(cl.getResource("img/bookmarkforward.jpg")).getImage()
						.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				bookmarkForwardAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		configAction = new AbstractAction("Edit properties",
				new ImageIcon(new ImageIcon(cl.getResource("img/config.jpg")).getImage().getScaledInstance(ICON_SIZE,
						ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				configAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		endAction = new AbstractAction("End", new ImageIcon(new ImageIcon(cl.getResource("img/end.jpg")).getImage()
				.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				endAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		forwardAction = new AbstractAction("Forward", new ImageIcon(new ImageIcon(cl.getResource("img/right.jpg"))
				.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				forwardAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		globalConfigAction = new AbstractAction("Edit global properties",
				new ImageIcon(new ImageIcon(cl.getResource("img/globalconfig.jpg")).getImage()
						.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				globalConfigAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		multiTabAction = new AbstractAction("Multi-pane mode",
				new ImageIcon(new ImageIcon(cl.getResource("img/tabbed.jpg")).getImage().getScaledInstance(ICON_SIZE,
						ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				multiTabAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		multiTabAction.setEnabled(!isMultiPaneView);
		recordAction = new AbstractAction("Record", new ImageIcon(new ImageIcon(cl.getResource("img/record.jpg"))
				.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent event) {
				buttonDisable();
				recordAction();
			}
		};
		resetZoomAction = new AbstractAction("Alvie Ripristina Visuale",
				new ImageIcon(new ImageIcon(cl.getResource("img/restore.jpg")).getImage().getScaledInstance(ICON_SIZE,
						ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				resetZoomAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		setBookmarkAction = new AbstractAction("Set bookmark",
				new ImageIcon(new ImageIcon(cl.getResource("img/setbookmark.jpg")).getImage()
						.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				setBookmarkAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		setBookmarkAction.setEnabled(false);
		singleTabAction = new AbstractAction("Single pane mode",
				new ImageIcon(new ImageIcon(cl.getResource("img/frames.jpg")).getImage().getScaledInstance(ICON_SIZE,
						ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				singleTabAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		singleTabAction.setEnabled(isMultiPaneView);
		unsetBookmarkAction = new AbstractAction("Unset bookmark",
				new ImageIcon(new ImageIcon(cl.getResource("img/unsetbookmark.jpg")).getImage()
						.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				unsetBookmarkAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		unsetBookmarkAction.setEnabled(false);
		viewAction = new AbstractAction("View", new ImageIcon(new ImageIcon(cl.getResource("img/view.jpg")).getImage()
				.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				viewAction();
			}
		};
		zoomInAction = new AbstractAction("Alvie Ingrandisci",
				new ImageIcon(new ImageIcon(cl.getResource("img/zoomin.jpg")).getImage().getScaledInstance(ICON_SIZE,
						ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				zoomInAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
		zoomOutAction = new AbstractAction("Alvie Rimpicciolisci",
				new ImageIcon(new ImageIcon(cl.getResource("img/zoomout.jpg")).getImage().getScaledInstance(ICON_SIZE,
						ICON_SIZE, Image.SCALE_AREA_AVERAGING))) {
			public void actionPerformed(ActionEvent e) {
				buttonDisable();
				zoomOutAction();
				buttonUpdate();
				toolBar.requestFocus();
				System.gc();
			}
		};
	}

	private void createAlgorithmPlayer() {
		algorithmPlayer = new AlgorithmPlayer();
		algorithmPlayer.createMainFrame();
		algorithmPlayer.getMainFrame().getContentPane().add(createAlvieMenu(), BorderLayout.NORTH);
		algorithmPlayer.getMainFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		algorithmPlayer.setMultiPaneView(isMultiPaneView);
		algorithmPlayer.getTabbedPane().addMouseListener(new MouseAdapter() {
			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					createBookmarksPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}

			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}
		});
		algorithmPlayer.getMainFrame().setVisible(true);
	}

	private JMenuBar createAlvieMenu() {
		JMenuBar toret = new JMenuBar();
		toret.setLayout(new FlowLayout(FlowLayout.LEFT));
		bookmarks = new Vector<String>();
		toret.add(toolBar);
		return toret;
	}

	private JPopupMenu createBookmarksPopupMenu() {
		JPopupMenu bookmarksPopupMenu = new JPopupMenu();
		JMenu menu = new JMenu(localResourceBundle.getString("menu006"));
		for (int i = 0; i < bookmarks.size(); i++) {
			addBookmark(menu, bookmarks.get(i));
		}
		bookmarksPopupMenu.add(menu);
		return bookmarksPopupMenu;
	}

	private JToolBar createToolBar() {
		JToolBar tb = new JToolBar(JToolBar.HORIZONTAL);
		JButton tmp = tb.add(aboutAction);
		tmp.setMnemonic(KeyEvent.VK_A);
		tmp.setToolTipText(localResourceBundle.getString("toolTip023"));
		tmp = tb.add(globalConfigAction);
		tmp.setMnemonic(KeyEvent.VK_G);
		tmp.setToolTipText(localResourceBundle.getString("toolTip021"));
		tb.addSeparator();
		tmp = tb.add(recordAction);
		tmp.setMnemonic(KeyEvent.VK_R);
		tmp.setToolTipText(localResourceBundle.getString("toolTip001"));
		tmp = tb.add(viewAction);
		tmp.setMnemonic(KeyEvent.VK_V);
		tmp.setToolTipText(localResourceBundle.getString("toolTip002"));
		tmp = tb.add(configAction);
		tmp.setMnemonic(KeyEvent.VK_C);
		tmp.setToolTipText(localResourceBundle.getString("toolTip020"));
		tb.addSeparator();
		tmp = tb.add(beginAction);
		tmp.setMnemonic(KeyEvent.VK_HOME);
		tmp.setToolTipText(localResourceBundle.getString("toolTip003"));
		tmp = tb.add(backwardAction);
		tmp.setMnemonic(KeyEvent.VK_LEFT);
		tmp.setToolTipText(localResourceBundle.getString("toolTip005"));
		tmp = tb.add(forwardAction);
		tmp.setMnemonic(KeyEvent.VK_RIGHT);
		tmp.setToolTipText(localResourceBundle.getString("toolTip004"));
		tmp = tb.add(endAction);
		tmp.setMnemonic(KeyEvent.VK_END);
		tmp.setToolTipText(localResourceBundle.getString("toolTip006"));
		tb.addSeparator();
		tmp = tb.add(zoomInAction);
		tmp.setMnemonic(KeyEvent.VK_O);
		tmp.setToolTipText(localResourceBundle.getString("toolTip015"));
		tmp = tb.add(resetZoomAction);
		tmp.setMnemonic(KeyEvent.VK_EQUALS);
		tmp.setToolTipText(localResourceBundle.getString("toolTip016"));
		tmp = tb.add(zoomOutAction);
		tmp.setMnemonic(KeyEvent.VK_I);
		tmp.setToolTipText(localResourceBundle.getString("toolTip017"));
		tb.addSeparator();
		tmp = tb.add(singleTabAction);
		tmp.setMnemonic(KeyEvent.VK_S);
		tmp.setToolTipText(localResourceBundle.getString("toolTip007"));
		tmp = tb.add(multiTabAction);
		tmp.setMnemonic(KeyEvent.VK_M);
		tmp.setToolTipText(localResourceBundle.getString("toolTip008"));
		tb.addSeparator();
		tmp = tb.add(setBookmarkAction);
		tmp.setMnemonic(KeyEvent.VK_B);
		tmp.setToolTipText(localResourceBundle.getString("toolTip009"));
		tmp = tb.add(unsetBookmarkAction);
		tmp.setMnemonic(KeyEvent.VK_U);
		tmp.setToolTipText(localResourceBundle.getString("toolTip010"));
		tmp = tb.add(bookmarkBackwardAction);
		tmp.setMnemonic(KeyEvent.VK_PAGE_DOWN);
		tmp.setToolTipText(localResourceBundle.getString("toolTip011"));
		tmp = tb.add(bookmarkForwardAction);
		tmp.setMnemonic(KeyEvent.VK_PAGE_UP);
		tmp.setToolTipText(localResourceBundle.getString("toolTip012"));
		tb.setFloatable(false);
		return tb;
	}

	private void endAction() {
		if (algorithmPlayer.getMaximumStepNumber() > 0) {
			algorithmPlayer.setStepNumber(algorithmPlayer.getMaximumStepNumber());
			algorithmPlayer.playCurrentStep();
			buttonUpdate();
		}
	}

	private void forwardAction() {
		int stepNumber = algorithmPlayer.getStepNumber();
		if (stepNumber < algorithmPlayer.getMaximumStepNumber()) {
			stepNumber++;
			algorithmPlayer.setStepNumber(stepNumber);
			algorithmPlayer.playCurrentStep();
		}
	}

	public AlgorithmPlayer getAlgorithmPlayer() {
		return algorithmPlayer;
	}

	public List<String> getFrameNames(String imgDirectory) {
		File dir = new File(imgDirectory);
		String[] filesArray = dir.list();
		List<String> files = new ArrayList<String>();
		for (String fileName : filesArray) {
			if (fileName.indexOf(".jpg") >= 0) {
				files.add(imgDirectory + fileName);
			}
		}
		return files;
	}

	private void globalConfigAction() {
		Properties config = new Properties();
		File configFile = new File(System.getProperty("user.home"), ".alviesettings");
		try {
			if (configFile.exists()) {
				config.load(new FileInputStream(configFile));
				Enumeration<?> propertyNames = config.propertyNames();
				Vector<String> pn = new Vector<String>();
				while (propertyNames.hasMoreElements()) {
					pn.add((String) propertyNames.nextElement());
				}
				int n = pn.size();
				String[] propertyName = new String[n];
				for (int i = 0; i < n; i++) {
					propertyName[i] = pn.elementAt(i);
				}
				for (int i = 1; i < n; i++) {
					int j = i;
					while (j > 0 && propertyName[j - 1].compareTo(propertyName[j]) > 0) {
						String t = propertyName[j];
						propertyName[j] = propertyName[j - 1];
						propertyName[j - 1] = t;
						j = j - 1;
					}
				}
				String[] propertyValue = new String[n];
				for (int i = 0; i < n; i++) {
					propertyValue[i] = config.getProperty(propertyName[i]);
				}
				PropertyEditor propertyEditor = new PropertyEditor(localResourceBundle.getString("title057"),
						localResourceBundle.getString("label002"), propertyName, propertyValue, this);
				boolean exit = false;
				do {
					int returnVal = propertyEditor.showDialog();
					if (returnVal == PropertyEditor.APPROVE) {
						propertyValue = propertyEditor.getValues();
						for (int i = 0; i < propertyName.length; i++) {
							config.setProperty(propertyName[i], propertyValue[i]);
						}
						try {
							config.store(new FileOutputStream(configFile), "AlViE configuration");
						} catch (IOException e) {
							MessageUtility.errorMessage(
									localResourceBundle.getString("errorMessage033") + ". " + e.getCause(),
									localResourceBundle.getString("title056"), errorId + 1);
						}
						exit = true;
					} else if (returnVal == PropertyEditor.CANCEL) {
						return;
					}
				} while (!exit);
				MessageUtility.informationMessage(localResourceBundle.getString("message018"),
						localResourceBundle.getString("title061"));
				System.exit(0);
			} else {
				MessageUtility.informationMessage(localResourceBundle.getString("message016"),
						localResourceBundle.getString("title060"));
			}
		} catch (IOException e) {
			MessageUtility.errorMessage(localResourceBundle.getString("errorMessage031") + ". " + e.getCause(),
					localResourceBundle.getString("title055"), errorId + 1);
		}
	}

	private void modalityButtonsChangeRoutine() {
		if (!algorithmPlayer.isMultiPaneView()) {
			multiTabAction.setEnabled(true);
			singleTabAction.setEnabled(false);
		} else {
			multiTabAction.setEnabled(false);
			singleTabAction.setEnabled(true);
		}
		toolBar.repaint();
	}

	private void multiTabAction() {
		algorithmPlayer.setMultiPaneView(true);
		if (algorithmPlayer.getMaximumStepNumber() > 0) {
			algorithmPlayer.playCurrentStep();
		}
	}

	private void recordAction() {
		String[] filePathName = selectAlgorithmFile();
		if (filePathName != null) {
			boolean load = GraphicInputRequestsUtility.createAreUSureFrame(
					Configuration.getInstance().getLocalConfigurationBundle().getString("title068"),
					Configuration.getInstance().getLocalConfigurationBundle().getString("question015"));
			if (load) {
				resetAlgorithmPlayer();
				String algorithmFile = filePathName[0];
				try {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(algorithmFile));
					String algorithmClassFile = bufferedReader.readLine();
					bufferedReader.close();
					Class<?> algorithmClass = Class.forName(algorithmClassFile);
					Constructor<?> constructor = algorithmClass.getConstructor();
					Object object = constructor.newInstance();
					Class<?>[] args = new Class[1];
					args[0] = String.class;
					Method executeMethod = algorithmClass.getMethod("execute", args);
					String visFilename = selectVisualizationXMLFileToBeWritten(filePathName[1], filePathName[2]);
					if (visFilename != null) {
						AlgorithmExecutor recordTask = new AlgorithmExecutor(object, visFilename, executeMethod);
						new JProgressDialog(parentFrame, localResourceBundle.getString("progressMessage001"), true,
								recordTask);
						if (algorithmPlayer.getMaximumStepNumber() > 0) {
							algorithmPlayer.createStepBean(algorithmPlayer.getMaximumStepNumber() + 1);
							algorithmPlayer.setStepNumber(0);
							algorithmPlayer.playCurrentStep();
						} else {
							algorithmPlayer.reset();
							isFirstVisualization = true;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					MessageUtility.errorMessage(localResourceBundle.getString("errorMessage021") + ". " + e.getCause(),
							localResourceBundle.getString("title036"), errorId + 3);
				}
			}
		}
		buttonUpdate();
		toolBar.requestFocus();
		System.gc();
	}

	private void removeAllBookmarks() {
		bookmarks = new Vector<String>();
	}

	private void removeBookmark(String title) {
		for (int i = 0; i < bookmarks.size(); i++) {
			if (bookmarks.elementAt(i).equals(title)) {
				bookmarks.remove(i);
				break;
			}
		}
	}

	private void resetAlgorithmPlayer() {
		removeAllBookmarks();
		algorithmPlayer.reset();
	}

	private void resetZoomAction() {
		if (algorithmPlayer.getCurrentViewPanel() != null) {
			algorithmPlayer.getCurrentViewPanel().resetZoomFactor();
		}
	}

	private String[] selectAlgorithmFile() {
		String[] toret = null;
		String algDirPath = Main.algDirPath;
		File algDir = new File(algDirPath);
		if (algDir.isDirectory()) {
			File[] dirName = algDir.listFiles();
			int fileNumber = 0;
			for (int i = 0; i < dirName.length; i++) {
				if (dirName[i].isDirectory()) {
					fileNumber = fileNumber + 1;
				}
			}
			String[] algName = new String[fileNumber];
			String[] algDescription = new String[fileNumber];
			String[] algCategory = new String[fileNumber];
			File[] cleanDirName = new File[fileNumber];
			int k = 0;
			for (int i = 0; i < dirName.length; i++) {
				if (dirName[i].isDirectory()) {
					try {
						File file = new File(Main.algDirPath + dirName[i].getName());
						ResourceBundle configurationBundle = ResourceBundle.getBundle("config/visualization",
								new Locale(Main.config.getProperty("language")),
								new URLClassLoader(new URL[] { file.toURI().toURL() }));
//						ResourceBundle configurationBundle = ResourceBundle.getBundle(
//								Main.algDirPath + dirName[i].getName() + "/config/visualization",
//								new Locale(Main.config.getProperty("language")));
						cleanDirName[k] = dirName[i];
						algName[k] = configurationBundle.getString("algorithmName");
						algDescription[k] = configurationBundle.getString("algorithmDescription");
						algCategory[k] = configurationBundle.getString("algorithmCategory");
						k = k + 1;
					} catch (Exception e) {
						MessageUtility.errorMessage(localResourceBundle.getString("errorMessage034"),
								localResourceBundle.getString("title063"), errorId + 10);
					}
				}
			}
			for (int i = 1; i < fileNumber; i++) {
				int j = i;
				String nan = algName[j];
				String nad = algDescription[j];
				String nac = algCategory[j];
				File ncdn = cleanDirName[j];
				while (j > 0 && algCategory[j - 1].compareTo(nac) > 0) {
					algName[j] = algName[j - 1];
					algDescription[j] = algDescription[j - 1];
					algCategory[j] = algCategory[j - 1];
					cleanDirName[j] = cleanDirName[j - 1];
					j = j - 1;
				}
				algName[j] = nan;
				algDescription[j] = nad;
				algCategory[j] = nac;
				cleanDirName[j] = ncdn;
			}
			if (k > 0) {
				TreeChooser treeChooser = new TreeChooser(localResourceBundle.getString("title001"),
						localResourceBundle.getString("title043"), algCategory, algDescription, algName, this);
				int algChoice = 0;
				boolean exit = false;
				do {
					int returnVal = treeChooser.showDialog();
					if (returnVal == TreeChooser.APPROVE) {
						algChoice = treeChooser.getChoice();
						exit = true;
					} else if (returnVal == TreeChooser.CANCEL) {
						treeChooser.dispose();
						return null;
					}
				} while (!exit);
				try {
					File file = new File(Main.algDirPath + cleanDirName[algChoice].getName());
					ResourceBundle configurationBundle = ResourceBundle.getBundle("config/visualization",
							new Locale(Main.config.getProperty("language")),
							new URLClassLoader(new URL[] { file.toURI().toURL() }));
//					ResourceBundle configurationBundle = ResourceBundle.getBundle(
//							Main.algDirPath + cleanDirName[algChoice].getName() + "/config/visualization",
//							new Locale(Main.config.getProperty("language")));
					String algFileName = algDirPath + cleanDirName[algChoice].getName() + "/algorithm/"
							+ configurationBundle.getString("algorithmFileName");
					String algVisFilePath = algDirPath + cleanDirName[algChoice].getName() + "/visualization/";
					String algVisDefaultFileName = algDirPath + cleanDirName[algChoice].getName() + "/visualization/"
							+ configurationBundle.getString("algorithmFileName") + ".xml";
					toret = new String[3];
					toret[0] = algFileName;
					toret[1] = algVisFilePath;
					toret[2] = algVisDefaultFileName;
				} catch (Exception e) {
					MessageUtility.errorMessage(localResourceBundle.getString("errorMessage034"),
							localResourceBundle.getString("title063"), errorId + 10);
				}
			} else {
				MessageUtility.informationMessage(localResourceBundle.getString("message008"),
						localResourceBundle.getString("title044"));
			}
		}
		return toret;
	}

	private File selectVisualizationXMLFileToBeRead() {
		File toret = null;
		String algDirPath = Main.algDirPath;
		File algDir = new File(algDirPath);
		if (algDir.isDirectory()) {
			File[] dirName = algDir.listFiles();
			int fileNumber = 0;
			for (int i = 0; i < dirName.length; i++) {
				if (dirName[i].isDirectory()) {
					fileNumber = fileNumber + 1;
				}
			}
			String[] algName = new String[fileNumber];
			String[] algDescription = new String[fileNumber];
			String[] algCategory = new String[fileNumber];
			File[] cleanDirName = new File[fileNumber];
			int k = 0;
			for (int i = 0; i < dirName.length; i++) {
				if (dirName[i].isDirectory()) {
					try {
						File file = new File(Main.algDirPath + dirName[i].getName());
						ResourceBundle configurationBundle = ResourceBundle.getBundle("config/visualization",
								new Locale(Main.config.getProperty("language")),
								new URLClassLoader(new URL[] { file.toURI().toURL() }));
						cleanDirName[k] = dirName[i];
						algName[k] = configurationBundle.getString("algorithmName");
						algDescription[k] = configurationBundle.getString("algorithmDescription");
						algCategory[k] = configurationBundle.getString("algorithmCategory");
						k = k + 1;
					} catch (Exception e) {
						e.printStackTrace();
						MessageUtility.errorMessage(localResourceBundle.getString("errorMessage034"),
								localResourceBundle.getString("title063"), errorId + 10);
					}
				}
			}
			for (int i = 1; i < fileNumber; i++) {
				int j = i;
				String nan = algName[j];
				String nad = algDescription[j];
				String nac = algCategory[j];
				File ncdn = cleanDirName[j];
				while (j > 0 && algCategory[j - 1].compareTo(nac) > 0) {
					algName[j] = algName[j - 1];
					algDescription[j] = algDescription[j - 1];
					algCategory[j] = algCategory[j - 1];
					cleanDirName[j] = cleanDirName[j - 1];
					j = j - 1;
				}
				algName[j] = nan;
				algDescription[j] = nad;
				algCategory[j] = nac;
				cleanDirName[j] = ncdn;
			}
			if (k > 0) {
				TreeChooser treeChooser = new TreeChooser(localResourceBundle.getString("title002"),
						localResourceBundle.getString("title043"), algCategory, algDescription, algName, this);
				int algChoice = 0;
				boolean exit = false;
				do {
					int returnVal = treeChooser.showDialog();
					if (returnVal == TreeChooser.APPROVE) {
						algChoice = treeChooser.getChoice();
						exit = true;
					} else if (returnVal == TreeChooser.CANCEL) {
						return null;
					}
				} while (!exit);
				try {
					File file = new File(Main.algDirPath + cleanDirName[algChoice].getName());
					ResourceBundle configurationBundle = ResourceBundle.getBundle("config/visualization",
							new Locale(Main.config.getProperty("language")),
							new URLClassLoader(new URL[] { file.toURI().toURL() }));
					String folderPath = algDirPath + cleanDirName[algChoice].getName() + "/visualization/";
					String defaultPath = algDirPath + cleanDirName[algChoice].getName() + "/visualization/"
							+ configurationBundle.getString("algorithmVisualizationFileName") + ".xml";
					AlViEFileFilter xmlFileFilter = new AlViEFileFilter();
					xmlFileFilter.addExtension("xml");
					JFileChooser fileChooser = new JFileChooser(folderPath);
					fileChooser.setDialogTitle(localResourceBundle.getString("title002"));
					fileChooser.setFileFilter(xmlFileFilter);
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					if (defaultPath != null && new File(defaultPath).exists())
						fileChooser.setSelectedFile(new File(defaultPath));
					int returnVal = 0;
					exit = true;
					do {
						returnVal = fileChooser.showOpenDialog(this);
						if (returnVal == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
							toret = fileChooser.getSelectedFile().getAbsoluteFile();
						} else {
							toret = null;
							exit = true;
						}
					} while (!exit);
				} catch (Exception e) {

				}
			} else {
				MessageUtility.informationMessage(localResourceBundle.getString("message009"),
						localResourceBundle.getString("title045"));
			}
		}
		return toret;
	}

	private String selectVisualizationXMLFileToBeWritten(String algorithmPath, String defaultPath) {
		File toret = null;
		AlViEFileFilter xmlFileFilter = new AlViEFileFilter();
		xmlFileFilter.addExtension("xml");
		JFileChooser fileChooser = new JFileChooser(algorithmPath);
		fileChooser.setDialogTitle(Configuration.getInstance().getLocalConfigurationBundle().getString("title002"));
		fileChooser.setFileFilter(xmlFileFilter);
		if (defaultPath != null)
			fileChooser.setSelectedFile(new File(defaultPath));
		int returnVal = 0;
		boolean exit = true;
		do {
			returnVal = fileChooser.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				toret = fileChooser.getSelectedFile().getAbsoluteFile();
				if (toret.exists())
					exit = GraphicInputRequestsUtility.createAreUSureFrame(
							Configuration.getInstance().getLocalConfigurationBundle().getString("title003"),
							"'" + toret.getName() + "' " + Configuration.getInstance().getLocalConfigurationBundle()
									.getString("question001"));
			} else {
				toret = null;
				exit = true;
			}
		} while (!exit);
		System.gc();
		if (toret != null) {
			return toret.getAbsolutePath();
		} else {
			return null;
		}
	}

	private void setBookmarkAction() {
		if (algorithmPlayer.setBookmark(algorithmPlayer.getStepNumber())) {
			String title = algorithmPlayer.getBookmarkTitle(algorithmPlayer.getStepNumber());
			bookmarks.add(title);
			setBookmarkAction.setEnabled(false);
			unsetBookmarkAction.setEnabled(true);
		}
	}

	public void setFocus() {
		toolBar.requestFocus();
	}

	private void singleTabAction() {
		algorithmPlayer.setMultiPaneView(false);
		if (algorithmPlayer.getMaximumStepNumber() > 0) {
			algorithmPlayer.playCurrentStep();
		}
	}

	private void unsetBookmarkAction() {
		if (algorithmPlayer.unsetBookmark(algorithmPlayer.getStepNumber())) {
			removeBookmark(algorithmPlayer.getBookmarkTitle(algorithmPlayer.getStepNumber()));
			setBookmarkAction.setEnabled(true);
			unsetBookmarkAction.setEnabled(false);
		}
	}

	private void viewAction() {
		File currentVisualizationFile = visualizationFile;
		visualizationFile = selectVisualizationXMLFileToBeRead();
		if (visualizationFile != null) {
			boolean load = true;
			if (isFirstVisualization) {
				isFirstVisualization = false;
			} else {
				load = GraphicInputRequestsUtility.createAreUSureFrame(
						Configuration.getInstance().getLocalConfigurationBundle().getString("title067"),
						Configuration.getInstance().getLocalConfigurationBundle().getString("question014"));
			}
			if (load) {
				resetAlgorithmPlayer();
				VisualizationLoader readTask = new VisualizationLoader();
				new JProgressDialog(parentFrame, localResourceBundle.getString("progressMessage002"), true, readTask);
				if (algorithmPlayer.getMaximumStepNumber() > 0) {
					algorithmPlayer.setStepNumber(0);
					algorithmPlayer.playCurrentStep();
				} else {
					algorithmPlayer.reset();
					isFirstVisualization = true;
				}
			} else {
				isFirstVisualization = true;
			}
		} else {
			visualizationFile = currentVisualizationFile;
		}
		buttonUpdate();
		toolBar.requestFocus();
		System.gc();
	}

	private void zoomInAction() {
		if (algorithmPlayer.getCurrentViewPanel() != null) {
			algorithmPlayer.getCurrentViewPanel().increaseZoomFactor();
		}
	}

	private void zoomOutAction() {
		if (algorithmPlayer.getCurrentViewPanel() != null) {
			algorithmPlayer.getCurrentViewPanel().decreaseZoomFactor();
		}
	}

}
