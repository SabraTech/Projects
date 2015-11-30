package eg.edu.alexu.csd.oop.draw.shapes;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

// TODO: Auto-generated Javadoc
/**
 * The Class Square.
 */
public class Square extends Rectangle implements Shape, Cloneable {

  /**
   * Instantiates a new square.
   */
  public Square() {
    super();
  }

  /**
   * Instantiates a new square.
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
  private Square(Point position, Color color, Color fillColor, Map<String, Double> properties) {
    this.setPosition(position);
    this.setColor(color);
    this.setFillColor(fillColor);
    this.setProperties(properties);
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.Rectangle#setProperties(java.util.Map)
   */
  @Override
  public void setProperties(Map<String, Double> properties) {
    // TODO Auto-generated method stub
    if (!properties.containsKey("Length")) {
      // do nothing
    } else {
      properties.put("Width", properties.get("Length"));
      super.setProperties(properties);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.Rectangle#getProperties()
   */
  @Override
  public Map<String, Double> getProperties() {
    // TODO Auto-generated method stub
    HashMap<String, Double> squareProperties = new HashMap<String, Double>();
    squareProperties.put("Length", super.getProperties().get("Length"));
    return squareProperties;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.Rectangle#clone()
   */
  @Override
  public Object clone() {
    Map<String, Double> newProp = this.getProperties();
    return new Square(this.getPosition(), this.getColor(), this.getFillColor(), newProp);
  }

}