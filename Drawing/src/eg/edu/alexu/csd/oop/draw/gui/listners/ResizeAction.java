package eg.edu.alexu.csd.oop.draw.gui.listners;

import eg.edu.alexu.csd.oop.draw.DrawingBoard;
import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.gui.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The Class ResizeAction.
 */
public class ResizeAction implements ActionListener {
  /** The panel. */
  private JPanel panel;
  /** The menu. */
  private JComboBox<Object> menu;

  /** The engine. */
  private DrawingEngine engine;

  /** The canvas. */
  DrawingBoard canvas;

  /** The gui window. */
  Gui guiWindow;

  /**
   * Instantiates a new resize action.
   *
   * @param menu
   *          the menu
   * @param panel
   *          the panel
   * @param engine
   *          the engine
   * @param canvas
   *          the canvas
   * @param guiWindow
   *          the gui window
   */
  public ResizeAction(JComboBox<Object> menu, JPanel panel, DrawingEngine engine,
      DrawingBoard canvas, Gui guiWindow) {
    this.panel = panel;
    this.menu = menu;
    this.engine = engine;
    this.canvas = canvas;
    this.guiWindow = guiWindow;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent arg0) {
    Shape oldShape = (Shape) menu.getSelectedItem();
    Map<String, Double> propertiesMap = oldShape.getProperties();
    JTextField[] fields = new JTextField[propertiesMap.size()];
    for (int counter = 0; counter < fields.length; counter++) {
      fields[counter] = new JTextField();
    }
    Object[] messages = new Object[fields.length * 2];
    Set<String> list = propertiesMap.keySet();
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
        propertiesMap.put(dummyString, Double.valueOf(fields[counter].getText().trim()));
        counter++;
      }
    }
    try {
      Shape newShape = (Shape) oldShape.clone();
      newShape.setProperties(propertiesMap);
      engine.updateShape(oldShape, newShape);
    } catch (CloneNotSupportedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    panel.add(canvas);
    guiWindow.refreshMenu();
    // then draw it on the panel

  }

}
