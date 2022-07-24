package MUD.Model.Map;

import MUD.Model.Inventory.BuffItem;
import MUD.Model.Inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class PlayerState {
    private String name;
    private String description;
    private double maxHealth;
    private double attack;
    private double defense;
    private Inventory inventory;
    private List<BuffItem> equippedBuffs;
    private Map map;

    public PlayerState(String name, String description, double maxHealth, double attack, double defense, Map map) {
        this.name = name;
        this.description = description;
        this.maxHealth = maxHealth;
        this.attack = attack;
        this.defense = defense;
        this.equippedBuffs = new ArrayList<>();
        this.inventory = new Inventory();
        this.map = map;
    }


// ----------- Getters ------------

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefense() {
        return defense;
    }

    public Inventory getInventory(){return inventory;}

    public List<BuffItem> getEquippedBuffs() {
        return equippedBuffs;
    }

    public Map getMap() {return map;}
}
