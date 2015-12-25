package eg.edu.alexu.csd.oop.game.world;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import eg.edu.alexu.csd.oop.game.GameObject;

public class Image implements GameObject, Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -3031636969615024280L;
  private static final int MAX_MSTATE = 1;
  private BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
  private int x;
  private int y;
  private boolean visible;
  protected String path;

  public Image(String path) {
    this.x = 0;
    this.y = 0;
    this.path = path;
    this.visible = true;
    try {
      spriteImages[0] = ImageIO.read(getClass().getResourceAsStream(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public void setX(int mX) {
    this.x = mX;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public void setY(int mY) {
    this.y = mY;
  }

  @Override
  public BufferedImage[] getSpriteImages() {
    return spriteImages;
  }

  @Override
  public int getWidth() {
    return spriteImages[0].getWidth();
  }

  @Override
  public int getHeight() {
    return spriteImages[0].getHeight();
  }

  @Override
  public boolean isVisible() {
    return visible;
  }

}
