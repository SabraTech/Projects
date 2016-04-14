package eg.edu.alexu.csd.filestructure.avl;

public class AVLTree<T extends Comparable<T>> implements IAVLTree<T> {
  
  private static final int ALLOWED_IMBALANCE = 1;
  AVLNode<T> root;
  
  public AVLTree() {
    root = null;
  }
  
  @Override
  public void insert(T key) {
    root = insertAVL(key, root);
  }
  
  private AVLNode<T> insertAVL(T key, AVLNode<T> root2) {
    if (root2 == null) {
      root2 = new AVLNode<T>(key, null, null);
    }
    int compareResult = key.compareTo(root2.getValue());
    
    if (compareResult < 0) {
      root2.left = insertAVL(key, root2.left);
    } else if (compareResult > 0) {
      root2.right = insertAVL(key, root2.right);
    } else {
      // duplicate
    }
    return balance(root2);
    
  }
  
  @Override
  public boolean delete(T key) {
    
    if (search(key)) {
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
    } else if (root.left != null && root.right != null) {
      root2.element = getMin(root2.right).element;
      root2.right = deleteAvl(root2.element, root2.right);
    } else {
      root2 = (root2.left != null) ? root2.left : root2.right;
    }
    
    return balance(root2);
    
  }
  
  private AVLNode<T> getMin(AVLNode<T> right) {
    if (right == null) {
      return null;
    } else if (right.left == null) {
      return right;
    }
    return getMin(right.left);
  }
  
  private AVLNode<T> getMax(AVLNode<T> left) {
    if (left == null) {
      return null;
    } else if (left.right == null) {
      return left;
    }
    return getMin(left.right);
  }
  
  @Override
  public boolean search(T key) {
    
    if (root == null) {
      return false;
    }
    
    return searchAvl(key, root);
    
  }
  
  private boolean searchAvl(T key, AVLNode<T> t) {
    while (t != null) {
      int compareResult = key.compareTo(t.element);
      if (compareResult < 0) {
        t = t.left;
      } else if (compareResult > 0) {
        t = t.right;
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
  
  private int getHeight(AVLNode<T> node) {
    return node == null ? -1 : node.height;
  }
  
  private AVLNode<T> balance(AVLNode<T> node) {
    
    if (node == null) {
      return node;
    }
    
    if (node.left.height - node.right.height > ALLOWED_IMBALANCE) {
      if (node.left.left.height >= node.right.right.height) {
        node = rotateWithLeftChild(node);
      } else {
        node = doubleWithLeftChild(node);
      }
    } else {
      if (node.right.height - node.left.height > ALLOWED_IMBALANCE) {
        if (node.right.right.height >= node.left.left.height) {
          node = rotateWithRightChild(node);
        } else {
          node = doubleWithRightChild(node);
        }
      }
    }
    node.height = Math.max(node.left.height, node.right.height) + 1;
    return node;
  }
  
  private AVLNode<T> doubleWithRightChild(AVLNode<T> node) {
    node.right = rotateWithLeftChild(node.right);
    return rotateWithRightChild(node);
  }
  
  private AVLNode<T> rotateWithRightChild(AVLNode<T> node) {
    AVLNode<T> k1 = node.right;
    node.right = k1.left;
    k1.left = node;
    node.height = Math.max(node.right.height, node.left.height) + 1;
    k1.height = Math.max(k1.right.height, node.height) + 1;
    return k1;
  }
  
  private AVLNode<T> doubleWithLeftChild(AVLNode<T> node) {
    node.left = rotateWithRightChild(node.left);
    return rotateWithLeftChild(node);
  }
  
  private AVLNode<T> rotateWithLeftChild(AVLNode<T> node) {
    AVLNode<T> k1 = node.left;
    node.left = k1.right;
    k1.right = node;
    node.height = Math.max(node.left.height, node.right.height) + 1;
    k1.height = Math.max(k1.left.height, node.height) + 1;
    return k1;
  }
  
  private class AVLNode<T extends Comparable<T>> implements INode<T> {
    
    private T element;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private int height;
    
    public AVLNode(T key, AVLNode<T> left, AVLNode<T> right) {
      this.element = key;
      this.left = left;
      this.right = right;
      this.height = 0;
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
