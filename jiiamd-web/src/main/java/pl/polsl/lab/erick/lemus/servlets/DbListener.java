/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/ServletListener.java to edit this template
 */
package pl.polsl.lab.erick.lemus.servlets;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 * Persistence.createEntityManagerFactory("WebJPA").createEntityManager()
 *
 * @author erick
 * @version 1.0.0
 */
@WebListener
public class DbListener implements ServletContextListener {
    
    /**
     * When the application starts, this function save in our context the database connection
     * @param sce is the servletcontext event
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // loading the JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
            return;
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/jiiamd2;create=true;", "jiiamd", "jiiamd");
            Statement statement = con.createStatement();
            DatabaseMetaData databaseMetadata = con.getMetaData();
            ResultSet resultSet = databaseMetadata.getTables(null, null, "HISTORY", null);
            if (!resultSet.next()) {
                statement.executeUpdate("CREATE TABLE HISTORY (\n"
                    + "ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), \n"
                    + "ACTIONADDED VARCHAR(500) NOT NULL\n"
                    + ")");
            }
            sce.getServletContext().setAttribute("dbConnection", con);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
    
    /**
     * Destroys any objects in the context
     * @param sce is the current servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
