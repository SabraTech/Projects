package eg.edu.alexu.csd.oop.draw.shapes;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.map.cloner.MapCloner;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Point;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class Triangle.
 */
public class Triangle extends MyShape implements Shape, Cloneable {

  /** The properties. */
  private Map<String, Double> properties;

  /**
   * Instantiates a new triangle.
   */
  public Triangle() {
    super();
    properties = new HashMap<String, Double>();
    for (int counter = 0; counter < 3; counter++) {
      properties.put("X" + counter, null);
      properties.put("Y" + counter, null);
    }
  }

  /**
   * Instantiates a new triangle.
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
  private Triangle(Point position, HashMap<String, Double> properties, Color color,
      Color fillColor) {
    super(position, color, fillColor);
    this.setProperties(properties);

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
      super.setPosition(position);
      for (int counter = 0; counter < 3; counter++) {
        if (properties.get("X" + counter) == null || properties.get("Y" + counter) == null) {
          return;
        }
      }
      shift(position.x - properties.get("X0"), position.y - properties.get("Y0"));
    }

  }

  /**
   * Shift.
   *
   * @param distX
   *          the d
   * @param distY
   *          the e
   */
  public void shift(double distX, double distY) {
    for (int counter = 0; counter < 3; counter++) {
      properties.put("X" + counter, properties.get("X" + counter) + distX);
      properties.put("Y" + counter, properties.get("Y" + counter) + distY);
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
    for (int counter = 0; counter < 3; counter++) {
      if (!properties.containsKey("X" + counter) || !properties.containsKey("Y" + counter)) {
        return;
      }
    }
    this.properties = MapCloner.cloneMap(properties);
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.MyShape#getProperties()
   */
  @Override
  public Map<String, Double> getProperties() {
    // TODO Auto-generated method stub
    return MapCloner.cloneMap(properties);
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.MyShape#draw(java.awt.Graphics)
   */
  @Override
  public void draw(Graphics canvas) {
    // TODO Auto-generated method stub
    for (int counter = 0; counter < 3; counter++) {
      if (properties.get("X" + counter) == null || properties.get("Y" + counter) == null) {
        return;
      }
    }
    int[] firstCoord = new int[4];
    int[] secondCoord = new int[4];
    for (int cnt = 0; cnt < 3; cnt++) {
      firstCoord[cnt] = (properties.get("X" + cnt)).intValue();
      secondCoord[cnt] = (properties.get("Y" + cnt)).intValue();
    }
    firstCoord[3] = properties.get("X0").intValue();
    secondCoord[3] = properties.get("Y0").intValue();

    canvas.setColor(getFillColor());
    canvas.fillPolygon(firstCoord, secondCoord, 4);
    canvas.setColor(getColor());
    canvas.drawPolygon(firstCoord, secondCoord, 4);

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

      return (Object) new Triangle(new Point(super.getPosition().x, super.getPosition().y),
          clonedProperties, super.getColor(), super.getFillColor());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      throw new CloneNotSupportedException();
    }
  }

}
