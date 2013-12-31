package gridBoardTest;

import static org.junit.Assert.*;
import gridBoard.*;

import org.junit.Before;
import org.junit.Test;

public class SquareSetTest {
	
	SquareSet set;
	Board b;
	
	@Before
	public void init(){
		set = new SquareSet();
		b = new Board(100,100);
	}
	
	@Test
	public void addElements(){
		for(int k = 0; k < 10; k++){
			for(int j = 0; j < 10; j++){
				set.add(b.getSquare(j, k));
			}
		}
		assertEquals(100,set.size());
	}
	
	@Test
	public void addSameElements(){
		set.add(b.getSquare(12,15));
		assertTrue(set.contains(b.getSquare(12,15)));
		
		set.add(b.getSquare(13,15));
		assertTrue(set.contains(b.getSquare(13,15)));
		
		assertEquals(2, set.size());
		set.add(b.getSquare(12,15));
		assertEquals(2, set.size());
		assertTrue(set.contains(b.getSquare(12,15)));
		assertTrue(set.contains(b.getSquare(13,15)));
	}
	
	@Test
	public void addNulls(){
		assertTrue(set.isEmpty());
		set.add(null);
		assertTrue(set.isEmpty());
		set.add(null);
		set.add(null);
		assertTrue(set.isEmpty());
	}
	

}
