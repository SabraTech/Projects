package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;

public class Sort<T extends Comparable<T>> implements ISort<T> {

  @Override
  public IHeap<T> heapSort(ArrayList<T> unordered) {
    if (unordered == null || unordered.size() == 0) {
      throw new RuntimeException("Array is null or size is zero");
    }
    
    IHeap<T> myHeap = new Heap<T>();
    IHeap<T> sortHeap = new Heap<T>();
    myHeap.build(unordered);
    sortHeap.build(unordered);
    
    for(int i=unordered.size()-1;i>0;--i){
      sortHeap.extract();
    }
    return sortHeap;
  }

  @Override
  public void sortSlow(ArrayList<T> unordered) {
    if (unordered == null || unordered.size() == 0) {
      throw new RuntimeException("Array is null or size is zero");
    }
    quickSort(unordered, 0, unordered.size() - 1);
  }

  private void quickSort(ArrayList<T> unordered, int lowerIndex, int upperIndex) {

    int lower = lowerIndex;
    int upper = upperIndex;
    // calculate the pivot
    T pivot = unordered.get(lower + (upper - lower) / 2);

    // divide into two arrays
    while (lower <= upper) {
      while ((unordered.get(lower).compareTo(pivot)) < 0) {
        lower++;
      }

      while ((unordered.get(lower).compareTo(pivot)) > 0) {
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

  @Override
  public void sortFast(ArrayList<T> unordered) {
    if (unordered == null || unordered.size() == 0) {
      throw new RuntimeException("Array is null or size is zero");
    }
    ArrayList<T> tmp = new ArrayList<T>();
    mergeSort(unordered, tmp, 0, unordered.size() - 1);

  }

  private void mergeSort(ArrayList<T> unordered, ArrayList<T> tmp, int left, int right) {
    if (left < right) {
      int center = (left + right) / 2;
      mergeSort(unordered, tmp, left, center);
      mergeSort(unordered, tmp, center + 1, right);
      merge(unordered, tmp, left, center + 1, right);
    }

  }

  private void merge(ArrayList<T> unordered, ArrayList<T> tmp, int left, int middle, int right) {
    int center = middle - 1;
    int k = left;
    int num = middle - left + 1;

    while (left <= center && middle <= right) {
      if (unordered.get(left).compareTo(unordered.get(middle)) <= 0) {
        tmp.set(k++, unordered.get(left++));
      } else {
        tmp.set(k++, unordered.get(middle++));
      }
    }

    // Copy rest of first half
    while (left <= center) {
      tmp.set(k++, unordered.get(left++));
    }

    // Copy rest of right half
    while (middle <= right) {
      tmp.set(k++, unordered.get(middle++));
    }

    // Copy tmp back to unorderdList
    for (int i = 0; i < num; i++, right--) {
      unordered.set(right, tmp.get(right));
    }

  }

}
