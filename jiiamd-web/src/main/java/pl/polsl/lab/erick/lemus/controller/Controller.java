/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.erick.lemus.controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;
import pl.polsl.lab.erick.lemus.algorithm.BellmanFord;
import pl.polsl.lab.erick.lemus.algorithm.Dijkstra;
import pl.polsl.lab.erick.lemus.exceptions.NumberException;
import pl.polsl.lab.erick.lemus.model.Edge;
import pl.polsl.lab.erick.lemus.model.Graph;
import pl.polsl.lab.erick.lemus.view.GuiApp;
import pl.polsl.lab.erick.lemus.view.View;

/**
 * Controller Class contains all logic to the user
 *
 * @author erick
 * @version 1.1
 */
public class Controller {

    /**
     * Our graph model
     */
    private Graph graph;
    /**
     * Contains all views, messages, etc
     */
    private View view;
    /**
     * Gui view of the application
     */
    //private GuiApp gApp;

    /**
     * Constructor
     *
     * @param graph is the graph that we want to find the shortest path
     * @param view user functionalities
     */
    public Controller(Graph graph, View view) {
        this.graph = graph;
        this.view = view;
        //this.gApp = gApp;
    }

    /**
     * get model of the graph
     *
     * @return the model of the graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * This method saves the model of the graph
     *
     * @param graph receive the model of the graph
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * This method starts all logic of the application
     * @param showGui boolean value that controls if we use console view or gui view
     */
    public void execute(boolean showGui) {
        if (!showGui) {
            view.printMessage("------------MENU-----------", false);
            int selectOption = 0;
            // this while doesn't permit finishing the aplication, only if the user select the option
            while (selectOption != 3) {
                view.printMenu();
                // variable that saves the string value of the input
                String stringValue = view.printMessage("", true);
                try {
                    // variable that saves the number inserted
                    selectOption = isNumber(stringValue);
                    switch (selectOption) {
                        // if we want to create one graph
                        case 1:
                            int vertices = isNumber(view.printMessage("Write number of Vertex: ", true));
                            ArrayList<Edge> graphMatrix = new ArrayList<>();
                            writeConnections(graphMatrix, vertices);
                            graph.setGraph(graphMatrix);
                            graph.setVertexNumber(vertices);
                            selectAlgorithm(graph.getVertexNumber(), graph.getGraph());
                            break;
                        // if we want to close the application
                        case 2:
                            view.printMessage("Solve default example...", false);
                            if (graph.getGraph() != null) {
                                view.printArrayList(graph.getGraph().toArray(new Edge[0]));
                                selectAlgorithm(graph.getVertexNumber(), graph.getGraph());
                            } else {
                                view.printMessage("Non default arguments...", false);
                            }
                            break;
                        // if we want to close the application
                        case 3:
                            view.printMessage("Finishing program.", false);
                            break;
                        // if it's an invalid option
                        default:
                            view.printMessage("Invalid option. Try again", false);
                            break;
                    }
                } catch (NumberException e) {
                    // show the error for the exception
                    view.printException(e.getMessage());
                }
            }
        }
        else{
            //gApp.setVisible(true);
        }
    }

    /**
     * This function review it the input only contains numbers
     *
     * @param value is the input of the user
     * @return return the number that the user has inserted
     * @throws NumberException this exception works if the input contains
     * symbols differente to numbers
     */
    public int isNumber(String value) throws NumberException {
        // With a regular expresion, we review if the input only contains numbers
        if (value.matches("[0-9]+") || value.matches("-[0-9]+")) {
            return Integer.parseInt(value);
        } else {
            // the exception is launched
            throw new NumberException();
        }
    }

    /**
     * this method inserts edges in the graph
     *
     * @param graph is the actual graph
     * @param vertexNumber is number of vertices that we need in the graph
     */
    void writeConnections(ArrayList<Edge> graph, int vertexNumber) {
        // for get inputs
        for (int i = 0; i < vertexNumber * vertexNumber;) {
            view.printSelectWay();
            // variable that saves the string value of the input
            String stringValue = view.printMessage("", true);
            try {
                // variable that saves the number inserted
                int option = isNumber(stringValue);
                if (option == 1) {
                    // variable that saves the number inserted
                    int origin = isNumber(view.printMessage("Insert Origin: ", true));
                    // variable that saves the number inserted
                    int destination = isNumber(view.printMessage("Insert Destination: ", true));
                    // variable that saves the number inserted
                    int weight = isNumber(view.printMessage("Insert Weight: ", true));
                    // check if (origin, destination) doesn't exist
                    if (reviewIfNotExist(origin, destination, graph) == false) {
                        // if it's true, we create a new edge
                        Edge edge = new Edge(origin, destination, weight);
                        graph.add(edge);
                        i++;
                    } else {
                        // print that the edge already exist
                        view.printEdgeExist(origin, destination);
                    }
                } else if (option == 2) {
                    // if we dont need more edges in the graph
                    break;
                } else {
                    // if it's an invalid option
                    view.printMessage("Invalid option. Try again", false);
                }
            } catch (NumberException e) {
                // we print message of the exception
                view.printException(e.getMessage());
            }
        }
        // finally, we print the graph
        view.printArrayList(graph.toArray(new Edge[0]));
    }

