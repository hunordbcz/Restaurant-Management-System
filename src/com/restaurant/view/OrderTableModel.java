package com.restaurant.view;

import com.restaurant.bll.MenuItem;
import com.restaurant.bll.Order;
import com.restaurant.bll.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class OrderTableModel extends AbstractRestaurantTableModel<MenuItem> {
    private Restaurant restaurant = Restaurant.getInstance();

    public OrderTableModel() {
        super(new ArrayList<>(), MenuItem.class);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MenuItem item = this.getItem(rowIndex);
        return restaurant.getMenuItemField(item, columnIndex);
    }

    public void makeOrder(int[] selectedRows){
        Order order = restaurant.createOrder();

        List<MenuItem> items = this.getItems(selectedRows);
        restaurant.addItemsToOrder(order, items);
    }
}
