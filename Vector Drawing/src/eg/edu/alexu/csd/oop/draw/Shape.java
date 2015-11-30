package eg.edu.alexu.csd.oop.draw;

/**
 * The Interface Shape.
 */
public interface Shape {

  /**
   * Sets the position.
   *
   * @param position
   *          the new position
   */
  public void setPosition(java.awt.Point position);

  /**
   * Gets the position.
   *
   * @return the position
   */
  public java.awt.Point getPosition();

  /**
   * Sets the properties.
   *
   * @param properties
   *          the properties
   */
  /* update shape specific properties (e.g., radius) */
  public void setProperties(java.util.Map<String, Double> properties);

  /**
   * Gets the properties.
   *
   * @return the properties
   */
  public java.util.Map<String, Double> getProperties();

  /**
   * Sets the color.
   *
   * @param color
   *          the new color
   */
  public void setColor(java.awt.Color color);

  /**
   * Gets the color.
   *
   * @return the color
   */
  public java.awt.Color getColor();

  /**
   * Sets the fill color.
   *
   * @param color
   *          the new fill color
   */
  public void setFillColor(java.awt.Color color);

  /**
   * Gets the fill color.
   *
   * @return the fill color
   */
  public java.awt.Color getFillColor();

  /**
   * Draw.
   *
   * @param canvas
   *          the canvas
   */
  /* redraw the shape on the canvas */
  public void draw(java.awt.Graphics canvas);

  /**
   * Clone.
   *
   * @return the object
   * @throws CloneNotSupportedException
   *           the clone not supported exception
   */
  /* create a deep clone of the shape */
  public Object clone() throws CloneNotSupportedException;

}
