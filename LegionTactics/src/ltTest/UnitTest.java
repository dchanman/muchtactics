package ltTest;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import gridBoard.Board;
import gridBoard.Square;
import gridBoard.SquareSet;
import lt.units.Path;
import lt.units.Unit;

public class UnitTest {
	
	Board b;
	TestUnit TU;
	
	@Before
	public void init(){
		b = new Board(3, 4);
		TU = new TestUnit(b, Unit.TEAM_ONE);
	}

	@Test
	public void checkBlockPercentageCalculation() {
		// check UP direction
		TU.setOrientation(Unit.ORIENTATION_UP);
		TU.setBlockrate(80);
		// same direction, 0% chance of blocking (back attack)
		assertEquals(0, TU.calculateBlockPercentageTest(Unit.ORIENTATION_UP));
		// side attack, 40% chance of blocking
		assertEquals(40, TU.calculateBlockPercentageTest(Unit.ORIENTATION_LEFT));
		assertEquals(40, TU.calculateBlockPercentageTest(Unit.ORIENTATION_RIGHT));
		// frontal attack, 80% chance of blocking
		assertEquals(80, TU.calculateBlockPercentageTest(Unit.ORIENTATION_DOWN));

		// check LEFT direction
		TU.setOrientation(Unit.ORIENTATION_LEFT);
		TU.setBlockrate(80);
		// same direction, 0% chance of blocking (back attack)
		assertEquals(0, TU.calculateBlockPercentageTest(Unit.ORIENTATION_LEFT));		
		// side attack, 40% chance of blocking
		assertEquals(40, TU.calculateBlockPercentageTest(Unit.ORIENTATION_UP));
		assertEquals(40, TU.calculateBlockPercentageTest(Unit.ORIENTATION_DOWN));
		// frontal attack, 80% chance of blocking
		assertEquals(80, TU.calculateBlockPercentageTest(Unit.ORIENTATION_RIGHT));

		// check DOWN direction
		TU.setOrientation(Unit.ORIENTATION_DOWN);
		TU.setBlockrate(80);
		// same direction, 0% chance of blocking (back attack)
		assertEquals(0, TU.calculateBlockPercentageTest(Unit.ORIENTATION_DOWN));
		// side attack, 40% chance of blocking
		assertEquals(40, TU.calculateBlockPercentageTest(Unit.ORIENTATION_LEFT));
		assertEquals(40, TU.calculateBlockPercentageTest(Unit.ORIENTATION_RIGHT));
		// frontal attack, 80% chance of blocking
		assertEquals(80, TU.calculateBlockPercentageTest(Unit.ORIENTATION_UP));

		// check UP direction
		TU.setOrientation(Unit.ORIENTATION_RIGHT);
		TU.setBlockrate(80);
		// same direction, 0% chance of blocking (back attack)
		assertEquals(0, TU.calculateBlockPercentageTest(Unit.ORIENTATION_RIGHT));		
		// side attack, 40% chance of blocking
		assertEquals(40, TU.calculateBlockPercentageTest(Unit.ORIENTATION_UP));
		assertEquals(40, TU.calculateBlockPercentageTest(Unit.ORIENTATION_DOWN));
		// frontal attack, 80% chance of blocking
		assertEquals(80, TU.calculateBlockPercentageTest(Unit.ORIENTATION_LEFT));
	}
	
	@Test
	public void checkDamageCalculation(){
		TU.setHP(100);
		TU.setOrientation(Unit.ORIENTATION_DOWN);
		TU.receiveDamage(29, Unit.ORIENTATION_DOWN);
		
		assertEquals(100-29,TU.getHP());	
	}
	
	@Test
	public void checkActive(){
		TU.setActive(true);
		//TU is on TEAM_ONE
		assertTrue(TU.isActive(Unit.TEAM_ONE));
		assertFalse(TU.isActive(Unit.TEAM_TWO));
		
		TU.setActive(false);
		assertFalse(TU.isActive(Unit.TEAM_ONE));
		assertFalse(TU.isActive(Unit.TEAM_TWO));
	}
	
