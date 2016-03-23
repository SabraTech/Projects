package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;
import java.util.Collection;

public class Heap<T extends Comparable<T>> implements IHeap<T> {

  private ArrayList<INode<T>> tree;
  private int size;

  public Heap() {
    tree = new ArrayList<INode<T>>();
    size = 0;
  }

  @Override
  public INode<T> getRoot() {
    if (size == 0) {
      return null;
    }
    return tree.get(0);
  }

  @Override
  public int size() {

    return size;
  }

  @Override
  public void heapify(INode<T> node) {

    if (size == 0) {
      return;
    }

    INode<T> left = node.getLeftChild();
    INode<T> right = node.getRightChild();
    INode<T> max = node;

    if ( left != null && left.getValue().compareTo(node.getValue()) > 0) {
      max = left;
    } 

    if ( right != null && left.getValue().compareTo(max.getValue()) > 0) {
      max = right;
    }

    if (max.getValue().compareTo(node.getValue()) != 0) {
      swap(max,node);
      heapify(max);
    }


  }

  @Override
  public T extract() {
    if (size() == 0) {
      return null;
    }

    INode<T> root = tree.get(0);
    T hold = root.getValue();
    swap(root, tree.get(size - 1));
    size--;
    heapify(getRoot());
    return hold;
  }

  @Override
  public void insert(T element) {
    if(tree.size() == size){
      tree.add(new Node(element, size++));
    }else{
      tree.set(size,new Node(element, size++));
    }
    
    
    INode<T> tmp = tree.get(size-1);
    
    while(tmp.getParent() != null && tmp.getParent().getValue().compareTo(tmp.getValue()) < 0){
      swap(tmp, tmp.getParent());
      tmp = tmp.getParent();
    }
  }

  @Override
  public void build(Collection<T> unordered) {
    size = unordered.size();
    tree.clear();
    
    int index = 0;
    for(T node : unordered){
      tree.add(new Node(node,index++));
    }
    
    for(int i=size/2-1;i>=0;--i){
      heapify(tree.get(i));
    }
    

  }

  private void swap(INode<T> i, INode<T> j) {
    T tmp = i.getValue();
    i.setValue(j.getValue());
    j.setValue(tmp);
  }

  private class Node implements INode<T> {
    
    private T value;
    private int index;
    
    public Node(T value, int index){
      this.value = value;
      this.index = index;
    }

    @Override
    public INode<T> getLeftChild() {
      if(index*2+1 < Heap.this.size){
        return Heap.this.tree.get(index*2+1);
      }
      return null;
    }

    @Override
    public INode<T> getRightChild() {
      if(index*2+2 < Heap.this.size){
        return Heap.this.tree.get(index*2+2);
      }
      return null;
    }

    @Override
    public INode<T> getParent() {
      if(index == 0){
        return null;
      }
      return Heap.this.tree.get((index-1)/2);
    }

    @Override
    public T getValue() {

      return value;
    }

    @Override
    public void setValue(T value) {
      this.value = value;
    }

  }

}
