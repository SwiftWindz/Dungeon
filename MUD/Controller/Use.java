package MUD.Controller;

import java.io.Serializable;

import MUD.Model.Character.Player;
import MUD.Model.Inventory.Item;

public class Use implements Action, Serializable{

    Player player;
    Item target;

    public Use(Player p, Item i){
        player = p;
        target = i;
    }

    @Override
    public void execute() {
        target.useItem(player);
        player.removeItemFromInventory(target);
    }
    
}
