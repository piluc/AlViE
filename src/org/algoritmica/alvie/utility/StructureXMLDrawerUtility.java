package org.algoritmica.alvie.utility;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

import org.algoritmica.alvie.config.Configuration;
import org.algoritmica.alvie.datastructure.DataStructureI;
import org.algoritmica.alvie.drawer.DrawerA;
import org.algoritmica.alvie.drawer.XMLDrawer;
import org.algoritmica.alvie.information.InformationI;

public abstract class StructureXMLDrawerUtility<DS extends DataStructureI<? extends InformationI>, D extends DrawerA> {
	private static ResourceBundle localResourceBundle = Configuration.getInstance().getLocalConfigurationBundle();

	private final static int errorId = 1100000;

	public static final int ARRAY = 0;

	public static final int BINARYTREE = 1;

	public static final int GRAPH = 2;

	public static final int LIST = 3;

	public static final int MATRIX = 4;

	public static final int PSEUDOCODE = 5;

	public static final int QUEUE = 6;

	public static final int STACK = 7;

	public static final int GEOMETRICFIGURE = 8;

	public static OutputStream openXMLFile(String fileName) {
		OutputStream outputStream = null;
		File file = new File(fileName);
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			MessageUtility.errorMessage(file.getAbsolutePath() + localResourceBundle.getString("errorMessage027"), localResourceBundle.getString("title042"), errorId + 1);
		}
		return outputStream;
	}

	private String[] structureType = { "Array", "BinaryTree", "Graph", "List", "Matrix", "Pseudocode", "Queue", "Stack", "GeometricFigure" };

	private String[] structureClassName = { "Array", "BinaryTree", "Graph", "List", "Matrix", "Pseudocode", "Queue", "Stack", "GeometricFigure" };

	private String[] visualStructureClassName = { "VisualArray", "VisualBinaryTree", "VisualGraph", "VisualList", "VisualMatrix", "VisualPseudocode", "VisualQueue", "VisualStack", "VisualGeometricFigure" };

	private String[] structureDrawerClassName = { "ArrayGraphicDrawer", "BinaryTreeGraphicDrawer", "GraphGraphicDrawer", "ListGraphicDrawer", "MatrixGraphicDrawer", "PseudocodeGraphicDrawer", "QueueGraphicDrawer", "StackGraphicDrawer", "GeometricFigureGraphicDrawer" };

	private XMLDrawer xmlFileDrawer;

	private String drawerName;

	protected DS dataStructure;

	protected D drawer;

	public StructureXMLDrawerUtility(DS ds, OutputStream os) {
		dataStructure = ds;
		xmlFileDrawer = new XMLDrawer(os);
	}

	public void endStep() {
		xmlFileDrawer.endStep();
	}

	public void endStructure() {
		xmlFileDrawer.endStructure();
	}

	public void endXMLFile() {
		xmlFileDrawer.write("</algorithm>");
		xmlFileDrawer.getPrintWriter().close();
	}

	public String getDrawerName() {
		return drawerName;
	}

	public void setDataStructure(DS ds) {
		dataStructure = ds;
	}

	public void setDefaultColor(Color c) {
		drawer.setDefaultColor(c);
	}

	public void setDefaultFont(Font f) {
		drawer.setDefaultFont(f);
	}

	public void setDefaultShape(String shape) {
		drawer.setDefaultShape(shape);
	}

	public void setDefaultShapeHeight(Double h) {
		drawer.setDefaultShapeHeight(h);
	}

	public void setDefaultShapeWidth(Double w) {
		drawer.setDefaultShapeWidth(w);
	}

	public void setOriginX(int x) {
		drawer.setOriginX(x);
	}

	public void setOriginY(int y) {
		drawer.setOriginY(y);
	}

	public void startGraphicStructure(String name, int structure) {
		xmlFileDrawer.startStructure(name, structureType[structure], visualStructureClassName[structure], structureClassName[structure], structureDrawerClassName[structure]);
	}

	public void startStep(int step) {
		xmlFileDrawer.startStep(step);
	}

	public void startXMLFile(String algorithmName) {
		xmlFileDrawer.startXML();
		xmlFileDrawer.write("<algorithm name=\"" + algorithmName + "\">");
	}
}