	@Test
	public void displayAttackChanceDistribution(){
		TU.setBlockrate(80);
		TU.setOrientation(Unit.ORIENTATION_DOWN);
		int[] results = new int[1000];
		
		//Down vs Down attacks, the Test Unit should be left with 100% success
		int count;
		for(int k = 0; k < results.length; k++){
			count = 0;
			for(int j = 0; j < 100; j++){
				if(TU.receiveDamage(1, Unit.ORIENTATION_DOWN))
					count++;
			}			
			results[k] = count;
		}
		System.out.println("Attacking from behind:");
		printDistributionAnalysis(results, 10, 100);
		
		//Left vs Down attacks, the Test Unit should be left with a distribution peaking at around 60.
		for(int k = 0; k < results.length; k++){
			count = 0;
			for(int j = 0; j < 100; j++){
				if(TU.receiveDamage(1, Unit.ORIENTATION_LEFT))
					count++;
			}
			if (count > 100)
				fail();
			results[k] = count;
		}
		System.out.println("Attacking from left:");
		printDistributionAnalysis(results, 10, 100);
		
		//Right vs Down attacks, the Test Unit should be left with a distribution peaking at around 60.
		for(int k = 0; k < results.length; k++){
			count = 0;
			for(int j = 0; j < 100; j++){
				if(TU.receiveDamage(1, Unit.ORIENTATION_RIGHT))
					count++;
			}
			results[k] = count;
		}
		System.out.println("Attacking from right:");
		printDistributionAnalysis(results, 10, 100);
		
		//Up vs Down attacks, the Test Unit should be left with a distribution peaking at around 40.
		for(int k = 0; k < results.length; k++){
			count = 0;
			for(int j = 0; j < 100; j++){
				if(TU.receiveDamage(1, Unit.ORIENTATION_UP))
					count++;
			}
			results[k] = count;
		}
		System.out.println("Attacking from front:");
		printDistributionAnalysis(results, 10, 100);
		
	}
	
	@Test
	public void testChangeOrientationTowardsOtherUnit(){
		/*
		 * Recall that the board is arbitraily 5x6:
		 * [0,0][0,1][0,2][0,3]
		 * [1,0][1,1][1,2][1,3]
		 * [2,0][2,1][2,2][2,3]
		 */
		TestUnit other = new TestUnit(b, Unit.TEAM_TWO);
		
		//test orthogonal directions
		TU.moveToSquare(b.getSquare(1, 1));
		other.moveToSquare(b.getSquare(0, 1));
		assertEquals(Unit.ORIENTATION_UP, TU.changeOrientationTowards(other));
		
		TU.moveToSquare(b.getSquare(1, 1));
		other.moveToSquare(b.getSquare(2, 1));
		assertEquals(Unit.ORIENTATION_DOWN, TU.changeOrientationTowards(other));
		
		TU.moveToSquare(b.getSquare(1, 1));
		other.moveToSquare(b.getSquare(1, 0));
		assertEquals(Unit.ORIENTATION_LEFT, TU.changeOrientationTowards(other));
		
		TU.moveToSquare(b.getSquare(1, 1));
		other.moveToSquare(b.getSquare(1, 2));
		assertEquals(Unit.ORIENTATION_RIGHT, TU.changeOrientationTowards(other));
		
		//test long diagonal directions
		TU.moveToSquare(b.getSquare(1, 1));
		other.moveToSquare(b.getSquare(0, 3));
		assertEquals(Unit.ORIENTATION_RIGHT, TU.changeOrientationTowards(other));
		TU.moveToSquare(b.getSquare(1, 1));
		other.moveToSquare(b.getSquare(2, 3));
		assertEquals(Unit.ORIENTATION_RIGHT, TU.changeOrientationTowards(other));
		
		TU.moveToSquare(b.getSquare(1, 2));
		other.moveToSquare(b.getSquare(0, 0));
		assertEquals(Unit.ORIENTATION_LEFT, TU.changeOrientationTowards(other));
		TU.moveToSquare(b.getSquare(1, 2));
		other.moveToSquare(b.getSquare(2, 0));
		assertEquals(Unit.ORIENTATION_LEFT, TU.changeOrientationTowards(other));
		
		TU.moveToSquare(b.getSquare(2, 1));
		other.moveToSquare(b.getSquare(0, 0));
		assertEquals(Unit.ORIENTATION_UP, TU.changeOrientationTowards(other));
		TU.moveToSquare(b.getSquare(2, 1));
		other.moveToSquare(b.getSquare(0, 2));
		assertEquals(Unit.ORIENTATION_UP, TU.changeOrientationTowards(other));
		
		TU.moveToSquare(b.getSquare(0, 1));
		other.moveToSquare(b.getSquare(2, 0));
		assertEquals(Unit.ORIENTATION_DOWN, TU.changeOrientationTowards(other));
		TU.moveToSquare(b.getSquare(0, 1));
		other.moveToSquare(b.getSquare(2, 2));
		assertEquals(Unit.ORIENTATION_DOWN, TU.changeOrientationTowards(other));
		
		//test equidistant angles
		TU.moveToSquare(b.getSquare(1, 1));
		other.moveToSquare(b.getSquare(2, 0));
		assertEquals(Unit.ORIENTATION_DOWN, TU.changeOrientationTowards(other));
		TU.moveToSquare(b.getSquare(1, 1));
		other.moveToSquare(b.getSquare(2, 2));
		assertEquals(Unit.ORIENTATION_DOWN, TU.changeOrientationTowards(other));
		TU.moveToSquare(b.getSquare(1, 1));
		other.moveToSquare(b.getSquare(0, 0));
		assertEquals(Unit.ORIENTATION_UP, TU.changeOrientationTowards(other));
		TU.moveToSquare(b.getSquare(1, 1));
		other.moveToSquare(b.getSquare(0, 2));
		assertEquals(Unit.ORIENTATION_UP, TU.changeOrientationTowards(other));

		
	}
	
