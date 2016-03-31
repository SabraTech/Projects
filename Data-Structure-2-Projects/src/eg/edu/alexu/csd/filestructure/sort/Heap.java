package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The Class Heap.
 *
 * @param <T>
 *          the generic type
 */
public class Heap<T extends Comparable<T>> implements IHeap<T> {

  /** The tree. */
  private ArrayList<INode<T>> tree;

  /** The size. */
  private int size;

  /**
   * Instantiates a new heap.
   */
  public Heap() {
    tree = new ArrayList<INode<T>>();
    size = 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.filestructure.sort.IHeap#getRoot()
   */
  @Override
  public INode<T> getRoot() {
    if (size == 0) {
      return null;
    }
    return tree.get(0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.filestructure.sort.IHeap#size()
   */
  @Override
  public int size() {

    return size;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.filestructure.sort.IHeap#heapify(eg.edu.alexu.csd.
   * filestructure.sort.INode)
   */
  @Override
  public void heapify(final INode<T> node) {

    if (size == 0 || size == 1) {
      return;
    }

    INode<T> left = node.getLeftChild();
    INode<T> right = node.getRightChild();
    INode<T> max = node;
    boolean flag = false;

    if (left != null && left.getValue().compareTo(node.getValue()) > 0) {
      max = left;
      flag = true;
    }

    if (right != null && right.getValue().compareTo(max.getValue()) > 0) {
      max = right;
      flag = true;
    }

    if (flag) {
      swap(max, node);
      heapify(max);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.filestructure.sort.IHeap#extract()
   */
  @Override
  public T extract() {
    if (size() == 0) {
      throw new RuntimeException("Size is zero");
    }

    if (size() == 1) {
      INode<T> root = tree.get(0);
      T value = root.getValue();
      size--;
      return value;
    }

    INode<T> root = tree.get(0);
    T hold = root.getValue();
    swap(root, tree.get(size - 1));
    size--;
    heapify(getRoot());
    return hold;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.filestructure.sort.IHeap#insert(java.lang.Comparable)
   */
  @Override
  public void insert(final T element) {
    if (tree.size() == size) {
      tree.add(new Node(element, size++));
    } else {
      tree.set(size, new Node(element, size++));
    }

    INode<T> tmp = tree.get(size - 1);

    while (tmp.getParent() != null && tmp.getParent().getValue()
        .compareTo(tmp.getValue()) < 0) {
      swap(tmp, tmp.getParent());
      tmp = tmp.getParent();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eg.edu.alexu.csd.filestructure.sort.IHeap#build(java.util.Collection)
   */
  @Override
  public void build(final Collection<T> unordered) {

    size = unordered.size();
    tree.clear();

    int index = 0;
    for (T node : unordered) {
      tree.add(new Node(node, index++));
    }

    for (int i = size / 2 - 1; i >= 0; --i) {
      heapify(tree.get(i));
    }

  }

  /**
   * Sets the size.
   *
   * @param size
   *          the new size
   */
  public void setSize(final int size) {
    
    this.size = size;
  }

  /**
   * Swap.
   *
   * @param i
   *          the i
   * @param j
   *          the j
   */
  private void swap(INode<T> i, INode<T> j) {
    T tmp = i.getValue();
    i.setValue(j.getValue());
    j.setValue(tmp);
  }

  /**
   * The Class Node.
   */
  private class Node implements INode<T> {

    /** The value. */
    private T value;

    /** The index. */
    private int index;

    /**
     * Instantiates a new node.
     *
     * @param value
     *          the value
     * @param index
     *          the index
     */
    public Node(T value, int index) {
      this.value = value;
      this.index = index;
    }

    /*
     * (non-Javadoc)
     * 
     * @see eg.edu.alexu.csd.filestructure.sort.INode#getLeftChild()
     */
    @Override
    public INode<T> getLeftChild() {
      if (index * 2 + 1 < Heap.this.size) {
        return Heap.this.tree.get(index * 2 + 1);
      }
      return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see eg.edu.alexu.csd.filestructure.sort.INode#getRightChild()
     */
    @Override
    public INode<T> getRightChild() {
      if (index * 2 + 2 < Heap.this.size) {
        return Heap.this.tree.get(index * 2 + 2);
      }
      return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see eg.edu.alexu.csd.filestructure.sort.INode#getParent()
     */
    @Override
    public INode<T> getParent() {
      if (index == 0) {
        return null;
      }
      return Heap.this.tree.get((index - 1) / 2);
    }

    /*
     * (non-Javadoc)
     * 
     * @see eg.edu.alexu.csd.filestructure.sort.INode#getValue()
     */
    @Override
    public T getValue() {
      return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * eg.edu.alexu.csd.filestructure.sort.INode#setValue(java.lang.Comparable)
     */
    @Override
    public void setValue(T value) {
      this.value = value;
    }

  }

}
