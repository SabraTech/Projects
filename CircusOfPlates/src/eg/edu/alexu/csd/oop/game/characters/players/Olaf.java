package eg.edu.alexu.csd.oop.game.characters.players;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Olaf extends Player {

  /**
   * 
   */
  private static final long serialVersionUID = -8227462446664917965L;

  public Olaf(int x, int y) {
    // create a bunch of buffered images and place into an array, to be
    // displayed sequentially
    this.x = x;
    this.y = y;
    this.visible = true;
    isCarrying = false;
    this.rightHandState = new FreeState();
    this.leftHandState = new FreeState();

    try {
      spiriteImages[0] = ImageIO.read(getClass().getResourceAsStream("/olaf.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

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

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public int getLeftHandX() {
    return this.x + spiriteImages[0].getWidth() * 2 / 20;
  }

  public int getLeftHandY() {
    return this.y + spiriteImages[0].getHeight() * 10 / 25;
  }

  public int getRightHandX() {
    return this.x + spiriteImages[0].getWidth() * 18 / 20 - 20;
  }

  public int getRightHandY() {
    return this.y + spiriteImages[0].getHeight() * 15 / 25 + 20;

  }

  @Override
  public Object clone() {
    Olaf d = new Olaf(this.x, this.y);
    d.setVisible(this.isVisible());
    d.isCarrying = this.isCarrying;
    d.setLeftHandState(this.getLeftHandState());
    d.setRightHandState(this.getRightHandState());
    d.maxX = this.maxX;
    d.minX = this.minX;
    return d;
  }
}
