package eg.edu.alexu.csd.filestructure.avl;

public class AvlTree<T extends Comparable<T>> implements IAVLTree<T> {

  private AVLNode root;

  public AvlTree() {
    root = null;
  }

  @Override
  public void insert(T key) {
    root = insertAvl(key, root);
  }

  private AVLNode insertAvl(T key, AVLNode root2) {
    if (root2 == null) {
      return new AVLNode(key);
    }
    int compareResult = key.compareTo(root2.getValue());

    if (compareResult < 0) {
      root2.left = insertAvl(key, root2.left);
    } else {
      root2.right = insertAvl(key, root2.right);
    }

    root2.height = (Math.max(getHeight(root2.left), getHeight(root2.right)) + 1);

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

  @Override
  public boolean delete(T key) {

    if (search(key)) {
      root = deleteAvl(key, root);
      return true;
    } else {
      return false;
    }

  }

  private AVLNode deleteAvl(T key, AVLNode root2) {
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

      root2.setHeight(Math.max(getHeight(root2.left), getHeight(root2.right)) + 1);

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

  private AVLNode getMin(AVLNode node) {
    AVLNode current = node;
    while (current.left != null) {
      current = current.left;
    }
    return current;
  }

  @Override
  public boolean search(T key) {
    AVLNode temp = root;
    while (temp != null) {
      if (temp.getValue().compareTo(key) == 0)
        return true;
      else if (temp.getValue().compareTo(key) > 0)
        temp = temp.left;
      else
        temp = temp.right;
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
    return (INode) root;
  }

  private int getHeight(AVLNode node) {
    if (node == null) {
      return 0;
    }
    return node.getHeight();
  }

  private int getBalance(AVLNode node) {
    if (node == null) {
      return 0;
    }
    return getHeight(node.left) - getHeight(node.right);
  }

  private AVLNode rotateRight(AVLNode y) {
    AVLNode x = y.left;
    AVLNode t2 = x.right;

    x.setRightChild(y);
    y.setLeftChild(t2);

    y.setHeight((Math.max(getHeight(y.left), getHeight(y.right)) + 1));
    x.setHeight( (Math.max(getHeight(x.left), getHeight(x.right)) + 1) );

    return x;
  }

  private AVLNode rotateLeft(AVLNode x) {
    AVLNode y = x.right;
    AVLNode t2 = y.left;

    y.setLeftChild(x);
    x.setRightChild(t2);

    x.setHeight((Math.max(getHeight(x.left), getHeight(x.right)) + 1));
    y.setHeight((Math.max(getHeight(y.left), getHeight(y.right)) + 1));

    return y;
  }

  private class AVLNode implements INode<T> {
    private T element;
    private AVLNode left;
    private AVLNode right;
    private int height;

    public AVLNode(T key) {
      this.element = key;
      this.height = 1;
    }

    @Override
    public INode<T> getLeftChild() {
      return this.left;
    }

    @Override
    public INode<T> getRightChild() {
      return this.right;
    }

    public void setLeftChild(AVLNode left) {
      this.left = left;
    }

    public void setRightChild(AVLNode right) {
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