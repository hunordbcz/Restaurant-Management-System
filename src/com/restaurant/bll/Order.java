package com.restaurant.bll;

import com.restaurant.util.Constants;

import java.util.Date;

public class Order {

    private int ID;
    private Date date;

    public Order() {
        this.ID = Constants.getID();
        this.date = new Date();
    }

    @Override
    public int hashCode() {
        int hash = Constants.getHashVar() + this.ID;
        return hash * date.getMinutes();

    }

    public int getID() {
        return ID;
    }

    public Date getDate() {
        return date;
    }
}
