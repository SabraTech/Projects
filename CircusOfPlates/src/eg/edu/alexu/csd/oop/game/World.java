package eg.edu.alexu.csd.oop.game;

public interface World {
  /** @return list of immovable object */
  java.util.List<GameObject> getConstantObjects();

  /** @return list of moving object */
  java.util.List<GameObject> getMovableObjects();

  /** @return list of user controlled object */
  java.util.List<GameObject> getControlableObjects();

  /** @return screen width */
  int getWidth();

  /** @return screen height */
  int getHeight();

  /**
   * refresh the world state and update locations
   * 
   * @return false means game over
   */
  boolean refresh();

  /**
   * status bar content
   * 
   * @return string to be shown at status bar
   */
  String getStatus();

  /** @return frequency of calling refresh */
  int getSpeed();

  /** @return frequency of receiving user input */
  int getControlSpeed();
}