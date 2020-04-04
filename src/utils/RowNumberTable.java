package utils;

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/*
 *	Use a JTable as a renderer for row numbers of a given main table.
 *  This table must be added to the row header of the scrollpane that
 *  contains the main table.
 */
public class RowNumberTable extends JTable
        implements ChangeListener, PropertyChangeListener, TableModelListener
{
    private JTable main;
    private Object[] indexes;

    public RowNumberTable(JTable table, Object[] indexes) {
        this.indexes = indexes;
        main = table;
        main.addPropertyChangeListener(this);
        main.getModel().addTableModelListener(this);

        setFocusable(false);
        setAutoCreateColumnsFromModel(false);
        setSelectionModel(main.getSelectionModel());

        TableColumn column = new TableColumn();
        column.setHeaderValue(" ");
        addColumn(column);
        column.setCellRenderer(new RowNumberRenderer());

        getColumnModel().getColumn(0).setPreferredWidth(50);
        setPreferredScrollableViewportSize(getPreferredSize());
    }

    @Override
    public void addNotify() {
        super.addNotify();

        Component c = getParent();

        if (c instanceof JViewport) {
            JViewport viewport = (JViewport)c;
            viewport.addChangeListener( this );
        }
    }

    @Override
    public int getRowCount() {
        return main.getRowCount();
    }

    @Override
    public int getRowHeight(int row) {
        int rowHeight = main.getRowHeight(row);

        if (rowHeight != super.getRowHeight(row))
            super.setRowHeight(row, rowHeight);
        return rowHeight;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return indexes[row].toString();//Integer.toString(row + 1);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {}

    public void stateChanged(ChangeEvent e) {
        JViewport viewport = (JViewport) e.getSource();
        JScrollPane scrollPane = (JScrollPane)viewport.getParent();
        scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
    }

    public void propertyChange(PropertyChangeEvent e) {
        if ("selectionModel".equals(e.getPropertyName()))
            setSelectionModel( main.getSelectionModel() );

        if ("rowHeight".equals(e.getPropertyName()))
            repaint();

        if ("model".equals(e.getPropertyName())) {
            main.getModel().addTableModelListener( this );
            revalidate();
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        revalidate();
    }

    private static class RowNumberRenderer extends DefaultTableCellRenderer
    {
        public RowNumberRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (table != null) {
                JTableHeader header = table.getTableHeader();

                if (header != null) {
                    setForeground(header.getForeground());
                    setBackground(header.getBackground());
                    setFont(header.getFont());
                }
            }

            if (isSelected)
                setFont( getFont().deriveFont(Font.BOLD) );

            setText((value == null) ? "" : value.toString());
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));

            return this;
        }
    }
}
