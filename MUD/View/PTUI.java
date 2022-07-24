package MUD.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import MUD.Save;
import MUD.Controller.Action;
import MUD.Controller.Attack;
import MUD.Controller.Disarm;
import MUD.Controller.Interpreter;
import MUD.Controller.Move;
import MUD.Controller.Open;
import MUD.Controller.Use;
import MUD.Model.Character.Bedtime;
import MUD.Model.Character.Behavior;
import MUD.Model.Character.Character;
import MUD.Model.Character.NPC;
import MUD.Model.Character.Player;
import MUD.Model.DayNight.DayNightCycle;
import MUD.Model.Inventory.Bag;
import MUD.Model.Inventory.BuffItem;
import MUD.Model.Inventory.Inventory;
import MUD.Model.Inventory.Item;
import MUD.Model.Map.Chest;
import MUD.Model.Map.Obstacle;
import MUD.Model.Map.Room;
import MUD.Model.Map.Tile;
import MUD.Model.Map.Trap;

/**
 * PTUI class works to manage the command structure by prompting the player for input
 * and feeding the input into the interpreters. Also validates commands and notifies 
 * the player when they've input an invalid command.
 * @author Cam Bacon
 */
public class PTUI implements Serializable  {
    private java.util.Map<String, Interpreter> validMoves;
    private Player player;
    private static boolean hasWon = false;
    private DayNightCycle dnCycle;

    public PTUI(Player player, DayNightCycle DNC){
        this.player = player;
        this.dnCycle = DNC;
        findValidMoves();
    }

    public static void win(){
        hasWon = true;
        System.out.println("Congradulations! You reached the Goal!");
    }

    public void findValidMoves(){
        java.util.Map<String, Interpreter> validMoves = new HashMap<String, Interpreter>();

        List<Tile> adjacentTiles = player.getAdjacentTiles();

        //mock objects for checking tile contents
        Trap mockTrap = new Trap(1, "name", "description");
        ArrayList<Item> mockItems = new ArrayList<>();
        Chest mockChest = new Chest("name", "description", mockItems);
        Character mockNPC = new NPC("name", "description", 1, 1, 1, Behavior.Hostile, Bedtime.Diurnal);
        Obstacle mockObstacle = new Obstacle("name", "description");

        //run through all adjacent tiles and list every possible action that can be taken with them
        for(Tile t : adjacentTiles){
            if(t.hasEntity(mockChest)){
                Chest chest = (Chest)t.getOccupant();
                Action open = new Open(player, chest);
                Interpreter interpreter = new Interpreter(open);
                validMoves.put("Open " + chest.getName(), interpreter);
            }
            else if(t.hasEntity(mockNPC)){
                NPC target = (NPC)t.getOccupant();
                Action attack = new Attack(player, target);
                Interpreter interpreter = new Interpreter(attack);
                validMoves.put("Attack " + target.getName(), interpreter);
            }
            else if(t.hasEntity(mockTrap)){
                Trap trap = (Trap)t.getOccupant();
                if(t.getContents().equals(".")){
                    Random r = new Random();
                    int randomInt = r.nextInt(3);
                   if(randomInt % 2 == 0){
                       t.setContents("T");
                   }
                }
                if(t.getContents().equals("T")){
                    if(trap.getDisarmable()){
                        Action disarm = new Disarm(trap, player);
                        Interpreter interpreter = new Interpreter(disarm);
                        validMoves.put("Disarm " + trap.getName(), interpreter);
                    }
                    else{
                        t.setContents(".");
                        t.setOccupant(null);
                    }
                }
                Action move = new Move(player, t);
                Interpreter interpreter = new Interpreter(move);
                validMoves.put("Move to " + t.getRow() + ", " + t.getCol(), interpreter);
            }
            else if(!t.hasEntity(mockObstacle)){
                Action move = new Move(player, t);
                Interpreter interpreter = new Interpreter(move);
                validMoves.put("Move to " + t.getRow() + ", " + t.getCol(), interpreter);
            }
        }

        Inventory inventory = player.getInventory();
        for(Bag b : inventory.getInventory()){
            for(Item i : b.getItems()){
                Action use = new Use(player, i);
                Interpreter interpreter = new Interpreter(use);
                validMoves.put("Use " + i.getName(), interpreter);
            }
        }

        this.validMoves = validMoves;
    }

