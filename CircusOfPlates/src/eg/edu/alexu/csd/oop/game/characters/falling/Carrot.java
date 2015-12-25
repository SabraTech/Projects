package eg.edu.alexu.csd.oop.game.characters.falling;

public class Carrot extends FallingCharacter {
  @Override
  public Object clone() {
    Carrot o = new Carrot();
    o.setX(this.getX());
    o.setY(this.getY());
    o.setColor(this.getColor());
    o.setVisible(true);
    o.setImage(this.getSpriteImages()[0]);
    return o;
  }
}
