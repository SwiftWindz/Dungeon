package MUD.Model.Character;

import java.util.ArrayList;
import java.util.List;

import MUD.Model.DayNight.Visitor;
import MUD.Model.Inventory.*;
import MUD.Model.Map.Direction;
import MUD.Model.Map.Door;
import MUD.Model.Map.Map;
import MUD.Model.Map.Room;
import MUD.Model.Map.Tile;
import MUD.Model.Map.Trap;
import MUD.Model.Map.Vertex;
import MUD.Model.Map.*;
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
    private Vertex<Room> currentVertex;
    // The tile the player is currently on
    private Tile currentTile;
    // The map the player resides in
    private Map map;
    //total monsters slain on player
    private int totalMonstersSlain;
    //total gold on player
    private Integer gold;
    //total items found on player
    private int totalItemsFound;

    private boolean hasPrayed;
  
    public Player(String name, String description, double maxHealth, double attack, double defense, Map map) {
        super(name, description, maxHealth, attack, defense);
        this.equippedBuffs = new ArrayList<>();
        this.inventory = new Inventory();
        this.map = map;
        this.totalMonstersSlain = 0;
        this.gold = 0;
        this.totalItemsFound = 0;
    }

    public Player(Player player) {
        super(player.getName(), player.getDescription(), player.getMaxHealth(), player.getMaxHealth(), player.getDefense());
            this.equippedBuffs = player.equippedBuffs;
            this.inventory = player.inventory;
            this.map = new Map(2, null);
            this.currentVertex = player.currentVertex;
            this.currentTile = player.currentTile;
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
                this.totalItemsFound++;
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

    public void setVertex(Vertex<Room> r){
        currentVertex = r;
    }

    public void setTile(Tile t){
        currentTile = t;
    }

    public Vertex<Room> getCurrentVertex() {
        return currentVertex;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public List<Tile> getAdjacentTiles(){
        List<Tile> adjacentTiles = new ArrayList<>();
        int currentRow = currentTile.getRow();
        int currentCol = currentTile.getCol();
        adjacentTiles.add(currentVertex.getValue().getContents()[currentRow+1][currentCol]);
        adjacentTiles.add(currentVertex.getValue().getContents()[currentRow+1][currentCol-1]);
        adjacentTiles.add(currentVertex.getValue().getContents()[currentRow][currentCol-1]);
        adjacentTiles.add(currentVertex.getValue().getContents()[currentRow-1][currentCol-1]);
        adjacentTiles.add(currentVertex.getValue().getContents()[currentRow-1][currentCol]);
        adjacentTiles.add(currentVertex.getValue().getContents()[currentRow-1][currentCol+1]);
        adjacentTiles.add(currentVertex.getValue().getContents()[currentRow][currentCol+1]);
        adjacentTiles.add(currentVertex.getValue().getContents()[currentRow+1][currentCol+1]);
        return adjacentTiles;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setCurrentVertex(Vertex<Room> currentVertex) {
        this.currentVertex = currentVertex;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void move(Tile t){
        Door mockDoor = new Door("name", "description", Direction.Right);
        Trap mockTrap = new Trap(0, "name", "description");
        if(t.isGoal()){
            PTUI.win();
        }
        if(t.hasEntity(mockDoor)){
            Door door = (Door)t.getOccupant();
            int[] coords = currentVertex.getCoordinate();
            if(door.getSide().equals(Direction.Right)){
                int[] tcoords = {coords[0] + 1, coords[1]};
                setCurrentVertex(currentVertex.getVertexAtCoordinate(tcoords));
                setCurrentTile(currentVertex.getValue().getContents()[4][1]);
                map.generateNeighbors(currentVertex);
            }
            else if(door.getSide().equals(Direction.Left)){
                int[] tcoords = {coords[0] - 1, coords[1]};
                setCurrentVertex(currentVertex.getVertexAtCoordinate(tcoords));
                setCurrentTile(currentVertex.getValue().getContents()[4][7]);
                map.generateNeighbors(currentVertex);
            }
            else if(door.getSide().equals(Direction.Up)){
                int[] tcoords = {coords[0], coords[1] + 1};
                setCurrentVertex(currentVertex.getVertexAtCoordinate(tcoords));
                setCurrentTile(currentVertex.getValue().getContents()[7][4]);
                map.generateNeighbors(currentVertex);
            }
            else if(door.getSide().equals(Direction.Down)){
                int[] tcoords = {coords[0], coords[1] - 1};
                setCurrentVertex(currentVertex.getVertexAtCoordinate(tcoords));
                setCurrentTile(currentVertex.getValue().getContents()[1][4]);
                map.generateNeighbors(currentVertex);
            }
            map.populate();
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
    public void setGold(Integer amount){
        this.gold = amount;
    }

    public Integer getGold(){
        return this.gold;
    }

    public void buyItem(Item item){
        this.pickupItem(item);
        this.gold -= item.getValue();
    }

    public void sellItem(Item item, Merchant buyer){
        buyer.buyItem(item);
        removeItemFromInventory(item);
        this.gold += (Integer) (item.getValue() / 2);
    }

    public PlayerState createState(){
        //I actually don't know if this is the proper way to get the player attributes since it's a subclass
        return new PlayerState(this.getName(), this.getDescription(), this.getMaxHealth(), this.getAttack(), this.getDefense(), this.map);
    }

    public void pray(Shrine shrine){
        Shrine.currentState = createState();
        this.hasPrayed = true;
        shrine.setPrayerStatus(true);
        Shrine.room = currentVertex;
    }

    public void restore(){
        currentVertex = Shrine.room;

    }

    public boolean getPrayed(){
        return this.hasPrayed;
    }

    public int getTotalMonstersSlain() {
        return totalMonstersSlain;
    }

    public void setTotalMonstersSlain(int totalMonstersSlain) {
        this.totalMonstersSlain = totalMonstersSlain;
    }

    public int getTotalItemsFound() {
        return totalItemsFound;
    }

    public void setTotalItemsFound(int totalItemsFound) {
        this.totalItemsFound = totalItemsFound;
    }

}
