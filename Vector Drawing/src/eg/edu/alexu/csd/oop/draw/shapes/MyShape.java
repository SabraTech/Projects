package eg.edu.alexu.csd.oop.draw.shapes;

import java.awt.Color;
import java.awt.Point;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * s The Class MyShape.
 */
public abstract class MyShape implements Shape, Cloneable {

  /** The position. */
  private Point position;

  /** The fill color. */
  private Color color;

  /** The fill color. */
  private Color fillColor;

  /**
   * Instantiates a new my shape.
   */
  public MyShape() {
    this.position = null;
    this.color = null;
    this.fillColor = null;
  }

  /**
   * Instantiates a new my shape.
   *
   * @param position
   *          the position
   * @param color
   *          the color
   * @param fillColor
   *          the fill color
   */
  public MyShape(Point position, Color color, Color fillColor) {
    this.position = position;
    this.color = color;
    this.fillColor = fillColor;
  }

  /**
   * setPostion sets the Shape position.
   * 
   * @param position
   *          the new position
   * 
   */
  @Override
  public void setPosition(java.awt.Point position) {
    this.position = position;

  }

  /**
   * getPosiotion returns the Shape position.
   * 
   * @return the shape position
   * 
   */
  @Override
  public java.awt.Point getPosition() {
    if (position == null) {
      return null;
    }
    return new Point(position.x, position.y);
  }

  /**
   * setProperties sets the shape's properties.
   * 
   * @param properties
   *          the map containing the properties.
   */
  @Override
  public abstract void setProperties(java.util.Map<String, Double> properties);

  /**
   * getProperties returns the shape's properties.
   * 
   * @return a map containing the shape's properties
   */
  @Override
  public abstract java.util.Map<String, Double> getProperties();

  /**
   * setColor sets the shape's color.
   * 
   * @param color
   *          the shape's color
   * 
   */
  @Override
  public void setColor(java.awt.Color color) {
    if (color == null) {
      this.color = null;
    } else {
      this.color = new Color(color.getRGB());
    }
  }

  /**
   * getColor returns the shape's color.
   * 
   * @return the shape's color
   * 
   */
  @Override
  public java.awt.Color getColor() {
    return color == null ? null : new Color(this.color.getRGB());
  }

  /**
   * setFillColor sets the fill color of the Shape.
   * 
   * @param fillColor
   *          the fill color
   * 
   */
  @Override
  public void setFillColor(java.awt.Color color) {
    this.fillColor = (color == null ? null : new Color(color.getRGB()));
  }

  /**
   * getFillColor returns the shape's fill color.
   * 
   * @return the shape's fill color
   * 
   */
  @Override
  public java.awt.Color getFillColor() {
    return fillColor == null ? null : new Color(this.fillColor.getRGB());
  }

  /**
   * draw draws the Shape.
   * 
   */
  @Override
  public abstract void draw(java.awt.Graphics canvas);

  /**
   * clone clones the shape.
   * 
   */
  @Override
  public abstract Object clone() throws CloneNotSupportedException;
}
