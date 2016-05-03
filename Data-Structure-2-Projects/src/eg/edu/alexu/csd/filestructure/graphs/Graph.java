package eg.edu.alexu.csd.filestructure.graphs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Graph implements IGraph {
  
  private Integer[][] graph;
  private int size, v, numEdges;
  private ArrayList<Integer> processedOrder;
  
  public Graph(){
    
  }
  
  @Override
  public void readGraph(File file) {
    try (Scanner scanner = new Scanner(file)) {
      v = scanner.nextInt();
      numEdges = scanner.nextInt();
      size = numEdges;
      graph = new Integer[v][v];
      for (int i = 0; i < numEdges; i++) {
        int fromNode = scanner.nextInt();
        int toNode = scanner.nextInt();
        int weight = scanner.nextInt();
        graph[fromNode][toNode] = weight;
      }
      scanner.close();
      
    } catch (IOException error) {
      throw new RuntimeException();
    }
    
  }
  
  @Override
  public int size() {
    return size;
  }
  
  @Override
  public ArrayList<Integer> getVertices() {
    ArrayList<Integer> vertices = new ArrayList<Integer>();
    for(int i=0;i<v;i++){
      vertices.add(i);
    }
    return vertices;
  }
  
  @Override
  public ArrayList<Integer> getNeighbors(int v) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int i = 0; i < graph.length; i++) {
      if (graph[v][i] != null) {
        result.add(i);
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
      for (int v = 0; v < size; v++) {
        if (!sptSet[v] && graph[u][v] != 0 && distances[u] != Integer.MAX_VALUE/2
            && distances[u] + graph[u][v] < distances[v]) {
          distances[v] = distances[u] + graph[u][v];
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
      for (int j = 0; j < size; j++) {
        for (int k = 0; k < size; k++) {
          if (graph[j][k] != Integer.MAX_VALUE/2) {
            if (distances[k] > distances[j] + graph[j][k]) {
              distances[k] = distances[j] + graph[j][k];
            }
          }
        }
      }
    }
    
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (graph[i][j] != Integer.MAX_VALUE) {
          if (distances[j] > distances[i] + graph[i][j]) {
            return false;
          }
        }
      }
    }
    return true;
  }
  
}
