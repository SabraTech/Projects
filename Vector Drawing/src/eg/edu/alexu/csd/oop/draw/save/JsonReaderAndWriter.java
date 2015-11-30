package eg.edu.alexu.csd.oop.draw.save;

import eg.edu.alexu.csd.oop.draw.Factory;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The Class JsonReaderAndWriter.
 */
public class JsonReaderAndWriter {

  /** The factory. */
  private Factory factory;

  /**
   * Instantiates a new Json reader and writer.
   */
  public JsonReaderAndWriter() {
    factory = Factory.getInstance();
  }

  /**
   * Writes the shape into the file shape.
   *
   * @param shape
   *          the shape
   * @param myFormatter
   *          the Formatter that writes into the file
   */
  private void writeShape(Shape shape, Formatter myFormatter) {

    myFormatter.format("\t\t\t \t\t{ \"className\" :  \"%s\"", shape.getClass().getName());// write
    // class
    // full
    // path
    myFormatter.format(",%n\t\t\t \t\t  \"position.x\" : \"%s\"",
        shape.getPosition() == null ? -1 : shape.getPosition().x);// position
    // x
    myFormatter.format(",%n\t\t\t \t\t  \"position.y\" : \"%s\"",
        shape.getPosition() == null ? -1 : shape.getPosition().y);// position
    // y
    myFormatter.format(",%n\t\t\t \t\t  \"color\" : \"%s\"",
        shape.getColor() == null ? "null" : shape.getColor().getRGB());// color
    myFormatter.format(",%n\t\t\t \t\t  \"fillColor\" : \"%s\"",
        shape.getFillColor() == null ? "null" : shape.getFillColor().getRGB());// fill
    // color
    if (shape.getProperties() != null) {
      String[] keys = new String[shape.getProperties().size()];
      Double[] values = new Double[shape.getProperties().size()];
      shape.getProperties().keySet().toArray(keys);
      shape.getProperties().values().toArray(values);
      for (int counter = 0; counter < keys.length; counter++) {
        // the get
        // and
        // write
        // properties
        // loop
        myFormatter.format(",%n\t\t\t \t\t  \"%s\" : \"%s\"", keys[counter],
            values[counter] == null ? "null" : values[counter].toString());
      }
    }
    myFormatter.format("%n\t\t\t \t\t}");
  }

  /**
   * Read shape.
   *
   * @param scanner
   *          the scanner from the file
   * @return the read shape
   * @throws InstantiationException
   *           the instantiation exception
   * @throws IllegalAccessException
   *           the illegal access exception
   * @throws ClassNotFoundException
   *           the class not found exception
   */
  private Shape readShape(Scanner scanner)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    StringTokenizer st = new StringTokenizer(scanner.nextLine());
    String dummy = null;
    String dummy2 = null;
    try {
      String[] keys;
      st.nextToken();
      st.nextToken();
      st.nextToken();

      dummy = st.nextToken();
      dummy2 = "  " + dummy;
      Shape shape = factory.getShapeByExactName(dummy.substring(1, dummy.length() - 2));

      st = new StringTokenizer(scanner.nextLine());

      st.nextToken();
      st.nextToken();// set the x of position
      dummy = st.nextToken();
      dummy2 += "  " + dummy;
      Point pos = new Point();
      pos.x = Integer.parseInt(dummy.substring(1, dummy.length() - 2));
      st = new StringTokenizer(scanner.nextLine());
      st.nextToken();
      st.nextToken();
      dummy = st.nextToken();
      dummy2 += "  " + dummy;
      pos.y = Integer.parseInt(dummy.substring(1, dummy.length() - 2));// set the y of
      // position

      shape.setPosition(pos.x == -1 ? null : pos);
      st = new StringTokenizer(scanner.nextLine());
      st.nextToken();
      st.nextToken();
      dummy = st.nextToken();
      dummy2 += "  " + dummy;
      shape.setColor(dummy.substring(1, dummy.length() - 2).equals("null") ? null
          : new Color(Integer.parseInt(dummy.substring(1, dummy.length() - 2))));// set
      // the
      // color
      st = new StringTokenizer(scanner.nextLine());
      st.nextToken();
      st.nextToken();
      dummy = st.nextToken();
      dummy2 += "  " + dummy;
      System.out.println(dummy);
      shape.setFillColor(dummy.replaceAll("\"", "").replaceAll(",", "").equals("null") ? null
          : new Color(Integer.parseInt(dummy.replaceAll("\"", "").replaceAll(",", ""))));// set
      // the
      // fill
      // color
      if (shape.getProperties() != null) {
        Map<String, Double> properties = shape.getProperties();
        keys = new String[properties.size()];
        properties.keySet().toArray(keys);// get the keys
        for (int counter = 0; counter < keys.length; counter++) {
          // the read
          // and
          // set
          // properties
          // loop
          st = new StringTokenizer(scanner.nextLine());
          st.nextToken();
          st.nextToken();
          dummy = st.nextToken();
          dummy2 += "  " + dummy;
          if (dummy.replaceAll("\"", "").replaceAll(",", "").equals("null")) {
            properties.put(keys[counter], null);
          } else {
            properties.put(keys[counter],
                Double.parseDouble(dummy.replaceAll("\"", "").replaceAll(",", "")));
          }
        }
        shape.setProperties(properties);
      }
      scanner.nextLine();
      return shape;
    } catch (Throwable e) {
      // TODO Auto-generated catch block
      throw new RuntimeException(e + dummy2);
    }
  }

  /**
   * Save.
   *
   * @param shapes
   *          the shapes array
   * @param path
   *          the path of the file to save to
   */
  public void save(Shape[] shapes, String path) {
    Formatter format;
    try {
      format = new Formatter(path);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("can't create file");
    }
    if (shapes == null) {
      format.close();
      return;
    }
    format.format("{%n");
    format.format("\t\"ShapeArray\" :%n\t\t\t \t[");
    for (int counter = 0; counter < shapes.length; counter++) {
      format.format("%n");
      writeShape(shapes[counter], format);
      if (counter < shapes.length - 1) {
        format.format(",");
      }
    }
    format.format("%n\t\t\t\t]");
    format.format("%n}");
    format.close();
    return;
  }

  /**
   * Load.
   *
   * @param path
   *          the path of the file to load from
   * @return the shape[] the shape array loaded
   * @throws InstantiationException
   *           the instantiation exception
   * @throws IllegalAccessException
   *           the illegal access exception
   * @throws ClassNotFoundException
   *           the class not found exception
   */
  public Shape[] load(String path)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    ArrayList<Shape> shapes = new ArrayList<Shape>();
    File file = new File(path);
    Scanner scanner;

    try {
      scanner = new Scanner(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("no such a file exists");
    }
    scanner.nextLine();
    scanner.nextLine();
    scanner.nextLine();
    while (!scanner.hasNext("]")) {
      shapes.add(readShape(scanner));
    }
    scanner.close();
    Shape[] returnedShapes = new Shape[shapes.size()];
    int counter = 0;
    for (Shape e : shapes) {
      returnedShapes[counter++] = e;
    }
    return returnedShapes;
  }

}
