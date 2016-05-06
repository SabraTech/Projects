package eg.edu.alexu.csd.filestructure.graphs;

public class Edge{
  
  private Integer from, to, weight;
  
  
  public Integer getFrom() {
    return from;
  }

  public Integer getTo() {
    return to;
  }

  public Integer getWeight() {
    return weight;
  }

  public Edge(Integer from, Integer to, Integer weight){
    this.from = from;
    this.to = to;
    this.weight = weight;
  }
  
}
