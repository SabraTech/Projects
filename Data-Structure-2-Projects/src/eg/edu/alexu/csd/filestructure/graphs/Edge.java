package eg.edu.alexu.csd.filestructure.graphs;


/**
 * The Class Edge.
 */
public class Edge {

  /** The weight. */
  private Integer from, to, weight;


  /**
   * Gets the from.
   *
   * @return the from
   */
  public Integer getFrom() {
    return from;
  }

  /**
   * Gets the to.
   *
   * @return the to
   */
  public Integer getTo() {
    return to;
  }

  /**
   * Gets the weight.
   *
   * @return the weight
   */
  public Integer getWeight() {
    return weight;
  }

  /**
   * Instantiates a new edge.
   *
   * @param from the from
   * @param to the to
   * @param weight the weight
   */
  public Edge(final Integer from, final Integer to, final Integer weight){
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

}
