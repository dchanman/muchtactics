package gridBoard;

/**
 * Square class for the gridBoard package. Basic tile on a playing board,
 * meant to hold a single unit on it. It is identified with a row and column
 * value. Note that Squares are deemed equal by address reference only, two squares
 * with the same row and column value will not be equal, as each board will only
 * have a single Square with that row/column value. Squares are only constructed 
 * by a Board.
 * @author Derek Chan
 */
public class Square {
	
	/**
	 * The unit occupying the Square
	 */
	protected AbstractUnit unit;
	/**
	 * The row number of the Square
	 */
	protected final int row;
	/**
	 * The column number of the Square
	 */
	protected final int col;
	
	/**
	 * Creates a new Square
	 * @param row - row number of the Square
	 * @param col - column number of the Square
	 */
	protected Square(int row, int col){
		unit = null;
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Gets the unit occupying this Square
	 * @return - the unit occupying this Square
	 */
	public AbstractUnit getUnit(){
		return unit;
	}
	
	/**
	 * Removes the unit occupying this Square
	 * @return - the unit that was on the Square prior to removal
	 */
	public AbstractUnit removeUnit(){
		AbstractUnit u = unit;
		unit = null;
		return u;
	}
	
	/**
	 * Places the unit onto this Square. The unit previously
	 * occupying the square is returned. The unit to be placed
	 * will have its Square reference updated to this Square.
	 * @param unit - the unit to be placed onto the Square
	 * @return - the unit that was occupying the Square prior to placing the new unit
	 */
	public AbstractUnit setUnit(AbstractUnit unit){
		AbstractUnit u = this.unit;
		this.unit = unit;
		unit.square = this;
		return u;
	}
	
	/**
	 * @return - the row number of the Square
	 */
	public int getRow(){
		return row;
	}
	
	/**
	 * @return - the column number of the Square
	 */
	public int getCol(){
		return col;
	}
	
	/**
	 * @return - true if there is no unit occupying the Square
	 */
	public boolean isEmpty(){
		return (unit==null);
	}
	
	@Override
	public boolean equals(Object o){
		return (this == o);
	}
	
	@Override
	public String toString(){
		return ("[ " + row + " , " + col + " ]");
	}

}
