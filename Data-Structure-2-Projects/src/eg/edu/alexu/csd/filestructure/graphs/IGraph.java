package eg.edu.alexu.csd.filestructure.graphs;

import java.util.ArrayList;

public interface IGraph {
  /**
   * Constructs a graph with the number of vertices and set of edges provided in the file. Input
   * file will contain several lines that describe the graph structure as follows. First line
   * contains two integers V and E which determine number of vertices and edges respectively. This
   * line is followed by E lines describing the edges in the graph. Each of the E lines contain 3
   * numbers: i, j, w separated by a single space. i,j represents the nodes that are connected
   * through this edge (0 <= i, j < V) and w determines the weight on this edge. Weights may be
   * negative or positive.
   * 
   * @param file
   *          The graph file
   */
  public void readGraph(java.io.File file);
  
  /**
   * Return the size of the graph
   * 
   * @return Size of the graph
   */
  public int size();
  
  /**
   * Returns the set of vertices in the graph
   * 
   * @return List of vertices
   */
  public ArrayList<Integer> getVertices();
  
  /**
   * Returns a list of the neighboring vertices to the vertex v
   * 
   * @param v
   *          ID of a vertex in the graph
   * @return A list of vertices adjacent to v
   */
  public ArrayList<Integer> getNeighbors(int v);
  
  /**
   * Runs the Dijkstra single-source shortest path algorithm on the graph
   * 
   * @param src
   *          ID of the source vertex for the shortest path algorithm
   * @param distances
   *          An array to be filled with distances from src to all vertices in the graph
   */
  public void runDijkstra(int src, int[] distances);
  
  /**
   * Returns the order of vertices with which Dijkstra processed them
   * 
   * @return List of vertices ordered processed by Dijkstra's SSSP algorithm
   */
  public ArrayList<Integer> getDijkstraProcessedOrder();
  
  /**
   * Runs the Bellman-Ford single-source shortest path algorithm on the graph
   * 
   * @param src
   *          ID of the source vertex for the shortest path algorithm
   * @param distances
   *          An array to be filled with distances from src to all vertices in the graph
   * @return False if the graph contains a negative cycle. True otherwise.
   */
  public boolean runBellmanFord(int src, int[] distances);
}
