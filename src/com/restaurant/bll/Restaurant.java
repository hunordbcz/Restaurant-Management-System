package com.restaurant.bll;

import com.restaurant.dao.RestaurantSerializator;
import com.restaurant.util.Helper;

import javax.swing.event.TableModelEvent;

import java.io.Serializable;
import java.util.*;

public class Restaurant extends Observable implements Serializable {
    private List<MenuItem> items;
    private Map<Order, List<MenuItem>> orders;
    private static Restaurant restaurantInstance = null;
    private static final long serialVersionUID = 123L;

    private Restaurant(){
        items = new ArrayList<>();
        orders = new HashMap<>();
    }

    public static Restaurant getInstance(){
        if(restaurantInstance == null){
            Restaurant restaurant = new RestaurantSerializator().get();
            restaurantInstance = Objects.requireNonNullElseGet(restaurant, Restaurant::new);
        }

        return restaurantInstance;
    }

    public static void setInstance(Restaurant restaurant) {
        restaurantInstance = restaurant;
    }

    public void set(){
        new RestaurantSerializator().set();
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void addItem(MenuItem item) {
        insertItem(items.size(), item);
//        item.addObserver(this);
    }

    public void insertItem(int pos, MenuItem item) {
        assert(item != null);

        items.add(pos, item);
        setChanged();
        notifyObservers(TableModelEvent.INSERT);

//        assert items.size() == +1
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

    public Object getMenuItemField(MenuItem item,  int nrField){
        return Helper.getFieldValue(MenuItem.class, item, nrField);
    }
    
    public void editMenuItem(MenuItem item, Object aValue, int nrField) {
        Helper.setFieldValue(MenuItem.class, item, aValue, nrField);
        setChanged();
        notifyObservers(TableModelEvent.UPDATE);
    }
    
    public Order createOrder() {
        Order order = new Order();
        List<MenuItem> items = new ArrayList<>();
        this.orders.put(order, items);
        return order;
    }

    public void addItemToOrder(Order order, MenuItem item){
        List<MenuItem> items = this.orders.get(order);
        items.add(item);
    }

    public void addItemsToOrder(Order order, List<MenuItem> items) {
        for (MenuItem item : items){
            this.addItemToOrder(order, item);
        }
    }

    public void removeItemFromOrder(Order order, MenuItem item){
        List<MenuItem> items = this.orders.get(order);
        items.remove(item);
    }

    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
