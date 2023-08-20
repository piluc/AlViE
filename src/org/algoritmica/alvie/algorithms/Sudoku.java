package org.algoritmica.alvie.algorithms;

import java.awt.Font;
import java.util.Vector;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.datastructure.Matrix;
import org.algoritmica.alvie.datastructure.MatrixI;
import org.algoritmica.alvie.gui.InputLoader;
import org.algoritmica.alvie.information.StringInformation;
import org.algoritmica.alvie.utility.MatrixXMLDrawerUtility;
import org.algoritmica.alvie.utility.MessageUtility;
import org.algoritmica.alvie.utility.PseudocodeXMLDrawerUtility;
import org.algoritmica.alvie.utility.VisualInnerClass;

public class Sudoku {
	class Cell {
		private int row;

		private int column;

		Cell(int r, int c) {
			row = r;
			column = c;
		}

	}

	private class VisualSudoku extends VisualInnerClass {
		private MatrixXMLDrawerUtility<StringInformation> boardDrawer;

		private PseudocodeXMLDrawerUtility pseudocodeDrawer;

		private Integer[] boardIndex;

		private String[] boardColor;

		private String[] boardFont;

		private boolean isPseudocodeVisible;

		private boolean onlyFirstLast;

		VisualSudoku(String visFile) {
			super("sudoku", visFile);
		}

		private void ieDigitDirectoryComputed(Cell cell,
				Array<StringInformation> directory) {
			if (!onlyFirstLast) {
				String cellColor = boardColor[cell.row * boardSize
						+ cell.column];
				boardColor[cell.row * boardSize + cell.column] = getResource("currentCellColor");
				boardDrawer.startStep(step++);
				boardDrawer.draw(boardIndex, boardColor, boardFont,
						getResource("ieDigitDirectoryComputedMessage"));
				if (isPseudocodeVisible) {
					pseudocodeDrawer.draw("", new int[] { 1 });
				}
				boardDrawer.endStep();
				boardColor[cell.row * boardSize + cell.column] = cellColor;
			}
		}

		private void ieDigitTrialFailed(Cell cell,
				Array<StringInformation> directory, int index) {
			if (!onlyFirstLast) {
				String cellColor = boardColor[cell.row * boardSize
						+ cell.column];
				boardColor[cell.row * boardSize + cell.column] = getResource("currentCellColor");
				boardDrawer.startStep(step++);
				boardDrawer.draw(boardIndex, boardColor, boardFont,
						getResource("ieDigitTrialFailedMessage"));
				if (isPseudocodeVisible) {
					pseudocodeDrawer.draw("", new int[] { 5 });
				}
				boardDrawer.endStep();
				boardColor[cell.row * boardSize + cell.column] = cellColor;
			}
		}

		private void ieEnd(boolean answer) {
			boardDrawer.startStep(step++);
			boardDrawer.draw(boardIndex, boardColor, boardFont,
					(answer ? getResource("ieEndTrueMessage")
							: getResource("ieEndFalseMessage")));
			boardDrawer.endStep();
			boardDrawer.endXMLFile();
		}

		private void ieNewSudokuInvocation(Cell cell) {
			if (!onlyFirstLast) {
				String cellColor = boardColor[cell.row * boardSize
						+ cell.column];
				boardColor[cell.row * boardSize + cell.column] = getResource("currentCellColor");
				boardDrawer.startStep(step++);
				boardDrawer.draw(boardIndex, boardColor, boardFont,
						getResource("ieNewSudokuInvocationMessage"));
				if (isPseudocodeVisible) {
					pseudocodeDrawer.draw("", new int[] { 0 });
				}
				boardDrawer.endStep();
				boardColor[cell.row * boardSize + cell.column] = cellColor;
			}
		}

		private void ieNextDigitTrial(Cell cell,
				Array<StringInformation> directory, int index) {
			if (!onlyFirstLast) {
				String cellColor = boardColor[cell.row * boardSize
						+ cell.column];
				boardColor[cell.row * boardSize + cell.column] = getResource("currentCellColor");
				boardDrawer.startStep(step++);
				boardDrawer.draw(boardIndex, boardColor, boardFont,
						getResource("ieNextDigitTrialMessage"));
				if (isPseudocodeVisible) {
					pseudocodeDrawer.draw("", new int[] { 3 });
				}
				boardDrawer.endStep();
				boardColor[cell.row * boardSize + cell.column] = cellColor;
			}
		}

		private void ieStart() {
			boardDrawer.startXMLFile(getResource("algorithmName"));
			boardDrawer.startStep(step++);
			boardDrawer.draw(boardIndex, boardColor, boardFont,
					getResource("ieStartMessage"));
			if (isPseudocodeVisible) {
				pseudocodeDrawer.draw("", null);
			}
			boardDrawer.endStep();
		}

