package eg.edu.alexu.csd.oop.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.map.cloner.MapCloner;

/**
 * The Class LineSegment.
 */
public class LineSegment extends MyShape {

  /** The properties. */
  private Map<String, Double> properties;

  /**
   * Instantiates a new line segment.
   */
  public LineSegment() {
    super();
    properties = new HashMap<String, Double>();
    properties.put("EndPointXCoordinate", null);
    properties.put("EndPointYCoordinate", null);
  }

  /**
   * Instantiates a new line segment.
   *
   * @param start
   *          the start
   * @param properties
   *          the properties
   * @param color
   *          the color
   * @param fillColor
   *          the fill color
   */
  private LineSegment(Point start, Map<String, Double> properties, Color color, Color fillColor) {
    super(start, color, fillColor);
    this.properties = properties;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.MyShape#setPosition(java.awt.Point)
   */
  @Override
  public void setPosition(Point position) {
    // TODO Auto-generated method stub
    if (position != null) {
      if (getPosition() != null) {
        if (properties.get("EndPointXCoordinate") != null
            && properties.get("EndPointYCoordinate") != null) {
          properties.put("EndPointXCoordinate",
              properties.get("EndPointXCoordinate") + (this.getPosition().x - position.x));
          properties.put("EndPointYCoordinate",
              properties.get("EndPointYCoordinate") + (this.getPosition().y - position.y));
          super.setPosition(position);
        } else {
          super.setPosition(position);
        }
      } else {
        super.setPosition(position);
      }

    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.MyShape#setProperties(java.util.Map)
   */
  @Override
  public void setProperties(Map<String, Double> properties) {
    // TODO Auto-generated method stub
    if (!properties.containsKey("EndPointXCoordinate")
        || !properties.containsKey("EndPointYCoordinate")) {
      // do nothing
    } else {
      this.properties = MapCloner.getInst().cloneMap(properties);
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
    return MapCloner.getInst().cloneMap(properties);
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.MyShape#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics canvas) {
    // TODO Auto-generated method stub
    if (super.getPosition() != null && properties.get("EndPointXCoordinate") != null
        && properties.get("EndPointYCoordinate") != null) {

      Graphics2D canvas2D = (Graphics2D) canvas;
      canvas2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      canvas2D.setStroke(new BasicStroke(2));
      Double firstCoord = properties.get("EndPointXCoordinate");
      Double secondCoord = properties.get("EndPointYCoordinate");
      canvas2D.setColor(getFillColor());
      canvas2D.drawLine(super.getPosition().x, super.getPosition().y, firstCoord.intValue(),
          secondCoord.intValue());

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
      HashMap<String, Double> clonedProperties = MapCloner.getInst().cloneMap(properties);
      return (Object) new LineSegment(new Point(super.getPosition().x, super.getPosition().y),
          clonedProperties, super.getColor(), super.getFillColor());

    } catch (Exception e) {
      // TODO Auto-generated catch block
      throw new CloneNotSupportedException();
    }
  }

}
