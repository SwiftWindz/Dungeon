package MUD.Model.Map;

public class Shrine extends Tile {
    private boolean prayedAt = false;
    private boolean isActive = false;
    public static PlayerState currentState;
    public static Vertex<Room> room;


    /**
     * Constructor for the Tile class.
     *
     * @param r
     * @param c
     */
    public Shrine(int r, int c) {
        super(r, c);
        this.contents = "&";
    }

    public boolean getPrayerStatus() {
        return this.prayedAt;
    }

    public boolean getActiveStatus() {
        return this.isActive;
    }

    public void setPrayerStatus(boolean flag) {
        this.prayedAt = flag;
    }

    public void setActiveStatus(boolean flag) {
        this.isActive = flag;
    }


}
