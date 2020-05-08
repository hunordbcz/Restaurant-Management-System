package bll;

import util.Constants;

import javax.swing.event.TableModelEvent;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class Order extends Observable implements Serializable {

    private int ID;
    private int table;
    private double totalPrice;
    private String date = new Timestamp(new Date().getTime()).toString();

    public Order(int table) {
        this.ID = Constants.getOrderID();
        this.table = table;
        this.totalPrice = 0D;
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

    public double calculatePrice(){
        List<MenuItem> items = Restaurant.getInstance().getOrderItems(this);
        totalPrice = 0D;
        for(MenuItem item : items){
            totalPrice+=item.getPrice();
        }
        setChanged();
        notifyObservers(TableModelEvent.UPDATE);
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public int getID() {
        return ID;
    }

    public String getDate() {
        return date;
    }

    public int getTable() {
        return table;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bill #").append(ID).append(" - Table ").append(table).append("\n");
        stringBuilder.append("Date: ").append(date).append("\n");

        List<MenuItem> items = Restaurant.getInstance().getOrderItems(this);
        for (MenuItem item : items) {
            stringBuilder.append(item.getName()).append("\t~\tPrice: ").append(item.getPrice()).append("\n\n");
        }
        stringBuilder.append("Total: ").append(totalPrice);
        return stringBuilder.toString();
    }
}
