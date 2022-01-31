/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.lab.erick.lemus.servlets;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import pl.polsl.lab.erick.lemus.controller.Controller;
import pl.polsl.lab.erick.lemus.exceptions.NumberException;
import pl.polsl.lab.erick.lemus.model.Edge;
import pl.polsl.lab.erick.lemus.model.Graph;
import pl.polsl.lab.erick.lemus.view.View;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import pl.polsl.lab.erick.lemus.model.History;
import sun.tools.jconsole.ConnectDialog;

/**
 * This class allows to select type of algorithm to use and create a table with
 * all vertices
 *
 * @author erick
 * @version 1.0.0
 */
@WebServlet("/MoveToDijkstra")
public class MoveToDijkstra extends HttpServlet {

    /**
     * It is our database connection
     */
    private Connection db;
    
    /**
     * This function gets the database connection from the servlet context
     * @throws ServletException we need to handel the exceptions for Servlets
     */
    @Override
    public void init() throws ServletException {
        db = (Connection) getServletContext().getAttribute("dbConnection");
    }

    /**
     * this method allow to call a Get method to create and move to the next
     * view
     *
     * @param request it's the all entrance data
     * @param response it't the response of our servlet
     * @throws ServletException exception if there is an error during the
     * execution
     * @throws IOException exception of i/o
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Using default args
        int vertexNumber = 0;
        ArrayList<Edge> defArray = null;
        // Model of the Graph
        Graph model = new Graph(vertexNumber, defArray);
        // View of the application
        View view = new View();
        //GuiApp guiapp = new GuiApp(new Controller(null,null,null));
        // Controller contains all logic of the application 
        Controller controller = new Controller(model, view);
        // Execute de application
        //controller.execute(true);

        response.setContentType("text/html; charset=ISO-8859-2");
        PrintWriter out = response.getWriter();

        // Get parameter values - firstName i lastName
        String nVertices = request.getParameter("NumberVertices");
        String algorithm = request.getParameter("optionsRadios");
        try {
            int nVer = controller.isNumber(nVertices);
            if (nVer <= 0 || nVer > 10) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Number of vertices must be greater than 0 and smaller than 10');");
                out.println("location='index.html';");
                out.println("</script>");
            } else {
                System.out.println("nV: " + nVertices);
                System.out.println("al: " + algorithm);
                String solveWith = "";
                if (algorithm.equals("option1")) {
                    //Dijkstra
                    solveWith = "SolveDijkstra";
                } else {
                    //Bellman Ford
                    solveWith = "SolveBellmanFord";
                }
                String htmlPage = "<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "    <head>\n"
                        + "        <meta charset=\"utf-8\">\n"
                        + "        <title>Dijkstra-Bellman/Ford</title>\n"
                        + "        <base href=\"/\">\n"
                        + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                        + "        <link rel=\"icon\" type=\"image/x-icon\" href=\"favicon.ico\">\n"
                        + "        <link rel=\"stylesheet\" href=\"https://bootswatch.com/4/superhero/bootstrap.min.css\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <nav class=\"navbar navbar-expand-lg navbar-dark bg-primary\">\n"
                        + "            <a class=\"navbar-brand\" href=\"/jiiamd-web\">Dijkstra-Bellman/Ford</a>\n"
                        + "            <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarColor01\"\n"
                        + "                    aria-controls=\"navbarColor01\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n"
                        + "                <span class=\"navbar-toggler-icon\"></span>\n"
                        + "            </button>\n"
                        + "<div class=\"collapse navbar-collapse\" id=\"navbarNav\">\n"
                        + "<ul class=\"navbar-nav\">\n"
                        + "                    <li class=\"nav-item\">\n"
                        + "                        <form action=\"jiiamd-web/CookiesServlet\">\n"
                        + "                            <button type=\"submit\" class=\"btn btn-primary\">Cookies</button>\n"
                        + "                        </form>\n"
                        + "                    </li>\n"
                        + "                    <li class=\"nav-item\">\n"
                        + "                        <form action=\"jiiamd-web/HistoryServlet\">\n"
                        + "                            <button type=\"submit\" class=\"btn btn-primary\">History</button>\n"
                        + "                        </form>\n"
                        + "                    </li>\n"
                        + "                </ul>"
                        + "            </div>"
                        + "\n"
                        + "            <div class=\"collapse navbar-collapse\" id=\"navbarColor01\">\n"
                        + "            </div>\n"
                        + "        </nav>\n"
                        + "        <div class=\"container-fluid\">\n"
                        + "            <div class=\"row row-content justify-content-around\">\n"
                        + "                <div class=\"col-md-12\">\n"
                        + "                    <br>\n"
                        + "                    <div class=\"row justify-content-around\">\n"
                        + "                        <form method=\"POST\" action=\"jiiamd-web/" + solveWith + "\">\n"
                        + "                            <fieldset>\n"
                        + "                                <legend>" + solveWith + "</legend>\n"
                        + "                                <div class=\"form-group\">\n"
                        + "                                    \n";
                String table = "<table name=\"MyTable\" id=\"MyTable\"><thead><tr><th>#</th>";
                for (int i = 0; i < nVer; i++) {
                    table += "<th>" + Integer.toString(i) + "</th>";
                }
                table += "</tr></thead><tbody>";
                for (int i = 0; i < nVer; i++) {
                    table += "<tr><td>" + Integer.toString(i) + "</td>";
                    for (int j = 0; j < nVer; j++) {
                        table += "<td><input type=\"number\" id=\"cell" + Integer.toString(i) + "-" + Integer.toString(j) + "\""
                                + "name=\"cell" + Integer.toString(i) + "-" + Integer.toString(j) + "\" value=\"0\"></td>";
                    }
                    table += "</tr>";
                }
                table += "</tbody></table>";
                htmlPage += table;
                htmlPage
                        += "                                </div>\n<div class=\"form-group\">\n"
                        + "                                    <div class=\"input-group mb-3\">\n"
                        + "                                        <span class=\"input-group-text\" id=\"basic-addon1\">Source</span>\n"
                        + "                                        <input type=\"number\" class=\"col-sm-6 form-control\" placeholder=\"Source\"\n"
                        + "                                               aria-label=\"Source\" aria-describedby=\"basic-addon1\" name=\"Source\" value='0'>\n"
                        + "                                    </div>\n"
                        + "                                </div>"
                        + "                                <input type=\"submit\" class=\"btn btn-success\" value=\"Solve With " + solveWith + "\" />\n"
                        + "                            </fieldset>\n"
                        + "                        </form>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "            </div>\n"
                        + "            <br>\n"
                        + "        </div>\n"
                        + "\n"
                        + "        <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"\n"
                        + "                integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\"\n"
                        + "        crossorigin=\"anonymous\"></script>\n"
                        + "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\"\n"
                        + "                integrity=\"sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49\"\n"
                        + "        crossorigin=\"anonymous\"></script>\n"
                        + "        <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\"\n"
                        + "                integrity=\"sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy\"\n"
                        + "        crossorigin=\"anonymous\"></script>\n"
                        + "        <script src=\"https://partner.googleadservices.com/gampad/cookie.js?domain=bestjquery.com&callback=_gfp_s_&client=ca-pub-3311815518700050&cookie=ID%3D68e70b235f70e2a5-227f003d5fb900d5%3AT%3D1616635537%3ART%3D1616635537%3AS%3DALNI_MY1majeWKQO28vrHA0sXJlB0qEImQ\"></script><script src=\"https://pagead2.googlesyndication.com/pagead/js/r20210322/r20190131/show_ads_impl.js\" id=\"google_shimpl\"></script><script type=\"text/javascript\" src=\"https://code.jquery.com/jquery-1.12.0.min.js\"></script>\n"
                        + "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>\n"
                        + "    </body>\n"
                        + "</html>\n"
                        + "";
                ServletContext servletcontext = getServletContext();
                servletcontext.setAttribute("numVer", nVertices);
                //System.out.println(htmlPage);
                Cookie[] cookies = request.getCookies();
                int solvingPage = 1;
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("solvingPage")) {
                            solvingPage = Integer.parseInt(cookie.getValue());
                            solvingPage += 1;
                            cookie.setValue(Integer.toString(solvingPage));
                            break;
                        }
                    }
                }

                Cookie cookie = new Cookie("solvingPage", Integer.toString(solvingPage));
                response.addCookie(cookie);
                //HttpSession session = request.getSession();
                try {
                    History h = new History(0, "New graph with " + nVertices + " vertices at " + new java.util.Date().toString());
                    h.addHistory(db);
                } catch (SQLException ex) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('"+ex.getMessage()+"');");
                    out.println("</script>");
                }
                /*ArrayList<String> history = (ArrayList<String>) session.getAttribute("history");
                if (history != null) {
                    history.add("New graph with " + nVertices + " vertices.");
                } else {
                    history = new ArrayList<String>();
                    history.add("New graph with " + nVertices + " vertices.");
                }
                session.removeAttribute("history");
                session.setAttribute("history", history);*/
                out.println(htmlPage);
            }
        } catch (NumberException ex) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Number of vertices is not a number');");
            out.println("location='index.html';");
            out.println("</script>");
        }
    }

}
