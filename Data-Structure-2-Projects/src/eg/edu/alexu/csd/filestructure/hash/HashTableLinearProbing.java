package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;


/**
 * The Class HashTableLinearProbing.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class HashTableLinearProbing<K, V>
implements IHash<K, V>, IHashLinearProbing {

  /** The capacity. */
  private int capacity;

  /** The size. */
  private int size;

  /** The collisions. */
  private int collisions;

  /** The table. */
  private ArrayList<Pair<K, V>> table;

  /** The orderOfAdd. */
  private ArrayList<Pair<K, V>> orderOfAdd;

  /**
   * Instantiates a new hash table linear probing.
   */
  public HashTableLinearProbing() {
    size = 0;
    capacity = 1200;
    collisions = 0;
    table = new ArrayList<Pair<K, V>>();
    orderOfAdd = new ArrayList<Pair<K, V>>();
    for (int i = 0; i < 1200; i++) {
      table.add(null);
    }
  }

  /**
   * Hash.
   *
   * @param key the key
   * @return the int
   */
  private int hash(final K key) {
    return (key.hashCode() & 0x7fffffff) % capacity;
  }

  /* (non-Javadoc)
   * @see eg.edu.alexu.csd.filestructure.hash.IHash#
   * put(java.lang.Object, java.lang.Object)
   */
  @Override
  public void put(final K key, final V value) {
    int j;
    orderOfAdd.add(new Pair<K, V>(key, value));
    int count = 0;
    for (j = hash(key); count < capacity;j = (j + 1) % capacity) {
      if (table.get(j) == null || table.get(j).getKey().equals(-1)) {
        break;
      }
      count++;
    }
    if (count == capacity) {
      reHash();
      collisions += count + 1;
      return;
    }
    if (count != 0) {
      collisions += count + 1;
    }
    table.set(j, new Pair<K, V>(key, value));
    size++;

  }

  /**
   * Re hash.
   */
  private void reHash() {
    ArrayList<Pair<K, V>> tmp = new ArrayList<Pair<K, V>>();
    capacity = capacity * 2;
    size = 0;

    for (Pair<K, V> p : orderOfAdd) {
      tmp.add(p);
    }

    table = new ArrayList<Pair<K, V>>();
    orderOfAdd = new ArrayList<Pair<K,V>>();
    for (int i = 0; i < capacity; i++) {
      table.add(null);
    }

    for (Pair<K, V> p : tmp) {
        put(p.getKey(), p.getValue());
    }
  }

  /* (non-Javadoc)
   * @see eg.edu.alexu.csd.filestructure.hash.IHash#get(java.lang.Object)
   */
  @Override
  public String get(final K key) {
    for (int i = hash(key); table.get(i) != null; i = (i + 1) % capacity) {
      if (key.equals(table.get(i).getKey())) {
        return (String) table.get(i).getValue();
      }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see eg.edu.alexu.csd.filestructure.hash.IHash#delete(java.lang.Object)
   */
  @Override
  public void delete(final K key) {
    for (int i = hash(key); table.get(i) != null; i = (i + 1) % capacity) {
      if (key.equals(table.get(i).getKey())) {
        table.set(i, new Pair<K, V>((K) new Integer(-1),
            table.get(i).getValue()));
        size--;
        return;
      }
    }
  }

  /* (non-Javadoc)
   * @see eg.edu.alexu.csd.filestructure.hash.IHash#contains(java.lang.Object)
   */
  @Override
  public boolean contains(final K key) {
    return get(key) != null;
  }

  /* (non-Javadoc)
   * @see eg.edu.alexu.csd.filestructure.hash.IHash#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /* (non-Javadoc)
   * @see eg.edu.alexu.csd.filestructure.hash.IHash#size()
   */
  @Override
  public int size() {
    return size;
  }

  /* (non-Javadoc)
   * @see eg.edu.alexu.csd.filestructure.hash.IHash#capacity()
   */
  @Override
  public int capacity() {
    return capacity;
  }

  /* (non-Javadoc)
   * @see eg.edu.alexu.csd.filestructure.hash.IHash#collisions()
   */
  @Override
  public int collisions() {
    return collisions;
  }

  /* (non-Javadoc)
   * @see eg.edu.alexu.csd.filestructure.hash.IHash#keys()
   */
  @Override
  public Iterable<K> keys() {
    ArrayList<K> keys = new ArrayList<K>();
    for (Pair<K, V> tmp : table) {
      if (tmp != null) {
        keys.add(tmp.getKey());
      }
    }
    return keys;
  }
}
