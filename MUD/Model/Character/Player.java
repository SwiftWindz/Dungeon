package MUD.Model.Character;

import java.util.ArrayList;
import java.util.List;

import MUD.Model.DayNight.Visitor;
import MUD.Model.Inventory.*;
import MUD.Model.Map.Door;
import MUD.Model.Map.Map;
import MUD.Model.Map.Room;
import MUD.Model.Map.Tile;
import MUD.Model.Map.Trap;
import MUD.View.PTUI;

/**
 * Class for a player character within the game. This class is the 
 * concrete element stereotype for the Visitor pattern. 
 * @author Phil Ganem
 */
public class Player extends Character {

    // The players inventory. Contains bags which contain items.
    private Inventory inventory;
    // The players currently equipped weapon. Increases attack stat.
    private Weapon weaponSlot;
    // The players currently equipped armor. Increases defense stat.
    private Armor armorSlot;
    // The players equipped buffs
    private List<BuffItem> equippedBuffs;
    // The room the player is in
    private Room currentRoom;
    // The tile the player is currently on
    private Tile currentTile;
    // The map the player resides in
    private Map map;
  
    public Player(String name, String description, double maxHealth, double attack, double defense, Map map) {
        super(name, description, maxHealth, attack, defense);
        this.equippedBuffs = new ArrayList<>();
        this.inventory = new Inventory();
        this.map = map;
    }

    public List<BuffItem> getEquippedBuffs() {
        return equippedBuffs;
    }

    /**
     * This method gets equips a buff to the player and 
     * increases a specific stat for 10 turns
     * @param buff this is the buff that is equipped to the
     * player
     */
    public void equipBuff(BuffItem buff) {
        equippedBuffs.add(buff);
        double statIncrease = buff.getStatChange();
        BuffType buffType = buff.getBuffType();
        double totalStat;
        switch (buffType) {
            case ATTACK:
                totalStat = this.getAttack() + statIncrease;
                this.setAttack(totalStat);
                break;
            case DEFENSE:
                totalStat = this.getDefense() + statIncrease; 
                this.setDefense(totalStat);
                break;
            case HEALTH:
                totalStat = this.getMaxHealth() + statIncrease;
                this.setMaxHealth(totalStat);
                totalStat = this.getCurrentHealth() + statIncrease;
                this.setCurrentHealth(totalStat);
                break;

        }
    }

    /**
     * This method removes a buff from the user
     * and decreases the specific stat
     * @param buff the buff that is removed from the user
     */
    public void removeBuff(BuffItem buff) {
        equippedBuffs.remove(buff);
        double statDecrease = buff.getStatChange();
        BuffType buffType = buff.getBuffType();
        double totalStat;
        switch (buffType) {
            case ATTACK:
                totalStat = this.getAttack() - statDecrease;
                this.setAttack(totalStat);
                break;
            case DEFENSE:
                totalStat = this.getDefense() - statDecrease; 
                this.setDefense(totalStat);
                break;
            case HEALTH:
                totalStat = this.getMaxHealth() - statDecrease;
                this.setMaxHealth(totalStat);
                if (totalStat < getCurrentHealth()) {
                    this.setCurrentHealth(totalStat);
                }
                break;
        }
        
    }

    /**
     * This method decreases the turns left for
     * all equipped buffs and if the turns left
     * is 0 then the buff gets removed
     */
    public void decreaseBuffTurnsLeft() {
        for (BuffItem buff: equippedBuffs) {
            buff.decreaseTurnsLeft();
            if (buff.isTurnsLeftZero()) {
                removeBuff(buff);
            }
        }
    }
    /**
     * Equips a weapon to the player, increasing the players attack stat.
     * If player has weapon equipped then  the weapon gets replaced
     * @param weapon this is the weapon to equip to the player
     */
    public void equipWeapon(Weapon weapon) {
        double attack = getAttack();
        if(weaponSlot != null) {
            List<Bag> bags = inventory.getInventory();
            for (Bag bag : bags) {
                if (bag.hasSpace()) {
                    bag.addItem(weaponSlot);
                    continue;
                }
            }
            attack -= weaponSlot.getAttack();            
        }
        weaponSlot = weapon;
        attack += weapon.getAttack();
        setAttack(attack);
     }

