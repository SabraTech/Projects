package eg.edu.alexu.csd.oop.draw.shapes;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * The Class Circle.
 */
public class Circle extends Ellipse implements Shape, Cloneable {

  /**
   * Instantiates a new circle.
   */
  public Circle() {
    super();
  }

  /**
   * Instantiates a new circle.
   *
   * @param position
   *          the position
   * @param color
   *          the color
   * @param fillColor
   *          the fill color
   * @param properties
   *          the properties
   */
  private Circle(Point position, Color color, Color fillColor, Map<String, Double> properties) {
    this.setPosition(position);
    this.setColor(color);
    this.setFillColor(fillColor);
    this.setProperties(properties);
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.Ellipse#setProperties(java.util.Map)
   */
  @Override
  public void setProperties(Map<String, Double> properties) {
    // TODO Auto-generated method stub
    if (!properties.containsKey("MajorRadius")) {
      // do nothing
    } else {
      properties.put("MinorRadius", properties.get("MajorRadius"));
      super.setProperties(properties);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.Ellipse#getProperties()
   */
  @Override
  public Map<String, Double> getProperties() {
    // TODO Auto-generated method stub
    HashMap<String, Double> circleProperties = new HashMap<String, Double>();
    circleProperties.put("MajorRadius", super.getProperties().get("MajorRadius"));
    return circleProperties;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.Ellipse#clone()
   */
  @Override
  public Object clone() {
    return new Circle(this.getPosition(), this.getColor(), this.getFillColor(),
        this.getProperties());
  }

}