package gridBoard;

import java.util.ArrayList;

/**
 * Board object which will contain all the Squares
 * and Units that will be used in a game. Also provides
 * basic directional and relative Square methods that
 * can be used.
 * @author Derek Chan
 * @version 1.0
 */
public class Board {
	
	/**
	 * Stored as board[row][col].
	 */
	protected Square[][] board;
	protected ArrayList<AbstractUnit> units;
	
	/**
	 * Adjacent square to the left
	 */
	public final static int SQUARE_TO_LEFT = 0;
	/**
	 * Adjacent square to the top left
	 */
	public final static int SQUARE_TO_UPLEFT = 1;
	/**
	 * Adjacent square above
	 */
	public final static int SQUARE_TO_UP = 2;
	/**
	 * Adjacent square to the top right
	 */
	public final static int SQUARE_TO_UPRIGHT = 3;
	/**
	 * Adjacent square to the right
	 */
	public final static int SQUARE_TO_RIGHT = 4;
	/**
	 * Adjacent square to the bottom right
	 */
	public final static int SQUARE_TO_DOWNRIGHT = 5;
	/**
	 * Adjacent square below
	 */
	public final static int SQUARE_TO_DOWN = 6;
	/**
	 * Adjacent square to the bottom left
	 */
	public final static int SQUARE_TO_DOWNLEFT = 7;
	
	/**
	 * Creates a new empty board with the given dimensions
	 * @param numRows - the number of rows on the board 
	 * @param numCols - the number of columns on the board
	 */
	public Board(int numRows, int numCols){
		units = new ArrayList<AbstractUnit>();
		/*
		 * Instantiate all the squares in our board
		 */
		board = new Square[numRows][numCols];
		for(int k = 0; k < numRows; k++){
			for(int j = 0; j < numCols; j++){
				board[k][j] = new Square(k,j);
			}
		}
	}
	
	/**
	 * Gets the square at a given row-col coordinate
	 * @param numRow - the row of the square
	 * @param numCol - the column of the square
	 * @return the square at the coordinate, null if the square is not on the board
	 */
	public Square getSquare(int numRow, int numCol){
		if (squareCoordinatesIsOnBoard(numRow, numCol))
			return board[numRow][numCol];
		return null;
	}
	
	/**
	 * @param numRow - must be a valid row on the board
	 * @return - an array of all the squares in the row, null if the row is not on the board
	 */
	public Square[] getRow(int numRow){
		if (squareCoordinatesIsOnBoard(numRow, 0))
			return board[numRow];		
		return null;
	}
	
	/**
	 * @param numCol - must be a valid column on the board
	 * @return - an array of all the squares in the column, null if the row is not on the board
	 */
	public Square[] getColumn(int numCol){
		if (squareCoordinatesIsOnBoard(0, numCol)){
			Square[] col = new Square[board.length];
			for(int k = 0; k < col.length; k++){
				col[k] = board[k][numCol];
			}
			return col;
		}
		return null;
	}

	/**
	 * Returns an adjacent square in the direction provided. Use the
	 * integer directional definitions provided in this class
	 * (Board.SQUARE_TO_LEFT, Board.SQUARE_TO_UP, Board.SQUARE_TO_RIGHT, Board.SQUARE_TO_DOWN,
	 * Board.SQUARE_TO_TOPLEFT, Board.SQUARE_TO_TOPRIGHT, Board.SQUARE_TO_DOWNLEFT, Board.SQUARE_TO_DOWNRIGHT) 
	 * @param direction - a direction
	 * @param square - a reference square
	 * @return - the adjacent square in the direction given to the reference square,
	 * 			  null is returned if the square is off the board.
	 */
	public Square getAdjacentSquare(int direction, Square square){
		int row = square.row;
		int col = square.col;
		
		switch (direction){
			case SQUARE_TO_LEFT: 
				col--;
				break;
			case SQUARE_TO_UPLEFT:
				row--;
				col--;
				break;
			case SQUARE_TO_UP:
				row--;
				break;
			case SQUARE_TO_UPRIGHT:
				row--;
				col++;
				break;
			case SQUARE_TO_RIGHT:
				col++;
				break;
			case SQUARE_TO_DOWNRIGHT:
				row++;
				col++;
				break;
			case SQUARE_TO_DOWN:
				row++;
				break;
			case SQUARE_TO_DOWNLEFT:
				row++;
				col--;
				break;			
		}
		
		if (squareCoordinatesIsOnBoard(row, col)){
			return board[row][col];
		}
		else{
			return null;
		}
	}
	
	/**
	 * Checks if a given Square coordinate is actually on the board.
	 * @param row - the row of the coordinate
	 * @param col - the column of the coordinate
	 * @return true if the coordinate is on the board
	 */
	protected boolean squareCoordinatesIsOnBoard(int row, int col){
		if((row >= board.length)
				||(col >= board[0].length)
				||(row < 0 )
				||(col < 0 )){
			return false;
		}
		return true;
	}

}