    /**
     * This method checks if one edge already exist in the graph Also, it uses
     * Stream and anyMatch function to insert conditions
     *
     * @param origin receives an origin vertice
     * @param destination receives a destination vertice
     * @param graph receives the actual graph
     * @return true if edge already exist, otherwise returns false
     */
    public boolean reviewIfNotExist(int origin, int destination, ArrayList<Edge> graph) {
        // Use of Java Stream
        Stream<Edge> stream = graph.stream();
        return stream.anyMatch(e -> e.getuVertex() == origin && e.getvVertex() == destination);

        /*for (Edge e: graph) {
            //check if [originVertice in the actual iteration] == [inserted
            //origin] check if [destinationVertice in the actual iteration] == [inserted destination]
            if (e.getuVertex() == origin && e.getvVertex() == destination) {
                return true;
            }
        }
        return false;*/
    }

    /**
     * This method select the algorithm that we want to use
     *
     * @param vertexNumber receive the number of vertices in the graph
     * @param graph is our graph
     */
    void selectAlgorithm(int vertexNumber, ArrayList<Edge> graph) {
        int option;
        // this while doesn't permit finishing the aplication, only if the user select the option
        while (true) {
            try {
                // print options of algorithms
                view.printAlgorithmOptions();
                // variable that saves the number inserted
                option = isNumber(view.printMessage("", true));
                boolean flag = false;
                switch (option) {
                    case 1:
                        // it's the Dijkstra algorithm
                        view.printMessage("Selected Dijkstra", false);
                        // variable that saves the number inserted
                        int source = isNumber(view.printMessage("Write your source: ", true));
                        // check if there are elements in the graph
                        if (graph.size() >= 1) {
                            // We check if source that we want exist in the graph
                            if (sourceExist(source, graph)) {
                                Dijkstra d = new Dijkstra(vertexNumber);
                                d.dijkstra(source, graph);
                                view.printSolution(d.getPath(), d.getDistances(), d.getMAX_VALUE());
                            } else {
                                view.printMessage("Source doesn't exist in this graph.", false);
                            }
                        } else {
                            view.printMessage("There aren't elements in the graph", false);
                        }
                        break;
                    case 2:
                        // it's the Bellman-Ford algorithm
                        view.printMessage("Selected Bellman-Ford", false);
                        // variable that saves the number inserted
                        int sourceBellman = isNumber(view.printMessage("Write your source: ", true));
                        // check if there are elements in the graph
                        if (graph.size() >= 1) {
                            // We check if source that we want exist in the graph
                            if (sourceExist(sourceBellman, graph)) {
                                BellmanFord b = new BellmanFord(vertexNumber);
                                boolean bellmanFord = b.executeBellmanFord(sourceBellman, graph);
                                // We review if the graph contains a negative cycle
                                if (!bellmanFord) {
                                    view.printSolution(b.getPath(), b.getDistances(), b.getMAX_VALUE());
                                } else {
                                    // if it's true, the graph contains a negative cycle
                                    view.printMessage("There is a negative cycle.", false);
                                }
                            } else {
                                view.printMessage("Source doesn't exist in this graph.", false);
                            }
                        } else {
                            view.printMessage("There aren't elements in the graph", false);
                        }
                        break;
                    case 3:
                        // we print the graph
                        view.printArrayList(graph.toArray(new Edge[0]));
                        break;
                    case 4:
                        // Return to the previous menu
                        view.printMessage("Returning.", false);
                        flag = true;
                        break;
                    default:
                        // It's an invalid option
                        view.printMessage("Invalid option. Try again", false);
                }
                if (flag) {
                    // breaks loop for return to the previous menu
                    break;
                }
            } catch (NumberException e) {
                view.printException(e.getMessage());
            }
        }
    }

    /**
     * This function review if the selected source exist in the graph
     *
     * @param source is the selected source
     * @param graph is the graph with all edges
     * @return true if the selected source exist, otherwise return false
     */
    boolean sourceExist(int source, ArrayList<Edge> graph) {
        for (Edge e : graph) {
            // check if vertice in the actual iteration == source
            if (e.getuVertex() == source) {
                // returns true if it already exist
                return true;
            }
        }
        return false;
    }

}
