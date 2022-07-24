package MUD.Model.Inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The class for a bag within the players inventory. A composite
 * stereotype within the composite pattern.
 * @author Phil Ganem
 */
public class Bag implements InventoryElement, Serializable {

    //The number of items a bag can hold.
    private int size;
    // Value of all the items in the bag.
    private int value;
    // The list of items in the bag.
    private List<Item> items;

    /**
     * Constructor for the bag.
     * @param size The number of items the bag can hold.
     */
    public Bag(int size) {
        this.items = new ArrayList<Item>();
        this.value = 0;
        this.size = size;
    }

    /**
     * Constructor for a bag with a default size of 6.
     */
    public Bag() {
        this.items = new ArrayList<Item>();
        this.value = 0;
        this.size = 6;
    }


// ----------- Getters and Setters ------------

    @Override
    public int getValue() {
        int totalValue = 0;
        for (Item item : items) {
            totalValue += item.getValue();
        }
        // Add the value of the bag and items in the bag.
        return totalValue + value;
    }

    @Override
    public boolean hasSpace() {
        //If # of items < the bag's capacity
        return items.size() < size;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item){
        this.items.add(item);
    }

}
