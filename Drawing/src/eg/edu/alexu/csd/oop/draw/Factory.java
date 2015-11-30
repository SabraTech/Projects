package eg.edu.alexu.csd.oop.draw;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Factory.
 */
public class Factory {

  /** The single instance. */
  private static Factory singleInstance;

  /** The supported shapes list. */
  private List<Class<? extends Shape>> shapes;

  /**
   * Instantiates a new factory.
   */
  private Factory() {
    shapes = new ArrayList<Class<? extends Shape>>();
  }

  /**
   * Gets the single instance of Factory.
   *
   * @return single instance of Factory
   */
  public static Factory getInstance() {
    if (singleInstance == null) {
      singleInstance = new Factory();
    }
    return singleInstance;
  }

  /**
   * Adds the shapes.
   *
   * @param newShapes
   *          the new shapes
   */
  public void addShapes(List<Class<? extends Shape>> newShapes) {
    for (Class<? extends Shape> dummy : newShapes) {
      if (!shapes.contains(dummy)) {
        shapes.add(dummy);
      }
    }
  }

  /**
   * Gets the shape by simple name.
   *
   * @param simpleName
   *          the simple name
   * @return the shape by simple name
   */
  public Shape getShapeBySimpleName(String simpleName) {

    for (int counter = 0; counter < shapes.size(); counter++) {
      if (shapes.get(counter).getSimpleName().equals(simpleName)) {
        int index = counter;
        try {
          return (Shape) Class
              .forName(shapes.get(index).getName(), false, shapes.get(index).getClassLoader())
              .newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
          // TODO Auto-generated catch block
          throw new RuntimeException(e.toString());
        }
      }
    }
    throw new RuntimeException("couldn't find such a shape " + simpleName);
  }

  /**
   * Gets the shape by exact name.
   *
   * @param exactName
   *          the exact name
   * @return the shape by exact name
   */
  public Shape getShapeByExactName(String exactName) {
    for (int counter = 0; counter < shapes.size(); counter++) {
      if (shapes.get(counter).getName().equals(exactName)) {
        int index = counter;
        try {
          // the classes loaded through URLClassLoader have to specify the classloader to use the
          // classForName
          return (Shape) Class
              .forName(shapes.get(index).getName(), false, shapes.get(index).getClassLoader())
              .newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
          // TODO Auto-generated catch block
          throw new RuntimeException("at first catch ");
        }
      }
    }
    try {
      // here I'm sure that the shape is not a plug-in shape, so it's not loaded by URLClassLoader
      // and can get an instance by normal CL directly.
      return (Shape) Class.forName(exactName).newInstance();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      throw new RuntimeException(e + " name = " + exactName);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      throw new RuntimeException(e + " name = " + exactName);
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      throw new RuntimeException(e + " name = " + exactName);
    }
  }

}
