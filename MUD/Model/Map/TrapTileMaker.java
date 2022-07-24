package MUD.Model.Map;

import java.util.Random;

public class TrapTileMaker implements TileMaker{

    String[] BASIC_TRAP = {"2", "Basic Trap", "A basic trap for basic dungeons"};
    String[] SUPER_TRAP = {"5", "Super Trap", "Like a basic trap but super"};
    String[] DEADLY_TRAP = {"20", "Deadly Trap", "Someone really doesn't like you"};

    @Override
    public Tile makeTile(int r, int c) {
        //initializing the new tile
        Tile tile = new Tile(r,c);
        
        //deciding which type of trap will be added
        Random random = new Random();
        Integer randInt = random.nextInt(21);
        if (randInt < 11)
            tile.setOccupant(new Trap(Integer.parseInt(BASIC_TRAP[0]), BASIC_TRAP[1], BASIC_TRAP[2]));
        else if (randInt <= 19)
            tile.setOccupant(new Trap(Integer.parseInt(SUPER_TRAP[0]), SUPER_TRAP[1], SUPER_TRAP[2]));
        else
            tile.setOccupant(new Trap(Integer.parseInt(DEADLY_TRAP[0]), DEADLY_TRAP[1], DEADLY_TRAP[2]));

        return tile;
    }
    
}
