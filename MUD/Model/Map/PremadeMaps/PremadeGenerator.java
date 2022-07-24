package MUD.Model.Map.PremadeMaps;

import MUD.Model.Character.Bedtime;
import MUD.Model.Character.Behavior;
import MUD.Model.Character.NPC;
import MUD.Model.Inventory.*;
import MUD.Model.Map.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class PremadeGenerator {
    private final DiffEnum difficulty;
    private File[] files;
    public PremadeGenerator(DiffEnum difficulty){

        this.difficulty = difficulty;
    }

    public Map generate() throws FileNotFoundException {
        Map generatedMap;

        switch (this.difficulty) {
            case EASY -> {
                this.files = new File("MUD\\Model\\Map\\PremadeMaps\\Easy").listFiles();
                Room[] generatedContents = new Room[]{//Initialize the room with room numbers and size, read into it later
                        new Room(9,9,1),
                        new Room(9,9,2),
                        new Room(9,9,3)};
                int roomIndex = 0; // room index variable, this will only be incremented after we have traversed
                                    // through the entire character array

                for (File file : this.files){//For each file in the subdirectory
                    char[][] temp = new char[9][9];//temp array to store read contents in before passing them to a room Object
                    Scanner in = new Scanner(file);
                    int tempIndex = 0; //index variable used to traverse temp array

                    while (in.hasNextLine()){
                        String line = in.nextLine();
                        char[] lineArr = line.toCharArray();
                        temp[tempIndex] = lineArr;
                        tempIndex++;
                    }
                    //at this point temp should have all the room contents as a 2d array of chars
                    Tile[][] contents = new Tile[9][9]; //array to feed setContents() method

                    for (int i = 0; i < contents.length; i++){//for every Tile object in contents
                        for (int j = 0; j < contents[0].length; j++){

                            Tile tempTile = new Tile(i, j, Character.toString(temp[i][j]));//get string representation of tile contents for new constructor
                            tempTile.setOccupant(returnOccupantFromContents(i, j, tempTile.getContents()));//set occupant based on contents
                            contents[i][j] =  tempTile; // populate with prepared tiles

                        }
                    }

                    generatedContents[roomIndex].setContents(contents);//each room gets its contents set here
                    generatedContents[roomIndex].populateTraps();//done here since the occupant of Trap tiles is hidden initially
                    roomIndex++;
                    in.close();

                }

                generatedMap = new Map(3, generatedContents[0]);
                generatedMap.setContents(generatedContents);
                Set<Vertex<Room>> rooms = generatedMap.getRooms();

                for(Vertex<Room> r : rooms){
                    for(Vertex<Room> j : rooms){
                        r.connect(j);
                        j.connect(r);
                    }
                }
                
            }

            case NORMAL -> {

                this.files = new File("MUD/Model/Map/PremadeMaps/Normal").listFiles();
                Room[] generatedContents = new Room[]{//Initialize the room with room numbers and size, read into it later
                        new Room(9,9,1),
                        new Room(9,9,2),
                        new Room(9,9,3),
                        new Room(9,9,4)};
                int roomIndex = 0; // room index variable, this will only be incremented after we have traversed
                // through the entire character array

                for (File file : this.files){//For each file in the subdirectory
                    char[][] temp = new char[9][9];//temp array to store read contents in before passing them to a room Object
                    Scanner in = new Scanner(file);
                    int tempIndex = 0; //index variable used to traverse temp array

                    while (in.hasNextLine()){
                        String line = in.nextLine();
                        char[] lineArr = line.toCharArray();
                        temp[tempIndex] = lineArr;
                        tempIndex++;
                    }
                    //at this point temp should have all the room contents as a 2d array of chars
                    Tile[][] contents = new Tile[9][9]; //array to feed setContents() method

                    for (int i = 0; i < contents.length; i++){//for every Tile object in contents
                        for (int j = 0; j < contents[0].length; j++){

                            Tile tempTile = new Tile(i, j, Character.toString(temp[i][j]));//get string representation of tile contents for new constructor
                            tempTile.setOccupant(returnOccupantFromContents(i, j, tempTile.getContents()));//set occupant based on contents
                            contents[i][j] =  tempTile; // populate with prepared tiles

                        }
                    }

                    generatedContents[roomIndex].setContents(contents);//each room gets its contents set here
                    generatedContents[roomIndex].populateTraps();
                    roomIndex++;
                    in.close();

                }

                generatedMap = new Map(4, generatedContents[0]);
                generatedMap.setContents(generatedContents);
                Set<Vertex<Room>> rooms = generatedMap.getRooms();

                for(Vertex<Room> r : rooms){
                    for(Vertex<Room> j : rooms){
                        if(!r.equals(j)){
                            r.connect(j);
                            j.connect(r);
                        }
                    }
                }
            }

            case HARD -> {
                this.files = new File("MUD/Model/Map/PremadeMaps/Hard").listFiles();
                Room[] generatedContents = new Room[]{//Initialize the room with room numbers and size, read into it later
                        new Room(9,9,1),
                        new Room(9,9,2),
                        new Room(9,9,3),
                        new Room(9,9,4),
                        new Room(9,9,5)};
                int roomIndex = 0; // room index variable, this will only be incremented after we have traversed
                // through the entire character array

                for (File file : this.files){//For each file in the subdirectory
                    char[][] temp = new char[9][9];//temp array to store read contents in before passing them to a room Object
                    Scanner in = new Scanner(file);
                    int tempIndex = 0; //index variable used to traverse temp array

                    while (in.hasNextLine()){
                        String line = in.nextLine();
                        char[] lineArr = line.toCharArray();
                        temp[tempIndex] = lineArr;
                        tempIndex++;
                    }
                    //at this point temp should have all the room contents as a 2d array of chars
                    Tile[][] contents = new Tile[9][9]; //array to feed setContents() method

                    for (int i = 0; i < contents.length; i++){//for every Tile object in contents
                        for (int j = 0; j < contents[0].length; j++){

                            Tile tempTile = new Tile(i, j, Character.toString(temp[i][j]));//get string representation of tile contents for new constructor
                            tempTile.setOccupant(returnOccupantFromContents(i, j, tempTile.getContents()));//set occupant based on contents
                            contents[i][j] =  tempTile; // populate with prepared tiles

                        }
                    }

                    generatedContents[roomIndex].setContents(contents);//each room gets its contents set here
                    generatedContents[roomIndex].populateTraps();
                    roomIndex++;
                    in.close();
                }

                generatedMap = new Map(5, generatedContents[0]);
                generatedMap.setContents(generatedContents);
                Set<Vertex<Room>> rooms = generatedMap.getRooms();

                for(Vertex<Room> r : rooms){
                    for(Vertex<Room> j : rooms){
                        if(!r.equals(j)){
                            r.connect(j);
                            j.connect(r);
                        }
                    }
                }
            }

            default -> throw new IllegalStateException("Unexpected difficulty value: " + this.difficulty + ". Map was not correctly generated.");
        }
        return generatedMap;
    }

    public Object returnOccupantFromContents(int row, int col, String contents){

        Door rDoor = new Door("Door.", "A sharp metal door. Heavy, but able to be opened.", Direction.Right);
        Door lDoor = new Door("Door.", "A sharp metal door. Heavy, but able to be opened.", Direction.Left);
        NPC enemy = new NPC("Garfield", "A very angry cat.", 50, 5,0, Behavior.Hostile, Bedtime.Diurnal, "MUD/View/images/garfield.png");
        Obstacle obstacle = new Obstacle("Wall.", "The walls of the room.");

        Chest chest = new Chest("Chest", "A chest of goodies", generateChestLoot());

        switch(contents){

            case "O":
                return obstacle;
            case "E":
                return enemy;
            case "D":
                if (col == 1) return lDoor;
                if (col == 7) return rDoor;
            case "C":
                return chest;
            case "M": //refactor for merchant after merge
                break;

        }

        return null;
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
