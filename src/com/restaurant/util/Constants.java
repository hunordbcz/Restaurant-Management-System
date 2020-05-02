package com.restaurant.util;

public class Constants {

    private static String SERIALIZABLE_NAME = "Restaurant.data";
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

    public static int getMenuItemFieldNr() {
        return MENU_ITEM_FIELD_NR;
    }
}