	@Test
	public void testBasicPathing(){
		//we'll need a bigger board
		b = new Board(7,7);
		
		TestUnit main_character = new TestUnit(b, Unit.TEAM_ONE);
		main_character.setStatic(false);
		main_character.setMovement(3);
		TestUnit a_friend = new TestUnit(b, Unit.TEAM_ONE);
		a_friend.setStatic(false);
		TestUnit a_friendly_structure = new TestUnit(b, Unit.TEAM_ONE);
		a_friendly_structure.setStatic(true);
		TestUnit an_enemy = new TestUnit(b, Unit.TEAM_TWO);
		an_enemy.setStatic(false);
		TestUnit an_enemy_structure = new TestUnit(b, Unit.TEAM_TWO);
		an_enemy_structure.setStatic(true);
		
		main_character.moveToSquare(b.getSquare(3, 3));
		ArrayList<Path> paths = new ArrayList<Path>(main_character.getLegalPaths());
		ArrayList<Square> destinations = new ArrayList<Square>();
		for(Path p: paths){
			destinations.add(p.destination());
			//System.out.println(p.destination().toString());
		}		
		
		
		
		assertTrue(destinations.contains(b.getSquare(0, 3)));
		assertTrue(destinations.contains(b.getSquare(1, 3)));
		assertTrue(destinations.contains(b.getSquare(2, 3)));
		assertFalse(destinations.contains(b.getSquare(3, 3))); //this square is occupied by itself
		assertTrue(destinations.contains(b.getSquare(4, 3)));
		assertTrue(destinations.contains(b.getSquare(5, 3)));
		assertTrue(destinations.contains(b.getSquare(6, 3)));
		
		assertTrue(destinations.contains(b.getSquare(1, 2)));
		assertTrue(destinations.contains(b.getSquare(2, 2)));
		assertTrue(destinations.contains(b.getSquare(3, 2)));
		assertTrue(destinations.contains(b.getSquare(4, 2)));
		assertTrue(destinations.contains(b.getSquare(5, 2)));
		
		assertTrue(destinations.contains(b.getSquare(1, 4)));
		assertTrue(destinations.contains(b.getSquare(2, 4)));
		assertTrue(destinations.contains(b.getSquare(3, 4)));
		assertTrue(destinations.contains(b.getSquare(4, 4)));
		assertTrue(destinations.contains(b.getSquare(5, 4)));
		
		assertTrue(destinations.contains(b.getSquare(2, 1)));
		assertTrue(destinations.contains(b.getSquare(3, 1)));
		assertTrue(destinations.contains(b.getSquare(4, 1)));
		
		assertTrue(destinations.contains(b.getSquare(2, 5)));
		assertTrue(destinations.contains(b.getSquare(3, 5)));
		assertTrue(destinations.contains(b.getSquare(4, 5)));
		
		assertTrue(destinations.contains(b.getSquare(3, 0)));
		
		assertTrue(destinations.contains(b.getSquare(3, 6)));
		
		assertEquals(24, destinations.size());
	}

