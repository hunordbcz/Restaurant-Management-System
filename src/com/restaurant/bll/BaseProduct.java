package com.restaurant.bll;

import java.io.Serializable;

public class BaseProduct extends MenuItem implements Serializable {
    public BaseProduct(String name, Double price){
        this.name = name;
        this.price = price;
        this.setType(this.getClass().getSimpleName());
    }
}
