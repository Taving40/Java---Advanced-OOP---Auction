package auction;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> items = new ArrayList<Item>();
    private static Inventory singleInstance = null;

    public static Inventory getInstance(){
        if (singleInstance == null)
            singleInstance = new Inventory();
        return singleInstance;
    }

    public boolean isInInventory(String name){
        for(Item x: items) {
            if (x.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Item lookUpItem(String name){
        try{
            CSVHandler.writeCSVAudit("src\\csv\\audit.csv", "Looked-up item in inventory, " + LocalTime.now() + "\n", true);
        } catch (IOException e){
            System.out.println("Audit service failed to find file.");
        }
        for(Item x: items) {
            if (x.getName().equals(name)) {
                return x;
            }
        }
        return null;
    }

    public void storeItem(Item i){
        items.add(i);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void removeItem(Item i){
        items.remove(i);
    }

    @Override
    public String toString() {
        String rez = "Inventory: \n";
        for(int i = 1; i <= items.size(); i++){
            rez += "Item no. " + String.valueOf(i) + ": " + items.get(i-1).toString() + "\n";
        }
        return rez;
    }

    public void showInventory(){
        System.out.println(this.toString());
    }
}
