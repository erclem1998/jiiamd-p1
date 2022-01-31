/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.erick.lemus.view;

import java.util.ArrayList;
import java.util.Scanner;
import pl.polsl.lab.erick.lemus.model.Edge;

/**
 * View Class contains all necessary messages for the application
 * @author erick
 * @version 1.1
 */
public class View {
    
    /**
     * Constructor
     */
    public View() {}
    
    /**
     * Print options in the first menu
     */
    public void printMenu() {
        System.out.println("1. Create Graph");
        System.out.println("2. Solve default example");
        System.out.println("3. Exit");
        System.out.println("Option: ");
    }
    
    /**
     * Print if we want to insert a new edge or finish insertions
     */
    public void printSelectWay(){
        System.out.println("1. Insert Edge (origin, destination, weight)");
        System.out.println("2. Finish Insertions");
    }
    
    /**
     * Print the graph in console
     * @param graph receives an arraylist of edges that represents a graph
     * and this method uses varargs
     */
    public void printArrayList(Edge... graph){
        //Using for of java 5
        for(Edge e: graph){
            System.out.println("("+e.getuVertex()+","+e.getvVertex()+","+e.getWeigth()+")");
        }
    }
    
    /**
     * Print if one edge already exist
     * @param origin is the origin vertice in the edge
     * @param destination is the destinatio vertice in the edge
     */
    public void printEdgeExist(int origin, int destination){
        System.out.println("This Edge ("+origin+","+destination+") already exist.");
    }
    
    /**
     * Print options of enabled algorithms in the application
     */
    public void printAlgorithmOptions(){
        System.out.println("1. Dijkstra");
        System.out.println("2. Bellman-Ford");
        System.out.println("3. Print Graph");
        System.out.println("4. Return");
        System.out.println("Option: ");
    }
    
    /**
     * Print an exception message.
     * @param message is the message got with the exception
     */
    public void printException(String message){
        System.err.println(message);
        System.out.println("Try again.");
    }
    
    /**
     * Print some message in console
     * @param message is the message that we want to print
     * @param isInput receives true if we want to write some input, false if is only a print message
     * @return a string value
     */
    public String printMessage(String message, boolean isInput){
        System.out.println(message);
        if(isInput==false){
            return null;
        }
        Scanner scan = new Scanner(System.in);
        return scan.next();
    }
    
    /**
     * Print the solution of the graph (Vertice   Distance   Path)
     * from source vertice
     * @param distances is an array, it has the distance to the remaining vertices
     * @param path distances is an array, it has the path to the remaining vertices
     * @param MAX_VALUE is a value like infinite
     */
    public void printSolution(ArrayList<String> path, ArrayList<Integer> distances, int MAX_VALUE) {
        System.out.println("Vertice \t\t Distance \t Path");
        for (int i = 0; i < distances.size(); i++) {
            if(distances.get(i)==MAX_VALUE){
                System.out.println(i + " \t\t " + "Not Allowed" + " \t\t " + path.get(i));
                continue;
            }
            System.out.println(i + " \t\t " + distances.get(i) + " \t\t " + path.get(i));
        }
    }
}
