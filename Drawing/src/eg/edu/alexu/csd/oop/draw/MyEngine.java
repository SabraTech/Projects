package eg.edu.alexu.csd.oop.draw;

import eg.edu.alexu.csd.oop.draw.history.Action;
import eg.edu.alexu.csd.oop.draw.history.History;
import eg.edu.alexu.csd.oop.draw.save.JsonReaderAndWriter;
import eg.edu.alexu.csd.oop.draw.save.XmlReaderAndWriter;
import eg.edu.alexu.csd.oop.draw.shapes.ShapesComparator;
import eg.edu.alexu.csd.oop.draw.state.State;
import eg.edu.alexu.csd.oop.draw.state.StatesComparator;

import java.awt.Graphics;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * The Class Engine.
 */
public class MyEngine implements DrawingEngine {

  /** The shape comparator. */
  private Comparator<Shape> shapeComparator;

  /** The queue carrying the states (the shapes and their insertion time). */
  private PriorityQueue<State> currentShapesQueue;

  /** The global time, incremented whenever any shape is added. */
  private int time;

  /** The instance that carries the history. */
  private History history;

  /** The state comparator. */
  private Comparator<State> stateComparator;

  /** The json reader and writer. */
  private JsonReaderAndWriter jasonReaderAndWriter;

  /** The xml reader and writer. */
  private XmlReaderAndWriter xmlReaderAndWriter;
  /** the class finder. */
  private ClassFinder classFinderInst;

