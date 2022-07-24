package MUD.Model.Save;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.github.underscore.U;
import com.google.gson.Gson;
import MUD.Model.Character.Player;
import MUD.Model.Map.Map;

/**
 * Adapts Underscore library to work with MUD
 * @author Phil Ganem
 */
public class XMLSaveAdapter implements Save {

    @Override
    public void exportSavePlayer(Player player, String fileName) {
        FileWriter writer;
        Gson gson = new Gson();
        String xml = "";
        String json = gson.toJson(player);
        
        player.setMap(null);
        try{xml = U.jsonToXml(json);}
        catch(Exception e){System.out.println(e);}
        try{
            writer = new FileWriter("MUD\\Saves\\PlayerSaves\\" + fileName + ".xml");
            writer.write(xml);
            writer.close();
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public void exportSaveMap(Map map, String fileName) {
        FileWriter writer;
        Gson gson = new Gson();
        String xml = "";

        String json = gson.toJson(map);       
        try{xml = U.jsonToXml(json);
        }catch(Exception e){System.out.println(e);}
        try {
            writer = new FileWriter("MUD\\Saves\\MapSaves\\" + fileName + ".xml");
            writer.write(xml);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Player importSavePlayer(String fileName) {
        Path path = Path.of("MUD\\Saves\\PlayerSaves\\" + fileName + ".xml");
        Gson gson = new Gson();
        String json;
        Player savedPlayer = null;
        try {
            json = U.xmlToJson(Files.readString(path));
            savedPlayer = gson.fromJson(json, Player.class);
        }catch(Exception e){e.printStackTrace();}
        return savedPlayer;  
        
    }

    @Override
    public Map importSaveMap(String fileName) {
        Gson gson = new Gson();
        String json;
        Map savedMap = null;
        Path path = Path.of("MUD\\Saves\\MapSaves\\" + fileName + ".xml");
     
        try {
            json = U.xmlToJson(Files.readString(path));
            savedMap = gson.fromJson(json, Map.class);
        }catch(Exception e) {e.printStackTrace();}
        return savedMap;        
    }

}
    
