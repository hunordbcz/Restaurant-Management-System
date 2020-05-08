import bll.Restaurant;
import presentation.AdministratorGUI;
import presentation.ChefGUI;
import presentation.WaiterGUI;
import util.Constants;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            Constants.setSerializableName(args[0]);
        }
        Restaurant restaurant = Restaurant.getInstance();

        ChefGUI chefGUI = new ChefGUI();
        AdministratorGUI administratorGUI = new AdministratorGUI();
        WaiterGUI waiterGUI = new WaiterGUI();

        restaurant.addObserver(chefGUI);
        restaurant.addObserver(administratorGUI);
        restaurant.addObserver(waiterGUI);
    }
}