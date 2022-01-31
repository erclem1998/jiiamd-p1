package tests;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.polsl.lab.erick.lemus.view.View;

/**
 * ViewTest check all methods of View Class
 * @author erick
 * @version 1.0
 */
public class ViewTest {
    
    /**
     * Objecto of the view
     */
    private static View view;
    
    /**
     * This method initialice the view
     */
    @BeforeAll
    public static void setUp() {
        view = new View();
    }
    
    /**
     * This test checks if view is not null
     */
    @Test 
    public void isInitialized(){
        assertNotNull(view, "view shouldn't be null");
    }
    
    /**
     * This test checks if we printed only a message
     */
    @Test
    public void verifyIfIsMessage(){
        assertNull(view.printMessage("Testing this method", false), "printMessage should be null");
    }

}
