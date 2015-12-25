package eg.edu.alexu.csd.oop.game.listeners;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import eg.edu.alexu.csd.oop.game.GameEngine.GameController;
import eg.edu.alexu.csd.oop.game.characters.players.ObserverIF;


public abstract class GameLevels implements ActionListener, ObserverIF {

  protected GameController gameController;

  @Override
  public abstract void actionPerformed(ActionEvent e);

  @Override
  public abstract void notify(int event);

}
