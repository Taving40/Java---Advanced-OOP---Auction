package auction;

import java.util.ArrayList;

public class Inventory { //should be written to file or stored in DB to avoid calling it when needing to look up an item via its name

    private ArrayList<Item> items;
    private static Inventory single_instance = null;

    public Inventory(){}

    public static Inventory getInstance()
    {
        if (single_instance == null)
            single_instance = new Inventory();

        return single_instance;
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
            rez += "Item no. " + String.valueOf(i) + ": " + items.get(i-1).toString();
        }
        return rez;
    }

    public void showInventory(){
        System.out.println(this.toString());
    }
}