	@Test
	public void testPathingWithFriendlyObstacles(){
		//we'll need a bigger board
				b = new Board(7,7);
				
				TestUnit main_character = new TestUnit(b, Unit.TEAM_ONE);
				main_character.setStatic(false);
				main_character.setMovement(3);
				TestUnit a_friend = new TestUnit(b, Unit.TEAM_ONE);
				a_friend.setStatic(false);
				TestUnit a_friend2 = new TestUnit(b, Unit.TEAM_ONE);
				a_friend.setStatic(false);
				TestUnit a_friend3 = new TestUnit(b, Unit.TEAM_ONE);
				a_friend.setStatic(false);

				
				main_character.moveToSquare(b.getSquare(3, 3));
				a_friend.moveToSquare(b.getSquare(3, 2));
				a_friend2.moveToSquare(b.getSquare(3, 6));
				a_friend3.moveToSquare(b.getSquare(5, 3));
				ArrayList<Path> paths = new ArrayList<Path>(main_character.getLegalPaths());
				ArrayList<Square> destinations = new ArrayList<Square>();
				for(Path p: paths){
					destinations.add(p.destination());
					//System.out.println(p.destination().toString());
				}		
				
				
				
				assertTrue(destinations.contains(b.getSquare(0, 3)));
				assertTrue(destinations.contains(b.getSquare(1, 3)));
				assertTrue(destinations.contains(b.getSquare(2, 3)));
				assertFalse(destinations.contains(b.getSquare(3, 3))); //this square is occupied by itself
				assertTrue(destinations.contains(b.getSquare(4, 3)));
				//assertTrue(destinations.contains(b.getSquare(5, 3)));
				assertTrue(destinations.contains(b.getSquare(6, 3)));
				
				assertTrue(destinations.contains(b.getSquare(1, 2)));
				assertTrue(destinations.contains(b.getSquare(2, 2)));
				assertFalse(destinations.contains(b.getSquare(3, 2)));
				assertTrue(destinations.contains(b.getSquare(4, 2)));
				assertTrue(destinations.contains(b.getSquare(5, 2)));
				
				assertTrue(destinations.contains(b.getSquare(1, 4)));
				assertTrue(destinations.contains(b.getSquare(2, 4)));
				assertTrue(destinations.contains(b.getSquare(3, 4)));
				assertTrue(destinations.contains(b.getSquare(4, 4)));
				assertTrue(destinations.contains(b.getSquare(5, 4)));
				
				assertTrue(destinations.contains(b.getSquare(2, 1)));
				assertTrue(destinations.contains(b.getSquare(3, 1)));
				assertTrue(destinations.contains(b.getSquare(4, 1)));
				
				assertTrue(destinations.contains(b.getSquare(2, 5)));
				assertTrue(destinations.contains(b.getSquare(3, 5)));
				assertTrue(destinations.contains(b.getSquare(4, 5)));
				
				assertTrue(destinations.contains(b.getSquare(3, 0)));
				
				assertFalse(destinations.contains(b.getSquare(3, 6)));
				
				assertEquals(21, destinations.size());
	}
	
