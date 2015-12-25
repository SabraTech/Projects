package eg.edu.alexu.csd.oop.game.characters.loading;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.filechooser.FileSystemView;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.GameWindow;
import eg.edu.alexu.csd.oop.game.characters.falling.FallingCharacter;

public class FallingCharacterPool {

  private static FallingCharacterPool shapePool = null;

  private FallingCharactersFactory shapeFactory;
  public ArrayList<FallingCharacter> objectPool;
  private Random randomGenerator;
  private String home = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();

  private FallingCharacterPool(String name) {
    objectPool = new ArrayList<FallingCharacter>();
    randomGenerator = new Random();
    shapeFactory = FallingCharactersFactory.getInstance();
    shapeFactory.addFallingCharacters(home + File.separatorChar + name + File.separatorChar
        + "plugin.jar" + File.separatorChar, home + File.separatorChar + name + File.separatorChar
            + "images" + File.separatorChar);
    this.generate();

  }

  public static FallingCharacterPool getInstance(String name) {
    if (shapePool == null)
      shapePool = new FallingCharacterPool(name);
    return shapePool;
  }

  public static void destroyInstance() {
    shapePool = null;
  }

  public void generate() {
    FallingCharacter fallingShape;
    for (int counter = 0; counter < 100; counter++) {
      int randomInt = randomGenerator.nextInt(FallingCharactersFactory.getInstance()
          .getSupportedClassesCount());
      int randomColor = randomGenerator.nextInt(FallingCharactersFactory.getInstance()
          .getColorCount(randomInt));
      fallingShape = shapeFactory.getFallingCharacter(randomInt, randomColor);
      objectPool.add(fallingShape);
    }
  }

  public FallingCharacter getShape() {
    FallingCharacter fallingShape;
    GameWindow.log.debug("check now if there is object in the pool");
    if (objectPool.isEmpty()) {
      GameWindow.log.debug("create the object by request from factory");
      int randomInt = randomGenerator.nextInt(FallingCharactersFactory.getInstance()
          .getSupportedClassesCount());
      int randomColor = randomGenerator.nextInt(FallingCharactersFactory.getInstance()
          .getColorCount(randomInt));
      fallingShape = shapeFactory.getFallingCharacter(randomInt, randomColor);
    } else {
      GameWindow.log.debug("return object from the pool");
      fallingShape = objectPool.remove(0);
    }
    return fallingShape;
  }

  public void returnObject(GameObject shape) {
    if (!(shape instanceof FallingCharacter))
      throw new IllegalArgumentException();
    objectPool.add((FallingCharacter) shape);
  }

}
