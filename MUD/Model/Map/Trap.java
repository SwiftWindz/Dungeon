package MUD.Model.Map;

import java.io.Serializable;

/**
 * Class which represents a Trap that can damage a character upon activation. A character has a 50% chance to notice the trap
 * before they step on the trapped tile, and a 50% chance to disarm said trap, else they are attacked.
 *
 * @author Nathan Perez
 */

import java.util.Random;

import MUD.Model.Character.Character;

public class Trap implements TileElement, Serializable {
    private final int attack;
    private boolean isTriggered;
    private final String name;
    private final String description;
    private boolean isDisarmed;
    private boolean disarmAttempted;

    /**
     * Constructor for the Trap class.
     * @param name Name of the Trap object.
     * @param description Description of the Trap object.
     * @param attack Attack of the Trap object.
     */
    public Trap(int attack, String name, String description) {
        this.attack = attack;
        this.name = name;
        this.description = description;
        this.isTriggered = false;
        this.isDisarmed = false;
        this.disarmAttempted = false;
    }

    /**
     * Method that triggers an attack on the character that triggered the trap.
     * @param character The character that triggered the trap.
     */
    public void trigger(Character character) {
        // Calls the takeDamage method on the character being attacked.
        if (isTriggered && this.isDisarmed == false) character.takeDamage(this.getAttack());
        // If the trap is disarmed, the trap is no longer triggered.
        // this.isDisarmed = true;
    }

    private int getAttack() {
        return this.attack;
    }

    public String getName() {
        return name;
    }

    public void setTriggered(boolean isTriggered) {
        this.isTriggered = isTriggered;
    }

    @Override
    public String toString() {
        return this.name + " | " + this.description;
    }

    public boolean getStatus(){
        return isDisarmed;
    }

    public boolean getDisarmable(){
        return !disarmAttempted;
    }

    /**
     * Method that handles the random chance of disarming a trap,
     * and the results of a failure/success.
     * @param disarmer The character that stepped on the trap.
     */
    public void disarm(Character disarmer) {
        disarmAttempted = true;
        Random r = new Random();
        int randomInt = r.nextInt(2);
        boolean disarmSuccess = (randomInt % 2 == 0);

        if (disarmSuccess) {
            this.isTriggered = false;
            this.isDisarmed = true;
        } else {
            this.isTriggered = true;
            trigger(disarmer);
        }

    }

}
