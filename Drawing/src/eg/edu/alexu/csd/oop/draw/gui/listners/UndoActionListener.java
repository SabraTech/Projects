package eg.edu.alexu.csd.oop.draw.gui.listners;

import eg.edu.alexu.csd.oop.draw.DrawingBoard;
import eg.edu.alexu.csd.oop.draw.MyEngine;
import eg.edu.alexu.csd.oop.draw.gui.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The Class UndoActionListner.
 */
public class UndoActionListener implements ActionListener {

  /** The engine. */
  private MyEngine engine;

  /** The panel. */
  private JPanel panel;

  /** The gui window. */
  private Gui guiWindow;

  /** The canvas. */
  private DrawingBoard canvas;

  /**
   * Instantiates a new undo action listener.
   *
   * @param engine
   *          the engine
   * @param panel
   *          the panel
   * @param guiWindow
   *          the gui window
   * @param canvas
   *          the canvas
   */
  public UndoActionListener(MyEngine engine, JPanel panel, Gui guiWindow, DrawingBoard canvas) {
    this.engine = engine;
    this.panel = panel;
    this.guiWindow = guiWindow;
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
      engine.undo();
      guiWindow.refreshMenu();
      panel.add(canvas);
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(null, "No history!");
    }

  }
}