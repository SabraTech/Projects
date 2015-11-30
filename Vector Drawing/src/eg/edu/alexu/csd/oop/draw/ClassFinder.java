package eg.edu.alexu.csd.oop.draw;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * The Class ClassFinder.
 */
public class ClassFinder {

  /** The returned set of shapes. */
  private Set<Class<? extends Shape>> returned = new HashSet<Class<? extends Shape>>();

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

  /**
   * Gets the plugin.
   *
   * @param jarPath
   *          the jar path
   * @return the plugin
   */
  @SuppressWarnings("unchecked")
  public Set<Class<? extends Shape>> getPluginShapes(String jarPath) {
    Set<Class<? extends Shape>> pluginShapes = new HashSet<Class<? extends Shape>>();
    ClassLoader loader = null;
    try {
      loader = URLClassLoader.newInstance(new URL[] { (new File(jarPath)).toURI().toURL() },
          getClass().getClassLoader());
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
    }
    String path = (new File(jarPath)).getPath();
    try {
      JarInputStream inputStream = new JarInputStream(new FileInputStream(path));
      JarEntry dummyEntry = inputStream.getNextJarEntry();
      while (dummyEntry != null) {
        if (dummyEntry.getName().endsWith(".class")) {
          String dummy2 = dummyEntry.getName().replaceAll("/", ".");
          dummy2 = dummy2.split(".class")[0];
          String className = dummyEntry.getName().substring(0, dummyEntry.getName().length() - 6);
          className = className.replace('/', '.');
          try {
            Class<?> dummyClass = Class.forName(dummy2, true, loader);
            if (this.superClass.isAssignableFrom(dummyClass) && !dummyClass.isInterface()
                && !Modifier.isAbstract(dummyClass.getModifiers())) {
              pluginShapes.add((Class<? extends Shape>) dummyClass);
            }

          } catch (Throwable exception) {
            // do nothing
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

  /**
   * Adds the class to the set after carrying the check on it's superclass and that it's neither an
   * interface nor an abstract class.
   *
   * @param className
   *          the class name
   */
  @SuppressWarnings("unchecked")
  private void addClass(String className) {
    try {

      Class<?> theClass = Class.forName(className, false, this.getClass().getClassLoader());
      if (this.superClass.isAssignableFrom(theClass) && !theClass.isInterface()
          && !Modifier.isAbstract(theClass.getModifiers())) {
        this.returned.add((Class<? extends Shape>) theClass);

      }
    } catch (Throwable t) {
      // throw it
    }
  }

  /**
   * Gets the classes.
   *
   * @return the classes
   */
  public Set<Class<? extends Shape>> getSupportedClasses() {
    String classpath = System.getProperty("java.class.path");
    String pathSeparator = System.getProperty("path.separator");
    StringTokenizer tokenizer = new StringTokenizer(classpath, pathSeparator);
    while (tokenizer.hasMoreTokens()) {
      String dummy = tokenizer.nextToken();
      File currentDirectory = new File(dummy);
      if (isJar(dummy)) {
        try {
          accessJar(dummy);
        } catch (Throwable e) {
          // do nothing
        }
      } else {
        processFile(currentDirectory.getAbsolutePath(), "");
      }

    }

    return this.returned;
  }

  /**
   * Process file.
   *
   * @param base
   *          the base
   * @param current
   *          the current
   */
  private void processFile(String base, String current) {
    File currentDirectory = new File(base + File.separatorChar + current);
    if (isJar(currentDirectory.getName())) {
      try {
        accessJar(currentDirectory.getName());
      } catch (Exception e) {
        // do nothing
      }
      return;
    } else {
      Set<File> directories = new HashSet<File>();
      File[] children = currentDirectory.listFiles();
      if (children == null || children.length == 0) {
        return;
      }
      for (int counter = 0; counter < children.length; counter++) {
        File child = children[counter];
        if (child.isDirectory()) {
          directories.add(children[counter]);
        } else if (isJar(child.getName())) {
          try {
            accessJar(currentDirectory + File.separator + child.getName());
          } catch (MalformedURLException e) {
            // do nothing
          }
        } else {
          // do nothing
        }
        if (child.getName().endsWith(".class")) {
          String className = getClassName(
              current + ((current == "") ? "" : File.separator) + child.getName());
          addClass(className);
        }
      }

      // call process file on each directory. This is an iterative call!!
      for (Iterator<File> counter = directories.iterator(); counter.hasNext();) {
        processFile(base,
            current + ((current == "") ? "" : File.separator) + ((File) counter.next()).getName());
      }
    }
  }

  /**
   * Checks if is jar.
   *
   * @param name
   *          the name
   * @return true, if is jar
   */
  protected boolean isJar(String name) {
    try {
      String dummy = name.substring(name.length() - 4, name.length());
      if (dummy.equalsIgnoreCase(".jar")) {
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Gets the class name.
   *
   * @param fileName
   *          the file name
   * @return the class name
   */
  protected String getClassName(String fileName) {
    String newName = fileName.replace(File.separatorChar, '.');
    newName = newName.replace('/', '.');
    return newName.substring(0, fileName.length() - 6);
  }

  /**
   * Iterates through the files in a jar looking for files that may be classes. The ZipFile to be
   * searched
   *
   * @param dummy
   *          the dummy
   * @throws MalformedURLException
   *           the malformed url exception
   */

  @SuppressWarnings("unchecked")
  public void accessJar(String dummy) throws MalformedURLException {
    ClassLoader loader = getClass().getClassLoader();
    String path = (new File(dummy)).getPath();
    try {
      JarInputStream inputStream = new JarInputStream(new FileInputStream(path));
      JarEntry dummyEntry = inputStream.getNextJarEntry();
      while (dummyEntry != null) {
        if (dummyEntry.getName().endsWith(".class")) {
          String dummy2 = dummyEntry.getName().replaceAll("/", ".");
          dummy2 = dummy2.split(".class")[0];
          String className = dummyEntry.getName().substring(0, dummyEntry.getName().length() - 6);
          className = className.replace('/', '.');
          try {
            Class<?> dummyClass = Class.forName(dummy2, true, loader);
            if (this.superClass.isAssignableFrom(dummyClass) && !dummyClass.isInterface()
                && !Modifier.isAbstract(dummyClass.getModifiers())) {
              returned.add((Class<? extends Shape>) dummyClass);
            }

          } catch (Throwable exception) {
            // let's see the exception
          }
        }
        dummyEntry = inputStream.getNextJarEntry();

      }
      inputStream.close();
    } catch (Exception ex) {
      // do nothing
    }
  }

}