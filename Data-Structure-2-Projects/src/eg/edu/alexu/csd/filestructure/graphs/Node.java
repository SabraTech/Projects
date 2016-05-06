package eg.edu.alexu.csd.filestructure.graphs;

public class Node implements Comparable<Node> {
  
  private Integer v, weight;
  
  public Node(Integer v, Integer weight){
    this.v = v;
    this.weight = weight;
  }

  public int getV() {
    return v;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public int compareTo(Node o) {
    return this.weight.compareTo(o.weight);
  }
  
}
