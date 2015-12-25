package eg.edu.alexu.csd.oop.game.characters.falling;

import java.awt.image.BufferedImage;
import java.util.Random;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.characters.falling.state.FreeState;
import eg.edu.alexu.csd.oop.game.characters.falling.state.State;
import eg.edu.alexu.csd.oop.game.characters.players.ObserverIF;
import eg.edu.alexu.csd.oop.game.characters.players.Player;

public abstract class FallingCharacter implements GameObject, Cloneable {// this
                                                                         // is
                                                                         // the
  // class that
  // all
  // the falling
  public static final int MAX_MSTATE = 1; // characters will
  public static final int RIGHT_HAND = 1;
  public static final int LEFT_HAND = 2;

  // extend
  // all variables are protected for the subclasses to
  // be able to use
  protected State state;
  protected boolean isVisible;
  protected BufferedImage[] spirits;
  protected int color;
  public static int COLOR_RED = 0;
  public static int COLOR_PINK = 1;
  public static int COLOR_Blue = 2;
  // its observer
  private ObserverIF observer;

  public FallingCharacter() {
    spirits = new BufferedImage[1];
    state = new FreeState(new Random().nextInt(600), new Random().nextInt(600));
    isVisible = true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.game.GameObject#getX()
   */
  @Override
  public final int getX() {
    return state.getX();
  }

  public final void setImage(BufferedImage image) {
    spirits[0] = image;
    state.setDimensions(spirits[0].getWidth(), spirits[0].getHeight());
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.game.GameObject#setX(int)
   */
  @Override
  public final void setX(int x) {
    state.setX(x);
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.game.GameObject#getY()
   */
  @Override
  public final int getY() {
    return state.getY();
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.game.GameObject#setY(int)
   */
  @Override
  public final void setY(int y) {
    state.setY(y);
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.game.GameObject#getWidth()
   */
  @Override
  public final int getWidth() {
    return spirits[0].getWidth();
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.game.GameObject#getHeight()
   */
  @Override
  public final int getHeight() {
    return spirits[0].getHeight();
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.game.GameObject#isVisible()
   */
  @Override
  public final boolean isVisible() {
    return isVisible;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.oop.game.GameObject#getSpriteImages()
   */
  @Override
  public final BufferedImage[] getSpriteImages() {
    return spirits;
  }

  public final void setVisible(boolean b) {
    this.isVisible = b;
  }

  public final int getColor() {
    return color;
  }

  public final void setColor(int color) {
    this.color = color;
  }

  public void setSpirit(BufferedImage image) {
    this.spirits[0] = image;
  }

  public void processEvent(Player clown, int event, int hand) {
    if (event == State.EVENT_SET_FREE) {
      notify(hand);
    }
    this.state = state.nextState(event);
    state.setXLimits(clown);
  }

  public final void addObserver(ObserverIF observer) {
    this.observer = observer;
  }

  public final void removeObserver(ObserverIF observer) {
    this.observer = null;
  }

  // call when change state to free
  public final void notify(int event) {
    // just if has a observer to notify
    if (this.observer != null) {
      observer.notify(event);
    }

  }

  public abstract Object clone();

  public void setState(State s) {
    this.state = s;
  }

  public State getState() {
    return this.state;
  }

}
