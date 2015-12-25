package eg.edu.alexu.csd.oop.game.characters.falling.state;

import eg.edu.alexu.csd.oop.game.characters.players.Player;

public class FreeState implements State {
  private int x, y;
  private int width, height;

  public FreeState(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public State nextState(int event) {
    if (event == State.EVENT_LEFTHAND_COLLECTED) {
      State next = new LeftHandCaptured(x, y);
      next.setDimensions(width, height);
      return next;
    } else if (event == State.EVENT_RIGHTHAND_COLLECTED) {
      State next = new RightHandCaptured(x, y);
      next.setDimensions(width, height);
      return next;
    } else {
      return this;
    }
  }

  @Override
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public void setY(int y) {
    this.y = y;
  }

  @Override
  public void setXLimits(Player clown) {
    // no limits in this state;
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
    this.height = height;
  }

}
