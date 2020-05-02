package com.restaurant.bll;

import java.util.LinkedList;
import java.util.List;

public class CompositeProduct extends MenuItem {
    private List<MenuItem> products;

    public CompositeProduct(String name){
        this.name = name;
        this.price = 0D;
        this.products = new LinkedList<>();
        this.setType(this.getClass().getSimpleName());
    }

    public void addProduct(MenuItem product){
        this.products.add(product);
        this.price += product.getPrice();
    }

    public Boolean removeProduct(MenuItem product){
        if(!this.products.contains(product)){
            return false;
        }
        this.products.remove(product);
        this.price -= product.getPrice();

        return true;
    }

    public List<MenuItem> getProducts() {
        return products;
    }
}
