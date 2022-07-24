package MUD.Model.Map;

import MUD.Model.Character.Player;
import MUD.Model.Inventory.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which represents a chest for the player to loot.
 * Contains its own inventory with which to add to the inventory of the character that opens the chest.
 * @author Nathan Perez
 */
public class Chest implements TileElement, Serializable{

    private boolean isOpen;
    private final String name;
    private final String description;
    private ArrayList<Item> itemList;

    /**
     * Constructor for the Chest class.
     * @param name Name of the Chest object.
     * @param description Description of the Chest object.
     */
    public Chest(String name, String description, ArrayList<Item> itemList){
        this.name = name;
        this.description = description;
        this.isOpen = false;
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return this.name + " | " + this.description + " | " + this.itemList;
    }

    public void open(Player opener) {
        this.isOpen = true;

        for (int index = 0; index < itemList.size(); index++){
            Item item = itemList.remove(index);
            opener.pickupItem(item);
        }

    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public List<Item> getItemList(){
        return this.itemList;
    }

    public void setItemList(ArrayList<Item> itemList){
        this.itemList = itemList;
    }

    public String getName() {
        return name;
    }
}
