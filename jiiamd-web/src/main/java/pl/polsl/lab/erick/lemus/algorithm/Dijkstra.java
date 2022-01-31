/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.erick.lemus.algorithm;

import java.util.ArrayList;
import pl.polsl.lab.erick.lemus.model.Edge;

/**
 * Dijkstra Class contains all logic of the algorithm
 * @author erick
 * @version 1.1
 */
public class Dijkstra {
    
    /**
     * Number of vertices in the graph
     */
    private int vertices;
    /**
     * Number that represents the infinite
     */
    private static final int MAX_VALUE = 999999;
    /**
     * Array that will contain all distances
     */
    private ArrayList<Integer> distances;
    /**
     * Array that will contain all paths
     */
    private ArrayList<String> path;
    
    /**
     * Constructor
     * @param vertices receive number of vertices of the graph
     */
    public Dijkstra(int vertices) {
        this.vertices = vertices;
    }
    
    /**
     * Get number of vertices in the graph
     * @return number of vertices in the graph
     */
    public int getVertices() {
        return vertices;
    }
    
    /**
     * Get an integer array that contains calculated distances from
     * source to the remaining vertices
     * @return distances from source to the remaining vertices
     */
    public ArrayList<Integer> getDistances() {
        return distances;
    }
    
    /**
     * Get a string array that contains paths from 
     * source to the remaining vertices
     * @return paths from source to the remaining vertices
     */
    public ArrayList<String> getPath() {
        return path;
    }
    
    /**
     * Get the MAX_VALUE that represents an infinite number
     * @return MAX_VALUE of one weight
     */
    public static int getMAX_VALUE() {
        return MAX_VALUE;
    }
    
    
    /**
     * This method executes the algorithm of dijkstra
     * @param graph receives an arraylist of edges that represents a graph
     * @param source receives a vertice that represents our source
     * @return It returns true if the execution was satisfatory, 
     * and false if there is a negative cycle
     */
    public boolean dijkstra(int source, ArrayList<Edge> graph)
    {
        //Initialize an integer array with the same quantity of vertices in the graph
        // It will have all distances from source to the remaining vertices
        distances = new ArrayList<Integer>();
        //Initialize a string array with the same quantity of vertices in the graph
        //It will have all paths from source to the remaining vertices
        path = new ArrayList<String>();
        
        // All paths ever starts with the source vertice
        for (int i = 0; i < vertices; i++) {
            path.add(Integer.toString(source));
        }
        
        //We create a Boolean array, that represents if one vertice is included in the path
        ArrayList<Boolean> isIncluded = new ArrayList<>();
 
        // All distances are initialized with the MAX_VALUE 
        // Except the position of our source vertice
        for (int i = 0; i < vertices; i++) {
            distances.add(MAX_VALUE);
            isIncluded.add(false);
        }
        distances.set(source,0);
        // Search the shortest path from source to the remaining vertices
        // it's vertices - 1 because we omit one iteration that is of the source
        for (int i = 0; i < vertices-1; i++) {
            int u = getIndexWithMinDistance(distances, isIncluded);
            isIncluded.set(u, true);
            for(int j=0;j<vertices;j++){
                if (!isIncluded.get(j) && getWeight(u, j, graph) != 0 && distances.get(u) != MAX_VALUE && distances.get(u) + getWeight(u, j, graph) < distances.get(j)){
                        distances.set(j, distances.get(u) + getWeight(u, j, graph));
                        path.set(j, path.get(u)+"->"+Integer.toString(j));
                }
            }
        }
        return true;
    }
    
    /**
     * This function returns an index that has the minimum distance
     * @param distance receive an integer array that contains all distances
     * @param isIncluded receive a Boolean array that contains if one vertices is included in the path
     * @return a number, it's one index
     */
    int getIndexWithMinDistance(ArrayList<Integer> distance, ArrayList<Boolean> isIncluded){
        // represents the minimun distance
        int min = MAX_VALUE;
        // it's our index with the minimun distance
        int minIndex=-1;
        for (int vertex = 0; vertex < vertices; vertex++) {
            //if the actual vertice is not included yet and
            //distance[actual vertice] <= min
            if(isIncluded.get(vertex) == false && distance.get(vertex) <= min){
                // we update the min value to the distance[actual vertice]
                min = distance.get(vertex);
                minIndex = vertex;
            }
        }
        return minIndex;
    }
    
    /**
     * This function returns weight between source and destination vertices
     * @param source is the source vertice
     * @param destination is the destination vertice
     * @param graph is our graph
     * @return a number, this represents the weight
     */
    public int getWeight(int source, int destination, ArrayList<Edge> graph){
        for(int i=0;i<graph.size();i++){
            //If in the actual iteration our source and destination are the same to the
            //parameters, we return the weigth
            if(graph.get(i).getuVertex()==source && graph.get(i).getvVertex()==destination){
                return graph.get(i).getWeigth();
            }
        }
        return 0;
    }
    
}
