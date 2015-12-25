package eg.edu.alexu.csd.oop.game.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import eg.edu.alexu.csd.oop.game.GameEngine;
import eg.edu.alexu.csd.oop.game.GameWindow;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.GameEngine.GameController;
import eg.edu.alexu.csd.oop.game.world.EasyWorld;
import eg.edu.alexu.csd.oop.game.world.HardWorld;
import eg.edu.alexu.csd.oop.game.world.MediumWorld;

public class HardGame extends GameLevels {
  
  private GameController gameController = null;

  @Override
  public void actionPerformed(ActionEvent e) {
    GameWindow.log.info("The user choose hard level to play");
    GameWindow.log.debug("hard level has started");
    JMenuBar  menuBar = new JMenuBar();
    JMenu menu = new JMenu("Game Option");
    JMenuItem newMenuItem = new JMenuItem("New Game");
    JMenuItem pauseMenuItem = new JMenuItem("Pause");
    JMenuItem resumeMenuItem = new JMenuItem("Resume");
    menu.add(newMenuItem);
    menu.addSeparator();
    menu.add(pauseMenuItem);
    menu.add(resumeMenuItem);
    menuBar.add(menu);
    World hardWorld = new HardWorld(980, 700);
    ((HardWorld) hardWorld).addObserver(this);
    gameController = GameEngine.start("----", hardWorld, menuBar,JFrame.DISPOSE_ON_CLOSE, Color.WHITE);
    newMenuItem.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
          gameController.changeWorld(new eg.edu.alexu.csd.oop.game.world.HardWorld(780, 580));
        }
      });
    pauseMenuItem.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        GameWindow.log.info("The user choose to pasue the game");
          gameController.pause();
        }
      });
      resumeMenuItem.addActionListener(new ActionListener() {
        @Override public void actionPerformed(ActionEvent e) {
          GameWindow.log.info("The user choose to resume");
          gameController.resume();
        }
      });
    
  }

  @Override
  public void notify(int event) {
  if(event == 3){
    GameWindow.log.debug("The Game finiesh will win all levels");
    }
  }

}
