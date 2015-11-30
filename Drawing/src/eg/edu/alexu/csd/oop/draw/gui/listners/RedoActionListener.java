package eg.edu.alexu.csd.oop.draw.gui.listners;

import eg.edu.alexu.csd.oop.draw.DrawingBoard;
import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.gui.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The listener interface for receiving redoAction events. The class that is interested in
 * processing a redoAction event implements this interface, and the object created with that class
 * is registered with a component using the component's <code>addRedoActionListener</code> method.
 * When the redoAction event occurs, that object's appropriate method is invoked.
 *
 * @see RedoActionEvent
 */
public class RedoActionListener implements ActionListener {
  /** The engine. */
  private DrawingEngine engine;

  /** The gui window. */
  private Gui guiWindow;

  /** The panel. */
  private JPanel panel;

  /** The canvas. */
  private DrawingBoard canvas;

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
  public RedoActionListener(DrawingEngine engine, Gui guiWindow, JPanel panel,
      DrawingBoard canvas, JComboBox<Object> menu) {
    this.engine = engine;
    this.guiWindow = guiWindow;
    this.panel = panel;
    this.canvas = canvas;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent action) {
    // TODO Auto-generated method stub
    try {
      engine.redo();
      guiWindow.refreshMenu();
      panel.add(canvas);
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(null, "No next!");
    }
  }

}
