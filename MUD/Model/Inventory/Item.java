package MUD.Model.Inventory;

import java.io.Serializable;

import MUD.Model.Character.Player;

/**
 * The Item class is the leaf stereotype for the composite pattern.
 * @author Phil Ganem
 */
public class Item implements InventoryElement, Serializable {

    // The name of the item.
    private final String name;
    // The description of the item.
    private final String description;
    // The gold value of the item.
    private final int value;

    /**
     * Constructor for the item.
     * @param name The name of the item.
     * @param description The description of the item.
     * @param value The gold value of the item.
     */
    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public void useItem(Player player){
        System.out.println("Error, this should be overriden by the item type's individual version.");
    }

// ----------- Getters and Setters ------------

    @Override
    public boolean hasSpace() {
        return false;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
