package MUD.Model.DayNight;

import java.io.Serializable;

import MUD.Model.Character.Player;

/**
 * Class which will handle keeping track of the current state of the game as well as changing that state
 * @author Carter Vail
 */
public class DayNightCycle implements Serializable {
    //the current state
    private Time currentTime;
    //time between cycles in seconds
    private int cycleTime;
    //the current game map
    private Player player;
    //the time to be checked against to see if state should change
    private Long checkTime;
    
    

    public DayNightCycle(Player player){
        this.currentTime = new Day();
        this.cycleTime = 300;
        this.player = player;
    }

    public Time getTime(){
        return this.currentTime;
    }

    public void setTime(Time time){
        this.currentTime = time;
    }

    public void changeTime(){
        changeTime(false);
    }

    public void changeTime(boolean override){
        if (System.currentTimeMillis() - this.checkTime > cycleTime * 1000 || override == true){
            this.checkTime = System.currentTimeMillis();
            if (this.currentTime instanceof Day){
                this.currentTime = new Night();
                this.currentTime.SendVisit(new NightVisitor(), this.player);
            }
            else{
                this.currentTime = new Day();
                this.currentTime.SendVisit(new DayVisitor(), this.player);
            }
        }
    }

    public void startCycle(){
        this.checkTime = System.currentTimeMillis(); 
    }  
}
