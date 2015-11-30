package eg.edu.alexu.csd.oop.draw.gui.listners;

import eg.edu.alexu.csd.oop.draw.DrawingBoard;
import eg.edu.alexu.csd.oop.draw.MyEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.gui.Gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The listener interface for receiving movingAction events. The class that is interested in
 * processing a movingAction event implements this interface, and the object created with that class
 * is registered with a component using the component's <code>addMovingActionListener</code> method.
 * When the movingAction event occurs, that object's appropriate method is invoked.
 *
 * @see MovingActionEvent
 */
public class MovingActionListener implements ActionListener {
  /** The engine. */
  private MyEngine engine;

  /** The gui window. */
  private Gui guiWindow;

  /** The panel. */
  private JPanel panel;

  /** The canvas. */
  private DrawingBoard canvas;

  /** The menu. */
  private JComboBox<Object> menu;

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
  public MovingActionListener(MyEngine engine, Gui guiWindow, JPanel panel, DrawingBoard canvas,
      JComboBox<Object> menu) {
    this.engine = engine;
    this.guiWindow = guiWindow;
    this.panel = panel;
    this.canvas = canvas;
    this.menu = menu;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent action) {
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
    } catch (CloneNotSupportedException exception) {
      // TODO Auto-generated catch block
      exception.printStackTrace();
    }
    guiWindow.refreshMenu();
    panel.add(canvas);
  }

}
