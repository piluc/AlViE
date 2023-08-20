package org.algoritmica.alvie.gui;

public abstract class ProgressTask implements Runnable {
	private JProgressDialog jpd;

	public ProgressTask() {
	}

	public ProgressTask(JProgressDialog jpd) {
		this.jpd = jpd;
	}

	protected final void step(String step) {
		jpd.step(step);
	}

	protected final void endStep() {
		jpd.endStep();
	}

	protected final void completed() {
		jpd.completed();
	}

	public abstract void execute();

	public final void run() {
		execute();
		jpd.setVisible(false);
		jpd.dispose();
	}

	public void setDialog(JProgressDialog progressDialog) {
		this.jpd = progressDialog;
	}
}
