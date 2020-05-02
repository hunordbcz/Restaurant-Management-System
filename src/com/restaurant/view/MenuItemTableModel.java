package com.restaurant.view;

import com.restaurant.bll.MenuItem;
import com.restaurant.bll.Restaurant;
import com.restaurant.util.Helper;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MenuItemTableModel extends AbstractTableModel implements Observer {

    private Restaurant restaurant;
    private List<MenuItem> source;
    private Boolean isComposite;
    private Boolean isEditable;

    public MenuItemTableModel(List<MenuItem> source, Boolean isComposite, Boolean isEditable) {
        restaurant = Restaurant.getInstance();
        this.source = source;
        this.isComposite = isComposite;
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
        return Helper.getColumnClass(MenuItem.class, columnIndex);
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
    }

    public void insertItem(int row, MenuItem item) {
        source.add(row, item);
    }

    public void removeItem(int row) {
        MenuItem item = getItem(row);
        if (isComposite) {
            source.remove(item);
            fireTableRowsDeleted(row, row);
        } else {
            restaurant.removeItem(item);
        }
    }

    public MenuItem getItem(int row) {
        return source.get(row);
    }

    public List<MenuItem> getItems(int[] rows) {
        List<MenuItem> items = new LinkedList<>();
        for (int row : rows) {
            items.add(getItem(row));
        }
        return items;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0 && isEditable;
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
