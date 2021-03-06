package presentation;

import bll.Order;
import bll.Restaurant;
import util.Helper;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ActiveOrdersTableModel extends AbstractTableModel {
    private List<Order> source;
    private Restaurant restaurant;

    public ActiveOrdersTableModel(List<Order> src) {
        restaurant = Restaurant.getInstance();
        this.source = src;
    }

    @Override
    public int getRowCount() {
        return source.size();
    }

    @Override
    public int getColumnCount() {
        return Order.class.getDeclaredFields().length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Helper.getFieldClass(Order.class, columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return Helper.getFieldName(Order.class, columnIndex).toUpperCase();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        Order order = this.getItem(rowIndex);
        return Helper.getFieldValue(Order.class, order, columnIndex);
    }

    public Order getItem(int row) {
        return source.get(row);
    }

    public List<Order> getItems(int[] rows){
        List<Order> items = new ArrayList<>();
        for(int row : rows){
            items.add(getItem(row));
        }
        return items;
    }

    public void addItem(Order item){
        this.source.add(item);
        fireTableRowsInserted(0, getRowCount());
    }

    public void removeItem(int row) {
        this.source.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void update(Observable o, Object arg) {
        switch ((int) arg) {
            case TableModelEvent.INSERT:
                fireTableRowsInserted(0, getRowCount());
                break;
            case TableModelEvent.UPDATE:
                fireTableRowsUpdated(0, getRowCount());
                break;
            case TableModelEvent.DELETE:
                fireTableRowsDeleted(0, getRowCount());
            default:
                break;
        }
    }
}
