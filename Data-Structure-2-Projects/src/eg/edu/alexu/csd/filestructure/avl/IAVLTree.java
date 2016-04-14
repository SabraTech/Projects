package eg.edu.alexu.csd.filestructure.avl;

public interface IAVLTree<T extends Comparable<T>> {
  /**
   * Insert the given value using the key
   * 
   * @param key
   *          the value to be inserted in the tree
   */
  void insert(T key);
  
  /**
   * Delete the key (if exists)
   * 
   * @param key
   *          the key of the node
   * @return true if node deleted, false if not exists
   */
  boolean delete(T key);
  
  /**
   * Search for a specific element using the key in the tree
   * 
   * @param key
   *          the key of the node
   * @return true if the key exists, false otherwise
   */
  boolean search(T key);
  
  /**
   * Return the height of the AVL tree. This is the longest path from the root
   * to a leaf-node
   * 
   * @return tree height
   */
  int height();
  
  /**
   * Return the root of your AVL tree.
   * 
   * @return root of the AVL tree.
   */
  INode<T> getTree();
}
