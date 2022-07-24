package MUD.Model.DayNight;

import MUD.Model.Character.NPC;
import MUD.Model.Character.Player;

/**
 * Visitor interface for the Visitor pattern. Defines the classes
 * that can be visited by the Visitor.
 * @author Phil Ganem
 */
public interface Visitor {
        
        /**
         * Visits an NPC running an algorithm.
         * @param npc
         */
        public void visitNPC(NPC npc);
        
        /**
         * Visits a Player running an algorithm.
         * @param player
         */
        public void visitPlayer(Player player);
        
}
