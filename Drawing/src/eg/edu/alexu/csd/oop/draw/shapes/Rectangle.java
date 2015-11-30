package eg.edu.alexu.csd.oop.draw.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.map.cloner.MapCloner;

/**
 * The Class Rectangle.
 */
public class Rectangle extends MyShape {

  /** The properties. */
  private Map<String, Double> properties;

  /**
   * Instantiates a new rectangle.
   */
  public Rectangle() {
    super();
    properties = new HashMap<String, Double>();
    properties.put("Width", null);
    properties.put("Length", null);
  }

  /**
   * Instantiates a new rectangle.
   *
   * @param position
   *          the position
   * @param properties
   *          the properties
   * @param color
   *          the color
   * @param fillColor
   *          the fill color
   */
  private Rectangle(Point position, Map<String, Double> properties, Color color, Color fillColor) {
    super(position, color, fillColor);
    this.properties = properties;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.MyShape#setProperties(java.util.Map)
   */
  @Override
  public void setProperties(Map<String, Double> properties) {
    // TODO Auto-generated method stub
    if (!properties.containsKey("Width") || !properties.containsKey("Length")) {
      // do nothing
    } else {
      this.properties = properties;
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.MyShape#getProperties()
   */
  @Override
  public Map<String, Double> getProperties() {
    // TODO Auto-generated method stub
    return properties;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.MyShape#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics canvas) {
    // TODO Auto-generated method stub
    if (super.getPosition() == null || properties.get("Width") == null
        || properties.get("Length") == null) {
      // do nothing
    } else {

      Double width = properties.get("Width");
      Double length = properties.get("Length");
      canvas.setColor(getFillColor());
      canvas.fillRect(super.getPosition().x, super.getPosition().y, width.intValue(),
          length.intValue());
      canvas.setColor(getColor());
      canvas.drawRect(super.getPosition().x, super.getPosition().y, width.intValue(),
          length.intValue());
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.MyShape#clone()
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    // TODO Auto-generated method stub

    try {
      HashMap<String, Double> clonedProperties = MapCloner.cloneMap(properties);
      return (Object) new Rectangle(new Point(super.getPosition().x, super.getPosition().y),
          clonedProperties, super.getColor(), super.getFillColor());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      throw new CloneNotSupportedException();
    }
  }

}
