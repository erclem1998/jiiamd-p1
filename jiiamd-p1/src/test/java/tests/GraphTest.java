package tests;

/*
 * Click nbfs,//nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs,//nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.lab.erick.lemus.model.Edge;
import pl.polsl.lab.erick.lemus.model.Graph;

/**
 * GraphTest check all methods of Graph Class
 * @author erick
 * @version 1.0
 */
public class GraphTest {
    
    /**
     * It's a Graph object
     */
    private Graph graph;
    /**
     * This arraylist will save all edges of the graph in test
     */
    private ArrayList<Edge> arr = new ArrayList<>();
    
    /**
     * This method create and initialice the graph
     */
    @BeforeEach
    public void setUp(){
        this.graph = new Graph(0, null);
    }
    
    /**
     * Thist test checks next elements with their respective asserts:
     *      - If graph is null
     *      - if number of vertices is zero
     */
    @Test
    public void testSetUp(){
        assertEquals(null, this.graph.getGraph(), "graph should be null when app starts");
        assertEquals(0, this.graph.getVertexNumber(), "vertexNumber should be 0 when app starts");
    }
    
    /**
     * This test set a graph and number of vertices. 
     * Also, checks of number of vertices is not zero and if graph is not null
     */
    @Test
    public void testNewValues(){
        this.graph.setGraph(arr);
        this.graph.setVertexNumber(4);
        assertNotEquals(0, this.graph.getVertexNumber(), "Vertex number shouldn't be zero");
        assertNotNull(this.graph.getGraph(), "Graph shouldn't be null");
    }
    
}
