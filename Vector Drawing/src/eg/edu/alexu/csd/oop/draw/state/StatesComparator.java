package eg.edu.alexu.csd.oop.draw.state;

import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * The Class that compares states.
 */
public class StatesComparator implements Comparator<State> {

  /**
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object) takes two State
   *      arguments, return -1 if the first is smaller, 0 if they're equal, 1 if the second is
   *      smaller
   */
  @Override
  public int compare(State arg0, State arg1) {
    // TODO Auto-generated method stub
    return arg0.getTime() - arg1.getTime();
  }

}
