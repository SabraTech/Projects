package eg.edu.alexu.csd.oop.jdbc;

import java.awt.EventQueue;

import javax.swing.UIManager;

import eg.edu.alexu.csd.oop.jdbc.gui.Gui;


public class Main {
  
  /**
   * Launch the application.
   *
   * @param args
   *          the arguments
   */

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
          Gui window = new Gui();
          window.getFrame().setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

}
