/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.erick.lemus.algorithm;

import java.util.ArrayList;
import pl.polsl.lab.erick.lemus.model.Edge;

/**
 * BellmanFord Class contains all logic of the algorithm
 * @author erick
 * @version 1.1
 */
public class BellmanFord {
    
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
    public BellmanFord(int vertices) {
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
     * Get the MAX_VALUE that represents an infinite number
     * @return MAX_VALUE of one weight
     */
    public static int getMAX_VALUE() {
        return MAX_VALUE;
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
     * This method executes the algorithm of Bellman-Ford
     * @param graph receives an arraylist of edges that represents a graph
     * @param source receives a vertice that represents our source
     * @return It returns true if there isn't a negative cycle, 
     * and false if there is a negative cycle
     */
    public boolean executeBellmanFord(int source, ArrayList<Edge> graph) {
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
        
        // All distances are initialized with the MAX_VALUE 
        // Except the position of our source vertice
        for (int i = 0; i < vertices; i++) {
            distances.add(MAX_VALUE);
        }
        distances.set(source, 0);
        // Search the shortest path from source to the remaining vertices
        // it's vertices - 1 because we omit one iteration that is of the source
        for (int i = 0; i < vertices - 1; i++) {
            // We update the distance for adjacent vertices to the source
            for (int j = 0; j < graph.size(); j++) {
                /**
                 * To update the distance, we need some conditions:
                 * - distance[source]!=infinite
                 * - distance[Remaining vertice]>distance[source]+ (weight of the actual iteration)
                 */
                if (graph.get(j).getWeigth()!= 0 && distances.get(graph.get(j).getuVertex()) != MAX_VALUE && distances.get(graph.get(j).getvVertex()) > distances.get(graph.get(j).getuVertex()) + graph.get(j).getWeigth()) {
                    /**
                     * If the condition is true:
                     * - distance[Remaining vertice] = distance[source]+ (weight of the actual iteration)
                     * - path[Remaining vertice] = path[source] + "->" + Remaining vertice of the actual iteration
                     */
                    distances.set(graph.get(j).getvVertex(), distances.get(graph.get(j).getuVertex()) + graph.get(j).getWeigth());
                    path.set(graph.get(j).getvVertex(), path.get(graph.get(j).getuVertex()) + "->" + Integer.toString(graph.get(j).getvVertex()));
                }
            }
        }
        //Now, we need to review if some distance of one vertice can be updated yet
        for (int i = 0; i < graph.size(); i++) {
            //To review if one distance can be updated yet, we need some conditions
            //- distance[actual iteration] != infinite
            //- distance[Remaining vertice] > distance[Actual iteration]+ (weight of the actual iteration)
            if (graph.get(i).getWeigth()!= 0 && distances.get(graph.get(i).getuVertex()) != MAX_VALUE && distances.get(graph.get(i).getuVertex()) + graph.get(i).getWeigth() < distances.get(graph.get(i).getvVertex())) {
                //if this condition is true, we return false
                //this indicates that our graph contains a negative cycle
                return true;
            }
        }
        //if all is correct, we return true
        //this indicates that our graph doesn't have a negative cycle
        return false;
    }
}