		private void init() {
			boardDrawer = new MatrixXMLDrawerUtility<StringInformation>(board,
					getResource("boardName"), getOutputStream());
			boardDrawer.setOriginX(Integer
					.parseInt(getResource("boardXMLDrawerOriginX")));
			boardDrawer.setOriginY(Integer
					.parseInt(getResource("boardXMLDrawerOriginY")));
			boardDrawer.setDefaultFont(Font.decode(getResource("boardFont")));
			boardDrawer.setDefaultShapeWidth(Double
					.parseDouble(getResource("matrixElementWidth")));
			boardDrawer.setDefaultShapeHeight(Double
					.parseDouble(getResource("matrixElementHeight")));
			pseudocodeDrawer = new PseudocodeXMLDrawerUtility(this);
			isPseudocodeVisible = Boolean
					.parseBoolean(getResource("pseudocodeVisible"));
			onlyFirstLast = Boolean.parseBoolean(getResource("onlyFirstLast"));
			if (!onlyFirstLast) {
				initBoardDifferentialDrawArrays();
			}
		}

		private void initBoardDifferentialDrawArrays() {
			boardIndex = new Integer[boardSize * boardSize];
			boardColor = new String[boardSize * boardSize];
			boardFont = new String[boardSize * boardSize];
			String[] currentColor = { getResource("evenSubboardColor"),
					getResource("oddSubboardColor") };
			int currentColorIndex = 0;
			for (int i = 0; i < boardSize; i++) {
				if ((subboardSize % 2 == 0) && (i % subboardSize == 0)) {
					currentColorIndex = 1 - currentColorIndex;
				} else if ((subboardSize % 2 != 0) && (i % subboardSize != 0)) {
					currentColorIndex = 1 - currentColorIndex;
				}
				for (int j = 0; j < boardSize; j++) {
					boardIndex[i * boardSize + j] = i * boardSize + j;
					if (j % subboardSize == 0) {
						currentColorIndex = 1 - currentColorIndex;
					}
					boardColor[i * boardSize + j] = currentColor[currentColorIndex];
					if (isInputBoardCell[i][j]) {
						boardFont[i * boardSize + j] = getResource("boardInputFont");
					} else {
						boardFont[i * boardSize + j] = getResource("boardFont");
					}
				}
			}
		}
	}

	private MatrixI<StringInformation> board;

	private final StringInformation empty = new StringInformation(" ");

	private int boardSize, subboardSize;

	private StringInformation[] digitString;

	private boolean[][] digitIsNotInRow, digitIsNotInColumn,
			digitIsNotInSubboard, isInputBoardCell;

	private VisualSudoku vs;

	private void assign(StringInformation digit, Cell cell) {
		int digitIndex = Integer.parseInt(digit.stringValue()) - 1;
		int subboardIndex = subboardSize * (cell.row / subboardSize)
				+ cell.column / subboardSize;
		board.setAt(new StringInformation(extractFirstDigit(board.elementAt(
				cell.row, cell.column).stringValue())), cell.row, cell.column);
		digitIsNotInRow[cell.row][digitIndex] = false;
		digitIsNotInColumn[cell.column][digitIndex] = false;
		digitIsNotInSubboard[subboardIndex][digitIndex] = false;
	}

	private Array<StringInformation> cellDigitDirectory(Cell cell) {
		Vector<StringInformation> digitVector = new Vector<StringInformation>();
		if (isInputBoardCell[cell.row][cell.column]) {
			digitVector.add(board.elementAt(cell.row, cell.column));
		} else {
			for (int i = 0; i < digitString.length; i++) {
				if (isAcceptable(i, cell)) {
					digitVector.add(digitString[i]);
				}
			}
		}
		int n = digitVector.size();
		Array<StringInformation> digitArray = new Array<StringInformation>(n,
				new StringInformation(""));
		for (int i = 0; i < n; i++) {
			digitArray.setAt(digitVector.elementAt(i), i);
		}
		return digitArray;
	}

	private void empty(Cell cell) {
		board.setAt(empty, cell.row, cell.column);
	}

	private void empty(StringInformation digit, Cell cell) {
		int digitIndex = Integer.parseInt(digit.stringValue()) - 1;
		int subboardIndex = subboardSize * (cell.row / subboardSize)
				+ cell.column / subboardSize;
		board.setAt(new StringInformation(removeDigit(board.elementAt(cell.row,
				cell.column).stringValue())), cell.row, cell.column);
		digitIsNotInRow[cell.row][digitIndex] = true;
		digitIsNotInColumn[cell.column][digitIndex] = true;
		digitIsNotInSubboard[subboardIndex][digitIndex] = true;
	}

	public void execute(String visFile) {
		System.gc();
		vs = new VisualSudoku(visFile);
		if (readInput()) {
			initVariables();
			findSolution();
			MessageUtility.algorithmTerminated(vs.getResource("algorithmName"));
		}
	}

	private String extractFirstDigit(String s) {
		int index = s.indexOf(';');
		if (index > 0) {
			String digit = s.substring(1, index);
			return digit + " " + "[" + s.substring(index + 1);
		} else {
			index = s.indexOf(']');
			if (index > 0) {
				return s.substring(1, index) + " []";
			} else {
				return " ";
			}
		}
	}

