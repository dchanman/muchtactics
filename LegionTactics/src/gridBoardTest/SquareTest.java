package gridBoardTest;

import static org.junit.Assert.*;
import gridBoard.*;

import org.junit.Before;
import org.junit.Test;

public class SquareTest {
	
	Board b;
	Square s1, s2, s3;
	
	@Before
	public void init(){
		b = new Board(4,4);
	}
	
	@Test
	public void testEquality(){
		s1 = b.getSquare(1, 1);
		s2 = b.getSquare(1, 1);
		s3 = b.getSquare(1, 1);		
		assertEquals(s1, s2);
		assertEquals(s1, s3);
		assertEquals(s3, s2);
		assertEquals(s1, s1);
		
		s1 = b.getSquare(1, 2);
		s2 = b.getSquare(2, 1);
		assertNotSame(s1,s2);
		
		
	}

}
