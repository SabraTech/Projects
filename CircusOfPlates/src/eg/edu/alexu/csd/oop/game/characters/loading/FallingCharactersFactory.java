package eg.edu.alexu.csd.oop.game.characters.loading;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import eg.edu.alexu.csd.oop.game.GameWindow;
import eg.edu.alexu.csd.oop.game.characters.falling.FallingCharacter;

public class FallingCharactersFactory {
  private static FallingCharactersFactory singleInstance;
  private ClassFinder finder;
  public ArrayList<MyEntry<Class<?>, MyEntry<BufferedImage, Integer>[]>> characterArray;
  public static int COLOR_BlUE = 0;
  public static int COLOR_PINK = 1;
  public static int COLOR_RED = 2;
  public static int COLOR_GREEN = 3;

  private FallingCharactersFactory() {// singleton design pattern
    finder = new ClassFinder(FallingCharacter.class);
    characterArray = new ArrayList<>();
  }

  public static FallingCharactersFactory getInstance() {
    if (singleInstance == null) {
      singleInstance = new FallingCharactersFactory();
    }
    return singleInstance;
  }

  public void addFallingCharacters(String jarPath, String imagesDirectory) {
    GameWindow.log.debug("loading the jar file now");
    characterArray = finder.getFallingChars(jarPath, imagesDirectory);

  }

  public static void destroyInstance() {
    singleInstance = null;
  }

  // }

  public FallingCharacter getFallingCharacter(int randomInt, int color) {

    MyEntry<Class<?>, MyEntry<BufferedImage, Integer>[]> dummyEntry = characterArray.get(randomInt);
    Class<?> charClass = dummyEntry.getFirst();
    BufferedImage img = (dummyEntry.getSecond())[color].getFirst();
    FallingCharacter fallingCharacter = null;
    try {
      fallingCharacter = (FallingCharacter) charClass.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    fallingCharacter.setImage(img);

    fallingCharacter.setColor((dummyEntry.getSecond())[color].getSecond());
    return fallingCharacter;
  }

  public int getColorCount(int supportedClass) {
    return characterArray.get(supportedClass).getSecond().length;
  }

  public int getSupportedClassesCount() {
    return characterArray.size();
  }

  public static void main(String args[]) {

  }

}