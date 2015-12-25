package eg.edu.alexu.csd.oop.game.characters.players;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.characters.falling.FallingCharacter;

public abstract class Player implements GameObject, ObserverIF, Serializable, Cloneable {

  /**
   * 
   */
  private static final long serialVersionUID = 6657600023113538195L;
  protected static final int MAX_MSTATE = 1;
  // an array of sprite images that are drawn sequentially
  protected BufferedImage[] spiriteImages = new BufferedImage[MAX_MSTATE];
  protected int x;
  protected int y;
  protected boolean visible;
  protected boolean isCarrying;
  protected PlayerState rightHandState;
  protected PlayerState leftHandState;
  protected int maxX;
  protected int minX;

  // get notification which hand set free
  public void notify(int hand) {
    if (hand == FallingCharacter.RIGHT_HAND) {
      rightHandState = rightHandState.nextState(PlayerState.EVENT_FREE);
    } else if (hand == FallingCharacter.LEFT_HAND) {
      leftHandState = leftHandState.nextState(PlayerState.EVENT_FREE);
    }
  }

  public PlayerState getRightHandState() {
    return this.rightHandState;
  }

  public void setRightHandState(int event) {
    this.rightHandState = rightHandState.nextState(event);
  }

  public PlayerState getLeftHandState() {
    return this.leftHandState;
  }

  public void setLeftHandState(int event) {
    this.leftHandState = leftHandState.nextState(event);
  }

  public boolean rightIsFree() {
    return rightHandState.isFree();
  }

  public boolean leftIsFree() {
    return leftHandState.isFree();
  }

  @Override
  public final int getX() {
    return this.x;
  }

  @Override
  public final void setX(int x) {
    if (x <= this.minX) {
      this.x = this.minX;
    } else if (x >= this.maxX) {
      this.x = this.maxX;
    } else {
      this.x = x;
    }
  }

  @Override
  public final int getY() {
    return this.y;
  }

  @Override
  public final void setY(int y) {

  }

  @Override
  public int getWidth() {
    return spiriteImages[0].getWidth();
  }

  @Override
  public int getHeight() {
    return spiriteImages[0].getHeight();
  }

  @Override
  public boolean isVisible() {
    return this.visible;
  }

  @Override
  public final BufferedImage[] getSpriteImages() {
    return spiriteImages;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public abstract int getLeftHandX();

  public abstract int getLeftHandY();

  public abstract int getRightHandX();

  public abstract int getRightHandY();

  public final boolean isCarrying() {
    return isCarrying;
  }

  public final void carry() {
    isCarrying = true;
  }

  public final void setMinX(int x) {
    this.minX = x;
  }

  public final void setMaxX(int x) {
    this.maxX = x;
  }

  public abstract Object clone();

  public void setLeftHandState(PlayerState s) {
    this.leftHandState = s;
  }

  public void setRightHandState(PlayerState s) {
    this.rightHandState = s;
  }
}
