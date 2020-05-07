package com.restaurant.util;

import com.restaurant.bll.Restaurant;

public class Constants {

    public static Restaurant restaurantInstance;
    private static String SERIALIZABLE_NAME = "Restaurant.ser";
    private static final int HASH_VAR = 35;
    private static final int MENU_ITEM_FIELD_NR = 2;
    private static  int ID = 0;

    private Constants(){

    }

    public static String getSerializableName() {
        return SERIALIZABLE_NAME;
    }

    public static void setSerializableName(String serializableName) {
        SERIALIZABLE_NAME = serializableName;
    }

    public static int getHashVar() {
        return HASH_VAR;
    }

    public static int getID() {
        return ++ID;
    }

    public static void setID(int IDStart){
        ID = IDStart;
    }

    public static int getMenuItemFieldNr() {
        return MENU_ITEM_FIELD_NR;
    }
}
