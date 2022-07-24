package MUD.Controller;

import java.io.Serializable;

import MUD.Model.Character.Player;
import MUD.Model.Map.Tile;
/**
 * Move concrete command, tells the player to move to a targeted tile
 * holds only a reference to the character to call on and a target
 * execute sends the target to the character so it can perform the action
 * @author Cam Bacon
 */

public class Move implements Action, Serializable {

    private Player player;
    private Tile target;

    public Move(Player p, Tile t){
        player = p;
        target = t;
    }

    @Override
    public void execute() {
        player.move(target);
    }
    
}
