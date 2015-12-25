package eg.edu.alexu.csd.oop.game.characters.falling.state;

import eg.edu.alexu.csd.oop.game.characters.players.Player;

public class LeftHandCaptured implements State {
  private int x;
  private int y;
  private int width;
  private Player clown;

  public LeftHandCaptured(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public State nextState(int event) {
    if (event == State.EVENT_SET_FREE) {
      return new FreeState(x, y);
    }
    return this;
  }

  @Override
  public void setX(int x) {
    this.x = clown.getLeftHandX() - width / 2;
  }

  @Override
  public void setY(int y) {// y shouldn't be changed

  }

  @Override
  public void setXLimits(Player clown) {
    this.clown = clown;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public void setDimensions(int width, int height) {
    this.width = width;
  }
}
