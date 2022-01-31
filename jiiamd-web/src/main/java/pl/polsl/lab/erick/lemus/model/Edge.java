/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.erick.lemus.model;

/**
 * Edge Class describes the connection between origin and destination vertices
 * Also, It contains the weight between both vertices
 *
 * @author erick
 * @version 1.0
 */
public class Edge {
    
    /**
     * origin Vertice
     */
    private int uVertex;

    public void setuVertex(int uVertex) {
        this.uVertex = uVertex;
    }

    public void setvVertex(int vVertex) {
        this.vVertex = vVertex;
    }

    public void setWeigth(int weigth) {
        this.weigth = weigth;
    }
    /**
     * destination Vertice
     */
    private int vVertex;
    /**
     * weight between origin and destination Vertices
     */
    private int weigth;
    
    /**
     * Constructor
     * 
     * @param uVertex receives the origin vertice
     * @param vVertex receives the destination vertice
     * @param weigth  receives the weight between both vertices
     */
    public Edge(int uVertex, int vVertex, int weigth) {
        this.uVertex = uVertex;
        this.vVertex = vVertex;
        this.weigth = weigth;
    }
    
    /**
     * Get origin vertice of the edge
     * @return origin vertice
     */
    public int getuVertex() {
        return uVertex;
    }
    
    /**
     * Get destination vertice of the edge
     * @return destination vertice
     */
    public int getvVertex() {
        return vVertex;
    }
    
    /**
     * Get weight between origin and destination vertices
     * @return weight of the edge
     */
    public int getWeigth() {
        return weigth;
    }
    
}
