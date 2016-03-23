package eg.edu.alexu.csd.filestructure.sort;

public interface ISort<T extends Comparable<T>> {
  /**
   * Sorts the given collection of elements using heap-sort algorithm in-place,
   * and returns a clone of the complete heap that you constructed during the
   * sorting, and before you empty it. Runs in O(n lg n) time
   * 
   * @param unordered
   *          unordered elements
   * @return heap structure used
   */
  IHeap<T> heapSort(java.util.ArrayList<T> unordered);

  /**
   * Sorts the given collection of elements using any O(n^2) algorithm in-place
   * 
   * @param unordered
   *          unordered elements
   */
  void sortSlow(java.util.ArrayList<T> unordered);

  /**
   * Sorts the given collection of elements using any O(n lg n) algorithm
   * in-place
   * 
   * @param unordered
   *          unordered elements
   */
  void sortFast(java.util.ArrayList<T> unordered);
}
