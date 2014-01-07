package gridBoard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * A set of Squares with basic getters and setters
 * to ensure that the Squares contained in a list
 * within this object are all non-null and unique.
 * @author Derek Chan
 * @version 1.0
 *
 */
public class SquareSet implements Set<Square>{
	
	private ArrayList<Square> set;
	
	public SquareSet(){
		set = new ArrayList<Square>();
	}
	
	public Square get(int index){
		return set.get(index);
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);		
	}

	@Override
	public Iterator<Square> iterator() {
		return set.iterator();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	@Override
	public boolean add(Square e) {
		if (e == null || set.contains(e)){
			return false;
		}
		else
			return set.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Square> c) {
		Iterator<? extends Square> i = c.iterator();
		Square s;
		boolean flag = true;
		while(i.hasNext()){
			s  = i.next();
			if(this.add(s) == false){
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	@Override
	public void clear() {
		set.clear();
	}

}
