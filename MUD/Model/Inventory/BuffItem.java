package MUD.Model.Inventory;

import MUD.Model.Character.Player;

/**
 * This is a concrete class for a buff item
 * that increases the players stats for 10
 * turns
 * 
 * @author Borna Eshraghi
 */
public class BuffItem extends Item{
    private BuffType buffType;
    private int turnsLeft;
    private double statChange;

    public BuffItem(String name, String description, int value, BuffType buffType, double statChange) {
        super(name, description, value);
        // The stat that the buff alters
        this.buffType = buffType;
        // The number of turns that the buff remains active
        this.turnsLeft = 10;
        // the value that the specific stat is altered by
        this.statChange = statChange;

    }

    @Override
    public void useItem(Player player){
        player.equipBuff(this);
    }

    public BuffType getBuffType() {
        return buffType;
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }

    public void decreaseTurnsLeft() {
        turnsLeft -= 1;
    }

    public boolean isTurnsLeftZero() {
        return turnsLeft == 0;
    }

    public double getStatChange() {
        return statChange;
    }

    @Override
    public String toString() {
        String string = getName() + ": " + statChange;
        return string;
    }
}
