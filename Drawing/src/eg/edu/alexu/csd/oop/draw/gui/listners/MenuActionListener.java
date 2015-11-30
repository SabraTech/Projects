package eg.edu.alexu.csd.oop.draw.gui.listners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 * The listener interface for receiving menuAction events. The class that is interested in
 * processing a menuAction event implements this interface, and the object created with that class
 * is registered with a component using the component's <code>addMenuActionListener</code> method.
 * When the menuAction event occurs, that object's appropriate method is invoked.
 *
 * @see MenuActionEvent
 */
public class MenuActionListener implements ItemListener {

  /** The copy. */
  private JButton copy;

  /** The move to. */
  private JButton moveTo;

  /** The color. */
  private JButton color;

  /** The delete. */
  private JButton delete;

  /** The resize. */
  private JButton resize;

  /** The menu. */
  private JComboBox<Object> menu;

  /**
   * Instantiates a new menu action listener.
   *
   * @param copy
   *          the copy
   * @param moveTo
   *          the move to
   * @param color
   *          the color
   * @param delete
   *          the delete
   * @param resize
   *          the resize
   * @param menu
   *          the menu
   */
  public MenuActionListener(JButton copy, JButton moveTo, JButton color, JButton delete,
      JButton resize, JComboBox<Object> menu) {
    // TODO Auto-generated constructor stub
    this.copy = copy;
    this.moveTo = moveTo;
    this.color = color;
    this.delete = delete;
    this.resize = resize;
    this.menu = menu;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
   */
  @Override
  public void itemStateChanged(ItemEvent arg0) {
    // TODO Auto-generated method stub
    Object item = menu.getSelectedItem();
    if ("Choose Shape".equals(item)) {
      copy.setEnabled(false);
      moveTo.setEnabled(false);
      color.setEnabled(false);
      delete.setEnabled(false);
      resize.setEnabled(false);
    } else {
      copy.setEnabled(true);
      moveTo.setEnabled(true);
      color.setEnabled(true);
      delete.setEnabled(true);
      resize.setEnabled(true);
    }
  }
}
