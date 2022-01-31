package tests;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.lab.erick.lemus.model.Edge;

/**
 * EdgeTest check all methods of Edge Class
 * @author erick
 * @version 1.0
 */
public class EdgeTest {   
    
    /**
     * This test checks if next elements are not zero:
     *      - source (uVertex)
     *      - destination (vVertex)
     *      - weight
     */
    @Test
    public void checkNewEdge(){
        Edge newEdge = new Edge(1, 5, 4);
        assertNotEquals(0, newEdge.getuVertex(),"Uvertex Shouldn't be zero");
        assertEquals(5, newEdge.getvVertex(),"Vvertex Should be 5");
        assertNotEquals(0, newEdge.getWeigth(),"Weight Shouldn't be zero");
    }
    
}
