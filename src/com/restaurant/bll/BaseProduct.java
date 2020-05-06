package com.restaurant.bll;

import com.restaurant.util.Constants;

import java.io.Serializable;

public class BaseProduct extends MenuItem {
    public BaseProduct(String name, Double price){
        this.ID = Constants.getID();
        this.name = name;
        this.price = price;
        this.setType(this.getClass().getSimpleName());
    }
}
