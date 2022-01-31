/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.erick.lemus.model;

import javax.swing.table.AbstractTableModel;

/**
 * model of the graph for jtable
 * @author erick
 * @version 1.0
 */
public class GraphModel extends AbstractTableModel {

    private String[] columnNames;
    
    /**
     * data included in the table
     */
    private Object[][] data;
    
    Class[] types;

    public GraphModel(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    /**
     * get number oof columns
     * @return number of columns in the table
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * get number of rows
     * @return number of rows in the table
     */
    public int getRowCount() {
        return data.length;
    }

    /**
     * @return column title with the number <code> col </code>
     * @param col column no
     */
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * @return the content of the selected cell in the table
     * @param row row number of the selected cell
     * @param col Column number of the selected cell
     */
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /**
     * method used to specify the default editing and presentation for
     * each cell if the last column had not been implemented
     * would contain text ("true" / "false") instead of checkboxes
     *
     * @return class of the selected column
     * @param c the number of the selected column
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /**
     * Say if cell is editable
     * @param row number of the table
     * @param col number of the table
     */
    public boolean isCellEditable(int row, int col) {
        
        return true;
    }

    /**
     * method implemented when table data can change
     *
     * @param value actual value
     * @param row number of the table
     * @param col number of the table
     */
    public void setValueAt(Object value, int row, int col) {

        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

}
