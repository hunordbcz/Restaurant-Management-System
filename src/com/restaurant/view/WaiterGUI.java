package com.restaurant.view;

import com.restaurant.bll.Restaurant;
import com.restaurant.dao.FileReader;
import com.restaurant.dao.FileWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WaiterGUI extends JFrame {
    private JPanel waiter;
    private JTable menuItemTable;
    private JTable currentOrderTable;
    private JTable activeOrdersTable;
    private JButton addItemButton;
    private JButton removeItemButton;
    private JButton makeBillButton;
    private JButton makeOrderButton;

    private MenuItemTableModel menuItemTableModel;
    private OrderTableModel currentOrderModel;

    ListSelectionModel menuSelection;
    ListSelectionModel currentOrderSelection;

    private static int[] selectedRows;

    public WaiterGUI(){
        this.setTitle(this.getClass().getSimpleName());
        this.setContentPane(this.waiter);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        menuItemTableModel = new MenuItemTableModel(Restaurant.getInstance().getItems(),false, null);
        menuSelection = menuItemTable.getSelectionModel();
        menuItemTable.setModel(menuItemTableModel);

        currentOrderModel = new OrderTableModel();
        currentOrderTable.setModel(currentOrderModel);
        currentOrderSelection= currentOrderTable.getSelectionModel();


        String[] header = new String[]{"ID", "Name", "Quantity"};
        String[] data = {"asd", "asd", "asd"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        activeOrdersTable.setModel(model);
        model.addRow(data);



        addListeners();

    }

    private void addListeners(){
        Restaurant restaurant = Restaurant.getInstance();
        restaurant.addObserver(menuItemTableModel);
        restaurant.addObserver(currentOrderModel);

        addMenuItemSelectionListener();
        addCurrentOrderSelectionListener();
        addItemButtonListener();
        addRemoveItemButtonListener();
        addMakeOrderListener();
        addMakeBillListener();
    }

    private void addMenuItemSelectionListener() {
        menuSelection.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                WaiterGUI.selectedRows = menuItemTable.getSelectedRows();

                System.out.println(selectedRows.length);
                addItemButton.setEnabled(selectedRows.length >0);
            }
        });
    }

    private void addRemoveItemButtonListener() {
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOrderModel.removeItems(currentOrderTable.getSelectedRows());
                makeOrderButton.setEnabled(currentOrderTable.getRowCount() > 0);
                currentOrderSelection.clearSelection();
            }
        });
    }

    private void addMakeOrderListener() {
        makeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentOrderTable.getRowCount() == 0){
                    JOptionPane.showMessageDialog(null, "Order is empty, can't be made.", "Empty Order", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                currentOrderModel.makeOrder(currentOrderTable.getSelectedRows());
            }
        });
    }

    private void addMakeBillListener() {
        makeBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Restaurant.getInstance().set();
            }
        });
    }

    private void addCurrentOrderSelectionListener() {
        currentOrderSelection.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                removeItemButton.setEnabled(currentOrderTable.getSelectedRows().length > 0);
            }
        });
    }

    private void addItemButtonListener() {
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOrderModel.addItems(menuItemTableModel.getItems(menuItemTable.getSelectedRows()));
                makeOrderButton.setEnabled(currentOrderTable.getRowCount() > 0);
            }
        });
    }
}
