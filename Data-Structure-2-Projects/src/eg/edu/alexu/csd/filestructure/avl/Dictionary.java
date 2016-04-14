package eg.edu.alexu.csd.filestructure.avl;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary implements IDictionary {
  
  private AVLTree<String> tree;
  private int size;
  public Dictionary(){
    tree = new AVLTree();
    size = 0;
  }

  @Override
  public void load(File file) {
    
    try (Scanner scanner = new Scanner(file)) {

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        tree.insert(line);
        size++;
      }

      scanner.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

  @Override
  public boolean insert(String word) {
    if(tree.search(word)){
      return false;
    }else{
      tree.insert(word);
      size++;
      return true;
    }
  }

  @Override
  public boolean exists(String word) {
    
    if(tree.search(word)){
      return true;
    }else{
      return false;
    }
  }

  @Override
  public boolean delete(String word) {
    
    if(tree.delete(word)){
      size--;
      return true;
    }else{
      return false;
    }
  }

  @Override
  public int size() {
    
    return size;
  }

  @Override
  public int height() {
    
    return tree.height();
  }
  
}
