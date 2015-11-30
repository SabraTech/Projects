package eg.edu.alexu.csd.oop.draw.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import eg.edu.alexu.csd.oop.draw.ClassFinder;
import eg.edu.alexu.csd.oop.draw.DrawingBoard;
import eg.edu.alexu.csd.oop.draw.Factory;
import eg.edu.alexu.csd.oop.draw.MyEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * The Class MainWindow.
 */
public class MainWindow {

  /** The frame. */
  Box shapesBox;
  JFrame frame;
  ClassFinder myClassFinder = new ClassFinder(Shape.class);
  /** The redo. */
  // edit buttons
  private JButton delete, resize, color, moveTo, copy, undo, redo;

  /** The about. */
  // feature buttons
  private JButton save, load, install, about;

  /** The panel. */
  private JPanel panel;

  /** The menu. */
  private JComboBox<Object> menu;

  /** The engine. */
  MyEngine engine = new MyEngine();
  Factory factory = Factory.getInstance();

  private ActionDialogsScreen AD;

  /** The canvas. */
  DrawingBoard canvas = new DrawingBoard(engine);

  /**
   * Launch the application.
   *
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          MainWindow window = new MainWindow();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public MainWindow() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    factory = Factory.getInstance();
    factory.addShapes(engine.getSupportedShapes());
    frame = new JFrame();
    frame.setSize(1200, 730);
    frame.setLocationRelativeTo(null);
    frame.setTitle("Vector Drawing Application");
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // object of action buttons
    AD = new ActionDialogsScreen();

    // panel holds all buttons
    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setBounds(10, 48, 206, 643);
    frame.getContentPane().setLayout(null);

    panel = new JPanel();
    panel.setSize(962, 643);
    panel.setLocation(222, 48);
    panel.setBackground(Color.white);
    frame.getContentPane().add(panel);

    frame.getContentPane().add(buttonsPanel);
    buttonsPanel.setLayout(null);

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

    menu = new JComboBox<Object>(new Object[] { "Choose Shape" });
    menu.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent arg0) {
        // TODO Auto-generated method stub
        Object item = menu.getSelectedItem();
        if ("Choose Shape".equals(item)) {
          copy.setEnabled(false);
          moveTo.setEnabled(false);
          color.setEnabled(false);
          delete.setEnabled(false);
          resize.setEnabled(false);
        } else {
          copy.setEnabled(true);
          moveTo.setEnabled(true);
          color.setEnabled(true);
          delete.setEnabled(true);
          resize.setEnabled(true);
        }
      }
    });
    menu.setBounds(10, 146, 186, 23);
    buttonsPanel.add(menu);

    JLabel selectShape = new JLabel("Select Shape");
    selectShape.setBounds(10, 112, 186, 23);
    buttonsPanel.add(selectShape);

    // send buttons to action class
    redo.addActionListener(AD);
    redo.setToolTipText("Return The undo moves");
    copy.addActionListener(AD);
    copy.setToolTipText("Make a copy of the shape");
    moveTo.addActionListener(AD);
    moveTo.setToolTipText("Move the shape to another place");
    color.addActionListener(AD);
    color.setToolTipText("Change the stroke and fill color of the shape");
    delete.addActionListener(AD);
    delete.setToolTipText("Delete the shape");
    load.addActionListener(AD);
    load.setToolTipText("Load a saved file");
    undo.addActionListener(AD);
    undo.setToolTipText("return to a old move");
    save.addActionListener(AD);
    save.setToolTipText("Save the canvas in file");
    resize.addActionListener(AD);
    resize.setToolTipText("Change the size of selected shape");
    install.addActionListener(AD);
    install.setToolTipText("Add a new shapes");
    about.addActionListener(AD);
    about.setToolTipText("Know who we are");

    JLabel lblShapes = new JLabel("Shapes");
    lblShapes.setFont(new Font("Arial Black", Font.BOLD, 14));
    lblShapes.setBounds(10, 11, 206, 27);
    frame.getContentPane().add(lblShapes);

    createShapeButtons(engine.getSupportedShapes());

  }
  /*
   * END of initialize
   * 
   */

