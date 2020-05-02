package com.restaurant.controller;

import com.restaurant.bll.BaseProduct;
import com.restaurant.bll.MenuItem;
import com.restaurant.bll.Restaurant;
import com.restaurant.dao.RestaurantSerializator;
import com.restaurant.view.AdministratorGUI;
import com.restaurant.view.ChefGUI;
import com.restaurant.view.WaiterGUI;

import java.util.LinkedList;
import java.util.List;

public class RestaurantController {
    Restaurant restaurant;

    public RestaurantController(){
        this.restaurant = RestaurantSerializator.get();

        List<MenuItem> test = new LinkedList<>();
        MenuItem data = new BaseProduct("Test", 2D);
        restaurant.addItem(data);

//        ChefGUI chefGUI = new ChefGUI();
        AdministratorGUI administratorGUI = new AdministratorGUI();
//        WaiterGUI waiterGUI = new WaiterGUI();
    }
}
