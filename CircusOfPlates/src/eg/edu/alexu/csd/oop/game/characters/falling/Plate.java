package eg.edu.alexu.csd.oop.game.characters.falling;

public class Plate extends FallingCharacter {
  @Override
  public Object clone() {
    Plate o = new Plate();
    o.setX(this.getX());
    o.setY(this.getY());
    o.setColor(this.getColor());
    o.setVisible(true);
    o.setImage(this.getSpriteImages()[0]);
    return o;
  }
}
