package eg.edu.alexu.csd.oop.game.characters.loading;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.imageio.ImageIO;

/**
 * The Class ClassFinder.
 */
public class ClassFinder {

  /** The super class. */
  private Class<?> superClass = null;

  /**
   * Instantiates a new class finder.
   *
   * @param superClass
   *          the super class
   */
  public ClassFinder(Class<?> superClass) {
    this.superClass = superClass;

  }

  public ArrayList<MyEntry<Class<?>, MyEntry<BufferedImage, Integer>[]>> getFallingChars(
      String jarPath, String imagesDirectory) {
    ArrayList<MyEntry<Class<?>, MyEntry<BufferedImage, Integer>[]>> returnedList = new ArrayList<MyEntry<Class<?>, MyEntry<BufferedImage, Integer>[]>>();
    for (Class<?> theClass : getPluginShapes(jarPath)) {
      // System.out.println(theClass);
      MyEntry<String, Integer>[] ImagePaths = getImages(theClass.getSimpleName(), imagesDirectory);
      @SuppressWarnings("unchecked")
      MyEntry<BufferedImage, Integer>[] images = new MyEntry[ImagePaths.length];
      int counter = 0;
      for (MyEntry<String, Integer> dummy : ImagePaths) {
        try {
          images[counter++] = new MyEntry<BufferedImage, Integer>(ImageIO.read(new File(dummy
              .getFirst())), dummy.getSecond());
        } catch (Exception e) {
          // System.out.println("exception " + dummy);
        }
      }
      returnedList.add(new MyEntry<Class<?>, MyEntry<BufferedImage, Integer>[]>(theClass, images));
    }
    return returnedList;
  }

  public MyEntry<String, Integer>[] getImages(String className, String ImagesDirectory) {
    File directory = new File(ImagesDirectory);
    System.out.println(directory.exists());
    File[] files = directory.listFiles();
    String[] ImageNames = new String[] { className + "Blue.png", className + "Green.png", className
        + "Pink.png", className + "Red.png" };
    ArrayList<MyEntry<String, Integer>> paths = new ArrayList<MyEntry<String, Integer>>();

    for (File dummy : files) {
      System.out.println(dummy);
      int counter2 = 0;
      for (String ImageName : ImageNames) {
        if (dummy.getName().replace(ImagesDirectory, "").equalsIgnoreCase(ImageName)) {
          paths.add(new MyEntry<String, Integer>(dummy.getAbsolutePath(), counter2));
        }
        counter2++;
      }

    }
    @SuppressWarnings("unchecked")
    MyEntry<String, Integer>[] ret = new MyEntry[paths.size()];
    for (int counter = 0; counter < paths.size(); counter++) {
      ret[counter] = paths.get(counter);
    }
    return ret;
  }

  /**
   * Gets the plugin.
   *
   * @param jarPath
   *          the jar path
   * @return the plugin
   */
  public Set<Class<?>> getPluginShapes(String jarPath) {
    Set<Class<?>> pluginShapes = new HashSet<Class<?>>();
    ClassLoader loader = null;
    try {
      loader = URLClassLoader.newInstance(new URL[] { (new File(jarPath)).toURI().toURL() },
          getClass().getClassLoader());
    } catch (MalformedURLException e) {
      // System.out.println("wrong path");
    }
    String path = (new File(jarPath)).getPath();
    try {
      JarInputStream inputStream = new JarInputStream(new FileInputStream(path));
      JarEntry dummyEntry = inputStream.getNextJarEntry();
      while (dummyEntry != null) {
        // System.out.println(dummyEntry);
        if (dummyEntry.getName().endsWith(".class")) {
          System.out.println(dummyEntry.getName());
          String dummy2 = dummyEntry.getName().replaceAll("/", ".");
          dummy2 = dummy2.split(".class")[0];
          String className = dummyEntry.getName().substring(0, dummyEntry.getName().length() - 6);
          className = className.replace('/', '.');
          System.out.println(className);
          Class<?> dummyClass = Class.forName(className, true, loader);
          if (this.superClass.isAssignableFrom(dummyClass) && !dummyClass.isInterface() && !Modifier
              .isAbstract(dummyClass.getModifiers())) {
            pluginShapes.add((Class<?>) dummyClass);
            System.out.println(dummyClass);
          }

        }
        dummyEntry = inputStream.getNextJarEntry();

      }
      inputStream.close();
    } catch (Exception ex) {
      // ignore it
    }
    return pluginShapes;
  }

}
