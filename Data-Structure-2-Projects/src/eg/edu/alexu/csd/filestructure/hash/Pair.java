package eg.edu.alexu.csd.filestructure.hash;


/**
 * The Class Pair.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class Pair<K, V> {

  /** The key. */
  private K key;

  /** The value. */
  private V value;

  /**
   * Instantiates a new pair.
   *
   * @param key the key
   * @param value the value
   */
  public Pair(final K key, final V value) {
    this.key = key;
    this.value = value;
  }

  /**
   * Gets the key.
   *
   * @return the key
   */
  public K getKey() {
    return key;
  }

  /**
   * Sets the key.
   *
   * @param key the new key
   */
  public void setKey(final K key) {
    this.key = key;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public V getValue() {
    return value;
  }

  /**
   * Sets the value.
   *
   * @param value the new value
   */
  public void setValue(final V value) {
    this.value = value;
  }

}
