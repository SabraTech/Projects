package eg.edu.alexu.csd.filestructure.avl;



public class AvlTree<T extends Comparable<T>> implements IAVLTree<T> {
  
  private AVLNode<T> root;
  
  public AvlTree() {
    root = null;
  }
  
  @Override
  public void insert(T key) {
    root = insertAvl(key, root);
  }
  
  private AVLNode<T> insertAvl(T key, AVLNode<T> root2) {
    if (root2 == null) {
      return new AVLNode<T>(key);
    }
    int compareResult = key.compareTo(root2.getValue());
    
    if (compareResult < 0) {
      root2.setLeftChild(insertAvl(key, root2.left));
    } else {
      root2.setRightChild(insertAvl(key, root2.right));
    }
    
    return root2;
    
  }
  
  private AVLNode<T> balance(AVLNode<T> node) {
    if(node == null){
      return node;
    }
    
    int check = getBalance(node);
    if(check > 1 ){
      if(getHeight(node.left.left) >= getHeight(node.left.right)){
        node = rotateWithLeftChild(node);
      }else{
        node = doubleWithLeftChild(node);
      }
    }else{
      if(getHeight(node.right) - getHeight(node.left) > 1){
        if(getHeight(node.right.right) >= getHeight(node.right.left)){
          node = rotateWithRightChild(node);
        }else{
          node = doubleWithRightChild(node);
        }
      }
    }
    node.setHeight(Math.max(getHeight(node.left), getHeight(node.right))+1);
    return node;
  }

  private AVLNode<T> doubleWithRightChild(AVLNode<T> node) {
    node.setRightChild(rotateWithLeftChild(node.right));
    return rotateWithRightChild(node);
  }

  private AVLNode<T> rotateWithRightChild(AVLNode<T> node) {
    AVLNode<T> k1 = node.right;
    node.setRightChild(k1.left);
    k1.setLeftChild(node);
    node.setHeight(Math.max(getHeight(node.left), getHeight(node.right))+1);
    k1.setHeight(Math.max(getHeight(k1.right), node.getHeight())+1);
    return k1;
  }

  private AVLNode<T> doubleWithLeftChild(AVLNode<T> node) {
    node.setLeftChild(rotateWithRightChild(node.left));
    return rotateWithLeftChild(node);
  }

  private AVLNode<T> rotateWithLeftChild(AVLNode<T> node) {
    AVLNode<T> k1 = node.left;
    node.setLeftChild(k1.right);
    k1.setRightChild(node);
    node.setHeight(Math.max(getHeight(node.left), getHeight(node.right))+1);
    k1.setHeight(Math.max(getHeight(k1.left), node.getHeight())+1);
    return k1;
  }

  @Override
  public boolean delete(T key) {
    
    if (root != null) {
      root = deleteAvl(key, root);
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
      root2.setLeftChild(deleteAvl(key, root2.left));
    } else if (compareResult > 0) {
      root2.setRightChild(deleteAvl(key, root2.right));
    } else if(root2.left != null && root2.right != null) {
      root2.setValue(getMin(root2.right).getValue());
      root2.setRightChild(deleteAvl(root2.getValue(), root2.right));
    }else{
      if(root2.left != null){
        root2 = root2.left;
      }else{
        root2 = root2.right;
      }
    }
    
    return root2;
    
  }
  
  private AVLNode<T> getMin(AVLNode<T> node) {
    AVLNode<T> current = node;
    while (current.left != null) {
      current = current.left;
    }
    return current;
  }
  
  @Override
  public boolean search(T key) {
    AVLNode<T> temp = root;
    while (temp != null) {
      if (temp.getValue().compareTo(key) == 0)
        return true;
      else if (temp.getValue().compareTo(key) > 0)
        temp = temp.right;
      else
        temp = temp.left;
    }
    return false;
  }
  
  @Override
  public int height() {
    if (root == null) {
      return 0;
    }
    return root.getHeight();
  }
  
  @Override
  public INode<T> getTree() {
    return root;
  }
  
  private int getHeight(AVLNode<T> node) {
    if (node == null) {
      return -1;
    }
    return node.getHeight();
  }
  
  private int getBalance(AVLNode<T> node) {
    if (node == null) {
      return 0;
    }
    return getHeight(node.left) - getHeight(node.right);
  }
  
 
  private class AVLNode<T extends Comparable<T>> implements INode<T> {
    
    private T element;
    private AVLNode<T> left;
    private AVLNode<T> right;
    private int height;
    
    public AVLNode(T key) {
      this.element = key;
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