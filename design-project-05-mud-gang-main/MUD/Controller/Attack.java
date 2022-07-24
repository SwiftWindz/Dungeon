package MUD.Controller;

import java.io.Serializable;

import MUD.Model.Character.Character;
import MUD.Model.Character.Player;
/**
 * Attack concrete command, tells the player to attack a targeted tile
 * holds only a reference to the character to call on and a target
 * execute sends the target to the character so it can perform the action
 * @author Cam Bacon
 */

public class Attack implements Action, Serializable{

    private Player player; //reference to the character to call on
    private Character target; //targeted tile to recieve the attack

    public Attack(Player p, Character t){
        player = p;
        target = t;
    }

    @Override
    public void execute() { //tells the character to attack the target
        player.attack(target);
    }
    
}
