package MUD.Model.Map;

import java.util.Random;

import MUD.Model.Character.Bedtime;
import MUD.Model.Character.Behavior;
import MUD.Model.Character.NPC;

public class EnemyTileMaker implements TileMaker{

    public NPC generateRandomEnemy(){
        /**
         * Method which randomly generates an enemy to populate the board with.
         * @return a random NPC Enemy
         */
        NPC[] arr = new NPC[]{
                new NPC("Garfield", "A very angry cat.", 50, 5,0, Behavior.Hostile, Bedtime.Diurnal, "MUD/View/images/garfield.png"),
                new NPC("Bobby St. Jacques", "Beloved leader.", 100, 10,3, Behavior.Hostile, Bedtime.Diurnal, "MUD/View/images/he.png"),
                new NPC("Slime", "A blue ball of goop.", 75, 5, 5, Behavior.Hostile, Bedtime.Nocturnal, "MUD/View/images/slime.png"),
                new NPC("Orc", "A big, angry, green guy with horns and an axe.", 110,20,0,Behavior.Hostile, Bedtime.Diurnal, "MUD/View/images/orc.png"),
                new NPC("Bat", "You cant see me.", 15, 10,3, Behavior.Hostile, Bedtime.Nocturnal, "MUD/View/images/bat.png"),
                new NPC("The Devil", "Just here to play rock and roll", 100, 25,3, Behavior.Hostile, Bedtime.Nocturnal, "MUD/View/images/devil.png"),
                new NPC("The Wizard", "He's a 'wizard.' Magic's not real, idiot", 25, 25,3, Behavior.Hostile, Bedtime.Diurnal, "MUD/View/images/wizard.png"),
                new NPC("Imposter", "Wrong game", 10, 5,3, Behavior.Hostile, Bedtime.Diurnal, "MUD/View/images/imposter.png"),
                new NPC("Goblin", "There is no depth of casual cruelty or random violence to which a Goblin will not sink, if it senses that is has the power to do so", 
                5, 5, 0, Behavior.Hostile, Bedtime.Nocturnal, "MUD/View/images/goblin.png"),
                new NPC("Dwarf", "Just minin'", 2, 10,15, Behavior.Hostile, Bedtime.Diurnal, "MUD/View/images/dwarf.png"),
                };

        int rnd = new Random().nextInt(0, arr.length);

        return arr[rnd];
    }
    @Override
    public Tile makeTile(int r, int c) {
        Tile tile = new Tile(r, c);
        NPC enemy = generateRandomEnemy();
        tile.setContents("E");
        tile.setOccupant(enemy);
        return tile;
    }
    
}
