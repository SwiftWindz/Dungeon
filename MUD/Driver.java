package MUD;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import MUD.Model.Character.Player;
import MUD.Model.DayNight.DayNightCycle;
import MUD.Model.Map.Map;
import MUD.Model.Map.PremadeMaps.DiffEnum;
import MUD.Model.Map.PremadeMaps.PremadeGenerator;
import MUD.View.PTUI;
// import MUD.Model.DayNight.DayNightCycle;

public class Driver {
    
    public static void main(String[] args) throws FileNotFoundException {
        // Clears the screen at the start of the game.
        System.out.println("\033[H\033[2J");
        System.out.println(
        "        ___________\n" + 
        "   ._____l_______l_____.\n" +
        "   ||_____/  |  \\_____||\n" +
        "         /   |   \\\n" +
        "        /    |    \\\n" +
        "       /     |     \\\n" +
        "      /      |      \\\n" +
        "     /       |       \\\n" +
        "    /        |        \\\n" +
        "   |         |         |\n" +
        "    \\        |        /\n" +
        "     \\       |       /\n" +
        "      \\      |      /\n" +
        "       \\     |     /\n" +
        "        \\    |    /\n" +
        "         \\   |   /\n" +
        "          \\  |  /\n" +
        "           \\ | /\n" +
        "            \\ /\n" +
        "             V\n");

        System.out.println("     WELCOME TO THE MUD");
        System.out.println("  Press enter to continue...");


        try (//Loads a save file if one exists.
            Scanner scanner = new Scanner(System.in)) {
            scanner.nextLine();
            System.out.println("Would you like to load a save? (y/n)");
            String response = scanner.nextLine();

            // -------------------------------------------
            if (response.equals("y")){
                while(true){
                    System.out.println("What is the name of your save file? (ex: save)");
                    String saveName = scanner.nextLine();
                    if (saveName.equals("q") || saveName.equals("Q") || saveName.equals("quit") || saveName.equals("Quit")){
                        System.out.println("Quitting...");
                        break;
                    }
                    try {
                        String path = "MUD\\Saves\\" + saveName + ".data";
                        Load load = new Load(path);
                        PTUI ptui = load.getPtui();
                        ptui.getDnCycle().startCycle();
                        ptui.runUi();
                        break;
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found. Please try again.");
                    } catch (IOException e) {
                        System.out.println("Incorrect input format");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                
            // -------------------------------------------

            } else {
                System.out.flush();
                //Map map = new Map(2);
                //map.populate();
                PremadeGenerator pg = new PremadeGenerator(DiffEnum.NORMAL);
                Map map = pg.generate();
                Player player = new Player("You", "The player", 100, 10, 0, map);
                
                // Intro
                System.out.println("What is the name of your adventurer?");
                String name = scanner.nextLine();
                player.setName(name);
                System.out.println("What is your adventurer's description?");
                String description = scanner.nextLine();
                player.setDescription(description);
                System.out.println("Press enter to begin your descent into the dungeon...");
                scanner.nextLine();

                //Flush
                System.out.println("\033[H\033[2J");
                System.out.flush();

                player.setCurrentVertex(map.getStartRoom());
                player.setCurrentTile(player.getCurrentVertex().getValue().getContents()[1][1]);
                DayNightCycle dnCycle = new DayNightCycle(player);
                dnCycle.startCycle();
                PTUI ptui = new PTUI(player, dnCycle);
                ptui.runUi();
            }
        }
    }

}