    public boolean validateMove(String input){
        Set<String> possibleMoves = validMoves.keySet();
        return possibleMoves.contains(input);
    }

    public List<NPC> getAttackingNPCs(){
        List<NPC> attackingNPCs = new ArrayList<>();

        List<Tile> adjacentTiles = player.getAdjacentTiles();

        Character mockNPC = new NPC("name", "description", 1, 1, 1, Behavior.Hostile, Bedtime.Diurnal);

        for(Tile t : adjacentTiles){
            if(t.hasEntity(mockNPC)){
                NPC n = (NPC)t.getOccupant();
                if(n.isDefeated()){
                    t.setOccupant(null);
                    t.setContents(".");
                }
                else{
                    attackingNPCs.add((NPC)t.getOccupant());
                }
            }
        }

        return attackingNPCs;
    }

    public void displayRoom(){
        Room currentRoom = player.getRoom();
        Tile[][] roomTiles = currentRoom.getContents();
        for(int i = 0; i < currentRoom.getHeight(); i++){
            for(int j = 0; j < currentRoom.getWidth(); j++){
                if(roomTiles[i][j].equals(player.getCurrentTile())){
                    System.out.print("\u001B[32m@\u001B[0m" + " "); //ANSI code to make player icon green
                }
                else{
                    System.out.print(roomTiles[i][j].getContents() + " ");
                }
            }
            System.out.println();
        }
    }

    public String promptUser(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please input a valid move: ");
        String command;
        try {
            command = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return command;
    }

    public void runUi(){
        findValidMoves();
        displayRoom();
        while(!hasWon){
            if (player.isDefeated()){
                System.out.println("You have died!");
                break;
            }
            System.out.println(validMoves.keySet());
            String userCommand = promptUser();
            if(userCommand.equals("quit")){
                try (Scanner scanner = new Scanner(System.in)) {
                    System.out.println("Would you like to save? (y/n)");
                    String response = scanner.nextLine();
                    if(response.equals("y")){
                        while(true) {
                            System.out.println("Would you like to call your save?");
                            String saveName = scanner.nextLine();
                            if (saveName.equals("q") || saveName.equals("Q") || saveName.equals("quit") || saveName.equals("Quit")){
                                System.out.println("Game not saved.");
                                System.out.println("Quitting...");
                                break;
                            } else if(saveName.equals(" ") || saveName.equals("")){
                                System.out.println("Save file name cannot be a blankspace.");
                                
                            } else {
                                try {
                                    new Save(this, saveName);
                                    break;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println("Save failed. Please try again.");
                                }
                            }
                        }
                    }
                }
                break;
            }
            if(validateMove(userCommand)){
                System.out.println("\033[H\033[2J");
                validMoves.get(userCommand).invoke();
                List<BuffItem> buffs = player.getEquippedBuffs();
                for(int index = 0; index < buffs.size(); index++){
                    BuffItem buff = buffs.get(index);
                    buff.decreaseTurnsLeft();
                    if (buff.isTurnsLeftZero()) {
                        player.removeBuff(buff);
                    }
                }
                // Clears the screen each time a command is run to make it look cleaner
                System.out.flush();
                dnCycle.changeTime();
                for(NPC n : getAttackingNPCs()){
                        n.attack(player);
                }
                findValidMoves();
                displayRoom(); 
                System.out.println("STATUS:");
                System.out.println("HP: " + player.getCurrentHealth() + " ATK: " + player.getAttack() + " DEF: " + player.getDefense());
                continue;
            }
            System.out.println("Invalid move. Please input a valid move.");
        }
        System.out.println("Ending game.");
    }

    public Player getPlayer() {
        return player;
    }

    public DayNightCycle getDnCycle() {
        return dnCycle;
    }
}
