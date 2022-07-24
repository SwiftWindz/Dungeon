package MUD.Model.Map;

import java.io.Serializable;

/**
 * Class which represents an openable door D that occupies a tile, giving entry to the next room in the map.
 * @author Nathan Perez
 */
public class Door implements TileElement, Serializable{

    private final String name;
    private final String description;
    private final String side;

    /**
     * Constructor for the Door class.
     * @param name Name of the Door object.
     * @param description Description of the Door object.
     */
    public Door(String name, String description, String side){
        this.name = name;
        this.description = description;
        this.side = side;

    }

    @Override
    public String toString() {
        return this.name + " | " + this.description;
    }

    public String getSide() {
        return side;
    }

}
