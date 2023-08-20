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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.algoritmica.alvie.config.Configuration;

@SuppressWarnings("serial")
public class PropertyEditor extends JDialog {
	public static final int CANCEL = 0;
	public static final int APPROVE = 1;
	public static final int WAITING = 2;
	private PropertyTable editorPane;
	private String cancelButtonName = Configuration.getInstance()
			.getLocalConfigurationBundle().getString("buttonName013");
	private String okButtonName = Configuration.getInstance()
			.getLocalConfigurationBundle().getString("buttonName014");
	private int state;
	private boolean isChanged;

	private class PropertyTable extends JPanel {
		private String[] columnNames = { "Property name", "Property value" };

		private Object[][] data;

		public PropertyTable(String[] nam, String[] val) {
			super(new GridLayout(1, 0));
			data = new Object[nam.length][2];
			for (int r = 0; r < data.length; r++) {
				data[r][0] = nam[r];
				data[r][1] = val[r];
			}
			JTable table = new JTable(new PropertyTableModel());
			table.setPreferredScrollableViewportSize(new Dimension(500, 70));
//			table.setFillsViewportHeight(true);
			JScrollPane scrollPane = new JScrollPane(table);
			add(scrollPane);
		}

		class PropertyTableModel extends AbstractTableModel {
			public int getColumnCount() {
				return columnNames.length;
			}

			public int getRowCount() {
				return data.length;
			}

			public String getColumnName(int col) {
				return columnNames[col];
			}

			public Object getValueAt(int row, int col) {
				return data[row][col];
			}

			public boolean isCellEditable(int row, int col) {
				if (col < 1) {
					return false;
				} else {
					return true;
				}
			}

			public void setValueAt(Object value, int row, int col) {
				if (!value.equals(data[row][col])) {
					isChanged = true;
					data[row][col] = value;
					fireTableCellUpdated(row, col);
				}
			}
		}
	}

	public PropertyEditor(String title, String text, String[] nam,
			String[] val, JFrame owner) {
		super(JOptionPane.getFrameForComponent(owner), true);
		editorPane = new PropertyTable(nam, val);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				state = CANCEL;
				setVisible(false);
			}
		});
		state = WAITING;
		isChanged = false;
		setTitle(title);
		JButton cancelButton = new JButton(cancelButtonName); //$NON-NLS-1$
		JButton okButton = new JButton(okButtonName); //$NON-NLS-1$
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = CANCEL;
				setVisible(false);
			}
		});
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isChanged) {
					state = APPROVE;
				} else {
					state = CANCEL;
				}
				setVisible(false);
			}
		});
		getRootPane().setDefaultButton(okButton);
		JScrollPane scroller = new JScrollPane(editorPane);
		scroller.setPreferredSize(new Dimension(600, 400));
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
		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.SOUTH);
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - this.getBounds().width) / 2;
		int y = (dim.height - this.getBounds().height) / 2;
		setLocation(x, y);
		setVisible(true);
	}

	public String[] getValues() {
		String[] value = new String[editorPane.data.length];
		for (int r = 0; r < value.length; r++) {
			value[r] = editorPane.data[r][1].toString();
		}
		return value;
	}

	public int showDialog() {
		return state;
	}
}