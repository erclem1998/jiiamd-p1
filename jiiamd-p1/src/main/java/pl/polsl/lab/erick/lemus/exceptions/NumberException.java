/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.erick.lemus.exceptions;


/**
 * NumberException Class extend of InputMismatchException for control any mistakes
 * in the input.
 *
 * @author erick
 * @version 1.0
 */
public class NumberException extends Exception{
    
    /**
     * Constructor with method super
     */
    public NumberException(){
        super();
    }
    
    /**
     * Get a message that describe the error
     * 
     * @return message that describes type of error
     */
    @Override
    public String getMessage(){
        return "It's not a number";
    }
    
}
