package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;

public class HashTableChaining<K, V> implements IHash<K, V>, IHashChaining {
  
  private int M;
  private int collisions;
  private int size;
  private ArrayList<ArrayList<Pair<K, V>>> table;
  
  public HashTableChaining() {
    size = 0;
    M = 1200;
    table = new ArrayList<ArrayList<Pair<K, V>>>();
    for (int i = 0; i < 1200; i++) {
      table.add(new ArrayList<Pair<K, V>>());
    }
  }
  
  private int hash(K key) {
    return (key.hashCode() & 0x7fffffff) % M;
  }
  
  @Override
  public void put(K key, V value) {
    int i = hash(key);
    collisions += table.get(i).size();
    table.get(i).add(new Pair<K, V>(key, value));
    size++;
  }
  
  @Override
  public String get(K key) {
    int i = hash(key);
    ArrayList<Pair<K, V>> list = table.get(i);
    for (int j = 0; j < list.size(); j++) {
      if (list.get(j).getKey().equals(key)) {
        return (String) list.get(j).getValue();
      }
    }
    return null;
  }
  
  @Override
  public void delete(K key) {
    int i = hash(key);
    ArrayList<Pair<K, V>> list = table.get(i);
    for (int j = 0; j < list.size(); j++) {
      if (list.get(j).getKey().equals(key)) {
        list.remove(j);
      }
    }
    
  }
  
  @Override
  public boolean contains(K key) {
    return get(key) != null;
  }
  
  @Override
  public boolean isEmpty() {
    return size == 0;
  }
  
  @Override
  public int size() {
    return size;
  }
  
  @Override
  public int capacity() {
    return M;
  }
  
  @Override
  public int collisions() {
    return collisions;
  }
  
  @Override
  public Iterable<K> keys() {
    ArrayList<K> keys = new ArrayList<K>();
    for (ArrayList<Pair<K, V>> list : table) {
      for (Pair<K, V> entry : list) {
        keys.add(entry.getKey());
      }
    }
    return keys;
  }
  
}
