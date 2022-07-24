package MUD.Model.DayNight;

import MUD.Model.Character.Player;

/**
 * Interface to define methods for different times to be used in the day night cycle
 * @Author Carter Vail
 */
public interface Time  {
    public void SendVisit(Visitor visitor, Player player); 
}
