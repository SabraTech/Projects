package eg.edu.alexu.csd.oop.game.characters.falling.state;

import eg.edu.alexu.csd.oop.game.characters.players.Player;

public interface State {
  public static final int EVENT_SET_FREE = 0;
  public static final int EVENT_LEFTHAND_COLLECTED = 1;
  public static final int EVENT_RIGHTHAND_COLLECTED = 2;

  public State nextState(int event);

  public void setX(int x);

  public void setY(int y);

  public void setDimensions(int width, int height);

  public void setXLimits(Player clown);

  public int getX();

  public int getY();

}
