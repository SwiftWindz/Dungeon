package MUD.Model.Map;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

public class Vertex<E> {
    private Room value;
    private Set<Vertex<Room>> neighbors;
    private int[] coordinate;
    private int roomNumber; //Room index per-instance
    public static int globalNumber = 0; // Largest room index concurrently

    public Vertex(Room value, int[] coordinate) {
        this.value = value;
        this.neighbors = new HashSet<>();
        this.coordinate = coordinate;
        globalNumber++;
        this.roomNumber = globalNumber;
    }

    public Room getValue() {
        return value;
    }

    public int getRoomNumber(){
        return this.roomNumber;
    }

    public void connect(Vertex<Room> neighbor) {
        neighbors.add(neighbor);
    }

    public boolean connected(Vertex<Room> neighbor) {
        return neighbors.contains(neighbor);
    }

    public Set<Vertex<Room>> getNeighbors() {
        return neighbors;
    }

    public Vertex<Room> getVertexAtCoordinate(int[] coordinate){

        for (Vertex<Room> r : this.getNeighbors()){

            if (Arrays.equals(r.getCoordinate(), coordinate)) return r;

        }
        return null;
    }

    public int[] getCoordinate(){
        return this.coordinate;
    }
}

