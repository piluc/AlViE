package org.algoritmica.alvie.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class VisualLink extends PaintableAC {

	private Color lineColor;

	private LineType lineType;

	private float lineThickness;

	private VisualNode source;

	private VisualNode destination;

	private long id;

	public VisualLink(VisualNode source, VisualNode destination) {
		setSource(source);
		setDestination(destination);
	}

	public Rectangle2D getBoundingBox(Graphics2D graphics) {
		double sourceNodeWidth = source.getBoundingBox(graphics).getWidth();
		double sourceNodeHeight = source.getBoundingBox(graphics).getHeight();
		double destinationNodeWidth = destination.getBoundingBox(graphics).getWidth();
		double destinationNodeHeight = destination.getBoundingBox(graphics).getHeight();
		double startX = source.getOrigin().getX() + sourceNodeWidth / 2;
		double startY = source.getOrigin().getY() - sourceNodeHeight / 2;
		double endX = destination.getOrigin().getX() + destinationNodeWidth / 2;
		double endY = destination.getOrigin().getY() - destinationNodeHeight / 2;
		double width = Math.abs(endX - startX);
		double height = Math.abs(endY - startY);
		if (startX > endX) {
			startX = endX;
		}
		if (startY > endY) {
			startY = endY;
		}
		return new Rectangle2D.Double(startX, startY, width, height);
	}

	public VisualNode getDestination() {
		return destination;
	}

	public Color getLineColor() {
		return this.lineColor;
	}

	public float getLineThickness() {
		return this.lineThickness;
	}

	public LineType getLineType() {
		return lineType;
	}

	public VisualNode getSource() {
		return source;
	}

	public void paint(Graphics2D graphics) {
		double sourceNodeWidth = source.getBoundingBox(graphics).getWidth();
		double sourceNodeHeight = source.getBoundingBox(graphics).getHeight();
		double destinationNodeWidth = destination.getBoundingBox(graphics).getWidth();
		double destinationNodeHeight = destination.getBoundingBox(graphics).getHeight();
		double startX = source.getOrigin().getX() + sourceNodeWidth / 2;
		double startY = source.getOrigin().getY() - sourceNodeHeight / 2;
		double endX = destination.getOrigin().getX() + destinationNodeWidth / 2;
		double endY = destination.getOrigin().getY() - destinationNodeHeight / 2;
		Color currentColor = graphics.getColor();
		graphics.setColor(this.lineColor);
		Stroke currentStroke = graphics.getStroke();
		if (lineType == LineType.CONTINUE) {
			graphics.setStroke(new BasicStroke(lineThickness));
		} else {
			graphics.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 5 }, 0));
		}
		Line2D segment = new Line2D.Double(startX, startY, endX, endY);
		graphics.draw(segment);
		graphics.setStroke(currentStroke);
		graphics.setColor(currentColor);
	}

	public void paint(Graphics2D graphics, AffineTransform transform) {
		this.paint(graphics);
	}

	public void setDestination(VisualNode node) {
		this.destination = node;
	}

	public void setLineColor(Color color) {
		this.lineColor = color;
	}

	public void setLineThickness(float thickness) {
		this.lineThickness = thickness;
	}

	public void setLineType(LineType type) {
		this.lineType = type;
	}

	public void setSource(VisualNode node) {
		this.source = node;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
