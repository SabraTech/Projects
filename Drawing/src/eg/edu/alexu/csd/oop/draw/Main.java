package eg.edu.alexu.csd.oop.draw;

import eg.edu.alexu.csd.oop.draw.gui.Gui;

import java.awt.EventQueue;

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
          Gui window = new Gui();
          window.getFrame().setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

}
