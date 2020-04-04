package utils;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.Color;
import java.awt.Dimension;
import java.util.*;

public class DataFrameViewer extends JTable {
    private Object[] indexes, colNames;

    public DataFrameViewer(Object[][] data, Object[] colNames, Object[] indexes) {
        super(data, new ArrayList<Object>(Arrays.asList(colNames)).subList(1, colNames.length).toArray());

        // table initialization
        this.indexes = indexes;
        this.colNames = colNames;
        setRowHeight(30);
        setIntercellSpacing(new Dimension(10, 10));
        setGridColor(Color.blue);
        setForeground(Color.black);
        setSelectionForeground(Color.yellow);
        setSelectionBackground(Color.blue);
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column + 1].toString();
    }

    @Override
    public ListSelectionModel getSelectionModel() {
        return super.getSelectionModel();
    }

    public void draw() {
        // Creating frame
        Box contents = new Box(BoxLayout.Y_AXIS);
        TableColumn a = new TableColumn();
        JTable jRowTable = new RowNumberTable(this, indexes);
        JScrollPane scroll = new JScrollPane(this);
        scroll.setRowHeaderView(jRowTable);
        scroll.setCorner(scroll.UPPER_LEFT_CORNER,jRowTable.getTableHeader());
        scroll.setSize(300,300);
        contents.add(scroll);

        // Showing window
        JFrame f = new JFrame("Data Viewer");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(contents);
        f.setSize(700, 500);
        f.setVisible(true);
        f.setAlwaysOnTop(true);
    }
}
