package presentation;

import bll.Order;
import bll.Restaurant;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class WaiterGUI extends JFrame implements Observer {
    private JPanel waiter;
    private JTable menuItemTable;
    private JTable currentOrderTable;
    private JTable activeOrdersTable;
    private JButton addItemButton;
    private JButton removeItemButton;
    private JButton makeBillButton;
    private JButton newOrderButton;
    private JTextField tableTextField;
    private JLabel orderId;
    private JLabel tableId;
    private JButton editOrderButton;

    private MenuItemTableModel menuItemTableModel = null;
    private OrderItemsTableModel currentOrderModel = null;
    private ActiveOrdersTableModel activeOrdersModel = null;

    ListSelectionModel menuSelection;
    ListSelectionModel currentOrderSelection;
    ListSelectionModel activeOrderSelection;

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

        currentOrderModel = new OrderItemsTableModel(null);
        currentOrderTable.setModel(currentOrderModel);
        currentOrderSelection= currentOrderTable.getSelectionModel();

        activeOrdersModel = new ActiveOrdersTableModel(Restaurant.getInstance().getOrders());
        activeOrdersTable.setModel(activeOrdersModel);
        activeOrderSelection = activeOrdersTable.getSelectionModel();

        addListeners();

        tableTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                newOrderButton.setEnabled(tableTextField.getText().length() > 0);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                newOrderButton.setEnabled(tableTextField.getText().length() > 0);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        editOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order order = activeOrdersModel.getItem(activeOrdersTable.getSelectedRow());
                currentOrderModel = new OrderItemsTableModel(order);
                currentOrderTable.setModel(currentOrderModel);
                orderId.setText(order.getID() + "");
                tableId.setText(order.getTable() + "");
            }
        });
    }

    private void addListeners(){
        Restaurant restaurant = Restaurant.getInstance();

        addMenuItemSelectionListener();
        addCurrentOrderSelectionListener();
        addActiveOrdersSelectionListener();
        addItemButtonListener();
        addRemoveItemButtonListener();
        addNewOrderListener();
        addMakeBillListener();
    }

    private void addMenuItemSelectionListener() {
        menuSelection.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                WaiterGUI.selectedRows = menuItemTable.getSelectedRows();
                addItemButton.setEnabled(selectedRows.length >0);
            }
        });
    }

    private void addRemoveItemButtonListener() {
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOrderModel.removeItems(currentOrderTable.getSelectedRows());
                newOrderButton.setEnabled(currentOrderTable.getRowCount() > 0);
                currentOrderSelection.clearSelection();
            }
        });
    }

    private void addNewOrderListener() {
        newOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int table;
                try{
                    table = Integer.parseInt(tableTextField.getText());
                }catch (Exception i){
                    JOptionPane.showMessageDialog(null, "Wrong table", "Error in Inputs", JOptionPane.ERROR_MESSAGE);
                    newOrderButton.setEnabled(false);
                    return;
                }

                Order order = Restaurant.getInstance().createOrder(table);
                currentOrderModel = new OrderItemsTableModel(order);
                currentOrderTable.setModel(currentOrderModel);

                orderId.setText(order.getID() + "");
                tableId.setText(order.getTable() + "");
            }
        });
    }

    private void addMakeBillListener() {
        makeBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Restaurant.getInstance().makeBill(activeOrdersModel.getItem(activeOrdersTable.getSelectedRow()));
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

    private void addActiveOrdersSelectionListener() {
        activeOrderSelection.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                makeBillButton.setEnabled(activeOrdersTable.getSelectedRows().length > 0);
                editOrderButton.setEnabled(activeOrdersTable.getSelectedRows().length > 0);
            }
        });
    }

    private void addItemButtonListener() {
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentOrderModel.getCurrentOrder() == null) {
                    JOptionPane.showMessageDialog(null, "No Order Selected", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                currentOrderModel.addItems(menuItemTableModel.getItems(menuItemTable.getSelectedRows()));
                newOrderButton.setEnabled(currentOrderTable.getRowCount() > 0);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        menuItemTableModel =  new MenuItemTableModel(Restaurant.getInstance().getItems(),false, null);
        menuItemTable.setModel(menuItemTableModel);

        activeOrdersModel = new ActiveOrdersTableModel(Restaurant.getInstance().getOrders());
        activeOrdersTable.setModel(activeOrdersModel);

        if(currentOrderModel.getCurrentOrder() == null){
            return;
        }
        Order order = currentOrderModel.getCurrentOrder();
        currentOrderModel = new OrderItemsTableModel(order);
        currentOrderTable.setModel(currentOrderModel);
        orderId.setText(order.getID() + "");
        tableId.setText(order.getTable() + "");
    }
}
