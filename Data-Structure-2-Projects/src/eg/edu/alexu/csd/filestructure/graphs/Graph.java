package eg.edu.alexu.csd.filestructure.graphs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Graph implements IGraph {
  
  private ArrayList<ArrayList<Node>> adjList = new ArrayList<ArrayList<Node>>();
  private ArrayList<Edge> edgeList = new ArrayList<Edge>();
  private Integer[][] graph;
  private int size, sizeTest;
  private ArrayList<Integer> processedOrder;
  
  @Override
  public void readGraph(File file) {
   
    try (Scanner scanner = new Scanner(file)) {
      int v = scanner.nextInt();
      size = v;
      graph = new Integer[size][size];
      int numEdges = scanner.nextInt();
      for(int i=0;i<numEdges;i++){
            adjList.add(new ArrayList<Node>());
      }
      sizeTest = numEdges;
      for (int i = 0; i < numEdges; i++) {
        int fromNode = scanner.nextInt();
        if(fromNode>=size || fromNode<0){
          throw new RuntimeException();
        }
        int toNode = scanner.nextInt();
        if(toNode>=size || toNode<0){
          throw new RuntimeException();
        }
        int weight = scanner.nextInt();
        edgeList.add(new Edge(fromNode,toNode,weight));
        adjList.get(fromNode).add(new Node(toNode,weight));
        graph[fromNode][toNode] = weight;
      }
      scanner.close();
      
    } catch (Exception error) {
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
    PriorityQueue<Node> queue = new PriorityQueue<Node>();
    
    distances[src] = 0;
    queue.add(new Node(src,0));
   while(queue.size()!=0) {
      Node e = queue.poll(); 
     processedOrder.add(e.getV());
      for (int i = 0; i < adjList.get(e.getV()).size(); i++) {
        if (distances[adjList.get(e.getV()).get(i).getV()] >distances[e.getV()]+graph[e.getV()][adjList.get(e.getV()).get(i).getV()]){
          queue.remove(new Node(adjList.get(e.getV()).get(i).getV(),distances[adjList.get(e.getV()).get(i).getV()]));
          distances[adjList.get(e.getV()).get(i).getV()]=distances[e.getV()]+graph[e.getV()][adjList.get(e.getV()).get(i).getV()];
          queue.add(new Node(adjList.get(e.getV()).get(i).getV(),distances[adjList.get(e.getV()).get(i).getV()]));
        }
      }
    }
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
