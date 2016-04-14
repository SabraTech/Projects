package eg.edu.alexu.csd.filestructure.avl;

public interface IDictionary {
  /**
   * Load the dictionary into an AVL Tree data structure. The file is text
   * containing a list of words. Each word will be in a separate line
   * 
   * @param file
   *          the dictionary file
   */
  void load(java.io.File file);
  
  /**
   * Takes a word and inserts it, only if it is not already in the dictionary
   * 
   * @param word
   *          word to insert
   * @return true if inserted successfully, false if already exists
   */
  boolean insert(String word);
  
  /**
   * Takes a word from the user and checks whether it is found or not.
   * 
   * @param word
   *          word to lookup
   * @return true if exists, false if not
   */
  boolean exists(String word);
  
  /**
   * Takes a word from the user and removes it from the dictionary.
   * 
   * @param word
   *          word to delete
   * @return false if the word is not in the dictionary, true otherwise
   */
  boolean delete(String word);
  
  /**
   * Prints the current size of your dictionary
   * 
   * @return dictionary size
   */
  int size();
  
  /**
   * Print the maximum height of the current tree
   * 
   * @return AVL tree height
   */
  int height();
}
