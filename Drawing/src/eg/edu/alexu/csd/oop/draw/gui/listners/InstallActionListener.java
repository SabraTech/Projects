package eg.edu.alexu.csd.oop.draw.gui.listners;

import eg.edu.alexu.csd.oop.draw.ClassFinder;
import eg.edu.alexu.csd.oop.draw.DrawingBoard;
import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Factory;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.gui.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The listener interface for receiving installAction events. The class that is interested in
 * processing a installAction event implements this interface, and the object created with that
 * class is registered with a component using the component's <code>addInstallActionListener
 * </code> method. When the installAction event occurs, that object's appropriate method is invoked.
 *
 * @see InstallActionEvent
 */
public class InstallActionListener implements ActionListener {
  /** The engine. */
  private DrawingEngine engine;

  /** The gui window. */
  private Gui guiWindow;

  /** The factory. */
  private Factory factory;

  /** The my class finder. */
  private ClassFinder myClassFinder;

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
   * @param factory
   *          the factory
   * @param myClassFinder
   *          the my class finder
   */
  public InstallActionListener(DrawingEngine engine, Gui guiWindow, JPanel panel,
      DrawingBoard canvas, JComboBox<Object> menu, Factory factory, ClassFinder myClassFinder) {
    this.engine = engine;
    this.guiWindow = guiWindow;
    this.factory = factory;
    this.myClassFinder = myClassFinder;
  }

  /**
   * Action performed.
   *
   * @param action
   *          the action
   */
  @Override
  public void actionPerformed(ActionEvent action) {
    // TODO Auto-generated method stub
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
    LinkedList<Class<? extends Shape>> newShapesList = new LinkedList<>();
    Set<Class<? extends Shape>> pluginShapes = myClassFinder.getPluginShapes(path);
    for (Class<? extends Shape> dummy : pluginShapes) {
      newShapesList.add(dummy);
    }
    for (Class<? extends Shape> dummy : engine.getSupportedShapes()) {
      newShapesList.add(dummy);
    }
    guiWindow.removeShapesBox();
    guiWindow.createShapeButtons(newShapesList);
    factory.addShapes(newShapesList);
  }

}
