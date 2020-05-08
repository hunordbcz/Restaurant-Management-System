package bll;

import java.util.List;

public interface IRestaurantProcessing {


    /**
     * @return List of the Menu Items of the restaurant
     * @post The Menu Item must not be null
     */
    public List<MenuItem> getItems();

    /**
     * @param item Menu Item that should be added to the current Restaurant
     * @pre The item that will be added is not null
     * @post The size of the list of the items should be incremented
     */
    public void addItem(MenuItem item);

    /**
     * @param item Menu Item that should be removed to the current Restaurant
     * @pre The item that will be removed is not null
     * @post The size of the list of the items should be decremented
     */
    public void removeItem(MenuItem item);

    /**
     * Returns the value of the nth field from a MenuItem object
     *
     * @param item    The given object
     * @param nrField The nth field to get
     * @return The value of the nth field
     * @pre Item mustn't be null | nrField must be positive | nrField mustn't be greater then the nr of the fields from the MenuItem class
     */
    public Object getMenuItemField(MenuItem item, int nrField);

    /**
     * Edits the nth value from an object
     *
     * @param item    The object that should be edited
     * @param aValue  The value that should be replaced with
     * @param nrField The nth field that should be edited
     * @pre Item mustn't be null | aValue mustn't be null | nrField must be positive | nrField mustn't be greater then the nr of the fields from the MenuItem class
     */
    public void editMenuItem(MenuItem item, Object aValue, int nrField);

    /**
     * Creates a new order
     *
     * @param table Identifies the table from which the order came
     * @return An Order object
     * @pre Table must be positive
     * @post Order must be nonNull and it's table must coincide with *table*
     */
    public Order createOrder(int table);

    /**
     * Adds an item to an order
     *
     * @param order Order where the item will be added
     * @param item  Item that will be added to the order
     * @pre Order must not be null | Item must not be null
     * @post Order must contain the item
     */
    public void addItemToOrder(Order order, MenuItem item);

    /**
     * Returns the items from an Order
     *
     * @param order The given order
     * @return List of MenuItems
     * @pre Order must not be null
     * @post List must not be null
     */
    public List<MenuItem> getOrderItems(Order order);

    /**
     * Removes an item from an order
     *
     * @param order The given order
     * @param item  The given item
     * @pre Order must not be null | Item must not be null
     * @post Order must not contain the item
     */
    public void removeItemFromOrder(Order order, MenuItem item);

    /**
     * Returns every order from the restaurant
     *
     * @return List of orders
     */
    public List<Order> getOrders();

    /**
     * Makes a bill for an order
     *
     * @param order The given order
     * @pre Order must not be null
     * @post Order must not exist anymore
     */
    public void makeBill(Order order);
}
