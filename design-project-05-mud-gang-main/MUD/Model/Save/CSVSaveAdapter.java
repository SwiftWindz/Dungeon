package MUD.Model.Save;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.Gson;
import MUD.Model.Character.Player;
import MUD.Model.Map.Map;

/**
 * Adapts CSV to work with MUD
 * @author Phil Ganem
 */
public class CSVSaveAdapter implements Save {

    @Override
    public void exportSavePlayer(Player player, String fileName) {
        Gson gson = new Gson();
        String json = gson.toJson(player);
        File file = new File("MUD\\Saves\\PlayerSaves\\" + fileName + ".csv");
        
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            System.out.println("File Created successfully");
            writer.close();
        }catch(Exception e){System.out.println("ERROR:" + e);}
    }

    @Override
    public Player importSavePlayer(String fileName){
        Gson gson = new Gson();
        Player savedPlayer = null;
        try{
            Path path = Path.of("MUD\\Saves\\PlayerSaves\\" + fileName + ".csv");
            savedPlayer = gson.fromJson(Files.readString(path), Player.class);
            return savedPlayer;
        }catch(Exception e){System.out.print("ERROR:" + e);} return savedPlayer;
    }

    @Override
    public void exportSaveMap(Map map, String fileName) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        File file = new File("MUD\\Saves\\MapSaves\\" + fileName + ".csv");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            System.out.println("File Created successfully");
            writer.close();
        } catch (Exception e){System.out.println("ERROR:" + e);}
    }

    @Override
    public Map importSaveMap(String fileName) {
        Gson gson = new Gson();
        Map savedMap = null;
        try{
            Path path = Path.of("MUD\\Saves\\MapSaves\\" + fileName + ".csv");
            savedMap = gson.fromJson(Files.readString(path), Map.class);
            return savedMap;
        }catch(Exception e){System.out.print("ERROR:" + e);} return savedMap;        
    }

}
