/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.erick.lemus.model;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 *  Use a JTable as a renderer for row numbers of a given main table.
 *  This table must be added to the row header of the scrollpane that
 *  contains the main table.
 * @author erick
 * @version 1.0
 */
public class RowNumberTable extends JTable implements ChangeListener, PropertyChangeListener, TableModelListener
{
        /**
         * our table
         */
	private JTable main;
        
        /**
         * constructor of the class
         * @param table is the current jtable
         */
	public RowNumberTable(JTable table)
	{
		main = table;
		main.addPropertyChangeListener( this );
		main.getModel().addTableModelListener( this );

		setFocusable( false );
		setAutoCreateColumnsFromModel( false );
		setSelectionModel( main.getSelectionModel() );


		TableColumn column = new TableColumn();
		column.setHeaderValue(" ");
		addColumn( column );
		column.setCellRenderer(new RowNumberRenderer());

		getColumnModel().getColumn(0).setPreferredWidth(50);
		setPreferredScrollableViewportSize(getPreferredSize());
	}
        
        /**
         * notify any things
         */
	@Override
	public void addNotify()
	{
		super.addNotify();

		Component c = getParent();

		//  Keep scrolling of the row table in sync with the main table.

		if (c instanceof JViewport)
		{
			JViewport viewport = (JViewport)c;
			viewport.addChangeListener( this );
		}
	}

	/*
	 *  Delegate method to main table
	 */
	@Override
	public int getRowCount()
	{
		return main.getRowCount();
	}
        
        /**
         * get the height of the row
         * @param row current row
         * @return the height of the row
         */
	@Override
	public int getRowHeight(int row)
	{
		int rowHeight = main.getRowHeight(row);

		if (rowHeight != super.getRowHeight(row))
		{
			super.setRowHeight(row, rowHeight);
		}

		return rowHeight;
	}

	/*
	 *  No model is being used for this table so just use the row number
	 *  as the value of the cell.
	 */
	@Override
	public Object getValueAt(int row, int column)
	{
		return Integer.toString(row);
		//return Integer.toString(row + 1);
	}

	/*
	 *  Don't edit data in the main TableModel by mistake
	 */
	@Override
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}

	/*
	 *  Do nothing since the table ignores the model
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {}
        
        /**
         * Implement the ChangeListener
         * @param e is de change event
         */
	public void stateChanged(ChangeEvent e)
	{
		//  Keep the scrolling of the row table in sync with main table

		JViewport viewport = (JViewport) e.getSource();
		JScrollPane scrollPane = (JScrollPane)viewport.getParent();
		scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
	}
        
        /**
         * Implement the PropertyChangeListener
         * @param e is the property event change
         */
	public void propertyChange(PropertyChangeEvent e)
	{
		//  Keep the row table in sync with the main table

		if ("selectionModel".equals(e.getPropertyName()))
		{
			setSelectionModel( main.getSelectionModel() );
		}

		if ("rowHeight".equals(e.getPropertyName()))
		{
			repaint();
		}

		if ("model".equals(e.getPropertyName()))
		{
			main.getModel().addTableModelListener( this );
			revalidate();
		}
	}
        
        /**
         * Implement the TableModelListener
         * @param e is the table model event
         */
	@Override
	public void tableChanged(TableModelEvent e)
	{
		revalidate();
	}

	/**
         * Attempt to mimic the table header renderer
         */
	private static class RowNumberRenderer extends DefaultTableCellRenderer
	{
                
            /**
             * It's the constructor of the class
             */
		public RowNumberRenderer()
		{
			setHorizontalAlignment(JLabel.CENTER);
		}
                
                /**
                 * Function to get and render the cell component
                 * @param table is the table
                 * @param value current value
                 * @param isSelected if is selected
                 * @param hasFocus if it has finished edit
                 * @param row current row
                 * @param column current column
                 * @return return the jtable
                 */
		public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			if (table != null)
			{
				JTableHeader header = table.getTableHeader();

				if (header != null)
				{
					setForeground(header.getForeground());
					setBackground(header.getBackground());
					setFont(header.getFont());
				}
			}

			if (isSelected)
			{
				setFont( getFont().deriveFont(Font.BOLD) );
			}

			setText((value == null) ? "" : value.toString());
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));

			return this;
		}
	}
}
