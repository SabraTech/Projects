package eg.edu.alexu.csd.oop.draw.gui;

import eg.edu.alexu.csd.oop.draw.ClassFinder;
import eg.edu.alexu.csd.oop.draw.DrawingBoard;
import eg.edu.alexu.csd.oop.draw.Factory;
import eg.edu.alexu.csd.oop.draw.MyEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.gui.listners.AboutActionListener;
import eg.edu.alexu.csd.oop.draw.gui.listners.ColorActionListener;
import eg.edu.alexu.csd.oop.draw.gui.listners.CopyActionListener;
import eg.edu.alexu.csd.oop.draw.gui.listners.DeleteActionListener;
import eg.edu.alexu.csd.oop.draw.gui.listners.InstallActionListener;
import eg.edu.alexu.csd.oop.draw.gui.listners.LoadActionListener;
import eg.edu.alexu.csd.oop.draw.gui.listners.MenuActionListener;
import eg.edu.alexu.csd.oop.draw.gui.listners.MovingActionListener;
import eg.edu.alexu.csd.oop.draw.gui.listners.RedoActionListener;
import eg.edu.alexu.csd.oop.draw.gui.listners.ResizeAction;
import eg.edu.alexu.csd.oop.draw.gui.listners.SaveActionListner;
import eg.edu.alexu.csd.oop.draw.gui.listners.ShapeButtonListener;
import eg.edu.alexu.csd.oop.draw.gui.listners.UndoActionListener;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Class MainWindow.
 */
public class Gui {

  /** The frame. */
  private Box shapesBox;

  /** The frame. */
  private JFrame frame;

  /** The my class finder. */
  private ClassFinder myClassFinder;
  /** The edit buttons. */
  private JButton delete, resize, color, moveTo, copy, undo, redo;

  /** The saving and plug-in buttons. */
  private JButton save, load, install, about;

  /** The panel. */
  private JPanel panel;

  /** The menu. */
  private JComboBox<Object> menu;

  /** The engine. */
  private MyEngine engine;

  /** The factory. */
  private Factory factory;

  /** The canvas. */
  private DrawingBoard canvas;

  /** The buttons panel. */
  private JPanel buttonsPanel;

  /**
   * Create the application.
   */
  public Gui() {
    myClassFinder = new ClassFinder(Shape.class);
    engine = new MyEngine();
    factory = Factory.getInstance();
    factory.addShapes(engine.getSupportedShapes());
    canvas = new DrawingBoard(engine);
    initialize();
  }

  /**
   * Gets the frame.
   *
   * @return the frame
   */
  public JFrame getFrame() {
    return frame;
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    initializeFrame();
    createShapeButtons(engine.getSupportedShapes());
  }
  /*
   * END of initialize
   * 
   */

  /**
   * Refresh menu.
   */
  public void refreshMenu() {
    Shape[] existingShapes = engine.getShapes();
    menu.removeAllItems();
    String choose = "Choose Shape";
    menu.addItem(choose);
    for (Shape dummyShape : existingShapes) {
      menu.addItem(dummyShape);
    }
  }

  /**
   * Creates the shape buttons.
   *
   * @param shapeList
   *          the shape list
   */
  public void createShapeButtons(List<Class<? extends Shape>> shapeList) {
    if (shapesBox != null) {
      frame.remove(shapesBox);
    }
    shapesBox = Box.createHorizontalBox();
    shapesBox.setBounds(222, 11, 962, 27);

    JButton[] shapesButtons = new JButton[shapeList.size()];
    int counter = 0;
    for (Class<? extends Shape> shape : shapeList) {
      shapesButtons[counter] = new JButton(shape.getSimpleName());
      shapesButtons[counter].addActionListener(new ShapeButtonListener(engine, this, panel, canvas,
          menu, shapesButtons[counter], factory));
      shapesButtons[counter].setToolTipText("Draw a " + shape.getSimpleName());
      shapesBox.add(shapesButtons[counter]);
      counter++;
    }
    frame.getContentPane().add(shapesBox);
    shapesBox.setVisible(true);
    // frame.repaint();
  }

  /**
   * Removes the shapes box.
   */
  /*
   * END update the menu
   */
  public void removeShapesBox() {
    shapesBox.setVisible(false);
  }

