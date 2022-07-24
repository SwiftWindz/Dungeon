package MUD.Model.Map;

import java.io.Serializable;
import java.util.Random;


/**
 * Class which represents a given Room in a Map.
 * This class serves as a Concrete Composite class for the Composite pattern.
 * @author Nathan Perez
 */
public class Room implements MapElement, Serializable{
    private Tile[][] contents;
    private boolean isGoal;
    private int width;
    private int height;
    private String description;
    private final Integer MAX_CHESTS = 3;
    private final Integer MAX_TRAPS = 4;
    private final Integer MAX_OBSTACLES = 10;
    private final Integer MAX_ENEMIES = 5;
    /**
     * Constructor for the Room class.
     * @param width The width of the room (in Tiles).
     * @param height The height of the room (in Tiles).
     */
    public Room(int width, int height, int num) {
        this.contents = new Tile[height][width];
        this.width = width;
        this.height = height;

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

    /**
     * method to set traps within a room using the TrapTileMaker
     */
    public void populateTraps() {
        TrapTileMaker trapMaker = new TrapTileMaker();
        Random random = new Random();
        int numTraps = random.nextInt(MAX_TRAPS + 1);
        for (int i = 0; i < numTraps; ++i){
            int c = random.nextInt(this.width);
            int r = random.nextInt(this.height);
            Tile randomTile = this.contents[r][c];
                if (randomTile.getContents().equals(".")) {
                    this.contents[r][c] = trapMaker.makeTile(r, c);
                }
                else 
                    --i; //lowers i so that an extra pass happens since it was unable to place at the last spot
        }
    }

    /**
     * method to set a merchant within a room using the MerchantTileMaker
     */
    public void populateMerchant() {
        MerchantTileMaker merchantMaker = new MerchantTileMaker();
        Random random = new Random();
        while(true){
            int c = random.nextInt(this.width);
            int r = random.nextInt(this.height);
            Tile randomTile = this.contents[r][c];
                if (randomTile.getContents().equals(".")) {
                    this.contents[r][c] = merchantMaker.makeTile(r, c);
                    break;
                }
        }
    }

    /**
     * method to set obstacles within a room using the ObstacleTileMaker
     */
    public void populateObstacles() {
        ObstacleTileMaker obsMaker = new ObstacleTileMaker();
        Random random = new Random();
        int numObs = random.nextInt(3, MAX_OBSTACLES);
        numObs += 1;
        for (int i = 0; i < numObs; ++i){
            int c = random.nextInt(this.width);
            int r = random.nextInt(this.height);
            Tile randomTile = this.contents[r][c];
                if (randomTile.getContents().equals(".")) {
                    this.contents[r][c] = obsMaker.makeTile(r, c);
                }
                else 
                    --i; //lowers i so that an extra pass happens since it was unable to place at the last spot
        }
    }

    /**
     * method to set enemies within a room using the EnemyTileMaker
     */
    public void populateEnemies() {
        EnemyTileMaker enemyMaker = new EnemyTileMaker();
        Random random = new Random();
        int numEnemy = random.nextInt(1, MAX_ENEMIES);
        for (int i = 0; i < numEnemy; ++i){
            int c = random.nextInt(this.width);
            int r = random.nextInt(this.height);
            Tile randomTile = this.contents[r][c];
                if (randomTile.getContents().equals(".")) {
                    this.contents[r][c] = enemyMaker.makeTile(r, c);
                }
                else 
                    --i; //lowers i so that an extra pass happens since it was unable to place at the last spot
        }
    }

    /**
     * method to set chests within a room using the ChestTileMaker
     */
    public void populateChests() {
        ChestTileMaker chestMaker = new ChestTileMaker();
        Random random = new Random();
        int numChests = ((random.nextInt(MAX_CHESTS * 10) + 7) / 10);
        for (int i = 0; i < numChests; ++i){
            int c = random.nextInt(this.width);
            int r = random.nextInt(this.height);
            Tile randomTile = this.contents[r][c];
                if (randomTile.getContents().equals(".")) {
                    this.contents[r][c] = chestMaker.makeTile(r, c);
                }
                else 
                    --i; //lowers i so that an extra pass happens since it was unable to place at the last spot
        }
    }

    /**
     * method to set A Shrine within a room using the ShrineTileMaker
     */
    public void populateShrine() {
        ShrineTileMaker shrineMaker = new ShrineTileMaker();
        Random random = new Random();
        while(true){
            int c = random.nextInt(this.width);
            int r = random.nextInt(this.height);
            Tile randomTile = this.contents[r][c];
                if (randomTile.getContents().equals(".")) {
                    this.contents[r][c] = shrineMaker.makeTile(r, c);
                    break;
                }
        }
    }
    

    /**
     * Method which randomly adds a specified TileElement into each tile of a room,
     * up until the MAX_ENTITY bound is met.
     */
    public void populate(){
        
        populateChests();
        populateEnemies();
        populateObstacles();
        populateTraps();
        Random random = new Random();
        if (random.nextInt(7) == 5){
        populateMerchant();
        }
        

        setBarriers(); //create borders of room MUST happen after initial Obstacle population
        setDoors();
    }

    /**
     * The populate method for use with Endless mode, adds shrine chance to generate in the room
     */
    public void populateEndless(){
        
        populateChests();
        populateEnemies();
        populateObstacles();
        populateTraps();
        Random random = new Random();
        if (random.nextInt(7) == 5){ // 1/6 chance of a merchant in a room
        populateMerchant();
        }
        if (random.nextInt(11) == 8){  // 1/10 chance for a shrine in each room
            populateShrine();
        }

        setBarriers(); //create borders of room MUST happen after initial Obstacle population
        setDoors();
    }

    /**
     * Method for populating a room to be used a starting room
     * A starting room only contains a merchant and a shrine
     */
    public void populateStart(){
        for (Tile[] tileRow : contents) {
            for (Tile tile : tileRow) {
                if (tile.getContents() != "D" && tile.getContents() != "O"){
                    tile.setContents(".");
                    tile.setOccupant(null);
                }
            }
        }
        populateMerchant();
        populateShrine();
        
        setBarriers();
        setDoors();
    }

    /**
     * Method which completely overwrites the border of the room with "barrier" obstacles, so long as the overwritten
     * tile is not already designated as a door.
     */
    public void setBarriers(){

        Obstacle barrier = new Obstacle("Wall.", "The walls of the room.");

        for (int i = 0; i < this.width; i++) {

            if (!this.contents[0][i].getContents().equals("D")) { //If the content of the current tile is NOT a door to another room
                this.contents[0][i].setContents("O");//establish the border for the north wall
                this.contents[0][i].setOccupant(barrier);
            }
            if (!this.contents[this.height - 1][i].getContents().equals("D")){
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
     * Method which sets four doors in the room, one on each wall.
     */
    public void setDoors(){

        //Hardcoded so that every door is in the middle of their respective wall in the 9x9 Rooms, which are also hard-coded.
        Door rDoor = new Door("Door.", "A sharp metal door. Heavy, but able to be opened.", Direction.Right);
        Door lDoor = new Door("Door.", "A sharp metal door. Heavy, but able to be opened.", Direction.Left);
        Door uDoor = new Door("Door.", "A sharp metal door. Heavy, but able to be opened.", Direction.Up);
        Door dDoor = new Door("Door.", "A sharp metal door. Heavy, but able to be opened.", Direction.Down);
        
        
        this.contents[4][1].setContents("D");//Left-most door
        this.contents[4][1].setOccupant(lDoor);

        this.contents[4][7].setContents("D");//Right-most door
        this.contents[4][7].setOccupant(rDoor);

        this.contents[1][4].setContents("D");//Upper door
        this.contents[1][4].setOccupant(uDoor);

        this.contents[7][4].setContents("D");//Lower door
        this.contents[7][4].setOccupant(dDoor);
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

    public void setContents(Tile[][] contents) {
        this.contents = contents;
    }
}