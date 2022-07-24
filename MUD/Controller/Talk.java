package MUD.Controller;

import java.io.Serializable;

import MUD.Model.Character.Merchant;
import MUD.Model.Character.Player;

public class Talk implements Action, Serializable{

    private Player player;
    private Merchant merchant;

    public Talk(Player p, Merchant m){
        player = p;
        merchant = m;
    }

    @Override
    public void execute() {
        merchant.talk(player);
    }
}