  /**
   * Instantiates a new engine.
   */
  public MyEngine() {
    stateComparator = new StatesComparator();
    shapeComparator = new ShapesComparator();
    currentShapesQueue = new PriorityQueue<State>(20000, stateComparator);
    time = 0;
    history = History.getInstance();
    jasonReaderAndWriter = new JsonReaderAndWriter();
    xmlReaderAndWriter = new XmlReaderAndWriter();
    classFinderInst = new ClassFinder(Shape.class);
    // set the shapes supported by the factory
    Factory.getInstance().addShapes(this.getSupportedShapes());
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.DrawingEngine#refresh(java.awt.Graphics)
   */
  @Override
  public void refresh(Graphics canvas) {
    // TODO Auto-generated method stub
    Shape[] currentShapes = this.getShapes();
    for (Shape s : currentShapes) {
      s.draw(canvas);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.DrawingEngine#addShape(eg.edu.alexu.csd.oop.draw.Shape)
   */
  @Override
  public void addShape(Shape shape) {
    // TODO Auto-generated method stub
    if (shape == null) {
      throw new RuntimeException("null Shape was Sent");
    }
    Shape dummy = null;
    try {
      dummy = (Shape) shape;
      currentShapesQueue.add(new State((Shape) dummy, time));
    } catch (Exception e) {
      System.out.println(e);
    }
    history.addAction(new Action(new State(dummy, time++), false));
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.DrawingEngine#removeShape(eg.edu.alexu.csd.oop.draw.Shape)
   */
  @Override
  public void removeShape(Shape shape) {
    // TODO Auto-generated method stub
    if (shape == null) {
      throw new RuntimeException("null shape sent");
    }
    Iterator<State> iterate = currentShapesQueue.iterator();
    State dummy = null;
    while (iterate.hasNext()) {
      State dummyState = iterate.next();
      if (shapeComparator.compare(dummyState.getShape(), shape) == 0) { // they are equal
        dummy = dummyState;
        break;
      }
    }
    if (dummy != null) { // != null means found
      history.addAction(new Action(dummy, true));
      currentShapesQueue.remove(dummy);
    } else {
      throw new RuntimeException("nO such a shape exists");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.DrawingEngine#updateShape(eg.edu.alexu.csd.oop.draw.Shape,
   * eg.edu.alexu.csd.oop.draw.Shape)
   */
  @Override
  public void updateShape(Shape oldShape, Shape newShape) {
    // TODO Auto-generated method stub
    Iterator<State> iterate = currentShapesQueue.iterator();
    State dummyState = null;
    while (iterate.hasNext()) {
      State dummyState2 = iterate.next();
      if (shapeComparator.compare(dummyState2.getShape(), oldShape) == 0) {
        dummyState = dummyState2;
        break;
      }
    }
    State dummyState3;
    try {
      dummyState3 = new State((Shape) newShape.clone(), dummyState.getTime());
    } catch (CloneNotSupportedException e) {
      // TODO Auto-generated catch block
      throw new RuntimeException(
          "Clone not supported by" + dummyState.getShape().getClass().getName());
    }
    currentShapesQueue.remove(dummyState);
    currentShapesQueue.add(dummyState3);
    try {
      history.addAction(
          new Action(new State((Shape) dummyState.getShape().clone(), dummyState.getTime()),
              dummyState3, true));
    } catch (CloneNotSupportedException e) {
      // TODO Auto-generated catch block
      throw new RuntimeException("Clone not supported by" + dummyState.getClass().getName());
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.DrawingEngine#getShapes()
   */
  @Override
  public Shape[] getShapes() {
    // TODO Auto-generated method stub
    Shape[] shapes = new Shape[currentShapesQueue.size()];
    Iterator<State> queueIterator = currentShapesQueue.iterator();
    int counter = 0;
    while (queueIterator.hasNext()) {
      shapes[counter++] = queueIterator.next().getShape();
    }
    return shapes;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.DrawingEngine#getSupportedShapes()
   */
  @Override
  public List<Class<? extends Shape>> getSupportedShapes() { // not yet
    // implemented
    // TODO Auto-generated method stub

    Set<Class<? extends Shape>> dummyReturnList = classFinderInst.getSupportedClasses();
    List<Class<? extends Shape>> suppShapes = new LinkedList<Class<? extends Shape>>();

    for (Class<? extends Shape> dummyClass : dummyReturnList) {
      suppShapes.add(dummyClass);
    }
    return suppShapes;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.DrawingEngine#undo()
   */
  @Override
  public void undo() {
    // TODO Auto-generated method stub
    if (history.getCurrent() == null) {
      throw new RuntimeException("no History");
    }
    Action dummyAction = history.getCurrent();
    if (dummyAction.isEdited()) {
      Iterator<State> iterate = currentShapesQueue.iterator();
      State dummyState = null;
      while (iterate.hasNext()) {
        State dummyState2 = iterate.next();
        if (shapeComparator.compare(dummyState2.getShape(),
            dummyAction.getState2().getShape()) == 0) {
          dummyState = dummyState2;
          break;
        }
      }
      currentShapesQueue.remove(dummyState);
      currentShapesQueue.add(dummyAction.getState());
      history.getToPrev();
    } else if (dummyAction.isRemoved()) {
      currentShapesQueue.add(dummyAction.getState());
      history.getToPrev();
    } else {
      Iterator<State> queueIterator = currentShapesQueue.iterator();
      while (queueIterator.hasNext()) {
        State dummy = queueIterator.next();
        if (dummy.getTime() == dummyAction.getState().getTime()) {
          queueIterator.remove();
        }
      }
      history.getToPrev();
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.DrawingEngine#redo()
   */
  @Override
  public void redo() {
    // TODO Auto-generated method stub
    Action dummyAction;
    try {
      dummyAction = history.getNext();
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      throw new RuntimeException("no next");
    }

    if (dummyAction.isEdited()) {
      Iterator<State> iterate = currentShapesQueue.iterator();
      State dummyState = null;
      while (iterate.hasNext()) {
        State dummyState2 = iterate.next();
        if (shapeComparator.compare(dummyState2.getShape(),
            dummyAction.getState().getShape()) == 0) {
          dummyState = dummyState2;
          break;
        }
      }
      currentShapesQueue.remove(dummyState);
      currentShapesQueue.add(dummyAction.getState2());
    } else if (dummyAction.isRemoved()) {
      Iterator<State> queueIterator = currentShapesQueue.iterator();
      while (queueIterator.hasNext()) {
        State dummy = queueIterator.next();
        if (dummy.getTime() == dummyAction.getState().getTime()) {
          queueIterator.remove();
        }
      }
    } else {
      currentShapesQueue.add(dummyAction.getState());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.DrawingEngine#save(java.lang.String)
   */
  @Override
  public void save(String path) {
    // TODO Auto-generated method stub
    String pathEdited = (path.substring(path.lastIndexOf(".") + 1, path.length()));
    if (pathEdited.equalsIgnoreCase("json")) {
      jasonReaderAndWriter.save(this.getShapes(), path);
    } else if (pathEdited.equalsIgnoreCase("xml")) {
      xmlReaderAndWriter.writeXml(path, this.getShapes());
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.draw.DrawingEngine#load(java.lang.String)
   */
  @Override
  public void load(String path) {
    Shape[] shapes = null;
    // TODO Auto-generated method stub
    String pathEdited = (path.substring(path.lastIndexOf(".") + 1, path.length()));
    if (pathEdited.equalsIgnoreCase("json")) {
      try {
        Shape[] temp = jasonReaderAndWriter.load(path);
        if (temp != null) {
          shapes = temp;
          currentShapesQueue.clear();
          history.clear();
          for (int counter = 0; counter < shapes.length; counter++) {
            currentShapesQueue.add(new State(shapes[counter], time++));
          }
        }

      } catch (Exception e) {
        // TODO Auto-generated catch block
        throw new RuntimeException(e);
      }
    } else if (pathEdited.equalsIgnoreCase("xml")) {
      XmlReaderAndWriter xml = new XmlReaderAndWriter();
      Shape[] temp = xml.readXml(path);
      if (temp != null) {
        shapes = temp;
        currentShapesQueue.clear();
        history.clear();
        for (int counter = 0; counter < shapes.length; counter++) {
          currentShapesQueue.add(new State(shapes[counter], time++));
        }
      }
    }
  }

}
