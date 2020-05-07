package com.restaurant.view;

import com.restaurant.bll.Order;
import com.restaurant.bll.Restaurant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Observable;
import java.util.Observer;


public class ChefGUI extends JFrame implements Observer {
    private ActiveOrdersTableModel activeOrdersModel;
    private OrderItemsTableModel orderItemsTableModel;
    private ListSelectionModel activeOrderSelection;
    private Order lastOrder;

    public ChefGUI(){
        this.setTitle(this.getClass().getSimpleName());
        this.setContentPane(this.chef);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        activeOrdersModel = new ActiveOrdersTableModel(Restaurant.getInstance().getOrders());
        ordersTable.setModel(activeOrdersModel);
        activeOrderSelection = ordersTable.getSelectionModel();

        activeOrderSelection.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int row = ordersTable.getSelectedRow();
                if(0 <= row && row < Restaurant.getInstance().getOrders().size()){
                    lastOrder = activeOrdersModel.getItem(ordersTable.getSelectedRow());
                    orderItemsTableModel = new OrderItemsTableModel(lastOrder);
                    orderItemsTable.setModel(orderItemsTableModel);
                }
            }
        });
    }

    private JPanel chef;
    private JTable orderItemsTable;
    private JTable ordersTable;
    private DefaultTableModel model;

    @Override
    public void update(Observable o, Object arg) {
        activeOrdersModel = new ActiveOrdersTableModel(Restaurant.getInstance().getOrders());
        ordersTable.setModel(activeOrdersModel);

        orderItemsTableModel = new OrderItemsTableModel(lastOrder);
        orderItemsTable.setModel(orderItemsTableModel);
    }
}
