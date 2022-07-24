package MUD.Model.Map;

import java.io.Serializable;

/**
 * Class which represents a given Map for the player to traverse through.
 * This class serves as a Concrete Composite class for the Composite pattern.
 * @author Nathan Perez
 */
public class Map implements MapElement, Serializable{
    private final Room[] contents; //Linear array of Rooms to outline the map layout
    private int width;
    private int numberOfRooms;
    private String description;

    /**
     * Constructor for the Map class.
     * @param width The width of the map (in Rooms).
     */
    public Map(int width) {
        this.contents = new Room[width];//This assumes that the Rooms being passed in will be squares
        this.width = width;
        this.numberOfRooms = this.contents.length;
        // By default, each room in a map is a 9 x 9 grid of tiles
        for (int i = 0; i < this.contents.length; i++){
            this.contents[i] = new Room(9, 9, i); //Hardcoded odd-numbered sides
        }

    }

    /**
     * Method to search for a given entity in the entire Map.
     * @param entity: The entity to be searched for.
     * @return true or false
     */
    @Override
    public boolean hasEntity(Object entity) {
        boolean flag = false;
        for (Room room  : contents){
            flag = room.hasEntity(entity);
        }

        return flag;
    }

    @Override
    public boolean isGoal() {
        return false;
    }

    @Override
    public String toString() {
        return this.description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Method to search for the total instances of a given entity in the entire Map.
     * @param entity: The entity to be searched for.
     * @return true or false
     */
    public int getRemainingEntities(Object entity) {
        int count = 0;
        for (Room room  : this.contents){
                count += room.getRemainingEntities(entity);
            }

        return count;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public Room[] getContents() {
        return contents;
        
    }

    /**
     * Method which populates the map, therefore populating every Room within the map's array of contents. Removes the
     * unused left-most door of the first Room, as the player cannot travel through a door that is not connected
     * to a room.
     */
    public void populate() {

        for (Room room  : contents){
                room.populate();

        }

        this.contents[0].clearDoors(4, 1);//Clear the first room's left-most space.
        this.contents[width-1].setGoal(true);
    }

}