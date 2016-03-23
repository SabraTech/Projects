package eg.edu.alexu.csd.filestructure.sort;

public interface IHeap<T extends Comparable<T>> extends Cloneable {
  /**
  * Return the root of the underlying binary tree
  * @return INode wrapper for the root element of the tree
  */
  INode<T> getRoot();
  /**
  * Returns the number of elements in the tree
  * @return Number of actual elements in the tree
  */
  int size();
  /**
  * Runs in O(lg n) time, is the key to maintaining the max-heap property.
  * @param heap the reference to the tree to be heapified
  */
  void heapify(INode<T> node);
  /**
  * Extract the maximum element out of the heap, and remove it from the heap.
  * Run in O(lg n) time
  * @return max element in heap
  */
  T extract();
  /**
  * Insert the given element to the heap. Run in O(lg n) time
  * @param element element to add
  */
  void insert(T element);
  /**
  * Construct the heap using the only given unordered list of elements
  * @param unordered unordered list of elements
  * @return a heap structure
  */
  void build(java.util.Collection<T> unordered);

}
