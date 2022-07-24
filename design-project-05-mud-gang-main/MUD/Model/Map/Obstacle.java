package MUD.Model.Map;

import java.io.Serializable;

/**
 * Class which represents an impassable Object that blocks a tile.
 * @author Nathan Perez
 */
public class Obstacle implements TileElement, Serializable{

    private final String name;
    private final String description;

    /**
     * Constructor for the Obstacle class.
     * @param name Name of the Obstacle object.
     * @param description Description of the Obstacle object.
     */
    public Obstacle(String name, String description){
        this.name = name;
        this.description = description;

    }

    @Override
    public String toString() {
        return this.name + " | " + this.description;
    }

}
