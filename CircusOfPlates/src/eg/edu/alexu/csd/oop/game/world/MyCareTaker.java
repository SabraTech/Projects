package eg.edu.alexu.csd.oop.game.world;

import java.io.IOException;

public class MyCareTaker {
  MomentoIf mom;

  public void saveState(MomentoIf o) throws IOException {
    mom = o;
  }

  public MomentoIf restoreState() {
    return mom;
  }

}
