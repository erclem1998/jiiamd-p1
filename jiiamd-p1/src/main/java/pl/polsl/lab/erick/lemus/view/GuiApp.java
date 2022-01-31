/*
 * This file contains the view designed
 */
package pl.polsl.lab.erick.lemus.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import pl.polsl.lab.erick.lemus.algorithm.BellmanFord;
import pl.polsl.lab.erick.lemus.algorithm.Dijkstra;
import pl.polsl.lab.erick.lemus.controller.Controller;
import pl.polsl.lab.erick.lemus.exceptions.NumberException;
import pl.polsl.lab.erick.lemus.model.GraphModel;
import pl.polsl.lab.erick.lemus.model.RowNumberTable;
import pl.polsl.lab.erick.lemus.model.Edge;

/**
 * This class contains all components for the applicaction
 * @author erick
 * @version 1.0
 */
public class GuiApp extends JFrame implements ActionListener, ItemListener {
    
    /**
     * It's our main table where we can put in all edges
     */
    private JTable graphTable;
    /**
     * it will show the history of actions
     */
    private JTable historyTable;
    /**
     * It will show the solution (vertice, distance and path)
     */
    private JTable solutions;
    /**
     * Model of the solution table
     */
    private DefaultTableModel solutionModel;
    /**
     * Model of the history table
     */
    private DefaultTableModel historyModel;
    /**
     * Represents number of vertices of our graph
     */
    private int vertices = 2;
    /**
     * It will contain all columns that represents one vertice
     */
    private String[] columnNames;
    /**
     * It's our tabbed pane with 3 different tabs
     */
    private JTabbedPane tabbedPane;
    /**
     * It will show vertical and horizontal scroll
     */
    private JScrollPane js;
    /**
     * to access to some functions
     */
    private Controller ctl;
    /**
     * It will controlls if we have solved with any algorithm at least one time
     */
    private boolean graphGenerated;

    /**
     * Constructor of the class
     * @param ctl is the current controller
     */
    public GuiApp(Controller ctl) {
        super("Dijkstra/Bellman-Ford");
        this.ctl = ctl;
        init();
    }

    /**
     * This method will start the interface
     */
    private void init() {
        //set size of the gui app
        this.setBounds(250, 100, 500, 250);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        graphGenerated = false;
        tabbedPane = new JTabbedPane();
        
        //create teable first time
        createTable(vertices);
        
        //---------------------------Solutions pane-----------------------------
        //default columns and datasolutions
        String[] clNamesSolution = {"Vertice", "Distance", "Path"};
        Object[][] dataHistorySolution = {
            {null, null, null}
        };
        solutionModel = new DefaultTableModel(dataHistorySolution, clNamesSolution);
        solutions = new JTable(solutionModel);
        JTable rowTableSolution = new RowNumberTable(solutions);
        JScrollPane jsModel = new JScrollPane(solutions, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsModel.setRowHeaderView(rowTableSolution);
        jsModel.setCorner(JScrollPane.UPPER_LEFT_CORNER,rowTableSolution.getTableHeader());
        tabbedPane.addTab("Solutions", jsModel);
        
        //-------------------------History Pane---------------------------------
        String[] clNames = {"Action"};
        Object[][] dataHistory = {
            {"Application started."}
        };
        historyModel = new DefaultTableModel(dataHistory, clNames);
        historyTable = new JTable(historyModel);
        JTable rowTableHistory = new RowNumberTable(historyTable);
        JScrollPane jsHistory = new JScrollPane(historyTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsHistory.setRowHeaderView(rowTableHistory);
        jsHistory.setCorner(JScrollPane.UPPER_LEFT_CORNER,rowTableHistory.getTableHeader());
        tabbedPane.addTab("History", jsHistory);
        this.add(tabbedPane);
        
        //--------------------------Menu bar component--------------------------
        this.setJMenuBar(this.createMenuBar());
    }

    /**
     * This funciont will create a menu bar
     * @return it will return our menu with all options
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        
        //----------------------------Action options----------------------------
        menuBar = new JMenuBar();
        menu = new JMenu("Actions");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);
        //***************************Create new graph---------------------------    
        menuItem = new JMenuItem("Create New Graph", KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.addActionListener(new ActionListener() {
            /**
             * Action for create a new graph
             */
            public void actionPerformed(ActionEvent ev) {
                String s = (String) JOptionPane.showInputDialog(
                        null, "Number of Vertices...",
                        "Customized Dialog",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,//brak opcji do wyboru
                        "");
                try {
                    if (s != null) {
                        int numVertices = ctl.isNumber(s);
                        if (numVertices > 1) {
                            createTable(numVertices);
                            historyModel.addRow(new Object[]{"New graph with " + vertices + " vertices."});
                            JOptionPane.showMessageDialog(null, "It has created a new table");
                        } else {
                            JOptionPane.showMessageDialog(null, "Number of vertices must be greater than 1");
                        }
                    }
                } catch (NumberException ex) {
                    JOptionPane.showMessageDialog(null, "Number of vertices is not a number");
                }
            }
        });
        menu.add(menuItem);
        
