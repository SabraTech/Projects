package eg.edu.alexu.csd.filestructure.avl;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * The Class Dictionary.
 */
public class Dictionary implements IDictionary {
  
  /** The tree. */
  private AVLTree<String> tree;
  
  /** The size. */
  private int size;
  
  /**
   * Instantiates a new dictionary.
   */
  public Dictionary() {
    
    tree = new AVLTree<String>();
    size = 0;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.filestructure.avl.IDictionary#load(java.io.File)
   */
  @Override
  public void load(File file) {
    
    try (Scanner scanner = new Scanner(file)) {
      
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        tree.insert(line);
        size++;
      }
      
      scanner.close();
      
    } catch (IOException error) {
      error.printStackTrace();
    }
    
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * eg.edu.alexu.csd.filestructure.avl.IDictionary#insert(java.lang.String)
   */
  @Override
  public boolean insert(String word) {
    if (tree.search(word)) {
      return false;
    } else {
      tree.insert(word);
      size++;
      return true;
    }
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * eg.edu.alexu.csd.filestructure.avl.IDictionary#exists(java.lang.String)
   */
  @Override
  public boolean exists(String word) {
    
    if (tree.search(word)) {
      return true;
    } else {
      return false;
    }
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * eg.edu.alexu.csd.filestructure.avl.IDictionary#delete(java.lang.String)
   */
  @Override
  public boolean delete(String word) {
    
    if (tree.delete(word)) {
      size--;
      return true;
    } else {
      return false;
    }
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.filestructure.avl.IDictionary#size()
   */
  @Override
  public int size() {
    
    return size;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.filestructure.avl.IDictionary#height()
   */
  @Override
  public int height() {
    
    return tree.height();
  }
  
}