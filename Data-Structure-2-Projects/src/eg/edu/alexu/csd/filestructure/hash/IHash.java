package eg.edu.alexu.csd.filestructure.hash;

public interface IHash<K, V> {
  /** put keyvalue pair into the table */
  public void put(K key, V value);
  
  /** get value paired with key, return null if hash don't contain key */
  public String get(K key);
  
  /** remove key (and its value) from table */
  public void delete(K key);
  
  /** return true if there is a value paired with key and false otherwise. */
  public boolean contains(K key);
  
  /** return true if the table is empty. */
  public boolean isEmpty();
  
  /** return the number of the active entries in the table */
  public int size();
  
  /** return the size of the table (including the empty slots). */
  public int capacity();
  
  /** return the total number of collisions occured so far. */
  public int collisions();
  
  /** all keys in the table */
  Iterable<K> keys();
}
