package MUD.Model.Map;

public class BlankTileMaker implements TileMaker{

    @Override
    public Tile makeTile(int r, int c) {
        return new Tile(r, c);
    }

    
}
