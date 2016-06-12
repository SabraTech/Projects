package eg.edu.alexu.csd.filestructure.graphs;


/**
 * The Class Node.
 */
public class Node implements Comparable<Node> {

  /** The weight. */
  private Integer v, weight;

  /**
   * Instantiates a new node.
   *
   * @param v the v
   * @param weight the weight
   */
  public Node(final Integer v, final Integer weight) {
    this.v = v;
    this.weight = weight;
  }

  /**
   * Gets the v.
   *
   * @return the v
   */
  public int getV() {
    return v;
  }

  /**
   * Gets the weight.
   *
   * @return the weight
   */
  public int getWeight() {
    return weight;
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(final Node o) {
    return this.weight.compareTo(o.weight);
  }

}
