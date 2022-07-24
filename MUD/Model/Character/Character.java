package MUD.Model.Character;

import java.io.Serializable;

import MUD.Model.DayNight.Visitor;

/**
 * Abstract class for a character within the game. This class is the 
 * element stereotype for the Visitor pattern. 
 * @author Phil Ganem
 */
public abstract class Character implements Serializable {

    // Name of the character. This is displayed to the user
    private String name; 
    // Description of the character. This is displayed to the user
    private String description;
    // The maximum health of the character. The character cannot have more health than this.
    private double maxHealth;
    // The current health of the character. This is modified by damage and healing.
    // if the character's health is less than or equal to 0, the character is dead.
    private double currentHealth;
    // The attack stat of the character. This is used to determine
    // the damage inflicted on another character. This can be modified
    // by a weapon (Only available to the player).
    private double attack;
    // The attack value of the character before debuffs and buffs.
    private final double trueAttack;
    // The defense stat of the character. This is used to determine have 
    // much damage is reduced when attacked by another character. This can be
    // modified by armor (Only available to the player) or the time of day 
    // (Only available to NPCs).
    private double defense;
    // A boolean value that determines if the character is alive or dead.
    private boolean defeated;

    /**
     * Constructor for the character.
     * @param name The name of the character.
     * @param description The description of the character.
     * @param maxHealth The maximum health of the character.
     * @param attack The attack stat of the character.
     * @param defense The defense stat of the character.
     */
    public Character(String name, String description, double maxHealth, double attack, double defense) {
        this.name = name;
        this.description = description;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.attack = attack;
        this.trueAttack = attack;
        this.defense = defense;
        this.defeated = false;
    }

    /**
     * Method for one character to apply damage to another.
     * @param chr: The character to apply damage to.
     * @return void
     */
    public void attack(Character chr){
        // Calls the takeDamage method on the character being attacked.
        chr.takeDamage(this.getAttack());
        System.out.println(this.getName() + " has attacked " + chr.getName() + " for " + this.getAttack() + " damage!");
        if(chr instanceof Player){return;}

    }

    /**
     * Method for reducing a character's health upon taking damage.
     * @param trueDamageTaken The amount of damage to be taken before modifications.
     * @return void
     */
    public void takeDamage(double trueDamageTaken) {
        // Calculate the damage taken after defense
        double damageTaken;
        if(this.defense >= trueDamageTaken){damageTaken = 0;} 
        else{damageTaken = trueDamageTaken - this.defense;}
        // Sets the current health to the current health minus the damage taken.
        double newHealth = this.currentHealth - damageTaken;
        setCurrentHealth(newHealth);
        // If the current health is less than or equal to 0, the character is defeated.
        if(this.currentHealth <= 0){this.defeated = true;}
        
    }

    /**
     * Method for determining whether or not a character is defeated.
     * @return boolean: true if defeated, false if not defeated.
     */
    public boolean isDefeated() {
        return this.defeated;
    }
    
    /**
     * Method for having a visitor use a method on a character.
     * @param v Visitor object.
     * @return void
     */
    public abstract void accept(Visitor v);


// ----------- Getters and Setters ------------

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(double currentHealth) {
        this.currentHealth = currentHealth;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;

    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    public double getTrueAttack() {
        return trueAttack;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}