package eg.edu.alexu.csd.filestructure.graphs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Graph implements IGraph {
  
  private ArrayList<ArrayList<Node>> adjList = new ArrayList<ArrayList<Node>>();
  private ArrayList<Edge> edgeList = new ArrayList<Edge>();
  private int size, sizeTest;
  private ArrayList<Integer> processedOrder;
  
  @Override
  public void readGraph(File file) {
   
    try (Scanner scanner = new Scanner(file)) {
      int v = scanner.nextInt();
      size = v;
      int numEdges = scanner.nextInt();
      for(int i=0;i<numEdges;i++){
            adjList.add(new ArrayList<Node>());
      }
      sizeTest = numEdges;
      for (int i = 0; i < numEdges; i++) {
        int fromNode = scanner.nextInt();
        int toNode = scanner.nextInt();
        int weight = scanner.nextInt();
        edgeList.add(new Edge(fromNode,toNode,weight));
        adjList.get(fromNode).add(new Node(toNode,weight));
      }
      scanner.close();
      
    } catch (IOException error) {
      throw new RuntimeException();
    }
    
  }
  
  @Override
  public int size() {
    return sizeTest;
  }
  
  @Override
  public ArrayList<Integer> getVertices() {
    ArrayList<Integer> vertices = new ArrayList<Integer>();
    for(int i=0;i<size;i++){
      vertices.add(i);
    }
    return vertices;
  }
  
  @Override
  public ArrayList<Integer> getNeighbors(int v) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int i = 0; i < edgeList.size(); i++) {
      Edge e = edgeList.get(i);
      if(e.getFrom() == v){
        result.add(e.getTo());
      }
    }
    return result;
  }
  
  @Override
  public void runDijkstra(int src, int[] distances) {
    processedOrder = new ArrayList<Integer>();
    Arrays.fill(distances, Integer.MAX_VALUE/2);
    boolean sptSet[] = new boolean[size];
    Arrays.fill(sptSet, false);
    
    distances[src] = 0;
    processedOrder.add(src);
    for (int count = 0; count < size - 1; count++) {
      int u = minDistance(distances, sptSet);
      processedOrder.add(u);
      sptSet[u] = true;
      for (int v = 0; v < adjList.get(u).size(); v++) {
        if (!sptSet[v] && adjList.get(u).get(v).getWeight() != 0 && distances[u] != Integer.MAX_VALUE/2
            && distances[u] + adjList.get(u).get(v).getWeight() < distances[v]) {
          distances[v] = distances[u] +adjList.get(u).get(v).getWeight();
        }
      }
    }
  }
  
  private int minDistance(int[] distances, boolean[] sptSet) {
    int min = Integer.MAX_VALUE/2, minIndex = -1;
    for (int v = 0; v < size; v++) {
      if (sptSet[v] == false && distances[v] <= min) {
        min = distances[v];
        minIndex = v;
      }
    }
    return minIndex;
  }
  
  @Override
  public ArrayList<Integer> getDijkstraProcessedOrder() {
    return processedOrder;
  }
  
  @Override
  public boolean runBellmanFord(int src, int[] distances) {
    Arrays.fill(distances, Integer.MAX_VALUE/2);
    distances[src] = 0;
    for (int i = 0; i < size - 1; i++) {
      for (int j = 0; j < edgeList.size(); j++) {
        Edge e = edgeList.get(j);
        if(distances[e.getFrom()]!=Integer.MAX_VALUE/2&&distances[e.getTo()] > distances[e.getFrom()] + e.getWeight()){
          distances[e.getTo()] = distances[e.getFrom()] + e.getWeight();
        }
      }
    }
    
    for (int i = 0; i < edgeList.size(); i++) {
      Edge e = edgeList.get(i);
      if(distances[e.getFrom()]!=Integer.MAX_VALUE/2&&distances[e.getTo()] > distances[e.getFrom()] + e.getWeight()){
        return false;
      }
    }
    return true;
  }
  
}
