package MUD.Model.Map;

import java.io.Serializable;

/**
 * Class which represents a given Tile in a Room for the player to navigate through.
 * This class serves as a Leaf class for the Composite pattern.
 * @author Nathan Perez
 */
public class Tile implements MapElement, Serializable {
    private String contents = ".";
    private Object occupant;
    private int row;
    private int col;

    /**
     * Constructor for the Tile class.
     */
    public Tile(int r, int c){
        row = r;
        col = c;
    }

    /**
     * Method to search for a given entity in a Tile.
     * @param entity: The entity to be searched for.
     * @return true or false
     */
    @Override
    public boolean hasEntity(Object entity) {
        if (this.occupant == null)
            return false;

        return this.occupant.getClass().equals(entity.getClass());
    }

    @Override
    public boolean isGoal() {
        return contents.equals("G");
    }

    @Override
    public String toString() {
        return "[" + contents + "]";
    }

    public String getContents(){
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Object getOccupant() {
        return occupant;
    }

    public void setOccupant(Object occupant) {
        this.occupant = occupant;
    }

    public boolean isOccupied() {
        /* if the contents of the tile are not 'Empty' or 'Goal'*/
        return !(contents.equals(".") || contents.equals("G"));
    }

    public boolean isTraversable(){
        return !(contents.equals(".") || contents.equals("G"));
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}