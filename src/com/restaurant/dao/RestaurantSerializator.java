package com.restaurant.dao;

import com.restaurant.bll.Restaurant;
import com.restaurant.util.Constants;

import java.io.*;

public class RestaurantSerializator {
    private static FileWriter fileWriter;
    private static FileReader<Restaurant> fileReader;

    static {
        String fileName = Constants.getSerializableName();

        try {
            fileWriter = new FileWriter(fileName);
            fileReader = new FileReader<>(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void set(Restaurant restaurant) {
        try {
            fileWriter.out(restaurant);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileWriter.close();
        }
    }

    public static Restaurant get() {
        Restaurant restaurant;
        try {
            restaurant = fileReader.in();
        } catch (IOException | ClassNotFoundException i) {
            restaurant = Restaurant.getInstance();
            set(restaurant);
        }  finally {
            fileReader.close();
        }

        return restaurant;
    }
}
