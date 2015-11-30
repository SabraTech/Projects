package eg.edu.alexu.csd.oop.draw.gui.listners;

import eg.edu.alexu.csd.oop.draw.DrawingBoard;
import eg.edu.alexu.csd.oop.draw.MyEngine;
import eg.edu.alexu.csd.oop.draw.gui.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The Class LoadActionListner.
 */
public class LoadActionListener implements ActionListener {

  /** The gui window. */
  private Gui guiWindow;

  /** The engine. */
  private MyEngine engine;

  /** The panel. */
  private JPanel panel;

  /** The canvas. */
  private DrawingBoard canvas;

  /**
   * Instantiates a new load action listener.
   *
   * @param engine
   *          the engine
   * @param guiWindow
   *          the gui window
   * @param panel
   *          the panel
   * @param canvas
   *          the canvas
   */
  public LoadActionListener(MyEngine engine, Gui guiWindow, JPanel panel, DrawingBoard canvas) {
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
  public void actionPerformed(ActionEvent event) {
    String path = null;
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON file",
        new String[] { "json", "JSON" });
    FileNameExtensionFilter filter2 = new FileNameExtensionFilter("XML file",
        new String[] { "xml", "XML" });
    chooser.addChoosableFileFilter(filter);
    chooser.addChoosableFileFilter(filter2);
    chooser.setFileFilter(chooser.getChoosableFileFilters()[0]);
    chooser.setDialogTitle("Load File");
    int value = chooser.showOpenDialog(null);
    if (value == JFileChooser.APPROVE_OPTION) {
      path = chooser.getSelectedFile().getPath().toString();
    }
    JOptionPane.showConfirmDialog(null, path);
    engine.load(path);
    guiWindow.refreshMenu();
    panel.add(canvas);
  }

}
