package presentation;

import bll.Order;
import bll.Restaurant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Observable;
import java.util.Observer;


public class ChefGUI extends JFrame implements Observer {
    private JPanel chef;
    private JTable orderItemsTable;
    private JTable ordersTable;

    private ActiveOrdersTableModel activeOrdersModel;
    private OrderItemsTableModel orderItemsTableModel;
    private ListSelectionModel activeOrderSelection;
    private Order lastOrder;

    public ChefGUI() {
        this.setTitle(this.getClass().getSimpleName());
        this.setContentPane(this.chef);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        activeOrdersModel = new ActiveOrdersTableModel(Restaurant.getInstance().getOrders());
        ordersTable.setModel(activeOrdersModel);
        activeOrderSelection = ordersTable.getSelectionModel();

        addListeners();
    }

    private void addListeners() {
        addActiveOrderSelectListener();
    }

    private void addActiveOrderSelectListener() {
        activeOrderSelection.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int row = ordersTable.getSelectedRow();
                if (0 <= row && row < Restaurant.getInstance().getOrders().size()) {
                    lastOrder = activeOrdersModel.getItem(ordersTable.getSelectedRow());
                    orderItemsTableModel = new OrderItemsTableModel(lastOrder);
                    orderItemsTable.setModel(orderItemsTableModel);
                }
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        activeOrdersModel = new ActiveOrdersTableModel(Restaurant.getInstance().getOrders());
        ordersTable.setModel(activeOrdersModel);

        orderItemsTableModel = new OrderItemsTableModel(lastOrder);
        orderItemsTable.setModel(orderItemsTableModel);
    }
}
