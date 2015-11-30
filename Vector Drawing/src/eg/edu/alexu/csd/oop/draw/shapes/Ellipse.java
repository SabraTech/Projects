package eg.edu.alexu.csd.oop.draw.shapes;

import eg.edu.alexu.csd.oop.draw.map.cloner.MapCloner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class Ellipse.
 */
public class Ellipse extends MyShape {

  /** The properties map. */
  private Map<String, Double> properties;

  /**
   * Instantiates a new ellipse.
   */
  public Ellipse() {
    super();
    properties = new HashMap<String, Double>();
    properties.put("MajorRadius", null);
    properties.put("MinorRadius", null);

  }

  /**
   * Instantiates a new ellipse.
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
  private Ellipse(Point position, Map<String, Double> properties, Color color, Color fillColor) {
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
    if (properties.containsKey("MajorRadius") && properties.containsKey("MinorRadius")) {
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

    Double radiusOne = properties.get("MajorRadius");
    Double radiusTwo = properties.get("MinorRadius");
    canvas.setColor(getFillColor());
    canvas.fillOval(super.getPosition().x, super.getPosition().y, radiusOne.intValue(),
        radiusTwo.intValue());
    canvas.setColor(getColor());
    canvas.drawOval(super.getPosition().x, super.getPosition().y, radiusOne.intValue(),
        radiusTwo.intValue());

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
      HashMap<String, Double> clonedProperties = MapCloner.cloneMap(this.properties);
      return (Object) new Ellipse(new Point(super.getPosition().x, super.getPosition().y),
          clonedProperties, super.getColor(), super.getFillColor());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      throw new CloneNotSupportedException();
    }
  }

}