package bll;

import util.Constants;

public class BaseProduct extends MenuItem {
    public BaseProduct(String name, Double price){
        this.ID = Constants.getItemID();
        this.name = name;
        this.price = price;
        this.setType(this.getClass().getSimpleName());
    }
}
