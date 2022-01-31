package tests;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.lab.erick.lemus.algorithm.BellmanFord;
import pl.polsl.lab.erick.lemus.model.Edge;

/**
 * BellmanFordTest check all methods of BellmanFord Class
 * @author erick
 * @version 1.0
 */
public class BellmaFordTest {
    
    /**
     * It's an instance to execute executeBellmanFord
     */
    private BellmanFord bm;
    /**
     * This arraylist will save all edges of the graph in test
     */
    private static ArrayList<Edge> arr = new ArrayList<>();
    
    /**
     * This method creates a new instance of the object BellmanFord
     */
    @BeforeEach
    public void setUpBF(){
        bm = new BellmanFord(4);
    }
    
    /**
     * This method will be executed after all test
     */
    @AfterAll
    public static void check() {
        for (Edge arr1 : arr) {
            System.out.println(arr1.getuVertex()+","+arr1.getvVertex()+","+arr1.getWeigth());
        }
    }
    
    /**
     * This is a parametrized test that checks if new edges are created correctly
     * The assert checked in this test is assertNotNull
     * @param source is the source point in the graph
     * @param destination is the destination point in the graph
     * @param weight is the value pf weigth between source and destination
     */
    @ParameterizedTest
    @CsvSource({"0,0,0", "0,1,2", "0,2,4", "0,3,0",
                "1,0,2", "1,1,0", "1,2,1", "1,3,5",
                "2,0,4", "2,1,1", "2,2,0", "2,3,2",
                "3,0,0", "3,1,5", "3,2,2", "3,3,0"})
    public void setUp(String source, String destination, String weight) {
        Edge newEdge = new Edge(Integer.parseInt(source), Integer.parseInt(destination), Integer.parseInt(weight));
        assertNotNull(newEdge, "This object shouldn't be null");
        arr.add(newEdge);
    }
    
    /**
     * This test checks if number of vertices in the graph is not zero
     */
    @Test
    public void testNewBellmanFord(){
        assertNotEquals(0, bm.getVertices(), "Number of Vertices shouldn't be zero");
    }
    
    /**
     * This test executes BellmanFord algorithm and checks if the graph does not contains
     * a negative cycle
     */
    @Test
    public void testBellmanFordFunction(){
        System.out.println(arr.size());
        assertEquals(false, bm.executeBellmanFord(0, arr), "Graph shouldn't have a negative cycle");
    }
}
