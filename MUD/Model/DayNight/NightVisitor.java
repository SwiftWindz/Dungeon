package MUD.Model.DayNight;

import java.io.Serializable;

import MUD.Model.Character.Bedtime;
import MUD.Model.Character.NPC;
import MUD.Model.Character.Player;
import MUD.Model.Map.Room;
import MUD.Model.Map.Tile;

/**
 * Night visitor for the character classes.
 * @author Phil Ganem
 */
public class NightVisitor implements Visitor, Serializable{

    @Override
    public void visitNPC(NPC npc) {

        // Depending on the NPC's bedtime, modify the NPC's attack.
        if(npc.getBedtime() == Bedtime.Nocturnal) {
            // If the NPC is diurnal, increase attack by 10%.
            double modifiedAttack = npc.getTrueAttack() * 1.1;
            npc.setAttack(modifiedAttack);
        }
        else if(npc.getBedtime() == Bedtime.Diurnal) {
            // If the NPC is nocturnal, decrease attack by 20%.
            double modifiedAttack = npc.getTrueAttack() * 0.8;
            npc.setAttack(modifiedAttack);
        }

    }
        
    @Override
    public void visitPlayer(Player player) {
    	String note = "It has become night";
        Room room = player.getRoom();
        Tile[][] tiles = room.getContents();
        for (Tile row[] : tiles){
        	for (Tile tile : row) {
        		Object occupant = tile.getOccupant();
                if (occupant != null){
        		    if (occupant.getClass().equals(NPC.class)){
        		    	NPC npc = (NPC)occupant;
        		    	if (npc.getBedtime() == Bedtime.Nocturnal){
        		    		note += ", " + npc.getName() + "'s stats are increased";
        		    	}
        		    	if (npc.getBedtime() == Bedtime.Diurnal){
        		    		note += ", " + npc.getName() + "'s stats are decreased";
        		    	}
                    }
                }
            }
        }                
        note += "!";
        System.out.println(note);
    }
    
}
