package org.algoritmica.alvie.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class JProgressDialog extends JDialog {
	private int height;
	private int width;
	private JLabel label;
	private JProgressBar progressbar;
	private ProgressTask task;
	private Thread t;
	private JProgressDialog thisJPD;
	private JToolBar toolbar;
	private boolean indeterminate;
	private int startValue;
	private int endValue;

	public JProgressDialog(Frame owner, String title, boolean modal, ProgressTask pt) throws HeadlessException {
		super(owner, title, modal);
		height = 130;
		width = owner.getBounds().width / 3;
		this.task = pt;
		thisJPD = this;
		indeterminate = true;
		init();
		start();
	}

	public JProgressDialog(Frame owner, String title, boolean modal, int start, int end, ProgressTask pt) throws HeadlessException {
		super(owner, title, modal);
		height = 130;
		width = owner.getBounds().width / 3;
		this.task = pt;
		thisJPD = this;
		indeterminate = false;
		startValue = start;
		endValue = end;
		init();
		start();
	}

	private JToolBar getToolBar() {
		JToolBar res = new JToolBar();
		res.setLayout(new FlowLayout());
		res.setFloatable(false);
		JButton stop = new JButton("Cancel");
		stop.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ev) {
				t.stop();
				thisJPD.setVisible(false);
				thisJPD.dispose();
				System.gc();
			}
		});
		res.add(stop);
		return res;
	}

	private void start() {
		task.setDialog(this);
		t = new Thread(task);
		t.start();
		this.setVisible(true);
	}

	private void init() {
		JPanel internal = new JPanel(new BorderLayout());
		JPanel foo = new JPanel();
		label = new JLabel();
		foo.add(label);
		internal.add(foo, BorderLayout.NORTH);
		progressbar = new JProgressBar(JProgressBar.HORIZONTAL);
		progressbar.setIndeterminate(indeterminate);
		if (!indeterminate) {
			progressbar.setStringPainted(true);
		}
		progressbar.setMinimum(startValue);
		progressbar.setMaximum(endValue);
		progressbar.setValue(startValue);
		foo = new JPanel();
		foo.add(progressbar);
		internal.add(foo, BorderLayout.CENTER);
		toolbar = getToolBar();
		internal.add(toolbar, BorderLayout.SOUTH);
		setContentPane(internal);
		setSize(new Dimension(width, height));
		setLocationRelativeTo(getOwner());
	}

	public void step(String step) {
		label.setText(step);
		if (!indeterminate) {
			progressbar.setValue(progressbar.getValue() + 1);
		}
		repaint();
	}

	public void removeCancel() {
		this.remove(toolbar);
		repaint();
	}

	public void endStep() {
		label.setText("");
		repaint();
	}

	public void completed() {
		this.setVisible(false);
		this.dispose();
	}
}
