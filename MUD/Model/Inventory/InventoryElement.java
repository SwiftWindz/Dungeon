package MUD.Model.Inventory;

/**
 * Interface for an inventory element.
 * @author Phil Ganem
 */
public interface InventoryElement {

    /**
     * Method for getting the value of the inventory element.
     * @return Integer value of the inventory element.
     */
    public int getValue();

    /**
     * Method for determining if the inventory element has space.
     * @return True if the inventory element has space, false otherwise.
     */
    public boolean hasSpace();
    
}
