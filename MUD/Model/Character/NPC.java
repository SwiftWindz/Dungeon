package MUD.Model.Character;

import MUD.Model.DayNight.Visitor;
import MUD.Model.Map.Tile;

/**
 * Class for an NPC within the game. This class is the 
 * concrete element stereotype for the Visitor pattern. 
 * @author Phil Ganem
 */
public class NPC extends Character {

    // The behavior of the NPC. This determines how the NPC 
    //acts around the player character.
    private Behavior behavior;
    // The bedtime of the NPC. Determines which state of the 
    // day/night cycle will buff/debuff the NPC.
    private final Bedtime bedtime;

    private Tile home;

    private String sprite;

    /**
     * Constructor for an NPC.
     * @param name The name of the NPC.
     * @param description The description of the NPC.
     * @param maxHealth The maximum health of the NPC.
     * @param attack The attack stat of the NPC.
     * @param defense The defense stat of the NPC.
     * @param behavior The behavior of the NPC.
     * @param bedtime The bedtime of the NPC.
     */
    public NPC(String name, String description, double maxHealth, double attack,
    double defense, Behavior behavior, Bedtime bedtime, String sprite) {
       
        super(name, description, maxHealth, attack, defense);
        this.behavior = behavior;
        this.bedtime = bedtime;
        this.home = null;
        this.sprite = sprite;

    }

    @Override
    public void accept(Visitor v) {
        v.visitNPC(this);
    }

// ----------- Getters and Setters ------------

    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public Bedtime getBedtime() {
        return bedtime;
    }

    public void setHome(Tile home) {
        this.home = home;
    }

    public Tile getHome() {
        return home;
    }

    public String getSprite() {
        return sprite;
    }
    
}
    
