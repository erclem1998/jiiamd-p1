/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.lab.erick.lemus.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.lab.erick.lemus.model.History;

/**
 * This class contains necesary methods to show the history in the application
 *
 * @author erick
 * @version 1.0.0
 */
@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {
    
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
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request it's the all entrance data
     * @param response it't the response of our servlet
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Create an html file
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
                    + "                            <a class=\"nav-link active\" data-toggle=\"tab\" href=\"#simbolos\">History</a>\n"
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
                    + "                                                <td>Message</td>\n"
                    + "                                            </tr>\n"
                    + "                                        </thead>\n";
            //create table and add data from session storage
            htmlPage += "<tbody>";
            
            try {
                History hist = new History(0,"");
                for(History h: hist.getHistoryValue(db)){
                    htmlPage += "<tr>"
                            + "<td>" + h.getActionAdded() + "</td>"
                            + "</tr>";
                }
            } catch (SQLException sqle) {
                System.err.println(sqle.getMessage());
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
            //show html page
            out.println(htmlPage);
        } catch (Exception e) {
            //show an error
            out.println("<script type=\"text/javascript\">");
            out.println("alert('An error has been ocurred');");
            out.println("</script>");
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
