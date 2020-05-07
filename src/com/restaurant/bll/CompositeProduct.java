package com.restaurant.bll;

import com.restaurant.util.Constants;

import javax.swing.event.TableModelEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem {
    private final List<MenuItem> products;

    public CompositeProduct(String name) {
        this.ID = Constants.getID();
        this.name = name;
        this.price = 0D;
        this.products = new ArrayList<>();
        this.setType(this.getClass().getSimpleName());
    }

    public void addProduct(MenuItem product) {
        this.products.add(product);
        recalculatePrice();
    }

    public Boolean removeProduct(MenuItem product) {
        if (!this.products.contains(product)) {
            return false;
        }
        this.products.remove(product);
        recalculatePrice();

        return true;
    }

    public List<MenuItem> getProducts() {
        return products;
    }

    public void recalculatePrice() {
        this.price = 0D;
        for (MenuItem item : products) {
            price += item.getPrice();
        }
        setChanged();
        notifyObservers(TableModelEvent.UPDATE);
    }
}
