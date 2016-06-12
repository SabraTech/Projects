package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;

/**
 * The Class Sort.
 *
 * @param <T>
 *          the generic type
 */
public class Sort<T extends Comparable<T>> implements ISort<T> {

  /*
   * (non-Javadoc)
   *
   * @see eg.edu.alexu.csd.filestructure.sort
   * .ISort#heapSort(java.util.ArrayList)
   */
  @Override
  public IHeap<T> heapSort(final ArrayList<T> unordered) {
    if (unordered == null || unordered.size() == 0) {
      throw new RuntimeException("Array is null or size is zero");
    }

    Heap<T> myHeap = new Heap<T>();
    ArrayList<T> orderedArray = new ArrayList<T>();
    myHeap.build(unordered);
    for (int i = unordered.size() - 1; i > 0; --i) {
      orderedArray.add(myHeap.extract());
    }
    myHeap.setSize(unordered.size());
    return myHeap;

  }

  /*
   * (non-Javadoc)
   *
   * @see eg.edu.alexu.csd.filestructure.sort
   * .ISort#sortSlow(java.util.ArrayList)
   */
  @Override
  public void sortSlow(final ArrayList<T> unordered) {
    if (unordered == null || unordered.size() == 0) {
      throw new RuntimeException("Array is null or size is zero");
    }
    for (int i = 0; i < unordered.size(); i++) {
      for (int j = 0; j < unordered.size() - 1; j++) {
        if (unordered.get(j).compareTo(unordered.get(j + 1)) == 1) {

          T tmp = unordered.get(j);
          unordered.set(j, unordered.get(j + 1));
          unordered.set(j + 1, tmp);

        }
      }
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see eg.edu.alexu.csd.filestructure.sort
   * .ISort#sortFast(java.util.ArrayList)
   */
  @Override
  public void sortFast(final ArrayList<T> unordered) {

    if (unordered == null || unordered.size() == 0) {
      throw new RuntimeException("Array is null or size is zero");
    }
    quickSort(unordered, 0, unordered.size() - 1);

  }

  /**
   * Quick sort.
   *
   * @param unordered
   *          the unordered
   * @param lowerIndex
   *          the lower index
   * @param upperIndex
   *          the upper index
   */
  private void quickSort(final ArrayList<T> unordered,
      final int lowerIndex, final int upperIndex) {

    if (lowerIndex >= upperIndex) {
      return;
    }

    int lower = lowerIndex;
    int upper = upperIndex;
    // calculate the pivot
    T pivot = unordered.get(lower + (upper - lower) / 2);

    // divide into two arrays
    while (lower <= upper) {
      // <
      while ((unordered.get(lower).compareTo(pivot)) < 0) {
        lower++;
      }
      // >
      while ((unordered.get(upper).compareTo(pivot)) > 0) {
        upper--;
      }

      if (lower <= upper) {
        T tmp = unordered.get(lower);
        unordered.set(lower, unordered.get(upper));
        unordered.set(upper, tmp);
        lower++;
        upper--;
      }
    }

    if (lowerIndex < upper) {
      quickSort(unordered, lowerIndex, upper);
    }
    if (lower < upperIndex) {
      quickSort(unordered, lower, upperIndex);
    }

  }

}
