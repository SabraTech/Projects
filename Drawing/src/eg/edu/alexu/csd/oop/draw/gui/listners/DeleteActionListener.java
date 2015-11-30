package eg.edu.alexu.csd.oop.draw.gui.listners;

import eg.edu.alexu.csd.oop.draw.DrawingBoard;
import eg.edu.alexu.csd.oop.draw.MyEngine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.gui.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * The listener interface for receiving deleteAction events. The class that is interested in
 * processing a deleteAction event implements this interface, and the object created with that class
 * is registered with a component using the component's <code>addDeleteActionListener</code> method.
 * When the deleteAction event occurs, that object's appropriate method is invoked.
 *
 * @see DeleteActionEvent
 */
public class DeleteActionListener implements ActionListener {

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
   * Instantiates a new delete action listener.
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
  public DeleteActionListener(MyEngine engine, Gui guiWindow, JPanel panel, DrawingBoard canvas,
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
    Shape callShape = (Shape) menu.getSelectedItem();
    engine.removeShape(callShape);
    guiWindow.refreshMenu();
    panel.add(canvas);
  }

}
