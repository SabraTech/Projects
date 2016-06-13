package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;


/**
 * The Class HashTableDoubleHashing.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class HashTableDoubleHashing<K, V>
implements IHash<K, V>, IHashDouble {

  /** The capacity. */
  private int capacity;

  /** The size. */
  private int size;

  /** The collisions. */
  private int collisions;

  /** The double hash factor. */
  private int doubleHashFactor;

  /** The table. */
  private ArrayList<Pair<K, V>> table;

  /** The orderOfAdd. */
  private ArrayList<Pair<K, V>> orderOfAdd;

  /**
   * Instantiates a new hash table double hashing.
   */
  public HashTableDoubleHashing() {
    size = 0;
    capacity = 1200;
    collisions = 0;
    doubleHashFactor = largestPrime(capacity);
    table = new ArrayList<Pair<K, V>>();
    orderOfAdd = new ArrayList<Pair<K, V>>();
    for (int i = 0; i < 1200; i++) {
      table.add(null);
    }
  }

  /**
   * Largest prime.
   *
   * @param target the target
   * @return the int
   */
  private int largestPrime(final int target) {
    for (int i = target - 1; i > 0; i--) {
      boolean prime = true;
      for (int j = 2; j * j <= i && prime; j++) {
        if (i % j == 0) {
          prime = false;
        }
      }
      if (prime) {
        return i;
      }
    }
    return 0;
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

  /**
   * Hash2.
   *
   * @param key the key
   * @return the int
   */
  private int hash2(final K key) {
    return 1193 - ((Integer) key % 1193);
  }

  /* (non-Javadoc)
   * @see eg.edu.alexu.csd.filestructure.hash.IHash#put(java.lang.
   * Object, java.lang.Object)
   */
  @Override
  public void put(final K key, final V value) {
    int j;
    int h2 = hash2(key);
      orderOfAdd.add(new Pair<K, V>(key, value));
      int count = 0;
      for (j = hash(key);count < capacity;
          j = (hash(key) + count * h2) % capacity) {
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
        collisions += count;
      }
      table.set(j, new Pair<K, V>(key, value));
      size++;
  }

  /**
   * Re hash.
   */
  private void reHash() {
    doubleHashFactor = largestPrime(capacity * 2);
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
    int h = hash(key);
    int h2 = hash2(key);

    for (; table.get(h) != null; h = (int) (h + h2) % capacity) {
      if (table.get(h).getKey().equals(key)) {
        return (String) table.get(h).getValue();
      }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see eg.edu.alexu.csd.filestructure.hash.IHash#delete(java.lang.Object)
   */
  @Override
  public void delete(final K key) {
    int h = hash(key);
    int h2 = hash2(key);

    for (; table.get(h) != null; h = (int) (h + h2) % capacity) {
      if (table.get(h).getKey().equals(key)) {
        table.set(h, new Pair<K, V>((K) new Integer(-1),
            table.get(h).getValue()));
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
