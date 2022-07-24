package MUD.Model.Map;

import MUD.Model.Character.Bedtime;
import MUD.Model.Character.Behavior;
import MUD.Model.Character.NPC;
import MUD.Model.Inventory.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class which represents a given Room in a Map.
 * This class serves as a Concrete Composite class for the Composite pattern.
 * @author Nathan Perez
 */
public class Room implements MapElement, Serializable{
    private final Tile[][] contents;
    private boolean isGoal;
    private int width;
    private int height;
    private String description;
    private final Integer MAX_CHESTS = 3;
    private final Integer MAX_TRAPS = 2;
    private final Integer MAX_OBSTACLES = 5;
    private final Integer MAX_ENEMIES = 5;
    private int roomNumber;

    /**
     * Constructor for the Room class.
     * @param width The width of the room (in Tiles).
     * @param height The height of the room (in Tiles).
     */
    public Room(int width, int height, int num) {
        this.contents = new Tile[height][width];
        this.width = width;
        this.height = height;
        this.roomNumber = num;

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                this.contents[i][j] = new Tile(i, j);
            }
        }

    }

    /**
     * Method to search for a given entity in a Room.
     * @param entity: The entity to be searched for.
     * @return true or false
     */
    @Override
    public boolean hasEntity(Object entity) {
        boolean flag = false;
        for (Tile[] tileRow : contents) {
            for (Tile tile : tileRow) {
                flag = tile.hasEntity(entity);
            }
        }
        return flag;
    }

    @Override
    public boolean isGoal() {
        return this.isGoal;
    }

    public void setGoal(boolean isGoal) {
        this.isGoal = isGoal;
    }

    @Override
    public String toString() {
        return this.description;
    }

    public void setDescription(String bodyText) {
        this.description = bodyText;
    }

    /**
     * Method to search for the total instances of a given entity in an entire Room.
     * @param entity: The entity to be searched for.
     * @return true or false
     */
    public int getRemainingEntities(Object entity) {
        int count = 0;
        for (Tile[] tileRow : contents) {
            for (Tile tile : tileRow) {
                if (tile.hasEntity(entity)) {
                    count++;
                }
            }
        }
        return count;
    }

    public void populateTraps() {
        int count = 0;

        while (count < MAX_TRAPS) {
            for (Tile[] tileRow : contents) {
                Trap dummyTrap = new Trap(1, "Dummy Trap", "A default trap even the" + " lowest of adventurers could defeat.");

                Random r = new Random();
                int randomWidth = r.nextInt(this.width);
                /*Select random tile within the current Tile row*/
                if (tileRow[randomWidth].getContents().equals(".")) {
                    tileRow[randomWidth].setOccupant(dummyTrap);
                    count++;
                }

            }

        }
    }
    public void populateObstacles(Obstacle obstacle) {
        int count = 0;

        while (count < MAX_OBSTACLES) {
            for (Tile[] tileRow : contents) {

                Random r = new Random();
                int randomWidth = r.nextInt(this.width);
                /*Select random tile within the current Tile row*/
                if (tileRow[randomWidth].getContents().equals(".")) {
                    tileRow[randomWidth].setContents("O");
                    tileRow[randomWidth].setOccupant(obstacle);
                    count++;
                }

            }

        }
    }
    public void populateEnemies() {
        int count = 0;

        while (count < MAX_ENEMIES) {

            for (Tile[] tileRow : contents) {

                Random r = new Random();
                int randomWidth = r.nextInt(this.width);
                /*Select random tile within the current Tile row*/
                if (tileRow[randomWidth].getContents().equals(".")) {
                    NPC enemy = generateRandomEnemy();
                    tileRow[randomWidth].setContents("\033[0;31mE\033[0m");
                    tileRow[randomWidth].setOccupant(enemy);
                    enemy.setHome(tileRow[randomWidth]);
                    count++;
                }

            }

        }
    }
    public void populateChests(Chest chest) {
        int count = 0;

        while (count < MAX_CHESTS) {
            for (Tile[] tileRow : contents) {

                Random r = new Random();
                int randomWidth = r.nextInt(this.width);
                /*Select random tile within the current Tile row*/
                if (tileRow[randomWidth].getContents().equals(".")) {
                    tileRow[randomWidth].setContents("C");
                    tileRow[randomWidth].setOccupant(chest);
                    count++;
                }

            }

        }
    }

    /**
     * Method which randomly adds a specified TileElement into each tile of a room,
     * up until the MAX_ENTITY bound is met.
     */
    public void populate(){
        
        Obstacle dummyObstacle = new Obstacle("Stick in the mud", "It's a stick in the mud. In M.U.D.");

        ArrayList<Item> dummyList = generateChestLoot();
        Chest dummyChest = new Chest("Wooden chest", "A wooden chest containing one item.", dummyList);

        populateChests(dummyChest);
        populateEnemies();
        populateObstacles(dummyObstacle);
        populateTraps();

        setBarriers(); //create borders of room MUST happen after initial Obstacle population
        setDoors();

    }

    /**
     * Method which completely overwrites the border of the room with "barrier" obstacles, so long as the overwritten
     * tile is not already designated as a door.
     */
    public void setBarriers(){

        Obstacle barrier = new Obstacle("Wall.", "The walls of the room.");

        for (int i = 0; i < this.width; i++) {

            if (!this.contents[0][i].getContents().equals("\033[0;33mD\033[0m")) { //If the content of the current tile is NOT a door to another room
                this.contents[0][i].setContents("O");//establish the border for the north wall
                this.contents[0][i].setOccupant(barrier);
            }
            if (!this.contents[this.height - 1][i].getContents().equals("\033[0;33mD\033[0m")){
                this.contents[this.height - 1][i].setContents("O"); //establish the border for the southern wall
                this.contents[this.height - 1][i].setOccupant(barrier);
            }
        }

        for (int i = 0; i < this.height ; i++){ //establish the border for the western wall

            if (!this.contents[i][0].getContents().equals("\033[0;33mD\033[0m")) {
                this.contents[i][0].setContents("O");
                this.contents[i][0].setOccupant(barrier);
            }
            if (!this.contents[i][this.width - 1].getContents().equals("D")) { //establish the border for the eastern wall
                this.contents[i][this.width - 1].setContents("O");
                this.contents[i][this.width - 1].setOccupant(barrier);
            }
        }

    }

    /**
     * Method which sets two doors in a room at two predefined locations, on the eastern and western walls of the
     * room, respectively. [(4,0), (0,8)]
     */
    public void setDoors(){

        //Hardcoded so that every door is either at (4, 0), (0, 8), or both in the 9x9 Rooms, which are also hard-coded.
        Door rDoor = new Door("Door.", "A sharp metal door. Heavy, but able to be opened.", "right");
        Door lDoor = new Door("Door.", "A sharp metal door. Heavy, but able to be opened.", "left");
        this.contents[4][1].setContents("\033[0;33mD\033[0m");//Left-most door
        this.contents[4][1].setOccupant(lDoor);

        this.contents[4][7].setContents("\033[0;33mD\033[0m");//Right-most door
        this.contents[4][7].setOccupant(rDoor);


    }

    /**
     * Method which completely overwrites the door at a specified Tile, replacing it with an impassable obstacle.
     * @param row - the row at which the replaced Tile is located
     * @param col - the col at which the replaced Tile is located
     */
    public void clearDoors(int row, int col){

        Obstacle obstacle = new Obstacle("Wall.", "The walls of the room.");
        this.contents[row][col].setContents("O");
        this.contents[row][col].setOccupant(obstacle);

    }

    public Tile[][] getContents() {
        return contents;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public NPC generateRandomEnemy(){
        /**
         * Method which randomly generates an enemy to populate the board with.
         * @return a random NPC Enemy
         */
        //Slime, Bobby St. Jacques, Garfield, Bread, Orc
        NPC[] arr = new NPC[]{new NPC("Garfield", "A very angry cat.", 50, 5,0, Behavior.Hostile, Bedtime.Diurnal),
                new NPC("Bobby St. Jacques", "Beloved leader.", 100, 10,3, Behavior.Hostile, Bedtime.Diurnal),
                new NPC("Bread", "The moldy bits are sentient.", 50, 5,10, Behavior.Hostile, Bedtime.Diurnal),
                new NPC("Slime", "A blue ball of goop.", 75, 5, 5, Behavior.Hostile, Bedtime.Nocturnal),
                new NPC("Orc", "A big, angry, green guy with horns and an axe.", 110,20,0,Behavior.Hostile, Bedtime.Diurnal),
                new NPC("Phil", "Literally the weakest guy in the game", 5,25,5,Behavior.Hostile, Bedtime.Nocturnal),
                new NPC("Banana", "That banana has a gun!", 25, 10,0, Behavior.Hostile, Bedtime.Diurnal),
                new NPC("Bat", "You cant see me.", 15, 10,3, Behavior.Hostile, Bedtime.Nocturnal),
                new NPC("The Devil", "Just here to play rock and roll", 100, 25,3, Behavior.Hostile, Bedtime.Nocturnal),
                new NPC("The Wizard", "He's a 'wizard.' Magic's not real, idiot", 25, 25,3, Behavior.Hostile, Bedtime.Diurnal),
                new NPC("Elve", "He's a 'wizard.' Magics not real, idiot", 25, 25,3, Behavior.Hostile, Bedtime.Diurnal),
                new NPC("Imposter", "Wrong game", 10, 5,3, Behavior.Hostile, Bedtime.Diurnal),
                new NPC("Gnome", "Just wants to protect his garden", 15, 5,10, Behavior.Hostile, Bedtime.Diurnal),
                new NPC("Goblin", "There is no depth of casual cruelty or random violence to which a Goblin will not sink, if it senses that is has the power to do so",
                5, 5, 0, Behavior.Hostile, Bedtime.Nocturnal),
                new NPC("Dwarf", "Just minin'", 2, 10,15, Behavior.Hostile, Bedtime.Diurnal),
                };

        int rnd = new Random().nextInt(arr.length);

        return arr[rnd];
    }

    public ArrayList<Item> generateChestLoot(){
        Weapon wetNapkin = new Weapon("A wet napkin","It's a napkin. It's wet.", 1, 1);
        ArrayList<Item> lootList = new ArrayList<>();

        Item[] possibleItems = new Item[]{wetNapkin,
                wetNapkin,
                wetNapkin,
                new Weapon("Toy Dagger","It makes a cute squeak with every hit.", 2, 50),
                new BuffItem("Health Potion", "A swirling yellow elixir, brewed with hope.", 200, BuffType.HEALTH, 20),
                new BuffItem("Strength Potion", "A swirling red elixir, brewed with determination.", 150, BuffType.ATTACK, 20),
                new Armor("Steel bucket.", "A hard helmet, perfect for the new adventurer.", 3, 30),
                new Armor("Adamantine Chestplate", "A Chestplate made of Adamantine. Nearly indestructible.", 20, 3000),
                new Armor("Steel Chestplate", "A Chestplate made of steel.", 8, 1300),
                new Armor("Leather Chestplate", "A Chestplate made of leather.", 5, 500),
                new Armor("Leather Helmet", "A helmet made of leather.", 3, 300),
                new Weapon("Adamantine Sword", "A sword made of Adamantine. Nearly indestructible.", 20, 3000),
                new Weapon("Steel Axe", "A axe made of Steel.", 10, 150),
                new Weapon("Steel Sword", "A sword made of Steel.", 10, 100),
                new Weapon("Steel Dagger", "A dagger made of Steel.", 5, 50),
                new Weapon("Gold Broadsword", "A Broadsword made of gold.", 13, 1000),
                new Weapon("Gold Sword", "A sword made of gold.", 10, 500),
                new Weapon("Gold Dagger", "A dagger made of gold.", 5, 250),
                new Weapon("Broken pasta noodle.","Worthless. More worthless than worthless.", 0, 0),
                new Weapon("Blade of Quel'delar","The strongest blade in the world", 100, 50000),
                new Weapon("Sword of the Dark Knight","The (second) strongest blade in the world", 20, 1000),
                new Food("Hot Dog Water", "Smells good :)", 111, 50),
                new Food("Apple", "Red and juicy", 10, 5),
                new Food("Banana", "Yellow and juicy", 10, 5),
                new Food("Cookie", "A chocolate chip cookie", 10, 5),
                new Food("Ham", "Ham", 10, 5),
                new Food("Cheese", "Cheese", 10, 5),
                new Food("Chicken Leg", "KFC", 10, 5),

            };

        int size = new Random().nextInt(5);

        for (int i = 0; i < size; i++){
            Item randomItem = possibleItems[new Random().nextInt(possibleItems.length)];

            lootList.add(randomItem);

        }

        return lootList;
    }

}