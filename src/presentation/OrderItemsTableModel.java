package presentation;

import bll.MenuItem;
import bll.Order;
import bll.Restaurant;
import util.Helper;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderItemsTableModel extends AbstractTableModel {
    private List<MenuItem> source;
    private Restaurant restaurant;
    private Order currentOrder = null;

    public OrderItemsTableModel(Order order) {
        restaurant = Restaurant.getInstance();
        currentOrder = order;
        source = restaurant.getOrderItems(order);
        if(source == null){
            source = new ArrayList<>();
        }
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    @Override
    public int getRowCount() {
        return source.size();
    }

    @Override
    public int getColumnCount() {
        return MenuItem.class.getDeclaredFields().length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Helper.getFieldClass(MenuItem.class, columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return Objects.requireNonNull(Helper.getFieldName(MenuItem.class, columnIndex)).toUpperCase();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MenuItem item = this.getItem(rowIndex);
        return restaurant.getMenuItemField(item, columnIndex);
    }

    public MenuItem getItem(int row) {
        return source.get(row);
    }

    public List<MenuItem> getItems(int[] rows){
        List<MenuItem> items = new ArrayList<>();
        for(int row : rows){
            items.add(getItem(row));
        }
        return items;
    }

    public void addItem(MenuItem item){
        restaurant.addItemToOrder(currentOrder, item);
    }

    public void addItems(List<MenuItem> items){
        for (MenuItem item : items){
            this.addItem(item);
        }
    }

    public void removeItem(int row) {
        restaurant.removeItemFromOrder(currentOrder, getItem(row));
    }

    public void removeItems(int[] selectedRows){
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            this.removeItem(selectedRows[i]);
        }
    }
}
