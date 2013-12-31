package gridBoard;

public abstract class AbstractUnit {
	
	/**
	 * The square this unit is currently occupying
	 */
	protected Square square;
	/**
	 * The board this unit is currently occupying
	 */
	protected Board board;
	
	public AbstractUnit(Board board){
		this.board = board;
	}
	
	/**
	 * Moves this unit to the destination Square
	 * @param destination - the destination Square
	 * @return - the unit that was previously occupying the square
	 */
	public AbstractUnit moveToSquare(Square destination){
		this.square.removeUnit();
		return destination.setUnit(this);
	}
	
	/**
	 * Returns an adjacent square to this unit, specified by the 
	 * direction constants in the Board class.
	 * @param direction - the direction for adjacency
	 * @return - the adjacent square in the direction
	 */
	public Square getAdjacentSquare(int direction){
		return board.getAdjacentSquare(direction, this.square);
	}
	
	/**
	 * @return - the Square this unit is occupying
	 */
	public Square getSquare(){
		return square;
	}
	
	/**
	 * Returns a set of Squares that this unit is permitted to move to
	 * @return
	 */
	public abstract SquareSet getLegalMoves();

}
