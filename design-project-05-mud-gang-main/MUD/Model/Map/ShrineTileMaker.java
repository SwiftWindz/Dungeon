package MUD.Model.Map;

public class ShrineTileMaker implements TileMaker{

    @Override
    public Tile makeTile(int r, int c) {
        Tile tile = new Shrine(r, c);
        tile.setContents("S");
        return tile;
    }
    
}
