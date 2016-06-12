package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;


/**
 * The Class HashTableChaining.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class HashTableChaining<K, V> implements IHash<K, V>, IHashChaining {
  
  /** The capacity. */
  private int capacity;
  
  /** The collisions. */
  private int collisions;
  
  /** The size. */
  private int size;
  
  /** The table. */
  private ArrayList<ArrayList<Pair<K, V>>> table;

  /**
   * Instantiates a new hash table chaining.
   */
  public HashTableChaining() {
    size = 0;
    capacity = 1200;
    table = new ArrayList<ArrayList<Pair<K, V>>>();
    for (int i = 0; i < 1200; i++) {
      table.add(new ArrayList<Pair<K, V>>());
    }
  }

  /**
   * Hash.
   *
   * @param key the key
   * @return the int
   */
  private int hash(K key) {
    return (key.hashCode() & 0x7fffffff) % capacity;
  }

  /**
   * Put.
   *
   * @param key the key
   * @param value the value
   */
  @Override
  public void put(K key, V value) {
    int i = hash(key);
    collisions += table.get(i).size();
    table.get(i).add(new Pair<K, V>(key, value));
    size++;
  }

  /**
   * Gets the.
   *
   * @param key the key
   * @return the string
   */
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

  /**
   * Delete.
   *
   * @param key the key
   */
  @Override
  public void delete(K key) {
    int i = hash(key);
    ArrayList<Pair<K, V>> list = table.get(i);
    for (int j = 0; j < list.size(); j++) {
      if (list.get(j).getKey().equals(key)) {
        list.remove(j);
        size--;
        return;
      }
    }
  }

  /**
   * Contains.
   *
   * @param key the key
   * @return true, if successful
   */
  @Override
  public boolean contains(K key) {
    return get(key) != null;
  }

  /**
   * Checks if is empty.
   *
   * @return true, if is empty
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Size.
   *
   * @return the int
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Capacity.
   *
   * @return the int
   */
  @Override
  public int capacity() {
    return capacity;
  }

  /**
   * Collisions.
   *
   * @return the int
   */
  @Override
  public int collisions() {
    return collisions;
  }

  /**
   * Keys.
   *
   * @return the iterable
   */
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
