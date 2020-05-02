package com.restaurant.view;

import com.restaurant.bll.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WaiterGUI extends JFrame {
    public WaiterGUI(){
        this.setTitle(this.getClass().getSimpleName());
        this.setContentPane(this.waiter);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        String header[] = new String[]{"ID", "Name", "Quantity"};
        MenuItemTableModel modelMenu = new MenuItemTableModel(Restaurant.getInstance().getItems(),false, false);
        Restaurant.getInstance().addObserver(modelMenu);
        DefaultTableModel model = new DefaultTableModel(header, 0);
        menuTable.setModel(modelMenu);
        menuTable.getTableHeader().setReorderingAllowed(false);
        activeOrdersTable.setModel(model);
        activeOrdersTable.getTableHeader().setReorderingAllowed(false);
        currentOrderTable.setModel(model);
        currentOrderTable.getTableHeader().setReorderingAllowed(false);

        String[] data = {"asd", "asd", "asd"};
        model.addRow(data);
    }

    private JPanel waiter;
    private JTable menuTable;
    private JTable currentOrderTable;
    private JButton addItemButton;
    private JButton removeItemButton;
    private JTable activeOrdersTable;
    private JButton makeBillButton;
    private JButton makeOrderButton;
}
