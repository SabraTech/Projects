package eg.edu.alexu.csd.oop.draw.gui.listners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * The listener interface for receiving aboutAction events. The class that is interested in
 * processing a aboutAction event implements this interface, and the object created with that class
 * is registered with a component using the component's <code>addAboutActionListener</code> method.
 * When the aboutAction event occurs, that object's appropriate method is invoked.
 *
 * @see AboutActionEvent
 */
public class AboutActionListener implements ActionListener {

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent action) {
    // TODO Auto-generated method stub
    JOptionPane.showMessageDialog(null,
        "Developers: Mohamed Sabra and Mohamed Atef"
            + "\nProgramming Language Used: Java\nVersion: 1.0",
        "Info", JOptionPane.INFORMATION_MESSAGE);
  }

}
