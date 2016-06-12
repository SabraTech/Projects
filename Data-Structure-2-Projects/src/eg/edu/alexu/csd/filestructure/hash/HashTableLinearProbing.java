package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;

public class HashTableLinearProbing<K, V> implements IHash<K, V>, IHashLinearProbing {
  
  private int M;
  private int size;
  private int collisions;
  private ArrayList<Pair<K, V>> table;
  
  public HashTableLinearProbing() {
    size = 0;
    M = 1200;
    collisions = 0;
    table = new ArrayList<Pair<K, V>>();
    for (int i = 0; i < 1200; i++) {
      table.add(null);
    }
  }
  
  private int hash(K key) {
    return (key.hashCode() & 0x7fffffff) % M;
  }
  
  @Override
  public void put(K key, V value) {
    int i;
    int j;
    for (i = hash(key); table.get(i) != null
        && !table.get(i).getKey().equals(-1); i = (i + 1) % M) {
      if (table.get(i).equals(key)) {
        break;
      }
    }
    for (j = hash(key); table.get(j) != null; j = (j + 1) % M) {
      collisions++;
    }
    table.set(i, new Pair<K, V>(key, value));
    size++;
    
    if (size == M) {
      reHash();
    }
    
  }
  
  private void reHash() {
    ArrayList<Pair<K, V>> tmp = new ArrayList<Pair<K, V>>();
    for (int i = 0; i < M; i++) {
      tmp.add(null);
    }
    M = M * 2;
    size = 0;
    int count = 0;
    
    for (Pair<K, V> p : table) {
      tmp.set(count++, p);
    }
    
    table = new ArrayList<Pair<K, V>>();
    for (int i = 0; i < M; i++) {
      table.add(null);
    }
    
    for (Pair<K, V> p : tmp) {
      if (p != null) {
        put(p.getKey(), p.getValue());
      }
    }
  }
  
  @Override
  public String get(K key) {
    for (int i = hash(key); table.get(i) != null; i = (i + 1) % M) {
      if (key.equals(table.get(i).getKey())) {
        return (String) table.get(i).getValue();
      }
    }
    return null;
  }
  
  @Override
  public void delete(K key) {
    for (int i = hash(key); table.get(i) != null; i = (i + 1) % M) {
      if (key.equals(table.get(i).getKey())) {
        table.set(i, new Pair<K, V>((K) new Integer(-1), table.get(i).getValue()));
        size--;
        return;
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
    for (Pair<K, V> tmp : table) {
      keys.add(tmp.getKey());
    }
    return keys;
  }
  
}
