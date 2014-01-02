package lt.units;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import gridBoard.AbstractUnit;
import gridBoard.Board;
import gridBoard.Square;
import gridBoard.SquareSet;

public abstract class Unit extends AbstractUnit{

	/**
	 * The unit belongs to Team 1
	 */
	public final static int TEAM_ONE = 1;
	/**
	 * The unit belongs to Team 2
	 */
	public final static int TEAM_TWO = 2;
	
	/**
	 * Facing to the right
	 */
	public final static int ORIENTATION_RIGHT = 1;
	/**
	 * Facing to the top
	 */
	public final static int ORIENTATION_UP = 2;
	/**
	 * Facing to the left
	 */
	public final static int ORIENTATION_LEFT = 3;
	/**
	 * Facing to the bottom
	 */
	public final static int ORIENTATION_DOWN = 4;
	
	/**
	 * The team that this unit belongs to.
	 */
	protected int team;
	/**
	 * The maximum health this unit has.
	 */
	protected int max_hp;
	/**
	 * The health this unit has.
	 */
	protected int hp;
	/**
	 * The power of this unit
	 */
	protected int power;
	/**
	 * The blocking chance of this unit
	 */
	protected int blockrate;
	/**
	 * The movement of this unit
	 */
	protected int movement;
	/**
	 * Orientation of this unit
	 */
	protected int orientation;
	/**
	 * If this unit is active or inactive
	 */
	protected boolean active;
	/**
	 * If this unit will move out of the way for other units to pass
	 */
	protected boolean is_static;
	
	private static Random random;
	
	/**
	 * Creates a new Unit with a team affiliation.
	 * @param board - the board this unit will be used with
	 * @param team - the team this unit is affiliated with
	 */
	public Unit(Board board, int team) {
		super(board);
		random = new Random();
		this.team = team;
		orientation = ORIENTATION_DOWN;
	}
	
	/**
	 * Causes this unit to receive damage. Blocking is applied here.
	 * @param damage - the amount of damage dealt to this unit
	 * @return - true if the attack succeeded, false if it was blocked
	 */
	public boolean receiveDamage(int damage, int orientation_of_attacker){
		int chance_of_attack = calculateBlockPercentage(orientation_of_attacker);
		
		//successful attack
		if (random.nextInt(100) >= chance_of_attack){
			this.hp -= damage;
			return true;
		}
		
		//unsuccessful attack
		return false;
	}
	
	/**
	 * Provides the calculated block percentage depending on the angle of
	 * attack and the orientation of the this unit.
	 * @param orientation_of_attacker - the orientation of the attacker
	 * @return - the percentage chance of a block occurring
	 */
	protected int calculateBlockPercentage(int orientation_of_attacker){
		int calculated_rate;
		int orientationDifference = Math.abs(this.orientation - orientation_of_attacker);
		
		switch(orientationDifference){
			//head to head attack
			case 2: calculated_rate = this.blockrate;
			break;
			//side attack
			case 1: calculated_rate = this.blockrate/2;
			break;
			//back attack
			case 0: calculated_rate = 0;
			break;
			//side attack
			case 3: calculated_rate = this.blockrate/2;
			break;
			default: calculated_rate = 100;
		}
		
		return calculated_rate;
	}
	
	/**
	 * Changes this unit's orientation towards another unit
	 * @param other - the other unit this unit will face
	 * @return - this unit's new orientation
	 */
	public int changeOrientationTowards(Unit other){
		int newOrientation = this.orientation;
		Square otherSquare = other.getSquare();
		//dx will the the number of columns between the two units
		int dx = otherSquare.getCol() - this.square.getCol();
		//dy will the the number of rows between the two units
		int dy = otherSquare.getRow() - this.square.getRow();
		//if the unit we will face is farther in the x direction, we will face left or right
		if (Math.abs(dx) > Math.abs(dy)){
			if(dx > 0){
				newOrientation = Unit.ORIENTATION_RIGHT;
			}
			else{
				newOrientation = Unit.ORIENTATION_LEFT;
			}
		}
		//note that if the other unit is eqidistant in the x and y directions, we will face up or down
		else{
			if (dy > 0){
				newOrientation = Unit.ORIENTATION_DOWN;
			}
			else{
				newOrientation = Unit.ORIENTATION_UP;
			}
		}
		this.orientation = newOrientation;
		return newOrientation;
	}
	
	/**
	 * Gets a list of Paths this unit can move to. Each Path will have a
	 * unique destination Square. Paths will not cross Squares occupied by 
	 * either an enemy or static unit.
	 * @return - a list of Paths this unit can take
	 */
	public List<Path> getLegalPaths(){
		PathMapTable map = new PathMapTable();
		LinkedList<Square> queue = new LinkedList<Square>();
				
		queue.add(this.square);
		map.put(this.square, new Path());
		
		Path current_path;
		Unit U;
		while(queue.isEmpty() == false){
			Square current = queue.remove(0);
			if (current == null) continue;
			if (((Unit)current.getUnit()) != null && ((Unit)current.getUnit()).team != this.team) continue;
			current_path = map.get(current);
			if (current_path == null || current_path.length() > this.movement) {
				continue;
			}
			current_path.add(current);

			if (current_path.length() > this.movement) continue;
			U = (Unit) current.getUnit();
			if (U != null) {
				if (U.team != this.team)
					continue;
				if (U.is_static == true)
					continue;
			}
			map.put(this.board.getAdjacentSquare(Board.SQUARE_TO_UP, current),
					new Path(current_path));
			map.put(this.board.getAdjacentSquare(Board.SQUARE_TO_DOWN, current),
					new Path(current_path));
			map.put(this.board.getAdjacentSquare(Board.SQUARE_TO_LEFT, current),
					new Path(current_path));
			map.put(this.board
					.getAdjacentSquare(Board.SQUARE_TO_RIGHT, current),
					new Path(current_path));

			queue.add(this.board.getAdjacentSquare(Board.SQUARE_TO_UP, current));
			queue.add(this.board.getAdjacentSquare(Board.SQUARE_TO_DOWN,
					current));
			queue.add(this.board.getAdjacentSquare(Board.SQUARE_TO_LEFT,
					current));
			queue.add(this.board.getAdjacentSquare(Board.SQUARE_TO_RIGHT,
					current));

		}
		
		return map.getAllPaths();
		
	}
	
	/**
	 * Determines if the unit can be active or not on this turn
	 * @return - true if this unit is active on this turn.
	 */
	public boolean isActive(int current_team_turn){
		return (active && current_team_turn == this.team);
	}
	
	/**
	 * Private Map class used to ensure that no Square already existing 
	 * in the Table can be overwritten with a new Path.
	 * @author Derek Chan
	 */
	private class PathMapTable{
		
		HashMap<Square, Path> map = new HashMap<Square, Path>();
		
		public Path put(Square key, Path value){
			if(key == null){
				return null;
			}
			else if(map.containsKey(key)){
				return map.get(key);
			}
			else{
				return map.put(key, value);
			}
		}
		
		public Path get(Square key){
			return map.get(key);
		}
		
		public Path remove(Square key){
			if (key == null) return null;
			return map.remove(key);
		}
		
		/**
		 * Produces a list of Paths that are not already occupied by another unit.
		 * @return - a list of Paths
		 */
		public List<Path> getAllPaths(){
			ArrayList<Path> paths = new ArrayList<Path>();
			for(Square key: map.keySet()){
				if(key.isEmpty()){
					paths.add(map.get(key));
				}
					
			}
			return paths;
		}
	}

}
