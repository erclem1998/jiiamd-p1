/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.lab.erick.lemus.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.lab.erick.lemus.algorithm.BellmanFord;
import pl.polsl.lab.erick.lemus.controller.Controller;
import pl.polsl.lab.erick.lemus.exceptions.NumberException;
import pl.polsl.lab.erick.lemus.model.Edge;
import pl.polsl.lab.erick.lemus.model.Graph;
import pl.polsl.lab.erick.lemus.model.History;
import pl.polsl.lab.erick.lemus.view.View;

/**
 * This class allows to type weight en every edges and solve using Bellman-Ford
 *
 * @author erick
 * @version 1.0.0
 */
@WebServlet("/SolveBellmanFord")
public class SolveBellmanFord extends HttpServlet {

    /**
     * It is our database connection
     */
    private Connection db;

    /**
     * This function gets the database connection from the servlet context
     *
     * @throws ServletException we need to handel the exceptions for Servlets
     */
    @Override
    public void init() throws ServletException {
        db = (Connection) getServletContext().getAttribute("dbConnection");
    }

    /**
     * this method allow to call a Post method to do all logic to execute
     * Bellman-Ford
     *
     * @param request it's the all entrance data
     * @param response it't the response of our servlet
     * @throws ServletException exception if there is an error during the
     * execution
     * @throws IOException exception of i/o
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
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
        ServletContext sc = getServletContext();
        int numVertices = Integer.parseInt((String) sc.getAttribute("numVer"));
        String source = request.getParameter("Source");
        //System.out.println("Source = "+source);
        try {
            ArrayList<Edge> returnedArrayList = new ArrayList<>();
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    String actual = request.getParameter("cell" + Integer.toString(i) + "-" + Integer.toString(j));
                    //System.out.println("["+i+","+j+"] = "+actual);
                    Edge newEdge = new Edge(i, j, controller.isNumber(actual));
                    returnedArrayList.add(newEdge);
                }
            }
            try {
                int sourceNumber = controller.isNumber(source);

                BellmanFord newEjecution = new BellmanFord(numVertices);
                boolean isNotValid = newEjecution.executeBellmanFord(sourceNumber, returnedArrayList);
                if (isNotValid) {
                    String htmlNegativeCycle = "<!DOCTYPE html>\n"
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
                            + "                <ul class=\"navbar-nav\">\n"
                            + "                    <li class=\"nav-item\">\n"
                            + "                        <form action=\"https://google.com\">\n"
                            + "                            <button type=\"submit\" class=\"btn btn-primary\">Cookies</button>\n"
                            + "                        </form>\n"
                            + "                    </li>\n"
                            + "                    <li class=\"nav-item\">\n"
                            + "                        <form action=\"https://google.com\">\n"
                            + "                            <button type=\"submit\" class=\"btn btn-primary\">History</button>\n"
                            + "                        </form>\n"
                            + "                    </li>\n"
                            + "                </ul>\n"
                            + "            </div>"
                            + "\n"
                            + "            <div class=\"collapse navbar-collapse\" id=\"navbarColor01\">\n"
                            + "            </div>\n"
                            + "        </nav>\n"
                            + "        <div class=\"container-fluid\">\n"
                            + "            <div class=\"row justify-content-center\">\n"
                            + "                <br>\n"
                            + "                <h1> Your graph has a negative cycle </h1>\n"
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
                            + "</html>";
                    out.println(htmlNegativeCycle);
                } else {
                    System.out.println(newEjecution.getPath());
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
                            + "        </nav>\n<br>"
                            + "        <div class=\"container-fluid\">\n"
                            + "            <div class=\"row justify-content-center\">\n"
                            + "                <div class=\"col-md-8 tab\">\n"
                            + "                    <ul class=\"nav nav-tabs justify-content-center\">\n"
                            + "                        <li class=\"nav-item active\">\n"
                            + "                            <a class=\"nav-link active\" data-toggle=\"tab\" href=\"#simbolos\">Solution</a>\n"
                            + "                        </li>\n"
                            + "                    </ul>\n"
                            + "                    <div id=\"myTabContent\" class=\"tab-content\">\n"
                            + "                        <div class=\"tab-pane fade in active show\" id=\"simbolos\">\n"
                            + "                            <div class=\"col-md-12\">\n"
                            + "                                <br>\n"
                            + "                                <div style=\"width: auto; height: auto;\">\n"
                            + "                                    <table class=\"table table-success table-hover\">\n"
                            + "                                        <thead>\n"
                            + "                                            <tr>\n"
                            + "                                                <td>Vertice</td>\n"
                            + "                                                <td>Distance</td>\n"
                            + "                                                <td>Path</td>\n"
                            + "                                            </tr>\n"
                            + "                                        </thead>\n";
                    htmlPage += "<tbody>";
                    for (int i = 0; i < newEjecution.getPath().size(); i++) {
                        htmlPage += "<tr>"
                                + "<td>" + i + "</td>" + "<td>" + newEjecution.getDistances().get(i) + "</td>" + "<td>" + newEjecution.getPath().get(i) + "</td>"
                                + "</tr>";
                    }
                    htmlPage += "</tbody>";
                    htmlPage += "                                    </table>\n"
                            + "                                </div>\n"
                            + "                                <br>\n"
                            + "                            </div>\n"
                            + "                        </div>\n"
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
                    Cookie[] cookies = request.getCookies();
                    int numOfSolvesBellman = 1;
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals("numOfSolvesBellman")) {
                                numOfSolvesBellman = Integer.parseInt(cookie.getValue());
                                numOfSolvesBellman += 1;
                                cookie.setValue(Integer.toString(numOfSolvesBellman));
                                break;
                            }
                        }
                    }

                    Cookie cookie = new Cookie("numOfSolvesBellman", Integer.toString(numOfSolvesBellman));
                    response.addCookie(cookie);
                    //HttpSession session = request.getSession();
                    try {
                        History h = new History(0, "Graph has been solved with Bellman/Ford Algorithm at " + new java.util.Date().toString());
                        h.addHistory(db);
                    } catch (SQLException ex) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('" + ex.getMessage() + "');");
                        out.println("</script>");
                    }
                    /*ArrayList<String> history = (ArrayList<String>) session.getAttribute("history");
                    if (history != null) {
                        history.add("Graph has been solved with Bellman/Ford Algorithm at " + new java.util.Date().toString());
                    } else {
                        history = new ArrayList<String>();
                        history.add("Graph has been solved with Bellman/Ford Algorithm at " + new java.util.Date().toString());
                    }
                    session.removeAttribute("history");
                    session.setAttribute("history", history);*/
                    out.println(htmlPage);
                }

            } catch (Exception e) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Source is not valid');");
                out.println("</script>");
            }

        } catch (NumberException ex) {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('There are some invalid weights');");
            out.println("</script>");
        }

        //out.println(htmlPage);
    }

}
