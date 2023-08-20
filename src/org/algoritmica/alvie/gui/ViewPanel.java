package org.algoritmica.alvie.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.algoritmica.alvie.desktop.Main;
import org.algoritmica.alvie.graphic.PaintableI;
import org.algoritmica.alvie.graphic.PositionedI;

@SuppressWarnings("serial")
public class ViewPanel extends JSplitPane implements ViewPanelI {

	private static final double DIVIDER_LEVEL_NUMERATOR = Double
			.parseDouble(Main.config.getProperty("dataStructureMessageRatioNumerator"));
	private static final double DIVIDER_LEVEL_DENOMINATOR = Double
			.parseDouble(Main.config.getProperty("dataStructureMessageRatioDenominator"));

	private static final int SLIDER_MIDDLE_VALUE = 100;
	private JSlider horSlider;
	private JSlider verSlider;

	private DataStructureViewPanel drawingPanel;
	private MessageViewPanel messagePanel;

	private Point2D.Double clicked;

	private boolean flash;

	public ViewPanel(Dimension d, String dataStructurePanelTitle, String messagePanelTitle) {
		super(JSplitPane.VERTICAL_SPLIT);
		setResizeWeight(DIVIDER_LEVEL_NUMERATOR / DIVIDER_LEVEL_DENOMINATOR);
		setPreferredSize(d);
		setOneTouchExpandable(true);
		setContinuousLayout(true);

		drawingPanel = new DataStructureViewPanel();
		verSlider = createSlider(false);
		horSlider = createSlider(true);

		JScrollPane visScrollArea = new JScrollPane(drawingPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		JPanel topPane = new JPanel(new BorderLayout());

		topPane.setBorder(new TitledBorder(new EtchedBorder(), dataStructurePanelTitle));
		topPane.add(verSlider, BorderLayout.WEST);
		topPane.add(visScrollArea, BorderLayout.CENTER);
		topPane.add(horSlider, BorderLayout.SOUTH);

		messagePanel = new MessageViewPanel();
		JScrollPane msgScrollArea = new JScrollPane(messagePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel bottomPane = new JPanel(new BorderLayout());
		bottomPane.setBorder(new TitledBorder(new EtchedBorder(), messagePanelTitle));
		bottomPane.add(msgScrollArea, BorderLayout.CENTER);

		topPane.setPreferredSize(
				new Dimension((int) (DIVIDER_LEVEL_NUMERATOR / DIVIDER_LEVEL_DENOMINATOR * d.height), d.width));
		bottomPane.setPreferredSize(
				new Dimension((int) ((1 - DIVIDER_LEVEL_NUMERATOR / DIVIDER_LEVEL_DENOMINATOR) * d.height), d.width));

		setTopComponent(topPane);
		setBottomComponent(bottomPane);
		flash = false;
	}

	public JSlider createSlider(boolean isHorizontal) {
		JSlider slider;
		if (isHorizontal) {
			slider = new JSlider(JSlider.HORIZONTAL, 1, 2 * SLIDER_MIDDLE_VALUE, SLIDER_MIDDLE_VALUE);
			slider.setName("horizontal");
		} else {
			slider = new JSlider(JSlider.VERTICAL, 1, 2 * SLIDER_MIDDLE_VALUE, SLIDER_MIDDLE_VALUE);
			slider.setName("vertical");
		}
		slider.addChangeListener(new SliderListener());
		slider.setMajorTickSpacing(SLIDER_MIDDLE_VALUE / 10);
		slider.setMinorTickSpacing(SLIDER_MIDDLE_VALUE / 100);
		slider.setPaintTicks(true);
		slider.setToolTipText("" + slider.getValue() / SLIDER_MIDDLE_VALUE);
		return slider;
	}

	public void draw(Vector<PaintableI> items) {
		drawingPanel.draw(items);
		if (flash) {
			drawingPanel.flash(items);
		}
	}

	public void cleanDrawingPanel() {
		drawingPanel.clean();
	}

	public void cleanMessagePanel() {
		messagePanel.clean();
	}

	public void drawMessage(PositionedI message) {
		messagePanel.drawMessage(message);
	}

	public Graphics2D getViewGraphics() {
		return (Graphics2D) this.getGraphics();
	}

	public DataStructureViewPanel getDrawingPanel() {
		return drawingPanel;
	}

	public MessageViewPanel getMessagePanel() {
		return messagePanel;
	}

	public void decreaseZoomFactor() {
		drawingPanel.setZoomFactor(drawingPanel.getZoomFactor() / 2);
		repaint();
	}

	public void increaseZoomFactor() {
		drawingPanel.setZoomFactor(drawingPanel.getZoomFactor() * 2);
		repaint();
	}

	public void resetZoomFactor() {
		drawingPanel.setZoomFactor(1);
		horSlider.setValue(SLIDER_MIDDLE_VALUE);
		verSlider.setValue(SLIDER_MIDDLE_VALUE);
		repaint();
	}

	public Point2D.Double getClicked() {
		return clicked;
	}

	public boolean getFlash() {
		return flash;
	}

	public void setFlash(boolean f) {
		flash = f;
	}

	public void setClicked(Point2D.Double clicked) {
		this.clicked = clicked;
	}

	public void setMouseListener(MouseListener l) {
		addMouseListener(l);
		drawingPanel.addMouseListener(l);
		messagePanel.addMouseListener(l);
	}

	private class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			double value = (double) source.getValue() / SLIDER_MIDDLE_VALUE;
			if (source.getName().equals("horizontal"))
				drawingPanel.setScaleWidth(value);
			else if (source.getName().equals("vertical"))
				drawingPanel.setScaleHeight(value);
			source.setToolTipText("" + value);
			repaint();
		}
	}
}
