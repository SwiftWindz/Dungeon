package MUD.Controller;

import java.io.Serializable;

import MUD.Model.Character.Player;
import MUD.Model.Map.Chest;
/**
 * Open concrete command, tells the player to open the chest on a targeted tile
 * holds only a reference to the character to call on and a target
 * execute sends the target to the character so it can perform the action
 * @author Cam Bacon
 */
public class Open implements Action, Serializable {

    private Player player;
    private Chest target;

    public Open(Player p, Chest c){
        player = p;
        target = c;
    }

    @Override
    public void execute() {
        target.open(player);
    }
    
}
