package eg.edu.alexu.csd.oop.jdbc.sql.parser;
//done

/**
 * The Class MyEntry.
 *
 * @param <T>
 *          the generic type
 * @param <G>
 *          the generic type
 */
public class MyEntry<T, G> {

  /** The first entry. */
  private T dummy;

  /** The second entry. */
  private G dummy2;

  /**
   * Instantiates a new my entry.
   *
   * @param dummy
   *          the first entry
   * @param dummy2
   *          the second entry
   */
  public MyEntry(T dummy, G dummy2) {
    this.dummy = dummy;
    this.dummy2 = dummy2;
  }

  /**
   * Gets the first entry.
   *
   * @return entry1
   */
  public T getFirst() {
    return dummy;
  }

  /**
   * Gets the second entry.
   *
   * @return entry2
   */
  public G getSecond() {
    return dummy2;
  }

}
