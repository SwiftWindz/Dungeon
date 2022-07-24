package MUD.Controller;

import java.io.Serializable;
import MUD.Model.Character.Player;
import MUD.Model.Map.Trap;

/**
 * Disarm concrete command, tells the player to disarm a trap on a targeted tile
 * holds only a reference to the character to call on and a target
 * execute sends the target to the character so it can perform the action
 * @author Cam Bacon
 */
public class Disarm implements Action, Serializable{

    private Trap target;
    private Player player;

    public Disarm(Trap t, Player p){
        target = t;
        player = p;
    }

    @Override
    public void execute() {
        target.disarm(player);

    }

    public Trap getTarget() {
        return target;
    }
    
}
