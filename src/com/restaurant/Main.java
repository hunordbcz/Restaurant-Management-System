package com.restaurant;

import com.restaurant.controller.RestaurantController;
import com.restaurant.util.Constants;

public class Main {

    public static void main(String[] args) {
        if(args.length > 0){
            Constants.setSerializableName(args[0]);
        }
        new RestaurantController();
    }
}