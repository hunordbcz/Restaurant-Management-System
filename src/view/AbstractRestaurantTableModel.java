package view;

import util.Helper;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class AbstractRestaurantTableModel<T> extends AbstractTableModel implements Observer {
    protected List<T> source;
    private final Class<T> type;
    private Boolean isEditable = false;

    public AbstractRestaurantTableModel(List<T> source, Class<T> type, Boolean isEditable) {
        this.source = source;
        this.type = type;
        this.isEditable = isEditable;
    }

    public AbstractRestaurantTableModel(List<T> source, Class<T> type){
        if(source == null){
            source = new ArrayList<T>();
        }
        this.source = source;
        this.type = type;
    }

    @Override
    public int getRowCount() {
        return source.size();
    }

    @Override
    public int getColumnCount() {
        return type.getDeclaredFields().length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Helper.getFieldClass(type, columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return Helper.getFieldName(type, columnIndex).toUpperCase();
    }

    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return isEditable;
    }

    public T getItem(int row) {
        return source.get(row);
    }

    public List<T> getItems(int[] rows){
        List<T> items = new ArrayList<>();
        for(int row : rows){
            items.add(getItem(row));
        }
        return items;
    }

    public void addItem(T item){
        this.source.add(item);
        fireTableRowsInserted(0, getRowCount());
    }

    public void addItems(List<T> items){
        for (T item : items){
            this.addItem(item);
        }
    }

    public void removeItem(int row) {
        this.source.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void removeItems(int[] selectedRows){
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            this.removeItem(selectedRows[i]);
        }
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