        //************************Option Dijkstra*******************************
        menuItem = new JMenuItem("Solve with Dijkstra", KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("");
        menuItem.addActionListener(new ActionListener() {
            /**
             * Action for solve the graph with dijkstra algorithm
             * @params ev it's the event
             */
            public void actionPerformed(ActionEvent ev) {
                //We need to check if there are empty or invalid values
                if (checkValues()) {
                    try {
                        //we need add all edges (u,d,w) into one arraylist
                        String s = (String) JOptionPane.showInputDialog(
                                null, "Select your source...",
                                "Customized Dialog",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,//brak opcji do wyboru
                                "");
                        if(s!=null){
                            int source = ctl.isNumber(s);
                            if (source > -1 && source < vertices) {
                                ArrayList<Edge> graphMatrix = convertToArrayList();
                                Dijkstra newEjecution = new Dijkstra(vertices);
                                newEjecution.dijkstra(source, graphMatrix);
                                historyModel.addRow(new Object[]{"Solving with Dijkstra. Source=" + source + "."});
                                //Delete all rows for the solution model
                                solutionModel.setRowCount(0);
                                //Add new solution for the current graph
                                for (int i = 0; i < newEjecution.getDistances().size(); i++) {
                                    solutionModel.addRow(new Object[]{i, newEjecution.getDistances().get(i), newEjecution.getPath().get(i)});
                                }
                                //System.out.println(newEjecution.getPath());
                                generateGraph(graphMatrix);
                                graphGenerated = true;
                                JOptionPane.showMessageDialog(null, "Dijkstra Algorithm finished");
                            } else {
                                JOptionPane.showMessageDialog(null, "Your source must be -1<value<" + vertices);
                            }
                        }
                    } catch (NumberException ex) {
                        JOptionPane.showMessageDialog(null, "Your source is not a number");
                    }
                }
            }
        });
        menu.add(menuItem);
        
        //**************************Options Bellman-Ford************************
        menuItem = new JMenuItem("Solve with Bellman-Ford", KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
        menuItem.addActionListener(new ActionListener() {
            /**
             * Action for solve the graph with bellman-ford algorithm
             * @param ev it's the event
             */
            public void actionPerformed(ActionEvent ev) {
                //We need to check if there are empty or invalid values
                if (checkValues()) {
                    try {
                        //we need add all edges (u,d,w) into one arraylist
                        String s = (String) JOptionPane.showInputDialog(
                                null, "Select your source...",
                                "Customized Dialog",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,
                                "");
                        if(s!=null){
                            int source = ctl.isNumber(s);
                            if (source > -1 && source < vertices) {
                                ArrayList<Edge> graphMatrix = convertToArrayList();
                                BellmanFord newEjecution = new BellmanFord(vertices);
                                newEjecution.executeBellmanFord(source, graphMatrix);
                                historyModel.addRow(new Object[]{"Solving with Bellman-Ford. Source=" + source + "."});
                                //Delete all rows for the solution model
                                solutionModel.setRowCount(0);
                                //Add new solution for the current graph
                                for (int i = 0; i < newEjecution.getDistances().size(); i++) {
                                    solutionModel.addRow(new Object[]{i, newEjecution.getDistances().get(i), newEjecution.getPath().get(i)});
                                }
                                //System.out.println(newEjecution.getPath());
                                generateGraph(graphMatrix);
                                graphGenerated = true;
                                JOptionPane.showMessageDialog(null, "Bellman-Ford Algorithm finished");
                            } else {
                                JOptionPane.showMessageDialog(null, "Your source must be -1<value<" + vertices);
                            }
                        }
                    } catch (NumberException ex) {
                        JOptionPane.showMessageDialog(null, "Your source is not a number");
                    }
                }
            }
        });
        menu.add(menuItem);
        
        //----------------------------Other options-----------------------------
        menu = new JMenu("Others");
        //****************************Show graph********************************
        menuItem = new JMenuItem("Show Graph", KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menuItem.addActionListener(new ActionListener() {
            /**
             * Action for show a graph image
             * @param ev it's an event
             */
            public void actionPerformed(ActionEvent ev) {
                if (graphGenerated == true) {
                    try {
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "graph.jpg");
                        historyModel.addRow(new Object[]{"Showing graph."});
                        //System.out.println("Final");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Graph has not solved yet");
                }
            }
        });
        menu.add(menuItem);
        menuBar.add(menu);

        return menuBar;
    }

    /**
     * this method will create a table
     * @param s it receives a number of vertices
     */
    public void createTable(int s) {
        //remove current jscrollpane
        tabbedPane.remove(js);
        vertices = s;
        //we add new name of columns
        columnNames = new String[vertices];
        for (int i = 0; i < vertices; i++) {
            columnNames[i] = Integer.toString(i);
        }
        //create new data object
        Object[][] data = new Object[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                data[i][j] = new String("");
            }
        }
        //create new graph table
        graphTable = new JTable(new GraphModel(columnNames, data));
        JTable rowTable = new RowNumberTable(graphTable);
        js = new JScrollPane(graphTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        graphTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        js.setRowHeaderView(rowTable);
        js.setCorner(JScrollPane.UPPER_LEFT_CORNER,
                rowTable.getTableHeader());
        tabbedPane.add(js, "Graph Values", 0);
        tabbedPane.setSelectedIndex(0);
    }

    /**
     * this function will check if all values are correct
     * @return return true if every values are ok
     */
    private boolean checkValues() {
        for (int i = 0; i < graphTable.getRowCount(); i++) {
            for (int j = 0; j < graphTable.getColumnCount(); j++) {
                try {
                    int value = ctl.isNumber(graphTable.getValueAt(i, j).toString());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Invalid Value in position [" + i + "][" + j + "]");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * this function will create an arraylist from jtable
     * @return it returns an arraylist with all edges from jtable
     */
    private ArrayList<Edge> convertToArrayList() {
        //add new edges to return
        ArrayList<Edge> returnedArrayList = new ArrayList<>();
        for (int i = 0; i < graphTable.getRowCount(); i++) {
            for (int j = 0; j < graphTable.getColumnCount(); j++) {
                try {
                    Edge newEdge = new Edge(i, j, ctl.isNumber(graphTable.getValueAt(i, j).toString()));
                    returnedArrayList.add(newEdge);
                } catch (NumberException ex) {
                    Logger.getLogger(GuiApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return returnedArrayList;
    }

    /**
     * This method creates an image of the graph
     * @param graphMatrix have all edges of the graph
     */
    public void generateGraph(ArrayList<Edge> graphMatrix) {
        ArrayList<Edge> connections = new ArrayList<Edge>();
        ArrayList<Integer> nodes = new ArrayList<Integer>();
        //add new different edges without repetitions: (u,v)==(v,u) are same in different positions
        for (Edge actual : graphMatrix) {
            if (connections.isEmpty()) {
                connections.add(actual);
                continue;
            }
            boolean existEdge = false;
            for (Edge rev : connections) {
                if (((rev.getuVertex() == actual.getuVertex() && rev.getvVertex() == actual.getvVertex()) || (rev.getuVertex() == actual.getvVertex() && rev.getvVertex() == actual.getuVertex()))) {
                    existEdge = true;
                    break;
                }
            }
            if (existEdge == false) {
                connections.add(actual);
            }
        }
        //add different vertices to list
        for (Edge actual : graphMatrix) {
            if (nodes.isEmpty()) {
                nodes.add(actual.getuVertex());
                nodes.add(actual.getvVertex());
                continue;
            }
            boolean existUvertice = false;
            for (int n : nodes) {
                if (n == actual.getuVertex()) {
                    existUvertice = true;
                    break;
                }
            }
            if (existUvertice == false) {
                nodes.add(actual.getuVertex());
            }
            boolean existVvertice = false;
            for (int n : nodes) {
                if (n == actual.getvVertex()) {
                    existVvertice = true;
                    break;
                }
            }
            if (existVvertice == false) {
                nodes.add(actual.getvVertex());
            }
        }
        //create a dot file to generate our image with graphviz
        String dotString = "digraph G{\n";
        for (int actual : nodes) {
            dotString += "Node" + actual + "[label=\"" + actual + "\"];\n";
        }
        for (Edge e : connections) {
            if (e.getuVertex() != e.getvVertex() && e.getWeigth() != 0) {
                dotString += "Node" + e.getuVertex() + "->Node" + e.getvVertex() + "[label=\"" + e.getWeigth() + "\"];\n";
            }
        }
        dotString += "}";
        FileWriter newFile = null;
        PrintWriter pw = null;
        //create dot file
        try {
            String path = "graph" + ".dot";
            newFile = new FileWriter(path);
            pw = new PrintWriter(newFile);
            pw.println(dotString);
        } catch (Exception e) {
        } finally {
            try {
                if (null != newFile) {
                    newFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //generate our image using graphviz and dot file
        try {
            String dotPath = "C:\\Program Files\\Graphviz\\bin\\dot.exe";
            String fileInputPath = "graph.dot";
            String fileOutputPath = "graph.jpg";
            String tParam = "-Tjpg";
            String tOParam = "-o";
            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();

            rt.exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Default method
     * @param e reppresents the action event of the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Default method
     * @param e represents the item event of changes
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
