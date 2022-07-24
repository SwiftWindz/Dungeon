package MUD.Model.Inventory;

import MUD.Model.Character.Player;

/**
 * The class for a players armor.
 * @author Phil Ganem
 */
public class Armor extends Item{

    // The armor's defense value that it provides to the player 
    // when worn.
    private double defense;

    /**
     * Constructor for the armor.
     * @param name The name of the armor.
     * @param description The description of the armor.
     * @param value The gold value of the armor.
     * @param defense The defense value of the armor.
     */
    public Armor(String name, String description, double defense, int value) {
        super(name, description, value);
        this.defense = defense;
    }

    @Override
    public void useItem(Player player){
        player.equipArmor(this);
    }
    
    /**
     * Method for getting the defense value of the armor.
     * @return The defense value of the armor.
     */
    public double getDefense(){
        return this.defense;
    }
}
