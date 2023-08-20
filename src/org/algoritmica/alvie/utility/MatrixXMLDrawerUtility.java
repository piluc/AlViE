package org.algoritmica.alvie.utility;

import java.io.OutputStream;

import org.algoritmica.alvie.datastructure.MatrixI;
import org.algoritmica.alvie.drawer.MatrixXMLDrawer;
import org.algoritmica.alvie.information.InformationI;
import org.algoritmica.alvie.visualdatastructure.VisualMatrix;

public class MatrixXMLDrawerUtility<I extends InformationI> extends
		StructureXMLDrawerUtility<MatrixI<I>, MatrixXMLDrawer<I>> {
	private VisualMatrix<I> visualDataStructure;

	public MatrixXMLDrawerUtility(MatrixI<I> m, String name, OutputStream os) {
		super(m, os);
		visualDataStructure = new VisualMatrix<I>();
		drawer = new MatrixXMLDrawer<I>(name, os);
		visualDataStructure.setXMLDrawer(drawer);
	}

	public void draw(Integer[] index, String[] color, String[] font,
			String message) {
		startGraphicStructure(drawer.getName(), MATRIX);
		drawer.draw(index, color, font, dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}

	public void draw(Integer[] index, String[] color, String message) {
		if (index != null) {
			String[] font = new String[index.length];
			for (int i = 0; i < index.length; i++) {
				font[i] = FontUtility.getStringFont(drawer.getDefaultFont());
			}
			draw(index, color, font, message);
		} else {
			draw(null, null, null, message);
		}
	}

	public void draw(String message) {
		startGraphicStructure(drawer.getName(), MATRIX);
		drawer.draw(null, null, null, dataStructure);
		drawer.drawMessage(message);
		endStructure();
	}
}
