package view;

import bll.CompositeProduct;
import bll.MenuItem;
import bll.Restaurant;
import util.Helper;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MenuItemTableModel extends AbstractTableModel implements Observer {
    private Restaurant restaurant;
    private List<MenuItem> source;
    private CompositeProduct compositeProduct;
    private Boolean isEditable;

    public MenuItemTableModel(List<MenuItem> source, Boolean isEditable, CompositeProduct compositeProduct) {
        restaurant = Restaurant.getInstance();
        this.source = source;
        this.compositeProduct = compositeProduct;
        this.isEditable = isEditable;
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
    public String getColumnName(int column) {
        return Helper.getFieldName(MenuItem.class, column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MenuItem item = getItem(rowIndex);
        return restaurant.getMenuItemField(item, columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        MenuItem item = getItem(rowIndex);
        restaurant.editMenuItem(item, aValue, columnIndex);
        if(compositeProduct != null){
            compositeProduct.recalculatePrice();
        }
    }

    public void insertItem(int row, MenuItem item) {
        source.add(row, item);
    }

    public void removeItem(int row) {
        MenuItem item = getItem(row);
        if (compositeProduct != null) {
            compositeProduct.removeProduct(item);
            fireTableRowsDeleted(row, row);
        } else {
            restaurant.removeItem(item);
        }
    }

    public MenuItem getItem(int row) {
        return source.get(row);
    }

    public List<MenuItem> getItems(int[] rows) {
        List<MenuItem> items = new ArrayList<>();
        for (int row : rows) {
            items.add(getItem(row));
        }
        return items;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(getItem(rowIndex).getType().equals("CompositeProduct") && columnIndex == 3){
            return false;
        }
        return columnIndex > 1 && isEditable;
    }

    @Override
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