  /**
   * Adds the buttons.
   *
   * @param buttonsPanel
   *          the buttons panel
   */
  public void addButtons(JPanel buttonsPanel) {
    resize = new JButton("Resize");
    resize.setBounds(105, 235, 91, 23);
    resize.setEnabled(false);
    buttonsPanel.add(resize);
    save = new JButton("Save");
    save.setBounds(105, 45, 91, 23);
    buttonsPanel.add(save);
    undo = new JButton("Undo");
    undo.setBounds(10, 11, 85, 23);
    buttonsPanel.add(undo);
    load = new JButton("Load");
    load.setBounds(10, 45, 85, 23);
    buttonsPanel.add(load);
    delete = new JButton("delete");
    delete.setBounds(10, 303, 186, 23);
    delete.setEnabled(false);
    buttonsPanel.add(delete);
    color = new JButton("Color");
    color.setBounds(10, 235, 85, 23);
    color.setEnabled(false);
    buttonsPanel.add(color);
    moveTo = new JButton("Cut");
    moveTo.setBounds(10, 269, 85, 23);
    moveTo.setEnabled(false);
    buttonsPanel.add(moveTo);
    copy = new JButton("Copy");
    copy.setBounds(105, 269, 91, 23);
    copy.setEnabled(false);
    buttonsPanel.add(copy);
    redo = new JButton("Redo");
    redo.setBounds(105, 11, 91, 23);
    buttonsPanel.add(redo);
    install = new JButton("Install Shape");
    install.setBounds(10, 460, 186, 23);
    buttonsPanel.add(install);
    about = new JButton("About");
    about.setBounds(10, 494, 186, 23);
    buttonsPanel.add(about);
    addButtonsActionListeners();
  }

  /**
   * Adds the buttons action listeners.
   */
  public void addButtonsActionListeners() {
    load.addActionListener(new LoadActionListener(engine, this, panel, canvas));
    load.setToolTipText("Load a saved file");
    undo.addActionListener(new UndoActionListener(engine, panel, this, canvas));
    undo.setToolTipText("return to a old move");
    save.addActionListener(new SaveActionListner(engine));
    save.setToolTipText("Save the canvas in file");
    resize.addActionListener(new ResizeAction(menu, panel, engine, canvas, this));
    resize.setToolTipText("Change the size of selected shape");
    delete.addActionListener(new DeleteActionListener(engine, this, panel, canvas, menu));
    delete.setToolTipText("Delete the shape");
    color.addActionListener(new ColorActionListener(engine, this, panel, canvas, menu));
    color.setToolTipText("Change the stroke and fill color of the shape");
    moveTo.addActionListener(new MovingActionListener(engine, this, panel, canvas, menu));
    moveTo.setToolTipText("Move the shape to another place");
    copy.addActionListener(new CopyActionListener(engine, this, panel, canvas, menu));
    copy.setToolTipText("Make a copy of the shape");
    redo.addActionListener(new RedoActionListener(engine, this, panel, canvas, menu));
    redo.setToolTipText("Return The undo moves");
    copy.addActionListener(new CopyActionListener(engine, this, panel, canvas, menu));
    copy.setToolTipText("Make a copy of the shape");
    install.addActionListener(
        new InstallActionListener(engine, this, panel, canvas, menu, factory, myClassFinder));
    install.setToolTipText("Add a new shapes");
    about.addActionListener(new AboutActionListener());
    about.setToolTipText("Know who we are");
  }

  /**
   * Initialize frame.
   */
  private void initializeFrame() {
    frame = new JFrame();
    frame.setSize(1200, 730);
    frame.setLocationRelativeTo(null);
    frame.setTitle("Vector Drawing Application");
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    buttonsPanel = new JPanel();
    buttonsPanel.setBounds(10, 48, 206, 643);
    frame.getContentPane().setLayout(null);
    panel = new JPanel();
    panel.setSize(962, 643);
    panel.setLocation(222, 48);
    panel.setBackground(Color.white);
    frame.getContentPane().add(panel);
    frame.getContentPane().add(buttonsPanel);
    buttonsPanel.setLayout(null);
    menu = new JComboBox<Object>(new Object[] { "Choose Shape" });

    menu.setBounds(10, 146, 186, 23);
    buttonsPanel.add(menu);
    addButtons(buttonsPanel);
    menu.addItemListener(new MenuActionListener(copy, moveTo, color, delete, resize, menu));
    JLabel selectShape = new JLabel("Select Shape");
    selectShape.setBounds(10, 112, 186, 23);
    buttonsPanel.add(selectShape);
    JLabel lblShapes = new JLabel("Shapes");
    lblShapes.setFont(new Font("Arial Black", Font.BOLD, 14));
    lblShapes.setBounds(10, 11, 206, 27);
    frame.getContentPane().add(lblShapes);
  }

}