	@Test
	public void testPathingWithMixedStaticObstacles(){
		//we'll need a bigger board
				b = new Board(7,7);
				
				TestUnit main_character = new TestUnit(b, Unit.TEAM_ONE);
				main_character.setStatic(false);
				main_character.setMovement(3);
				TestUnit a_friendly_structure = new TestUnit(b, Unit.TEAM_ONE);
				a_friendly_structure.setStatic(true);
				TestUnit a_friendly_structure2 = new TestUnit(b, Unit.TEAM_ONE);
				a_friendly_structure2.setStatic(true);				
				TestUnit an_enemy_structure = new TestUnit(b, Unit.TEAM_TWO);
				an_enemy_structure.setStatic(true);
				TestUnit an_enemy_structure2 = new TestUnit(b, Unit.TEAM_TWO);
				an_enemy_structure2.setStatic(true);
				
				main_character.moveToSquare(b.getSquare(3, 3));
				a_friendly_structure.moveToSquare(b.getSquare(3, 2));
				a_friendly_structure2.moveToSquare(b.getSquare(3, 6));
				an_enemy_structure.moveToSquare(b.getSquare(5, 3));
				an_enemy_structure2.moveToSquare(b.getSquare(2, 4));
				ArrayList<Path> paths = new ArrayList<Path>(main_character.getLegalPaths());
				ArrayList<Square> destinations = new ArrayList<Square>();
				for(Path p: paths){
					destinations.add(p.destination());
					//System.out.println(p.destination().toString());
				}		
				
				
				
				assertTrue(destinations.contains(b.getSquare(0, 3)));
				assertTrue(destinations.contains(b.getSquare(1, 3)));
				assertTrue(destinations.contains(b.getSquare(2, 3)));
				assertFalse(destinations.contains(b.getSquare(3, 3))); //this square is occupied by itself
				assertTrue(destinations.contains(b.getSquare(4, 3)));
				assertFalse(destinations.contains(b.getSquare(5, 3)));
				assertFalse(destinations.contains(b.getSquare(6, 3)));
				
				assertTrue(destinations.contains(b.getSquare(1, 2)));
				assertTrue(destinations.contains(b.getSquare(2, 2)));
				assertFalse(destinations.contains(b.getSquare(3, 2)));
				assertTrue(destinations.contains(b.getSquare(4, 2)));
				assertTrue(destinations.contains(b.getSquare(5, 2)));
				
				assertTrue(destinations.contains(b.getSquare(1, 4)));
				assertFalse(destinations.contains(b.getSquare(2, 4)));
				assertTrue(destinations.contains(b.getSquare(3, 4)));
				assertTrue(destinations.contains(b.getSquare(4, 4)));
				assertTrue(destinations.contains(b.getSquare(5, 4)));
				
				assertTrue(destinations.contains(b.getSquare(2, 1)));
				assertFalse(destinations.contains(b.getSquare(3, 1)));
				assertTrue(destinations.contains(b.getSquare(4, 1)));
				
				assertTrue(destinations.contains(b.getSquare(2, 5)));
				assertTrue(destinations.contains(b.getSquare(3, 5)));
				assertTrue(destinations.contains(b.getSquare(4, 5)));
				
				assertFalse(destinations.contains(b.getSquare(3, 0)));
				
				assertFalse(destinations.contains(b.getSquare(3, 6)));
				
				assertEquals(17, destinations.size());
	}
	
	//@Test
	public void checkPrintDistributionAnalysisHelperFunction(){
		int[] A = {1,0,1,1,1,1,
				2,2,
				3,3,3,
				4,4,4,4,4,
				5,5,5};
		System.out.println("Check the PrintDistributionAnalysis Function");
		printDistributionAnalysis(A, 1, 5);
		System.out.println();
	}
	
	/**
	 * Helper function that will print via System.out the distribution of an array
	 * @param results - the results array to be processed
	 * @param rangeSize - the desired size of each interval
	 * @param maxValue - the largest possible value in the results array
	 */
	private void printDistributionAnalysis(int[] results, int rangeSize, int maxValue){
		int range = maxValue/rangeSize;
		int[] distribution = new int[range+1];
		
		for(int k = 0; k < results.length; k++){
			if(results[k]/rangeSize >= results.length){
				System.out.println("results[k]: " + results[k]);
				System.out.println("rangeSize: " + rangeSize);
				System.out.println("results.length: " + results.length);
				fail();
			}
			distribution[results[k]/rangeSize]++;
		}
		
		System.out.println("Distribution results:");		
		for(int k = 0; k < distribution.length; k++){
			System.out.println("["+rangeSize*k+"+] : " + distribution[k]);
		}
		
		System.out.println();
	}
	
	private class TestUnit extends Unit {

		public TestUnit(Board board, int team) {
			super(board, team);
		}

		@Override
		public SquareSet getLegalMoves() {
			return null;
		}

		public int calculateBlockPercentageTest(int orientation) {
			return calculateBlockPercentage(orientation);
		}

		public void setBlockrate(int blockrate) {
			this.blockrate = blockrate;
		}

		public void setOrientation(int orientation) {
			this.orientation = orientation;
		}
		
		public int getHP(){
			return this.hp;
		}
		
		public void setHP(int hp){
			this.hp = hp;
		}
		
		public void setActive(boolean active){
			this.active = active;
		}
		
		public void setMovement(int movement){
			this.movement = movement;
		}
		
		public void setStatic(boolean is_static){
			this.is_static = is_static;
		}
	} 

}
