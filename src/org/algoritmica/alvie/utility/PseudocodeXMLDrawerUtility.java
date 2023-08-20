package org.algoritmica.alvie.utility;

import java.awt.Font;
import java.io.File;

import org.algoritmica.alvie.datastructure.Pseudocode;
import org.algoritmica.alvie.drawer.PseudocodeXMLDrawer;
import org.algoritmica.alvie.gui.PseudocodeReader;
import org.algoritmica.alvie.visualdatastructure.VisualPseudocode;

public class PseudocodeXMLDrawerUtility extends StructureXMLDrawerUtility<Pseudocode, PseudocodeXMLDrawer> {
	private VisualPseudocode visualDataStructure;

	private String emphasizedLineColor, emphasizedLineHeight, emphasizedLineWidth;

	private void readPseudocode(VisualInnerClass vic, String prefix) {
		PseudocodeReader pseudocodeLoader = new PseudocodeReader();
		dataStructure = (Pseudocode) pseudocodeLoader.load(new File(vic.getAlgorithmPath() + "pseudocode/" + vic.getResource(prefix + "FileName") + ".xml"));
		if (dataStructure != null) {
			visualDataStructure = new VisualPseudocode();
			drawer = new PseudocodeXMLDrawer(vic.getResource(prefix + "Title"), vic.getOutputStream());
			visualDataStructure.setXMLDrawer(drawer);
			setEmphasizedLineColor(vic.getResource(prefix + "EmphasizedLineColor"));
			setFont(vic.getResource(prefix + "Font"));
			setOriginX(Integer.parseInt(vic.getResource(prefix + "OriginX")));
			setOriginY(Integer.parseInt(vic.getResource(prefix + "OriginY")));
			setLineHeight(vic.getResource(prefix + "LineHeight"));
			setLineWidth(vic.getResource(prefix + "LineWidth"));
			setEmphasizedLineHeight(vic.getResource(prefix + "EmphasizedLineHeight"));
			setEmphasizedLineWidth(vic.getResource(prefix + "EmphasizedLineWidth"));
		}
	}

	public PseudocodeXMLDrawerUtility(VisualInnerClass vic) {
		super(null, vic.getOutputStream());
		readPseudocode(vic, "pseudocode");
	}

	public PseudocodeXMLDrawerUtility(VisualInnerClass vic, String prefix) {
		super(null, vic.getOutputStream());
		readPseudocode(vic, prefix);
	}

	public void draw(String msg, int[] lineIndex) {
		if (dataStructure != null) {
			startGraphicStructure(drawer.getName(), PSEUDOCODE);
			if (lineIndex != null) {
				Integer[] pseudocodeIndex = new Integer[lineIndex.length];
				String[] pseudocodeColor = new String[lineIndex.length];
				String[] pseudocodeFont = new String[lineIndex.length];
				String[] pseudocodeShapeHeight = new String[lineIndex.length];
				String[] pseudocodeShapeWidth = new String[lineIndex.length];
				for (int i = 0; i < lineIndex.length; i++) {
					pseudocodeIndex[i] = lineIndex[i];
					pseudocodeColor[i] = emphasizedLineColor;
					pseudocodeFont[i] = FontUtility.getStringFont(drawer.getDefaultFont());
					pseudocodeShapeHeight[i] = emphasizedLineHeight;
					pseudocodeShapeWidth[i] = emphasizedLineWidth;
				}
				drawer.draw(pseudocodeIndex, pseudocodeColor, pseudocodeFont, pseudocodeShapeHeight, pseudocodeShapeWidth, dataStructure);
			} else {
				drawer.draw(null, null, null, null, null, dataStructure);
			}
			drawer.drawMessage(msg);
			endStructure();
		}
	}

	public void setFont(String font) {
		drawer.setDefaultFont(Font.decode(font));
	}

	public void setEmphasizedLineColor(String color) {
		emphasizedLineColor = color;
	}

	public void setEmphasizedLineHeight(String height) {
		emphasizedLineHeight = height;
	}

	public void setEmphasizedLineWidth(String width) {
		emphasizedLineWidth = width;
	}

	public void setLineHeight(String h) {
		drawer.setDefaultShapeHeight(Double.parseDouble(h));
	}

	public void setLineWidth(String w) {
		drawer.setDefaultShapeWidth(Double.parseDouble(w));
	}
}
