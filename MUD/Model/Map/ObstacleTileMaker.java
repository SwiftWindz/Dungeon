package MUD.Model.Map;

import java.util.Random;

public class ObstacleTileMaker implements TileMaker {
    String[] NAMES = {"Big Boulder", "The Immovable Object", "Stop Sign", "Stick"};
    String[] DESCRIPTIONS = {"Too big for M.U.D.", "It can't be moved!", "It would be rude to walk through it.", "A real stick in the M.U.D."};

    @Override
    public Tile makeTile(int r, int c) {
        Tile tile = new Tile(r, c);
        tile.setContents("O");
        Integer randInt = new Random().nextInt(0, 4);
        tile.setOccupant(new Obstacle(NAMES[randInt], DESCRIPTIONS[randInt]));
        return tile;
    }
    
}
