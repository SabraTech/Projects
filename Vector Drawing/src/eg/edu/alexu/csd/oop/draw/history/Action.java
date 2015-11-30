package eg.edu.alexu.csd.oop.draw.history;

import eg.edu.alexu.csd.oop.draw.state.State;

// TODO: Auto-generated Javadoc
/**
 * The Class Action.
 */
public class Action {

  /** The state. */
  private State state;

  /** The second state in case of edit. */
  private State state2;

  /** The boolean to check if it's a removal action. */
  private boolean isRemoved;

  /** The boolean to know if it's an edit action. */
  private boolean isEdited;

  /**
   * Instantiates a new action. the action is removal or addition
   * 
   * @param state
   *          the state
   * @param isRemoved
   *          the is removed
   */
  public Action(State state, boolean isRemoved) {
    this.state = state;
    this.isRemoved = isRemoved;
    this.isEdited = false;
  }

  /**
   * Instantiates a new action. the action is edit.
   *
   * @param state
   *          the state
   * @param state2
   *          the state2
   * @param isEdited
   *          the is edited
   */
  public Action(State state, State state2, boolean isEdited) {
    this.state = state;
    this.state2 = state2;
    this.isRemoved = false;
    this.isEdited = true;
  }

  /**
   * Gets the state.
   *
   * @return the state
   */
  public State getState() {
    return this.state;
  }

  /**
   * Gets the state2.
   *
   * @return the state2
   */
  public State getState2() {
    return this.state2;
  }

  /**
   * Checks if is removed.
   *
   * @return true, if is removed
   */
  public boolean isRemoved() {
    return this.isRemoved;
  }

  /**
   * Checks if is edited.
   *
   * @return true, if is edited
   */
  public boolean isEdited() {
    return this.isEdited;
  }
}
