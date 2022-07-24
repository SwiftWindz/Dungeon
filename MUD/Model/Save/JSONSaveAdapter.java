package MUD.Model.Save;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.Gson;
import MUD.Model.Character.Player;
import MUD.Model.Map.Map;


/**
 * Adapts GSON to work with MUD
 * @author Phil Ganem
 */
public class JSONSaveAdapter implements Save {
    
    @Override
    public void exportSavePlayer(Player player, String fileName) {
        Gson gson = new Gson();
        try {
            String json = gson.toJson(player);
            FileWriter writer = new FileWriter("MUD\\Saves\\PlayerSaves\\" + fileName + ".json");
            writer.write(json);
            writer.close();
        } catch (StackOverflowError e) {
            System.out.println("stack overflow error");
        }        
        catch (Exception e) {System.out.println("ERROR:" + e.getMessage());}
    }

    @Override
    public void exportSaveMap(Map map, String fileName) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
       
        try {
            FileWriter writer = new FileWriter("MUD\\Saves\\MapSaves\\" + fileName + ".json");
            writer.write(json);
            writer.close();
        }catch(Exception e){System.out.println("ERROR:" + e);}
    }

    @Override
    public Player importSavePlayer(String fileName) {
        Gson gson = new Gson();
        Path path = Path.of("MUD\\Saves\\PlayerSaves\\" + fileName + ".json");
        Player savedPlayer = null;
        try{
            savedPlayer = gson.fromJson(Files.readString(path), Player.class);
            return savedPlayer;
        }catch(Exception e){System.out.println("ERROR:" + e);} return savedPlayer;
    }

    @Override
    public Map importSaveMap(String fileName) {
        Gson gson = new Gson();
        Path path = Path.of("MUD\\Saves\\MapSaves\\" + fileName + ".json");
        Map savedMap = null;
        try{
            savedMap = gson.fromJson(Files.readString(path), Map.class);
            return savedMap;
        }catch(Exception e) {System.out.println("ERROR:" + e);} return savedMap;
    }    
    
    // public static void main(String[] args) {
    //     CSVSaveAdapter jsonAdapter = new CSVSaveAdapter();
    //     Player player = new Player("Phil", "The player", 100, 10, 0, new Map(new Room(9, 9, 0)));
    //     Map map = new Map(new Room(9, 9, 0));
    //     // jsonAdapter.exportSavePlayer(player, "test");
    //     jsonAdapter.exportSaveMap(map, "test");

    // }

}
