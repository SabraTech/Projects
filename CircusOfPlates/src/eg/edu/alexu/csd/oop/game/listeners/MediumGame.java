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
import eg.edu.alexu.csd.oop.game.world.ExtendableWorld;
import eg.edu.alexu.csd.oop.game.world.HardWorld;
import eg.edu.alexu.csd.oop.game.world.MediumWorld;
import eg.edu.alexu.csd.oop.game.world.WorldItrator;

public class MediumGame extends GameLevels {

  private GameController gameController = null;

  @Override
  public void actionPerformed(ActionEvent e) {
    GameWindow.log.info("The user choose medium level to play");
    GameWindow.log.debug("medium level has started");
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Game Option");
    JMenuItem newMenuItem = new JMenuItem("New Game");
    JMenuItem pauseMenuItem = new JMenuItem("Pause");
    JMenuItem resumeMenuItem = new JMenuItem("Resume");
    menu.add(newMenuItem);
    menu.addSeparator();
    menu.add(pauseMenuItem);
    menu.add(resumeMenuItem);
    menuBar.add(menu);
    World mediumWorld = new MediumWorld(780, 580);
    ((MediumWorld) mediumWorld).addObserver(this);
    gameController = GameEngine.start("----", mediumWorld, menuBar, JFrame.DISPOSE_ON_CLOSE, Color.WHITE);
    newMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        gameController.changeWorld(new eg.edu.alexu.csd.oop.game.world.MediumWorld(780, 580));
      }
    });
    pauseMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        GameWindow.log.info("The user choose to pasue the game");
        gameController.pause();
      }
    });
    resumeMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
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

    // if(event == 2){
    // GameWindow.log.debug("The Game leveled up to next level");
    // World hardWorld = new HardWorld(800,800);
    // ((HardWorld) hardWorld).addObserver(this);
    // gameController.changeWorld(hardWorld);
    // }else if(event == 3){
    // GameWindow.log.debug("The Game finiesh will win all levels");
    // JOptionPane.showConfirmDialog(null, "You win all levels");
    // }
  }

}
