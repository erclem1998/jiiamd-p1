package pl.polsl.lab.erick.lemus.main;

import pl.polsl.lab.erick.lemus.view.GuiApp;
import java.util.ArrayList;
import pl.polsl.lab.erick.lemus.controller.Controller;
import pl.polsl.lab.erick.lemus.model.Edge;
import pl.polsl.lab.erick.lemus.model.Graph;
import pl.polsl.lab.erick.lemus.view.View;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 * Main Class starts Application to find the shortest path since an origin point to the 
 * rest of points in the graph
 * @author erick
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Using default args
        int vertexNumber=0;
        ArrayList<Edge> defArray = null;
        if(args.length>0){
            vertexNumber = Integer.parseInt(args[0]);
            defArray = new ArrayList<>();
            for (int i=1;i<args.length;i++){
                String[] listValue = args[i].split(",");
                Edge nEdge = new Edge(Integer.parseInt(listValue[0]), Integer.parseInt(listValue[1]), Integer.parseInt(listValue[2]));
                defArray.add(nEdge);
            }
        }
        // Model of the Graph
        Graph model = new Graph(vertexNumber,defArray);
        // View of the application
        View view = new View();
        GuiApp guiapp = new GuiApp(new Controller(null,null,null));
        // Controller contains all logic of the application 
        Controller controller = new Controller(model, view, guiapp);
        // Execute de application
        controller.execute(true);
        
    }

}
