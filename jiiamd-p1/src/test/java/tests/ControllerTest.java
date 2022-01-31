package tests;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.lab.erick.lemus.controller.Controller;
import pl.polsl.lab.erick.lemus.exceptions.NumberException;
import pl.polsl.lab.erick.lemus.model.Edge;
import pl.polsl.lab.erick.lemus.model.Graph;
import pl.polsl.lab.erick.lemus.view.GuiApp;
import pl.polsl.lab.erick.lemus.view.View;

/**
 * ControllerTest check all methods of Controller Class
 * @author erick
 * @version 1.0
 */
public class ControllerTest {
    
    /**
     * Object of controller
     */
    private static Controller ctl;
    /**
     * Creates a new arraylist of edges to the graph
     */
    private static ArrayList<Edge> arr = new ArrayList<>();
    
    /**
     * This method will be executed after all test
     */
    @AfterAll
    public static void staticcheck() {
        for (Edge arr1 : arr) {
            System.out.println(arr1.getuVertex()+","+arr1.getvVertex()+","+arr1.getWeigth());
        }
    }
    
    /**
     * This method initialice controller an receives model and view
     */
    @BeforeAll
    public static void setUp() {
        // Model of the Graph
        Graph model = new Graph(0,null);
        // View of the application
        View view = new View();
        // Gui view of the applicaction
        GuiApp gApp = new GuiApp(ctl);
        ctl = new Controller(model, view, gApp);
    }
    
    /**
     * This is a parametrized test that checks if new edges contains:
     *      - only numbers in source
     *      - only numbers in destination
     *      - only numbers in weight
     * Also, checks if edge doesn't exist in the graph
     * @param source is the source point in the graph
     * @param destination is the destination point in the graph
     * @param weight is the value pf weigth between source and destination
     */
    @ParameterizedTest
    @CsvSource({"0,0,0", "0,1,2", "0,2,4", "0,3,0",
                "1,0,2", "1,1,0", "1,2,1", "1,3,5",
                "2,0,4", "2,1,1", "2,2,0", "2,3,2",
                "3,0,0", "3,1,5", "3,2,2", "3,3,0"})
    public void testAddEdgeToGraph(String source, String destination, String weight){
        //String actualValue = input.toUpperCase();
        //System.out.println(source+"->"+destination+"->"+weight);
        try {
            assertNotEquals(-1, ctl.isNumber(source), "Source should be a number");
        } catch (NumberException e) {
            fail("Fail for "+source);
        }
        try {
            assertNotEquals(-1, ctl.isNumber(destination), "Destination should be a number");
        } catch (NumberException e) {
            fail("Fail for "+destination);
        }
        try {
            assertNotEquals(-1, ctl.isNumber(weight), "Weight should be a number");
        } catch (NumberException e) {
            fail("Fail for "+weight);
        }
        int s=Integer.parseInt(source);
        int d=Integer.parseInt(destination);
        int w=Integer.parseInt(weight);
        Edge newEdge = new Edge(s, d, w);
        assertFalse(ctl.reviewIfNotExist(s, d, arr), "The edge should be false");
        arr.add(newEdge);
    }
    
    /**
     * This test checks if graph in controller is not null
     */
    @Test
    public void testSetGraph(){
        Graph graph = new Graph(4, arr);
        ctl.setGraph(graph);
        assertNotNull(ctl.getGraph(), "Should be not null");
    }

}
