package org.algoritmica.alvie.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.algoritmica.alvie.config.Configuration;

/*
 * This modal dialog lets the user choose any number of items from a supplied
 * list.
 */
@SuppressWarnings("serial")
public class TreeChooser extends JDialog implements ActionListener {
	public static final int CANCEL = 0;
	public static final int APPROVE = 1;
	public static final int WAITING = 2;
	private AlgorithmTree treePane;
	private ResourceBundle localResourceBundle = Configuration.getInstance()
			.getLocalConfigurationBundle();
	private int choice;
	private int state;
	private String[] algorithmCategory, algorithmDescription, algorithmName;

	public int showDialog() {
		return state;
	}

	public int getChoice() {
		return choice;
	}

	public TreeChooser(String title, String text, String[] cat, String[] des,
			String[] nam, AlViE owner) {
		super(JOptionPane.getFrameForComponent(owner), true);
		algorithmCategory = cat;
		algorithmDescription = des;
		algorithmName = nam;
		treePane = new AlgorithmTree();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				state = CANCEL;
				setVisible(false);
			}
		});
		state = WAITING;
		setTitle(title);
		JLabel textLabel = new JLabel(" " + text); //$NON-NLS-1$
		JButton cancelButton = new JButton(localResourceBundle
				.getString("buttonName013")); //$NON-NLS-1$
		JButton okButton = new JButton(localResourceBundle
				.getString("buttonName014")); //$NON-NLS-1$
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = CANCEL;
				setVisible(false);
			}
		});
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = APPROVE;
				if (choice <0) {
					state = CANCEL;
				}
				setVisible(false);
			}
		});
		getRootPane().setDefaultButton(okButton);
		JPanel headerPane = new JPanel();
		headerPane.setLayout(new BoxLayout(headerPane, BoxLayout.X_AXIS));
		headerPane.add(textLabel);
		headerPane.add(Box.createHorizontalGlue());
		headerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		JScrollPane scroller = new JScrollPane(treePane);
		scroller.setPreferredSize(new Dimension(500, 400));
		JPanel scrollPane = new JPanel(new BorderLayout());
		scrollPane.add(scroller, BorderLayout.CENTER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(cancelButton);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(okButton);
		Container contentPane = getContentPane();
		contentPane.add(headerPane, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.SOUTH);
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - this.getBounds().width) / 2;
		int y = (dim.height - this.getBounds().height) / 2;
		setLocation(x, y);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		choice = Integer.parseInt(e.getActionCommand());
	}

	class AlgorithmTree extends JPanel implements TreeSelectionListener {
		private JEditorPane descriptionPane;
		private JTree tree;
		private String lineStyle = "Angled";

		public AlgorithmTree() {
			super(new GridLayout(1, 0));

			// Create the nodes.
			DefaultMutableTreeNode top = new DefaultMutableTreeNode(
					"AlViE algorithm visualization collection");
			createNodes(top);

			// Create a tree that allows one selection at a time.
			tree = new JTree(top);
			tree.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.addTreeSelectionListener(this);
			tree.putClientProperty("JTree.lineStyle", lineStyle);

			// Create the scroll pane and add the tree to it.
			JScrollPane treeView = new JScrollPane(tree);

			// Create the description viewing pane.
			descriptionPane = new JEditorPane();
			descriptionPane.setEditable(false);
			JScrollPane htmlView = new JScrollPane(descriptionPane);

			// Add the scroll panes to a split pane.
			JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			splitPane.setTopComponent(treeView);
			splitPane.setBottomComponent(htmlView);

			Dimension minimumSize = new Dimension(100, 50);
			htmlView.setMinimumSize(minimumSize);
			treeView.setMinimumSize(minimumSize);
			splitPane.setDividerLocation(300);
			splitPane.setPreferredSize(new Dimension(500, 300));

			add(splitPane);
		}

		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
					.getLastSelectedPathComponent();

			if (node == null)
				return;

			Object nodeInfo = node.getUserObject();
			if (node.isLeaf()) {
				AlgorithmInfo algorithm = (AlgorithmInfo) nodeInfo;
				displayDescription(algorithm.algorithmDescription);
				choice = algorithm.algorithmIndex;
			} else {
				choice = -1;
			}
		}

		private class AlgorithmInfo {
			private String algorithmName;
			private String algorithmDescription;
			private int algorithmIndex;

			private AlgorithmInfo(String name, String description, int index) {
				algorithmName = name;
				algorithmDescription = description;
				algorithmIndex = index;
			}

			public String toString() {
				return algorithmName;
			}
		}

		private void displayDescription(String description) {
			descriptionPane.setText(description);
		}

		private void createNodes(DefaultMutableTreeNode top) {
			DefaultMutableTreeNode category = null;
			DefaultMutableTreeNode algorithm = null;
			String currentCategory = null;

			for (int ac = 0; ac < algorithmCategory.length; ac++) {
				if (currentCategory == null
						|| !currentCategory.equals(algorithmCategory[ac])) {
					currentCategory = algorithmCategory[ac];
					category = new DefaultMutableTreeNode(algorithmCategory[ac]);
					top.add(category);
				}
				algorithm = new DefaultMutableTreeNode(new AlgorithmInfo(
						algorithmName[ac], algorithmDescription[ac], ac));
				category.add(algorithm);
			}
		}
	}
}