  /**
   * The Class ActionDialogsScreen.
   */
  /*
   * Listener of button action
   */
  private class ActionDialogsScreen implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      // TODO Auto-generated method stub
      JButton src = (JButton) arg0.getSource();
      if (src.equals(resize)) {
        Shape oldShape = (Shape) menu.getSelectedItem();
        Map<String, Double> trymap = oldShape.getProperties();
        JTextField[] fields = new JTextField[trymap.size()];
        for (int counter = 0; counter < fields.length; counter++) {
          fields[counter] = new JTextField();
        }
        Object[] messages = new Object[fields.length * 2];
        Set<String> list = trymap.keySet();
        int messagesCounter = 0;
        int fieldsCounter = 0;
        for (String dummyString : list) {
          messages[messagesCounter] = dummyString;
          messages[messagesCounter + 1] = fields[fieldsCounter];
          messagesCounter += 2;
          fieldsCounter++;
        }
        int option = JOptionPane.showConfirmDialog(null, messages, "Enter all your values",
            JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
          int counter = 0;
          for (String dummyString : list) {
            trymap.put(dummyString, Double.valueOf(fields[counter].getText().trim()));
            counter++;
          }
        }
        try {
          Shape newShape = (Shape) oldShape.clone();
          newShape.setProperties(trymap);
          engine.updateShape(oldShape, newShape);
        } catch (CloneNotSupportedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        panel.add(canvas);
        refreshMenu();
        // then draw it on the panel
      } else if (src.equals(save)) {
        String path = null;
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON file",
            new String[] { "json", "JSON" });
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("XML file",
            new String[] { "xml", "XML" });
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.addChoosableFileFilter(filter2);
        fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[0]);
        fileChooser.setDialogTitle("Choose Place to save in it");
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
          path = fileChooser.getSelectedFile().getPath().toString();
        }
        JOptionPane.showConfirmDialog(null, path);
        engine.save(path);
        // call the save() method
      } else if (src.equals(load)) {
        String path = null;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON file",
            new String[] { "json", "JSON" });
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("XML file",
            new String[] { "xml", "XML" });
        chooser.addChoosableFileFilter(filter);
        chooser.addChoosableFileFilter(filter2);
        chooser.setFileFilter(chooser.getChoosableFileFilters()[0]);
        // FileNameExtensionFilter filter = new
        // FileNameExtensionFilter("xml","XML","json","JSON");
        // chooser.setFileFilter(filter);
        chooser.setDialogTitle("Load File");
        int val = chooser.showOpenDialog(null);
        if (val == JFileChooser.APPROVE_OPTION) {
          path = chooser.getSelectedFile().getPath().toString();
        }
        JOptionPane.showConfirmDialog(null, path);
        engine.load(path);
        refreshMenu();
        panel.add(canvas);
        // call the load() method
      } else if (src.equals(undo)) {
        try {
          engine.undo();
          refreshMenu();
          panel.add(canvas);
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, "No history!");
        }
      } else if (src.equals(delete)) {
        Shape callShape = (Shape) menu.getSelectedItem();
        engine.removeShape(callShape);
        refreshMenu();
        panel.add(canvas);
      } else if (src.equals(color)) {
        Color color = Color.black;
        color = JColorChooser.showDialog(null, "Choose Stroke Color", color);
        Color fillColor = Color.black;
        fillColor = JColorChooser.showDialog(null, "choose Fill Color", fillColor);
        Shape oldShape = (Shape) menu.getSelectedItem();
        try {
          Shape newShape = (Shape) oldShape.clone();
          newShape.setColor(color);
          newShape.setFillColor(fillColor);
          engine.updateShape(oldShape, newShape);
          panel.add(canvas);
          refreshMenu();
        } catch (CloneNotSupportedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      } else if (src.equals(moveTo)) {
        Shape oldShape = (Shape) menu.getSelectedItem();
        String positionX = null;
        String positionY = null;
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        Object[] message = { "PositionX", field1, "PositionY", field2 };
        int option = JOptionPane.showConfirmDialog(null, message, "Copy to ...",
            JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
          positionX = field1.getText();
          positionY = field2.getText();
        }
        Point newPoint = new Point(Integer.parseInt(positionX), Integer.parseInt(positionY));
        try {
          Shape newShape = (Shape) oldShape.clone();
          newShape.setPosition(newPoint);
          engine.updateShape(oldShape, newShape);
        } catch (CloneNotSupportedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        refreshMenu();
        panel.add(canvas);
      } else if (src.equals(copy)) {
        Shape oldShape = (Shape) menu.getSelectedItem();
        String positionX = null;
        String positionY = null;
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        Object[] message = { "PositionX", field1, "PositionY", field2 };
        int option = JOptionPane.showConfirmDialog(null, message, "Copy to ...",
            JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
          positionX = field1.getText();
          positionY = field2.getText();
        }
        Point newPoint = new Point(Integer.parseInt(positionX), Integer.parseInt(positionY));
        try {
          Shape newShape = (Shape) oldShape.clone();
          newShape.setPosition(newPoint);
          engine.addShape(newShape);
        } catch (CloneNotSupportedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        refreshMenu();
        panel.add(canvas);
      } else if (src.equals(redo)) {
        try {
          engine.redo();
          refreshMenu();
          panel.add(canvas);
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, "No next!");
        }

      } else if (src.equals(install)) {
        String path = null;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Jar File",
            new String[] { "jar", "JAR" });
        chooser.addChoosableFileFilter(filter);
        chooser.setFileFilter(chooser.getChoosableFileFilters()[0]);
        chooser.setDialogTitle("Choose Plugin");
        int val = chooser.showOpenDialog(null);
        if (val == JFileChooser.APPROVE_OPTION) {
          path = chooser.getSelectedFile().getPath().toString();
        }
        LinkedList<Class<? extends Shape>> newShapesList = new LinkedList<>(); // =
                                                                               // myClassFinder.getPlugin(path);
        Set<Class<? extends Shape>> pluginShapes = myClassFinder.getPluginShapes(path);
        for (Class<? extends Shape> dummy : pluginShapes) {
          System.out.println("the list contains" + dummy);
          newShapesList.add(dummy);
        }
        for (Class<? extends Shape> dummy : engine.getSupportedShapes()) {
          newShapesList.add(dummy);
        }
        removeShapesBox();
        createShapeButtons(newShapesList);
        factory.addShapes(newShapesList);

        // frame.repaint();
        // EventQueue.invokeLater(new Runnable() {
        // public void run() {
        // try {
        // InstallWindow window = new InstallWindow(engine);
        // window.frame1.setVisible(true);
        // } catch (Exception e) {
        // System.out.println(e + " at the install");
        // }
        // }
        // });
      } else if (src.equals(about)) {
        JOptionPane.showMessageDialog(null,
            "Developers: Mohamed Sabra and Mohamed Atef"
                + "\nProgramming Language Used: Java\nVersion: 1.0",
            "Info", JOptionPane.INFORMATION_MESSAGE);
      } else {
        Color color = Color.black;
        Color fillColor = Color.black;
        color = JColorChooser.showDialog(null, "Choose Stroke Color", color);
        fillColor = JColorChooser.showDialog(null, "Choose Fill Color", fillColor);
        Shape shape = factory.getShapeBySimpleName(src.getText());
        String positionX = null;
        String positionY = null;
        Map<String, Double> propertiesMap = shape.getProperties();
        JTextField[] fields = new JTextField[propertiesMap.size() + 2];
        for (int counter = 0; counter < fields.length; counter++) {
          fields[counter] = new JTextField();
        }
        Object[] messages = new Object[fields.length * 2];
        Set<String> list = propertiesMap.keySet();
        messages[0] = "PositionX";
        messages[1] = fields[0];
        messages[2] = "PositionY";
        messages[3] = fields[1];
        int messagesCounter = 4;
        int fieldsCounter = 2;
        for (String dummyString : list) {
          messages[messagesCounter] = dummyString;
          messages[messagesCounter + 1] = fields[fieldsCounter];
          messagesCounter += 2;
          fieldsCounter++;
        }
        int option = JOptionPane.showConfirmDialog(null, messages, "Enter all your values",
            JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
          positionX = fields[0].getText();
          positionY = fields[1].getText();
          int counter = 2;
          for (String dummyString : list) {
            propertiesMap.put(dummyString, Double.valueOf(fields[counter].getText().trim()));
            counter++;
          }
        }
        shape.setProperties(propertiesMap);
        Point point = new Point(Integer.parseInt(positionX), Integer.parseInt(positionY));
        shape.setColor(color);
        shape.setFillColor(fillColor);
        shape.setPosition(point);
        engine.addShape(shape);
        menu.addItem(shape);
        panel.add(canvas);
      }
    }
  }
  /*
   * END Listener of button action
   */

  /*
   * update the menu
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

  public void createShapeButtons(List<Class<? extends Shape>> shapeList) {
    if (shapesBox != null) {
      frame.remove(shapesBox);
    }
    shapesBox = Box.createHorizontalBox();
    shapesBox.setBounds(222, 11, 962, 27);

    JButton[] shapesButtons = new JButton[shapeList.size()];
    int counter = 0;
    for (Class<? extends Shape> shape : shapeList) {
      System.out.println(shape);
      shapesButtons[counter] = new JButton(shape.getSimpleName());
      shapesButtons[counter].addActionListener(AD);
      shapesButtons[counter].setToolTipText("Draw a " + shape.getSimpleName());
      shapesBox.add(shapesButtons[counter]);
      counter++;
    }
    frame.getContentPane().add(shapesBox);
    shapesBox.setVisible(true);
    // frame.repaint();
  }

  /*
   * END update the menu
   */
  public void removeShapesBox() {
    shapesBox.setVisible(false);
  }
}
