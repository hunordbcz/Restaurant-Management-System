package com.restaurant.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChefGUI extends JFrame {
    public ChefGUI(){
        this.setTitle(this.getClass().getSimpleName());
        this.setContentPane(this.chef);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        String header[] = new String[]{"ID", "Name", "Quantity"};
        model = new DefaultTableModel(header, 0);
        ordersTable.setModel(model);
        ordersTable.getTableHeader().setReorderingAllowed(false);
    }

    private JPanel chef;
    private JTable ordersTable;
    private JButton markAsDoneButton;
    private DefaultTableModel model;

    private void createUIComponents() {
        ordersTable = new JTable(){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
    }
}
