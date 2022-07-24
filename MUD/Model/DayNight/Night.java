package MUD.Model.DayNight;


import MUD.Model.Character.Player;
import MUD.Model.Map.Room;
import MUD.Model.Map.Tile;

import java.io.Serializable;

import MUD.Model.Character.NPC;

/**
 * Child class of Time for sending Day visitors when the game state is Day
 * @author Carter Vail
 */
public class Night implements Time, Serializable{

    //used to send a visitor to the map in order to affect NPCs and notify the player
    @Override
    public void SendVisit(Visitor visitor, Player player) {
        Room room = player.getRoom();
        Tile tiles[][] = room.getContents();
        for (Tile row[] : tiles){
        	for (Tile tile : row) {
        		Object occupant = tile.getOccupant();
                if (occupant != null){
        		    if (occupant.getClass().equals(NPC.class)){
        		    	NPC npc = (NPC)occupant;
        			    visitor.visitNPC(npc);
        		    }
                }
            }
        }
        visitor.visitPlayer(player);
     
    }
 
}
