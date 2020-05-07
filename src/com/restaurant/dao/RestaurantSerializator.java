package com.restaurant.dao;

import com.restaurant.bll.Restaurant;
import com.restaurant.util.Constants;

import java.io.*;

public class RestaurantSerializator {
    String fileName;

    public RestaurantSerializator(String fileName){
        this.fileName = fileName;
    }

    public RestaurantSerializator(){
        this.fileName = Constants.getSerializableName();
    }

    public void set() {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.out(Restaurant.getInstance());
            fileWriter.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public Restaurant get() {
        Restaurant restaurant;

        try {
            FileReader<Restaurant> fileReader = new FileReader<>(fileName);
            restaurant = fileReader.in();
            fileReader.close();
        } catch (IOException | ClassNotFoundException e) {
            restaurant = null;
        }

        return restaurant;
    }
}
