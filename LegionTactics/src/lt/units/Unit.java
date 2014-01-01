package lt.units;

import java.util.Random;

import gridBoard.AbstractUnit;
import gridBoard.Board;
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
	public final static int ORIENTATION_RIGHT = 0;
	/**
	 * Facing to the top
	 */
	public final static int ORIENTATION_UP = 1;
	/**
	 * Facing to the left
	 */
	public final static int ORIENTATION_LEFT = 2;
	/**
	 * Facing to the bottom
	 */
	public final static int ORIENTATION_DOWN = 3;
	
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
	 * Orientation of this unit
	 */
	protected int orientation;
	
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
		if (random.nextInt(100) > chance_of_attack){
			this.hp -= damage;
			return true;
		}
		
		//unsuccessful attack
		return false;
	}
	
	private int calculateBlockPercentage(int orientation_of_attacker){
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

}
