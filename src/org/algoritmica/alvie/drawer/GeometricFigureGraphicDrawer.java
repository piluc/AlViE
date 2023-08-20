package org.algoritmica.alvie.drawer;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import org.algoritmica.alvie.bean.VisualGeometricFigureBean;
import org.algoritmica.alvie.datastructure.GeometricFigure;
import org.algoritmica.alvie.graphic.PaintableI;
import org.algoritmica.alvie.graphic.Text;
import org.algoritmica.alvie.graphic.VisualCircle;
import org.algoritmica.alvie.graphic.VisualGeometricObjectA;
import org.algoritmica.alvie.graphic.VisualPoint;
import org.algoritmica.alvie.graphic.VisualRectangle;
import org.algoritmica.alvie.graphic.VisualSegment;
import org.algoritmica.alvie.graphic.VisualText;
import org.algoritmica.alvie.gui.ViewPanel;
import org.algoritmica.alvie.gui.ViewPanelI;
import org.algoritmica.alvie.information.GeometricObjectInformation;

/*
 * This is the graphical geometric figure drawer. Each element has its own color, (in the case
 * of a text geometric object) font, and (in the case of a segment) line thickness. The
 * elements are drawn as expected. The message drawing method simply draws 
 * the message in the view panel associated with the drawer.
 */
public class GeometricFigureGraphicDrawer extends GeometricFigureDrawerA {
	private Color[] color;

	private Font[] font;

	private Float[] lineThickness;

	private ViewPanelI viewPanel;

	public GeometricFigureGraphicDrawer(String name, ViewPanel viewPanel) {
		super(name);
		this.viewPanel = viewPanel;
	}

	public void draw(GeometricFigure figure, VisualGeometricFigureBean bean) {
		int n = figure.length();
		color = new Color[n];
		font = new Font[n];
		lineThickness = new Float[n];
		for (int i = 0; i < n; i++) {
			color[i] = bean.getDefaultColor() != null ? bean.getDefaultColor() : getDefaultColor();
			font[i] = bean.getDefaultFont() != null ? bean.getDefaultFont() : getDefaultFont();
			lineThickness[i] = bean.getDefaultLineThickness() != null ? bean.getDefaultLineThickness() : getDefaultLineThickness();
		}
		for (Integer i : bean.getElementColorCollection().keySet()) {
			color[i] = bean.getElementColorCollection().get(i);
			font[i] = bean.getElementFontCollection().get(i);
			lineThickness[i] = bean.getLineThicknessCollection().get(i);
		}
		setOriginX(!isMultiPane() ? bean.getOriginX() : getDefaultOriginX());
		setOriginY(!isMultiPane() ? bean.getOriginY() : getDefaultOriginY());
		draw(getOriginX(), getOriginY(), figure);
	}

	private void draw(int x, int y, GeometricFigure figure) {
		viewPanel.draw(visit(x, y, figure));
	}

	public void drawMessage(String message) {
		Text messageText = new Text(message);
		viewPanel.drawMessage(messageText);
	}

	public ViewPanelI getPanel() {
		return viewPanel;
	}

	public void setPanel(ViewPanel panel) {
		this.viewPanel = panel;
	}

	private Vector<PaintableI> visit(int x, int y, GeometricFigure figure) {
		int size = figure.length();
		Vector<PaintableI> graphicObjectCollection = new Vector<PaintableI>();
		for (int k = 0; k < size; k++) {
			GeometricObjectInformation object = figure.elementAt(k);
			VisualGeometricObjectA vo = new VisualPoint(getOriginX() + (int) object.getFirstValue(), getOriginY() + (int) object.getSecondValue());
			if (object.getType() == GeometricObjectInformation.CIRCLE) {
				vo = new VisualCircle(getOriginX() + (int) object.getFirstValue(), getOriginY() + (int) object.getSecondValue(), (int) object.getThirdValue());
			} else if (object.getType() == GeometricObjectInformation.SEGMENT) {
				vo = new VisualSegment(getOriginX() + (int) object.getFirstValue(), getOriginY() + (int) object.getSecondValue(), getOriginX() + (int) object.getThirdValue(), getOriginY() + (int) object.getFourthValue());
			} else if (object.getType() == GeometricObjectInformation.RECTANGLE) {
				vo = new VisualRectangle(getOriginX() + (int) object.getFirstValue(), getOriginY() + (int) object.getSecondValue(), (int) object.getThirdValue(), (int) object.getFourthValue());
			} else if (object.getType() == GeometricObjectInformation.TEXT) {
				vo = new VisualText(object.getText(), getOriginX() + (int) object.getFirstValue(), getOriginY() + (int) object.getSecondValue());
			}
			if (color != null) {
				vo.setColor(color[k]);
			} else {
				vo.setColor(getDefaultColor());
			}
			if (lineThickness != null) {
				vo.setLineThickness(lineThickness[k]);
			} else {
				vo.setLineThickness(getDefaultLineThickness());
			}
			graphicObjectCollection.add(vo);
		}
		return graphicObjectCollection;
	}
}
