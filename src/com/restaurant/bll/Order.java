package com.restaurant.bll;

import com.restaurant.util.Constants;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Order implements Serializable {

    private int ID;
    private String date = new Timestamp(new Date().getTime()).getMonth() + "-" + new Timestamp(new Date().getTime()).getDay();

    public Order() {
        this.ID = Constants.getID();
    }

    @Override
    public int hashCode() {
        return this.ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getID() == order.getID() &&
                Objects.equals(date, order.date);
    }

    public int getID() {
        return ID;
    }

    public String getDate() {
        return date;
    }
}
