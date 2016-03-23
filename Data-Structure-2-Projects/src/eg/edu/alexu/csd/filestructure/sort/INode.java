package eg.edu.alexu.csd.filestructure.sort;

public interface INode<T extends Comparable<T>> {
  /**
  * Returns the left child of the current element/node in the heap tree
  * @return INode wrapper to the left child of the current element/node
  */
  INode<T> getLeftChild();
  /**
  * Returns the right child of the current element/node in the heap tree
  * @return INode wrapper to the right child of the current element/node
  */
  INode<T> getRightChild();
  /**
  * Returns the parent node of the current element/node in the heap tree
  * @return INode wrapper to the parent of the current element/node
  */
  INode<T> getParent();
  /**
  * Set/Get the value of the current node
  * @return Value of the current node
  */
  T getValue();
  void setValue(T value);

}
