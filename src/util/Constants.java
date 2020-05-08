package util;

public class Constants {
    private static String SERIALIZABLE_NAME = "restaurant.ser";
    private static final int HASH_VAR = 35;
    private static final int MENU_ITEM_FIELD_NR = 2;
    private static int itemID = 0;
    private static int orderID = 0;

    private Constants() {

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

    public static int getItemID() {
        return ++itemID;
    }

    public static void setItemID(int itemID) {
        Constants.itemID = itemID;
    }

    public static int getOrderID() {
        return ++orderID;
    }

    public static void setOrderID(int orderID) {
        Constants.orderID = orderID;
    }

    public static int getMenuItemFieldNr() {
        return MENU_ITEM_FIELD_NR;
    }
}
