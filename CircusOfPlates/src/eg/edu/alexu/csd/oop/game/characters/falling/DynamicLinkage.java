package eg.edu.alexu.csd.oop.game.characters.falling;

public class DynamicLinkage extends FallingCharacter {
  @Override
  public Object clone() {
    DynamicLinkage o = new DynamicLinkage();
    o.setX(this.getX());
    o.setY(this.getY());
    o.setColor(this.getColor());
    o.setVisible(true);
    o.setImage(this.getSpriteImages()[0]);
    return o;
  }
}
