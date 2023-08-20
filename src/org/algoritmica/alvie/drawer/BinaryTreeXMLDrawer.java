package org.algoritmica.alvie.drawer;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.BinaryTreeI;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.utility.ColorUtility;
import org.algoritmica.alvie.utility.FontUtility;

/*
 * This is the XML binary tree drawer. The XML binary tree, element, visual binary tree and visual element
 * definition is specified in the algorithm XSD file. The message drawing is delegated
 * to an XML drawer.
 */
public class BinaryTreeXMLDrawer<I extends InformationI> extends BinaryTreeDrawerA {
	protected XMLDrawer xmlDrawer;

	public BinaryTreeXMLDrawer(String name, OutputStream outputStream) {
		super(name);
		xmlDrawer = new XMLDrawer(outputStream);
	}

	private void recursivelyDraw(BinaryTreeI<I> binaryTree, Long position) {
		if (binaryTree.size() > 0) {
			xmlDrawer.write("<element id=\"" + position + "\" value=\"" + binaryTree.root().stringValue() + "\"/>");
			recursivelyDraw(binaryTree.leftSubtree(), 2 * position + 1);
			recursivelyDraw(binaryTree.rightSubtree(), 2 * position + 2);
		}
	}

	public void draw(BinaryTreeI<I> binaryTree) {
		int size = binaryTree.size();
		xmlDrawer.write("<binaryTree size=\"" + size + "\" type=\"" + binaryTree.getType() + "\">");
		recursivelyDraw(binaryTree, 0L);
		xmlDrawer.write("</binaryTree>");
	}

	private void drawVisual(Integer[] position, String[] color, String[] font, String[] shape, String[] height, String[] width, Integer[] linePosition, String[] lineType, String[] lineColor, String[] lineThickness) {
		xmlDrawer.write("<visualBinaryTree defaultColor=\"" + ColorUtility.getStringColor(getDefaultColor()) + "\" defaultFont=\"" + FontUtility.getStringFont(getDefaultFont()) + "\" defaultShape=\"" + getDefaultShape() + "\" defaultShapeHeight=\"" + getDefaultShapeHeight() + "\" defaultShapeWidth=\"" + getDefaultShapeWidth() + "\"  defaultLineColor=\"" + ColorUtility.getStringColor(getDefaultLineColor()) + "\" defaultLineType=\"" + getDefaultLineType() + "\" defaultLineThickness=\""
				+ getDefaultLineThickness() + "\" originX=\"" + getOriginX() + "\" originY=\"" + getOriginY() + "\">");
		if (position != null) {
			for (int c = 0; c < position.length; c++) {
				xmlDrawer.write("<visualLinkedElement id=\"" + position[c] + "\" color=\"" + color[c] + "\" font=\"" + font[c] + "\" shape=\"" + shape[c] + "\" shapeHeight=\"" + height[c] + "\" shapeWidth=\"" + width[c] + "\"/>");
			}
		}
		if (linePosition != null) {
			for (int c = 0; c < linePosition.length; c++) {
				xmlDrawer.write("<visualLink id=\"" + linePosition[c] + "\" lineColor=\"" + lineColor[c] + "\" lineType=\"" + lineType[c] + "\" lineThickness=\"" + lineThickness[c] + "\"/>");
			}
		}
		xmlDrawer.write("</visualBinaryTree>");
	}

	public void draw(Integer[] position, String[] color, String[] font, String[] shape, String[] height, String[] width, Integer[] linePosition, String[] lineType, String[] lineColor, String[] lineThickness, BinaryTreeI<I> binaryTree) {
		draw(binaryTree);
		drawVisual(position, color, font, shape, height, width, linePosition, lineType, lineColor, lineThickness);
	}

	public void drawMessage(String message) {
		xmlDrawer.drawMessage(message);
	}

	public XMLDrawer getXMLDrawer() {
		return xmlDrawer;
	}

}
