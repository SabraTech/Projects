package eg.edu.alexu.csd.oop.draw.shapes;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;

import eg.edu.alexu.csd.oop.draw.Shape;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class that compares the shape, returns zero only if the shapes are equal, otherwise returns
 * any dummy value.
 */
public class ShapesComparator implements Comparator<Shape> {

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  @Override
  public int compare(Shape arg0, Shape arg1) {
    // TODO Auto-generated method stub
    if (arg0 == null && arg1 == null) {
      return 0;
    }
    if (arg0.getClass() != arg1.getClass()) {
      //System.out.println("diff class");
      return 1;// not eQual
    }
    try {
      if (arg0.getPosition() == null && arg1.getPosition() == null) {
        // do nothing
      } else if (arg0.getPosition().x != arg1.getPosition().x
          || arg0.getPosition().y != arg1.getPosition().y) {
        return 1;
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      return 1;
    }
    if (arg0.getColor() == null && arg1.getColor() == null) {
      // do nothing
    } else {
      try {
        if (arg0.getColor().getRGB() != arg1.getColor().getRGB()) {
          return 1;
        }
      } catch (Exception e) {
        // TODO Auto-generated catch block
        return 1;
      }
    }
    if (arg0.getFillColor() == null && arg1.getFillColor() == null) {
      // do nothing
    } else {
      try {
        if (arg0.getFillColor().getRGB() != arg1.getFillColor().getRGB()) {
          return 1;
        }
      } catch (Exception e) {
        // TODO Auto-generated catch block
        return 1;
      }
    }
    if (arg0.getProperties() == null && arg1.getProperties() == null) {
      return 0;
    }
    Set<Entry<String, Double>> one = arg0.getProperties().entrySet();
    Set<Entry<String, Double>> two = arg1.getProperties().entrySet();
    if (one.size() != two.size()) {
      return 1;// different maps thus not eQual
    }

    Iterator<Entry<String, Double>> map1Iterator = one.iterator();
    Iterator<Entry<String, Double>> map2Iterator = two.iterator();
    while (map1Iterator.hasNext()) {
      Entry<String, Double> dummy1u = map1Iterator.next();
      Entry<String, Double> dummy2 = map2Iterator.next();
      if (!dummy1u.getKey().equals(dummy2.getKey())) {
        //System.out.println(dummy1u.getKey() + " " + dummy2.getKey() + "are not equal");
        return 1;
      }
      if (dummy1u.getValue() == null && dummy2.getValue() == null) {
        continue;
      }
      if (dummy1u.getValue().compareTo(dummy2.getValue()) != 0) {
        //System.out.println(dummy1u.getValue() + " " + dummy2.getValue() + "are not equal");
        return 1;
      }
    }
    return 0;
  }
}