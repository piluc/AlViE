package org.algoritmica.alvie.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.algoritmica.alvie.desktop.Main;

public class VisualNode extends PaintableAC {
	private String text;

	protected Point2D origin;

	protected ShapeAC shape;

	protected int id;

	private Font font = Font.decode(Main.config.getProperty("defaultVisualDataStructureFont"));

	public VisualNode(String text, ShapeAC shape, int x, int y) {
		this.text = text;
		this.shape = shape;
		origin = new Point(x, y);
		shape.setOrigin(origin);
		shape.setFillColor(Color.red);
		shape.setLineType(LineType.CONTINUE);
		shape.setLineColor(Color.black);
	}

	private Color blackOrWhiteColor(Color c) {
		if (c.getRed() < 125 && c.getGreen() < 125 && c.getBlue() < 125) {
			return Color.white;
		} else {
			return Color.black;
		}
	}

	public Rectangle2D getBoundingBox(Graphics2D graphics) {
		return shape.getBoundingBox(graphics);
	}

	public Color getFillColor() {
		return shape.getFillColor();
	}

	public Color getTextColor() {
		return blackOrWhiteColor(getFillColor());
	}

	public Font getFont() {
		return font;
	}

	public double getHeight() {
		return shape.getHeight();
	}

	public int getId() {
		return id;
	}

	public Point2D getOrigin() {
		return origin;
	}

	public ShapeAC getShape() {
		return shape;
	}

	public String getText() {
		return text;
	}

	public double getWidth() {
		return shape.getWidth();
	}

	@SuppressWarnings("unused")
	private Color oppositeColor(Color c) {
		int oppositeRed = 255 - c.getRed();
		int oppositeGreen = 255 - c.getGreen();
		int oppositeBlue = 255 - c.getBlue();
		return new Color(oppositeRed, oppositeGreen, oppositeBlue);
	}

	public void paint(Graphics2D graphics) {
		int borderDeltaX = 2;
		int borderDeltaY = 2;
		double width = shape.width;
		double height = shape.height;
		Color fillColor = getFillColor();
		// Color oppositeFillColor = oppositeColor(fillColor);
		Color oppositeFillColor = blackOrWhiteColor(fillColor);
		Color currentColor = graphics.getColor();
		graphics.setColor(oppositeFillColor);
		graphics.fill(shape.getShape());
		graphics.setColor(fillColor);
		graphics.fill(shape.getInnerShape(borderDeltaX, borderDeltaY));
		graphics.setColor(oppositeFillColor);
		Font currentFont = graphics.getFont();
		graphics.setFont(font);
		font = scaleFont(text, width, height, graphics);
		graphics.setFont(font);
		int tw = graphics.getFontMetrics().stringWidth(text);
		int th = graphics.getFontMetrics().getHeight();
		graphics.drawString(text, (int) (shape.getOrigin().getX() + (width - tw) / 2), (int) (shape.getOrigin().getY() - (height - th) / 2));
		graphics.setFont(currentFont);
		graphics.setColor(currentColor);
	}

	private Font scaleFont(String text, double w, double h, Graphics2D g) {
		int horizontalSeparatorSpace = Integer.parseInt(Main.config.getProperty("borderInformationHorizontalSeparator"));
		int verticalSeparatorSpace = Integer.parseInt(Main.config.getProperty("borderInformationVerticalSeparator"));
		Font font = g.getFont();
		while (font.getSize() > 0 && g.getFontMetrics(font).stringWidth(text) > w - 2 * horizontalSeparatorSpace) {
			font = new Font(font.getName(), font.getStyle(), font.getSize() - 1);
		}
		while (font.getSize() > 0 && g.getFontMetrics(font).getHeight() > h - 2 * verticalSeparatorSpace) {
			font = new Font(font.getName(), font.getStyle(), font.getSize() - 1);
		}
		if (font.getSize() > 0) {
			return font;
		} else {
			return g.getFont();
		}
	}

	public void setFillColor(Color color) {
		shape.setFillColor(color);
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLineType(LineType t) {

	}

	public void setOrigin(Point2D origin) {
		this.origin = origin;
		shape.setOrigin(origin);
	}

	public void setShape(ShapeAC shape) {
		shape.setOrigin(this.origin);
		shape.setFillColor(this.getFillColor());
		shape.setLineColor(this.getShape().getLineColor());
		this.shape = shape;
	}

	public void setSize(double width, double height) {
		shape.setSize(width, height);
	}
}
