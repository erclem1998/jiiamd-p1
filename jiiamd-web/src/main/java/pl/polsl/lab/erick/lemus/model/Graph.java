/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.erick.lemus.model;

import java.util.ArrayList;

/**
 * Graph Class contains the model of the graph.
 * This contains an arraylist of Edges that represents a graph
 * @author erick
 * @version 1.1
 */
public class Graph {
    
    /**
     * Number of vertices in the graph
     */
    private int vertexNumber;
    /**
     * Represents a graph, where each edge has 3 values (origin, destination, weight)
     */
    private ArrayList<Edge> graph;
    
    /**
     * Constructor
     * 
     * @param vertexNumber receive a number of vertices of the graph
     * @param graph receive an arraylist of Edges with form (origin,destination,weight)
     */
    public Graph(int vertexNumber, ArrayList<Edge> graph) {
        this.vertexNumber = vertexNumber;
        this.graph = graph;
    }
    
    /**
     * Return the number of vertices in the graph
     * 
     * @return number of vertices 
     */
    public int getVertexNumber() {
        return vertexNumber;
    }
    
    /**
     * Returns the graph, it's saved in one arraylist
     * 
     * @return the graph saved
     */
    public ArrayList<Edge> getGraph() {
        return graph;
    }
    
    /**
     * Receives number of vertices that we want to save
     * 
     * @param vertexNumber is the number of vertices that we need in the graph
     */
    public void setVertexNumber(int vertexNumber) {
        this.vertexNumber = vertexNumber;
    }
    
    /**
     * Receives the graph that we want to save
     * 
     * @param graph receives an arraylist with all edges
     */
    public void setGraph(ArrayList<Edge> graph) {
        this.graph = graph;
    }
    
}
