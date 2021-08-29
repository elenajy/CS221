import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {
		Scanner fileScan = new Scanner(new File(filename));
	
		if (!fileScan.hasNextInt()) {
			throw new InvalidFileFormatException("Top row must contain two integers.");
		}
		ROWS = fileScan.nextInt();
		if (!fileScan.hasNextInt()) {
			throw new InvalidFileFormatException("Top row must contain two integers.");
		}
		COLS = fileScan.nextInt();
		
		board = new char[ROWS][COLS];
		
		try {
			int rowCounter = 0;
					
			// counts number of rows to make sure rows and cols match given (e.g., 3x4 vs 4x3)
			while (fileScan.hasNextLine()) {
				if(fileScan.nextLine().length() != 0) {
					rowCounter++;
				} 
			}
					
			// reopens file so cursor goes to beginning of file
			fileScan = new Scanner(new File(filename));
			fileScan.nextLine();
					
			if (ROWS != rowCounter) {
				throw new InvalidFileFormatException("Specified dimensions do not match given dimensions");
			}
		
			// repopulate data into the board
			int currentRow = 0;
			int currentCol = 0;
			
			int startCount = 0;
			int endCount = 0;
			
			int filled = 0;
			
			
			// checks each number in the file
			while (fileScan.hasNext()) {
				char current = fileScan.next().charAt(0);
				if (ALLOWED_CHARS.indexOf(current) != -1) { // makes sure inputted data is valid
					// runs through possible invalidites
					if (currentRow >= ROWS) {
						throw new InvalidFileFormatException("More data given then specified in dimensions");
					} else if (currentCol == COLS-1) { // at the end of the line, need to move to next line
						board[currentRow][currentCol] = current;
						if (current == START) {
							startingPoint = new Point(currentRow, currentCol);
							startCount++;
						} if (current == END) {
							endingPoint = new Point(currentRow, currentCol);
							endCount++;
						}
						currentCol = 0;
						currentRow++;
					} else { // inputs data to board
						board[currentRow][currentCol] = current;
						if (current == START) {
							startingPoint = new Point(currentRow, currentCol);
							startCount++;
						} if (current == END) {
							endingPoint = new Point(currentRow, currentCol);
							endCount++;
						}
						currentCol++;
					}
					
					filled++;
				} else {
					throw new InvalidFileFormatException("Data is not in the specified CHAR list");
				}
			}
			
			// checks to see if there are too few/little starting/endpoint points that needed
			if (startCount != 1 || endCount != 1) {
				throw new InvalidFileFormatException("Too few or many starting/ending points");
			}
			
			// checks to see if not all the board is filled out
			if (filled != ROWS * COLS) {
				throw new InvalidFileFormatException("Too few positions given to fill dimensions given");
			}
				
			// checks if there are more numbers that are outside of specified dimensions
			if (fileScan.hasNext()) {
				throw new InvalidFileFormatException("More numbers that are in the specified dimensions");
			} else {
				;
			}	
		} 
		
		finally {
			if (fileScan != null) {
				fileScan.close();
			}
		}
		
		fileScan.close();
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