    /**
     * Equips an armor piece, modifying the players defense stat.
     * if there is armor already equipped then it replaces the
     * current armor
     * @param armor This is the armor that is going to get
     * equipped to the Player
     */
    public void equipArmor(Armor armor) {
        double defense = getDefense();
        if (armorSlot != null) {
            List<Bag> bags = inventory.getInventory();
            for (Bag bag : bags) {
                if (bag.hasSpace()) {
                    bag.addItem(armorSlot);
                }
            }
            defense -= armorSlot.getDefense();
        }
        armorSlot = armor;
        defense += armor.getDefense();
        setDefense(defense);

    }
 
    public void pickupItem(Item item){

        for (Bag bag : this.inventory.getInventory()){
            //Look for first bag with available space
            if (bag.hasSpace()) {
                bag.addItem(item);
                break;
            }

        }

    }

    /**
     * This methods removes an item
     * from the inventory
     * @param item the item to be removed
     */
    public void removeItemFromInventory(Item item) {
        Inventory inventory = this.getInventory();
        for (Bag bag : inventory.getInventory()) {
            List<Item> items = bag.getItems();
            for (int index = 0; index < items.size(); index++) {
                Item itemInBag = items.get(index);
                if (item == itemInBag) {
                    items.remove(item);
                    return;
                }
            }
        }
    }

    /**
     * Method for a player to eat a food item and have their
     * health restored.
     * @param Food 
     */
    public void eat(Food food) {
        double health = getCurrentHealth();
        health += food.getHealthRestored();
        setCurrentHealth(health);

    }
    
    @Override
    public void accept(Visitor v) {
        v.visitPlayer(this);
    }

    public void setRoom(Room r){
        currentRoom = r;
    }

    public void setTile(Tile t){
        currentTile = t;
    }

    public Room getRoom(){
        return currentRoom;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public List<Tile> getAdjacentTiles(){
        List<Tile> adjacentTiles = new ArrayList<>();
        int currentRow = currentTile.getRow();
        int currentCol = currentTile.getCol();
        adjacentTiles.add(currentRoom.getContents()[currentRow+1][currentCol]);
        adjacentTiles.add(currentRoom.getContents()[currentRow+1][currentCol-1]);
        adjacentTiles.add(currentRoom.getContents()[currentRow][currentCol-1]);
        adjacentTiles.add(currentRoom.getContents()[currentRow-1][currentCol-1]);
        adjacentTiles.add(currentRoom.getContents()[currentRow-1][currentCol]);
        adjacentTiles.add(currentRoom.getContents()[currentRow-1][currentCol+1]);
        adjacentTiles.add(currentRoom.getContents()[currentRow][currentCol+1]);
        adjacentTiles.add(currentRoom.getContents()[currentRow+1][currentCol+1]);
        return adjacentTiles;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public Map getMap() {
        return map;
    }

    public void move(Tile t){
        Door mockDoor = new Door("name", "description", "right");
        Trap mockTrap = new Trap(0, "name", "description");
        if(t.hasEntity(mockDoor)){
            Door door = (Door)t.getOccupant();
            if(currentRoom.isGoal()){
                PTUI.win();
            }
            else if(door.getSide().equals("right")){
                setCurrentRoom(map.getContents()[currentRoom.getRoomNumber() + 1]);
                setCurrentTile(currentRoom.getContents()[4][1]);
            }
            else{
                setCurrentRoom(map.getContents()[currentRoom.getRoomNumber() - 1]);
                setCurrentTile(currentRoom.getContents()[4][7]);
            }
        }
        else if(t.hasEntity(mockTrap)){
            Trap tr = (Trap)t.getOccupant();
            if(!tr.getStatus()){
                setCurrentTile(t);
                tr.setTriggered(true);
                tr.trigger(this);
                t.setContents(".");
                t.setOccupant(null);
            }
        }
        else{
            setCurrentTile(t);
        }
    }

}
