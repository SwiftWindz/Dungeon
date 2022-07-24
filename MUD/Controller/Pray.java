package MUD.Controller;

import java.io.Serializable;

import MUD.Model.Character.Player;
import MUD.Model.Map.Shrine;
/**
 * Pray concrete action. Uses a shrine to create a memento of the player for later restoration on death.
 * @author Cam Bacon
 */
public class Pray implements Action, Serializable{
    
    private Player player;
    private Shrine shrine;

    public Pray(Player p, Shrine s){
        player = p;
        shrine = s;
    }

    @Override
    public void execute() {
        player.pray(shrine);
    };
}
