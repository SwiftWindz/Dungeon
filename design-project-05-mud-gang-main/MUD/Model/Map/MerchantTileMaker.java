package MUD.Model.Map;

import java.util.Random;

import MUD.Model.Character.Merchant;

public class MerchantTileMaker implements TileMaker {
    String[] NAMES = {"RattleBones McGee", "Jim", "qwerty"};
    String[] DESCS = {"A skeleton merchant", "Used to be an adventurer like you, now sells stuff", "asdf merchant"};

    @Override
    public Tile makeTile(int r, int c) {
        Tile tile = new Tile(r, c);
        tile.setContents("M");
        int randInt = new Random().nextInt(3);
        tile.setOccupant(new Merchant(NAMES[randInt], DESCS[randInt], 1000000));
        return tile;
    }
    
}
