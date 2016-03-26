package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;

public class Sort<T extends Comparable<T>> implements ISort<T> {

  @Override
  public IHeap<T> heapSort(ArrayList<T> unordered) {
    Heap<T> heap = new Heap();
    return heap.heapSort(unordered);
  }
  
  

  @Override
  public void sortSlow(ArrayList<T> unordered) {
    if (unordered == null || unordered.size() == 0) {
      throw new RuntimeException("Array is null or size is zero");
    }
    for(int i=0;i<unordered.size();i++){
      for(int j=0;j<unordered.size()-1;j++){
        if(unordered.get(j).compareTo(unordered.get(j+1)) == 1){
          
          T tmp = unordered.get(j);
          unordered.set(j , unordered.get(j+1));
          unordered.set(j+1 , tmp);
          
      }
      }
    }
  }

  
  @Override
  public void sortFast(ArrayList<T> unordered) {
    
    if (unordered == null || unordered.size() == 0) {
      throw new RuntimeException("Array is null or size is zero");
    }
    quickSort(unordered, 0, unordered.size() - 1);
    
    
  }
  
  private void quickSort(ArrayList<T> unordered, int lowerIndex, int upperIndex) {
    
    if (lowerIndex >= upperIndex){
      return;
    }
    
    int lower = lowerIndex;
    int upper = upperIndex;
    // calculate the pivot
    T pivot = unordered.get(lower + (upper - lower) / 2);

    // divide into two arrays
    while (lower <= upper) {
      while ((unordered.get(lower).compareTo(pivot)) < 0) {
        lower++;
      }

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