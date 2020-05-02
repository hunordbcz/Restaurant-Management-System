package com.restaurant.bll;

import javax.swing.event.TableModelEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Observable;

public abstract class MenuItem {
    protected String type;
    protected String name;
    protected Double price;
    protected String date = new Timestamp(new Date().getTime()).getMonth() + "-" + new Timestamp(new Date().getTime()).getDay();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
