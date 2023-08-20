package org.algoritmica.alvie.utility;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.BinaryTreeI;
import org.algoritmica.alvie.drawer.BinaryTreeXMLDrawer;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.visualdatastructure.VisualBinaryTree;

public class BinaryTreeXMLDrawerUtility<I extends InformationI> extends
		StructureXMLDrawerUtility<BinaryTreeI<I>, BinaryTreeXMLDrawer<I>> {
	private VisualBinaryTree<I> visualDataStructure;

	public BinaryTreeXMLDrawerUtility(BinaryTreeI<I> bt, String name,
			OutputStream os) {
		super(bt, os);
		visualDataStructure = new VisualBinaryTree<I>();
		drawer = new BinaryTreeXMLDrawer<I>(name, os);
		visualDataStructure.setXMLDrawer(drawer);
	}

	public void draw(Integer[] position, String[] color, String[] font,
			String[] shape, String[] height, String[] width,
			Integer[] linePosition, String[] lineType, String[] lineColor,
			String[] lineThickness, String message) {
		startGraphicStructure(drawer.getName(), BINARYTREE);
		drawer.draw(position, color, font, shape, height, width, linePosition,
				lineType, lineColor, lineThickness, dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}

	public void draw(Integer[] index, String[] color, String message) {
		if (index != null) {
			String[] font = new String[index.length];
			String[] height = new String[index.length];
			String[] shape = new String[index.length];
			String[] width = new String[index.length];
			for (int i = 0; i < index.length; i++) {
				font[i] = FontUtility.getStringFont(drawer.getDefaultFont());
				height[i] = drawer.getDefaultShapeHeight().toString();
				shape[i] = drawer.getDefaultShape();
				width[i] = drawer.getDefaultShapeWidth().toString();
			}
			draw(index, color, font, shape, height, width, null, null, null,
					null, message);
		} else {
			draw(null, null, null, null, null, null, null, null, null, null,
					message);
		}
	}

	public void draw(Integer[] index, String[] color, String[] font,
			String[] shape, String[] height, String[] width, String message) {
		draw(index, color, font, shape, height, width, null, null, null, null,
				message);
	}

	public void draw(String message) {
		startGraphicStructure(drawer.getName(), BINARYTREE);
		drawer.draw(null, null, null, null, null, null, null, null, null, null,
				dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}
}
