package eg.edu.alexu.csd.oop.game.characters.falling;

public class SmallBall extends FallingCharacter {
  @Override
  public Object clone() {
    SmallBall o = new SmallBall();
    o.setX(this.getX());
    o.setY(this.getY());
    o.setColor(this.getColor());
    o.setVisible(true);
    o.setImage(this.getSpriteImages()[0]);

    return o;
  }
}
