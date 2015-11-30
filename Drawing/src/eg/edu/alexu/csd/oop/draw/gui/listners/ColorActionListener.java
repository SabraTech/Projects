
package eg.edu.alexu.csd.oop.draw.gui.listners;

import eg.edu.alexu.csd.oop.draw.DrawingBoard;
import eg.edu.alexu.csd.oop.draw.MyEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.gui.Gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * The listener interface for receiving colorAction events. The class that is interested in
 * processing a colorAction event implements this interface, and the object created with that class
 * is registered with a component using the component's <code>addColorActionListener</code> method.
 * When the colorAction event occurs, that object's appropriate method is invoked.
 *
 * @see ColorActionEvent
 */
public class ColorActionListener implements ActionListener {
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
  public ColorActionListener(MyEngine engine, Gui guiWindow, JPanel panel, DrawingBoard canvas,
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
    // TODO Auto-generated method stub
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
      guiWindow.refreshMenu();
    } catch (CloneNotSupportedException exception) {
      // TODO Auto-generated catch block
      exception.printStackTrace();
    }
  }

}
