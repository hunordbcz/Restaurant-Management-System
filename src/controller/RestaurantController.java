package controller;

import bll.Restaurant;
import view.AdministratorGUI;
import view.ChefGUI;
import view.WaiterGUI;

public class RestaurantController {
    public RestaurantController(){
        Restaurant restaurant = Restaurant.getInstance();

        ChefGUI chefGUI = new ChefGUI();
        AdministratorGUI administratorGUI = new AdministratorGUI();
        WaiterGUI waiterGUI = new WaiterGUI();

        restaurant.addObserver(chefGUI);
        restaurant.addObserver(administratorGUI);
        restaurant.addObserver(waiterGUI);
    }
}
