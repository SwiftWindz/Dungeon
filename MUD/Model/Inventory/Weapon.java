package MUD.Model.Inventory;

import MUD.Model.Character.Player;

/**
 * The weapon class for the player.
 * @author Phil Ganem
 */
public class Weapon extends Item  {

    // The weapon's attack value that it provides to the player
    // when used.
    private double attack;

    /**
     * Constructor for the weapon.
     * @param name The name of the weapon.
     * @param description The description of the weapon.
     * @param value The gold value of the weapon.
     * @param attack The attack value of the weapon.
     */
    public Weapon(String name, String description, double attack, int value) {
        super(name, description, value);
        this.attack = attack;
    }

    @Override
    public void useItem(Player player){
        player.equipWeapon(this);
    }
    
// ----------- Getters and Setters ------------

    /**
     * Method for getting the attack value of the weapon.
     * @return The attack value of the weapon.
     */
    public double getAttack(){
        return this.attack;
    }
}
