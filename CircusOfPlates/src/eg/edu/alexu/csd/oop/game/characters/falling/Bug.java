package eg.edu.alexu.csd.oop.game.characters.falling;

public class Bug extends FallingCharacter {
  @Override
  public Object clone() {
    Bug o = new Bug();
    o.setX(this.getX());
    o.setY(this.getY());
    o.setColor(this.getColor());
    o.setVisible(true);
    o.setImage(this.getSpriteImages()[0]);
    return o;
  }
}
