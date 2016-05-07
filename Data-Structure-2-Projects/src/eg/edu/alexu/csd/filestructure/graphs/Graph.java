package eg.edu.alexu.csd.filestructure.graphs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * The Class Graph.
 */
public class Graph implements IGraph {

  /** The adj list. */
  private ArrayList<ArrayList<Node>> adjList = new ArrayList<ArrayList<Node>>();

  /** The edge list. */
  private ArrayList<Edge> edgeList = new ArrayList<Edge>();

  /** The graph. */
  private Integer[][] graph;

  /** The size test. */
  private int size, sizeTest;

  /** The processed order. */
  private ArrayList<Integer> processedOrder;

  /*
   * (non-Javadoc)
   *
   * @see eg.edu.alexu.csd.filestructure.graphs.IGraph#readGraph(java.io.File)
   */
  @Override
  public void readGraph(final File file) {

    try (Scanner scanner = new Scanner(file)) {
      int v = scanner.nextInt();
      size = v;
      graph = new Integer[size][size];
      int numEdges = scanner.nextInt();
      for (int i = 0; i < numEdges; i++) {
        adjList.add(new ArrayList<Node>());
      }
      sizeTest = numEdges;
      for (int i = 0; i < numEdges; i++) {
        int fromNode = scanner.nextInt();
        if (fromNode >= size || fromNode < 0) {
          throw new RuntimeException();
        }
        int toNode = scanner.nextInt();
        if (toNode >= size || toNode < 0) {
          throw new RuntimeException();
        }
        int weight = scanner.nextInt();
        edgeList.add(new Edge(fromNode, toNode, weight));
        adjList.get(fromNode).add(new Node(toNode, weight));
        graph[fromNode][toNode] = weight;
      }
      scanner.close();

    } catch (Exception error) {
      throw new RuntimeException();
    }

  }

  /*
   * (non-Javadoc)
   *
   * @see eg.edu.alexu.csd.filestructure.graphs.IGraph#size()
   */
  @Override
  public int size() {
    return sizeTest;
  }
  
  /*
   * (non-Javadoc)
   *
   * @see eg.edu.alexu.csd.filestructure.graphs.IGraph#getVertices()
   */
  @Override
  public ArrayList<Integer> getVertices() {
    ArrayList<Integer> vertices = new ArrayList<Integer>();
    for (int i = 0; i < size; i++) {
      vertices.add(i);
    }
    return vertices;
  }

  /*
   * (non-Javadoc)
   *
   * @see eg.edu.alexu.csd.filestructure.graphs.IGraph#getNeighbors(int)
   */
  @Override
  public ArrayList<Integer> getNeighbors(final int v) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int i = 0; i < edgeList.size(); i++) {
      Edge e = edgeList.get(i);
      if (e.getFrom() == v) {
        result.add(e.getTo());
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   *
   * @see eg.edu.alexu.csd.filestructure.graphs.IGraph#runDijkstra(int, int[])
   */
  @Override
  public void runDijkstra(final int src, final int[] distances) {
    processedOrder = new ArrayList<Integer>();
    Arrays.fill(distances, Integer.MAX_VALUE / 2);
    PriorityQueue<Node> queue = new PriorityQueue<Node>();

    distances[src] = 0;
    queue.add(new Node(src, 0));
    while (queue.size() != 0) {
      Node e = queue.poll();
      processedOrder.add(e.getV());
      for (int i = 0; i < adjList.get(e.getV()).size(); i++) {
        if (distances[adjList.get(e.getV()).get(i).getV()] > distances[e.getV()]
            + graph[e.getV()][adjList.get(e.getV()).get(i).getV()]) {
          queue.remove(new Node(adjList.get(e.getV()).get(i).getV(),
              distances[adjList.get(e.getV()).get(i).getV()]));
          distances[adjList.get(e.getV()).get(i).getV()] = distances[e.getV()]
              + graph[e.getV()][adjList.get(e.getV()).get(i).getV()];
          queue.add(new Node(adjList.get(e.getV()).get(i).getV(),
              distances[adjList.get(e.getV()).get(i).getV()]));
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see eg.edu.alexu.csd.filestructure.graphs.IGraph#getDijkstraProcessedOrder()
   */
  @Override
  public ArrayList<Integer> getDijkstraProcessedOrder() {
    return processedOrder;
  }

  /*
   * (non-Javadoc)
   *
   * @see eg.edu.alexu.csd.filestructure.graphs.IGraph#runBellmanFord(int, int[])
   */
  @Override
  public boolean runBellmanFord(final int src, final int[] distances) {
    Arrays.fill(distances, Integer.MAX_VALUE / 2);
    distances[src] = 0;
    for (int i = 0; i < size - 1; i++) {
      for (int j = 0; j < edgeList.size(); j++) {
        Edge e = edgeList.get(j);
        if (distances[e.getFrom()] != Integer.MAX_VALUE / 2
            && distances[e.getTo()] > distances[e.getFrom()] + e.getWeight()) {
          distances[e.getTo()] = distances[e.getFrom()] + e.getWeight();
        }
      }
    }

    for (int i = 0; i < edgeList.size(); i++) {
      Edge e = edgeList.get(i);
      if (distances[e.getFrom()] != Integer.MAX_VALUE / 2
          && distances[e.getTo()] > distances[e.getFrom()] + e.getWeight()) {
        return false;
      }
    }
    return true;
  }

}
