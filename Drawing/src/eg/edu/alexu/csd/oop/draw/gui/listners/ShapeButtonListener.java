package eg.edu.alexu.csd.oop.draw.gui.listners;

import eg.edu.alexu.csd.oop.draw.DrawingBoard;
import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Factory;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.gui.Gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ShapeButtonListener implements ActionListener {
  /** The engine. */
  private DrawingEngine engine;

  /** The panel. */
  private JPanel panel;

  /** The canvas. */
  private DrawingBoard canvas;

  /** The menu. */
  private JComboBox<Object> menu;
  private Factory factory;
  private JButton sourceButton;

  /**
   * Instantiates a new color action listener.
   *
   * @param engine
   *          the engine
   * @param guiWindow
   *          the gui window
   * @param panel
   *          the panel
   * @param canvas
   *          the canvas
   * @param menu
   *          the menu
   */
  public ShapeButtonListener(DrawingEngine engine, Gui guiWindow, JPanel panel,
      DrawingBoard canvas, JComboBox<Object> menu, JButton sourceButton, Factory factory) {
    this.engine = engine;
    this.panel = panel;
    this.canvas = canvas;
    this.menu = menu;
    this.sourceButton = sourceButton;
    this.factory = factory;
  }

  @Override
  public void actionPerformed(ActionEvent action) {
    Color color = Color.black;
    Color fillColor = Color.black;
    color = JColorChooser.showDialog(null, "Choose Stroke Color", color);
    fillColor = JColorChooser.showDialog(null, "Choose Fill Color", fillColor);
    Shape shape = factory.getShapeBySimpleName(sourceButton.getText());
    String positionX = null;
    String positionY = null;
    Map<String, Double> propertiesMap = shape.getProperties();
    if (propertiesMap != null) {
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
    }

    Point point = new Point(Integer.parseInt(positionX), Integer.parseInt(positionY));
    shape.setColor(color);
    shape.setFillColor(fillColor);
    shape.setPosition(point);
    engine.addShape(shape);
    menu.addItem(shape);
    panel.add(canvas);
  }

}
