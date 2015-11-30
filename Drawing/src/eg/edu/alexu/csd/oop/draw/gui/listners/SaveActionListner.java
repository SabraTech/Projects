package eg.edu.alexu.csd.oop.draw.gui.listners;

import eg.edu.alexu.csd.oop.draw.MyEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The Class SaveActionListner.
 */
public class SaveActionListner implements ActionListener {

  /** The engine. */
  private MyEngine engine;

  /**
   * Instantiates a new save action listner.
   *
   * @param engine
   *          the engine
   */
  public SaveActionListner(MyEngine engine) {
    this.engine = engine;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent arg0) {
    // TODO Auto-generated method stub
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
  }

}
