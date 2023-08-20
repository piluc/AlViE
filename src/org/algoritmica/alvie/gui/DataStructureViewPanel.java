package org.algoritmica.alvie.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

import org.algoritmica.alvie.desktop.Main;
import org.algoritmica.alvie.graphic.PaintableI;

@SuppressWarnings("serial")
public class DataStructureViewPanel extends JPanel {

	private static final int SENSIBILITY_OFFSET = 2;

	private double zoomFactor = 1;
	private double scaleWidth = 1;
	private double scaleHeight = 1;

	private Graphics2D graphics;

	private Rectangle2D.Double effectiveBoundingBox = new Rectangle2D.Double();

	private Vector<PaintableI> items;

	private Vector<PaintableI> flashItems;

	public DataStructureViewPanel() {
		clean();
	}

	public PaintableI PaintableContains(Point2D.Double p) {
		Rectangle2D.Double clickedArea = new Rectangle2D.Double(p.getX() - SENSIBILITY_OFFSET / 2, p.getY() - SENSIBILITY_OFFSET / 2, SENSIBILITY_OFFSET, SENSIBILITY_OFFSET);
		for (PaintableI item : items) {
			if (item.getBoundingBox(graphics).intersects(clickedArea))
				return item;
		}
		return null;
	}

	public void clean() {
		setBackground(Color.white);
		items = new Vector<PaintableI>();
		flashItems = new Vector<PaintableI>();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (items != null) {
			graphics = (Graphics2D) g;
			graphics.scale(zoomFactor * scaleWidth, zoomFactor * scaleHeight);
			int n = items.size();
			for (int i = 0; i < n; i++) {
				items.elementAt(i).paint(graphics);
				effectiveBoundingBox.add(items.elementAt(i).getBoundingBox(graphics));
			}
			resize();
		}
	}

	public void draw(Vector<PaintableI> itms) {
		int n = itms.size();
		for (int i = 0; i < n; i++) {
			items.add(itms.elementAt(i));
		}
	}

	public void flash(Vector<PaintableI> itms) {
		int n = itms.size();
		for (int i = 0; i < n; i++) {
			flashItems.add(itms.elementAt(i));
		}
	}

	public BufferedImage getFlashImage(int width, int height, int type) {
		BufferedImage bimage = new BufferedImage(Math.max(width, getWidth()), height + getHeight(), type);
		Graphics2D g2d = bimage.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		int n = flashItems.size();
		for (int i = 0; i < n; i++) {
			flashItems.elementAt(i).paint(g2d);
			effectiveBoundingBox.add(flashItems.elementAt(i).getBoundingBox(g2d));
		}
		return bimage;
	}

	public void setScaleWidth(double value) {
		scaleWidth = value;
	}

	public void setScaleHeight(double value) {
		scaleHeight = value;
	}

	public double getScaledX(double measure) {
		return measure * (zoomFactor * scaleWidth);
	}

	public double getScaledY(double measure) {
		return measure * (zoomFactor * scaleHeight);
	}

	public double getEffectiveX(double measure) {
		return measure / (zoomFactor * scaleWidth);
	}

	public double getEffectiveY(double measure) {
		return measure / (zoomFactor * scaleHeight);
	}

	public void resize() {
		setPreferredSize(new Dimension((int) (getScaledX(effectiveBoundingBox.getWidth())) + Integer.parseInt(Main.config.getProperty("defaultVisualDataStructureOriginX")), (int) (getScaledY(effectiveBoundingBox.getHeight())) + Integer.parseInt(Main.config.getProperty("defaultVisualDataStructureOriginY"))));
		revalidate();
	}

	public double getZoomFactor() {
		return zoomFactor;
	}

	public void setZoomFactor(double value) {
		zoomFactor = value;
	}

}
