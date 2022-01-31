/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.erick.lemus.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class represents a data from the history in the database
 *
 * @author erick
 * @version 1.0.0
 */
public class History {

    /**
     * id of the action in the history
     */
    private int id;
    /**
     * It's the action that we have done in our application
     */
    private String actionAdded;

    /**
     * Constructor of the class
     *
     * @param id is a number
     * @param actionAdded is the action that we have done
     */
    public History(int id, String actionAdded) {
        this.id = id;
        this.actionAdded = actionAdded;
    }

    /**
     * This function returns the id
     *
     * @return a numeric value that represents the id
     */
    public int getId() {
        return id;
    }

    /**
     * Return the message
     *
     * @return a string message in the history
     */
    public String getActionAdded() {
        return actionAdded;
    }

    /**
     * Save thenew id
     *
     * @param id is numeric value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Save the message
     *
     * @param actionAdded is a string value
     */
    public void setActionAdded(String actionAdded) {
        this.actionAdded = actionAdded;
    }

    /**
     * This method gets the connection to db to add new value to the history
     *
     * @param db it's the database connection
     * @throws SQLException it's an exception in the database connection
     */
    public void addHistory(Connection db) throws SQLException {
        Statement statement;
        statement = db.createStatement();
        statement.executeUpdate("INSERT INTO HISTORY (ACTIONADDED) VALUES ('" + this.getActionAdded() + "')");
    }
    
    /**
     * This function returns all values from database
     * @param db it's the database connection
     * @return returns an arraylist with all elements from the history table in the database
     * @throws SQLException it's an exception in the database connection
     */
    public ArrayList<History> getHistoryValue(Connection db) throws SQLException {
        Statement statement = db.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM HISTORY");
        ArrayList<History> historyList = new ArrayList<History>();
        while (rs.next()) {
            History h = new History(rs.getInt("ID"), rs.getString("ACTIONADDED"));
            historyList.add(h);
        }
        rs.close();
        return historyList;
    }

}
