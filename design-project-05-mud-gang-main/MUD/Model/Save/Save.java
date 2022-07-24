package MUD.Model.Save;

import java.io.FileNotFoundException;
import java.io.IOException;
import MUD.Model.Character.Player;
import MUD.Model.Map.Map;

/**
 * Interface for defining a type of save/load.
 * @author Phil Ganem
 */
public interface Save {
    /**
     * Creates a save file for a player character.
     * @throws FileNotFoundException
     */
    public void exportSavePlayer(Player player, String fileName);

    /**
     * Creates a save file for a map.
     */
    public void exportSaveMap(Map map, String fileName);

    /**
     * Imports/"loads" a save file for a player character.
     * @throws IOException
     * @throws JSONException
     */
    public Player importSavePlayer(String fileName);


    /**
     * Imports/"loads" a save file for a map.
     */
    public Map importSaveMap(String fileName);

}