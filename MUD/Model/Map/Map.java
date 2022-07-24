package MUD.Model.Map;

import java.util.HashSet;
import java.util.Set;

/**
 * Class which represents a given Map for the player to traverse through.
 * This class serves as a Concrete Composite class for the Composite pattern.
 * @author Nathan Perez
 */
public class Map implements MapElement{
    private Room[] contents; //KEEP for premade mode
    private int width;
    private int numberOfRooms;
    private String description;

    private transient Vertex<Room> startRoom;
    private transient Set<Vertex<Room>> rooms;

    /**
     * Constructor for the Map class.
     * @param width The width of the map (in Rooms).
     */
    public Map(int width, Room startRoom) {
        this.contents = new Room[width];//This assumes that the Rooms being passed in will be squares
        this.rooms = new HashSet<Vertex<Room>>();
        this.width = width;
        this.numberOfRooms = this.contents.length;
        // By default, each room in a map is a 9 x 9 grid of tiles
        for (int i = 0; i < this.contents.length; i++){
            this.contents[i] = new Room(9, 9, i); //Hardcoded odd-numbered sides
        }
        int[] coordinate = {0, 0};
        this.startRoom = new Vertex<Room>(startRoom, coordinate);

    }

    /**
     * Default Constructor for the Map class, to be used in endless mode.
     *
     */
    public Map(Room startRoom){
        int[] coordinate = {0,0};
        this.startRoom = new Vertex<>(startRoom, coordinate);
        this.rooms = this.startRoom.getNeighbors();
        generateNeighbors(this.startRoom);
        populate(); //POP
        startRoom.populateStart();
    }

    private boolean checkLeft(Vertex<Room> room){

        int x = room.getCoordinate()[1];
        int y = room.getCoordinate()[0];
        int[] leftCoords = {y, x-1};

        return room.getVertexAtCoordinate(leftCoords) != null;
    }
    private boolean checkRight(Vertex<Room> room){

        int x = room.getCoordinate()[1];
        int y = room.getCoordinate()[0];
        int[] leftCoords = {y, x+1};

        return room.getVertexAtCoordinate(leftCoords) != null;
    }
    private boolean checkUp(Vertex<Room> room){

        int x = room.getCoordinate()[1];
        int y = room.getCoordinate()[0];
        int[] leftCoords = {y+1, x};

        return room.getVertexAtCoordinate(leftCoords) != null;
    }
    private boolean checkDown(Vertex<Room> room){

        int x = room.getCoordinate()[1];
        int y = room.getCoordinate()[0];
        int[] leftCoords = {y-1, x};

        return room.getVertexAtCoordinate(leftCoords) != null;
    }

    public void generateNeighbors(Vertex<Room> room){

        //lookup to see which directions already have a room there
        //generate in directions without a room
        int[] coordinate = room.getCoordinate();

        if (!checkLeft(room)){ // no previous Room Vertex to the left of the current one
            int[] newCoordinate = {coordinate[0], coordinate[1] - 1};
            Vertex<Room> newRoom = new Vertex<Room>(new Room(9, 9, 0), newCoordinate);
            rooms.add(newRoom);// WHAT NUMBER AM I PUTTING HERE???
            startRoom.connect(newRoom);
            newRoom.connect(startRoom);
            for(Vertex<Room> r : startRoom.getNeighbors()){
                r.connect(newRoom);
                newRoom.connect(r);
            }
        }

        if (!checkRight(room)){ // no previous Room Vertex to the right of the current one
            int[] newCoordinate = {coordinate[0], coordinate[1] + 1};
            Vertex<Room> newRoom = new Vertex<Room>(new Room(9, 9, 0), newCoordinate);
            rooms.add(newRoom);// WHAT NUMBER AM I PUTTING HERE???
            startRoom.connect(newRoom);
            newRoom.connect(startRoom);
            for(Vertex<Room> r : startRoom.getNeighbors()){
                r.connect(newRoom);
                newRoom.connect(r);
            }
        }

        if (!checkUp(room)){ // no previous Room Vertex above the current one
            int[] newCoordinate = {coordinate[0] + 1, coordinate[1]};
            Vertex<Room> newRoom = new Vertex<Room>(new Room(9, 9, 0), newCoordinate);
            rooms.add(newRoom);// WHAT NUMBER AM I PUTTING HERE???
            startRoom.connect(newRoom);
            newRoom.connect(startRoom);
            for(Vertex<Room> r : startRoom.getNeighbors()){
                r.connect(newRoom);
                newRoom.connect(r);
            }
        }

        if (!checkDown(room)){ // no previous Room Vertex below the current one
            int[] newCoordinate = {coordinate[0] - 1, coordinate[1]};
            Vertex<Room> newRoom = new Vertex<Room>(new Room(9, 9, 0), newCoordinate);
            rooms.add(newRoom);// WHAT NUMBER AM I PUTTING HERE???
            startRoom.connect(newRoom);
            newRoom.connect(startRoom);
            for(Vertex<Room> r : startRoom.getNeighbors()){
                r.connect(newRoom);
                newRoom.connect(r);
            }
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
        for (Vertex<Room> room  : startRoom.getNeighbors()){
            flag = room.getValue().hasEntity(entity);
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
        for (Vertex<Room> room  : startRoom.getNeighbors()){
                count += room.getValue().getRemainingEntities(entity);
            }

        return count;
    }

    public int getNumberOfRooms() {
        return startRoom.getNeighbors().size();
    }

    public Set<Vertex<Room>> getContents() {
        return startRoom.getNeighbors();
        
    }
    public Vertex<Room> getStartRoom(){
        return this.startRoom;
    }
    public Set<Vertex<Room>> getRooms() {
        return rooms;
    }
    /**
     * Method which populates the map, therefore populating every Room within the map's array of contents. Removes the
     * unused left-most door of the first Room, as the player cannot travel through a door that is not connected
     * to a room.
     */
    public void populate() {

        startRoom.getValue().populate();

        for (Vertex<Room> room  : startRoom.getNeighbors()){
                room.getValue().populateEndless();

        }

    }

    public void setContents(Room[] contents){
        int[] coord = {0, 0};
        this.startRoom = new Vertex<Room>(contents[0], coord);
        this.rooms.add(startRoom);
        for(int i = 1; i < contents.length; i++){
            int[] nCoord = {i, 0};
            this.rooms.add(new Vertex<Room>(contents[i], nCoord));
        }
        this.contents = contents;
    }
}