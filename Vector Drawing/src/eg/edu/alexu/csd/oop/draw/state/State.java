package eg.edu.alexu.csd.oop.draw.state;

import eg.edu.alexu.csd.oop.draw.Shape;

// TODO: Auto-generated Javadoc
/**
 * The Class State.
 */
public class State {
  
  /** The shape. */
  private Shape shape;
  
  /** The time. */
  private int time;

  /**
   * Instantiates a new state.
   *
   * @param shape the shape
   * @param time the time
   */
  public State(Shape shape, int time) {
    this.shape = shape;
    this.time = time;
  }

  /**
   * Gets the time.
   *
   * @return the time
   */
  public int getTime() {
    return time;
  }

  /**
   * Gets the shape.
   *
   * @return the shape
   */
  public Shape getShape() {
    return shape;
  }

}
