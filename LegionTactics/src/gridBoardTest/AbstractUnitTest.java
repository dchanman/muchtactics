package gridBoardTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gridBoard.*;

/**
 * Test the AbstractUnit class and its basic functionality
 * such as placing units, moving units, getting adjacent Squares
 * and finding basic legal moves.
 * @author Derek Chan
 * @version 1.0
 *
 */
public class AbstractUnitTest {
	
	Board b;
	TestUnit u1, u2, u3, u4;
	
	@Before
	public void init(){
		b = new Board(3,4);
		u1 = new TestUnit(b, 1);
		u2 = new TestUnit(b, 2);
		u3 = new TestUnit(b, 3);
		u4 = new TestUnit(b, 4);
	}
	
	@Test
	public void testPlacingUnitsOnBoard(){
		//place a unit on arbitrary square
		b.getSquare(1, 1).setUnit(u1);
		assertEquals(u1,b.getSquare(1, 1).getUnit());
		
		//place unit on bottomrightmost corner
		b.getSquare(2, 3).setUnit(u2);
		assertEquals(u2,b.getSquare(2, 3).getUnit());
		
		//place unit on topleftmost corner
		b.getSquare(0, 0).setUnit(u3);
		assertEquals(u3,b.getSquare(0, 0).getUnit());
		
		//place a unit on an occupied square
		AbstractUnit u = b.getSquare(0, 0).setUnit(u4);
		assertEquals(u4,b.getSquare(0, 0).getUnit());
		assertEquals(u, u3);
		
		//place unit on another occupied square
		u = b.getSquare(1, 1).setUnit(u);
		assertEquals(u3,b.getSquare(1, 1).getUnit());
		assertEquals(u, u1);
	}
	
	@Test
	public void testMovingUnitsOnBoard() {
		// place a unit on arbitrary square
		b.getSquare(1, 1).setUnit(u1);
		assertEquals(u1, b.getSquare(1, 1).getUnit());

		// move to another square
		u1.moveToSquare(b.getSquare(1, 3));
		assertNull(b.getSquare(1, 1).getUnit());
		assertEquals(b.getSquare(1, 3), u1.getSquare());

		// move to another square
		u1.moveToSquare(b.getSquare(0, 0));
		assertNull(b.getSquare(1, 3).getUnit());
		assertEquals(b.getSquare(0, 0), u1.getSquare());

		// move to another square
		u1.moveToSquare(b.getSquare(2, 3));
		assertNull(b.getSquare(0, 0).getUnit());
		assertEquals(b.getSquare(2, 3), u1.getSquare());
		
		// place a unit on arbitrary square
		b.getSquare(1, 1).setUnit(u2);
		assertEquals(u2, b.getSquare(1, 1).getUnit());
		
		//move a unit onto an occupied square
		AbstractUnit u = u1.moveToSquare(u2.getSquare());
		assertEquals(u, u2);
		assertEquals(u1, b.getSquare(1,1).getUnit());
		
	}
	
	@Test
	public void testAdjacentSquares(){
		b.getSquare(1, 1).setUnit(u1);
		assertEquals(b.getAdjacentSquare(Board.SQUARE_TO_DOWN, b.getSquare(1,1)),u1.getAdjacentSquare(Board.SQUARE_TO_DOWN));
		assertEquals(b.getAdjacentSquare(Board.SQUARE_TO_UP, b.getSquare(1,1)),u1.getAdjacentSquare(Board.SQUARE_TO_UP));
		assertEquals(b.getAdjacentSquare(Board.SQUARE_TO_LEFT, b.getSquare(1,1)),u1.getAdjacentSquare(Board.SQUARE_TO_LEFT));
		assertEquals(b.getAdjacentSquare(Board.SQUARE_TO_RIGHT, b.getSquare(1,1)),u1.getAdjacentSquare(Board.SQUARE_TO_RIGHT));
		assertEquals(b.getAdjacentSquare(Board.SQUARE_TO_UPRIGHT, b.getSquare(1,1)),u1.getAdjacentSquare(Board.SQUARE_TO_UPRIGHT));
	}
	
	@Test
	public void testBasicLegalMoves(){
		//top left corner
		b.getSquare(0, 0).setUnit(u1);
		assertTrue(u1.getLegalMoves().contains(b.getSquare(0, 1)));
		assertTrue(u1.getLegalMoves().contains(b.getSquare(1, 0)));
		assertEquals(2, u1.getLegalMoves().size());
		
		//bottom right
		b.getSquare(2, 3).setUnit(u1);		
		assertTrue(u1.getLegalMoves().contains(b.getSquare(2, 2)));
		assertTrue(u1.getLegalMoves().contains(b.getSquare(1, 3)));
		assertEquals(2, u1.getLegalMoves().size());
		
		//center
		b.getSquare(1, 1).setUnit(u1);		
		assertTrue(u1.getLegalMoves().contains(b.getSquare(2, 1)));
		assertTrue(u1.getLegalMoves().contains(b.getSquare(1, 2)));
		assertTrue(u1.getLegalMoves().contains(b.getSquare(1, 0)));
		assertTrue(u1.getLegalMoves().contains(b.getSquare(0, 1)));
		assertEquals(4, u1.getLegalMoves().size());
		
		//bottom side
		b.getSquare(2, 2).setUnit(u1);		
		assertTrue(u1.getLegalMoves().contains(b.getSquare(2, 1)));
		assertTrue(u1.getLegalMoves().contains(b.getSquare(1, 2)));
		assertTrue(u1.getLegalMoves().contains(b.getSquare(2, 3)));
		assertEquals(3, u1.getLegalMoves().size());
		
		//right side
		b.getSquare(1, 3).setUnit(u1);		
		assertTrue(u1.getLegalMoves().contains(b.getSquare(1, 2)));
		assertTrue(u1.getLegalMoves().contains(b.getSquare(0, 3)));
		assertTrue(u1.getLegalMoves().contains(b.getSquare(2, 3)));
		assertEquals(3, u1.getLegalMoves().size());
	}
	
	private class TestUnit extends AbstractUnit{
		
		private final int id;
		
		/**
		 * Test unit, ID field to check for uniqueness for the unit.
		 * @param board
		 * @param id
		 */
		public TestUnit(Board board, int id) {
			super(board);
			this.id = id;
		}

		@Override
		public SquareSet getLegalMoves() {
			SquareSet s = new SquareSet();
			s.add(getAdjacentSquare(Board.SQUARE_TO_DOWN));
			s.add(getAdjacentSquare(Board.SQUARE_TO_UP));
			s.add(getAdjacentSquare(Board.SQUARE_TO_LEFT));
			s.add(getAdjacentSquare(Board.SQUARE_TO_RIGHT));
			return s;			
		}

		public int getId() {
			return id;
		}	
		
	}
	
}
