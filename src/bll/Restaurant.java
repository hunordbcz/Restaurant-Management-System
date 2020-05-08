package bll;

import dao.FileWriter;
import dao.RestaurantSerializator;
import util.Constants;
import util.Helper;

import javax.swing.event.TableModelEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Restaurant extends Observable implements IRestaurantProcessing, Serializable, Observer {
    private static Restaurant restaurantInstance = null;
    private final List<MenuItem> items;
    private final Map<Order, List<MenuItem>> orders;

    private Restaurant() {
        items = new ArrayList<>();
        orders = new HashMap<>();
    }

    public static Restaurant getInstance() {
        if (restaurantInstance == null) {
            Restaurant restaurant = new RestaurantSerializator().get();
            restaurantInstance = Objects.requireNonNullElseGet(restaurant, Restaurant::new);
            Constants.setItemID(restaurantInstance.getItems().size());
            Constants.setOrderID(restaurantInstance.getOrders().size());
        }

        return restaurantInstance;
    }

    public static void setInstance(Restaurant restaurant) {
        restaurantInstance = restaurant;
    }

    public boolean serializeAndSet() {
        return new RestaurantSerializator().set();
    }

    /**
     * @return List of the Menu Items of the restaurant
     * @post The Menu Item must not be null
     */
    @Override
    public List<MenuItem> getItems() {
        List<MenuItem> items = this.items;

        assert isWellFormed();
        return items;
    }

    /**
     * @param item Menu Item that should be added to the current Restaurant
     * @pre The item that will be added is not null
     * @post The size of the list of the items should be incremented
     */
    @Override
    public void addItem(MenuItem item) {
        assert item != null;
        assert isWellFormed();
        int size = items.size();

        item.addObserver(this);
        items.add(item);

        assert isWellFormed();
        assert items.size() == size + 1;
        setChanged();
        notifyObservers(TableModelEvent.INSERT);
    }

    /**
     * @param item Menu Item that should be removed to the current Restaurant
     * @pre The item that will be removed is not null
     * @post The size of the list of the items should be decremented
     */
    @Override
    public void removeItem(MenuItem item) {
        assert item != null;
        assert isWellFormed();
        int size = items.size();

        items.remove(item);

        assert isWellFormed();
        assert size - 1 == items.size();
        setChanged();
        notifyObservers(TableModelEvent.DELETE);
    }

    /**
     * Returns the value of the nth field from a MenuItem object
     *
     * @param item    The given object
     * @param nrField The nth field to get
     * @return The value of the nth field
     * @pre Item mustn't be null | nrField must be positive | nrField mustn't be greater then the nr of the fields from the MenuItem class
     */
    @Override
    public Object getMenuItemField(MenuItem item, int nrField) {
        assert item != null;
        assert nrField >= 0;
        assert nrField < MenuItem.class.getDeclaredFields().length;
        assert isWellFormed();

        return Helper.getFieldValue(MenuItem.class, item, nrField);
    }

    /**
     * Edits the nth value from an object
     *
     * @param item    The object that should be edited
     * @param aValue  The value that should be replaced with
     * @param nrField The nth field that should be edited
     * @pre Item mustn't be null | aValue mustn't be null | nrField must be positive | nrField mustn't be greater then the nr of the fields from the MenuItem class
     */
    @Override
    public void editMenuItem(MenuItem item, Object aValue, int nrField) {
        assert item != null;
        assert aValue != null;
        assert nrField >= 0;
        assert nrField < MenuItem.class.getDeclaredFields().length;
        assert isWellFormed();

        Helper.setFieldValue(MenuItem.class, item, aValue, nrField);

        assert isWellFormed();
        setChanged();
        notifyObservers(TableModelEvent.UPDATE);
    }

    /**
     * Creates a new order
     *
     * @param table Identifies the table from which the order came
     * @return An Order object
     * @pre Table must be positive
     * @post Order must be nonNull and it's table must coincide with *table*
     */
    @Override
    public Order createOrder(int table) {
        assert table > 0;
        assert isWellFormed();

        Order order = new Order(table);
        List<MenuItem> items = new ArrayList<>();
        this.orders.put(order, items);

        setChanged();
        notifyObservers(TableModelEvent.UPDATE);

        assert isWellFormed();
        return order;
    }

    /**
     * Adds an item to an order
     *
     * @param order Order where the item will be added
     * @param item  Item that will be added to the order
     * @pre Order must not be null | Item must not be null
     * @post Order must contain the item
     */
    @Override
    public void addItemToOrder(Order order, MenuItem item) {
        assert order != null;
        assert item != null;
        assert isWellFormed();

        List<MenuItem> items = getOrderItems(order);
        items.add(item);
        order.calculatePrice();

        assert items.contains(item);
        assert isWellFormed();

        setChanged();
        notifyObservers(TableModelEvent.UPDATE);
    }

    /**
     * Returns the items from an Order
     *
     * @param order The given order
     * @return List of MenuItems
     * @pre Order must not be null
     * @post List must not be null
     */
    @Override
    public List<MenuItem> getOrderItems(Order order) {
        assert order != null;
        assert isWellFormed();
        List<MenuItem> items = this.orders.get(order);
        assert items != null;
        assert isWellFormed();
        return items;
    }

    /**
     * Removes an item from an order
     *
     * @param order The given order
     * @param item  The given item
     * @pre Order must not be null | Item must not be null
     * @post Order must not contain the item
     */
    @Override
    public void removeItemFromOrder(Order order, MenuItem item) {
        assert order != null;
        assert item != null;
        assert isWellFormed();

        List<MenuItem> items = this.orders.get(order);
        items.remove(item);
        order.calculatePrice();

        assert !items.contains(item);
        assert isWellFormed();
        setChanged();
        notifyObservers(TableModelEvent.UPDATE);
    }

    /**
     * Returns every order from the restaurant
     *
     * @return List of orders
     */
    @Override
    public List<Order> getOrders() {
        return new ArrayList<>(this.orders.keySet());
    }

    /**
     * Makes a bill for an order
     *
     * @param order The given order
     * @pre Order must not be null
     * @post Order must not exist anymore
     */
    @Override
    public void makeBill(Order order) {
        assert order != null;
        assert isWellFormed();

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("bill-" + order.getID() + ".txt");
            fileWriter.outText(order.toString());
            orders.remove(order);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
        assert isWellFormed();
        assert orders.containsKey(order);
        setChanged();
        notifyObservers(TableModelEvent.DELETE);
    }

    @Override
    public void update(Observable o, Object arg) {
        for (Order order : getOrders()) {
            order.calculatePrice();
        }
        setChanged();
        notifyObservers(TableModelEvent.UPDATE);
    }

    private boolean isWellFormed() {
        return checkItems(items) && checkOrders();
    }

    private boolean checkItems(List<MenuItem> items) {
        List<Integer> itemIDs = new ArrayList<>();
        for (MenuItem item : items) {
            if (itemIDs.contains(item.getID())) {
                return false;
            } else {
                itemIDs.add(item.getID());
            }

            if (item instanceof CompositeProduct) {
                if (!item.getType().equals("CompositeProduct")) {
                    return false;
                }

                double priceCalc = 0d;
                for (MenuItem baseProduct : ((CompositeProduct) item).getProducts()) {
                    priceCalc += baseProduct.getPrice();
                }
                if (priceCalc != item.getPrice()) {
                    return false;
                }
            } else {
                if (!item.getType().equals("BaseProduct")) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkOrders() {
        List<Order> orders = getOrders();
        List<Integer> orderIDs = new ArrayList<>();

        for (Order order : orders) {
            if (orderIDs.contains(order.getID())) {
                return false;
            } else {
                orderIDs.add(order.getID());
            }

            List<MenuItem> orderItems = getOrderItems(order);
            if (!checkItems(orderItems)) {
                return false;
            }
            double priceCalc = 0d;

            for (MenuItem item : orderItems) {
                priceCalc += item.getPrice();
            }

            if (priceCalc != order.getTotalPrice()) {
                return false;
            }
        }

        return true;
    }
}
