package org.algoritmica.alvie.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class VisualArc extends PaintableAC {

	private Color lineColor;

	private LineType lineType;

	private float lineThickness;

	private VisualNode source;

	private VisualNode destination;

	private boolean isOriented;

	private boolean isWeighted;

	private String weight;

	private Font font;

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	private long id;

	public VisualArc(VisualNode source, VisualNode destination) {
		setSource(source);
		setDestination(destination);
	}

	/*
	 * The label is positioned either at the middle of the link (if the arc is
	 * not oriented) or at 1/3 of the distance between the source and the
	 * destination.
	 */
	private Point2D computeLabelOrigin(Graphics2D g) {
		int hS = (int) source.getHeight();
		int wS = (int) source.getWidth();
		int hD = (int) destination.getHeight();
		int wD = (int) destination.getWidth();
		int sX = (int) (source.getOrigin().getX() + wS / 2);
		int sY = (int) (source.getOrigin().getY() - hS / 2);
		int dX = (int) (destination.getOrigin().getX() + wD / 2);
		int dY = (int) (destination.getOrigin().getY() - hD / 2);
		int labelX = dX, labelY = dY;
		if (dX <= sX && dY >= sY) {
			labelX = dX + (sX - dX) / 3 - g.getFontMetrics().stringWidth(weight);
			labelY = dY - (dY - sY) / 3 - 1;
		}
		if (dX <= sX && dY < sY) {
			labelX = dX + (sX - dX) / 3 + 1;
			labelY = dY + (sY - dY) / 3 - 1;
		}
		if (dX > sX && dY <= sY) {
			labelX = dX - (dX - sX) / 3 - g.getFontMetrics().stringWidth(weight);
			labelY = dY + (sY - dY) / 3 - 1;
		}
		if (dX > sX && dY > sY) {
			labelX = dX - (dX - sX) / 3 + 1;
			labelY = dY - (dY - sY) / 3 - 1;
		}
		return new Point2D.Double(labelX, labelY);
	}

	/*
	 * The arrow is constructed as follows. First, the arrow head coordinates
	 * are computed as the intersection between the segment joining the source
	 * and the destination nodes and the rectangle bounding the destination
	 * node. Second, the rotation angle of the arrow is computed. Finally, the
	 * arrow triangle is constructed (horizontal with head at right) and
	 * rotated.
	 */
	private Shape createArrow() {
		double hS = (int) source.getHeight();
		double wS = (int) source.getWidth();
		double hD = (int) destination.getHeight();
		double wD = (int) destination.getWidth();
		double sX = (int) (source.getOrigin().getX() + wS / 2);
		double sY = (int) (source.getOrigin().getY() - hS / 2);
		double dX = (int) (destination.getOrigin().getX() + wD / 2);
		double dY = (int) (destination.getOrigin().getY() - hD / 2);
		double arrowX = dX, arrowY = dY;
		double rotationAngle = Math.toRadians(0);
		if (dX == sX && dY > sY) {
			arrowX = dX;
			arrowY = dY - hD / 2;
			rotationAngle = Math.toRadians(90);
		} else if (dX == sX && dY < sY) {
			arrowX = dX;
			arrowY = dY + hD / 2;
			rotationAngle = Math.toRadians(-90);
		} else if (dX < sX && dY == sY) {
			arrowX = dX + wD / 2;
			arrowY = dY;
			rotationAngle = Math.toRadians(180);
		} else if (dX > sX && dY == sY) {
			arrowX = dX - wD / 2;
			arrowY = dY;
		} else {
			double lowerXI = xIntersection(dY + hD / 2, sX, sY, dX, dY);
			double upperXI = xIntersection(dY - hD / 2, sX, sY, dX, dY);
			double leftYI = yIntersection(dX - wD / 2, sX, sY, dX, dY);
			double rightYI = yIntersection(dX + wD / 2, sX, sY, dX, dY);
			if (dX < sX && dY > sY) {
				if (upperXI < dX + wD / 2) {
					arrowX = upperXI;
					arrowY = dY - hD / 2;
					rotationAngle = Math.toRadians(180) - Math.atan((hD / 2) / (upperXI - dX));
				} else {
					arrowX = dX + wD / 2;
					arrowY = rightYI;
					rotationAngle = Math.toRadians(180) - Math.atan((dY - rightYI) / (wD / 2));
				}
			}
			if (dX < sX && dY < sY) {
				if (lowerXI < dX + wD / 2) {
					arrowX = lowerXI;
					arrowY = dY + hD / 2;
					rotationAngle = Math.toRadians(180) + Math.atan((rightYI - dY) / (wD / 2));
				} else {
					arrowX = dX + wD / 2;
					arrowY = rightYI;
					rotationAngle = Math.toRadians(180) + Math.atan((hD / 2) / (lowerXI - dX));
				}
			}
			if (dX > sX && dY < sY) {
				if (lowerXI > dX - wD / 2) {
					arrowX = lowerXI;
					arrowY = dY + hD / 2;
					rotationAngle = -Math.atan((hD / 2) / (dX - lowerXI));
				} else {
					arrowX = dX - wD / 2;
					arrowY = leftYI;
					rotationAngle = -Math.atan((leftYI - dY) / (wD / 2));
				}
			}
			if (dX > sX && dY > sY) {
				if (upperXI > dX - wD / 2) {
					arrowX = upperXI;
					arrowY = dY - hD / 2;
					rotationAngle = Math.atan((hD / 2) / (dX - upperXI));
				} else {
					arrowX = dX - wD / 2;
					arrowY = leftYI;
					rotationAngle = Math.atan((dY - leftYI) / (wD / 2));
				}
			}
		}
		int barb = 15;
		double angle = Math.toRadians(20);
		Polygon path = new Polygon();
		path.addPoint((int) arrowX, (int) arrowY);
		double x = arrowX - (barb * Math.cos(angle));
		double y = arrowY + (barb * Math.sin(angle));
		path.addPoint((int) x, (int) y);
		x = arrowX - (barb * Math.cos(-angle));
		y = arrowY + (barb * Math.sin(-angle));
		path.addPoint((int) x, (int) y);
		AffineTransform at = new AffineTransform();
		at.rotate(rotationAngle, arrowX, arrowY);
		Shape shape = at.createTransformedShape(path);
		return shape;
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

	public long getId() {
		return id;
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

	public String getWeight() {
		return weight;
	}

	public boolean isOriented() {
		return isOriented;
	}

	public boolean isWeighted() {
		return isWeighted;
	}

	private double xIntersection(double y, double x0, double y0, double x1, double y1) {
		return (x0 + (x1 - x0) * (y - y0) / (y1 - y0));
	}

	private double yIntersection(double x, double x0, double y0, double x1, double y1) {
		return (y0 + (y1 - y0) * (x - x0) / (x1 - x0));
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
		Font currentFont = graphics.getFont();
		graphics.setFont(font);
		Line2D segment = new Line2D.Double(startX, startY, endX, endY);
		graphics.draw(segment);
		if (isOriented) {
			Shape shape = createArrow();
			graphics.fill(shape);
		}
		if (isWeighted()) {
			Point2D labelOrigin = computeLabelOrigin(graphics);
			graphics.drawString(weight, (int) labelOrigin.getX(), (int) labelOrigin.getY());
		}
		graphics.setFont(currentFont);
		graphics.setStroke(currentStroke);
		graphics.setColor(currentColor);
	}

	public void setDestination(VisualNode node) {
		this.destination = node;
	}

	public void setId(long id) {
		this.id = id;
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

	public void setOriented(boolean isOriented) {
		this.isOriented = isOriented;
	}

	public void setSource(VisualNode node) {
		this.source = node;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public void setWeighted(boolean isWeighted) {
		this.isWeighted = isWeighted;
	}
}
