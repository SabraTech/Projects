package eg.edu.alexu.csd.oop.draw.history;

import java.util.Iterator;
import java.util.Stack;

/**
 * The Class History.
 */
public class History { // follow the sigelton design pattern

  /** The previous state. */
  private Stack<Action> prev;

  /** The next state. */
  private Stack<Action> next;

  /** The single instance. */
  private static History seule;

  /**
   * Instantiates a new history, follows the singleton design pattern.
   */
  private History() {
    prev = new Stack<Action>();
    next = new Stack<Action>();
  }

  /**
   * Gets the single instance of History.
   *
   * @return single instance of History
   */
  public static History getInstance() {
    if (seule == null) {
      seule = new History();
    }
    seule.clear();
    return seule;
  }

  /**
   * Gets the current.
   *
   * @return the current
   */
  public Action getCurrent() {
    if (prev.isEmpty()) {
      return null;
    }
    return prev.peek();
  }

  /**
   * Adds the action.
   *
   * @param action
   *          the action
   */
  public void addAction(Action action) {
    prev.push(action);
    if (prev.size() > 20) {
      prev.remove(0);
    }
    next.clear();
  }

  /**
   * Gets the next.
   *
   * @return the next
   */
  public Action getNext() {
    if (next.isEmpty()) {
      return null;
    }
    prev.push(next.pop());
    if (prev.size() > 20) {
      prev.remove(0);
    }
    return prev.peek();
  }

  /**
   * Gets the to previous state.
   *
   */
  public void getToPrev() {
    next.push(prev.pop());
  }

  /**
   * Clear.
   */
  public void clear() {
    prev.clear();
    next.clear();
  }

  /**
   * Gets the iterator.
   *
   * @return the iterator
   */
  public Iterator<Action> getIterator() {
    return prev.iterator();
  }

}
