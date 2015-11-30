package eg.edu.alexu.csd.oop.calculator;

import java.util.Iterator;
import java.util.Stack;

public class DataStructure {

	private Stack<String> primary, secondary;

	public DataStructure() {
		primary = new Stack<String>();
		secondary = new Stack<String>();
	}

	public String getTop() {
		if (primary.isEmpty()) {
			return null;
		}
		return primary.peek();
	}

	public void addResult(String s) {
		primary.push(s);
		if (primary.size() > 5) {
			primary.remove(0);
		}
		secondary.clear();
	}

	public String getPrev() {
		if (primary.size() < 2) {
			return null;
		}
		secondary.push(primary.pop());
		return primary.peek();
	}

	public String getNext() {
		if (secondary.isEmpty()) {
			return null;
		}
		primary.push(secondary.pop());
		if (primary.size() > 5) {
			primary.remove(0);
		}
		return primary.peek();
	}

	public void clear() {
		// TODO Auto-generated method stub
		primary.clear();
		secondary.clear();
	}

	public Iterator<String> getIterator() {
		// TODO Auto-generated method stub
		return primary.iterator();
	}

}
