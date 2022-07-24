package MUD.Model.Inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The class for a players inventory. The composite stereotype 
 * within the composite pattern.
 * @author Phil Ganem
 */
public class Inventory implements InventoryElement, Serializable {

    //Max number of bags that can be in the inventory.
    static final int MAX_BAGS = 6;

    // Current number of bags in the inventory.
    private int numOfBags;
    // The value of all the items in the inventory.
    private int value;
    // The list of bags in the inventory.
    private List<Bag> inventory;

    /**
     * Constructor for the inventory.
     * @param inventory The list of bags that make up the inventory.
     */
    public Inventory() {
        this.inventory = new ArrayList<Bag>();
        Bag adventurersFirstBag = new Bag();
        this.inventory.add(adventurersFirstBag);
        this.numOfBags = 1;
        this.value = 0;
    }

// ----------- Getters and Setters ------------

    @Override
    public int getValue() {
        for (Bag bag : inventory) {
            value += bag.getValue();
        }
        return value;
    }

    @Override
    public boolean hasSpace() {
        return false;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Bag> getInventory() {
        return inventory;
    }

    public void setInventory(List<Bag> inventory) {
        this.inventory = inventory;
    }

    public int getNumOfBags() {
        return numOfBags;
    }

}