	private void findSolution() {
		vs.init();
		vs.ieStart();
		Cell startingCell = new Cell(0, 0);
		if (isInputBoardCell[0][0]) {
			startingCell = nextEmptyCell(startingCell);
		}
		if (startingCell != null) {
			if (sudoku(startingCell)) {
				setFinalBoard();
				vs.ieEnd(true);
			} else {
				resetBoard();
				vs.ieEnd(false);
			}
		} else {
			vs.ieEnd(true);
		}
	}

	private void initVariables() {
		boardSize = board.width();
		digitIsNotInRow = new boolean[boardSize][boardSize];
		digitIsNotInColumn = new boolean[boardSize][boardSize];
		digitIsNotInSubboard = new boolean[boardSize][boardSize];
		isInputBoardCell = new boolean[boardSize][boardSize];
		digitString = new StringInformation[boardSize];
		for (int i = 0; i < boardSize; i++) {
			digitString[i] = new StringInformation("" + (i + 1));
			for (int j = 0; j < boardSize; j++) {
				digitIsNotInRow[i][j] = true;
				digitIsNotInColumn[i][j] = true;
				isInputBoardCell[i][j] = false;
				digitIsNotInSubboard[i][j] = true;
			}
		}
		int subboardIndex;
		int k;
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (!board.elementAt(i, j).isEqual(empty)) {
					subboardIndex = subboardSize * (i / subboardSize) + j
							/ subboardSize;
					for (k = 0; k < boardSize; k++) {
						if (board.elementAt(i, j).isEqual(digitString[k])) {
							break;
						}
					}
					digitIsNotInRow[i][k] = false;
					digitIsNotInColumn[j][k] = false;
					digitIsNotInSubboard[subboardIndex][k] = false;
					isInputBoardCell[i][j] = true;
				}
			}
		}
	}

	private boolean isAcceptable(int digit, Cell cell) {
		int submatrix = subboardSize * (cell.row / subboardSize) + cell.column
				/ subboardSize;
		return digitIsNotInRow[cell.row][digit]
				&& digitIsNotInColumn[cell.column][digit]
				&& digitIsNotInSubboard[submatrix][digit];
	}

	private boolean lastEmpty(Cell cell) {
		if (cell.column < (boardSize - 1)) {
			for (int j = cell.column + 1; j < boardSize; j++) {
				if (!isInputBoardCell[cell.row][j]) {
					return false;
				}
			}
		}
		for (int i = cell.row + 1; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (!isInputBoardCell[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	private String listing(Array<StringInformation> directory) {
		String listing = "[";
		for (int i = 0; i < directory.length() - 1; i++) {
			listing = listing + directory.elementAt(i).stringValue() + ";";
		}
		if (directory.length() > 0) {
			listing = listing
					+ directory.elementAt(directory.length() - 1).stringValue()
					+ "]";
		} else {
			listing = listing + "]";
		}
		return listing;
	}

	private Cell nextEmptyCell(Cell cell) {
		if (cell.column < (boardSize - 1)) {
			for (int j = cell.column + 1; j < boardSize; j++) {
				if (!isInputBoardCell[cell.row][j]) {
					return new Cell(cell.row, j);
				}
			}
		}
		for (int i = cell.row + 1; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (!isInputBoardCell[i][j]) {
					return new Cell(i, j);
				}
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean readInput() {
		InputLoader inputLoader = new InputLoader(vs.getAlgorithmPath(), vs
				.getResource("algorithmFileName"));
		board = (Matrix) inputLoader.load("Matrix", vs
				.getResource("selectInputMessage"));
		if (board == null) {
			return false;
		}
		boardSize = board.width();
		if (boardSize > 9) {
			MessageUtility.informationMessage(vs
					.getResource("boardTooBigMessage"), vs
					.getResource("boardTooBigMessageTitle"));
			return false;
		}
		subboardSize = (int) (Math.sqrt(boardSize));
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (board.elementAt(i, j) == null) {
					board.setAt(empty, i, j);
				}
			}
		}
		return true;
	}

	private String removeDigit(String s) {
		int index = s.indexOf('[');
		if (index > 0) {
			return s.substring(index);
		} else {
			return " ";
		}
	}

	private void resetBoard() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (!isInputBoardCell[i][j]) {
					board.setAt(new StringInformation(" "), i, j);
				}
			}
		}
	}

	private void setFinalBoard() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (!isInputBoardCell[i][j]) {
					String s = board.elementAt(i, j).stringValue();
					int index = s.indexOf(" [");
					board.setAt(new StringInformation(s.substring(0, index)),
							i, j);
				}
			}
		}
	}

	private boolean sudoku(Cell cell) {
		vs.ieNewSudokuInvocation(cell);
		Array<StringInformation> directory = cellDigitDirectory(cell);
		board.setAt(new StringInformation(listing(directory)), cell.row,
				cell.column);
		vs.ieDigitDirectoryComputed(cell, directory);
		for (int i = 0; i < directory.length(); i++) {
			vs.ieNextDigitTrial(cell, directory, i);
			assign(directory.elementAt(i), cell);
			if (!lastEmpty(cell) && !sudoku(nextEmptyCell(cell))) {
				vs.ieDigitTrialFailed(cell, directory, i);
				empty(directory.elementAt(i), cell);
			} else {
				return true;
			}
		}
		empty(cell);
		return false;
	}

}
