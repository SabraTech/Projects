package eg.edu.alexu.csd.oop.game.characters.players;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ClownType2 extends Player {

  /**
   * 
   */
  private static final long serialVersionUID = 3177988358039145418L;

  public ClownType2(int x, int y) {
    // create a bunch of buffered images and place into an array, to be
    // displayed sequentially
    this.x = x;
    this.y = y;
    this.visible = true;
    isCarrying = false;
    this.rightHandState = new FreeState();
    this.leftHandState = new FreeState();

    try {
      spiriteImages[0] = ImageIO.read(getClass().getResourceAsStream("/ClownType2.png"));
      // this.leftHandx = spiriteImages[0].getWidth() * 2 / 20;
      // this.leftHandY = 0;
      // this.rightHandX = spiriteImages[0].getWidth() * 18 / 20;
      // this.rightHandY = 0;
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
    return this.y + 0;
  }

  public int getRightHandX() {
    return this.x + spiriteImages[0].getWidth() * 18 / 20;
  }

  public int getRightHandY() {
    return this.y + 0;

  }

  @Override
  public Object clone() {
    ClownType2 d = new ClownType2(this.x, this.y);
    d.setVisible(this.isVisible());
    d.isCarrying = this.isCarrying;
    d.setLeftHandState(this.getLeftHandState());
    d.setRightHandState(this.getRightHandState());
    d.maxX = this.maxX;
    d.minX = this.minX;
    return d;
  }
}