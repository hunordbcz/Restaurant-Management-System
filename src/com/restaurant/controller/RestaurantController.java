package com.restaurant.controller;

import com.restaurant.bll.BaseProduct;
import com.restaurant.bll.MenuItem;
import com.restaurant.bll.Restaurant;
import com.restaurant.dao.FileReader;
import com.restaurant.dao.FileWriter;
import com.restaurant.dao.RestaurantSerializator;
import com.restaurant.view.AdministratorGUI;
import com.restaurant.view.ChefGUI;
import com.restaurant.view.WaiterGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantController {
    public RestaurantController(){
        ChefGUI chefGUI = new ChefGUI();
        AdministratorGUI administratorGUI = new AdministratorGUI();
        WaiterGUI waiterGUI = new WaiterGUI();
    }
}
