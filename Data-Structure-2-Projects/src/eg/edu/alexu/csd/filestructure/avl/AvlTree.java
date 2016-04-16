package eg.edu.alexu.csd.filestructure.avl;

/**
 * The Class AvlTree.
 *
 * @param <T>
 *          the generic type
 */
public class AvlTree<T extends Comparable<T>> implements IAVLTree<T> {
  
  /** The root. */
  private AVLNode root;
  
  /**
   * Instantiates a new avl tree.
   */
  public AvlTree() {
    root = null;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * eg.edu.alexu.csd.filestructure.avl.IAVLTree#insert(java.lang.Comparable)
   */
  @Override
  public void insert(final T key) {
    root = insertAvl(key, root);
  }
  
  /**
   * Insert avl.
   *
   * @param key
   *          the key
   * @param root2
   *          the root2
   * @return the AVL node
   */
  private AVLNode insertAvl(final T key, final AVLNode root2) {
    if (root2 == null) {
      return new AVLNode(key);
    }
    int compareResult = key.compareTo(root2.getValue());
    
    if (compareResult < 0) {
      root2.left = insertAvl(key, root2.left);
    } else {
      root2.right = insertAvl(key, root2.right);
    }
    
    root2.height = (Math.max(getHeight(root2.left), getHeight(root2.right))
        + 1);
    
    int balance = getBalance(root2);
    
    // left left case
    if (balance > 1 && key.compareTo(root2.left.getValue()) < 0) {
      return rotateRight(root2);
    }
    
    // right right case
    if (balance < -1 && key.compareTo(root2.right.getValue()) > 0) {
      return rotateLeft(root2);
    }
    
    // left right case
    if (balance > 1 && key.compareTo(root2.left.getValue()) > 0) {
      root2.left = rotateLeft(root2.left);
      return rotateRight(root2);
    }
    
    // right left case
    if (balance < -1 && key.compareTo(root2.right.getValue()) < 0) {
      root2.right = rotateRight(root2.right);
      return rotateLeft(root2);
    }
    return root2;
    
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * eg.edu.alexu.csd.filestructure.avl.IAVLTree#delete(java.lang.Comparable)
   */
  @Override
  public boolean delete(final T key) {
    
    if (search(key)) {
      root = deleteAvl(key, root);
      return true;
    } else {
      return false;
    }
    
  }
  
  /**
   * Delete avl.
   *
   * @param key
   *          the key
   * @param root2
   *          the root2
   * @return the AVL node
   */
  private AVLNode deleteAvl(final T key, AVLNode root2) {
    if (root2 == null) {
      return root2;
    }
    
    int compareResult = key.compareTo(root2.getValue());
    
    if (compareResult < 0) {
      root2.setLeftChild(deleteAvl(key, root2.left));
    } else if (compareResult > 0) {
      root2.setRightChild(deleteAvl(key, root2.right));
    } else {
      
      if ((root2.left == null) || (root2.right == null)) {
        AVLNode temp = null;
        if (temp == root2.left) {
          temp = root2.right;
        } else {
          temp = root2.left;
        }
        
        if (temp == null) {
          temp = root2;
          root2 = null;
        } else {
          root2 = temp;
        }
      } else {
        
        AVLNode temp = getMin(root2.right);
        root2.setValue(temp.getValue());
        root2.setRightChild(deleteAvl(temp.getValue(), root2.right));
      }
    }
    
    if (root2 == null) {
      return root2;
    }
    
    root2
        .setHeight(Math.max(getHeight(root2.left), getHeight(root2.right)) + 1);
    
    int balance = getBalance(root2);
    
    if (balance > 1 && getBalance(root2.left) >= 0) {
      return rotateRight(root2);
    }
    
    if (balance > 1 && getBalance(root2.left) < 0) {
      root2.setLeftChild(rotateLeft(root2.left));
      return rotateRight(root2);
    }
    
    if (balance < -1 && getBalance(root2.right) <= 0) {
      return rotateLeft(root2);
    }
    
    if (balance < -1 && getBalance(root2.right) > 0) {
      root2.setRightChild(rotateRight(root2.right));
      return rotateLeft(root2);
    }
    
    return root2;
  }
  
  /**
   * Gets the min.
   *
   * @param node
   *          the node
   * @return the min
   */
  private AVLNode getMin(final AVLNode node) {
    AVLNode current = node;
    while (current.left != null) {
      current = current.left;
    }
    return current;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * eg.edu.alexu.csd.filestructure.avl.IAVLTree#search(java.lang.Comparable)
   */
  @Override
  public boolean search(final T key) {
    AVLNode temp = root;
    while (temp != null) {
      if (temp.getValue().compareTo(key) == 0) {
        return true;
      } else if (temp.getValue().compareTo(key) > 0) {
        temp = temp.left;
      } else {
        temp = temp.right;
      }
    }
    return false;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.filestructure.avl.IAVLTree#height()
   */
  @Override
  public int height() {
    if (root == null) {
      return 0;
    }
    return root.getHeight();
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.filestructure.avl.IAVLTree#getTree()
   */
  @Override
  public INode<T> getTree() {
    return (INode<T>) root;
  }
  
  /**
   * Gets the height.
   *
   * @param node
   *          the node
   * @return the height
   */
  private int getHeight(final AVLNode node) {
    if (node == null) {
      return 0;
    }
    return node.getHeight();
  }
  
  /**
   * Gets the balance.
   *
   * @param node
   *          the node
   * @return the balance
   */
  private int getBalance(final AVLNode node) {
    if (node == null) {
      return 0;
    }
    return getHeight(node.left) - getHeight(node.right);
  }
  
  /**
   * Rotate right.
   *
   * @param y
   *          the y
   * @return the AVL node
   */
  private AVLNode rotateRight(final AVLNode y) {
    AVLNode x = y.left;
    AVLNode t2 = x.right;
    
    x.setRightChild(y);
    y.setLeftChild(t2);
    
    y.setHeight((Math.max(getHeight(y.left), getHeight(y.right)) + 1));
    x.setHeight((Math.max(getHeight(x.left), getHeight(x.right)) + 1));
    
    return x;
  }
  
  /**
   * Rotate left.
   *
   * @param x
   *          the x
   * @return the AVL node
   */
  private AVLNode rotateLeft(final AVLNode x) {
    AVLNode y = x.right;
    AVLNode t2 = y.left;
    
    y.setLeftChild(x);
    x.setRightChild(t2);
    
    x.setHeight((Math.max(getHeight(x.left), getHeight(x.right)) + 1));
    y.setHeight((Math.max(getHeight(y.left), getHeight(y.right)) + 1));
    
    return y;
  }
  
  /**
   * The Class AVLNode.
   */
  private class AVLNode implements INode<T> {
    
    /** The element. */
    private T element;
    
    /** The left. */
    private AVLNode left;
    
    /** The right. */
    private AVLNode right;
    
    /** The height. */
    private int height;
    
    /**
     * Instantiates a new AVL node.
     *
     * @param key
     *          the key
     */
    public AVLNode(T key) {
      this.element = key;
      this.height = 1;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see eg.edu.alexu.csd.filestructure.avl.INode#getLeftChild()
     */
    @Override
    public INode<T> getLeftChild() {
      return this.left;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see eg.edu.alexu.csd.filestructure.avl.INode#getRightChild()
     */
    @Override
    public INode<T> getRightChild() {
      return this.right;
    }
    
    /**
     * Sets the left child.
     *
     * @param left
     *          the new left child
     */
    public void setLeftChild(AVLNode left) {
      this.left = left;
    }
    
    /**
     * Sets the right child.
     *
     * @param right
     *          the new right child
     */
    public void setRightChild(AVLNode right) {
      this.right = right;
    }
    
    /**
     * Sets the height.
     *
     * @param height
     *          the new height
     */
    public void setHeight(int height) {
      this.height = height;
    }
    
    /**
     * Gets the height.
     *
     * @return the height
     */
    public int getHeight() {
      return this.height;
      
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see eg.edu.alexu.csd.filestructure.avl.INode#getValue()
     */
    @Override
    public T getValue() {
      return this.element;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * eg.edu.alexu.csd.filestructure.avl.INode#setValue(java.lang.Comparable)
     */
    @Override
    public void setValue(T value) {
      this.element = value;
      
    }
    
  }
}
