package lt.units;

import gridBoard.Square;

import java.util.ArrayList;

/**
 * Path class creates a queue of Squares that a unit may iterate through
 * to reach a destination. The path will contain its starting square.
 * @author Derek Chan
 */
public class Path {
	
	private ArrayList<Square> path;
	private int currIndex;
	
	
	/**
	 * Creates a new Path object
	 */
	public Path(){
		path = new ArrayList<Square>();
		currIndex = 0;
	}
	
	/**
	 * Creates a copy of the Path object
	 * @param p - the Path to copy
	 */
	public Path(Path p){
		path = new ArrayList<Square>();
		path.addAll(p.getAllElements());
		currIndex = 0;
	}
	
	/**
	 * Appends a Square to the end of the Path
	 * @param next - the next Square in the Path
	 */
	public void add(Square next){
		path.add(next);
	}
	
	/**
	 * @return - the destination of the Path
	 */
	public Square destination(){
		return path.get(path.size()-1);
	}
	
	/**
	 * Resets all progress in the path
	 * @return - the starting Square in the Path
	 */
	public Square reset(){
		currIndex = 0;
		return path.get(0);
	}
	
	/**
	 * @requires - the Path has not yet reached its destination
	 * @return - the next Square in the Path
	 */
	public Square next(){
		currIndex++;
		return path.get(currIndex);
	}
	
	/**
	 * @return - true if the Path has not yet reached its destination Square
	 */
	public boolean hasNext(){
		return (currIndex < path.size()-1);
	}
	
	/**
	 * @return - the length of the Path
	 */
	public int length(){
		return path.size();
	}
	
	private ArrayList<Square> getAllElements(){
		return path;
	}
	
}
