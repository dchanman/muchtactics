package gridBoardTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gridBoard.*;

/**
 * Test cases for the Board class, checks that
 * the Square getters are correctly returning legal
 * and illegal squares, and that Adjacent squares and files
 * are received properly. 
 * @author Derek Chan
 * @version 1.0
 *
 */
public class BoardTest {
	
	Board B;
	
	@Before
	public void init(){
		/*
		 * new board 3 by 4
		 * [0,0][0,1][0,2][0,3]
		 * [1,0][1,1][1,2][1,3]
		 * [2,0][2,1][2,2][2,3]
		 */
		B = new Board(3,4);
	}
	
	@Test
	public void checkSquareGettersLegalSquares(){
		Square S;
		int row, col;
		
		//boundary case 0,0
		row = 0;
		col = 0;
		S = B.getSquare(row, col);
		assertEquals(col, S.getCol());
		assertEquals(row, S.getRow());
		
		//general case in center
		row = 1;
		col = 2;
		S = B.getSquare(row, col);
		assertEquals(col, S.getCol());
		assertEquals(row, S.getRow());
		
		//general case where row == col
		row = 1;
		col = 1;
		S = B.getSquare(row, col);
		assertEquals(col, S.getCol());
		assertEquals(row, S.getRow());
		
		//boundary case, farthest edge
		row = 2;
		col = 3;
		S = B.getSquare(row, col);
		assertEquals(col, S.getCol());
		assertEquals(row, S.getRow());		
		
		//boundary case, top right corner
		row = 0;
		col = 3;
		S = B.getSquare(row, col);
		assertEquals(col, S.getCol());
		assertEquals(row, S.getRow());	
		
		//boundary case, bottom right corner
		row = 2;
		col = 0;
		S = B.getSquare(row, col);
		assertEquals(col, S.getCol());
		assertEquals(row, S.getRow());	
	}

	@Test
	public void checkSquareGettersIllegalSquares(){
		int row, col;
		Square S;
		
		//top left invalid row
		row = -1;
		col = 0;
		S = B.getSquare(row, col);
		assertNull(S);
		
		//top right invalid col
		row = 0;
		col = -1;
		S = B.getSquare(row, col);
		assertNull(S);
		
		//top right both invalid
		row = -1;
		col = -1;
		S = B.getSquare(row, col);
		assertNull(S);
		
		//col is too far
		row = 1;
		col = 4;
		S = B.getSquare(row, col);
		assertNull(S);
		
		//row is too far
		row = 3;
		col = 2;
		S = B.getSquare(row, col);
		assertNull(S);
		
		//bottom right invalid col
		row = 2;
		col = 4;
		S = B.getSquare(row, col);
		assertNull(S);
		
		//bottom right invalid row
		row = 3;
		col = 3;
		S = B.getSquare(row, col);
		assertNull(S);
		
		//bottom right invalid both
		row = 3;
		col = 4;
		S = B.getSquare(row, col);
		assertNull(S);
	}

	@Test
	public void checkAdjacentSquares(){
		Square S, adj;
		
		//top left boundary
		S = B.getSquare(0, 0);
		//legal
		adj = B.getAdjacentSquare(Board.SQUARE_TO_RIGHT, S);
		assertEquals(B.getSquare(0, 1), adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_DOWN, S);
		assertEquals(B.getSquare(1, 0), adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_DOWNRIGHT, S);
		assertEquals(B.getSquare(1, 1), adj);
		//illegal
		adj = B.getAdjacentSquare(Board.SQUARE_TO_LEFT, S);
		assertNull(adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_DOWNLEFT, S);
		assertNull(adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_UPLEFT, S);
		assertNull(adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_UP, S);
		assertNull(adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_UPRIGHT, S);
		assertNull(adj);
		
		//bottom right boundary
		S = B.getSquare(2, 3);
		//legal
		adj = B.getAdjacentSquare(Board.SQUARE_TO_UP, S);	
		assertEquals(B.getSquare(1, 3), adj);		
		adj = B.getAdjacentSquare(Board.SQUARE_TO_UPLEFT, S);
		assertEquals(B.getSquare(1, 2), adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_LEFT, S);
		assertEquals(B.getSquare(2, 2), adj);
		//illegal
		adj = B.getAdjacentSquare(Board.SQUARE_TO_RIGHT, S);
		assertNull(adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_UPRIGHT, S);
		assertNull(adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_DOWNRIGHT, S);
		assertNull(adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_DOWN, S);
		assertNull(adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_DOWNLEFT, S);
		assertNull(adj);
		
		//square with only legal adjacent squares
		S = B.getSquare(1, 1);
		//legal
		adj = B.getAdjacentSquare(Board.SQUARE_TO_UP, S);	
		assertEquals(B.getSquare(0, 1), adj);		
		adj = B.getAdjacentSquare(Board.SQUARE_TO_UPLEFT, S);
		assertEquals(B.getSquare(0, 0), adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_LEFT, S);
		assertEquals(B.getSquare(1, 0), adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_RIGHT, S);
		assertEquals(B.getSquare(1, 2), adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_UPRIGHT, S);
		assertEquals(B.getSquare(0, 2), adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_DOWNRIGHT, S);
		assertEquals(B.getSquare(2, 2), adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_DOWN, S);
		assertEquals(B.getSquare(2, 1), adj);
		adj = B.getAdjacentSquare(Board.SQUARE_TO_DOWNLEFT, S);
		assertEquals(B.getSquare(2, 0), adj);
		
	}

	@Test
	public void checkFileGetters(){
		Square[] s;
		
		s = B.getColumn(0);
		assertEquals(B.getSquare(0, 0), s[0]);
		assertEquals(B.getSquare(1, 0), s[1]);
		assertEquals(B.getSquare(2, 0), s[2]);
		assertEquals(3, s.length);
				
		s = B.getColumn(2);
		assertEquals(B.getSquare(0, 2), s[0]);
		assertEquals(B.getSquare(1, 2), s[1]);
		assertEquals(B.getSquare(2, 2), s[2]);
		assertEquals(3, s.length);
		
		s = B.getColumn(3);
		assertEquals(B.getSquare(0, 3), s[0]);
		assertEquals(B.getSquare(1, 3), s[1]);
		assertEquals(B.getSquare(2, 3), s[2]);
		assertEquals(3, s.length);
		
		s = B.getRow(0);
		assertEquals(B.getSquare(0, 0), s[0]);
		assertEquals(B.getSquare(0, 1), s[1]);
		assertEquals(B.getSquare(0, 2), s[2]);
		assertEquals(B.getSquare(0, 3), s[3]);
		assertEquals(4, s.length);
		
		s = B.getRow(1);
		assertEquals(B.getSquare(1, 0), s[0]);
		assertEquals(B.getSquare(1, 1), s[1]);
		assertEquals(B.getSquare(1, 2), s[2]);
		assertEquals(B.getSquare(1, 3), s[3]);
		assertEquals(4, s.length);
		
		s = B.getRow(2);
		assertEquals(B.getSquare(2, 0), s[0]);
		assertEquals(B.getSquare(2, 1), s[1]);
		assertEquals(B.getSquare(2, 2), s[2]);
		assertEquals(B.getSquare(2, 3), s[3]);
		assertEquals(4, s.length);
		
		s = B.getColumn(-1);
		assertNull(s);
		
		s = B.getRow(-1);
		assertNull(s);
		
		s = B.getColumn(4);
		assertNull(s);
		
		s = B.getRow(3);
		assertNull(s);
	}
	
}
