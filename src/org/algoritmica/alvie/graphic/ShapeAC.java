package org.algoritmica.alvie.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class ShapeAC extends Dimension2D implements ShapeI, CurveI {
	protected Point2D origin;

	protected double height;

	protected double width;

	private Color lineColor;

	private Color fillColor;

	private LineType lineType;

	private float lineSize;

	public ShapeAC() {

	}

	public ShapeAC(double width, double height) {

		this.width = width;
		this.height = height;
	}

	public ShapeAC(double x, double y, double width, double height) {

		this.setOrigin(new Point2D.Double(x, y));
		this.width = width;
		this.height = height;
	}

	public abstract Shape getShape();

	public abstract Shape getInnerShape(int borderDeltaX, int borderDeltaY);

	public void paint(Graphics2D graphics) {
		int borderDeltaX = 2;
		int borderDeltaY = 2;
		Color tempColor = graphics.getColor();
		graphics.setColor(getLineColor());
		graphics.fill(this.getShape());
		graphics.setColor(getFillColor());
		graphics.fill(this.getInnerShape(borderDeltaX, borderDeltaY));
		graphics.setColor(getLineColor());
		Shape s = this.getShape();
		graphics.draw(s);
		graphics.setColor(tempColor);
	}

	public Point2D getOrigin() {
		return origin;
	}

	public void setOrigin(Point2D origin) {
		this.origin = origin;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color color) {
		this.lineColor = color;
	}

	public LineType getLineType() {
		return lineType;
	}

	public void setLineType(LineType type) {
		this.lineType = type;
	}

	public float getLineThickness() {
		return lineSize;
	}

	public void setLineThickness(float size) {
		this.lineSize = size;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color color) {
		this.fillColor = color;
	}

	public Rectangle2D getBoundingBox(Graphics2D graphics) {
		return new Rectangle2D.Double(origin.getX(), origin.getY(), width, height);
	}
}
