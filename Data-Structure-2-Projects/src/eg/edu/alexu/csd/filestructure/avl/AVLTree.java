package eg.edu.alexu.csd.filestructure.avl;

public class AVLTree<T extends Comparable<T>> implements IAVLTree<T> {
  
  private AVLNode<T> root;
  
  public AVLTree() {
    root = null;
  }
  
  @Override
  public void insert(T key) {
    root = insertAvl(key, root);
  }
  
  private AVLNode<T> insertAvl(T key, AVLNode<T> root2) {
    if (root2 == null) {
      root2 = new AVLNode<T>(key);
    }
    int compareResult = key.compareTo(root2.getValue());
    
    if (compareResult < 0) {
      root2.left = insertAvl(key, root2.left);
    } else {
      root2.right = insertAvl(key, root2.right);
    }
    
    root2.height = Math.max(getHeight(root2.left), getHeight(root2.right)) + 1;
    
    int balance = getBalance(root2);
    
    // left left case
    if (balance > 1 && key.compareTo(root2.left.element) < 0) {
      return rotateRight(root2);
    }
    
    // right right case
    if (balance < -1 && key.compareTo(root2.right.element) > 0) {
      return rotateLeft(root2);
    }
    
    // left right case
    if (balance > 1 && key.compareTo(root2.left.element) > 0) {
      return rotateRight(root2);
    }
    
    // right left case
    if (balance < -1 && key.compareTo(root2.right.element) < 0) {
      return rotateLeft(root2);
    }
    return root2;
    
  }
  
  @Override
  public boolean delete(T key) {
    
    if (root == null) {
      return false;
    } else if (search(key)) {
      deleteAvl(key, root);
      return true;
    } else {
      return false;
    }
    
  }
  
  private AVLNode<T> deleteAvl(T key, AVLNode<T> root2) {
    if (root2 == null) {
      return root2;
    }
    int compareResult = key.compareTo(root2.getValue());
    
    if (compareResult < 0) {
      root2.left = deleteAvl(key, root2.left);
    } else if (compareResult > 0) {
      root2.right = deleteAvl(key, root2.right);
    } else {
      
      if (root2.left == null || root2.right == null) {
        AVLNode<T> temp = null;
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
        AVLNode<T> temp = getMin(root2.right);
        root2.element = temp.element;
        root2.right = deleteAvl(temp.element, root2.right);
      }
    }
    
    if (root2 == null) {
      return root2;
    }
    
    root2.height = Math.max(getHeight(root2.left), getHeight(root2.right)) + 1;
    
    int balance = getBalance(root2);
    
    // left left case
    if (balance > 1 && getBalance(root2.left) >= 0) {
      return rotateRight(root2);
    }
    
    // left right case
    if (balance > 1 && getBalance(root2.left) < 0) {
      root2.left = rotateLeft(root2.left);
      return rotateRight(root2);
    }
    
    // right right case
    if (balance < -1 && getBalance(root2.right) <= 0) {
      return rotateLeft(root2);
    }
    
    // left left case
    if (balance < -1 && getBalance(root2.left) > 0) {
      root2.right = rotateRight(root2.right);
      return rotateLeft(root2);
    }
    
    return root2;
    
  }
  
  private AVLNode<T> getMin(AVLNode<T> right) {
    if (right == null) {
      return null;
    } else if (right.left == null) {
      return right;
    }
    return getMin(right.left);
  }
  
  @Override
  public boolean search(T key) {
    
    if (root == null) {
      return false;
    }
    
    return searchAvl(key, root);
    
  }
  
  private boolean searchAvl(T key, AVLNode<T> node) {
    while (node != null) {
      int compareResult = key.compareTo(node.element);
      if (compareResult < 0) {
        node = node.left;
      } else if (compareResult > 0) {
        node = node.right;
      } else {
        return true;
      }
    }
    return false;
  }
  
  @Override
  public int height() {
    if (root == null) {
      return 0;
    }
    return root.height;
  }
  
  @Override
  public INode<T> getTree() {
    return root;
  }
  
  private int getHeight(AVLNode<T> node) {
    if (node == null) {
      return 0;
    }
    return node.height;
  }
  
  private int getBalance(AVLNode<T> node) {
    if (node == null) {
      return 0;
    }
    return getHeight(node.left) - getHeight(node.right);
  }
  
  private AVLNode<T> rotateRight(AVLNode<T> y) {
    AVLNode<T> x = y.left;
    AVLNode<T> t2 = x.right;
    
    x.right = y;
    y.left = t2;
    
    y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
    x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
    
    return x;
  }
  
  private AVLNode<T> rotateLeft(AVLNode<T> x) {
    AVLNode<T> y = x.right;
    AVLNode<T> t2 = y.left;
    
    x.left = x;
    y.right = t2;
    
    x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
    y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
    
    return y;
  }
  
  private class AVLNode<T extends Comparable<T>> implements INode<T> {
    
    private T element;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private int height;
    
    public AVLNode(T key) {
      this.element = key;
      this.height = 1;
    }
    
    @Override
    public INode<T> getLeftChild() {
      return (AVLNode<T>) this.left;
    }
    
    @Override
    public INode<T> getRightChild() {
      return (AVLNode<T>) this.right;
    }
    
    public void setLeftChild(AVLNode<T> left) {
      this.left = left;
    }
    
    public void setRightChild(AVLNode<T> right) {
      this.right = right;
    }
    
    public void setHeight(int height) {
      this.height = height;
    }
    
    public int getHeight() {
      return this.height;
      
    }
    
    @Override
    public T getValue() {
      return this.element;
    }
    
    @Override
    public void setValue(T value) {
      this.element = value;
      
    }
    
  }
  
}
