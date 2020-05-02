package com.restaurant.bll;

import com.restaurant.IRestaurantProcessing;
import com.restaurant.util.Helper;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.TableModelEvent;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Restaurant extends Observable implements  Serializable, Observer {
    private List<MenuItem> items;
    private Map<Order, LinkedList<MenuItem>> orders;
    private static Restaurant restaurantInstance = null;

    private Restaurant(){
        items = new LinkedList<>();
    }

    public static Restaurant getInstance(){
        if(restaurantInstance == null){
            restaurantInstance = new Restaurant();
        }

        return restaurantInstance;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void addItem(MenuItem item) {
        insertItem(items.size(), item);
    }

    public void insertItem(int pos, MenuItem item) {
        items.add(pos, item);
        setChanged();
        notifyObservers(TableModelEvent.INSERT);
    }

    public void removeItem(int pos) {
        items.remove(pos);
        setChanged();
        notifyObservers(TableModelEvent.DELETE);
    }

    public void removeItem(MenuItem item){
        items.remove(item);
        setChanged();
        notifyObservers(TableModelEvent.DELETE);
    }

    
    public void createMenuItem(@NotNull MenuItem item) {

    }

    
    public void deleteMenuItem(MenuItem item) {

    }

    public Object getMenuItemField(MenuItem item,  int nrField){
        Object value = "~?~";
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(Helper.getFieldName(MenuItem.class, nrField), MenuItem.class);
            Method method = propertyDescriptor.getReadMethod();
            value = method.invoke(item);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }
    
    public void editMenuItem(MenuItem item, Object aValue, int nrField) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(Helper.getFieldName(MenuItem.class, nrField), MenuItem.class);
            Method method = propertyDescriptor.getWriteMethod();
            method.invoke(item, aValue);
            setChanged();
            notifyObservers(TableModelEvent.UPDATE);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    
    public void createOrder(Order order, ArrayList<MenuItem> menuItem) {

    }

    
    public int computeOrderPrice(Order order) {
        return 0;
    }

    
    public void generateBill(String whatToPrint) {

    }

    
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(TableModelEvent.UPDATE);
    }
}
