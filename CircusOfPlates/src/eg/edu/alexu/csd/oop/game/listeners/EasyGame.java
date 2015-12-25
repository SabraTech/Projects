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
import eg.edu.alexu.csd.oop.game.GameEngine.GameController;
import eg.edu.alexu.csd.oop.game.GameWindow;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.characters.players.ObserverIF;
import eg.edu.alexu.csd.oop.game.world.EasyWorld;
import eg.edu.alexu.csd.oop.game.world.ExtendableWorld;
import eg.edu.alexu.csd.oop.game.world.HardWorld;
import eg.edu.alexu.csd.oop.game.world.MediumWorld;
import eg.edu.alexu.csd.oop.game.world.WorldItrator;

public class EasyGame extends GameLevels{
  
  private GameController gameController = null;
  
  @Override
  public void actionPerformed(ActionEvent e) {
    GameWindow.log.info("The user choose easy level to play");
    GameWindow.log.debug("easy level has started");
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
    World easyWorld = new EasyWorld(780, 580);
    ((EasyWorld) easyWorld).addObserver(this);
    gameController = GameEngine.start("----", easyWorld, menuBar,JFrame.DISPOSE_ON_CLOSE, Color.WHITE);
    newMenuItem.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
          gameController.changeWorld(new eg.edu.alexu.csd.oop.game.world.EasyWorld(780, 580));
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
    
    WorldItrator it = new WorldItrator();
    for (int counter = 0; counter < event; counter++) {
      it.next();
    }
    
    World world = it.next();
    ((ExtendableWorld) world).addObserver(this);
    gameController.changeWorld(world);
    
//    if(event == 1){
//      GameWindow.log.debug("The Game leveled up to next level");
//      World mediumWorld = new MediumWorld(800,800);
//      ((MediumWorld) mediumWorld).addObserver(this);
//      gameController.changeWorld(mediumWorld);
//    }else if(event == 2){
//      GameWindow.log.debug("The Game leveled up to next level");
//      World hardWorld = new HardWorld(800,800);
//      ((HardWorld) hardWorld).addObserver(this);
//      gameController.changeWorld(hardWorld);
//    }else if(event == 3){
//      GameWindow.log.debug("The Game finiesh will win all levels");
//      JOptionPane.showConfirmDialog(null, "You win all levels");
//    }
    
  }

}
