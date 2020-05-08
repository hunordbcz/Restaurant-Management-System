package dao;

import bll.Restaurant;
import util.Constants;

import java.io.IOException;

public class RestaurantSerializator {
    String fileName;

    public RestaurantSerializator(String fileName){
        this.fileName = fileName;
    }

    public RestaurantSerializator(){
        this.fileName = Constants.getSerializableName();
    }

    public boolean set() {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.out(Restaurant.getInstance());
            fileWriter.close();
            return true;
        } catch (IOException i) {
            i.printStackTrace();
        }
        return false;
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
