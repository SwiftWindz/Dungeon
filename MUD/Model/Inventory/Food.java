package MUD.Model.Inventory;

import MUD.Model.Character.Player;

/**
 * The food class is an item that can be eaten by the player to
 * restore health.
 * @author Phil Ganem
 */
public class Food extends Item {

    // The amount of health restored by the food.
    private final double healthRestored;

    /**
     * Constructor for the food class.
     * @param name The name of the food.
     * @param description The description of the food.
     * @param value The gold value of the food.
     * @param health The health restored by the food.
     */
    public Food(String name, String description, int value, double health) {
        super(name, description, value);
        this.healthRestored = health;
    }
    
    @Override
    /**
     * Uses the food to restore the player's health.
     * @param player The player who is using the food.
     */
    public void useItem(Player player){
        player.eat(this);
    }

    public double getHealthRestored() {
        return healthRestored;
    }

}
